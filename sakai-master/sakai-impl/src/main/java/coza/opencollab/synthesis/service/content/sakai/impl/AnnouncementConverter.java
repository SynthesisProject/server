package coza.opencollab.synthesis.service.content.sakai.impl;

import coza.opencollab.synthesis.service.api.content.AbstractResourceContentConverter;
import coza.opencollab.synthesis.service.api.content.StoredFileConsumer;
import coza.opencollab.synthesis.service.api.util.impl.ByteArrayEntry;
import coza.opencollab.synthesis.service.api.util.StorageEntry;
import coza.opencollab.synthesis.shared.Announcement;
import coza.opencollab.synthesis.shared.Attachment;
import coza.opencollab.synthesis.util.JsonParser;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;

/**
 * <p>AnnouncementConverter class.</p>
 *
 * @author OpenCollab
 * @version 1.0.0
 */
public class AnnouncementConverter extends AbstractResourceContentConverter<List<Announcement>, Object> {

    /**
     * The logger
     */
    private static final Logger LOG = Logger.getLogger(AnnouncementConverter.class);

    /** {@inheritDoc} */
    @Override
    public String getStorageKey() {
        return "announcement";
    }

    /** {@inheritDoc} */
    @Override
    public List<StorageEntry> convert(String moduleId, List<Announcement> announcements) {
        if (announcements == null || announcements.isEmpty()) {
            return null;
        }
        List<StorageEntry> entries = new ArrayList<StorageEntry>();
        Map<String, Announcement> data = new HashMap<String, Announcement>();
        for (Announcement announcement : announcements) {
            updateLinks(moduleId, announcement, entries);
            data.put(announcement.getId(), announcement);
            loadAttachments(moduleId, announcement.getAttachments(), entries);
        }
        if (data.size() > 0) {
            ByteArrayEntry entry = new ByteArrayEntry("announcements.json", "data", JsonParser.writeJsonBytes(data));
            entries.add(entry);
        }
        LOG.info("Announcements Converter executed");
        return entries;
    }

    /**
     * loads all the attachment files depending on whether it must be in the
     * data and/or in storage.
     */
    private void loadAttachments(String moduleId, List<Attachment> attachments, List<StorageEntry> entries) {
        if (attachments == null || attachments.isEmpty()) {
            return;
        }
        for (Attachment attachment : attachments) {
            if (attachment.getContentURL() != null) {
                String name = attachment.getName();
                attachment.setDownloadKey(addExtraResource(moduleId, entries, name, getDirectory(attachment.getId(), name), attachment.getContentURL()));
            }
            attachment.setContentURL(null);
        }
    }

    private String getDirectory(String id, String name) {
        return id.substring(0, id.length() - name.length() - 1);
    }

    /**
     * Update the links to point to downloaded files.
     */
    private void updateLinks(String moduleId, final Announcement announcement, final List<StorageEntry> entries) {
        announcement.setBody(updateLinks(moduleId, entries, new StoredFileConsumer() {

            @Override
            public void stored(String name, String directory, String mimeType, String originalUrl, int size, String downloadKey) {
                Attachment attachment = new Attachment();
                attachment.setId(originalUrl);
                attachment.setName(name);
                attachment.setSize(size);
                attachment.setPath(directory);
                attachment.setUrl(originalUrl);
                attachment.setDownloadKey(downloadKey);
                attachment.setLink(true);
                attachment.setMimeType(mimeType);
                announcement.addToAttachments(attachment);
            }
        }, announcement.getId(), announcement.getBody()));
    }
}
