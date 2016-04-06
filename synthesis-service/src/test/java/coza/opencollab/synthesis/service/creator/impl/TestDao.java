package coza.opencollab.synthesis.service.creator.impl;

import coza.opencollab.synthesis.SynthesisException;
import coza.opencollab.synthesis.service.api.dao.Dao;
import coza.opencollab.synthesis.service.api.dbo.ContentVersion;
import coza.opencollab.synthesis.service.api.dbo.ManagedModule;
import coza.opencollab.synthesis.service.api.dbo.ModuleTools;
import coza.opencollab.synthesis.service.api.dbo.Tool;

import java.io.Serializable;
import java.util.List;

/**
 * A test implementation of the RegistrationsDao
 * 
 * @author OpenCollab
 * @since 1.0.0
 */
public class TestDao implements Dao{
    private String currentToolVersion = "1.0.0";

    public String getCurrentToolVersion() {
        return currentToolVersion;
    }

    public void setCurrentToolVersion(String currentToolVersion) {
        this.currentToolVersion = currentToolVersion;
    }

    @Override
    public ManagedModule getManagedModule(String moduleId) {
        ManagedModule managedModule = new ManagedModule();
        managedModule.setModuleId(moduleId);
        managedModule.setActive(true);
        return managedModule;
    }

    @Override
    public ManagedModule createManagedModule(String moduleId) {
        return getManagedModule(moduleId);
    }

    @Override
    public void deactivate(ContentVersion contentVersion) {
        if(contentVersion != null){
            contentVersion.setActive(false);
        }
    }

    @Override
    public List<ContentVersion> getContentVersions(ManagedModule managedModule) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<ManagedModule> getManagedModules() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<ManagedModule> getInactiveManagedModules() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    @Override
    public ContentVersion getContentVersionWithNullCheck(String username, String moduleId, String toolName) throws SynthesisException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public ContentVersion getMasterContentVersion(String moduleId, String toolName) throws SynthesisException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<ContentVersion> getContentVersionsWithNullCheck(String username, String moduleId) throws SynthesisException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<ContentVersion> getMasterContentVersions(String moduleId) throws SynthesisException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<ContentVersion> getContentVersions(ManagedModule managedModule, String toolName) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public <E extends Serializable> E update(E entity) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public ContentVersion getContentVersionCloseTo(ManagedModule managedModule, String toolName, String contentVersion) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public int deactivateAllOther(ContentVersion contentVersion) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int deactivateAllContentVersions(Long moduleRegistrationId, String toolName) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ContentVersion createContentVersion(ManagedModule managedModule, String toolName, String version) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ContentVersion getContentVersion(ManagedModule managedModule, String toolName) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ContentVersion getContentVersion(ManagedModule managedModule, String toolName, String contentVersion) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<ContentVersion> getContentVersions(ManagedModule managedModule, String toolName, String fromContentVersion, String toContentVersion) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Tool> getTools() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void deleteManagedModule(Long id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<ContentVersion> getAllContentVersions() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

	@Override
	public ManagedModule getManagedModuleById(Long arg0) {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public void setManagedModule(ManagedModule arg0) {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

    @Override
    public List<ModuleTools> getModuleTools() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void deleteContentVersion(ManagedModule managedModule) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Tool getToolById(Long id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<ModuleTools> getModuleToolsPerModule(Long moduleId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int deleteModuleTool(Long moduleId, Long toolId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Tool createTool(Tool tool) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setTool(Tool tool) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void deleteTool(Tool tool) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
