package coza.opencollab.synthesis.service.synch.so;

import coza.opencollab.synthesis.service.report.so.Status;
import coza.opencollab.synthesis.service.api.ServiceObject;
import java.util.HashMap;
import java.util.Map;
import org.codehaus.jackson.annotate.JsonIgnore;

/**
 * The service object for synch status checks.
 *
 * @author OpenCollab
 * @since 1.0.0
 * @version 1.0.1
 */
public class SynchStatus extends ServiceObject{
    /**
     * The module code
     */
    private String moduleId;
    /**
     * The tool statuses.
     */
    private Map<String, Status> toolStatuses;

    /**
     * The module code
     *
     * @return a {@link java.lang.String} object.
     */
    public String getModuleId() {
        return moduleId;
    }

    /**
     * The module code
     *
     * @param moduleId a {@link java.lang.String} object.
     */
    public void setModuleId(String moduleId) {
        this.moduleId = moduleId;
    }

    /**
     * The tool statuses.
     *
     * @return a {@link java.util.Map} object.
     */
    public Map<String, Status> getTools() {
        return toolStatuses;
    }

    /**
     * The tool statuses.
     *
     * @param toolStatuses a {@link java.util.Map} object.
     */
    public void setTools(Map<String, Status> toolStatuses) {
        this.toolStatuses = toolStatuses;
    }
    
    /**
     * Add a tool status.
     *
     * @param toolName a {@link java.lang.String} object.
     * @param toolStatus a {@link coza.opencollab.synthesis.service.report.so.Status} object.
     */
    @JsonIgnore
    public void addToolStatus(String toolName, Status toolStatus){
        if(toolStatuses == null){
            toolStatuses = new HashMap<String, Status>();
        }
        toolStatuses.put(toolName, toolStatus);
    }
}
