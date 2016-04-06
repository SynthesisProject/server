package coza.opencollab.synthesis.service.lms.sakai.impl;

import coza.opencollab.synthesis.SynthesisException;
import coza.opencollab.synthesis.sakai.client.AnnouncementService;
import static coza.opencollab.synthesis.service.api.ErrorCodes.*;
import static coza.opencollab.synthesis.service.api.util.impl.DomHelper.*;
import coza.opencollab.synthesis.service.api.lms.ToolContentService;
import coza.opencollab.synthesis.shared.Announcement;
import coza.opencollab.synthesis.shared.Attachment;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.dom4j.Document;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * The Sakai implementation of the announcement handler.
 *
 * @author OpenCollab
 * @since 1.0.0
 * @version 1.0.0
 */
public class SakaiAnnouncementHandler implements ToolContentService {
    /*
     * The Sakai announcements for a module
     */
    @Autowired
    private AnnouncementService announcementService;
    /*
     * The Sakai resources for a module
     */
    @Autowired
    private SakaiResourceHandler resourcesHandler;

    /*
     * The Sakai resources for a module
     */
    /**
     * <p>Setter for the field <code>resourcesHandler</code>.</p>
     *
     * @param resourcesHandler a {@link coza.opencollab.synthesis.service.lms.sakai.impl.SakaiResourceHandler} object.
     */
    public void setResourcesHandler(SakaiResourceHandler resourcesHandler) {
        this.resourcesHandler = resourcesHandler;
    }

    /** {@inheritDoc} */
    @Override
    public Object getContent(String sessionId, String moduleId, Date fromDate) {
        try {
            Document document = createDocument(announcementService.getAnnouncementsForSite(sessionId, moduleId, fromDate));
            return getAnnouncements(sessionId, moduleId, document);
        } catch (Exception e) {
            throw new SynthesisException(LMS_CONTENT, "Could not retrieve the announcements from Sakai (" + moduleId + ").", e);
        }
    }

    /**
     * Retrieve the Announcement from the xml retrieved from Sakai.
     *
     * @return List of announcements
     * @param sessionId a {@link java.lang.String} object.
     * @param moduleId a {@link java.lang.String} object.
     * @param document a {@link org.dom4j.Document} object.
     */
    public List<Announcement> getAnnouncements(String sessionId, String moduleId, Document document) {
        List<Announcement> announcements = new ArrayList<Announcement>();
        List<Element> elements = getElements(document, "/list/message");
        for (Element element : elements) {
            announcements.add(getAnnouncement(sessionId, moduleId, element));
        }
        return announcements;
    }

    /**
     * Get Announcement receives a XML element which contains a announcement. It then parses the element and returns an
     * announcement object.
     *
     * @param announcementData XML node
     * @return List of announcements
     */
    private Announcement getAnnouncement(String sessionId, String moduleId, Element element) {
        Announcement announcement = new Announcement();
        announcement.setId(getAttribute(element, "id"));
        announcement.setDate(getAttributeAsDate(element, "date"));
        announcement.setFrom(getAttribute(element, "from"));
        announcement.setOrder(getAttribute(element, "order"));
        announcement.setSubject(getAttribute(element, "subject"));
        announcement.setUrl(getAttribute(element, "url"));
        announcement.setBeginDate(getAttributeAsDate(element, "begin_date"));
        announcement.setEndDate(getAttributeAsDate(element, "end_date"));
        announcement.setModDate(getAttributeAsDate(element, "mod_date"));
        announcement.setStatus(getAttribute(element, "status"));
        Element body = getElement(element, "body");
        announcement.setBody(body.getText());
        List<Element> groups = getElements(element, "groups/group");
        for(Element group: groups){
            announcement.addToGroups(group.getText());
        }
        List<Element> attachments = getElements(element, "attachments/attachment");
        for(Element attach: attachments){
            Attachment attachment = new Attachment();
            attachment.setId(attach.attributeValue("id"));
            attachment.setName(attach.attributeValue("name"));
            attachment.setSize(Integer.parseInt(attach.attributeValue("size")));
            attachment.setMimeType(attach.attributeValue("type"));
            String url = attach.attributeValue("url");
            attachment.setUrl(url);
            attachment.setPath(url.substring(url.indexOf(moduleId)));
            attachment.setContentURL(resourcesHandler.getResourceURL(sessionId, attach.attributeValue("id")));
            announcement.addToAttachments(attachment);
        }
        return announcement;
    }

    /** {@inheritDoc} */
    @Override
    public Object getUserContent(String sessionId, String username, String moduleId, Date fromDate) {
        return null;
    }

    /** {@inheritDoc} */
    @Override
    public Map updateUserContent(String sessionId, String username, String moduleId, Map content, String originalContent) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
