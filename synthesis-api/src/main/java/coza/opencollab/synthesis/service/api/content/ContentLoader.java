package coza.opencollab.synthesis.service.api.content;

import coza.opencollab.synthesis.service.api.synch.so.SynchContent;
import java.util.Map;

/**
 * The implementations must load module content in the Synthesis
 * service.
 * <p>
 * This may be a ongoing task, so content will have to be loaded periodically.
 *
 * @author OpenCollab
 * @since 1.0.0
 * @version 1.0.0
 */
public interface ContentLoader {
    /**
     * Load the module to the content management.
     *
     * @param moduleId The module id.
     */
    public void load(String moduleId);
    /**
     * Whether to update non existing version to the current version
     * without throwing an exception.
     *
     * @return a boolean.
     */
    public boolean silentlyUpdateNonExistingVersions();
    /**
     * The default non existing version.
     *
     * @return a {@link java.lang.String} object.
     */
    public String getNonExistingVersion();
    /**
     * Retrieves the synch content for the tool. This is content that is valid for
     * all users.
     *
     * @param moduleId The module id.
     * @param toolName The tool name.
     * @param fromContentVersion The version before the synch package start.
     * @param toContentVersion The end version.
     * @return The synch package.
     */
    public SynchContent getSynchContent(String moduleId, String toolName, String fromContentVersion, String toContentVersion);
    /**
     * Retrieves the synch content for the user on this tool. This is content that is valid for
     * a specific user.
     *
     * @param username The username.
     * @param moduleId The module id.
     * @param toolName The tool name.
     * @param fromContentVersion The version before the synch package start.
     * @return The synch package.
     */
    public SynchContent getUserContent(String username, String moduleId, String toolName, String fromContentVersion);
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
     * Merge two contents together. These must be valid contents.
     *
     * @param contentA a {@link coza.opencollab.synthesis.service.api.synch.so.SynchContent} object.
     * @param contentB a {@link coza.opencollab.synthesis.service.api.synch.so.SynchContent} object.
     * @return The merged content.
     */
    public SynchContent merge(SynchContent contentA, SynchContent contentB);
}
