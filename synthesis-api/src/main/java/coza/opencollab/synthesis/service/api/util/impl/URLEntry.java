package coza.opencollab.synthesis.service.api.util.impl;

import coza.opencollab.synthesis.service.api.util.StorageEntry;

/**
 * A entry that work with a download URL
 *
 * @author OpenCollab
 * @since 1.0.0
 * @version 1.0.0
 */
public class URLEntry extends StorageEntry{
    /**
     * The download URL.
     */
    private final String url;
    
    /**
     * Set all constructor
     *
     * @param name a {@link java.lang.String} object.
     * @param relativeDirectory a {@link java.lang.String} object.
     * @param url a {@link java.lang.String} object.
     */
    public URLEntry(String name, String relativeDirectory, String url){
        super(name, relativeDirectory);
        this.url = url;
    }
    
    
    /**
     * {@inheritDoc}
     *
     * The URLEntry only stores a download link to the file. Need to override
     */
    @Override
    public byte[] getContents() {
        return null;
    }

    /**
     * The download URL used to stream the file from
     *
     * @return the URL
     */
    public String getUrl() {
        return url;
    }
}
