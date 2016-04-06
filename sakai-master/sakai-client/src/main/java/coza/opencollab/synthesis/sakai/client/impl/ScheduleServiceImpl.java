package coza.opencollab.synthesis.sakai.client.impl;

import coza.opencollab.synthesis.sakai.client.ScheduleService;
import java.util.Date;
import javax.xml.rpc.ParameterMode;
import org.apache.axis.client.Call;
import org.apache.axis.encoding.XMLType;

/**
 * <p>ScheduleServiceImpl class.</p>
 *
 * @author OpenCollab
 * @version 1.0.0
 */
public class ScheduleServiceImpl extends AbstractServiceImpl  implements ScheduleService {

    /** {@inheritDoc} */
    @Override
    public String getScheduleForSite(String sessionId, String siteId, Date fromDate) throws Exception {
        Call nc = getCall();
        nc.setOperationName("getScheduleForSite");
        nc.addParameter("sessionId", XMLType.XSD_STRING, ParameterMode.IN);
        nc.addParameter("siteId", XMLType.XSD_STRING, ParameterMode.IN);
        nc.addParameter("fromDate", XMLType.XSD_STRING, ParameterMode.IN);
        nc.setReturnType(XMLType.XSD_STRING);
        String dateString = "";
        if (fromDate != null) {
            dateString = String.valueOf(fromDate.getTime());
        }
        return (String) nc.invoke(new Object[]{sessionId, siteId , dateString});
    }
}
