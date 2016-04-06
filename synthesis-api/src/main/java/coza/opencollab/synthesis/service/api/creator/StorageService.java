package coza.opencollab.synthesis.service.api.creator;

import coza.opencollab.synthesis.SynthesisException;
import coza.opencollab.synthesis.service.api.util.StorageEntry;
import coza.opencollab.synthesis.service.api.util.StorageFileHandler;
import coza.opencollab.synthesis.service.api.util.StorageFileReader;
import coza.opencollab.synthesis.service.api.util.StorageFileWriter;
import coza.opencollab.synthesis.service.api.util.StorageMemoryWriter;
import coza.opencollab.synthesis.service.api.util.impl.URLEntry;
import java.io.File;
import java.io.FileInputStream;
import java.util.List;

/**
 * A service that manage the storage
 *
 * @author OpenCollab
 * @version 1.0.0
 */
public interface StorageService {
    /**
     * Return the file handler for the file.
     *
     * @param file The file.
     * @return The handler if found or null.
     */
    public StorageFileHandler getFileHandler(File file);
    /**
     * Get the StorageFileReader for the given file.
     *
     * @param file Some existing file.
     * @return The StorageFileReader if the file exist, otherwise null.
     */
    public StorageFileReader getStorageFileReader(File file);
    /**
     * Get the StorageFileWriter, retrieved from the storageFileHandler
     * used in this service, for the given file.
     * <p>
     * Note the file will be created.
     *
     * @param file Some none existing file.
     * @return The StorageFileWriter.
     */
    public StorageFileWriter getStorageFileWriter(File file);
    /**
     * Get the StorageMemoryWriter, retrieved from the storageFileHandler
     * used in this service.
     *
     * @return The StorageMemoryWriter.
     */
    public StorageMemoryWriter getStorageMemoryWriter();
    /**
     * Retrieve the contents of a file relative to the root file.
     *
     * @param root The root file.
     * @param fileName The file name relative to the root file.
     * @return The contents.
     * @throws coza.opencollab.synthesis.SynthesisException If the file does not exist or if there is a problem reading the file.
     */
    public String getFileContents(File root, String fileName) throws SynthesisException;
    /**
     * Retrieve the base directory file. The directory will exist.
     *
     * @return The File object pointing the the base directory.
     */
    public File getBaseStorageDirectory();
    /**
     * Retrieve a file object that points to a existing directory.
     * The directory will be set depending on the key.
     *
     * @param key The key to the directory.
     * @return a {@link java.io.File} object.
     */
    public File getStorageDirectory(String key);
    /**
     * Retrieve a file object that points to a existing temp directory.
     * The directory will be set depending on the key.
     *
     * @param key The key to the directory.
     * @return a {@link java.io.File} object.
     */
    public File getTempDirectory(String key);
    /**
     * Create a temp directory that no one else will use.
     *
     * @param key The key to the directory.
     * @return A existing directory for temp work.
     */
    public File createTempDirectory(String key);
    /**
     * Build a file path for the directories.
     * <p>
     * This method will handle the path separators and the like.
     *
     * @param directories The directories in order.
     * @return The path not starting or ending with a separator. Or a empty string if directories is empty.
     */
    public String getDirectoryPath(String...directories);
    /**
     * Build a file path for the directories and file name.
     * <p>
     * This method will handle the path separators and the like.
     *
     * @param fileName The file name.
     * @param directories The directories in order.
     * @return The path not starting with a separator.
     */
    public String getFilePath(String fileName, String...directories);
    /**
     * Get the file object for the given parameters. This file object will
     * point to a valid directory. If the directory does not exist it will be created.
     *
     * @param key The key to the directory.
     * @param directory The directory within the storage directory.
     * @return The file object pointing to a directory.
     */
    public File getStorageDirectory(String key, String directory);
    /**
     * Get the file object for the given parameters. This file object will
     * point to a valid temp directory. If the directory does not exist it will be created.
     *
     * @param key The key to the directory.
     * @param directory The directory within the storage directory.
     * @return The file object pointing to a temp directory.
     */
    public File getTempDirectory(String key, String directory);
    /**
     * Get the file object for the given parameters. This file object will
     * point to a file (may not exist) with a valid directory. If the directory
     * does not exist it will be created.
     *
     * @param key The key to the directory.
     * @param fileName The file name.
     * @return The file object pointing to a file.
     */
    public File getStorageFile(String key, String fileName);
    /**
     * Get the file object for the given parameters. This file object will
     * point to a file (may not exist) with a valid directory. If the directory
     * does not exist it will be created.
     *
     * @param key The key to the directory.
     * @param directory The file directory within the storage directory.
     * @param fileName The file name.
     * @return The file object pointing to a file.
     */
    public File getStorageFile(String key, String directory, String fileName);
    /**
     * Store the file for later download.
     *
     * @param key The directory key.
     * @param directory The directory the file
     * @param fileName The file name.
     * @return A key that can be used to download this file later.
     * @param data an array of byte.
     */
    public String store(String key, String directory, String fileName, byte[] data);
    /**
     * Store the file for later download.
     *
     * @param key The directory key.
     * @param entry The file entry.
     * @return A key that can be used to download this file later.
     */
    public String store(String key, StorageEntry entry);
    /**
     * Store the URL as a file for later download.
     *
     * @param key The directory key.
     * @param entry The URL entry.
     * @return A key that can be used to download this file later.
     */
    public String store(String key, URLEntry entry);    
    /**
     * Store the files for later download. The download will be a compressed archive.
     *
     * @param key The directory key.
     * @param entries The file entries.
     * @return All the keys that can be used to download the files later.
     */
    public List<String> store(String key, List<StorageEntry> entries);
    /**
     * Store the files for later download.
     *
     * @param key The directory key.
     * @param entries The file entries.
     * @param name The name for the combined entries.
     * @return A key that can be used to download the files later as one.
     */
    public String store(String key, String name, List<StorageEntry> entries);
    /**
     * Get the download key for the file if it is in the storage services base.
     *
     * @param file The file.
     * @return The download key that can be used to retrieve the file later.
     */
    public String getDownloadKey(File file);
    /**
     * Retrieve the file for the download key.
     *
     * @param downloadKey The key returned from a store method call.
     * @param compressed Whether to compress the file of not.
     * @return The file pointing to the relative file depending on
     * the download key. Could be null if the file does not exist.
     * @throws coza.opencollab.synthesis.SynthesisException If the file could not be compressed/decompressed.
     */
    public File getDownloadFile(String downloadKey, boolean compressed) throws SynthesisException;
    /**
     * Retrieve the file stream for the download key.
     *
     * @param downloadKey The key returned from a store method call.
     * @return The file stream pointing to the relative file depending on
     * the download key. Could be null if the file does not exist.
     * @throws coza.opencollab.synthesis.SynthesisException If the file could not be found
     */
    public FileInputStream getDownloadFileStream(String downloadKey) throws SynthesisException;    
    /**
     * Retrieve the file name for the download key.
     *
     * @param downloadKey The key returned from a store method call.
     * @return The file name pointing to the relative file depending on
     * the download key. Could be null if the file does not exist.
     */
    public String getDownloadFileName(String downloadKey);        
    /**
     * Copy the source to the destination for storage.
     * <p>
     * The source will be saved in the format dictated by the storageFileHandler.
     *
     * @param source The source file.
     * @param destination The destination file.
     * @throws coza.opencollab.synthesis.SynthesisException If the copy fails.
     */
    public void copyToStorage(File source, File destination) throws SynthesisException;
    /**
     * Copy the source to the destination directory.
     *
     * @param source The source file.
     * @param destination The destination directory.
     * @throws coza.opencollab.synthesis.SynthesisException If the copy fails.
     */
    public void copyToDirectory(File source, File destination) throws SynthesisException;
}
