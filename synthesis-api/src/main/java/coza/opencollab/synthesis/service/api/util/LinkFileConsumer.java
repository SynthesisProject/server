package coza.opencollab.synthesis.service.api.util;

/**
 * A consumer API for files downloaded by <code>LinkDownloader</code>s.
 *
 * @author OpenCollab
 * @since 1.0.0
 * @version 1.0.0
 */
public interface LinkFileConsumer {
    /**
     * Called to add a file to the consumer.
     *
     * @param name The file name.
     * @param directory The file directory,
     * @param originalUrl The original url.
     * @param data The file data in bytes.
     * @return The link to the file.
     */
    public String addFile(String name, String directory, String originalUrl, byte[] data);
}
