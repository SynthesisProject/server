package coza.opencollab.synthesis.sakai.client;

import java.util.Date;

/**
 * Service to retrieve resource tool data from the Sakai LMS
 *
 * @author OpenCollab
 * @version 1.0.0
 */
public interface ResourcesService {

    /**
     * Get all resource tool data for a module (Sakai site) given the sessionId
     * , siteId and the fromDate. If the fromDate is null all resource tool data
     * will be returned
     *
     * @param sessionId a {@link java.lang.String} object.
     * @param siteId a {@link java.lang.String} object.
     * @param fromDate a {@link java.util.Date} object.
     * @throws java.lang.Exception if any.
     * @return a {@link java.lang.String} object.
     */
    public String getResourcesForSite(String sessionId, String siteId, Date fromDate) throws Exception;

    /**
     * Get a specific resource tool item, when given the session Id and the
     * resource id
     *
     * @param sessionId a {@link java.lang.String} object.
     * @param resourceId a {@link java.lang.String} object.
     * @throws java.lang.Exception if any.
     * @return a {@link java.lang.String} object.
     */
    public String getResource(String sessionId, String resourceId) throws Exception;

    /**
     * To get the actual resource data (file,link,etc) , provide the sessionId and resourceId
     *
     * @param sessionId a {@link java.lang.String} object.
     * @param resourceId a {@link java.lang.String} object.
     * @throws java.lang.Exception if any.
     * @return a {@link java.lang.String} object.
     */
    public String getResourceData(String sessionId, String resourceId) throws Exception;

    /**
     * To get the resource URL , provide the sessionId and resourceId
     *
     * @param sessionId a {@link java.lang.String} object.
     * @param resourceId a {@link java.lang.String} object.
     * @throws java.lang.Exception if any.
     * @return a {@link java.lang.String} object.
     */
    public String getResourceURL(String sessionId, String resourceId) throws Exception;
}
