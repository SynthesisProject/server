package coza.opencollab.synthesis.service.api.util;

import java.util.List;

/**
 * A StorageFileReader is created by a StorageFileHandler to
 * read the files and content of a file location.
 *
 * @author OpenCollab
 * @since 1.0.0
 * @version 1.0.0
 */
public interface StorageFileReader {
    /**
     * Retrieve all the entries, files and directory.
     *
     * @return All the entries.
     */
    public abstract List<StorageEntry> getEntries();
    /**
     * Does cleanup, must be called after use.
     */
    public void close();
}
