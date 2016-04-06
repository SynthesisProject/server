package coza.opencollab.synthesis.service.api.lms.filter;

import coza.opencollab.synthesis.shared.Tool;
import java.util.List;

/**
 * A filter class for <code>Tool</code>.
 *
 * @author OpenCollab
 * @since 1.0.0
 * @version 1.0.0
 */
public interface ToolFilter {
    /**
     * Checks whether a tool is allowed.
     *
     * @param tool The tool to check
     * @return true if the tool is allowed, otherwise false.
     */
    public boolean allow(Tool tool);
    /**
     * Filter the list, removing all the tools that is not allowed.
     *
     * @param tools List of tools to filter.
     */
    public void filter(List<Tool> tools);
}
