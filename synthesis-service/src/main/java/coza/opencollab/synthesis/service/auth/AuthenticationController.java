package coza.opencollab.synthesis.service.auth;

import static coza.opencollab.synthesis.util.JsonParser.*;
import coza.opencollab.synthesis.service.BaseController;
import coza.opencollab.synthesis.service.api.dao.Dao;
import coza.opencollab.synthesis.service.api.dbo.ManagedModule;
import coza.opencollab.synthesis.service.auth.so.AllowedSites;
import coza.opencollab.synthesis.service.auth.so.Login;
import coza.opencollab.synthesis.service.api.lms.LMSClient;
import coza.opencollab.synthesis.shared.Module;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * The controller for authentication. This handle logins (to sakai) and
 * registrations. This class handle RestFull service calls using JSON.
 *
 * @author OpenCollab
 * @since 1.0.0
 * @version 1.0.1
 */
@Controller
@RequestMapping("/service-auth")
public class AuthenticationController extends BaseController{

    /**
     * The implementation is injected by Spring
     */
    @Autowired
    private AuthenticationService authenticationService;
    
    /**
     * The implementation is injected by Spring
     */
    @Autowired
    private LMSClient lmsClient;
    /**
     * The dao
     */
    @Autowired
    private Dao dao;

    /**
     * Login the given user to sakai. Please is loginPost, better since
     * the password is not passed in the url.
     *
     * @param username The username
     * @param password The password
     * @return The Login object given a status and a sessionId of successful.
     * @deprecated This method must not be used and will be removed
     */
    @RequestMapping(value = "/login/{username}/{password:.+}", method = RequestMethod.GET)
    public @ResponseBody Login loginGet(@PathVariable String username, @PathVariable String password) {
        Login login = authenticationService.login(username, password);
        addDeprecateMessage("Authentication.loginGet", login);
        return login;
    }

    /**
     * Login the given user. The password is passed in the body under a
     * 'password' value.
     *
     * @param body The content of the call
     * @param username The username
     * @return The Login object given a status and a sessionId of successful.
     */
    @RequestMapping(value = "/login/{username}", method = RequestMethod.POST)
    public @ResponseBody Login loginPost(@RequestBody String body, @PathVariable String username) {
        String password = getPassword(body);
        return authenticationService.login(username, password);
    }
    
    /**
     * Returns the List of sites that the user is registered on
     *
     * @param username The username
     * @return The List of allowed sites
     */
    @RequestMapping(value = "/allowedSites/{username}", method = RequestMethod.GET)
    public @ResponseBody AllowedSites allowedSites(@PathVariable String username) {
        AllowedSites allowedSites = filterAllowedSites(lmsClient.getSitesUserCanAccess(username));
        return allowedSites;
    }
    
    /**
     * Returns the List of sites that the user is registered on
     *
     * @param username The username
     * @return The List of allowed sites
     * @param body a {@link java.lang.String} object.
     */
    @RequestMapping(value = "/allowedSitesPost/{username}", method = RequestMethod.POST)
    public @ResponseBody AllowedSites allowedSitesPost(@RequestBody String body, @PathVariable String username) {
        String password = getPassword(body);
        AllowedSites allowedSites = filterAllowedSites(lmsClient.getSitesUserCanAccess(username,password));
        return allowedSites;
    }
    
    /**
     * Filter LMS allowed sites against managed modules
     * 
     * @param modules
     * @return 
     */
    private AllowedSites filterAllowedSites(List<Module> modules) {
        AllowedSites allowedSites = new AllowedSites();
        List<ManagedModule> managedModules = dao.getManagedModules();
        for (Module module : modules) {
            if (moduleIsManaged(managedModules, module)) {
                allowedSites.addModule(module);
            }
        }
        return allowedSites;
    }
    
    /**
     * Check if an allowed site is managed
     * 
     * @param managedModules
     * @param module
     * @return 
     */
    private boolean moduleIsManaged(List<ManagedModule> managedModules, Module module) {
        for (ManagedModule managedModule : managedModules) {
            if (managedModule.getModuleId().equals(module.getId())) {
                return true;
            }
        }
        return false;
    }
    
    
    /**
      * Get the password from a request. The password may be in the form of a password string, or an encrypted
     * auth token which will be decrypted by the authentication service
     * @param content Content map of a request
     * @return The password (possibly decrypted from an auth token)
     */
    private String getPassword(String body){
    	String authToken = parseJson(body, "authToken");
    	if(authToken != null){
    		return authenticationService.decryptPassword(authToken);
    	}
    	else{
    		return (String) parseJson(body, "password");
    	}
    }
}
