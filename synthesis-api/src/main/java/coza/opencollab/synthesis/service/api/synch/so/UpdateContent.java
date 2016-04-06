package coza.opencollab.synthesis.service.api.synch.so;

import coza.opencollab.synthesis.service.api.ServiceCallStatus;
import coza.opencollab.synthesis.service.api.ServiceObject;
import java.util.Map;

/**
 * The status after a client update content.
 *
 * @author OpenCollab
 * @since 1.0.0
 * @version 1.0.0
 */
public class UpdateContent extends ServiceObject{
    /**
     * A content data object for return content to the client
     * after a user content update.
     */
    private Map responseContent;
    
    /**
     * Default constructor
     */
    public UpdateContent(){}
    
    /**
     * Constructor setting the status.
     *
     * @param statusCode a {@link coza.opencollab.synthesis.service.api.ServiceCallStatus} object.
     */
    public UpdateContent(ServiceCallStatus statusCode){
        super(statusCode);
    }
    
    /**
     * Copy constructor
     *
     * @param other a {@link coza.opencollab.synthesis.service.api.ServiceObject} object.
     */
    public UpdateContent(ServiceObject other){
        super(other);
    }

    /**
     * A content data object for return content to the client
     * after a user content update.
     *
     * @return a {@link java.util.Map} object.
     */
    public Map getResponseContent() {
        return responseContent;
    }

    /**
     * A content data object for return content to the client
     * after a user content update.
     *
     * @param responseContent a {@link java.util.Map} object.
     */
    public void setResponseContent(Map responseContent) {
        this.responseContent = responseContent;
    }
}
