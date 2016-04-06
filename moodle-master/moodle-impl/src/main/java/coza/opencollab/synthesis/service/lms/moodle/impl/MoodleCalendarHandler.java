package coza.opencollab.synthesis.service.lms.moodle.impl;

import coza.opencollab.synthesis.SynthesisException;
import static coza.opencollab.synthesis.service.api.ErrorCodes.LMS_CONTENT;
import coza.opencollab.synthesis.moodle.client.CalendarService;
import coza.opencollab.synthesis.moodle.client.ResourcesService;
import coza.opencollab.synthesis.moodle.client.UserService;
import coza.opencollab.synthesis.service.api.lms.ToolContentService;
import coza.opencollab.synthesis.shared.Schedule;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author OpenCollab
 * Notice:
 * Moodle does not support the adding attachments to Calendar events
 * Frequency of events is limited to weekly
 */
public class MoodleCalendarHandler implements ToolContentService {
    /*
     * The Moodle announcements for a module
     */
    private CalendarService calendarService;
    private ResourcesService resourceService;
    private UserService userService;
    
    @Autowired
    public MoodleCalendarHandler(CalendarService calendarService, ResourcesService resourceService) {
        super();
        this.calendarService = calendarService;
        this.resourceService = resourceService;
    }
    
    @Override
    public Object getContent(String sessionId, String moduleId, Date fromDate) {
        List<Schedule> scheduleList;
        scheduleList = new ArrayList<>();
        try {
            return scheduleList = calendarService.getEvents(sessionId, Integer.parseInt(moduleId) , fromDate);
        } catch (Exception e) {
            throw new SynthesisException(LMS_CONTENT, "Could not retrieve the schedule from Moodle (" + moduleId + ").", e);
        }
    }

    @Override
    public Object getUserContent(String sessionId, String username, String moduleId, Date fromDate) {
        return null;
    }

    @Override
    public Map updateUserContent(String sessionId, String username, String moduleId, Map content, String originalContent) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

 
}
