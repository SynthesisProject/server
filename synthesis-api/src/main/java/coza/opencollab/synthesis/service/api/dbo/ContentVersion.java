package coza.opencollab.synthesis.service.api.dbo;

import coza.opencollab.synthesis.service.api.Defaults;
import java.io.Serializable;
import java.sql.Timestamp;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Version;

/**
 * This is a representation of table CONTENT_VERSION
 *
 * @author OpenCollab
 * @since 1.0.0
 * @version 1.0.0
 */
@Entity
@Table(name = "CONTENT_VERSION")
@NamedQueries({
    @NamedQuery(
        name="contentVersionActive",
        query="SELECT c FROM ContentVersion c WHERE c.active = TRUE AND c.moduleRegistrationId = :" +
        Defaults.PARAMETER_MODULE_REGISTRATION_ID + " AND c.toolName = :" + Defaults.PARAMETER_TOOL_NAME
    ),
    @NamedQuery(
        name="contentVersion",
        query="SELECT c FROM ContentVersion c WHERE c.moduleRegistrationId = :" +
        Defaults.PARAMETER_MODULE_REGISTRATION_ID + " AND c.toolName = :" +
        Defaults.PARAMETER_TOOL_NAME + " AND c.contentVersion = :" + ContentVersion.PARAMETER_CONTENT_VERSION
    ),
    @NamedQuery(
        name="contentVersionCloseTo",
        query="SELECT c FROM ContentVersion c WHERE c.moduleRegistrationId = :" +
        Defaults.PARAMETER_MODULE_REGISTRATION_ID + " AND c.toolName = :" +
        Defaults.PARAMETER_TOOL_NAME + " AND c.contentVersion <= :" +
        ContentVersion.PARAMETER_CONTENT_VERSION + " ORDER BY c.contentVersion DESC"
    ),
    @NamedQuery(
        name="contentVersions",
        query="SELECT c FROM ContentVersion c WHERE c.active = TRUE AND c.moduleRegistrationId = :" + Defaults.PARAMETER_MODULE_REGISTRATION_ID
    ),
    @NamedQuery(
        name="contentVersionsForTool",
        query="SELECT c FROM ContentVersion c WHERE c.moduleRegistrationId = :" +
        Defaults.PARAMETER_MODULE_REGISTRATION_ID + " AND c.toolName = :" +
        Defaults.PARAMETER_TOOL_NAME + " ORDER BY c.contentVersion ASC"
    ),
    @NamedQuery(
        name="contentVersionRange",
        query="SELECT c FROM ContentVersion c WHERE c.moduleRegistrationId = :" +
        Defaults.PARAMETER_MODULE_REGISTRATION_ID + " AND c.toolName = :" +
        Defaults.PARAMETER_TOOL_NAME + " AND c.contentVersion > :versionFrom AND c.contentVersion <= :versionTo ORDER BY c.contentVersion ASC"
    ),
    @NamedQuery(
        name="contentVersionDeactivate",
        query="UPDATE ContentVersion c SET c.active = FALSE WHERE c.moduleRegistrationId = :" +
        Defaults.PARAMETER_MODULE_REGISTRATION_ID + " AND c.toolName = :" +
        Defaults.PARAMETER_TOOL_NAME + " AND c.contentVersion <> :" +
        ContentVersion.PARAMETER_CONTENT_VERSION
    ),
    @NamedQuery(
        name="contentVersionDeactivateAll",
        query="UPDATE ContentVersion c SET c.active = FALSE WHERE c.moduleRegistrationId = :" +
        Defaults.PARAMETER_MODULE_REGISTRATION_ID + " AND c.toolName = :" +
        Defaults.PARAMETER_TOOL_NAME
    ),
    @NamedQuery(
        name="contentVersionAll",
        query="SELECT DISTINCT c FROM ContentVersion c " +
        " ORDER BY c.contentVersion DESC"
    )
})
public class ContentVersion implements Serializable{
    private static final long serialVersionUID = -871287234347386182L;
    /**
     * The query parameter.
     */
    public static final String PARAMETER_CONTENT_VERSION = "contentVersion";
    /**
     * The id of the record
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    /**
     * The module registration id
     */
    private Long moduleRegistrationId;
    /**
     * The tool name
     */
    private String toolName;
    /**
     * The content version
     */
    private String contentVersion;
    /**
     * Active indicator.
     */
    private boolean active;
    /**
     * The last updated date and time.
     * Used as a optimistic lock by jpa
     */
    @Version
    private Timestamp lastUpdated;

    /**
     * The id of the record
     *
     * @return a {@link java.lang.Long} object.
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
     * The reference to the module registration table
     *
     * @return a {@link java.lang.Long} object.
     */
    public Long getModuleRegistrationId() {
        return moduleRegistrationId;
    }

    /**
     * The reference to the module registration table
     *
     * @param moduleRegistrationId a {@link java.lang.Long} object.
     */
    public void setModuleRegistrationId(Long moduleRegistrationId) {
        this.moduleRegistrationId = moduleRegistrationId;
    }

    /**
     * The tool name
     *
     * @return a {@link java.lang.String} object.
     */
    public String getToolName() {
        return toolName;
    }

    /**
     * The tool name
     *
     * @param toolName a {@link java.lang.String} object.
     */
    public void setToolName(String toolName) {
        this.toolName = toolName;
    }

    /**
     * The content version
     *
     * @return a {@link java.lang.String} object.
     */
    public String getContentVersion() {
        return contentVersion;
    }

    /**
     * The content version
     *
     * @param contentVersion a {@link java.lang.String} object.
     */
    public void setContentVersion(String contentVersion) {
        this.contentVersion = contentVersion;
    }

    /**
     * Active indicator.
     *
     * @return a boolean.
     */
    public boolean isActive() {
        return active;
    }

    /**
     * Active indicator.
     *
     * @param active a boolean.
     */
    public void setActive(boolean active) {
        this.active = active;
    }

    /**
     * The last updated date and time.
     * Used as a optimistic lock by jpa
     *
     * @return a {@link java.sql.Timestamp} object.
     */
    public Timestamp getLastUpdated() {
        return lastUpdated==null?null:new Timestamp(lastUpdated.getTime());
    }

    /**
     * The last updated date and time.
     * Used as a optimistic lock by jpa
     *
     * @param lastUpdated a {@link java.sql.Timestamp} object.
     */
    public void setLastUpdated(Timestamp lastUpdated) {
        this.lastUpdated = lastUpdated==null?null:new Timestamp(lastUpdated.getTime());
    }
    
    /**
     * Update the lastUpdated value.
     * This method is called by jpa before persist and update
     */
    @PrePersist
    @PreUpdate
    public void setLastUpdated(){
        lastUpdated = new Timestamp(System.currentTimeMillis());
    }
}
