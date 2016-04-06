package coza.opencollab.synthesis.service.api;

/**
 * The statuses for all service calls.
 *
 * @author OpenCollab
 * @since 1.0.0
 * @version 1.0.0
 */
public enum ServiceCallStatus {

    /**
     * Service call was a success.
     */
    SUCCESS,
    /**
     * Service call is successful but has a warning.
     */
    WARNING,
    /**
     * Service call had a error. Something could not be completed successfully
     */
    ERROR,
    /**
     * Service call caused an exception.
     */
    EXCEPTION;
}
