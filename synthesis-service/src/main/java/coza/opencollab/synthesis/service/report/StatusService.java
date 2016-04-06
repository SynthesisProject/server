package coza.opencollab.synthesis.service.report;

import coza.opencollab.synthesis.service.report.so.Status;
import java.util.List;
import java.util.Map;

/**
 * This define a service that reports on statuses. This includes client and current code and content
 * statuses
 *
 * @author OpenCollab
 * @version 1.0.1
 */
public interface StatusService {
    /**
     * Get the current (latest) version for the tool.
     *
     * @param moduleId The module id.
     * @param toolName The tool name.
     * @return The version.
     */
    public String getCurrentContentVersion(String moduleId, String toolName);
    /**
     * Get all the current tool content versions. The client base content version
     * will also be in the list.
     *
     * @param moduleId The module id.
     * @return The versions in a map with the tool names as keys.
     */
    public Map<String, String> getCurrentContentVersions(String moduleId);
    /**
     * Return the last known client content version.
     *
     * @param username The client username.
     * @param moduleId The module id.
     * @param toolName The tool name.
     * @return the last known content version for the client tool.
     */
    public String getClientContentVersion(String username, String moduleId, String toolName);
    /**
     * Get all the client last known tool content versions. The client base content version
     * will also be in the list.
     *
     * @param username The client username
     * @param moduleId The module id.
     * @return The versions in a map with the tool names as keys.
     */
    public Map<String, String> getClientContentVersions(String username, String moduleId);
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
}
