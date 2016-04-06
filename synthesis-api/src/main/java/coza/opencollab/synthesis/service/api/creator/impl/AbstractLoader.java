package coza.opencollab.synthesis.service.api.creator.impl;

import coza.opencollab.synthesis.service.api.creator.StorageService;
import coza.opencollab.synthesis.service.api.dao.Dao;
import coza.opencollab.synthesis.service.api.task.TaskService;
import coza.opencollab.synthesis.service.api.util.CacheManager;
import coza.opencollab.synthesis.service.api.util.StorageEntryComparer;
import coza.opencollab.synthesis.service.api.util.impl.DeletedFileEntry;
import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

/**
 * A abstract implementation to share some standard code.
 *
 * @author OpenCollab
 * @since 1.0.0
 * @version 1.0.0
 */
public abstract class AbstractLoader {
    /**
     * The logger
     */
    protected final Logger LOG = Logger.getLogger(getClass());
    /**
     * The task service.
     */
    @Autowired
    protected TaskService taskService;
    /**
     * The dao.
     */
    @Autowired
    protected Dao dao;
    /**
     * The storage service.
     */
    @Autowired
    protected StorageService storageService;
    /**
     * The cache manager.
     */
    @Autowired
    private CacheManager cacheManager;
    /**
     * Whether to update non existing version to the current version
     * without throwing an exception.
     */
    private boolean silentlyUpdateNonExistingVersions = false;
    /**
     * The default non existing version.
     */
    private String nonExistingVersion = "0";
    /**
     * The entry comparer.
     */
    protected StorageEntryComparer entryComparer;
    /**
     * The version date format.
     */
    private String versionDateFormat = "yyyyMMddHHmmssSSS";

    /**
     * A key used for storage.
     *
     * @return a {@link java.lang.String} object.
     */
    public abstract String getKey();

    /**
     * The task service.
     *
     * @param taskService a {@link coza.opencollab.synthesis.service.api.task.TaskService} object.
     */
    public void setTaskService(TaskService taskService) {
        this.taskService = taskService;
    }
    
    /**
     * The dao
     *
     * @param dao a {@link coza.opencollab.synthesis.service.api.dao.Dao} object.
     */
    public void setDao(Dao dao) {
        this.dao = dao;
    }

    /**
     * The storage service
     *
     * @param storageService a {@link coza.opencollab.synthesis.service.api.creator.StorageService} object.
     */
    public void setStorageService(StorageService storageService) {
        this.storageService = storageService;
    }

    /**
     * Whether to update non existing version to the current version
     * without throwing an exception.
     *
     * @param silentlyUpdateNonExistingVersions a boolean.
     */
    public void setSilentlyUpdateNonExistingVersions(boolean silentlyUpdateNonExistingVersions) {
        this.silentlyUpdateNonExistingVersions = silentlyUpdateNonExistingVersions;
    }

    /**
     * Whether to update non existing version to the current version
     * without throwing an exception.
     *
     * @return a boolean.
     */
    public boolean silentlyUpdateNonExistingVersions() {
        return silentlyUpdateNonExistingVersions;
    }

    /**
     * The default non existing version.
     *
     * @return a {@link java.lang.String} object.
     */
    public String getNonExistingVersion() {
        return nonExistingVersion;
    }

    /**
     * The default non existing version.
     *
     * @param nonExistingVersion a {@link java.lang.String} object.
     */
    public void setNonExistingVersion(String nonExistingVersion) {
        this.nonExistingVersion = nonExistingVersion;
    }

    /**
     * The entry comparer.
     *
     * @param entryComparer a {@link coza.opencollab.synthesis.service.api.util.StorageEntryComparer} object.
     */
    public void setEntryComparer(StorageEntryComparer entryComparer) {
        this.entryComparer = entryComparer;
    }

    /**
     * The version date format.
     *
     * @param versionDateFormat a {@link java.lang.String} object.
     */
    public void setVersionDateFormat(String versionDateFormat) {
        this.versionDateFormat = versionDateFormat;
    }
    
    /**
     * Acquire a lock using a cache.
     *
     * @param key Any values that make a good key.
     */
    protected void acquireLock(String... key){
        cacheManager.lock(key);
    }
    
    /**
     * Release a lock using a cache.
     *
     * @param key The same values used to acquire the lock.
     */
    protected void releaseLock(String... key){
        cacheManager.unlock(key);
    }

    /**
     * Build a absolute file name for the values provided.
     * Values can be username, tool name, module, version...
     *
     * @param values The values to use in the name.
     * @return A file name.
     */
    protected String getName(String... values) {
        if(values == null || values.length == 0){
            //let it break
            return null;
        }
        StringBuilder buff = new StringBuilder(values[0]);
        for(int i = 1; i < values.length; i++){
            buff.append("-");
            buff.append(values[i]);
        }
        return buff.toString();
    }
    
    /**
     * Cleanup deleted markers that is not necessary anymore.
     *
     * @param directory a {@link java.io.File} object.
     */
    protected void checkDeleted(File directory){
        File[] deletedFiles = directory.listFiles(DeletedFileEntry.getFileFilter());
        for(File deletedFile: deletedFiles){
            File realFile = DeletedFileEntry.getRealFile(deletedFile);
            if(realFile.exists() && realFile.lastModified() > deletedFile.lastModified()){
                deletedFile.delete();
            }
        }
    }
    
    /**
     * Gets a new date based version.
     *
     * @return a {@link java.lang.String} object.
     */
    protected String getNewVersion() {
        return new SimpleDateFormat(versionDateFormat).format(new Date());
    }
    
    /**
     * Get the date from the version.
     *
     * @param version a {@link java.lang.String} object.
     * @return a {@link java.util.Date} object.
     */
    protected Date getVersionDate(String version){
        if(StringUtils.isEmpty(version) || version.equals("0")){
            return null;
        }
        try {
            return new SimpleDateFormat(versionDateFormat).parse(version);
        } catch (ParseException e) {
            LOG.debug("Invalid version: " + version, e);
            return null;
        }
    }
}
