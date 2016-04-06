package coza.opencollab.synthesis.sakai.client.impl;

import coza.opencollab.synthesis.service.api.client.EventService;
import javax.xml.rpc.ParameterMode;
import org.apache.axis.client.Call;
import org.apache.axis.encoding.XMLType;

/**
 * Implements the Event Service to post events to Sakai.
 *
 * @author OpenCollab
 * @since 1.0.0
 * @version 1.0.0
 */
public class EventServiceImpl extends AbstractServiceImpl  implements EventService{

    /** {@inheritDoc} */
    @Override
    public void postEvent(String sessionId, String username, String eventName, String resource) throws Exception {
        postEvent(sessionId, username, eventName, resource, null, false);
    }

    /** {@inheritDoc} */
    @Override
    public void postEvent(String sessionId, String eventName, String resource, String context, boolean modify) throws Exception {
        Call nc = getCall();
        nc.setOperationName("postEvent");
        nc.addParameter("sessionid", XMLType.XSD_STRING, ParameterMode.IN);
        nc.addParameter("eventName", XMLType.XSD_STRING, ParameterMode.IN);
        nc.addParameter("resource", XMLType.XSD_STRING, ParameterMode.IN);
        nc.addParameter("context", XMLType.XSD_STRING, ParameterMode.IN);
        nc.addParameter("modify", XMLType.XSD_BOOLEAN, ParameterMode.IN);
        nc.setReturnType(XMLType.XSD_ANY);
        nc.invoke(new Object[]{sessionId, eventName, resource, context, modify});
    }

    /** {@inheritDoc} */
    @Override
    public void postEvent(String sessionId, String username, String eventName, String resource, String context, boolean modify) throws Exception {
        Call nc = getCall();
        nc.setOperationName("postEvent");
        nc.addParameter("sessionid", XMLType.XSD_STRING, ParameterMode.IN);
        nc.addParameter("username", XMLType.XSD_STRING, ParameterMode.IN);
        nc.addParameter("eventName", XMLType.XSD_STRING, ParameterMode.IN);
        nc.addParameter("resource", XMLType.XSD_STRING, ParameterMode.IN);
        nc.addParameter("context", XMLType.XSD_STRING, ParameterMode.IN);
        nc.addParameter("modify", XMLType.XSD_BOOLEAN, ParameterMode.IN);
        nc.setReturnType(XMLType.XSD_ANY);
        nc.invoke(new Object[]{sessionId, username, eventName, resource, context, modify});
    }
}
