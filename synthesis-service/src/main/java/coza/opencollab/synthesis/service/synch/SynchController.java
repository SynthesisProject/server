package coza.opencollab.synthesis.service.synch;

import static coza.opencollab.synthesis.util.JsonParser.*;
import coza.opencollab.synthesis.service.BaseController;
import coza.opencollab.synthesis.service.api.ServiceCallStatus;
import coza.opencollab.synthesis.service.api.ServiceObject;
import coza.opencollab.synthesis.service.auth.AuthenticationService;
import coza.opencollab.synthesis.service.synch.so.SynchStatus;
import coza.opencollab.synthesis.service.api.synch.so.SynchContent;
import coza.opencollab.synthesis.service.api.synch.so.UpdateContent;
import coza.opencollab.synthesis.service.synch.so.UpdateStatus;
import coza.opencollab.synthesis.service.util.impl.ZipUtil;

import java.io.IOException;
import java.util.Map;
import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *  The controller for content. This handle content checks and synchronization
 * with sakai. This class handle RestFull service calls using JSON.
 *
 * @author OpenCollab
 * @since 1.0.0
 * @version 1.0.1
 */
@Controller
@RequestMapping("/service-synch")
public class SynchController extends BaseController{
    
    /**
     * The logger.
     */
    private static final Logger log = Logger.getLogger(SynchController.class);
    
    /**
     * The client & tool synch implementation injected by Spring that will do all the work.
     */
    @Autowired
    private SynchService synchService;
    
    /**
     * The auth service
     */
    @Autowired
    private AuthenticationService authenticationService;
    
    
    /**
     * This method handles calls to retrieve tool content.
     *
     * @param body The body contain the password. {\"password\":\"123\"}
     * @param username The username
     * @param moduleId The module id
     * @param toolName The tool name
     * @param contentVersion The current tool content version
     * @return The new tool content
     */
    @RequestMapping(value = "/contentUpdate/{username}/{moduleId}/{toolName:.+}/{contentVersion:.+}", method = RequestMethod.POST)
    public @ResponseBody SynchContent getContent(@RequestBody String body, @PathVariable String username, @PathVariable String moduleId, @PathVariable String toolName, @PathVariable String contentVersion) {
        contentVersion = checkVersion(contentVersion);
        String password = getPassword(body);
        return synchService.getContent(username, password, moduleId, toolName, contentVersion);
    }
    
    /**
     * This method handles calls to retrieve tool content.
     *
     * @param body The body contain the password. {\"password\":\"123\"}
     * @param username The username
     * @param moduleId The module id
     * @param toolName The tool name
     * @param contentVersion The current tool content version
     * @return The new tool content
     */
    @RequestMapping(value = "/contentUpdateString/{username}/{moduleId}/{toolName:.+}/{contentVersion:.+}", method = RequestMethod.POST)
    public @ResponseBody ServiceObject getContentString(@RequestBody String body, @PathVariable String username, @PathVariable String moduleId, @PathVariable String toolName, @PathVariable String contentVersion) {
        contentVersion = checkVersion(contentVersion);
        String password = getPassword(body);
        SynchContent synchContent = synchService.getContent(username, password, moduleId, toolName, contentVersion);
        if(synchContent != null && !synchContent.isSuccessful()){
            return synchContent;
        }
        if (synchContent == null || synchContent.getContent() == null) {
            log.info("No new data for " + moduleId + ":" + toolName + ":" + contentVersion);
            return new ServiceObject(ServiceCallStatus.WARNING, "No new data for " + moduleId + ":" + toolName + ":" + contentVersion);
        }
        try {
            Map contentDetail = ZipUtil.getUnZippedBinary(synchContent.getContent());
            synchContent.setContent((byte[]) contentDetail.get("data"));
            synchContent.setContentString((String) contentDetail.get("text"));
            synchContent.setContentName((String) contentDetail.get("name"));
            synchContent.setMimeType("text/plain");
        }catch(IOException ex) {
            synchContent.setStatus(ServiceCallStatus.ERROR);
            synchContent.setMessage("Could not extract zipped content."); 
            synchContent.setContent(null);
        }        
        return synchContent;
    }
    
