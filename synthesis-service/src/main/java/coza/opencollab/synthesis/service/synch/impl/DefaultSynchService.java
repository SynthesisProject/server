package coza.opencollab.synthesis.service.synch.impl;

import static coza.opencollab.synthesis.service.api.ErrorCodes.CONTENT_VERSION;
import coza.opencollab.synthesis.service.api.ServiceCallStatus;
import coza.opencollab.synthesis.service.auth.AuthenticationService;
import coza.opencollab.synthesis.service.api.dao.Dao;
import coza.opencollab.synthesis.service.auth.so.Login;
import coza.opencollab.synthesis.service.api.content.ContentService;
import coza.opencollab.synthesis.service.api.dbo.ContentVersion;
import coza.opencollab.synthesis.service.api.dbo.ManagedModule;
import coza.opencollab.synthesis.service.event.EventService;
import coza.opencollab.synthesis.service.report.StatusService;
import coza.opencollab.synthesis.service.report.so.Status;
import coza.opencollab.synthesis.service.synch.SynchService;
import coza.opencollab.synthesis.service.api.synch.so.SynchContent;
import coza.opencollab.synthesis.service.synch.so.SynchStatus;
import coza.opencollab.synthesis.service.api.synch.so.UpdateContent;
import coza.opencollab.synthesis.service.event.EventCodes;
import coza.opencollab.synthesis.service.synch.so.UpdateStatus;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

/**
 * A default implementation of the synch service.
 *
 * @author OpenCollab
 * @since 1.0.0
 * @version 1.0.1
 */
public class DefaultSynchService implements SynchService {

    /**
     * The auth service
     */
    @Autowired
    private AuthenticationService authenticationService;
    /**
     * The event service.
     */
    @Autowired
    private EventService eventService;
    /**
     * The content service.
     */
    @Autowired
    private ContentService contentService;
    /**
     * The status service.
     */
    @Autowired
    private StatusService statusService;
    /**
     * The dao
     */
    @Autowired
    private Dao dao;

    /** {@inheritDoc} */
    @Override
    @Transactional
    public SynchContent getContent(String username, String password, String moduleId, String toolName, String contentVersion) {
        //must be able to log in
        Login login = authenticationService.login(username, password);
        if (!login.isSuccessful()) {
            return new SynchContent(login);
        }        
        authenticationService.logout(login);

        SynchContent content = contentService.getContentSynch(username, moduleId, toolName, contentVersion);
        eventService.addEventForUser(username, EventCodes.SYNCH_CONTENT_REQUEST, toolName + "(" + content.getVersion() + ")", moduleId);
        return content;
    }

    /** {@inheritDoc} */
    @Override
    public Status getStatus(String username, String moduleId, String toolName) {
        Status status = new Status();
        ManagedModule managedModule = dao.getManagedModule(moduleId);

        ContentVersion clientContentVersion = dao.getContentVersion(managedModule, toolName);
        if (clientContentVersion == null) {
            status.setClientContentVersion("Unknown");
            status.setCurrentContentVersion(dao.getMasterContentVersion(moduleId, toolName).getContentVersion());
            status.setContentSynchSize(-1L);
        } else {
            SynchContent contentSynchContent = contentService.getContentSynch(username, moduleId, toolName, clientContentVersion.getContentVersion());
            status.setClientContentVersion(clientContentVersion.getContentVersion());
            status.setCurrentContentVersion(contentSynchContent.getVersion());
            status.setContentSynchSize(contentSynchContent.getSize());
        }
        return status;
    }

    /** {@inheritDoc} */
    @Override
    public SynchStatus getSynchStatus(String username, SynchStatus synchStatus) {
        //check tool versions for the tool sent up
        Map<String, Status> tools = synchStatus.getTools();
        if (tools == null) {
            tools = new HashMap<String, Status>();
        }
        for (Map.Entry<String, Status> entry : tools.entrySet()) {
            Status status = entry.getValue();
            SynchContent contentSynchContent = contentService.getContentSynch(username, synchStatus.getModuleId(), entry.getKey(), status.getClientContentVersion());
            status.setCurrentContentVersion(contentSynchContent.getVersion());
            status.setContentSynchSize(contentSynchContent.getSize());
        }
        return synchStatus;
    }

    /** {@inheritDoc} */
    @Override
    @Transactional
    public UpdateStatus updateContentVersion(String username, String moduleId, String toolName, String contentVersion) {
        UpdateStatus updateStatus = new UpdateStatus(ServiceCallStatus.SUCCESS);
        if (StringUtils.isEmpty(contentVersion)) {
            updateStatus.setStatus(ServiceCallStatus.ERROR);
            updateStatus.setErrorCode(CONTENT_VERSION);
            updateStatus.setMessage("The content version is empty.");
            return updateStatus;
        }
        ManagedModule managedModule = dao.getManagedModule(moduleId);
        ContentVersion cv = dao.getContentVersion(managedModule, toolName, contentVersion);
        if (cv == null) {
            dao.createContentVersion(managedModule, toolName, contentVersion);
        } else if(!cv.isActive()){
            dao.deactivateAllContentVersions(managedModule.getId(), toolName);
            cv.setLastUpdated();
            cv.setActive(true);
            dao.update(cv);
        }
        return updateStatus;
    }

    /** {@inheritDoc} */
    @Override
    public UpdateContent updateContent(String username, String password, String moduleId, String toolName, Map<String, ?> content, String originalContent) {
        //must be able to log in
        Login login = authenticationService.login(username, password);
        authenticationService.logout(login);
        if (!login.isSuccessful()) {
            return new UpdateContent(login);
        }
        return contentService.updateUserContent(username, moduleId, toolName, content, originalContent);
    }
}
