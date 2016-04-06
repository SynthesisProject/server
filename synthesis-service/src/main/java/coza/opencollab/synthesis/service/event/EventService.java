package coza.opencollab.synthesis.service.event;

import java.util.Date;

/**
 * This api handles all events.
 *
 * @author OpenCollab
 * @since 1.0.0
 * @version 1.0.1
 */
public interface EventService {
    /**
     * Add a event with the current date and session id to be set later.
     *
     * @param event The event key.
     * @param ref A reference to the event
     */
    public void addEvent(String event, String ref);
    /**
     * Add a event with the current date and session id to be set later.
     *
     * @param event The event key.
     * @param ref A reference to the event
     * @param context The context, ex the module
     */
    public void addEvent(String event, String ref, String context);
    /**
     * Add a event with the current date and session id to be set later.
     *
     * @param event The event key.
     * @param ref A reference to the event
     * @param context The context, ex the module
     * @param modified Whether the resource was modified, default false.
     */
    public void addEvent(String event, String ref, String context, boolean modified);
    /**
     * Add a event with the current date.
     *
     * @param sessionId The session Id
     * @param event The event key.
     * @param ref A reference to the event
     */
    public void addEventForSession(String sessionId, String event, String ref);
    /**
     * Add a event with the current date.
     *
     * @param sessionId The session Id
     * @param event The event key.
     * @param ref A reference to the event
     * @param context The context, ex the module
     */
    public void addEventForSession(String sessionId, String event, String ref, String context);
    /**
     * Add a event with the current date
     *
     * @param sessionId The session Id
     * @param event The event key.
     * @param ref A reference to the event
     * @param context The context, ex the module
     * @param modified Whether the resource was modified, default false.
     */
    public void addEventForSession(String sessionId, String event, String ref, String context, boolean modified);
    /**
     * Add a event.
     *
     * @param sessionId The session Id
     * @param date The current date and time
     * @param event The event key.
     * @param ref A reference to the event
     * @param context The context, ex the module
     */
    public void addEventForSession(String sessionId, Date date, String event, String ref, String context);
    /**
     * Add a event.
     *
     * @param sessionId The session Id
     * @param date The current date and time
     * @param event The event key.
     * @param ref A reference to the event
     * @param context The context, ex the module
     * @param modified Whether the resource was modified, default false.
     */
    public void addEventForSession(String sessionId, Date date, String event, String ref, String context, boolean modified);
    /**
     * Add a event with the current date and session id to be set later.
     *
     * @param username The user to add the event for
     * @param event The event key.
     * @param ref A reference to the event
     */
    public void addEventForUser(String username, String event, String ref);
    /**
     * Add a event with the current date and session id to be set later.
     *
     * @param username The user to add the event for
     * @param event The event key.
     * @param ref A reference to the event
     * @param context The context, ex the module
     */
    public void addEventForUser(String username, String event, String ref, String context);
    /**
     * Add a event with the current date and session id to be set later.
     *
     * @param username The user to add the event for
     * @param event The event key.
     * @param ref A reference to the event
     * @param context The context, ex the module
     * @param modified Whether the resource was modified, default false.
     */
    public void addEventForUser(String username, String event, String ref, String context, boolean modified);
    /**
     * Add a event with the current date.
     *
     * @param username The user to add the event for
     * @param sessionId The session Id
     * @param event The event key.
     * @param ref A reference to the event
     * @param context The context, ex the module
     */
    public void addEventForUserSession(String username, String sessionId, String event, String ref, String context);
    /**
     * Add a event with the current date
     *
     * @param username The user to add the event for
     * @param sessionId The session Id
     * @param event The event key.
     * @param ref A reference to the event
     * @param context The context, ex the module
     * @param modified Whether the resource was modified, default false.
     */
    public void addEventForUserSession(String username, String sessionId, String event, String ref, String context, boolean modified);
    /**
     * Add a event.
     *
     * @param username The user to add the event for
     * @param sessionId The session Id
     * @param date The current date and time
     * @param event The event key.
     * @param ref A reference to the event
     * @param context The context, ex the module
     */
    public void addEventForUserSession(String username, String sessionId, Date date, String event, String ref, String context);
    /**
     * Add a event.
     *
     * @param username The user to add the event for
     * @param sessionId The session Id
     * @param date The current date and time
     * @param event The event key.
     * @param ref A reference to the event
     * @param context The context, ex the module
     * @param modified Whether the resource was modified, default false.
     */
    public void addEventForUserSession(String username, String sessionId, Date date, String event, String ref, String context, boolean modified);
}
