package coza.opencollab.synthesis.service.util.impl;

import coza.opencollab.synthesis.service.api.util.StorageEntry;
import coza.opencollab.synthesis.service.api.util.StorageEntryComparer;
import coza.opencollab.synthesis.service.api.util.impl.DeletedFileEntry;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * A abstract comparer for files. This class compare whether files and;
 * - add new files
 * - add deleted markers for deleted files
 * - and calls the abstract getNewEntry for files in both entries.
 *
 * @author OpenCollab
 * @since 1.0.0
 * @version 1.0.1
 */
public abstract class AbstractEntryComparer implements StorageEntryComparer {

    /** {@inheritDoc} */
    @Override
    public List<StorageEntry> diff(List<StorageEntry> entriesA, List<StorageEntry> entriesB) {
        return diff(entriesA, entriesB, false);
    }

    /** {@inheritDoc} */
    @Override
    public List<StorageEntry> diffWithDelete(List<StorageEntry> entriesA, List<StorageEntry> entriesB) {
        return diff(entriesA, entriesB, false);
    }
    
    /**
     * This goes through the entries and find the new entries in entriesB. If there is entries in entriesA that is not
     * in entriesB then delete markers will be added if the flag is true.
     * If the same entry exist in both entriesA and entriesB then the diff method is called on the entries.
     */
    private List<StorageEntry> diff(List<StorageEntry> entriesA, List<StorageEntry> entriesB, boolean deleteMarker) {
        List<StorageEntry> entries = new ArrayList<StorageEntry>();
        Iterator<StorageEntry> i = entriesA.iterator();
        while (i.hasNext()) {
            StorageEntry entryA = i.next();
            i.remove();
            int index = entriesB.indexOf(entryA);
            if (index < 0) {
                //file removed from new version
                if(deleteMarker){
                    entries.add(new DeletedFileEntry(entryA.getName(), entryA.getRelativeDirectory(), entryA.isDirectory()));
                }
            } else {
                StorageEntry entryB = entriesB.get(index);
                entriesB.remove(index);
                StorageEntry newEntry = diff(entryA, entryB);
                if (newEntry != null) {
                    entries.add(newEntry);
                }
            }
        }
        //all the entries still in entriesB is new
        entries.addAll(entriesB);
        return entries;
    }

    /**
     * Compare the actual contents of the entries and retrieve the diff (new in entryB).
     *
     * @return A new entry with the new contents, otherwise null.
     * @param entryA a {@link coza.opencollab.synthesis.service.api.util.StorageEntry} object.
     * @param entryB a {@link coza.opencollab.synthesis.service.api.util.StorageEntry} object.
     */
    public abstract StorageEntry diff(StorageEntry entryA, StorageEntry entryB);
    
    /** {@inheritDoc} */
    @Override
    public List<StorageEntry> merge(List<StorageEntry> entriesA, List<StorageEntry> entriesB){
        List<StorageEntry> entries = new ArrayList<StorageEntry>();
        Iterator<StorageEntry> i = entriesA.iterator();
        while (i.hasNext()) {
            StorageEntry entryA = i.next();
            i.remove();
            int index = entriesB.indexOf(entryA);
            if (index < 0) {
                //file not in B
                entries.add(entryA);
            } else {
                StorageEntry entryB = entriesB.get(index);
                entriesB.remove(index);
                StorageEntry newEntry = merge(entryA, entryB);
                if (newEntry != null) {
                    entries.add(newEntry);
                }
            }
        }
        //all the entries still in entriesB
        entries.addAll(entriesB);
        return entries;
    }

    /**
     * Merge the actual contents of the entries. EntryB will overwrite values of entryA.
     *
     * @return A new entry with the merged contents.
     * @param entryA a {@link coza.opencollab.synthesis.service.api.util.StorageEntry} object.
     * @param entryB a {@link coza.opencollab.synthesis.service.api.util.StorageEntry} object.
     */
    public abstract StorageEntry merge(StorageEntry entryA, StorageEntry entryB);
}
