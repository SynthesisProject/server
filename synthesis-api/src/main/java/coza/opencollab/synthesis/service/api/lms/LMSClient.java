package coza.opencollab.synthesis.service.api.lms;

import coza.opencollab.synthesis.shared.Module;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * The api for lms interaction.
 *
 * @author OpenCollab
 * @since 1.0.0
 * @version 1.0.0
 */
public interface LMSClient {
    /**
     * Log the user in to the lms.
     *
     * @param username The username
     * @param password The password
     * @return The session id for the valid login
     */
    public String login(String username, String password);
    /**
     * Log a user out of the lms.
     *
     * @param username The username.
     * @return true if the user was logged out, meaning the session was still valid, false otherwise.
     */
    public boolean logout(String username);
    /**
     * Retrieve extra user details from the lms.
     *
     * @param username The username.
     * @return Any extra data of the user.
     */
    public Map<String, String> getUserDetails(String username);
    /**
     * Retrieves the tool content from the lms.
     *
     * @param moduleId The module id.
     * @param toolName The tool name.
     * @param fromDate The date that the data needs to be retrieved from.
     * @return The data or null if there is no new data.
     */
    public Object getToolContent(String moduleId, String toolName, Date fromDate);
    /**
     * Retrieves the user content from the lms.
     *
     * @param username The username.
     * @param moduleId The module id.
     * @param toolName The tool name.
     * @param fromDate The date that the data needs to be retrieved from.
     * @return The data or null if there is no new data.
     */
    public Object getUserContent(String username, String moduleId, String toolName, Date fromDate);
    /**
     * Updates the user content on the lms.
     *
     * @param username The username.
     * @param moduleId The module id.
     * @param toolName The tool name.
     * @param content The data to send to the lms.
     * @return Any response from the lms.
     * @param originalContent a {@link java.lang.String} object.
     */
    public Map<String, ?> updateUserContent(String username, String moduleId, String toolName, Map<String, ?> content, String originalContent);
    
    /**
     * Get a list of modules a user can access
     *
     * @param username a {@link java.lang.String} object.
     * @return List of modules
     */
    public List<Module> getSitesUserCanAccess(String username);
    
        
    /**
     * Get a list of modules a user can access required for moodle
     *
     * @param username a {@link java.lang.String} object.
     * @param password a {@link java.lang.String} object.
     * @return List of modules
     */
    public List<Module> getSitesUserCanAccess(String username, String password);
}
