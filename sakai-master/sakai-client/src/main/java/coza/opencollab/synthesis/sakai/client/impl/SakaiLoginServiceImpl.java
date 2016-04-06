package coza.opencollab.synthesis.sakai.client.impl;

import coza.opencollab.synthesis.service.api.LoginService;
import javax.xml.rpc.ParameterMode;
import org.apache.axis.client.Call;
import org.apache.axis.encoding.XMLType;

/**
 * Implements the login service.
 *
 * @author OpenCollab
 * @since 1.0.0
 * @version 1.0.0
 */
public class SakaiLoginServiceImpl extends AbstractServiceImpl implements LoginService {
    
    /** {@inheritDoc} */
    @Override
    public String login(String username, String password) throws Exception {
        Call nc = getCall();
        nc.setOperationName("login");
        nc.addParameter("id", XMLType.XSD_STRING, ParameterMode.IN);
        nc.addParameter("pw", XMLType.XSD_STRING, ParameterMode.IN);
        nc.setReturnType(XMLType.XSD_STRING);
        return (String) nc.invoke(new Object[] { username, password });
    }
    
    /** {@inheritDoc} */
    @Override
    public boolean logout(String sessionId) throws Exception {
        Call nc = getCall();
        nc.setOperationName("logout");
        nc.addParameter("sessionid", XMLType.XSD_STRING, ParameterMode.IN);
        nc.setReturnType(XMLType.XSD_BOOLEAN);
        return (Boolean) nc.invoke(new Object[] { sessionId });
    }
    
    /** {@inheritDoc} */
    @Override
    public String getUserDetails(String sessionId, String username) throws Exception {
        Call nc = getCall();
        nc.setOperationName("getUserDetails");
        nc.addParameter("sessionid", XMLType.XSD_STRING, ParameterMode.IN);
        nc.addParameter("id", XMLType.XSD_STRING, ParameterMode.IN);
        nc.setReturnType(XMLType.XSD_STRING);
        return (String) nc.invoke(new Object[] { sessionId, username});
    }
}
