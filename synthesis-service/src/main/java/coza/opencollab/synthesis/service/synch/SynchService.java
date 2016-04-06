package coza.opencollab.synthesis.service.synch;

import coza.opencollab.synthesis.service.report.so.Status;
import coza.opencollab.synthesis.service.api.synch.so.SynchContent;
import coza.opencollab.synthesis.service.synch.so.SynchStatus;
import coza.opencollab.synthesis.service.api.synch.so.UpdateContent;
import coza.opencollab.synthesis.service.synch.so.UpdateStatus;
import java.util.Map;

/**
 * The api for code and content synchronization handling. Implementing classes will handle all
 * code and content synch checking and synchronization for clients and tools.
 *
 * @author OpenCollab
 * @since 1.0.0
 * @version 1.0.1
 */
public interface SynchService {

    /**
     * Retrieves the tool content. All new content from the current tool content version
     * will be retrieved and the tool content version will be updated.
     *
     * @param username The username.
     * @param password The user password.
     * @param moduleId The module id.
     * @param toolName The tool name.
     * @param contentVersion The tool current content version.
     * @return The new tool content.
     */
    public SynchContent getContent(String username, String password, String moduleId, String toolName, String contentVersion);
    /**
     * Get the status of a tool. This includes code and content for the users version and
     * the current versions.
     *
     * @param username The client username
     * @param moduleId The module id.
     * @param toolName The tool name
     * @return The status of the tool
     */
    public Status getStatus(String username, String moduleId, String toolName);
    /**
     * Checks if the client, any of the tools or any content for this user and module is out of synch
     *
     * @param username The username
     * @param synchStatus The input. This must at least contain the username, moduleId
     * @return The synchStatus containing the statuses for the client and all tools.
     */
    public SynchStatus getSynchStatus(String username, SynchStatus synchStatus);
    /**
     * Update the client's tool content version on the server.
     *
     * @param username The username.
     * @param moduleId The module id.
     * @param toolName The tool name.
     * @param contentVersion The current tool content version.
     * @return a {@link coza.opencollab.synthesis.service.synch.so.UpdateStatus} object.
     */
    public UpdateStatus updateContentVersion(String username, String moduleId, String toolName, String contentVersion);
    /**
     * Update/upload the content for the client tool from the client to the server.
     *
     * @param username The username.
     * @param password The user password.
     * @param moduleId The module id.
     * @param toolName The tool name.
     * @param content The content to update.
     * @return The updated files for the current tool versions.
     * @param originalContent a {@link java.lang.String} object.
     */
    public UpdateContent updateContent(String username, String password, String moduleId, String toolName, Map<String, ?> content, String originalContent);
}
