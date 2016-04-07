package coza.opencollab.synthesis.moodle.client;

import java.util.Collection;
import java.util.Date;

import coza.opencollab.synthesis.moodle.client.dao.file.MoodleFile;

/**
 *
 * @author OpenCollab
 */
public interface ResourcesService {

    /**
     * Get all resource tool data for a module (Sakai site) given the sessionId,
     * siteId and the fromDate. If the fromDate is null all resource tool data
     * will be returned
     *
     * @param defaultFormat default format
     * @param sessionId session id
     * @param siteId site id
     * @param fromDate filter items changed after this date
     * @return resources string
     */
    public String getResourcesForSite(String defaultFormat, String sessionId, int siteId, Date fromDate);

    /**
     * Retrieves MoodleFiles from Moodle
     *
     * @param token Authentication token
     * @param component component
     * @param filearea file area
     * @param instanceId instance id
     * @param itemId item id
     * @return resources string
     */
    public Collection<MoodleFile> retrieveMoodleFiles(String token, String component, String filearea, int instanceId, int itemId);

    public abstract byte[] downloadResource(String urlString, String sessionId);

    /**
     * Given the raw URL for the resource, add the session information in the
     * required format
     *
     * @param urlString base URL String
     * @param sessionId the session id
     * @return String resource URL with session id
     */
    public String getDownloadResourceUrl(String urlString, String sessionId);
}
