package coza.opencollab.synthesis.service.util.impl;

import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;
import coza.opencollab.synthesis.service.api.util.CacheManager;
import java.util.Arrays;

/**
 * A Hazel Cast implementation of the cache manager.
 *
 * @author OpenCollab
 * @version 1.0.1
 */
public class HazelcastCacheManager implements CacheManager{
    /**
     * The hazel cast instance.
     */
    private HazelcastInstance instance;
    /**
     * The default cache to use.
     */
    private String defaultCacheName = "default";

    /**
     * The hazel cast instance.
     *
     * @param instance a {@link com.hazelcast.core.HazelcastInstance} object.
     */
    public void setInstance(HazelcastInstance instance) {
        this.instance = instance;
    }

    /**
     * The default cache to use.
     *
     * @param defaultCacheName a {@link java.lang.String} object.
     */
    public void setDefaultCacheName(String defaultCacheName) {
        this.defaultCacheName = defaultCacheName;
    }
    
    /**
     * Retrieve the cache.
     * 
     * @param cacheName The name of the cache to retrieve. If null the default 
     * of the cache manager is retrieved.
     * @return The cache.
     */
    private IMap<String, String> getMap(String cacheName){
        if(cacheName == null){
            return instance.getMap(defaultCacheName);
        }else{
            return instance.getMap(cacheName);
        }
    }

    /** {@inheritDoc} */
    @Override
    public void init() {
        if (instance == null) {
            instance = Hazelcast.newHazelcastInstance();
        }
    }

    /** {@inheritDoc} */
    @Override
    public void destroy() {
        Hazelcast.shutdownAll();
    }

    /** {@inheritDoc} */
    @Override
    public String getStatus() {
        return "TODO";
    }

    /** {@inheritDoc} */
    @Override
    public String get(String key) {
        return get(key, defaultCacheName);
    }

    /** {@inheritDoc} */
    @Override
    public String get(String key, String cacheName) {
        return getMap(cacheName).get(key);
    }

    /** {@inheritDoc} */
    @Override
    public String put(String key, String value) {
        return put(key, value, defaultCacheName);
    }

    /** {@inheritDoc} */
    @Override
    public String put(String key, String value, String cacheName) {
        return getMap(cacheName).put(key, value);
    }

    /** {@inheritDoc} */
    @Override
    public String putIfAbsent(String key, String value) {
        return putIfAbsent(key, value, defaultCacheName);
    }

    /** {@inheritDoc} */
    @Override
    public String putIfAbsent(String key, String value, String cacheName) {
        return getMap(cacheName).putIfAbsent(key, value);
    }

    /** {@inheritDoc} */
    @Override
    public String remove(String key) {
        return remove(key, defaultCacheName);
    }

    /** {@inheritDoc} */
    @Override
    public String remove(String key, String cacheName) {
        return getMap(cacheName).remove(key);
    }
    
    /** {@inheritDoc} */
    @Override
    public void lock(String... key){
        instance.getLock(Arrays.toString(key)).lock();
    }
    
    /** {@inheritDoc} */
    @Override
    public void unlock(String... key){
        instance.getLock(Arrays.toString(key)).unlock();
    }
}
