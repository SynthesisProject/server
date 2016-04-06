package coza.opencollab.synthesis.service.content.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;

import com.google.gson.Gson;

import coza.opencollab.synthesis.moodle.client.dao.Forum;
import coza.opencollab.synthesis.moodle.client.dao.Token;
import coza.opencollab.synthesis.moodle.client.impl.AnnouncementServiceImpl;
import coza.opencollab.synthesis.moodle.client.impl.MoodleLoginServiceImpl;
import coza.opencollab.synthesis.moodle.client.impl.ResourcesServiceImpl;
import coza.opencollab.synthesis.service.api.util.LinkDownLoader;
import coza.opencollab.synthesis.service.api.util.LinkFileConsumer;
import coza.opencollab.synthesis.service.api.util.impl.ByteArrayEntry;
import coza.opencollab.synthesis.service.content.moodle.impl.MoodleAnnouncementConverter;
import coza.opencollab.synthesis.service.creator.impl.DefaultStorageService;
import coza.opencollab.synthesis.service.lms.moodle.impl.MoodleAnnouncementHandler;
import coza.opencollab.synthesis.shared.Announcement;

/**
 *
 * @author OpenCollab
 */
public class AnnouncementConverterTest {
    private static final Logger log = Logger.getLogger(AnnouncementConverterTest.class);
    private static final String MODULE_ID = "AFL1501-13-S1-43T";
    private String host = "http://moodle.opencollab.co.za";
    private String baseRestStr = host + "/webservice/rest/server.php?moodlewsrestformat=json&wstoken={0}&wsfunction={1}&{2}";
    private String appName = "moodle_mobile_app";
    private int timeout = 30000;
    private MoodleLoginServiceImpl loginService;
    
    public AnnouncementConverterTest() {
        loginService = new MoodleLoginServiceImpl(timeout, host, baseRestStr, appName);
    }
    
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

   
    
        /**
     * Test of convert method, of class AnnouncementConverter.
     */
//    @Test
    public void testMoodleConvert() {
        String host = "moodle.opencollab.co.za";
        String loginStr = "http://" + host
            + "/login/token.php?username={0}&password={1}&service={2}";
        String baseRestStr = "http://"
            + host
            + "/webservice/rest/server.php?moodlewsrestformat={0}&wstoken={1}&wsfunction={2}&{3}";
        String tokenStr = loginService.login("admin","Moodl3!s50cool");
        Token token = new Gson().fromJson(tokenStr, Token.class);
        AnnouncementServiceImpl annc = new AnnouncementServiceImpl();
        annc.setBaseRestStr(baseRestStr);
        Forum forum = new Forum();
        try {
            forum = annc.getAnnouncements(token.getToken(), 2);
        } catch (Exception e) {
            e.printStackTrace();
        }
        MoodleAnnouncementHandler mah = new MoodleAnnouncementHandler(annc, new ResourcesServiceImpl());
        List<Announcement> announcements = mah.getAnnouncements(token.getToken(), forum, new Date());
        assertEquals(announcements.size(), 3);
        
        MoodleAnnouncementConverter converter = new MoodleAnnouncementConverter();
        converter.setStorageService(new DefaultStorageService());
        converter.setLinkDownLoader(new LinkDownLoader(){
            @Override
            public String update(String key, String content, LinkFileConsumer consumer){
                return content;
            }
        });
        List resultStorageEntries = converter.convert(MODULE_ID, announcements);
        ByteArrayEntry de = (ByteArrayEntry) resultStorageEntries.get(0);
        log.info(de.getRelativePath());
        try {
            String contents = new String(de.getContents(),"UTF-8");
            log.info(contents);
        }catch(Exception e){
        }
        
        assertTrue(resultStorageEntries.size() > 0 );
    }
}