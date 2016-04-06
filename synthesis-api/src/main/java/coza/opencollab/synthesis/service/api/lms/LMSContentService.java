package coza.opencollab.synthesis.service.api.lms;

/**
 * The content service for the LMS.
 *
 * @author OpenCollab
 * @since 1.0.0
 * @version 1.0.0
 */
public interface LMSContentService {
    /**
     * Retrieve the resource from the lms.
     *
     * @param sessionId The session id.
     * @param resourceId The resource id.
     * @return The resource bytes.
     */
    public byte[] getResourceData(String sessionId, String resourceId);
    
    /**
     * Retrieve the resource download URL
     *
     * @param sessionId The session id.
     * @param resourceId The resource id.
     * @return The resource bytes.
     */
    public String getResourceURL(String sessionId, String resourceId);
}
