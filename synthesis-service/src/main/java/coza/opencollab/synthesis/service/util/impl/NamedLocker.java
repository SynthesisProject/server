package coza.opencollab.synthesis.service.util.impl;

import java.util.Arrays;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.Semaphore;

/**
 * A concurrent lock class that lock in names.
 *
 * @author OpenCollab
 * @since 1.0.0
 * @version 1.0.1
 */
public class NamedLocker {
    /**
     * A Semaphore to only allow on sync package creation at a time.
     */
    private ConcurrentMap<String, Semaphore> synchSemaphores = new ConcurrentHashMap<String, Semaphore>();
    private int permits = 1;
    
    /**
     * Default constructor.
     * Use 1 permit.
     */
    public NamedLocker(){
        this(1);
    }
    
    /**
     * Constructor setting the number of permits to use for every named lock.
     *
     * @param permits The number of permits.
     */
    public NamedLocker(int permits){
        this.permits = permits;
    }
    
    /**
     * Acquire a lock from a Semaphore.
     *
     * @param key Any values that make a good key to the Semaphore.
     */
    public void acquireLock(String... key){
        Semaphore tempS = new Semaphore(permits, true);
        Semaphore s = synchSemaphores.putIfAbsent(Arrays.toString(key), tempS);
        if(s == null){
            s = tempS;
        }
        s.acquireUninterruptibly();
    }
    
    /**
     * Release a lock from a Semaphore.
     *
     * @param key The same values used to acquire the Semaphore.
     */
    public void releaseLock(String... key){
        Semaphore s = synchSemaphores.get(Arrays.toString(key));
        if(s != null){
            s.release();
        }
    }
}
