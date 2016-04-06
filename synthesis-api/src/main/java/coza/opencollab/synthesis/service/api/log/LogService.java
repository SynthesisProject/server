package coza.opencollab.synthesis.service.api.log;

/**
 * A logging service for exceptions or any other events in this project.
 *
 * @author OpenCollab
 * @since 1.0.0
 * @version 1.0.0
 */
public interface LogService {
    /**
     * A priority indicator for events.
     */
    public enum Priority{
        High,
        Normal,
        Low;
    }
    /**
     * Log a message at a normal priority.
     *
     * @param message The message.
     */
    public void log(String message);
    /**
     * Log a message at a specified priority.
     *
     * @param message The message.
     * @param priority The priority.
     */
    public void log(String message, Priority priority);
    /**
     * Log a exception at a normal priority.
     *
     * @param message The message.
     * @param t The exception.
     */
    public void log(String message, Throwable t);
    /**
     * Log a exception at a specified priority.
     *
     * @param message The message.
     * @param t The exception.
     * @param priority The priority.
     */
    public void log(String message, Throwable t, Priority priority);
}
