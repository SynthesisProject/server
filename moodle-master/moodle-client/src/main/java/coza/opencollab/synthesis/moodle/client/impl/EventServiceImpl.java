package coza.opencollab.synthesis.moodle.client.impl;

import coza.opencollab.synthesis.service.api.client.EventService;

/**
 *
 * @author francois
 */
public class EventServiceImpl extends AbstractServiceImpl  implements EventService{

    @Override
    public void postEvent(String sessionId, String username, String eventName, String resource) throws Exception {
        //do nothing
    }

    @Override
    public void postEvent(String sessionId, String eventName, String resource, String context, boolean modify) throws Exception {
        //do nothing
    }

    @Override
    public void postEvent(String sessionId, String username, String eventName, String resource, String context, boolean modify) throws Exception {
        //do nothing
    }
    
}
