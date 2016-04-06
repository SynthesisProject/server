package coza.opencollab.synthesis.service.api.dbo;

import coza.opencollab.synthesis.service.api.Defaults;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Version;

/**
 * This dbo represent the MANAGED_MODULE table.
 *
 * @author OpenCollab
 * @since 1.0.0
 * @version 1.0.0
 */
@Entity
@Table(name = "MANAGED_MODULE")
@NamedQueries({
    @NamedQuery(
            name = "managedModules",
            query = "SELECT mm FROM ManagedModule mm WHERE mm.active = TRUE"
    ),
    @NamedQuery(
            name = "inactiveManagedModules",
            query = "SELECT mm FROM ManagedModule mm WHERE mm.active = FALSE"
    ),
    @NamedQuery(
            name = "managedModule",
            query = "SELECT mm FROM ManagedModule mm WHERE mm.moduleId = :" + Defaults.PARAMETER_MODULE_ID
    ),
    @NamedQuery(
            name = "managedModuleById",
            query = "SELECT mm FROM ManagedModule mm WHERE mm.id = :id"
    ),
    @NamedQuery(
            name = "updateManagedModule",
            query = "UPDATE ManagedModule mm SET "
            + "mm.moduleId = :" + Defaults.PARAMETER_MODULE_ID + " "
            + ",mm.lastSync = :lastSync "
            + ",mm.active = :active "
            + ",mm.lastUpdated = :lastUpdated "
            + ",mm.repository = :repository "
            + "WHERE mm.id = :id"
    ),
    @NamedQuery(
            name = "deleteManagedModuleById",
            query = "DELETE FROM ManagedModule mm WHERE mm.id = :id"
    )
})
public class ManagedModule implements Serializable {

    private static final long serialVersionUID = 245678987654L;
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
     * The module name.
     */
    private String moduleName;
    /**
     * The last sync time.
     */
    private Timestamp lastSync;
    /**
     * Active indicator.
     */
    private boolean active;
    /**
     * The last updated date and time. Used as a optimistic lock by jpa
     */
    @Version
    private Timestamp lastUpdated;

    /**
     * The repository type - ie. Sakai
     */
    private String repository;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.REFRESH)
    @JoinTable(name = "module_tools", joinColumns = {
        @JoinColumn(name = "module_id", nullable = false, updatable = false)},
            inverseJoinColumns = {
                @JoinColumn(name = "tool_id",
                        nullable = false, updatable = false)})
    private List<Tool> tools;

    /**
     * <p>
     * Constructor for ManagedModule.</p>
     */
    public ManagedModule() {
        this.tools = new ArrayList<>(0);
    }

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
     * The module id.
     *
     * @return a {@link java.lang.String} object.
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
     * The module name.
     *
     * @return a {@link java.lang.String} object.
     */
    public String getModuleName() {
        return moduleName;
    }

    /**
     * The module name.
     *
     * @param moduleName a {@link java.lang.String} object.
     */
    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    /**
     * The last sync time.
     *
     * @return a {@link java.sql.Timestamp} object.
     */
    public Timestamp getLastSync() {
        return lastSync == null ? null : new Timestamp(lastSync.getTime());
    }

    /**
     * The last sync time.
     *
     * @param lastSync a {@link java.sql.Timestamp} object.
     */
    public void setLastSync(Timestamp lastSync) {
        this.lastSync = lastSync == null ? null : new Timestamp(lastSync.getTime());
    }

    /**
     * Update the last sync time.
     */
    public void setLastSync() {
        lastSync = new Timestamp(System.currentTimeMillis());
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
     * The last updated date and time. Used as a optimistic lock by jpa
     *
     * @return a {@link java.sql.Timestamp} object.
     */
    public Timestamp getLastUpdated() {
        return lastUpdated == null ? null : new Timestamp(lastUpdated.getTime());
    }

    /**
     * The last updated date and time. Used as a optimistic lock by jpa
     *
     * @param lastUpdated a {@link java.sql.Timestamp} object.
     */
    public void setLastUpdated(Timestamp lastUpdated) {
        this.lastUpdated = lastUpdated == null ? null : new Timestamp(lastUpdated.getTime());
    }

    /**
     * Update the lastUpdated value. This method is called by jpa before persist
     * and update
     */
    @PrePersist
    @PreUpdate
    public void setLastUpdated() {
        lastUpdated = new Timestamp(System.currentTimeMillis());
    }

    /**
     * <p>
     * Getter for the field <code>repository</code>.</p>
     *
     * @return the repository
     */
    public String getRepository() {
        return repository;
    }

    /**
     * <p>
     * Setter for the field <code>repository</code>.</p>
     *
     * @param repository the repository to set
     */
    public void setRepository(String repository) {
        this.repository = repository;
    }

    /**
     * <p>
     * Getter for the field <code>tools</code>.</p>
     *
     * @return a {@link java.util.List} object.
     */
    public List<Tool> getTools() {
        return this.tools;
    }

    /**
     * <p>
     * Setter for the field <code>tools</code>.</p>
     *
     * @param tools a {@link java.util.List} object.
     */
    public void setTools(List<Tool> tools) {
        this.tools = tools;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "ManagedModule{" + "id=" + id + ", moduleId=" + moduleId + ", moduleName=" + moduleName + ", lastSync=" + lastSync + ", active=" + active + ", lastUpdated=" + lastUpdated + ", repository=" + repository + ", tools=" + tools + ", toolSize=" + tools.size() + '}';
    }

}
