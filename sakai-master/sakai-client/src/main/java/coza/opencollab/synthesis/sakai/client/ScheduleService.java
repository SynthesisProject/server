package coza.opencollab.synthesis.sakai.client;

import java.util.Date;

/**
 * Get all schedule events for a Site.
 *
 * @author OpenCollab
 * @version 1.0.0
 */
public interface ScheduleService {
    /**
     * Get all schedule events for a site given the siteId
     *
     * @param sessionId a {@link java.lang.String} object.
     * @param siteId a {@link java.lang.String} object.
     * @throws java.lang.Exception if any.
     * @param fromDate a {@link java.util.Date} object.
     * @return a {@link java.lang.String} object.
     */
    public String getScheduleForSite(String sessionId, String siteId, Date fromDate) throws Exception;
}
