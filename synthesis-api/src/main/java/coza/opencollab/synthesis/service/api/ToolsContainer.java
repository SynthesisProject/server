package coza.opencollab.synthesis.service.api;

import coza.opencollab.synthesis.service.api.dbo.Tool;
import java.util.List;

/**
 * <p>ToolsContainer class.</p>
 *
 * @author OpenCollab
 * @version 1.0.0
 */
public class ToolsContainer {
    private List<Tool> tools;

    /**
     * <p>Getter for the field <code>tools</code>.</p>
     *
     * @return a {@link java.util.List} object.
     */
    public List<Tool> getTools() {
        return tools;
    }

    /**
     * <p>Setter for the field <code>tools</code>.</p>
     *
     * @param tools a {@link java.util.List} object.
     */
    public void setTools(List<Tool> tools) {
        this.tools = tools;
    }
}
