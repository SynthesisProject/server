package coza.opencollab.synthesis.service;

import coza.opencollab.synthesis.service.api.Defaults;
import coza.opencollab.synthesis.service.api.ServiceObject;
import coza.opencollab.synthesis.service.auth.AuthenticationService;
import coza.opencollab.synthesis.service.auth.so.Login;
import coza.opencollab.synthesis.service.api.ServiceCallStatus;
import static coza.opencollab.synthesis.service.api.ErrorCodes.*;
import coza.opencollab.synthesis.SynthesisException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
	
/**
 * The base of all controllers used. Just some common code.
 *
 * @author OpenCollab
 * @since 1.0.0
 * @version 1.0.1
 */
public class BaseController implements Defaults {

    /**
     * The logger.
     */
    private static final Logger log = Logger.getLogger(BaseController.class);

    @Autowired
    private AuthenticationService authenticationService;
    
    /**
     * Adds a message to the message variable of the service object to inform the caller that the method used is
     * deprecated.
     *
     * @param what What is deprecated. Ex. LoginService.login
     * @param serviceObject The service object to update.
     */
    protected void addDeprecateMessage(String what, ServiceObject serviceObject) {
        serviceObject.setErrorCode(DEPRECATED);
        serviceObject.addMessage(what + " is deprecated and must not be used, it will be removed.");
    }
    
    /**
     * Tests if the authentication details are correct, it also logs out the user again
     * @param username Username
     * @param authToken Authtoken
     * @return A login object
     */
    protected Login testLoginWithToken(String username, String authToken){
    	Login login = authenticationService.loginWithToken(username, authToken);
        if (login.isSuccessful()) {
        	authenticationService.logout(login);
        }        
        return login;
    }

    /**
     * The exception handler to be called if there is a exception in one of the service methods. This is managed by
     * spring and does not need to be called directly. This is a general exception handler
     *
     * @param e a {@link java.lang.Exception} object.
     * @return a {@link coza.opencollab.synthesis.service.api.ServiceObject} object.
     */
    @ExceptionHandler
    public @ResponseBody ServiceObject handleException(Exception e) {
        log.error("Unsuccessful service call!", e);
        ServiceObject so = new ServiceObject(ServiceCallStatus.EXCEPTION);
        if (e.getLocalizedMessage() == null) {
            so.setMessage("Server Exception occured");
        }else{
            so.setMessage(e.getLocalizedMessage());
        }
        if(SynthesisException.class.isInstance(e)){
            so.setErrorCode(SynthesisException.class.cast(e).getErrorCode());
        }else{
            so.setErrorCode(GENERAL);
        }
        return so;
    }
}

