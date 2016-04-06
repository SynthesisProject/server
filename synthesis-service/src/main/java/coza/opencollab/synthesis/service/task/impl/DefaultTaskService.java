package coza.opencollab.synthesis.service.task.impl;

import coza.opencollab.synthesis.SynthesisException;
import coza.opencollab.synthesis.service.api.task.TaskService;
import static coza.opencollab.synthesis.service.api.ErrorCodes.INCORRECT_CONFIG;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import javax.annotation.PreDestroy;

/**
 * A implementation for the task service using executors.
 *
 * @author OpenCollab
 * @since 1.0.0
 * @version 1.0.1
 */
public class DefaultTaskService implements TaskService{
    /**
     * Whether this class loads content async or not.
     */
    private boolean asynch = true;
    /**
     * The executor service for async running
     */
    private ExecutorService executorService;
    /**
     * The scheduled executor service for async running
     */
    private ScheduledExecutorService scheduledExecutorService;
    /**
     * The scheduled tasks.
     */
    private Map<String, ScheduledFuture> scheduledTasks = new ConcurrentHashMap<String, ScheduledFuture>();
    /**
     * The delay before a scheduled content loading runs for the first time in minutes.
     */
    private long defaultDelay = 0L;
    /**
     * The delay between runs for a scheduled job in minutes.
     */
    private long defaultFrequency = 360L;

    /**
     * Whether this class loads tool async or not.
     *
     * @param asynch a boolean.
     */
    public void setAsynch(boolean asynch) {
        this.asynch = asynch;
    }

    /**
     * Whether this class loads tool async or not.
     *
     * @return a boolean.
     */
    public boolean isAsynch() {
        return asynch;
    }

    /**
     * The executor service for async running
     *
     * @param executorService a {@link java.util.concurrent.ExecutorService} object.
     */
    public void setExecutorService(ExecutorService executorService) {
        this.executorService = executorService;
    }

    /**
     * The scheduled executor service for async running
     *
     * @param scheduledExecutorService a {@link java.util.concurrent.ScheduledExecutorService} object.
     */
    public void setScheduledExecutorService(ScheduledExecutorService scheduledExecutorService) {
        this.scheduledExecutorService = scheduledExecutorService;
        if(executorService == null){
            executorService = scheduledExecutorService;
        }
    }

    /**
     * The delay before a scheduled content loading runs for the first time in hours.
     *
     * @param defaultDelay a long.
     */
    public void setDefaultDelay(long defaultDelay) {
        this.defaultDelay = defaultDelay;
    }

    /**
     * The delay between runs for a scheduled job in hours.
     *
     * @param defaultFrequency a long.
     */
    public void setDefaultFrequency(long defaultFrequency) {
        this.defaultFrequency = defaultFrequency;
    }
    
    /** {@inheritDoc} */
    @Override
    public Future<?> scheduleTask(Runnable runnable){
        if(!asynch){
            FutureTask future = new FutureTask(runnable, null);
            future.run();
            return future;
        }else if(executorService != null){
            return executorService.submit(runnable);
        }else if(scheduledExecutorService != null){
            return scheduledExecutorService.submit(runnable);
        }else{
            throw new SynthesisException(INCORRECT_CONFIG, "No executor set!");
        }
    }
    
    /** {@inheritDoc} */
    @Override
    public <T> Future<T> scheduleTask(Callable<T> callable){
        if(!asynch){
            FutureTask future = new FutureTask(callable);
            future.run();
            return future;
        }else if(executorService != null){
            return executorService.submit(callable);
        }else if(scheduledExecutorService != null){
            return scheduledExecutorService.submit(callable);
        }else{
            throw new SynthesisException(INCORRECT_CONFIG, "No executor set!");
        }
    }
    
    /** {@inheritDoc} */
    @Override
    public ScheduledFuture<?> scheduleTaskAtFixedRate(String taskName, Runnable runnable){
        return scheduleTaskAtFixedRate(taskName, runnable, defaultFrequency);
    }
    
    /** {@inheritDoc} */
    @Override
    public ScheduledFuture<?> scheduleTaskAtFixedRate(String taskName, Runnable runnable, long frequencyMinutes){
        if(scheduledExecutorService == null){
            throw new SynthesisException(INCORRECT_CONFIG, "The schedule executor is not set.");
        }
        if(!isTaskScheduled(taskName)){
            ScheduledFuture<?> future = scheduledExecutorService.scheduleAtFixedRate(runnable, defaultDelay, frequencyMinutes, TimeUnit.MINUTES);
            scheduledTasks.put(taskName, future);
        }
        return scheduledTasks.get(taskName);
    }
    
    /** {@inheritDoc} */
    @Override
    public boolean isTaskScheduled(String taskName){
        return scheduledTasks.containsKey(taskName);
    }
    
    /** {@inheritDoc} */
    @Override
    public void cancelTask(String taskName){
        ScheduledFuture scheduledFuture = scheduledTasks.remove(taskName);
        if(scheduledFuture != null){
            scheduledFuture.cancel(true);
        }
    }
    
    /**
     * Does cleanup.
     */
    @PreDestroy
    public void destroy(){
        if(executorService != null){
            executorService.shutdown();
        }
        for(Map.Entry<String, ScheduledFuture> entry: scheduledTasks.entrySet()){
            cancelTask(entry.getKey());
        }
        if(scheduledExecutorService != null){
            scheduledExecutorService.shutdown();
        }
    }
}
