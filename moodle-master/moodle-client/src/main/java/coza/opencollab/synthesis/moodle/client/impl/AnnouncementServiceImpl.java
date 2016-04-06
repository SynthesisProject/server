package coza.opencollab.synthesis.moodle.client.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import coza.opencollab.synthesis.moodle.client.AnnouncementService;
import coza.opencollab.synthesis.moodle.client.MoodleWS;
import coza.opencollab.synthesis.moodle.client.ResourcesService;
import coza.opencollab.synthesis.moodle.client.dao.Discussion;
import coza.opencollab.synthesis.moodle.client.dao.Forum;
import coza.opencollab.synthesis.moodle.client.dao.Post;
import coza.opencollab.synthesis.moodle.client.dao.PostWrapper;
import coza.opencollab.synthesis.moodle.client.dao.file.MoodleFile;
import java.util.Collection;

/**
 *
 * @author OpenCollab
 */
public class AnnouncementServiceImpl extends AbstractServiceImpl implements AnnouncementService {

    private static final String FORUMSFUNCTION = "mod_forum_get_forums_by_courses";
    private static final String DISCUSSIONSFUNCTION = "mod_forum_get_forum_discussions_paginated";
    private static final String POSTFUNCTION = "mod_forum_get_forum_discussion_posts";

    /**
     * Default constructor used by Spring
     */
    public AnnouncementServiceImpl() {
    }

    /**
     * Constructor used for testing
     *
     * @param timeout
     * @param host
     * @param baseRestStr
     * @param appName
     */
    public AnnouncementServiceImpl(int timeout, String host,
            String baseRestStr, String appName) {
        super(timeout, host, baseRestStr, appName);
    }

    /**
     * {@inheritDoc}
     */
    public Forum getAnnouncements(String token, int courseId) {
        Forum announceforum = new Forum();
        try {
            announceforum = getNewsForum(callService(token, FORUMSFUNCTION, "courseids[]=" + courseId));
            List<Discussion> discussions = getDiscussionsFromForum(token, announceforum.getId());
            announceforum.setDiscussions(discussions);
        } catch (Exception e) {
            log.error("Error getting announcements ", e);
        }
        return announceforum;
    }

    /**
     * Transform JSON into required Forum object and filter out forums of type
     * "News"
     *
     * @param json
     * @return
     */
    private Forum getNewsForum(String json) {
        List<Forum> forums = new ArrayList<Forum>();
        forums = gson.fromJson(json, new TypeToken<List<Forum>>() {
        }.getType());
        for (Forum f : forums) {
            if (f.getType().equals("news")) {
                return f;
            }
        }
        return null;
    }

    /**
     * Get moodle discussion from a forum
     *
     * @param token
     * @param forumId
     * @return
     * @throws Exception
     */
    private List<Discussion> getDiscussionsFromForum(String token, int forumId) throws Exception {
        String json = callService(token, DISCUSSIONSFUNCTION, "forumid=" + forumId);
        return getDiscussions(json, token);
    }

    /**
     * Parse JSON and return a list of Discussions
     *
     * @param json
     * @param token
     * @return
     */
    private List<Discussion> getDiscussions(String json, String token) {
        Map<String, List<Discussion>> discussionMap = gson.fromJson(json, new TypeToken<Map<String, List<Discussion>>>() {
        }.getType());
        List<Discussion> discussions = discussionMap.get("discussions");
        for (Discussion discussion : discussions) {
            List<PostWrapper> posts = discussion.getPosts();
            try {
                PostWrapper post = getDiscussionPosts(token, discussion.getId());
                posts.add(post);
            } catch (Exception e) {
                log.error("Error getting discussions ", e);
            }
            discussion.setPosts(posts);
        }
        return discussions;
    }

    /**
     * Get posts for a discussion
     *
     * @param token
     * @param discussionId
     * @return
     * @throws Exception
     */
    private PostWrapper getDiscussionPosts(String token, int discussionId) throws Exception {
        String json = callService(token, POSTFUNCTION, "discussionid=" + discussionId);
        PostWrapper post = gson.fromJson(json, new TypeToken<PostWrapper>() {
        }.getType());
        return post;
    }
}
