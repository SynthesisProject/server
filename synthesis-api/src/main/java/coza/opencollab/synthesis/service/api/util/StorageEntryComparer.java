package coza.opencollab.synthesis.service.api.util;

import java.util.List;

/**
 * Compares two entry collections and return a collections of new entries.
 *
 * @author OpenCollab
 * @since 1.0.0
 * @version 1.0.0
 */
public interface StorageEntryComparer {
    /**
     * Compares the entries from entriesA to the entries from entriesB and retrieve the diff (new) entries from entriesB.
     *
     * @param entriesA A list of entries.
     * @param entriesB A list of entries.
     * @return The entries that is new in entriesB. If there is entries that contain different data
     * but are the same entry then entryB's data will be returned.
     */
    public List<StorageEntry> diff(List<StorageEntry> entriesA, List<StorageEntry> entriesB);
    /**
     * Compares the entries from entriesA to the entries from entriesB and retrieve the diff (new) entries from entriesB.
     *
     * @param entriesA A list of entries.
     * @param entriesB A list of entries.
     * @return The entries that is new in entriesB. If there is entries that contain different data
     * but are the same entry then entryB's data will be returned. Also delete markers for
     * entries that is not in entriesB anymore.
     */
    public List<StorageEntry> diffWithDelete(List<StorageEntry> entriesA, List<StorageEntry> entriesB);
    /**
     * Merge the entries together. The values for newEntries will overwrite the values of baseEntries.
     *
     * @param baseEntries A list of entries that form the base.
     * @param newEntries A list of entries that will be added and overwrite the base.
     * @return All the merged entries.
     */
    public List<StorageEntry> merge(List<StorageEntry> baseEntries, List<StorageEntry> newEntries);
}
