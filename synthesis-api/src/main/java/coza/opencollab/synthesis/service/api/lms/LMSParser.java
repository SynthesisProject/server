package coza.opencollab.synthesis.service.api.lms;

import coza.opencollab.synthesis.service.api.lms.filter.ModuleFilter;
import coza.opencollab.synthesis.shared.Module;
import java.util.List;
import java.util.Map;

/**
 * A parser for data from the lms.
 *
 * @author OpenCollab
 * @since 1.0.0
 * @version 1.0.0
 */
public interface LMSParser {
    /**
     * Parse the session id.
     *
     * @param data The data from the lms.
     * @return The clean session id.
     */
    public String parseSessionId(String data);
    /**
     * Parse the user details to a map.
     *
     * @param data The data from the lms.
     * @return The map with the user details.
     */
    public Map<String, String> parseUserDetails(String data);
    /**
     * Parse the xml from the lms to modules.
     *
     * @param data The data from the lms.
     * @return A list of modules.
     */
    public List<Module> parseModules(String data);
    /**
     * Parse the xml from the lms to modules.
     *
     * @param data The data from the lms.
     * @param moduleFilter The module filter to use.
     * @return A list of modules.
     */
    public List<Module> parseModules(String data, ModuleFilter moduleFilter);
}
