package coza.opencollab.synthesis.service.api;

/**
 * The Module Service API to retrieve module data from Sakai.
 *
 * @author OpenCollab
 * @since 1.0.0
 * @version 1.0.0
 */
public interface ModuleService {
    /**
     * Return Formatted document listing all sites the given user has read access to.
     *
     * @param sessionId The session id of a super user
     * @param userId eid (eg jsmith26) if the user you want the list for
     * @return See getSitesUserCanAccess(sessionId)
     * @throws java.lang.Exception if any.
     */
    public String getSitesUserCanAccess(String sessionId, String userId) throws Exception;
    /**
     * Return Formatted document listing all sites user has read access based on their session id.
     *
     * @param sessionId The session id of a user who's list of sites you want to retrieve
     * @return a {@link java.lang.String} object.
     * @throws java.lang.Exception if any.
     */
    public String getSitesUserCanAccess(String sessionId) throws Exception;
}
