package coza.opencollab.synthesis.sakai.client.impl;

import coza.opencollab.synthesis.service.api.ModuleService;
import javax.xml.rpc.ParameterMode;
import org.apache.axis.client.Call;
import org.apache.axis.encoding.XMLType;

/**
 * Implements the Module Service to retrieve module data from Sakai.
 *
 * @author OpenCollab
 * @since 1.0.0
 * @version 1.0.0
 */
public class ModuleServiceImpl extends AbstractServiceImpl  implements ModuleService {

    /** {@inheritDoc} */
    @Override
    public String getSitesUserCanAccess(String sessionId, String userId) throws Exception {
        Call nc = getCall();
        nc.setOperationName("getSitesUserCanAccess");
        nc.addParameter("sessionId", XMLType.XSD_STRING, ParameterMode.IN);
        nc.addParameter("userId", XMLType.XSD_STRING, ParameterMode.IN);
        nc.setReturnType(XMLType.XSD_STRING);
        return (String) nc.invoke(new Object[]{sessionId, userId});
    }

    /** {@inheritDoc} */
    @Override
    public String getSitesUserCanAccess(String sessionId) throws Exception {
        Call nc = getCall();
        nc.setOperationName("getSitesUserCanAccess");
        nc.addParameter("sessionId", XMLType.XSD_STRING, ParameterMode.IN);
        nc.setReturnType(XMLType.XSD_STRING);
        return (String) nc.invoke(new Object[]{sessionId});
    }
}
