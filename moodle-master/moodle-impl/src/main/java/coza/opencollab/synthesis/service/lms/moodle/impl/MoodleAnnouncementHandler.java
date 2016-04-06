package coza.opencollab.synthesis.service.lms.moodle.impl;

import static coza.opencollab.synthesis.service.api.ErrorCodes.LMS_CONTENT;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.activation.MimetypesFileTypeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.codec.Base64;

import coza.opencollab.synthesis.SynthesisException;
import coza.opencollab.synthesis.moodle.client.AnnouncementService;
import coza.opencollab.synthesis.moodle.client.MoodleWS;
import coza.opencollab.synthesis.moodle.client.ResourcesService;
import coza.opencollab.synthesis.moodle.client.dao.Discussion;
import coza.opencollab.synthesis.moodle.client.dao.Forum;
import coza.opencollab.synthesis.moodle.client.dao.Post;
import coza.opencollab.synthesis.moodle.client.dao.PostWrapper;
import coza.opencollab.synthesis.moodle.client.dao.file.MoodleFile;
import coza.opencollab.synthesis.service.api.lms.ToolContentService;
import coza.opencollab.synthesis.shared.Announcement;
import coza.opencollab.synthesis.shared.Attachment;
import java.io.UnsupportedEncodingException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * The Moodle implementation of the announcement handler.
 *
 * @author OpenCollab
 * @since 1.0.0
 */
public class MoodleAnnouncementHandler implements ToolContentService {

    /*
     * The Moodle announcements for a module
     */
    private AnnouncementService announcementService;
    private ResourcesService resourceService;

    private final MimetypesFileTypeMap mimeTypesMap;

    @Autowired
    public MoodleAnnouncementHandler(AnnouncementService announcementService, ResourcesService resourceService) {
        super();
        this.announcementService = announcementService;
        this.resourceService = resourceService;
        mimeTypesMap = new MimetypesFileTypeMap();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Object getContent(String token, String courseId, Date fromDate) {
        Forum forum = new Forum();
        try {
            forum = announcementService.getAnnouncements(token, Integer.parseInt(courseId));
            return getAnnouncements(token, forum, fromDate);
        } catch (Exception e) {
            throw new SynthesisException(LMS_CONTENT, "Could not retrieve the announcements from Moodle (" + courseId + ").", e);
        }
    }

    /**
     * Retrieve the Announcement from the xml retrieved from Moodle.
     *
     * @return List of announcements
     */
    public List<Announcement> getAnnouncements(String token, Forum forum, Date fromDate) {
        List<Announcement> announcements = new ArrayList<>();
        List<Discussion> discussions = forum.getDiscussions();
        int order = 0;
        if (discussions != null) {
            for (Discussion disc : discussions) {
                List<PostWrapper> postWrappers = disc.getPosts();
                if (postWrappers != null) {
                    for (PostWrapper postWrapper : postWrappers) {
                        List<Post> posts = new ArrayList<Post>();
                        posts = postWrapper.getPosts();
                        for (Post post : posts) {
                            if (!postExpired(post, fromDate)) {
                                Announcement announcement = convertToAnnouncement(disc, post, token, order);
                                retrieveAndAddAttachments(token, forum, announcement, post, token);
                                announcements.add(announcement);
                                order = order++;
                            }
                        }
                    }
                }
            }
        }
        return (announcements.isEmpty())? null :announcements;
    }

    /**
     * @param post
     * @param fromDate if null the method returns false
     * @return whether the post has expired i.e. if the date posted was before
     * the fromDate
     */
    private boolean postExpired(Post post, Date fromDate) {
        if (fromDate == null) {
            return false;
        }
        Date postDate = MoodleWS.Converter.convertToDate(post.getCreated());
        if (post.getModified() > 0) {
            postDate = MoodleWS.Converter.convertToDate(post.getModified());
        }
        if (postDate.after(fromDate)) {
            return false;
        }
        return true;
    }

    /**
     * Converts the Moodle Discussion and Post to an Announcement
     *
     * @param discussion
     * @param postEntry
     * @return
     */
    private Announcement convertToAnnouncement(Discussion discussion, Post postEntry, String token, int order) {
        Announcement announcement = new Announcement();
        announcement.setId(String.valueOf(postEntry.getId()));
        announcement.setDate(MoodleWS.Converter.convertToDate(postEntry.getCreated()));
        announcement.setFrom(postEntry.getUserfullname());
        announcement.setSubject(discussion.getSubject());
        if (discussion.getTimestart() != 0) {
            announcement.setBeginDate(MoodleWS.Converter.convertToDate(discussion.getTimestart()));
        }
        if (discussion.getTimeend() != 0) {
            announcement.setEndDate(MoodleWS.Converter.convertToDate(discussion.getTimeend()));
        }
        announcement.setModDate(MoodleWS.Converter.convertToDate(discussion.getTimemodified()));
        announcement.setBody(postEntry.getMessage());
        if(!announcement.getBody().contains("webservice/pluginfile.php")){
            announcement.setBody(announcement.getBody().replaceAll("pluginfile.php", "webservice/pluginfile.php"));
        }
        //add token to any in body resources
        Document doc = Jsoup.parse(announcement.getBody());
        Elements images = doc.getElementsByTag("img");
        for (Element image : images) {
            String imageSrc = image.attr("src");
            if(imageSrc.contains("pluginfile.php")){
                announcement.setBody(announcement.getBody().replaceAll(imageSrc, imageSrc.concat("?token="+token)));
            }
        }
        announcement.setOrder(String.valueOf(order));
        return announcement;
    }

    void retrieveAndAddAttachments(String token, Forum forum, Announcement announcement, Post post, String sessionId) {
        Collection<MoodleFile> attachments = resourceService.retrieveMoodleFiles(token, MoodleWS.Parameter.Component.FORUM,
                MoodleWS.Parameter.FileArea.Attachment, forum.getCmid(), post.getId());
        Collection<MoodleFile> postAttachments = resourceService.retrieveMoodleFiles(token, MoodleWS.Parameter.Component.FORUM,
                MoodleWS.Parameter.FileArea.Post, forum.getCmid(), post.getId());
        attachments.addAll(postAttachments);
        for (MoodleFile mFile : attachments) {
            announcement.addToAttachments(convertFileToAttachment(mFile, sessionId));
        }
    }

    Attachment convertFileToAttachment(MoodleFile file, String sessionId) {
        Attachment attachment = new Attachment();
        String path = getPathFromURL(file.getUrl());
        String id = null;
        try{
            id = java.net.URLEncoder.encode(Base64.encode(path.getBytes()).toString(), "UTF-8");
        }catch(UnsupportedEncodingException uee ){
            id = Base64.encode(path.getBytes()).toString();
        }
        attachment.setId(id);
        attachment.setUrl(file.getUrl());
        attachment.setName(file.getFilename());
        attachment.setLink(false);
        attachment.setPath(path);
        //attachment.setContent(resourceService.downloadResource(file.getUrl(), sessionId));
        attachment.setMimeType(mimeTypesMap.getContentType(file.getFilename()));
        //attachment.setSize(attachment.getContent().length);
        attachment.setContentURL(resourceService.getDownloadResourceUrl( file.getUrl(), sessionId ));
        return attachment;
    }

    String getPathFromURL(String url) {
        String pl = "pluginfile.php";
        return url.substring(url.indexOf(pl) + pl.length() + 1);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Object getUserContent(String sessionId, String username, String moduleId, Date fromDate) {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Map updateUserContent(String sessionId, String username, String moduleId, Map content, String originalContent) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
