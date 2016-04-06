package coza.opencollab.synthesis.shared;

/**
 * This represents a module
 * 
 * @author OpenCollab
 * @since 1.0.0
 */
public class Module {
    /**
     * UID representing the Sakai site ID e.g. ff363bbb-c651-4fc6-8e1c-20f2ef37115a
     */
    private String id; 
    
    /**
     * String name of the module/sakai site. e.g. My Workspace.
     * Module naming format as per Unisa.
     * 7 chars Module Code
     * - Separator
     * 2 digit year designator
     * - Separator
     * Year course type 2 alphanumeric characters (Y1, Y2, S1, S2 )
     */
    private String name;
    /**
     * A description of the module.
     */
    private String description;
    
    /**
     * The date the site was created in milliseconds
     */
    private String createdDate;
    
    /**
     * UID representing the Sakai site ID e.g. ff363bbb-c651-4fc6-8e1c-20f2ef37115a
     */
    public String getId() {
        return id;
    }
    
    /**
     * UID representing the Sakai site ID e.g. ff363bbb-c651-4fc6-8e1c-20f2ef37115a
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * String name of the module/sakai site. e.g. My Workspace.
     * Module naming format as per Unisa.
     * 7 chars Module Code
     * - Separator
     * 2 digit year designator
     * - Separator
     * Year course type 2 alphanumeric characters (Y1, Y2, S1, S2 )
     */
    public String getName() {
        return name;
    }

    /**
     * String name of the module/sakai site. e.g. My Workspace.
     * Module naming format as per Unisa.
     * 7 chars Module Code
     * - Separator
     * 2 digit year designator
     * - Separator
     * Year course type 2 alphanumeric characters (Y1, Y2, S1, S2 )
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * A description of the module.
     */
    public String getDescription() {
        return description;
    }

    /**
     * A description of the module.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the createdDate
     */
    public String getCreatedDate() {
        return createdDate;
    }

    /**
     * @param createdDate the createdDate to set
     */
    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }
    
}
