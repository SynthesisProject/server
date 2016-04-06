package coza.opencollab.synthesis.service.api.dbo;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * This dbo represent the TOOL table.
 *
 * @author OpenCollab
 * @since 1.0.0
 * @version 1.0.0
 */
@Entity
@Table(name = "TOOL")
@NamedQueries({
    @NamedQuery(
        name="allTools",
        query="SELECT t FROM Tool t"
    ),
    @NamedQuery(
        name="getToolById",
        query="SELECT t FROM Tool t WHERE t.id = :id"
    )
})
public class Tool implements Serializable{
    private static final long serialVersionUID = 245623490654L;
    /**
     * The id of the record
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    /**
     * The tool name.
     */
    private String name;
    /**
     * The tool title in client applications.
     */
    private String title;
    /**
     * The tool description.
     */
    private String description;
    /**
     * Indicates if the tool is displayed on client menus (might only be part of other tools like home).
     */
    private String onMenu;
    
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.REMOVE, mappedBy = "tools")
    private Set<ManagedModule> managedModules = new HashSet<>(0);

    /**
     * <p>Getter for the field <code>id</code>.</p>
     *
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * <p>Setter for the field <code>id</code>.</p>
     *
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * <p>Getter for the field <code>name</code>.</p>
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * <p>Setter for the field <code>name</code>.</p>
     *
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * <p>Getter for the field <code>title</code>.</p>
     *
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * <p>Setter for the field <code>title</code>.</p>
     *
     * @param title the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * <p>Getter for the field <code>description</code>.</p>
     *
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * <p>Setter for the field <code>description</code>.</p>
     *
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * <p>Getter for the field <code>onMenu</code>.</p>
     *
     * @return the onMenu
     */
    public String getOnMenu() {
        return onMenu;
    }

    /**
     * <p>Setter for the field <code>onMenu</code>.</p>
     *
     * @param onMenu the onMenu to set
     */
    public void setOnMenu(String onMenu) {
        this.onMenu = onMenu;
    }

	/**
	 * <p>Getter for the field <code>managedModules</code>.</p>
	 *
	 * @return a {@link java.util.Set} object.
	 */
	public Set<ManagedModule> getManagedModules() {
		return this.managedModules;
	}
 
	/**
	 * <p>Setter for the field <code>managedModules</code>.</p>
	 *
	 * @param managedModules a {@link java.util.Set} object.
	 */
	public void setManagedModules(Set<ManagedModule> managedModules) {
		this.managedModules = managedModules;
	}

    /** {@inheritDoc} */
    @Override
    public String toString() {
        return "Tool{" + "id=" + id + ", name=" + name + ", title=" + title + ", description=" + description + ", onMenu=" + onMenu +'}';
    }
        
}
