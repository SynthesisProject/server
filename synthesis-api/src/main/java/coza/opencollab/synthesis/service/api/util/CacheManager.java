package coza.opencollab.synthesis.service.api.util;

/**
 * A cache manager API.
 *
 * @author OpenCollab
 * @since 1.0.0
 * @version 1.0.0
 */
public interface CacheManager {
    
    /**
     * The init method. Called before the class is used but
     * after all the properties are set.
     */
    public void init();
    
    /**
     * The destroy method.
     */
    public void destroy();
    
    /**
     * Retrieve a status of the cache.
     *
     * @return a {@link java.lang.String} object.
     */
    public String getStatus();
    
    /**
     * Retrieve the value for pthe given key.
     *
     * @param key The key for the value in the cache.
     * @return The value, or null if it does not exist in the cache.
     */
    public String get(String key);
    
    /**
     * Retrieve the value for pthe given key.
     *
     * @param key The key for the value in the cache.
     * @param cacheName The cache to look at.
     * @return The value, or null if it does not exist in the cache.
     */
    public String get(String key, String cacheName);
    
    /**
     * Place a value in the default cache.
     *
     * @param key The key for the value in the cache.
     * @param value The value to put in the cache.
     * @return a {@link java.lang.String} object.
     */
    public String put(String key, String value);
    
    /**
     * Place a value in the named cache.
     *
     * @param key The key for the value in the cache.
     * @param value The value to put in the cache.
     * @param cacheName The cache to look at.
     * @return a {@link java.lang.String} object.
     */
    public String put(String key, String value, String cacheName);
    
    /**
     * Place a value in the default cache.
     *
     * @param key The key for the value in the cache.
     * @param value The value to put in the cache.
     * @return a {@link java.lang.String} object.
     */
    public String putIfAbsent(String key, String value);
    
    /**
     * Place a value in the named cache.
     *
     * @param key The key for the value in the cache.
     * @param value The value to put in the cache.
     * @param cacheName The cache to look at.
     * @return a {@link java.lang.String} object.
     */
    public String putIfAbsent(String key, String value, String cacheName);
    
    /**
     * Removes a entry from the cache.
     *
     * @param key The key for the value in the cache.
     * @return The current entry for the key.
     */
    public String remove(String key);
    
    /**
     * Removes a entry from the cache.
     *
     * @param key The key for the value in the cache.
     * @param cacheName The cache to look at.
     * @return The current entry for the key.
     */
    public String remove(String key, String cacheName);
    
    /**
     * Do a cache wide concurrent lock.
     *
     * @param key The key of the lock.
     */
    public void lock(String... key);
    
    /**
     * Unlock the lock locked by the lock method.
     *
     * @param key The key of the lock.
     */
    public void unlock(String... key);
}
