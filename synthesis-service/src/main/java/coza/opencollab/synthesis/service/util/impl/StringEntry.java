package coza.opencollab.synthesis.service.util.impl;

import coza.opencollab.synthesis.service.api.Defaults;
import coza.opencollab.synthesis.service.api.util.StorageEntry;

/**
 *  A entry that work with strings
 *
 * @author OpenCollab
 * @since 1.0.0
 * @version 1.0.1
 */
public class StringEntry extends StorageEntry {
    /**
     * The data in String.
     */
    private String data;

    /**
     *  Set all constructor
     *
     * @param name a {@link java.lang.String} object.
     * @param relativePath a {@link java.lang.String} object.
     * @param data a {@link java.lang.String} object.
     */
    public StringEntry(String name, String relativePath, String data) {
        super(name, relativePath);
        this.data = data;
    }

    /** {@inheritDoc} */
    @Override
    public byte[] getContents() {
        return data.getBytes(Defaults.UTF8);
    }
}
