package coza.opencollab.synthesis.service.api.util;

/**
 * The api for a link down loader. Implementations will be able
 * to look at content and download additional links into content
 * of as separate files.
 *
 * @author OpenCollab
 * @since 1.0.0
 * @version 1.0.0
 */
public interface LinkDownLoader {
    /**
     * This method is called with the raw text content.
     * <p>
     * The implementation will go through the content and find
     * linked data to download and will then convert/update the
     * content with the data or push the additional file to the
     * LinkFileConsumer.
     *
     * @param key A key for this content.
     * @param content The text content to inspect and covert.
     * @param consumer A file consumer for the additional files.
     * @return The updated text content.
     */
    public String update(String key, String content, LinkFileConsumer consumer);
}
