package coza.opencollab.synthesis.moodle.client.impl;

import com.google.gson.reflect.TypeToken;
import coza.opencollab.synthesis.moodle.client.CalendarService;
import coza.opencollab.synthesis.moodle.client.dao.Event;
import coza.opencollab.synthesis.moodle.client.dao.Events;
import coza.opencollab.synthesis.shared.Schedule;
import java.text.DateFormat;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 *
 * @author OpenCollab
 */
public class CalendarServiceImpl extends AbstractServiceImpl implements CalendarService {

    private static final String CALENDAR_EVENTS_FUNCTION = "core_calendar_get_calendar_events";
    private final String queryProperties = "events[courseids][0]={0}&options[timemodified]={1}";
    // private String queryProperties = "events[courseids][0]={0}&options[timestart]={1}&options[timeend]={2}";


    private UserServiceImpl userService;
    private boolean endDateSetEndOfDay;
    private String rangeDateFormat;

    @Override
    public List<Schedule> getEvents(String token, int courseId, Date fromDate) {
        List<Schedule> scheduleList = new ArrayList<>();
        try {
            String startDateUnixTimestamp = null;
            if (null != fromDate) {
                startDateUnixTimestamp = String.valueOf(fromDate.getTime() / 1000);
            } else {
                DateFormat format = new SimpleDateFormat("yyyy", Locale.ENGLISH);
                Calendar now = Calendar.getInstance();   // Gets the current date and time
                int year = now.get(Calendar.YEAR);
                Calendar cal = Calendar.getInstance();
                cal.set(Calendar.YEAR, year);
                cal.set(Calendar.DAY_OF_YEAR, 1);
                Date start = cal.getTime();

                cal.set(Calendar.YEAR, year);
                cal.set(Calendar.MONTH, 11); // 11 = december
                cal.set(Calendar.DAY_OF_MONTH, 31); // new years eve
                startDateUnixTimestamp = String.valueOf(start.getTime() / 1000L);
            }
            Events events = getScheduleList(callService(token, CALENDAR_EVENTS_FUNCTION, MessageFormat.format(queryProperties, courseId, startDateUnixTimestamp)));
            for (Event event : events.getEvents()) {
                if (null == fromDate) {
                    scheduleList.add(getScheduleFromEvent(event, token, courseId));
                } else if(new Date(event.getTimemodified() * 1000L).after(fromDate)){
                    scheduleList.add(getScheduleFromEvent(event, token, courseId));
                }
            }
        } catch (Exception e) {
            log.error("Error getting schedule events ", e);
        }
        return scheduleList;
    }

    private Events getScheduleList(String json) {
        Events events = new Events();
        try {
            events = gson.fromJson(json, new TypeToken<Events>() {
            }.getType());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return events;
    }

    /**
     * Convert a Calendar event into the shared Schedule object Moodle only
     * repeats an event on a weekly basis & creates the actual entries as an
     * atomic event Therefore it will be its own schedule item, no need to be
     * aware of frequency. ## NOTE ## Moodle does not require an end date, but
     * the sakai implementation does, so when no end date is present set the end
     * date to the implied end date by the end of the same day as the start
     * date.
     *
     * @param event
     * @param token
     * @return
     */
    private Schedule getScheduleFromEvent(Event event, String token, int courseId) {
        Schedule schedule = new Schedule();
        schedule.setId(String.valueOf(event.getId()));
        schedule.setTitle(event.getName());
        schedule.setDescription(event.getDescription());
        schedule.setStart(new Date(event.getTimestart() * 1000L));
        schedule.setAllDay(false);
        schedule.setSite(String.valueOf(courseId));
        if (event.getTimeduration() > 0) {
            schedule.setEnd(new Date((event.getTimestart() + event.getTimeduration()) * 1000L));
        } else {
            if (isEndDateSetEndOfDay()) {
                Calendar cal = Calendar.getInstance();
                cal.setTime(schedule.getStart());
                cal.set(Calendar.HOUR, 23);
                cal.set(Calendar.MINUTE, 59);
                Date end = cal.getTime();
                schedule.setEnd(end);
            }
        }
        SimpleDateFormat sdf = new SimpleDateFormat(rangeDateFormat);
        schedule.setRange(sdf.format(schedule.getStart()) + "00000]" + sdf.format(schedule.getStart()) + "00000");
        schedule.setFrequency("");
        schedule.setRecurrenceInterval("");
        schedule.setRecurrenceCount("");
        schedule.setRecursUntil(null);
        schedule.setType(event.getEventtype());
        schedule.setSite(String.valueOf(event.getCourseid()));
        schedule.setCreateDate(new Date(event.getTimemodified() * 1000L));
        schedule.setModifiedDate(new Date(event.getTimemodified() * 1000L));
        schedule.setCreator(userService.getUserDisplayName(token, event.getUserid()));
        return schedule;
    }

    private void getRepeatFrequency(int repeatId, Schedule schedule) {

    }

    public UserServiceImpl getUserService() {
        return userService;
    }

    public void setUserService(UserServiceImpl userService) {
        this.userService = userService;
    }

    public boolean isEndDateSetEndOfDay() {
        return endDateSetEndOfDay;
    }

    public void setEndDateSetEndOfDay(boolean endDateSetEndOfDay) {
        this.endDateSetEndOfDay = endDateSetEndOfDay;
    }

    public String getRangeDateFormat() {
        return rangeDateFormat;
    }

    public void setRangeDateFormat(String rangeDateFormat) {
        this.rangeDateFormat = rangeDateFormat;
    }

}
