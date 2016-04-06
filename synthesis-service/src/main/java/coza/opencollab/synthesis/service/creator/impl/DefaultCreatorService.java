package coza.opencollab.synthesis.service.creator.impl;

import coza.opencollab.synthesis.service.api.content.ContentLoader;
import coza.opencollab.synthesis.service.creator.CreatorService;
import coza.opencollab.synthesis.service.api.creator.StorageService;
import coza.opencollab.synthesis.service.api.dao.Dao;
import coza.opencollab.synthesis.service.api.dbo.ManagedModule;
import coza.opencollab.synthesis.service.event.EventCodes;
import coza.opencollab.synthesis.service.event.EventService;
import coza.opencollab.synthesis.shared.Tool;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * The default implementation of the creator service.
 *
 * @author OpenCollab
 * @since 1.0.0
 * @version 1.0.1
 */
public class DefaultCreatorService implements CreatorService {
    /**
     * The event service injected.
     */
    @Autowired
    private EventService eventService;
    /**
     * The Storage service.
     */
    @Autowired
    private StorageService storageService;
    /**
     * The dao
     */
    @Autowired
    private Dao dao;
    
    @Autowired
    private ContentLoader contentLoader;
    /** {@inheritDoc} */
    @Override
    public List<Tool> getTools(String moduleId) {
        ManagedModule managedModule = dao.getManagedModule(moduleId);
        List<Tool> tools = new ArrayList<>();
        List<coza.opencollab.synthesis.service.api.dbo.Tool> dboTools = managedModule.getTools();
        for(coza.opencollab.synthesis.service.api.dbo.Tool dboTool : dboTools){
            Tool tool = new Tool();
            tool.setName(dboTool.getName());
            tool.setTitle(dboTool.getTitle());
            tools.add(tool);
        }
        eventService.addEvent(EventCodes.CREATOR_RETRIEVE_TOOLS, null, moduleId);
        return tools;
    }
    
    /** {@inheritDoc} */
    @Override
    public FileInputStream getDownloadFileStream(String downloadKey){
        return storageService.getDownloadFileStream(downloadKey);
    }    

    /** {@inheritDoc} */
    @Override
    public void addManagedModule(String moduleId) {
        contentLoader.load(moduleId);
    }

}
