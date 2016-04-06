package coza.opencollab.synthesis.service.event.so;

import java.util.Date;

/**
 * The Event service object
 *
 * @author OpenCollab
 * @since 1.0.0
 * @version 1.0.1
 */
public class Event {
    
    /**
     * The event key code
     */
    private String eventCode;
    
    /**
     * The context of the event
     */
    private String context;
    
    /**
     * The reference for the event
     */
    private String reference;
    
    /**
     * The time the event occurred
     */
    private Date timeStamp;

    /**
     * <p>Getter for the field <code>eventCode</code>.</p>
     *
     * @return the eventCode
     */
    public String getEventCode() {
        return eventCode;
    }

    /**
     * <p>Setter for the field <code>eventCode</code>.</p>
     *
     * @param eventCode the eventCode to set
     */
    public void setEventCode(String eventCode) {
        this.eventCode = eventCode;
    }

    /**
     * <p>Getter for the field <code>context</code>.</p>
     *
     * @return the context
     */
    public String getContext() {
        return context;
    }

    /**
     * <p>Setter for the field <code>context</code>.</p>
     *
     * @param context the context to set
     */
    public void setContext(String context) {
        this.context = context;
    }

    /**
     * <p>Getter for the field <code>reference</code>.</p>
     *
     * @return the reference
     */
    public String getReference() {
        return reference;
    }

    /**
     * <p>Setter for the field <code>reference</code>.</p>
     *
     * @param reference the reference to set
     */
    public void setReference(String reference) {
        this.reference = reference;
    }

    /**
     * <p>Getter for the field <code>timeStamp</code>.</p>
     *
     * @return the timeStamp
     */
    public Date getTimeStamp() {
        return timeStamp;
    }

    /**
     * <p>Setter for the field <code>timeStamp</code>.</p>
     *
     * @param timeStamp the timeStamp to set
     */
    public void setTimeStamp(Date timeStamp) {
        this.timeStamp = timeStamp;
    }
    
}
