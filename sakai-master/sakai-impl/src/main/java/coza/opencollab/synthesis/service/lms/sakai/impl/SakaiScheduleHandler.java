package coza.opencollab.synthesis.service.lms.sakai.impl;

import coza.opencollab.synthesis.SynthesisException;
import coza.opencollab.synthesis.shared.Schedule;
import coza.opencollab.synthesis.sakai.client.ScheduleService;
import static coza.opencollab.synthesis.service.api.ErrorCodes.*;
import coza.opencollab.synthesis.service.api.lms.ToolContentService;
import static coza.opencollab.synthesis.service.api.util.impl.DomHelper.*;
import coza.opencollab.synthesis.shared.Attachment;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.dom4j.Document;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Retrieves Schedule data and returns shared resource schedule object.
 *
 * @author OpenCollab
 * @version 1.0.0
 */
public class SakaiScheduleHandler implements ToolContentService {
    /*
     * The Sakai announcements for a  module
     */

    @Autowired
    private ScheduleService scheduleService;
    /*
     * The Sakai resources for a module
     */
    @Autowired
    private SakaiResourceHandler resourcesHandler;

    /*
     * The Sakai resources for a module
     */
    /**
     * <p>Setter for the field <code>resourcesHandler</code>.</p>
     *
     * @param resourcesHandler a {@link coza.opencollab.synthesis.service.lms.sakai.impl.SakaiResourceHandler} object.
     */
    public void setResourcesHandler(SakaiResourceHandler resourcesHandler) {
        this.resourcesHandler = resourcesHandler;
    }

    /** {@inheritDoc} */
    @Override
    public Object getContent(String sessionId, String moduleId, Date fromDate) {
        try {
            Document document = createDocument(scheduleService.getScheduleForSite(sessionId, moduleId, fromDate));
            return getScheduleItems(document, sessionId, moduleId);
        } catch (Exception e) {
            throw new SynthesisException(LMS_CONTENT, "Could not retrieve the schedule from Sakai (" + moduleId + ").", e);
        }
    }

    /** {@inheritDoc} */
    @Override
    public Object getUserContent(String sessionId, String username, String moduleId, Date fromDate) {
        return null;
    }

    /**
     * Parse Announcement takes the given xml in raw string form and generates an xml document. It then parses the xml
     * document and returns the announcement object(s)
     *
     * @return List of announcements
     * @param document a {@link org.dom4j.Document} object.
     * @param sessionId a {@link java.lang.String} object.
     * @param moduleId a {@link java.lang.String} object.
     */
    public List<Schedule> getScheduleItems(Document document, String sessionId, String moduleId) {
        List<Element> elements = getElements(document, "/list/item");
        List<Schedule> scheduleEvents = new ArrayList<Schedule>();
        for (Element element : elements) {
            Schedule schedule = new Schedule();
            schedule.setId(getSubElementValue(element, "id"));
            schedule.setTitle(getSubElementValue(element, "title"));
            schedule.setDescription(getSubElementValue(element, "description"));
            schedule.setRange(getSubElementValue(element, "range"));
            schedule.setStart(getSubElementValueAsDate(element, "start"));
            schedule.setEnd(getSubElementValueAsDate(element, "end"));
            schedule.setFrequency(getSubElementValue(element, "frequency"));
            schedule.setFrequencyDescription(getSubElementValue(element, "frequency_description"));
            schedule.setRecurrenceInterval(getSubElementValue(element, "recurrence_interval"));
            schedule.setRecurrenceCount(getSubElementValue(element, "recurrence_count"));
            schedule.setRecursUntil(getSubElementValueAsDate(element, "recurs_until"));
            schedule.setAllDay(getSubElementValueAsBoolean(element, "allDay"));
            schedule.setType(getSubElementValue(element, "type"));
            schedule.setSite(getSubElementValue(element, "site"));
            schedule.setFromSite(getSubElementValue(element, "from-site"));
            schedule.setLocation(getSubElementValue(element, "location"));
            schedule.setCreateDate(getSubElementValueAsDate(element, "create_date"));
            schedule.setModifiedDate(getSubElementValueAsDate(element, "modified_date"));
            schedule.setCreator(getSubElementValue(element, "creator"));
            List<Element> attachments = getElements(element, "attachments/attachment");
            for(Element attach: attachments){
                Attachment attachment = new Attachment();
                attachment.setId(attach.attributeValue("id"));
                attachment.setName(attach.attributeValue("name"));
                attachment.setSize(Integer.parseInt(attach.attributeValue("size")));
                attachment.setMimeType(attach.attributeValue("type"));
                String url = attach.attributeValue("url");
                attachment.setUrl(url);
                attachment.setPath(url.substring(url.indexOf(moduleId)));
                attachment.setContentURL(resourcesHandler.getResourceURL(sessionId, attach.attributeValue("id")));
                schedule.addToAttachments(attachment);
            }
            scheduleEvents.add(schedule);
        }
        return scheduleEvents;
    }

    /** {@inheritDoc} */
    @Override
    public Map updateUserContent(String sessionId, String username, String moduleId, Map content, String originalContent) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
