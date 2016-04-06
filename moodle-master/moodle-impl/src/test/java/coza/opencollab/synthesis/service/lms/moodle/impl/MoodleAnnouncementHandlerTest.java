package coza.opencollab.synthesis.service.lms.moodle.impl;
		

import coza.opencollab.synthesis.service.lms.moodle.impl.MoodleAnnouncementHandler;
import java.util.List;

import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import coza.opencollab.synthesis.moodle.client.dao.Forum;
import coza.opencollab.synthesis.moodle.client.impl.AnnouncementServiceImpl;
import coza.opencollab.synthesis.moodle.client.impl.MoodleLoginServiceImpl;
import coza.opencollab.synthesis.moodle.client.impl.ResourcesServiceImpl;
import coza.opencollab.synthesis.shared.Announcement;

public class MoodleAnnouncementHandlerTest {

    private static final Logger log = Logger.getLogger(MoodleAnnouncementHandlerTest.class);
    private String host = "http://moodle.opencollab.co.za";
    private String baseRestStr = host + "/webservice/rest/server.php?moodlewsrestformat=json&wstoken={0}&wsfunction={1}&{2}";
    private String appName = "moodle_mobile_app";
    private int timeout = 30000;
    
    private MoodleLoginServiceImpl loginService;
    private AnnouncementServiceImpl annc;
    private ResourcesServiceImpl resourceService;
    private MoodleAnnouncementHandler mah;
    

    @Before
	public void before() {
    	loginService = new MoodleLoginServiceImpl(timeout, host, baseRestStr, appName);
    	annc = new AnnouncementServiceImpl(timeout, host, baseRestStr, appName);
    	resourceService = new ResourcesServiceImpl(timeout, host, baseRestStr, appName);
        mah = new MoodleAnnouncementHandler(annc,resourceService);
    }
    
    @Ignore
    @Test
    public void shouldGetAnnouncementsTest() throws Exception {

        String token = loginService.login("admin","Moodl3!s50cool");
        log.debug("Login token : " + token);
        Forum forum = annc.getAnnouncements(token, 3);

        log.info("forum.getDiscussions().size(): " + forum.getDiscussions().size());
        log.info(forum.toString());
        List<Announcement> announcements = mah.getAnnouncements(token, forum, DateTime.now().minusYears(1).toDate());
        for(Announcement ann: announcements){
            log.info(ann.toString());
        }
        Assert.assertTrue(!announcements.isEmpty());
    }

    @Ignore
    @Test
    public void shouldGetPathFromUrl() {
    	String path = mah.getPathFromURL("http://localhost:8080/pluginfile.php/30/mod_forum/attachment/6/3337126049.jpg");
    	Assert.assertEquals("30/mod_forum/attachment/6/3337126049.jpg", path);
    }
    
    
}


