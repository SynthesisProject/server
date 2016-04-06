package coza.opencollab.synthesis.service.report;

import coza.opencollab.synthesis.service.BaseController;
import coza.opencollab.synthesis.service.report.so.Status;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * The controller for retrieving current data, like code or content versions.
 * This class handle RestFull service calls using JSON.
 *
 * @author OpenCollab
 * @since 1.0.0
 * @version 1.0.1
 */
@Controller
@RequestMapping("/service-status")
public class StatusController extends BaseController{    
    /**
     * The status service injected by Spring.
     */
    @Autowired
    private StatusService statusService;
    
    /**
     * This method retrieves all the content versions of all the tools.
     *
     * @param moduleId The module id.
     * @return The current content versions.
     */
    @RequestMapping(value = "/contentVersions/{moduleId}", method = RequestMethod.GET)
    public @ResponseBody Map<String, String> contentVersions(@PathVariable String moduleId) {
        return statusService.getCurrentContentVersions(moduleId);
    }
    
    /**
     * This method handles calls to get the client tool content version.
     *
     * @param username The user name.
     * @param moduleId The module id.
     * @param toolName The tool name.
     * @return The current tool content version.
     */
    @RequestMapping(value = "/toolContentVersion/{username}/{moduleId}/{toolName:.+}", method = RequestMethod.GET)
    public @ResponseBody String toolContentVersion(@PathVariable String username, @PathVariable String moduleId, @PathVariable String toolName) {
        return statusService.getClientContentVersion(username, moduleId, toolName);
    }
    
    /**
     * This method retrieves all the client content versions of all the tools.
     *
     * @param username The user name.
     * @param moduleId The module id.
     * @return The current content versions.
     */
    @RequestMapping(value = "/contentVersions/{username}/{moduleId}", method = RequestMethod.GET)
    public @ResponseBody Map<String, String> contentVersions(@PathVariable String username, @PathVariable String moduleId) {
        return statusService.getClientContentVersions(username, moduleId);
    }
    
    /**
     * Retrieve the status of the client's tool base and the current client base. Code and content.
     *
     * @param username The user name.
     * @param moduleId The module id.
     * @param toolName The tool name.
     * @return The Status.
     */
    @RequestMapping(value = "/status/{username}/{moduleId}/{toolName:.+}", method = RequestMethod.GET)
    public @ResponseBody Status getStatus(@PathVariable String username, @PathVariable String moduleId, @PathVariable String toolName){
        return statusService.getStatus(username, moduleId, toolName);
    }
}
