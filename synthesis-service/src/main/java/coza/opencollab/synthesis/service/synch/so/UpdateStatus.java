package coza.opencollab.synthesis.service.synch.so;

import coza.opencollab.synthesis.service.api.ServiceCallStatus;
import coza.opencollab.synthesis.service.api.ServiceObject;

/**
 * The status after a client update.
 *
 * @author OpenCollab
 * @since 1.0.0
 * @version 1.0.1
 */
public class UpdateStatus extends ServiceObject{
    
    /**
     * Default constructor
     */
    public UpdateStatus(){}
    
    /**
     * Constructor setting the status.
     *
     * @param statusCode a {@link coza.opencollab.synthesis.service.api.ServiceCallStatus} object.
     */
    public UpdateStatus(ServiceCallStatus statusCode){
        super(statusCode);
    }
    
    /**
     * Copy constructor
     *
     * @param other a {@link coza.opencollab.synthesis.service.api.ServiceObject} object.
     */
    public UpdateStatus(ServiceObject other){
        super(other);
    }
}
