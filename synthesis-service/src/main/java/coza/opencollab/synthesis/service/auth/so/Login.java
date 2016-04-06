package coza.opencollab.synthesis.service.auth.so;

import coza.opencollab.synthesis.service.api.ServiceCallStatus;
import coza.opencollab.synthesis.service.api.ServiceObject;
import java.io.Serializable;

/**
 * The service object for login.
 * This may be used by clients and other services
 *
 * @author OpenCollab
 * @since 1.0.0
 * @version 1.0.1
 */
public class Login extends ServiceObject implements Serializable{
    /**
     * The username.
     */
    private String username;
    /**
     * The session id of the login
     */
    private String sessionId;
    
    /**
     * Authentication token that can be used later
     * instead of using a password.
     */
    private String authToken;
    
    /**
     * Default Constructor.
     */
    public Login(){}
    
    /**
     * Constructor setting the Status Code.
     *
     * @param statusCode a {@link coza.opencollab.synthesis.service.api.ServiceCallStatus} object.
     */
    public Login(ServiceCallStatus statusCode){
        super(statusCode);
    }
    
    /**
     * Copy constructor.
     *
     * @param other a {@link coza.opencollab.synthesis.service.api.ServiceObject} object.
     */
    public Login(ServiceObject other){
        super(other);
    }

    /**
     * The username.
     *
     * @return String username
     */
    public String getUsername() {
        return username;
    }

    /**
     * The username.
     *
     * @param username a {@link java.lang.String} object.
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * The session id of the login
     *
     * @return a {@link java.lang.String} object.
     */
    public String getSessionId() {
        return sessionId;
    }

    /**
     * The session id of the login
     *
     * @param sessionId a {@link java.lang.String} object.
     */
    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

	/**
	 * Get the authentication token that can be used later
	 * instead of using a password.
	 *
	 * @return the authToken
	 */
	public String getAuthToken() {
		return authToken;
	}

	/**
	 * Set the authentication token that can be used later
	 * instead of using a password.
	 *
	 * @param authToken the authToken to set
	 */
	public void setAuthToken(String authToken) {
		this.authToken = authToken;
	}
    
}
