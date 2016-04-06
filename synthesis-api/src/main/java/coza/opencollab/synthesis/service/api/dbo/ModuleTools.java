package coza.opencollab.synthesis.service.api.dbo;

import coza.opencollab.synthesis.service.api.Defaults;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * This dbo represent the MODULE_TOOLS table.
 *
 * @author OpenCollab
 * @since 1.0.0
 * @version 1.0.0
 */
@Entity
@Table(name = "MODULE_TOOLS")
@NamedQueries({
    @NamedQuery(
        name="moduleToolsPerModule",
        query="SELECT mt.toolId FROM ModuleTools mt WHERE mt.moduleId = :" + Defaults.PARAMETER_MODULE_ID
    ),
    @NamedQuery(
        name="moduleTools",
        query="SELECT distinct mt.toolId FROM ModuleTools mt"
    ),
    @NamedQuery(
        name="deleteModuleTool",
        query="DELETE FROM ModuleTools mt WHERE mt.moduleId = :moduleId and mt.toolId = :toolId"
    )
})
public class ModuleTools implements Serializable{
    private static final long serialVersionUID = 245678890654L;
    /**
     * The id of the record
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    /**
     * The module id.
     */
    private String moduleId;
    /**
     * The tool id.
     */
    private String toolId;

    /**
     * The id of the record
     *
     * @return Long id
     */
    public Long getId() {
        return id;
    }

    /**
     * The id of the record
     *
     * @param id a {@link java.lang.Long} object.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * The module id.
     *
     * @return String moduleId
     */
    public String getModuleId() {
        return moduleId;
    }

    /**
     * The module id.
     *
     * @param moduleId a {@link java.lang.String} object.
     */
    public void setModuleId(String moduleId) {
        this.moduleId = moduleId;
    }

    /**
     * The tool id.
     *
     * @return String toolId
     */
    public String getToolId() {
        return toolId;
    }

    /**
     * The tool id.
     *
     * @param toolId a {@link java.lang.String} object.
     */
    public void setToolId(String toolId) {
        this.toolId = toolId;
    }
}
