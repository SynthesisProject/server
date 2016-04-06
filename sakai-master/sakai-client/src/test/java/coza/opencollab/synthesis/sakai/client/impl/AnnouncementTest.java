package coza.opencollab.synthesis.sakai.client.impl;

import java.util.Date;
import org.junit.Test;
import org.junit.BeforeClass;
import org.junit.Ignore;

/**
 *
 * @author OpenCollab
 */
@Ignore
public class AnnouncementTest {
    private static final SakaiLoginServiceImpl login = new SakaiLoginServiceImpl();
    private static final AnnouncementServiceImpl service = new AnnouncementServiceImpl();
    
    @BeforeClass
    public static void setup(){
        login.setServiceWSDL("https://sakai.opencollab.co.za/sakai-axis/SakaiLogin.jws?wsdl");
        service.setServiceWSDL("https://sakai.opencollab.co.za/sakai-axis/AnnouncementTool.jws?wsdl");
    }
    
    public AnnouncementTest() {
    }
    
    @Test
    public void testAnnouncments() throws Exception{
        String sessionId = login.login("oc_admin", "SpringCl@w");
        System.out.println(sessionId);
        String xml = service.getAnnouncementsForSite(sessionId, "AFL1501-13-S1-43T", null);
        System.out.println(xml);
        xml = service.getAnnouncementsForSite(sessionId, "AFL1501-13-S1-43T", new Date());
        System.out.println(xml);
        System.out.println(new Date(1359685503000L));
        System.out.println(new Date(1360613563000L));
    }
}