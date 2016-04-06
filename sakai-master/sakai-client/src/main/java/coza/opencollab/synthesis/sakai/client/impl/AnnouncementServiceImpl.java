package coza.opencollab.synthesis.sakai.client.impl;

import coza.opencollab.synthesis.sakai.client.AnnouncementService;
import java.util.Date;
import javax.xml.rpc.ParameterMode;
import org.apache.axis.client.Call;
import org.apache.axis.encoding.XMLType;

/**
 * <p>AnnouncementServiceImpl class.</p>
 *
 * @author OpenCollab
 * @version 1.0.0
 */
public class AnnouncementServiceImpl extends AbstractServiceImpl  implements AnnouncementService {

    /** {@inheritDoc} */
    @Override
    public String getAnnouncementsForSite(String sessionId, String siteId, Date fromDate) throws Exception {
         Call nc = getCall();
        nc.setOperationName("getAnnouncementsForSite");
        nc.addParameter("sessionId", XMLType.XSD_STRING, ParameterMode.IN);
        nc.addParameter("siteId", XMLType.XSD_STRING, ParameterMode.IN);
        nc.addParameter("fromDate", XMLType.XSD_STRING, ParameterMode.IN);
        nc.setReturnType(XMLType.XSD_STRING);
        String dateString = "";
        if(fromDate != null){
            dateString = String.valueOf(fromDate.getTime());
        }
        return (String) nc.invoke(new Object[]{sessionId, siteId, dateString});
    }
}
