package coza.opencollab.synthesis.service.api.content;

import coza.opencollab.synthesis.service.api.util.StorageEntry;
import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * Convert tool content to Synthesis client content format.
 *
 * @author Opencollab
 * @since 1.0.0
 * @version 1.0.0
 */
public interface ContentConverter <T, U> {
    /**
     * Convert the data of the tool to Storage entries.
     *
     * @param moduleId The module id.
     * @param data The data of the tool.
     * @return A list of entries from the data.
     */
    public List<StorageEntry> convert(String moduleId, T data);
    /**
     * Convert the data of the user to Storage entries.
     *
     * @param moduleId The module id.
     * @param data The data of the user.
     * @return A list of entries from the data.
     */
    public List<StorageEntry> convertUserData(String moduleId, U data);
    /**
     * Whether there are extra resources for this tool
     * not saved in the normal data.
     *
     * @param moduleId The module id.
     * @return true if there are extra resources.
     */
    public boolean hasExtraResources(String moduleId);
    /**
     * Retrieve the extra resources for this tool, if any,
     * not saved in the normal data.
     *
     * @param moduleId The module id.
     * @return The extra resources if any. The key is the file directory. The value is the actual file.
     */
    public Map<String, File> getExtraResources(String moduleId);
}
