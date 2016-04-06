package coza.opencollab.synthesis.service.content.impl;

import coza.opencollab.synthesis.push.service.PushService;
import coza.opencollab.synthesis.SynthesisException;
import static coza.opencollab.synthesis.service.api.ErrorCodes.*;
import coza.opencollab.synthesis.service.api.ServiceCallStatus;
import coza.opencollab.synthesis.service.api.content.ContentConverter;
import coza.opencollab.synthesis.service.api.content.ContentLoader;
import coza.opencollab.synthesis.service.api.creator.impl.AbstractLoader;
import coza.opencollab.synthesis.service.api.dbo.ContentVersion;
import coza.opencollab.synthesis.service.api.dbo.ManagedModule;
import coza.opencollab.synthesis.service.api.dbo.Tool;
import coza.opencollab.synthesis.service.api.lms.LMSClient;
import coza.opencollab.synthesis.service.api.synch.so.SynchContent;
import coza.opencollab.synthesis.service.api.util.StorageEntry;
import coza.opencollab.synthesis.service.api.util.StorageFileReader;
import coza.opencollab.synthesis.service.api.util.StorageFileWriter;
import coza.opencollab.synthesis.service.api.util.StorageMemoryWriter;
import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.FileSystemUtils;

/**
 * This is a asynchronous implementation of the {@link coza.opencollab.synthesis.service.api.content.ContentLoader}.
 * <p>
 * This class will; - check the repository from time to time for new data -
 * retrieve the content and register the new version - deactivate the old - work
 * out the synchronize data
 *
 * @author OpenCollab
 * @since 1.0.0
 * @version 1.0.1
 */
public class AsyncContentLoader extends AbstractLoader implements ContentLoader {

    /**
     * Whether this loader should start the scheduled jobs
     */
    private boolean scheduleManagedModules = false;
    /**
     * The client
     */
    @Autowired
    private LMSClient lmsClient;
    /**
     * The content converters for all the tools.
     */
    Map<String, ContentConverter> contentConvertors;
    
    @Autowired
    @Qualifier("pushService")
    private PushService pushService;

    /** {@inheritDoc} */
    @Override
    public String getKey() {
        return "content";
    }

    /**
     * Whether this loader should start the scheduled jobs
     *
     * @param scheduleManagedModules a boolean.
     */
    public void setScheduleManagedModules(boolean scheduleManagedModules) {
        this.scheduleManagedModules = scheduleManagedModules;
    }

    /**
     * The content converters for all the tools.
     *
     * @param contentConvertors a {@link java.util.Map} object.
     */
    public void setContentConvertors(Map<String, ContentConverter> contentConvertors) {
        this.contentConvertors = contentConvertors;
    }

    /**
     * The content converter for the tool name.
     */
    private ContentConverter getContentConverter(String toolName) {
        return contentConvertors.get(toolName);
    }

    /**
     * initiates the loader
     */
    @PostConstruct
    public void init() {
        LOG.info("Initializing...");
        if (scheduleManagedModules) {
            LOG.info("Starting Module schedules...");
            List<ManagedModule> managedModules = dao.getManagedModules();
            for (ManagedModule managedModule : managedModules) {
                taskService.scheduleTaskAtFixedRate(managedModule.getModuleId(), new ContentLoaderRunner(managedModule.getModuleId()));
                LOG.info(managedModule.getModuleId() + " Module schedule started");
            }
        }
        LOG.info("Initialized.");
    }

    /** {@inheritDoc} */
    @Override
    @Transactional
    public void load(String moduleId) {
        ManagedModule managedModule = dao.getManagedModule(moduleId);
        if (managedModule == null) {
            dao.createManagedModule(moduleId);
        } else if (!managedModule.isActive()) {
            managedModule.setActive(true);
            dao.update(managedModule);
        }
        if (!taskService.isTaskScheduled(moduleId)) {
            LOG.info("Loading runner for module (" + moduleId + ").");
            taskService.scheduleTaskAtFixedRate(moduleId, new ContentLoaderRunner(moduleId));
        }
    }

