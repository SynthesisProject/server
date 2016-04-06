package coza.opencollab.synthesis.service.content.impl;

import coza.opencollab.synthesis.SynthesisException;
import coza.opencollab.synthesis.service.api.ErrorCodes;
import static coza.opencollab.synthesis.service.api.ErrorCodes.*;
import coza.opencollab.synthesis.service.api.ServiceCallStatus;
import coza.opencollab.synthesis.service.api.content.ContentLoader;
import coza.opencollab.synthesis.service.api.dao.Dao;
import coza.opencollab.synthesis.service.api.content.ContentService;
import coza.opencollab.synthesis.service.api.creator.StorageService;
import coza.opencollab.synthesis.service.api.dbo.ContentVersion;
import coza.opencollab.synthesis.service.api.dbo.ManagedModule;
import coza.opencollab.synthesis.service.api.synch.so.SynchContent;
import coza.opencollab.synthesis.service.api.synch.so.UpdateContent;
import coza.opencollab.synthesis.service.api.util.VersionComparer;
import java.util.Map;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * The code service for client code.
 *
 * @author OpenCollab
 * @since 1.0.0
 * @version 1.0.1
 */
public class DefaultContentService implements ContentService{
    /**
     * The logger.
     */
    private static final Logger LOG = Logger.getLogger(DefaultContentService.class);
    /**
     * The dao
     */
    @Autowired
    private Dao dao;
    /**
     * The storage service.
     */
    @Autowired
    protected StorageService storageService;
    /**
     * The version comparer for the content.
     */
    private VersionComparer versionComparer;
    /**
     * The content loader.
     */
    private ContentLoader contentLoader;

    /**
     * The version comparer for the content.
     *
     * @param versionComparer a {@link coza.opencollab.synthesis.service.api.util.VersionComparer} object.
     */
    public void setVersionComparer(VersionComparer versionComparer) {
        this.versionComparer = versionComparer;
    }

    /**
     * The content loader.
     *
     * @param contentLoader a {@link coza.opencollab.synthesis.service.api.content.ContentLoader} object.
     */
    public void setContentLoader(ContentLoader contentLoader) {
        this.contentLoader = contentLoader;
    }

    /** {@inheritDoc} */
    @Override
    @Transactional
    public void loadContent(String moduleId) {
        contentLoader.load(moduleId);
    }
    
    /** {@inheritDoc} */
    @Override
    @Transactional
    public SynchContent getContentSynch(String username, String moduleId, String toolName, String fromContentVersion) {
        //first, get the user specific content
        SynchContent userContent = contentLoader.getUserContent(username, moduleId, toolName, fromContentVersion);
        SynchContent toolContent = getToolContent(moduleId, toolName, fromContentVersion);
        return merge(userContent, toolContent);
    }
    
    /**
     * Get the tool content. This method does a few checks and then calls the loader.
     *
     * @param moduleId a {@link java.lang.String} object.
     * @param toolName a {@link java.lang.String} object.
     * @param fromContentVersion a {@link java.lang.String} object.
     * @return a {@link coza.opencollab.synthesis.service.api.synch.so.SynchContent} object.
     */
    @Transactional
    public SynchContent getToolContent(String moduleId, String toolName, String fromContentVersion){
        ManagedModule managedModule = dao.getManagedModule(moduleId);
        if(managedModule == null){
            //The group module synch has not registered yet.
            SynchContent content = new SynchContent(ServiceCallStatus.WARNING);
            content.setVersion("No Content");
            content.setSize(0L);
            content.setMessage("The module is not loaded at this stage, try again later.");
            return content;
        }
        ContentVersion currentContentVersion = dao.getContentVersion(managedModule, toolName);
        SynchContent content = new SynchContent(ServiceCallStatus.SUCCESS);
        if(currentContentVersion == null){
            content.setVersion("No Content");
            content.setSize(0L);
            content.setMessage("The current content version is not set.");
            return content;
        }
        content.setVersion(currentContentVersion.getContentVersion());
        if(fromContentVersion == null){
            LOG.info("The client content version is 'null', setting it to nonexisting version string.");
            fromContentVersion = contentLoader.getNonExistingVersion();
        }
        ContentVersion contentVersion = getFromContentVersionForSynch(managedModule, toolName, fromContentVersion);
        if(contentVersion == null){
            LOG.info("The client content version (" + fromContentVersion + ") does not exist for " + moduleId + ":" + toolName + ".");
            LOG.info("Will try and synch anyway!");
        }else{
            fromContentVersion = contentVersion.getContentVersion();
        }
        int compared = versionComparer.compare(currentContentVersion.getContentVersion(), fromContentVersion);
        if(compared == 0){
            content.setSize(0L);
            return content;
        }else if(compared < 0){
            content.setStatus(ServiceCallStatus.ERROR);
            content.setErrorCode(INVALID_VERSION);
            content.setMessage("The content version (" + fromContentVersion + ") is not less then the previous version (" + currentContentVersion.getContentVersion() + ").");
            return content;
        }
        return contentLoader.getSynchContent(moduleId, toolName, fromContentVersion, currentContentVersion.getContentVersion());
    }
    
