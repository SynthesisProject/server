package coza.opencollab.synthesis.service.api;

import coza.opencollab.synthesis.service.api.dbo.ManagedModule;
import java.util.ArrayList;
import java.util.List;
 
/**
 * <p>ManagedModuleContainer class.</p>
 *
 * @author Samuel
 * @version 1.0.0
 */
public class ManagedModuleContainer {
 
    private List<ManagedModule> modules;

    /**
     * <p>Getter for the field <code>modules</code>.</p>
     *
     * @return a {@link java.util.List} object.
     */
    public List<ManagedModule> getModules() {
        return modules;
    }

    /**
     * <p>Setter for the field <code>modules</code>.</p>
     *
     * @param modules a {@link java.util.List} object.
     */
    public void setModules(List<ManagedModule> modules) {
        this.modules = modules;
    }
    
    /**
     * <p>Adds list to current <code>modules</code> list.</p>
     *
     * @param addModules a {@link java.util.List} object.
     */
    public void addModules(List<ManagedModule> addModules) {
        if (modules == null) {
            modules = new ArrayList<>();
        }
        modules.addAll(addModules);
    }    
 

}