    /**
     * Do the work. Note: Public for the transactional annotation
     *
     * @param moduleId a {@link java.lang.String} object.
     */
    @Transactional
    public void loadContent(String moduleId) {
        LOG.info("Start loading content for module (" + moduleId + ").");
        ManagedModule managedModule = dao.getManagedModule(moduleId);
        // The dashboard Controller removes records from the database, but the the 
        // list of modules are started at container start up.
        if (null != managedModule) { 
            if (!managedModule.isActive()) {
                LOG.info("Canceling module (" + moduleId + ").");
                taskService.cancelTask(managedModule.getModuleId());
                return;
            }

            List<Tool> tools = managedModule.getTools();
            for (Tool tool : tools) {
                LOG.info("Loading module (" + moduleId + ") tool (" + tool.getName() + ").");

                ContentVersion oldContentVersion;
                List<StorageEntry> entries;
                String newVersion;
                try {
                    String toolName = tool.getName();
                    oldContentVersion = dao.getContentVersion(managedModule, toolName);
                    Date fromDate;
                    if (oldContentVersion == null) {
                        fromDate = null;
                    } else {
                        fromDate = getVersionDate(oldContentVersion.getContentVersion());
                    }
                    Object data = lmsClient.getToolContent(moduleId, tool.getName(), fromDate);
                    if (data == null) {
                        LOG.debug("No data for module (" + moduleId + ") tool (" + tool.getName() + ").");
                        continue;
                    }
                    newVersion = getNewVersion();
                    ContentConverter contentConverter = getContentConverter(tool.getName());
                    if (contentConverter == null) {
                        LOG.warn("No converter for tool (" + tool.getName() + ").");
                        continue;
                    }
                    entries = contentConverter.convert(moduleId, data);
                    if (entries == null || entries.isEmpty()) {
                        LOG.debug("No data for module (" + moduleId + ") tool (" + tool.getName() + ").");
                        continue;
                    }
                } catch (SynthesisException e) {
                    LOG.warn("Could not get the data for module (" + moduleId + ") tool (" + tool.getName() + ").", e);
                    continue;
                }
                createStorageFile(entries, moduleId, tool.getName(), newVersion);
                ContentVersion newContentVersion = dao.createContentVersion(managedModule, tool.getName(), newVersion);
                dao.deactivateAllOther(newContentVersion);
                pushService.sheduleToolChangePush(managedModule, tool);
            }
            managedModule.setLastSync();
            LOG.info("Commiting content for module (" + moduleId + ").");
            dao.update(managedModule);
        }else{
            LOG.info("This module "+moduleId+" is no longer a managed Module, it will be fully removed at next Synthesis restart.");
        }
    }

    /**
     * Write the entries to the storage.
     */
    private File createStorageFile(List<StorageEntry> entries, String... values) {
        LOG.debug("Writing storage for (" + Arrays.toString(values) + ").");
        File storageDestination = storageService.getStorageFile(getKey(), getName(values));
        StorageFileWriter storage = null;
        try {
            storage = storageService.getStorageFileWriter(storageDestination);
            storage.write(entries);
            return storageDestination;
        } catch (SynthesisException e) {
            FileSystemUtils.deleteRecursively(storageDestination);
            throw e;
        } finally {
            if (storage != null) {
                storage.close();
            }
        }
    }

