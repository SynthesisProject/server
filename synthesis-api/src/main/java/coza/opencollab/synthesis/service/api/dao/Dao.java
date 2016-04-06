package coza.opencollab.synthesis.service.api.dao;

import coza.opencollab.synthesis.SynthesisException;
import coza.opencollab.synthesis.service.api.dbo.ContentVersion;
import coza.opencollab.synthesis.service.api.dbo.ManagedModule;
import coza.opencollab.synthesis.service.api.dbo.ModuleTools;
import coza.opencollab.synthesis.service.api.dbo.Tool;
import java.io.Serializable;
import java.util.List;

/**
 * This dao manages the registration process.
 *
 * @author OpenCollab
 * @since 1.0.0
 * @version 1.0.0
 */
public interface Dao {

    //----- Mics -----//
    /**
     * Updates/merges the entity to the persistence.
     *
     * @param entity The entity to update.
     * @return The updated entity
     * @param <E> a E object.
     */
    public <E extends Serializable> E update(E entity);

    //----- ManagedModule -----//
    /**
     * Create the manage module record for the module id.
     *
     * @param moduleId The module id.
     * @return The manage module.
     */
    public ManagedModule createManagedModule(String moduleId);

    /**
     * Retrieve all the active managed modules.
     *
     * @return All active managed modules.
     */
    public List<ManagedModule> getManagedModules();
    
    /**
     * Retrieve all inactive managed modules.
     *
     * @return All inactive managed modules.
     */
    public List<ManagedModule> getInactiveManagedModules();    

    /**
     * Retrieve a existing managed module if it exist. Note this may be active
     * or inactive.
     *
     * @param moduleId The module id.
     * @return The manage module or null if it does not exist.
     */
    public ManagedModule getManagedModule(String moduleId);

    /**
     * Retrieve a existing managed module if it exist. Note this may be active
     * or inactive.
     *
     * @return The manage module or null if it does not exist.
     * @param id a {@link java.lang.Long} object.
     */
    public ManagedModule getManagedModuleById(Long id);

    /**
     * Update a managed module
     *
     * @param mm a {@link coza.opencollab.synthesis.service.api.dbo.ManagedModule} object.
     */
    public void setManagedModule(ManagedModule mm);

    /**
     * Delete a managed module item.
     *
     * @param id a {@link java.lang.Long} object.
     */
    public void deleteManagedModule(Long id);

    //----- ContentVersion -----//
    /**
     * Create the content version record for the registration.
     *
     * @param managedModule a {@link coza.opencollab.synthesis.service.api.dbo.ManagedModule} object.
     * @param toolName The tool name.
     * @param version The new version of the content.
     * @return The content version.
     */
    public ContentVersion createContentVersion(ManagedModule managedModule, String toolName, String version);

    /**
     * Retrieve all existing content versions.
     *
     * @return The content all versions
     */
    public List<ContentVersion> getAllContentVersions();

    /**
     * Retrieve a existing active content version if it exist.
     *
     * @param managedModule a {@link coza.opencollab.synthesis.service.api.dbo.ManagedModule} object.
     * @param toolName The tool name.
     * @return The content version or null if it does not exist.
     */
    public ContentVersion getContentVersion(ManagedModule managedModule, String toolName);

    /**
     * Retrieve a specific content version if it exist. Note this may be active
     * or inactive.
     *
     * @param managedModule a {@link coza.opencollab.synthesis.service.api.dbo.ManagedModule} object.
     * @param toolName The tool name.
     * @param contentVersion The content version.
     * @return The content version or null if it does not exist.
     */
    public ContentVersion getContentVersion(ManagedModule managedModule, String toolName, String contentVersion);

    /**
     * Retrieve a content version that is either the specified version or the
     * closest one before this, if it exist. Note this may be active or
     * inactive.
     *
     * @param managedModule a {@link coza.opencollab.synthesis.service.api.dbo.ManagedModule} object.
     * @param toolName The tool name.
     * @param contentVersion The content version.
     * @return The content version or null if it does not exist.
     */
    public ContentVersion getContentVersionCloseTo(ManagedModule managedModule, String toolName, String contentVersion);

    /**
     * Retrieve the content version for the user's device.
     *
     * @param username The username.
     * @param moduleId The module id.
     * @param toolName The tool name.
     * @return The content version.
     * @throws coza.opencollab.synthesis.SynthesisException if the device registration, module
     * registration or content version cannot be found.
     */
    public ContentVersion getContentVersionWithNullCheck(String username, String moduleId, String toolName) throws SynthesisException;

    /**
     * Retrieve the master content version.
     *
     * @param moduleId The module id.
     * @param toolName The tool name.
     * @return the content version.
     * @throws coza.opencollab.synthesis.SynthesisException if the device registration, module
     * registration or content version cannot be found.
     */
    public ContentVersion getMasterContentVersion(String moduleId, String toolName) throws SynthesisException;

