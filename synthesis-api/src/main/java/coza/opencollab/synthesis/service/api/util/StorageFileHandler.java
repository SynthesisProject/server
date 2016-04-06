package coza.opencollab.synthesis.service.api.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * A helper class for handling files.
 * All actions must be autonomous.
 *
 * @author OpenCollab
 * @since 1.0.0
 * @version 1.0.0
 */
public interface StorageFileHandler {
    /**
     * The extention of the files that this handler can handle.
     *
     * @return just the extention without the fullstop.
     */
    public String getExtention();
    /**
     * Gets the mime type for this file handler.
     *
     * @return The mime type or null if the handler does not work with compressed files.
     */
    public String getMimeType();
    /**
     * Whether the handler can handle the file or directory.
     *
     * @param source The file/directory.
     * @return true if this handler can handle file.
     */
    public boolean canHandle(File source);
    /**
     * Updated the name to create the final full name for this handler.
     *
     * @param baseName The base name.
     * @return A full file name.
     */
    public String getDestinationName(String baseName);
    /**
     * Retrieve the contents of a file relative to the root file.
     *
     * @param root The root file.
     * @param fileName The file name relative to the root file.
     * @return The contents.
     * @throws java.io.FileNotFoundException If the file does not exist.
     * @throws java.io.IOException If there is a problem reading the file.
     */
    public String getFileContents(File root, String fileName) throws FileNotFoundException, IOException;
    /**
     * Retrieve the contents of a file relative to the root file.
     * <p>
     * The process should look for the first file from <code>fileNames</code> that
     * it can find and return that contents.
     *
     * @param root The root file.
     * @param fileNames The file names relative to the root file.
     * @return The contents of the first file found.
     * @throws java.io.FileNotFoundException If none of the files exist.
     * @throws java.io.IOException If there is a problem reading any of the files.
     */
    public String getFileContents(File root, String... fileNames) throws FileNotFoundException, IOException;
    /**
     * Write the normal directory to the destination file in this file handler format.
     *
     * @param source The source directory.
     * @param destination The destination for this file handler format.
     * @throws java.io.IOException if any.
     */
    public void writeFromDirectory(File source, File destination) throws IOException;
    /**
     * Write the this file handler format file to the destination normal directory.
     *
     * @param source The source in this file handler format.
     * @param destination The destination directory.
     * @throws java.io.IOException if any.
     */
    public void writeToDirectory(File source, File destination) throws IOException;
    /**
     * Create a StorageFileReader that can read files and content from the source.
     *
     * @param source The source file.
     * @return The StorageFileReader. Remember to close it.
     */
    public StorageFileReader getStorageFileReader(File source);
    /**
     * Create a StorageFileWriter that can write files and content to the destination.
     *
     * @param destination The destination to write to.
     * @return The StorageFileWriter. Remember to close it.
     */
    public StorageFileWriter getStorageFileWriter(File destination);
    /**
     * Create a StorageMemoryWriter that can write entries and content to memory.
     *
     * @return The StorageMemoryWriter. Call get content to get the content from memory.
     */
    public StorageMemoryWriter getMemoryWriter();
}