    /** {@inheritDoc} */
    @Override
    @Transactional
    public SynchContent getSynchContent(String moduleId, String toolName, String fromContentVersion, String toContentVersion) {
        LOG.debug("Retrieving synch content for toolname=" + toolName + "(" + fromContentVersion + " to " + toContentVersion + ").");
        ManagedModule managedModule = dao.getManagedModule(moduleId);
        File synchFile;
        SynchContent content = new SynchContent(ServiceCallStatus.SUCCESS);
        try {
            acquireLock(moduleId, toolName, fromContentVersion, toContentVersion);
            synchFile = createSynchFile(managedModule, toolName, fromContentVersion, toContentVersion);
        } finally {
            releaseLock(moduleId, toolName, fromContentVersion, toContentVersion);
        }
        content.setContentName(synchFile.getName());
        content.setMimeType(storageService.getFileHandler(synchFile).getMimeType());
        try {
            content.setContent(FileCopyUtils.copyToByteArray(synchFile));
            content.setSize(synchFile.length());
        } catch (IOException e) {
            LOG.error("Cannot read the synch content from file (" + synchFile.getAbsolutePath() + ").", e);
            content.setStatus(ServiceCallStatus.EXCEPTION);
            content.setErrorCode(FILE_MANIPULATION);
            content.addMessage("Cannot read the content synch content.");
        }
        content.setVersion(toContentVersion);
        return content;
    }

    /**
     * Compare the versions and write the entries to the synch.
     *
     * @param managedModule a {@link coza.opencollab.synthesis.service.api.dbo.ManagedModule} object.
     * @param toolName a {@link java.lang.String} object.
     * @param fromVersion a {@link java.lang.String} object.
     * @param toVersion a {@link java.lang.String} object.
     * @return the sync file
     */
    @Transactional
    public File createSynchFile(ManagedModule managedModule, String toolName, String fromVersion, String toVersion) {
        File synchDestination = storageService.getStorageFile(getKey(), getName(managedModule.getModuleId(), toolName, fromVersion, toVersion));
        if (synchDestination.exists()) {
            //this is already done
            return synchDestination;
        }
        LOG.debug("Writing synch for module (" + managedModule.getModuleId() + ") tool (" + toolName + ") version " + fromVersion + " to version " + toVersion + ".");
        List<ContentVersion> contentVersions = dao.getContentVersions(managedModule, toolName, fromVersion, toVersion);
        List<StorageEntry> entries = null;
        StorageFileWriter synch = storageService.getStorageFileWriter(synchDestination);
        StorageFileReader previousReader = null;
        StorageFileReader reader = null;
        try {
            for (ContentVersion contentVersion : contentVersions) {
                try {
                    File file = storageService.getStorageFile(getKey(), getName(managedModule.getModuleId(), toolName, contentVersion.getContentVersion()));
                    reader = storageService.getStorageFileReader(file);
                    if (entries != null && !entries.isEmpty()) {
                        entries = entryComparer.merge(entries, reader.getEntries());
                    } else {
                        entries = reader.getEntries();
                    }
                } catch (Exception exx) {
                    exx.printStackTrace();
                } finally {
                    //closing previous reader. The current reader could still be used
                    //in the next for loop when the content of the entries are
                    //read.
                    if (previousReader != null) {
                        previousReader.close();
                    }
                    previousReader = reader;
                }
            }
            synch.write(entries);
            LOG.debug("Done comparing version " + fromVersion + " to " + toVersion + ".");
            LOG.debug("Result of compare in " + synchDestination.getAbsolutePath());
            return synchDestination;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (previousReader != null) {
                previousReader.close();
            }
            if (synch != null) {
                synch.close();
            }
        }
        return synchDestination;
    }

