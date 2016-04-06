package coza.opencollab.synthesis.service.report.impl;

import coza.opencollab.synthesis.service.api.dao.Dao;
import coza.opencollab.synthesis.service.api.dbo.ContentVersion;
import coza.opencollab.synthesis.service.api.dbo.ManagedModule;
import coza.opencollab.synthesis.service.report.StatusService;
import coza.opencollab.synthesis.service.report.so.Status;
import coza.opencollab.synthesis.service.synch.SynchService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * The default implementation of the status service.
 *
 * @author OpenCollab
 * @since 1.0.0
 * @version 1.0.1
 */
public class DefaultStatusService implements StatusService{
    /**
     * The dao
     */
    @Autowired
    private Dao dao;
    /**
     * The synchronise service
     */
    @Autowired
    private SynchService synchService;
    
    /** {@inheritDoc} */
    @Override
    public String getCurrentContentVersion(String moduleId, String toolName){
        ContentVersion contentVersion = dao.getMasterContentVersion(moduleId, toolName);
        return contentVersion==null?null:contentVersion.getContentVersion();
    }
    
    /** {@inheritDoc} */
    @Override
    public Map<String, String> getCurrentContentVersions(String moduleId){
        List<ContentVersion> contentVersions = dao.getMasterContentVersions(moduleId);
        Map<String, String> versions = new HashMap<String, String>();
        for(ContentVersion contentVersion: contentVersions){
            versions.put(contentVersion.getToolName(), contentVersion.getContentVersion());
        }
        return versions;
    }
    
    /** {@inheritDoc} */
    @Override
    public String getClientContentVersion(String username, String moduleId, String toolName){
        ManagedModule managedModule = dao.getManagedModule(moduleId);
        ContentVersion contentVersion = dao.getContentVersion(managedModule, toolName);
        return contentVersion==null?null:contentVersion.getContentVersion();
    }
    
    /** {@inheritDoc} */
    @Override
    public Map<String, String> getClientContentVersions(String username, String moduleId){
        List<ContentVersion> contentVersions = dao.getContentVersionsWithNullCheck(username, moduleId);
        Map<String, String> versions = new HashMap<String, String>();
        for(ContentVersion contentVersion: contentVersions){
            versions.put(contentVersion.getToolName(), contentVersion.getContentVersion());
        }
        return versions;
    }
    
    /** {@inheritDoc} */
    @Override
    public Status getStatus(String username, String moduleId, String toolName){
        return synchService.getStatus(username, moduleId, toolName);
    }
}
