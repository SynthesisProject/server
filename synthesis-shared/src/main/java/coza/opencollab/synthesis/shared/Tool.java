package coza.opencollab.synthesis.shared;

/**
 * Represents a tool.
 * 
 * @author OpenCollab
 * @since 1.0.0
 */
public class Tool {
    /**
     * UID representing the tool ID e.g. ff363bbb-c651-4fc6-8e1c-20f2ef37115a
     */
    private String id;
    /**
     * The tool name, something like sakai.announcements.
     */
    private String name;
    /**
     * The tool title. This is the name displayed to the user.
     */
    private String title;
    /**
     * Whether the tool is in the menu.
     */
    private boolean onMenu = true;

    /**
     * UID representing the tool ID e.g. ff363bbb-c651-4fc6-8e1c-20f2ef37115a
     */
    public String getId() {
        return id;
    }

    /**
     * UID representing the tool ID e.g. ff363bbb-c651-4fc6-8e1c-20f2ef37115a
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * The tool name, something like sakai.announcements.
     */
    public String getName() {
        return name;
    }

    /**
     * The tool name, something like sakai.announcements.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * The tool title. This is the name displayed to the user.
     */
    public String getTitle() {
        return title;
    }

    /**
     * The tool title. This is the name displayed to the user.
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Whether the tool is in the menu.
     */
    public boolean isOnMenu() {
        return onMenu;
    }

    /**
     * Whether the tool is in the menu.
     */
    public void setOnMenu(boolean onMenu) {
        this.onMenu = onMenu;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "[id="+id+",name="+name+",title="+title+"]";
    }
}
