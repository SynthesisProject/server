
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import org.sakaiproject.calendar.api.CalendarService;
import org.apache.axis.AxisFault;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.sakaiproject.calendar.api.Calendar;
import org.sakaiproject.calendar.api.CalendarEvent;
import org.sakaiproject.calendar.api.RecurrenceRule;
import org.sakaiproject.component.cover.ComponentManager;
import org.sakaiproject.entity.api.Entity;
import org.sakaiproject.entity.api.Reference;
import org.sakaiproject.entity.api.ResourceProperties;
import org.sakaiproject.site.api.Site;
import org.sakaiproject.site.api.SiteService;
import org.sakaiproject.tool.api.Session;
import org.sakaiproject.tool.api.SessionManager;
import org.sakaiproject.user.api.User;
import org.sakaiproject.user.api.UserDirectoryService;
import org.sakaiproject.util.Xml;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 * The Schedule tool WS will be responsible for retrieving schedule data for a
 * site.
 *
 * @author OpenCollab
 * @since 1.0.0
 */
public class ScheduleTool {

    private static final Log LOG = LogFactory.getLog(ScheduleTool.class);
    private CalendarService calendarService;
    private SessionManager sessionManager;
    private UserDirectoryService userDirectoryService;
    private SiteService siteService;
    //1000 millisecoonds in a second, 60 seconds in a minute, 60 minutes in an hour
    private static final int ONE_HOUR = 60 * 60 * 1000;

    public ScheduleTool() {
        calendarService = (CalendarService) ComponentManager.get(CalendarService.class.getName());
        sessionManager = (SessionManager) ComponentManager.get(SessionManager.class.getName());
        userDirectoryService = (UserDirectoryService) ComponentManager.get(UserDirectoryService.class.getName());
        siteService = (SiteService) ComponentManager.get(SiteService.class.getName());
    }

    /**
     * Get Schedule events for a module.
     *
     * @param sessionId todo
     * @param siteId String
     * @param fromDate todo
     * @return XML document as a String
     * @throws AxisFault todo
     */
    public String getScheduleForSite(String sessionId, String siteId, String fromDate) throws AxisFault {
        establishSession(sessionId);
        //setup source and target calendar strings
        String calReference = "/calendar/calendar/" + siteId + "/main";
        Document dom = Xml.createDocument();
        Date filterDate = new Date(0);
        //filterDate preparation
        try {
            if (fromDate != null && !StringUtils.isEmpty(fromDate) && StringUtils.isNumeric(fromDate)) {
                filterDate = new Date(Long.parseLong(fromDate));
            }
        } catch (Exception e) {
            LOG.error("Could not convert the fromDate.", e);
        }
        Date comparisonDate;
        try {
            //get calendars
            Calendar calendar = calendarService.getCalendar(calReference);
            Node list = dom.createElement("list");
            dom.appendChild(list);

            //for every event in calendar1, add it to calendar2
            List eventsList = calendar.getEvents(null, null);

            for (Iterator i = eventsList.iterator(); i.hasNext();) {
                CalendarEvent cEvent = (CalendarEvent) i.next();
                Node item = dom.createElement("item");
                addNode(dom, item, "id", cEvent.getId());
                addNode(dom, item, "creator", getUserNameFromId(cEvent.getCreator()));
                addNode(dom, item, "modified_by", cEvent.getModifiedBy());
                addNode(dom, item, "title", cEvent.getDisplayName());
                addNode(dom, item, "description", cEvent.getDescriptionFormatted());
                addNode(dom, item, "range", cEvent.getRange().toString());
                addNode(dom, item, "start", String.valueOf(cEvent.getRange().firstTime().getTime()));
                addNode(dom, item, "end", String.valueOf(cEvent.getRange().lastTime().getTime()));
                String frequencyDescriptionText = "";
                String frequencyText = "";
                String recurenceInterval = "";
                String recurenceCount = "";
                String recurUnitl = "";
                RecurrenceRule recurRule = cEvent.getRecurrenceRule();
                if (recurRule != null) {
                    recurenceInterval = String.valueOf(recurRule.getInterval());
                    if (!StringUtils.isEmpty(recurRule.getFrequencyDescription())) {
                        frequencyDescriptionText = recurRule.getFrequencyDescription();
                    }
                    if (!StringUtils.isEmpty(recurRule.getFrequency())) {
                        frequencyText = recurRule.getFrequency();
                    }
                    if (recurRule.getCount() > 0) {
                        recurenceCount = String.valueOf(recurRule.getCount());
                    }
                    if (null != recurRule.getUntil()) {
                        recurUnitl = String.valueOf(recurRule.getUntil().getTime());
                    }
                }
                addNode(dom, item, "frequency_description", frequencyDescriptionText);
                addNode(dom, item, "frequency", frequencyText);
                addNode(dom, item, "recurrence_interval", recurenceInterval);
                addNode(dom, item, "recurrence_count", recurenceCount);
                addNode(dom, item, "recurs_until", recurUnitl);
                addNode(dom, item, "all-day", String.valueOf(cEvent.getRange().isSingleTime()));
                addNode(dom, item, "type", cEvent.getType());
                addNode(dom, item, "location", cEvent.getLocation());
                Site site = siteService.getSite(siteId);
                addNode(dom, item, "site", site.getTitle());
                addNode(dom, item, "from_site", cEvent.getSiteName());
                addNode(dom, item, "create_date", convertSakaiTimeToDate(cEvent.getProperties().getProperty(cEvent.getProperties().getNamePropCreationDate())));
                addNode(dom, item, "modified_date", convertSakaiTimeToDate(cEvent.getProperties().getProperty(cEvent.getProperties().getNamePropModifiedDate())));
                List<Reference> attachments = cEvent.getAttachments();
                this.getAttachments(dom, attachments, item);
                //On creation of an event the creation date and the modification Date are both set, subsequent modifications only update the mod date so we will compare against mod date.
                comparisonDate = new Date(Long.parseLong(convertSakaiTimeToDate(cEvent.getProperties().getProperty(cEvent.getProperties().getNamePropModifiedDate()))));
                if (filterDate.before(comparisonDate)) {
                    list.appendChild(item);
                }
            }
        } catch (Exception e) {
            LOG.error("Could not retrieve the schedule.", e);
            throw new AxisFault("Could not retrieve the schedule.", e);
        }
        return Xml.writeDocumentToString(dom);
    }

