package coza.opencollab.synthesis.sakai.client.impl;

import coza.opencollab.synthesis.sakai.client.impl.ResourcesServiceImpl;
import coza.opencollab.synthesis.sakai.client.impl.SakaiLoginServiceImpl;
import java.util.Date;
import org.junit.Test;
import org.junit.BeforeClass;
import org.junit.Ignore;

/**
 *
 * @author Jaques
 */
@Ignore
public class ResourceTest {
    private static final SakaiLoginServiceImpl login = new SakaiLoginServiceImpl();
    private static final ResourcesServiceImpl service = new ResourcesServiceImpl();
    
    @BeforeClass
    public static void setup(){
        login.setServiceWSDL("https://sakai.opencollab.co.za/sakai-axis/SakaiLogin.jws?wsdl");
        service.setServiceWSDL("https://sakai.opencollab.co.za/sakai-axis/ResourcesTool.jws?wsdl");
    }
    
    public ResourceTest() {
    }
    
    @Test
    public void testAnnouncments() throws Exception{
        String sessionId = login.login("oc_admin", "SpringCl@w");
        System.out.println(sessionId);
        String xml = service.getResourcesForSite(sessionId, "AFL1501-13-S1-43T", null);
        System.out.println(xml);
        xml = service.getResourcesForSite(sessionId, "AFL1501-13-S1-43T", new Date());
        System.out.println(xml);
        System.out.println(new Date(1359685503000L));
        System.out.println(new Date(1360613563000L));
        xml = service.getResourceData(sessionId, "/group/AFL1501-13-S1-43T/Links/OfficialStudyMaterial");
        System.out.println(xml);
    }
}