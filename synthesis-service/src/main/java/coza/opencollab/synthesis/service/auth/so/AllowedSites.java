package coza.opencollab.synthesis.service.auth.so;

import coza.opencollab.synthesis.service.api.ServiceCallStatus;
import coza.opencollab.synthesis.service.api.ServiceObject;
import coza.opencollab.synthesis.shared.Module;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * The service object for client allowed sites
 *
 * @author OpenCollab
 * @since 1.0.0
 * @version 1.0.1
 */
public class AllowedSites extends ServiceObject implements Serializable{
    
    /**
     * The list of sites that is allowed 
     */
    private List<Module> modules;
    
    /**
     * Default constructor
     */
    public AllowedSites(){}
    
    /**
     * Constructor setting the status.
     *
     * @param statusCode a {@link coza.opencollab.synthesis.service.api.ServiceCallStatus} object.
     */
    public AllowedSites(ServiceCallStatus statusCode){
        super(statusCode);
    }
    
    /**
     * Copy constructor
     *
     * @param other a {@link coza.opencollab.synthesis.service.api.ServiceObject} object.
     */
    public AllowedSites(ServiceObject other){
        super(other);
    }
    
    /**
     * Adds a single Module to the list of allowed modules
     *
     * @param module a {@link coza.opencollab.synthesis.shared.Module} object.
     */
    public void addModule(Module module) {
        if (modules == null) {
            modules = new ArrayList();
        }
        modules.add(module);
    }
    
    /**
     * <p>Getter for the field <code>modules</code>.</p>
     *
     * @return the modules
     */
    public List<Module> getModules() {
        return modules;
    }

    /**
     * <p>Setter for the field <code>modules</code>.</p>
     *
     * @param modules the modules to set
     */
    public void setModules(List<Module> modules) {
        this.modules = modules;
    }

}
