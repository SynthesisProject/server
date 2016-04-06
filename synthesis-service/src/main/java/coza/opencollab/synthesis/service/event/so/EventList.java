package coza.opencollab.synthesis.service.event.so;

import java.util.List;

/**
 * The Events list service object
 *
 * This object is to handle the json object
 *
 * @author OpenCollab
 * @since 1.0.0
 * @version 1.0.1
 */
public class EventList {
    
    /**
     * List off all events sent from client
     */
    private List<Event> events;

    /**
     * <p>Getter for the field <code>events</code>.</p>
     *
     * @return the events
     */
    public List<Event> getEvents() {
        return events;
    }

    /**
     * <p>Setter for the field <code>events</code>.</p>
     *
     * @param events the events to set
     */
    public void setEvents(List<Event> events) {
        this.events = events;
    }
}
