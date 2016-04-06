package coza.opencollab.synthesis.service.util.impl;

import coza.opencollab.synthesis.service.api.util.CacheManager;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * A simple map implementation of the cache manager.
 *
 * @author OpenCollab
 * @since 1.0.0
 * @version 1.0.1
 */
public class MapCacheManager implements CacheManager{
    /**
     * The map of maps of caches.
     */
    private static final ConcurrentMap<String, ConcurrentMap<String, String>> caches = new ConcurrentHashMap<String, ConcurrentMap<String, String>>();
    /**
     * The default cache name.
     */
    private static final String DEFAULT_CACHE = "default";
    /**
     * A locker for locks.
     */
    private static final NamedLocker locker = new NamedLocker(1);
    
    /**
     * Get the cache. Create it if needed.
     */
    private ConcurrentMap<String, String> getCache(String cacheName){
        ConcurrentMap<String, String> cache = caches.get(cacheName);
        if(cache == null){
            cache = new ConcurrentHashMap<String, String>();
            caches.put(cacheName, cache);
        }
        return cache;
    }

    /** {@inheritDoc} */
    @Override
    public void init() {
    }

    /** {@inheritDoc} */
    @Override
    public void destroy() {
        caches.clear();
    }
    
    /** {@inheritDoc} */
    @Override
    public String getStatus(){
        StringBuilder buf = new StringBuilder(getClass().getSimpleName());
        buf.append(" (size=");
        buf.append(caches.size());
        buf.append(")\n");
        for(Map.Entry<String, ConcurrentMap<String, String>> cacheEntry: caches.entrySet()){
            buf.append("\t");
            buf.append(cacheEntry.getKey());
            buf.append(" (size=");
            ConcurrentMap<String, String> cache = cacheEntry.getValue();
            buf.append(cache==null?"null":cache.size());
            buf.append(")\n");
        }
        return buf.toString();
    }
    
    /** {@inheritDoc} */
    @Override
    public String get(String key) {
        return getCache(DEFAULT_CACHE).get(key);
    }

    /** {@inheritDoc} */
    @Override
    public String get(String key, String cacheName) {
        return getCache(cacheName).get(key);
    }

    /** {@inheritDoc} */
    @Override
    public String put(String key, String value) {
        return getCache(DEFAULT_CACHE).put(key, value);
    }

    /** {@inheritDoc} */
    @Override
    public String put(String key, String value, String cacheName) {
        return getCache(cacheName).put(key, value);
    }

    /** {@inheritDoc} */
    @Override
    public String putIfAbsent(String key, String value) {
        return getCache(DEFAULT_CACHE).putIfAbsent(key, value);
    }

    /** {@inheritDoc} */
    @Override
    public String putIfAbsent(String key, String value, String cacheName) {
        return getCache(cacheName).putIfAbsent(key, value);
    }

    /** {@inheritDoc} */
    @Override
    public String remove(String key) {
        return getCache(DEFAULT_CACHE).remove(key);
    }

    /** {@inheritDoc} */
    @Override
    public String remove(String key, String cacheName) {
        return getCache(cacheName).remove(key);
    }
    
    /** {@inheritDoc} */
    @Override
    public void lock(String... key){
        locker.acquireLock(key);
    }
    
    /** {@inheritDoc} */
    @Override
    public void unlock(String... key){
        locker.releaseLock(key);
    }
}
