package coza.opencollab.synthesis.service.api.util;

import java.util.List;

/**
 * A StorageFileWriter is created by a StorageFileHandler to
 * write files and content of a file location.
 *
 * @author OpenCollab
 * @since 1.0.0
 * @version 1.0.0
 */
public interface StorageFileWriter {
    /**
     * Writes the entry to a location dictated by the StorageFileHandler
     *
     * @param entry File entry
     */
    public void write(StorageEntry entry);
    /**
     * Writes all the entries to a location dictated by the StorageFileHandler
     *
     * @param entries File entries
     */
    public void write(List<StorageEntry> entries);
    /**
     * Does cleanup, must be called after use.
     */
    public void close();
}
