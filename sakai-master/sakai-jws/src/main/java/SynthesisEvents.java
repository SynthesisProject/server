
import org.apache.axis.AxisFault;
import org.sakaiproject.component.cover.ComponentManager;
import org.sakaiproject.event.api.Event;
import org.sakaiproject.event.api.EventTrackingService;
import org.sakaiproject.event.api.NotificationService;
import org.sakaiproject.tool.api.Session;
import org.sakaiproject.tool.api.SessionManager;
import org.sakaiproject.user.api.User;
import org.sakaiproject.user.api.UserDirectoryService;
import org.sakaiproject.user.api.UserNotDefinedException;

/**
 * This is the service script to handle events.
 * 
 * @author OpenCollab
 * @since 1.0.0
 */
public class SynthesisEvents {
    private EventTrackingService eventTrackingService;
    private SessionManager sessionManager;
    private UserDirectoryService userDirectoryService;
    
    /**
     * Setup dependencies
     */
    public SynthesisEvents(){
        eventTrackingService = (EventTrackingService) ComponentManager.get(EventTrackingService.class.getName());
        sessionManager = (SessionManager) ComponentManager.get(SessionManager.class.getName());
        userDirectoryService = (UserDirectoryService) ComponentManager.get(UserDirectoryService.class.getName());
    }

    /**
     * Get the Session related to the given session id
     *
     * @param sessionid	the id of the session to retrieve
     * @return	the session, if it is active
     * @throws AxisFault if session is inactive
     */
    private Session establishSession(String sessionid) throws AxisFault {
        Session s = sessionManager.getSession(sessionid);

        if (s == null) {
            throw new AxisFault("Session \"" + sessionid + "\" is not active");
        }
        s.setActive();
        sessionManager.setCurrentSession(s);
        return s;
    }
    
    /**
     * Create a event in Sakai for the user.
     * The context will be null and modify will be false.
     * 
     * @param sessionId A admin session id.
     * @param username A username to log the event to.
     * @param eventName The event name.
     * @param resource The resource.
     * @throws org.apache.axis.AxisFault axis fault
     */
    public void postEvent(String sessionId, String username, String eventName, String resource) throws AxisFault{
        postEvent(username, eventName, resource, null, false);
    }
    
    /**
     * Create a event in Sakai for the user.
     * 
     * @param sessionId A admin session id.
     * @param eventName The event name.
     * @param resource The resource.
     * @param context The context.
     * @param modify true if the event cause something to be modified.
     * @throws org.apache.axis.AxisFault axis fault
     */
    public void postEvent(String sessionId, String eventName, String resource, String context, boolean modify) throws AxisFault{
        Session s = establishSession(sessionId);
        postEvent(sessionId, s.getUserEid(), eventName, resource, context, modify);
    }
    
    /**
     * Create a event in Sakai for the user.
     * 
     * @param sessionId A admin session id.
     * @param username A username to log the event to.
     * @param eventName The event name.
     * @param resource The resource.
     * @param context The context.
     * @param modify true if the event cause something to be modified.
     * @throws org.apache.axis.AxisFault axis fault
     */
    public void postEvent(String sessionId, String username, String eventName, String resource, String context, boolean modify) throws AxisFault{
        establishSession(sessionId);
        User user;
        try {
            user = userDirectoryService.getUserByEid(username);
        } catch (UserNotDefinedException ex) {
            throw new AxisFault("The user (" + username + ") is not defined.");
        }
        Event event = eventTrackingService.newEvent(eventName, resource, context, modify, NotificationService.NOTI_NONE);
        eventTrackingService.post(event, user);
    }
}
