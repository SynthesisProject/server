package coza.opencollab.synthesis.moodle.client;

import coza.opencollab.synthesis.shared.Schedule;
import java.util.Date;
import java.util.List;

/**
 *
 * @author OpenCollab
 */
public interface CalendarService {
    
    public List<Schedule> getEvents(String token, int courseId, Date fromDate);
}
