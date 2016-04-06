package coza.opencollab.synthesis.service.auth.so;

import coza.opencollab.synthesis.service.api.ServiceCallStatus;
import coza.opencollab.synthesis.service.api.ServiceObject;
import java.io.Serializable;
import java.util.Map;

/**
 * The service object for client registrations
 *
 * @author OpenCollab
 * @since 1.0.0
 * @version 1.0.1
 */
public class Register extends ServiceObject implements Serializable{
    /**
     * The registered module id
     */
    private String moduleId;
    /**
     * Extra user details.
     */
    private Map<String, String> userDetails;
    
    /**
     * Default constructor
     */
    public Register(){}
    
    /**
     * Constructor setting the status.
     *
     * @param statusCode a {@link coza.opencollab.synthesis.service.api.ServiceCallStatus} object.
     */
    public Register(ServiceCallStatus statusCode){
        super(statusCode);
    }
    
    /**
     * Copy constructor
     *
     * @param other a {@link coza.opencollab.synthesis.service.api.ServiceObject} object.
     */
    public Register(ServiceObject other){
        super(other);
    }

    /**
     * The registered module id
     *
     * @return module id
     */
    public String getModuleId() {
        return moduleId;
    }

    /**
     * The registered module id
     *
     * @param moduleId a {@link java.lang.String} object.
     */
    public void setModuleId(String moduleId) {
        this.moduleId = moduleId;
    }

    /**
     * Extra user details.
     *
     * @return user details
     */
    public Map<String, String> getUserDetails() {
        return userDetails;
    }

    /**
     * Extra user details.
     *
     * @param userDetails map
     */
    public void setUserDetails(Map<String, String> userDetails) {
        this.userDetails = userDetails;
    }
}
