package coza.opencollab.synthesis.service.api;

import java.io.Serializable;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import coza.opencollab.synthesis.service.api.ServiceCallStatus;
import coza.opencollab.synthesis.service.api.ServiceObject;

/**
 * The base service object.
 *
 * @author OpenCollab
 * @since 1.0.0
 * @version 1.0.0
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ServiceObject implements Serializable{
    
    /**
     * The status of the call.
     */
    private ServiceCallStatus status = ServiceCallStatus.SUCCESS;
    /**
     * The message for the call.
     * Note that this could be null if status is successful
     */
    private String message = "";
    /**
     * The errorCode, if a error occured.
     */
    private int errorCode = 0;
    /**
     * A instruction on what to do when a error occured.
     */
    private String instruction = "";
    
    /**
     * Default Constructor
     */
    public ServiceObject(){}
    
    /**
     * Constructor setting the Status Code.
     *
     * @param statusCode a {@link coza.opencollab.synthesis.service.api.ServiceCallStatus} object.
     */
    public ServiceObject(ServiceCallStatus statusCode){
        this.status = statusCode;
    }
    
    /**
     * Constructor setting the Status Code and message.
     *
     * @param statusCode a {@link coza.opencollab.synthesis.service.api.ServiceCallStatus} object.
     * @param message a {@link java.lang.String} object.
     */
    public ServiceObject(ServiceCallStatus statusCode, String message){
        this.status = statusCode;
        this.message = message;
    }    
    
    /**
     * Copy constructor
     *
     * @param other a {@link coza.opencollab.synthesis.service.api.ServiceObject} object.
     */
    public ServiceObject(ServiceObject other){
        this.status = other.status;
        this.message = other.message;
        this.instruction = other.instruction;
        this.errorCode = other.errorCode;
    }

    /**
     * The status of the call.
     *
     * @return a {@link coza.opencollab.synthesis.service.api.ServiceCallStatus} object.
     */
    public ServiceCallStatus getStatus() {
        return status;
    }

    /**
     * The status of the call.
     *
     * @param status a {@link coza.opencollab.synthesis.service.api.ServiceCallStatus} object.
     */
    public void setStatus(ServiceCallStatus status) {
        this.status = status;
    }
    
    /**
     * Whether the status of the call was successful.
     *
     * @return a boolean.
     */
    @JsonIgnore
    public boolean isSuccessful(){
        return ServiceCallStatus.SUCCESS.equals(status);
    }

    /**
     * The message for the call.
     * Note that this could be null if status is successful
     *
     * @return a {@link java.lang.String} object.
     */
    public String getMessage() {
        return message;
    }

    /**
     * The message for the call.
     * Note that this could be null if status is successful
     *
     * @param message a {@link java.lang.String} object.
     */
    public void setMessage(String message) {
        this.message = message;
    }
    
    /**
     * Add a message to the existing message.
     * <p>
     * If there is no existing message this message is set as the
     * object message. If there is a existing message this message
     * is added to the end of the existing message.
     *
     * @param message New message to add.
     */
    @JsonIgnore
    public void addMessage(String message){
        if(this.message != null){
            setMessage(getMessage()
                    + "\n"
                    + message);
        }else{
            setMessage(message);
        }
    }

    /**
     * The errorCode, if a error occured.
     *
     * @return a int.
     */
    public int getErrorCode() {
        return errorCode;
    }

    /**
     * The errorCode, if a error occured.
     *
     * @param errorCode a int.
     */
    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    /**
     * A instruction on what to do when a error occured.
     *
     * @return a {@link java.lang.String} object.
     */
    public String getInstruction() {
        return instruction;
    }

    /**
     * A instruction on what to do when a error occured.
     *
     * @param instruction a {@link java.lang.String} object.
     */
    public void setInstruction(String instruction) {
        this.instruction = instruction;
    }
}
