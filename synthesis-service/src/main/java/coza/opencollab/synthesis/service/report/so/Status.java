package coza.opencollab.synthesis.service.report.so;

import coza.opencollab.synthesis.service.api.ServiceCallStatus;
import coza.opencollab.synthesis.service.api.ServiceObject;
import java.io.Serializable;

/**
 * This class represent the status of a client tool, the base code or the tools, and its content.
 *
 * @author OpenCollab
 * @version 1.0.1
 * @since 1.0.0
 */
public class Status extends ServiceObject implements Serializable{

    /** The client content version. */
    private String clientContentVersion;
    
    /** The current content version. */
    private String currentContentVersion;
    /**
     * The size of the synch package to update the content.
     */
    private long contentSynchSize;
    
    /**
     * Default Constructor.
     */
    public Status(){}
    
    /**
     * Constructor setting the status.
     *
     * @param statusCode a {@link coza.opencollab.synthesis.service.api.ServiceCallStatus} object.
     */
    public Status(ServiceCallStatus statusCode){
        super(statusCode);
    }
    
    /**
     * Copy constructor.
     *
     * @param other a {@link coza.opencollab.synthesis.service.api.ServiceObject} object.
     */
    public Status(ServiceObject other){
        super(other);
    }

    /**
     * Set all constructor.
     *
     * @param clientContentVersion a {@link java.lang.String} object.
     * @param currentContentVersion a {@link java.lang.String} object.
     * @param contentSynchSize a int.
     */
    public Status(String clientContentVersion, String currentContentVersion, int contentSynchSize){
        this.clientContentVersion = clientContentVersion;
        this.currentContentVersion = currentContentVersion;
        this.contentSynchSize = contentSynchSize;
    }

    /**
     * Gets the client content version.
     *
     * @return the client content version
     */
    public String getClientContentVersion() {
        return clientContentVersion;
    }

    /**
     * Sets the client content version.
     *
     * @param clientContentVersion the new client content version
     */
    public void setClientContentVersion(String clientContentVersion) {
        this.clientContentVersion = clientContentVersion;
    }

    /**
     * Gets the current content version.
     *
     * @return the current content version
     */
    public String getCurrentContentVersion() {
        return currentContentVersion;
    }

    /**
     * Sets the current content version.
     *
     * @param currentContentVersion the new current content version
     */
    public void setCurrentContentVersion(String currentContentVersion) {
        this.currentContentVersion = currentContentVersion;
    }

    /**
     * Gets the size of the synch package to update the content.
     *
     * @return the size of the synch package to update the content
     */
    public long getContentSynchSize() {
        return contentSynchSize;
    }

    /**
     * Sets the size of the synch package to update the content.
     *
     * @param contentSynchSize the new size of the synch package to update the content
     */
    public void setContentSynchSize(long contentSynchSize) {
        this.contentSynchSize = contentSynchSize;
    }
}
