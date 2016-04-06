package coza.opencollab.synthesis.service.api.content;

import coza.opencollab.synthesis.service.api.synch.so.SynchContent;
import coza.opencollab.synthesis.service.api.synch.so.UpdateContent;
import java.util.Map;

/**
 * The service to manage client content.
 *
 * @author OpenCollab
 * @since 1.0.0
 * @version 1.0.0
 */
public interface ContentService {
    /**
     * Load the module to the content management.
     *
     * @param moduleId The module id.
     */
    public void loadContent(String moduleId);
    /**
     * This will create a synch package for the user with the updates to the tool
     * from the users version to the current version.
     *
     * @param username The username.
     * @param moduleId The module id.
     * @param toolName The tool name.
     * @param fromContentVersion The client's current tool content version.
     * @return The synch data with the tool update.
     */
    public SynchContent getContentSynch(String username, String moduleId, String toolName, String fromContentVersion);
    /**
     * Get the size of the update for the tool from the given version to the current.
     *
     * @param username The username.
     * @param moduleId The module id.
     * @param toolName The tool name.
     * @param fromContentVersion The from version, excluding.
     * @return The approximation size in bytes.
     */
    public long getContentSynchSize(String username, String moduleId, String toolName, String fromContentVersion);
    /**
     * Updated the content for the tool for the user.
     *
     * @param username The username.
     * @param moduleId The module id.
     * @param toolName The tool name.
     * @param content The user's content
     * @return The status of the update. Could also return extra data.
     * @param originalContent a {@link java.lang.String} object.
     */
    public UpdateContent updateUserContent(String username, String moduleId, String toolName, Map<String, ?> content, String originalContent );
 
}