    /**
     * Retrieve the active content versions for the module registration.
     *
     * @param managedModule a {@link coza.opencollab.synthesis.service.api.dbo.ManagedModule} object.
     * @return The content versions.
     */
    public List<ContentVersion> getContentVersions(ManagedModule managedModule);

    /**
     * Retrieve all the user's device content versions.
     *
     * @param username The username.
     * @param moduleId The module id.
     * @return A list of content versions.
     * @throws coza.opencollab.synthesis.SynthesisException if the device registration or module
     * registration cannot be found.
     */
    public List<ContentVersion> getContentVersionsWithNullCheck(String username, String moduleId) throws SynthesisException;

    /**
     * Retrieve all the master content versions.
     *
     * @param moduleId The module id.
     * @return A list of content versions.
     * @throws coza.opencollab.synthesis.SynthesisException if the device registration or module
     * registration cannot be found.
     */
    public List<ContentVersion> getMasterContentVersions(String moduleId) throws SynthesisException;

    /**
     * Retrieve all the content versions for the tool and module.
     *
     * @param managedModule a {@link coza.opencollab.synthesis.service.api.dbo.ManagedModule} object.
     * @param toolName The tool name.
     * @return The list of content versions in order from oldest to newest.
     */
    public List<ContentVersion> getContentVersions(ManagedModule managedModule, String toolName);

    /**
     * Retrieve all the content versions for the tool and module from the
     * <code>fromContentVersion</code> exclusive to the
     * <code>toContentVersion</code> inclusive.
     *
     * @param managedModule a {@link coza.opencollab.synthesis.service.api.dbo.ManagedModule} object.
     * @param toolName The tool name.
     * @param fromContentVersion The from content version, exclusive.
     * @param toContentVersion The content version, inclusive.
     * @return The list of content versions in order from oldest to newest.
     */
    public List<ContentVersion> getContentVersions(ManagedModule managedModule, String toolName, String fromContentVersion, String toContentVersion);

    /**
     * Deactivate all content versions for the device, module and tool name.
     *
     * @param moduleRegistrationId The module registration id.
     * @param toolName The tool name.
     * @return The number of content versions updated.
     */
    public int deactivateAllContentVersions(Long moduleRegistrationId, String toolName);

    /**
     * Deactivate all content versions for the device, module and tool name
     * other then this content version.
     *
     * @param contentVersion The content version not to deactivate.
     * @return The number of content versions updated.
     */
    public int deactivateAllOther(ContentVersion contentVersion);

    /**
     * Set the content version as inactive.
     *
     * @param contentVersion The content version to deactivate, could be null.
     */
    public void deactivate(ContentVersion contentVersion);

    /**
     * Delete the content version.
     *
     * @param managedModule a {@link coza.opencollab.synthesis.service.api.dbo.ManagedModule} object.
     */
    public void deleteContentVersion(ManagedModule managedModule);

    /**
     * Get all the tools available
     *
     * @return a {@link java.util.List} object.
     */
    public List<Tool> getTools();

    /**
     * Create tool
     *
     * @param tool a {@link coza.opencollab.synthesis.service.api.dbo.Tool} object.
     * @return a {@link coza.opencollab.synthesis.service.api.dbo.Tool} object.
     */
    public Tool createTool(Tool tool);
    
    /**
     * <p>deleteTool.</p>
     *
     * @param tool a {@link coza.opencollab.synthesis.service.api.dbo.Tool} object.
     */
    public void deleteTool(Tool tool);
    
    /**
     * Update the tool, when given an existing tool
     *
     * @param tool a {@link coza.opencollab.synthesis.service.api.dbo.Tool} object.
     */
    public void setTool(Tool tool);
    /**
     * Get all ModuleTool data
     *
     * @return a {@link java.util.List} object.
     */
    public List<ModuleTools> getModuleTools();
    
    /**
     * Get all ModuleTool data
     *
     * @param id a {@link java.lang.Long} object.
     * @return a {@link coza.opencollab.synthesis.service.api.dbo.Tool} object.
     */
    public Tool getToolById(Long id);
    
    /**
     * Get all tools related to a ManagedModule when given the ManagedModule.id
     *
     * @param moduleId a {@link java.lang.Long} object.
     * @return a {@link java.util.List} object.
     */
    public List<ModuleTools> getModuleToolsPerModule(Long moduleId);
    
    /**
     * When given the ManagedModule.id and the Tool.id delete the record that links these objects.
     *
     * @param moduleId a {@link java.lang.Long} object.
     * @param toolId a {@link java.lang.Long} object.
     * @return a int.
     */
    public int deleteModuleTool(Long moduleId, Long toolId) ;
}
