package coza.opencollab.synthesis.sakai.client;

import java.util.Date;

/**
 * <p>AnnouncementService interface.</p>
 *
 * @author OpenCollab
 * @version 1.0.0
 */
public interface AnnouncementService {
    /**
     * Get the announcements for a site from Moodle and return JSON
     *
     * @param sessionId a {@link java.lang.String} object.
     * @param siteId a {@link java.lang.String} object.
     * @param fromDate a {@link java.util.Date} object.
     * @throws java.lang.Exception if any.
     * @return a {@link java.lang.String} object.
     */
    public String getAnnouncementsForSite(String sessionId, String siteId, Date fromDate) throws Exception;
}
