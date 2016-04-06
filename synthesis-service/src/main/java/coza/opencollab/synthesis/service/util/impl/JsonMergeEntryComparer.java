package coza.opencollab.synthesis.service.util.impl;

import coza.opencollab.synthesis.service.api.Defaults;
import coza.opencollab.synthesis.service.api.util.StorageEntry;
import coza.opencollab.synthesis.service.api.util.StorageEntryComparer;
import coza.opencollab.synthesis.service.api.util.impl.ByteArrayEntry;
import coza.opencollab.synthesis.util.JsonParser;

/**
 * A comparer that combines (merge) json files.
 *
 * @author OpenCollab
 * @since 1.0.0
 * @version 1.0.1
 */
public class JsonMergeEntryComparer extends AbstractEntryComparer implements StorageEntryComparer {

    /** {@inheritDoc} */
    @Override
    public StorageEntry diff(StorageEntry entryA, StorageEntry entryB) {
        String diff = JsonParser.diff(new String(entryA.getContents(), Defaults.UTF8), new String(entryB.getContents(), Defaults.UTF8));
        return new ByteArrayEntry(entryA.getName(), entryA.getRelativeDirectory(), diff.getBytes(Defaults.UTF8));
    }
    
    /** {@inheritDoc} */
    @Override
    public StorageEntry merge(StorageEntry entryA, StorageEntry entryB){
        String merged = JsonParser.merge(new String(entryA.getContents(), Defaults.UTF8), new String(entryB.getContents(), Defaults.UTF8));
        return new ByteArrayEntry(entryA.getName(), entryA.getRelativeDirectory(), merged.getBytes(Defaults.UTF8));
    }
}
