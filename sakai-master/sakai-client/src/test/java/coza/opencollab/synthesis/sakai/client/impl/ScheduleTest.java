package coza.opencollab.synthesis.sakai.client.impl;

import coza.opencollab.synthesis.sakai.client.impl.ScheduleServiceImpl;
import coza.opencollab.synthesis.sakai.client.impl.SakaiLoginServiceImpl;
import java.util.Date;
import org.junit.Test;
import org.junit.BeforeClass;
import org.junit.Ignore;

/**
 *
 * @author OpenCollab
 */
@Ignore
public class ScheduleTest {
    private static final SakaiLoginServiceImpl login = new SakaiLoginServiceImpl();
    private static final ScheduleServiceImpl service = new ScheduleServiceImpl();
    
    @BeforeClass
    public static void setup(){
        login.setServiceWSDL("https://sakai.opencollab.co.za/sakai-axis/SakaiLogin.jws?wsdl");
        service.setServiceWSDL("https://sakai.opencollab.co.za/sakai-axis/ScheduleTool.jws?wsdl");
    }
    
    public ScheduleTest() {
    }
    
    @Test
    public void testSchedules() throws Exception{
        String sessionId = login.login("oc_admin", "SpringCl@w");
        System.out.println(sessionId);
        String xml = service.getScheduleForSite(sessionId, "AFL1501-13-S1-43T", null);
        System.out.println(xml);
        xml = service.getScheduleForSite(sessionId, "AFL1501-13-S1-43T", new Date());
        System.out.println(xml);
        System.out.println(new Date(1359685503000L));
        System.out.println(new Date(1360613563000L));
    }
}