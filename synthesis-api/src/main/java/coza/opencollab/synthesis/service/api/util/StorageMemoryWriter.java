package coza.opencollab.synthesis.service.api.util;

import java.util.List;

/**
 * A writer that write to memory.
 *
 * @author OpenCollab
 * @since 1.0.0
 * @version 1.0.0
 */
public interface StorageMemoryWriter {
    /**
     * Gets the mime type for this file handler.
     *
     * @return The mime type or null if the handler does not work with compressed files.
     */
    public String getMimeType();
    /**
     * Writes the entry to memory.
     *
     * @param entry File entry
     */
    public void write(StorageEntry entry);
    /**
     * Writes all the entries to memory.
     *
     * @param entries File entries
     */
    public void write(List<StorageEntry> entries);
    /**
     * Get the entries written to memory in the format of the original StorageFileHandler.
     *
     * After this method this class might be unusable.
     *
     * @return an array of byte.
     */
    public byte[] getContent();
}
