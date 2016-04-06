package coza.opencollab.synthesis.service.api.util;

import java.util.Map;

/**
 * A manager for sessions. Logs users in and out and mannage the session id.
 * Used with the lms client.
 *
 * @author OpenCollab
 * @since 1.0.0
 * @version 1.0.0
 */
public interface SessionManager {
    /**
     * Log the user in.
     *
     * @param username The username
     * @param password The password
     * @return The session id for the valid login
     */
    public String login(String username, String password);
    /**
     * A helper method to login as the administrator.
     * <p>
     * This method might log in to the lms again or just
     * return a valid login.
     *
     * @return a {@link java.lang.String} object.
     */
    public String loginAsAdmin();
    /**
     * Log a user out.
     *
     * @param username The username.
     * @return true if the user was logged out, meaning the session was still valid, false otherwise.
     */
    public boolean logout(String username);
    /**
     * Retrieve extra user details from the lms.
     *
     * @param username The username.
     * @return Any extra data of the user.
     */
    public Map<String, String> getUserDetails(String username);
}
