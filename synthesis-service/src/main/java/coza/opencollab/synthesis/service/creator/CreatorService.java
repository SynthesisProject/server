package coza.opencollab.synthesis.service.creator;

import coza.opencollab.synthesis.shared.Tool;
import java.io.FileInputStream;
import java.util.List;

/**
 * This is the API for all the Synthesis Creator services.
 * <p>
 * This will include retrieving of data and the creation of clients.
 *
 * @author OpenCollab
 * @since 1.0.0
 * @version 1.0.1
 */
public interface CreatorService {
    /**
     * Retrieves all the tools for the given module.
     *
     * @param moduleId The module id.
     * @return All the tools for the module.
     */
    public List<Tool> getTools(String moduleId);
    /**
     * Returns the file for the given download key.
     *
     * @param downloadKey The the download key.
     * @return The file object. Could be null if the file does not exist.
     */
    public FileInputStream getDownloadFileStream(String downloadKey);    
    /**
     * Adds an entry to the Managed modules and adds a schedule task to
     * load content
     *
     * @param moduleId The module id to add to content process
     */
    public void addManagedModule(String moduleId);
}
