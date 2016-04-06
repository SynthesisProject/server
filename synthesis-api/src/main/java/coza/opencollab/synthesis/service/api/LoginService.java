
package coza.opencollab.synthesis.service.api;

/**
 * The API for the login service.
 *
 * @author OpenCollab
 * @since 1.0.0
 * @version 1.0.0
 */
public interface LoginService {
    /**
     * Log in using the credentials.
     *
     * @param username a {@link java.lang.String} object.
     * @param password a {@link java.lang.String} object.
     * @return The session id.
     * @throws java.lang.Exception If there was a problem logging in.
     */
    public String login(String username, String password) throws Exception;
    /**
     * Log a user out.
     *
     * @param sessionId The session id.
     * @return true if the user was logged out, meaning the session was still valid, false otherwise.
     * @throws java.lang.Exception if any.
     */
    public boolean logout(String sessionId) throws Exception;
    /**
     * Retrieve the user details.
     *
     * @param sessionId The session id.
     * @param username The username.
     * @return The user details.
     * @throws java.lang.Exception if any.
     */
    public String getUserDetails(String sessionId, String username) throws Exception;
}
