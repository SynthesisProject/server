package coza.opencollab.synthesis.service.api.content;

/**
 * A consumer for stored files called after a file is stored in the storage service.
 *
 * @author OpenCollab
 * @since 1.0.0
 * @version 1.0.0
 */
public interface StoredFileConsumer {
    /**
     * Called after a file is stored in the storage service.
     *
     * @param name The file name.
     * @param directory The file directory.
     * @param mimeType The file mime type.
     * @param originalUrl The original url.
     * @param size The file size.
     * @param downloadKey The download key for the file.
     */
    public void stored(String name, String directory, String mimeType, String originalUrl, int size, String downloadKey);
}