    /**
     * This method gets the from content version for a synch.
     *
     * @param managedModule a {@link coza.opencollab.synthesis.service.api.dbo.ManagedModule} object.
     * @param toolName a {@link java.lang.String} object.
     * @param fromContentVersion a {@link java.lang.String} object.
     * @return a {@link coza.opencollab.synthesis.service.api.dbo.ContentVersion} object.
     */
    @Transactional
    public ContentVersion getFromContentVersionForSynch(ManagedModule managedModule, String toolName, String fromContentVersion){
        //first check that the exact version does not exist
        ContentVersion contentVersion = dao.getContentVersion(managedModule, toolName, fromContentVersion);
        if(contentVersion == null){
            //get the closed version
            contentVersion = dao.getContentVersionCloseTo(managedModule, toolName, fromContentVersion);
        }
        return contentVersion;
    }
    
    /**
     * Merge the content into one.
     * 
     * @param userContent
     * @param toolContent
     * @return 
     */
    private SynchContent merge(SynchContent userContent, SynchContent toolContent){
        if(userContent == null){
            //fine if it is null
            return toolContent;
        }
        if(toolContent == null){
            //fine if it is null
            return userContent;
        }
        SynchContent content = new SynchContent(ServiceCallStatus.ERROR);
        if(!userContent.isSuccessful() && !toolContent.isSuccessful()){
            content.setStatus(ServiceCallStatus.ERROR);
            content.setErrorCode(userContent.getErrorCode());
            content.addMessage(userContent.getMessage());
            content.addMessage(toolContent.getMessage());
        }else if(!userContent.isSuccessful()){
            content.setStatus(ServiceCallStatus.WARNING);
            content.setErrorCode(userContent.getErrorCode());
            content.addMessage(userContent.getMessage());
        }else if(!toolContent.isSuccessful()){
            content.setStatus(ServiceCallStatus.WARNING);
            content.setErrorCode(toolContent.getErrorCode());
            content.addMessage(toolContent.getMessage());
        }else if(userContent.getSize() == 0L){
            return toolContent;
        }else if(toolContent.getSize() == 0L){
            return userContent;
        }else{
            content = contentLoader.merge(toolContent, userContent);
        }
        return content;
    }

    /** {@inheritDoc} */
    @Override
    public long getContentSynchSize(String username, String moduleId, String toolName, String fromContentVersion) {
        return getContentSynch(username, moduleId, toolName, fromContentVersion).getSize();
    }

    /** {@inheritDoc} */
    @Override
    public UpdateContent updateUserContent(String username, String moduleId, String toolName, Map<String, ?> content, String originalContent ){
        UpdateContent updateContent = new UpdateContent(ServiceCallStatus.SUCCESS);
        try{
            Map response = contentLoader.updateUserContent(username, moduleId, toolName, content, originalContent);
            if(response == null){
                updateContent.setStatus(ServiceCallStatus.ERROR);
                updateContent.setErrorCode(ErrorCodes.LMS_CONTENT);
                updateContent.setMessage("User content not updated.");
            }else{
                updateContent.setResponseContent(response);
                updateContent.setMessage("User content updated.");
            }
        }catch(SynthesisException e){
            updateContent.setStatus(ServiceCallStatus.ERROR);
            updateContent.setErrorCode(ErrorCodes.LMS_CONTENT);
            updateContent.setMessage("User content could not be updated.");
        }
        return updateContent;
    }
    
    /**
     * Build a absolute file name for the values provided.
     * Values can be username, tool name, module, version...
     *
     * @param values The values to use in the name.
     * @return A file name.
     */
    protected String getName(String... values) {
        if(values == null || values.length == 0){
            //let it break
            return null;
        }
        StringBuilder buff = new StringBuilder(values[0]);
        for(int i = 1; i < values.length; i++){
            buff.append("-");
            buff.append(values[i]);
        }
        return buff.toString();
    }
}
