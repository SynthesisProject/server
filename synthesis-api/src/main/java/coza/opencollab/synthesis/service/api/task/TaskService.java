package coza.opencollab.synthesis.service.api.task;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledFuture;

/**
 * A service to execute tasks.
 *
 * @author OpenCollab
 * @since 1.0.0
 * @version 1.0.0
 */
public interface TaskService {
    /**
     * Schedule a task (the runnable) for execution.
     *
     * @param runnable The runnable to execute.
     * @return a {@link java.util.concurrent.Future} object.
     */
    public Future<?> scheduleTask(Runnable runnable);
    /**
     * Schedule a task (the callable) for execution.
     *
     * @param callable The callable to execute.
     * @param <T> a T object.
     * @return a {@link java.util.concurrent.Future} object.
     */
    public <T> Future<T> scheduleTask(Callable<T> callable);
    /**
     * Schedule the runnable for to run every <code>schedulePeriod</code> hours.
     * The task is only scheduled it if the name is not already scheduled.
     *
     * @param taskName Some unique task name.
     * @param runnable The runnable to execute.
     * @return a {@link java.util.concurrent.ScheduledFuture} object.
     */
    public ScheduledFuture<?> scheduleTaskAtFixedRate(String taskName, Runnable runnable);
    /**
     * Schedule the runnable for to run every <code>schedulePeriod</code> hours.
     * The task is only scheduled it if the name is not already scheduled.
     *
     * @param taskName Some unique task name.
     * @param runnable The runnable to execute.
     * @param frequencyMinutes a long.
     * @return a {@link java.util.concurrent.ScheduledFuture} object.
     */
    public ScheduledFuture<?> scheduleTaskAtFixedRate(String taskName, Runnable runnable, long frequencyMinutes);
    /**
     * Check if a task for the name is scheduled.
     *
     * @param taskName Some unique task name.
     * @return true if the task for the name is already scheduled.
     */
    public boolean isTaskScheduled(String taskName);
    /**
     * Cancels the task immediately, or after it finishes with the current
     * run if it is busy.
     *
     * @param taskName Some unique task name.
     */
    public void cancelTask(String taskName);
}
