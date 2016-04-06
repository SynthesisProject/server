package coza.opencollab.synthesis.service.api.content;

import coza.opencollab.synthesis.service.api.creator.StorageService;
import coza.opencollab.synthesis.service.api.util.LinkDownLoader;
import coza.opencollab.synthesis.service.api.util.LinkFileConsumer;
import coza.opencollab.synthesis.service.api.util.StorageEntry;
import coza.opencollab.synthesis.service.api.util.impl.ByteArrayEntry;
import coza.opencollab.synthesis.service.api.util.impl.URLEntry;
import java.io.File;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.FileFilterUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.ConfigurableMimeFileTypeMap;

/**
 * Just a abstract implementation of the content converter so that we can have
 * default implementations.
 *
 * @author OpenCollab
 * @since 1.0.0
 * @version 1.0.0
 */
public abstract class AbstractResourceContentConverter<T, U> extends AbstractContentConverter<T, U> {

    /**
     * The storage service.
     */
    @Autowired
    private StorageService storageService;
    /**
     * The link downloader.
     */
    private LinkDownLoader linkDownLoader;
    /**
     * A mime type map.
     */
    private final ConfigurableMimeFileTypeMap mimeTypeMap = new ConfigurableMimeFileTypeMap();
    /**
     * Whether to include the files in the content.
     */
    private boolean includeFilesInContent = false;
    /**
     * Whether to store files for download.
     */
    private boolean storeFilesForDownload = false;

    /**
     * The storage service.
     *
     * @param storageService a {@link coza.opencollab.synthesis.service.api.creator.StorageService} object.
     */
    public void setStorageService(StorageService storageService) {
        this.storageService = storageService;
    }

    /**
     * The link downloader.
     *
     * @param linkDownLoader a {@link coza.opencollab.synthesis.service.api.util.LinkDownLoader} object.
     */
    public void setLinkDownLoader(LinkDownLoader linkDownLoader) {
        this.linkDownLoader = linkDownLoader;
    }

    /**
     * Whether to include the files.
     *
     * @param includeFilesInContent a boolean.
     */
    public void setIncludeFilesInContent(boolean includeFilesInContent) {
        this.includeFilesInContent = includeFilesInContent;
    }

    /**
     * Whether toe store files for download.
     *
     * @param storeFilesForDownload a boolean.
     */
    public void setStoreFilesForDownload(boolean storeFilesForDownload) {
        this.storeFilesForDownload = storeFilesForDownload;
    }
    
    /**
     * Returns a storage key for this converter.
     *
     * @return The key.
     */
    public abstract String getStorageKey();

    /** {@inheritDoc} */
    @Override
    public boolean hasExtraResources(String moduleId) {
        return !includeFilesInContent && storeFilesForDownload;
    }

    /** {@inheritDoc} */
    @Override
    public Map<String, File> getExtraResources(String moduleId) {
        File directory = storageService.getStorageDirectory(getStorageKey(), moduleId);
        Collection<File> files = FileUtils.listFiles(directory, FileFilterUtils.fileFileFilter(), FileFilterUtils.trueFileFilter());
        Map<String, File> fileMap = new HashMap<>();
        int baseLength = directory.getAbsolutePath().length() - moduleId.length();
        for(File file: files){
            String path = file.getAbsolutePath();
            path = path.substring(baseLength);
            fileMap.put(path, file);
        }
        return fileMap;
    }

    /**
     * Add a extra resource. This method may include the resource in the entries
     * list and may save it for storage depending on the <code>includeFilesInContent</code>
     * and <code>storeFilesForDownload</code> variables.
     *
     * @param moduleId The moduleId.
     * @param entries The converters list of entries.
     * @param name a {@link java.lang.String} object.
     * @param directory a {@link java.lang.String} object.
     * @param contentURL a {@link java.lang.String} object.
     * @return The download key if the entry was saved to storage.
     */
    protected String addExtraResource(String moduleId, List<StorageEntry> entries, String name, String directory, String contentURL) {
        URLEntry entry = new URLEntry(name, directory, contentURL);
        if (includeFilesInContent) {
            entries.add(entry);
        }
        if (storeFilesForDownload) {
            String key;
            if(directory.startsWith(moduleId)){
                key = getStorageKey();
            }else{
                key = storageService.getDirectoryPath(getStorageKey(), moduleId);
            }
            return storageService.store(key, entry);
        }else{
            return null;
        }
    }

    /**
     * Update the links in the data to point to downloaded files.
     *
     * @param moduleId The module id.
     * @param entries The converters list of entries.
     * @param consumer The stored file consumer. Called if a file is stored.
     * @param dataId A id for the data.
     * @param dataId A id for the data.
     * @param dataId A id for the data.
     * @param dataId A id for the data.
     * @param dataId A id for the data.
     * @param dataId A id for the data.
     * @param data The data.
     * @return The updated data.
     */
    protected String updateLinks(String moduleId, final List<StorageEntry> entries, final StoredFileConsumer consumer, String dataId, String data) {
        String key = storageService.getDirectoryPath(moduleId, dataId);
        return linkDownLoader.update(key, data, new LinkFileConsumer() {
            @Override
            public String addFile(String name, String directory, String originalUrl, byte[] data) {
                ByteArrayEntry entry = new ByteArrayEntry(name, directory, data);
                if (includeFilesInContent) {
                    entries.add(entry);
                }
                String path = storageService.getFilePath(name, directory);
                if (storeFilesForDownload) {
                    consumer.stored(name, path, mimeTypeMap.getContentType(name), originalUrl, data.length, storageService.store(getStorageKey(), entry));
                }
                return path;
            }
        });
    }
}
