package coza.opencollab.synthesis.util;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * A util class for user data.
 *
 * @author OpenCollab
 * @since 1.0.0
 * @version 1.0.1
 */
public final class AuthenticationUtil {
    
    /**
     * Util class, so private
     */
    private AuthenticationUtil(){
        //utility class
    }

    /**
     * Get the username.
     *
     * @return a {@link java.lang.String} object.
     */
    public static String getUsername() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            return ((UserDetails) principal).getUsername();
        } else {
            return principal.toString();
        }
    }

    /**
     * Get the user display name.
     * XXX
     *
     * @return a {@link java.lang.String} object.
     */
    public static String getUserDisplayName() {
        return getUsername();
    }

    /**
     * Get the user email address.
     * XXX
     *
     * @return a {@link java.lang.String} object.
     */
    public static String getUserEmail() {
        return null;
    }
}