    /**
     * Given a list of attachments parse through the list and return an XML
     * element.
     *
     * @param dom XML Document
     * @param attachments List of attachments
     * @param message XML Element
     */
    private void getAttachments(Document dom, List<Reference> attachments, Node event) {
        Node attach = dom.createElement("attachments");
        event.appendChild(attach);
        for (Reference attRef : attachments) {
            Entity ent = attRef.getEntity();
            ResourceProperties resourceProperties = ent.getProperties();
            String id = ent.getId();
            String name = resourceProperties.getProperty(resourceProperties.getNamePropDisplayName());
            String url = ent.getUrl();
            String size = resourceProperties.getProperty(resourceProperties.getNamePropContentLength());
            String type = resourceProperties.getProperty(resourceProperties.getNamePropContentType());

            Element a = dom.createElement("attachment");
            a.setAttribute("id", id);
            a.setAttribute("url", url);
            a.setAttribute("name", name);
            a.setAttribute("size", size);
            a.setAttribute("type", type);
            attach.appendChild(a);
        }
    }

    /**
     * Get the Session related to the given sessionid
     *
     * @param sessionid	the id of the session to retrieve
     * @return	the session, if it is active
     * @throws AxisFault	if session is inactive
     */
    private Session establishSession(String sessionid) throws AxisFault {
        Session s = sessionManager.getSession(sessionid);

        if (s == null) {
            throw new AxisFault("Session \"" + sessionid + "\" is not active");
        }
        s.setActive();
        sessionManager.setCurrentSession(s);
        return s;
    }

    private void addNode(Document dom, Node item, String nodeName, String nodeValue) {
        Node node = dom.createElement(nodeName);
        node.setTextContent(nodeValue);
        item.appendChild(node);
    }

    /**
     * The beginning , end and mod dates are of an abstract Sakai specific
     * format, they can be empty or contain a long value which must be
     * reformatted to return a standard date in long format ""
     *
     * @param sakaiTime
     * @return
     * @throws ParseException
     */
    private String convertSakaiTimeToDate(String sakaiTime) throws ParseException {
        try {
            if (sakaiTime == null || sakaiTime.trim().length() == 0) {
                return null;
            }
            DateFormat inputDate = new SimpleDateFormat("yyyyMMddHHmmssSSS");
            Date date = inputDate.parse(sakaiTime);
            return String.valueOf(date.getTime() + java.util.Calendar.getInstance().getTimeZone().getOffset(date.getTime()));
        } catch (ParseException ex) {
            return null;
        }
    }

    private String getUserNameFromId(String userId) {
        if (!StringUtils.isEmpty(userId)) {
            try {
                User user = userDirectoryService.getUser(userId);
                return user.getDisplayName();
            } catch (Exception e) {
                LOG.error("Attempt to get the user's name for id : " + userId, e);
            }
        }
        return "";
    }
}