    /**
     * This method handles calls to check the synch status of the client, tools and content.
     *
     * @param body The body should contain json for the SynchStatus class.
     * Json: {\"deviceVersion\":\"20131010124513\",\"toolVersions\":{\"assign\":\"99151\",\"announce\":\"134548\",\"faq\":\"514984984\"}}
     * @param username The username
     * @param moduleId The module id
     * @return The updated SynchStatus containing the out of synch data
     */
    @RequestMapping(value = "/synchStatus/{username}/{moduleId}", method = RequestMethod.POST)
    public @ResponseBody SynchStatus getSynchStatusB(@RequestBody String body, @PathVariable String username, @PathVariable String moduleId) {
        SynchStatus synchStatus = parseJson(body, SynchStatus.class);
        synchStatus.setModuleId(moduleId);
        SynchStatus returnStatus = synchService.getSynchStatus(username, synchStatus);
        return returnStatus;
    }
    
    /**
     * This method handles calls to check the synch status of the client, tools and content.
     *
     * @param body The body should contain json for the SynchStatus class.
     * Json: {\"deviceVersion\":\"20131010124513\",\"toolVersions\":{\"assign\":\"99151\",\"announce\":\"134548\",\"faq\":\"514984984\"}}
     * @param username The username
     * @return The updated SynchStatus containing the out of synch data
     */
    @RequestMapping(value = "/synchStatus/{username}", method = RequestMethod.POST)
    public @ResponseBody SynchStatus getSynchStatus(@RequestBody String body, @PathVariable String username) {
        SynchStatus synchStatus = parseJson(body, SynchStatus.class);
        return synchService.getSynchStatus(username, synchStatus);
    }
    
    /**
     * Update the client's tool content version on the server
     *
     * @param username The username
     * @param moduleId The module id.
     * @param toolName The tool name
     * @param toolVersion The current tool version
     * @return a {@link coza.opencollab.synthesis.service.synch.so.UpdateStatus} object.
     */
    @RequestMapping(value = "/contentVersion/{username}/{moduleId}/{toolName:.+}/{toolVersion:.+}", method = RequestMethod.PUT)
    public @ResponseBody UpdateStatus updateContentVersion(@PathVariable String username, @PathVariable String moduleId, @PathVariable String toolName, @PathVariable String toolVersion){
        toolVersion = checkVersion(toolVersion);
        return synchService.updateContentVersion(username, moduleId, toolName, toolVersion);
    }
    
    /**
     * Update/upload the content for the client tool from the client to the server.
     *
     * @param username The username
     * @param moduleId The module id
     * @param toolName The tool name
     * @return The updated files for the current tool versions
     * @param body a {@link java.lang.String} object.
     */
    @RequestMapping(value = "/content/{username}/{moduleId}/{toolName:.+}", method = RequestMethod.PUT)
    public @ResponseBody UpdateContent updateContent(@RequestBody String body, @PathVariable String username, @PathVariable String moduleId, @PathVariable String toolName) {
        Map<String, ?> content = parseJsonToMap(body);
        String password = getPassword(content);
        return synchService.updateContent(username, password, moduleId, toolName, content, body);
    }
    

    /**
     * Get the password from a request. The password may be in the form of a password string, or an encrypted
     * auth token which will be decrypted by the authentication service
     * @param body Body of a request
     * @return The password (possibly decrypted from an auth token)
     */
    private String getPassword(String body){
    	return getPassword(parseJsonToMap(body));
    }
    
    /**
     * Get the password from a request map. The password may be in the form of a password string, or an encrypted
     * auth token which will be decrypted by the authentication service
     * @param content Content map of a request
     * @return The password (possibly decrypted from an auth token)
     */
    private String getPassword(Map<String, ?> content){
    	String authToken = (String)content.get("authToken");
    	if(authToken != null){
    		return authenticationService.decryptPassword(authToken);
    	}
    	else{
    		return (String) content.get("password");
    	}
    }
    /**
     * A helper method to fix versions from javascript
     */
    private String checkVersion(String version){
        if(version == null || version.length() == 0 || "undefined".equals(version)){
            return "0";
        }else{
            return version;
        }
    }
}