    /** {@inheritDoc} */
    @Override
    public SynchContent getUserContent(String username, String moduleId, String toolName, String fromContentVersion) {
        String newVersion = getNewVersion();
        Object data = lmsClient.getUserContent(username, moduleId, toolName, getVersionDate(fromContentVersion));
        if (data == null) {
            LOG.debug("No data for user (" + username + "), module (" + moduleId + ") tool (" + toolName + ").");
            return null;
        }
        ContentConverter contentConverter = getContentConverter(toolName);
        if (contentConverter == null) {
            LOG.warn("No converter for tool (" + toolName + ").");
            return null;
        }
        List<StorageEntry> entries = contentConverter.convertUserData(moduleId, data);
        if (entries == null || entries.isEmpty()) {
            LOG.debug("No data for user (" + username + "), module (" + moduleId + ") tool (" + toolName + ").");
            return null;
        }
        StorageMemoryWriter writer = storageService.getStorageMemoryWriter();
        writer.write(entries);
        SynchContent content = new SynchContent(ServiceCallStatus.SUCCESS);
        content.setContentName(getName(username, moduleId, toolName, newVersion));
        content.setVersion(newVersion);
        content.setMimeType(writer.getMimeType());
        byte[] memData = writer.getContent();
        content.setSize(memData.length);
        content.setContent(memData);
        return content;
    }

    /** {@inheritDoc} */
    @Override
    public Map<String, ?> updateUserContent(String username, String moduleId, String toolName, Map<String, ?> content, String originalContent) {
        return lmsClient.updateUserContent(username, moduleId, toolName, content, originalContent);
    }

    /** {@inheritDoc} */
    @Override
    public SynchContent merge(SynchContent contentA, SynchContent contentB) {
        StorageFileReader readerA = null;
        StorageFileReader readerB = null;
        StorageFileWriter writer = null;
        try {
            File tempLocation = storageService.getTempDirectory(getKey(), "merge");
            File tempSynch = File.createTempFile(contentB.getContentName(), "synch", tempLocation);
            File tempA = File.createTempFile(contentA.getVersion(), "A", tempLocation);
            File tempB = File.createTempFile(contentB.getVersion(), "B", tempLocation);
            FileCopyUtils.copy(contentA.getContent(), tempA);
            FileCopyUtils.copy(contentB.getContent(), tempB);
            readerA = storageService.getStorageFileReader(tempA);
            readerB = storageService.getStorageFileReader(tempB);
            List<StorageEntry> entries = entryComparer.merge(readerA.getEntries(), readerB.getEntries());
            writer = storageService.getStorageFileWriter(tempSynch);
            writer.write(entries);
            FileSystemUtils.deleteRecursively(tempA);
            FileSystemUtils.deleteRecursively(tempB);
            SynchContent content = new SynchContent(ServiceCallStatus.SUCCESS);
            content.setContentName(tempSynch.getName());
            content.setMimeType(storageService.getFileHandler(tempSynch).getMimeType());
            content.setSize(tempSynch.length());
            content.setVersion(contentA.getVersion());
            content.setContent(FileCopyUtils.copyToByteArray(tempSynch));
            FileSystemUtils.deleteRecursively(tempSynch);
            return content;
        } catch (IOException e) {
            LOG.error("Could not merge the contents (" + contentA + "," + contentB + ")", e);
            throw new SynthesisException(FILE_MANIPULATION, "Could not merge the contents.", e);
        } finally {
            if (readerA != null) {
                readerA.close();
            }
            if (readerB != null) {
                readerB.close();
            }
            if (writer != null) {
                writer.close();
            }
        }
    }

    /**
     * Check if there is new content version created after the content release.
     *
     * @param released a {@link java.sql.Timestamp} object.
     * @param contentVersions a {@link java.util.List} object.
     * @return a boolean.
     */
    @Transactional
    public boolean hasNewContent(Timestamp released, List<ContentVersion> contentVersions) {
        for (ContentVersion contentVersion : contentVersions) {
            if (released.before(contentVersion.getLastUpdated())) {
                return true;
            }
        }
        return false;
    }

    /**
     * A runnable to load content.
     */
    private class ContentLoaderRunner implements Runnable {

        /**
         * The module id.
         */
        private final String moduleId;

        /**
         * Constructor setting the module id.
         */
        ContentLoaderRunner(String moduleId) {
            this.moduleId = moduleId;
        }

        @Override
        public void run() {
            try {
                loadContent(moduleId);
            } catch (Exception e) {
                LOG.error("A unexpected exception occured.", e);
            }
        }
    }
}
