package coza.opencollab.synthesis.service.api.lms;

import java.util.Date;
import java.util.Map;

/**
 * This is the tool content service that can retrieve the content for the tool
 * from the lms.
 *
 * @author OpenCollab
 * @since 1.0.0
 * @version 1.0.0
 */
public interface ToolContentService<C, U> {
    /**
     * Retrieve the content for this tool for the module id and from date.
     *
     * @param sessionId The admin user session id.
     * @param moduleId The module id.
     * @param fromDate The from date. Only get content from that date forward. Can be null.
     * @return The content.
     */
    public C getContent(String sessionId, String moduleId, Date fromDate);
    /**
     * Retrieve the content for this tool for the user, module id and from date.
     *
     * @param sessionId The admin user session id.
     * @param username The user who's content we need to get.
     * @param moduleId The module id.
     * @param fromDate The from date. Only get data from that date forward.
     * @return The content.
     */
    public U getUserContent(String sessionId, String username, String moduleId, Date fromDate);
    /**
     * Updates the user content on the LMS.
     *
     * @param sessionId The admin user session id.
     * @param username The user who's content we need to update.
     * @param moduleId The module id.
     * @param content The content to update.
     * @param originalContent a {@link java.lang.String} object.
     * @return a {@link java.util.Map} object.
     */
    public Map updateUserContent(String sessionId, String username, String moduleId, Map content, String originalContent);
}
