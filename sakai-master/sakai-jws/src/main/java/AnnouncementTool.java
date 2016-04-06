
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.apache.axis.AxisFault;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.sakaiproject.announcement.api.AnnouncementMessage;
import org.sakaiproject.announcement.api.AnnouncementMessageHeader;
import org.sakaiproject.component.cover.ComponentManager;
import org.sakaiproject.site.api.SiteService;
import org.sakaiproject.announcement.api.AnnouncementService;
import org.sakaiproject.entity.api.Entity;
import org.sakaiproject.entity.api.Reference;
import org.sakaiproject.entity.api.ResourceProperties;
import org.sakaiproject.exception.IdUnusedException;
import org.sakaiproject.time.api.Time;
import org.sakaiproject.tool.api.Session;
import org.sakaiproject.tool.api.SessionManager;
import org.sakaiproject.util.Xml;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 * The web service dedicated to retrieving announcements.
 *
 * @author OpenCollab
 * @since 1.0.0
 */
public class AnnouncementTool {

    static final Log LOG = LogFactory.getLog(AnnouncementTool.class);
    private static final String ANNOUNCE_TOOL_ID = "sakai.announcements";
    private final SessionManager sessionManager;
    private final SiteService siteService;
    private final AnnouncementService announcementService;
    private final String DELETED = "DELETED";

    /**
     * Setup dependencies
     */
    public AnnouncementTool() {
        announcementService = (AnnouncementService) ComponentManager.get(AnnouncementService.class.getName());
        sessionManager = (SessionManager) ComponentManager.get(SessionManager.class.getName());
        siteService = (SiteService) ComponentManager.get(SiteService.class.getName());
    }

    /**
     * Get the Session related to the given session id
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

    /**
     * Retrieve a sites announcements when given the sessionId and the siteId.
     *
     * An announcement tool for a site/module has channel(s) Channel has
     * messages.
     *
     * @param sessionId e.g. 7ee79c0d-1e58-4307-a322-654215331ab6
     * @param siteId e.g. AFL1501-13-S1-43T
     * @param fromDate filter date
     * @return xml representation of the announcement. If the site does not have
     * announcements it returns an empty XML element.
     * @throws org.apache.axis.AxisFault
     */
    public String getAnnouncementsForSite(String sessionId, String siteId, final String fromDate) throws AxisFault {
        establishSession(sessionId);
        Document dom = Xml.createDocument();
        Element list = (Element) dom.createElement("list");
        dom.appendChild(list);
        //get get list of channels
        List<String> channelList = makeReferenceAnnouncements(siteId);
        if (channelList == null) {
            return Xml.writeDocumentToString(dom);
        }
        try {
            Date filterDate;
            if (!StringUtils.isEmpty(fromDate)) {
                filterDate = new Date(Long.parseLong(fromDate));
            } else {
                filterDate = new Date(0);
            }
            for (String ref : channelList) {
                if (ref == null) {
                    continue;
                }
                //get all messages for the channel

                List<AnnouncementMessage> annMsgs = announcementService.getMessages(ref, null, true, true);
                for (AnnouncementMessage aMessage : annMsgs) {
                    AnnouncementMessageHeader amh = aMessage.getAnnouncementHeader();
                    ResourceProperties messageProps = aMessage.getProperties();
                    //build message node
                    Element message = dom.createElement("message");
                    message.setAttribute("date", convertSakaiTimeToDate(amh.getDate()));
                    message.setAttribute("begin_date", convertSakaiTimeToDate(messageProps.getProperty(AnnouncementService.RELEASE_DATE)));
                    message.setAttribute("end_date", convertSakaiTimeToDate(messageProps.getProperty(AnnouncementService.RETRACT_DATE)));
                    // Set the latest change date
                    String modDate = convertSakaiTimeToDate(messageProps.getProperty(AnnouncementService.MOD_DATE));
                    if (modDate == null) {
                        modDate = message.getAttribute("date");
                    }
                    message.setAttribute("mod_date", modDate);
                    // Check if announcement must be added
                    if (addAnnouncement(message, filterDate, amh)) {

                        message.setAttribute("id", aMessage.getId());
                        message.setAttribute("subject", amh.getSubject());
                        message.setAttribute("from", amh.getFrom().getDisplayName());
                        message.setAttribute("url", aMessage.getUrl());
                        message.setAttribute("order", String.valueOf(aMessage.getHeader().getMessage_order()));

                        //build group node
                        List<String> groupsList = (List<String>) amh.getGroups();
                        addGroups(dom, groupsList, message);

                        Node bodyNode = dom.createElement("body");
                        message.appendChild(bodyNode);
                        bodyNode.appendChild(dom.createTextNode(aMessage.getBody()));

                        //get all attachments for a message
                        List<Reference> attachments = aMessage.getHeader().getAttachments();
                        if (attachments != null && !attachments.isEmpty()) {
                            message.appendChild(getAttachments(dom, attachments));
                        }
                        // Add to list
                        list.appendChild(message);
                    }
                }
            }
        } catch (Exception e) {
            LOG.error("Could not retrieve announcements.", e);
            throw new AxisFault("Could not retrieve announcements.", e);
        }
        return Xml.writeDocumentToString(dom);
    }

    /**
     * Check if announcement must be deleted or shown according to release
     * retract dates and previous checked date
     *
     * @param announcement
     * @param fromDate
     * @return 1 if announcement added, 0 if announcement skipped
     */
    private boolean addAnnouncement(Element announcement, Date fromDate, AnnouncementMessageHeader amh) {
        Date comparisonDate = getDateAttribute(announcement, "mod_date");
        Date releaseDate = getDateAttribute(announcement, "begin_date");
        Date retractDate = getDateAttribute(announcement, "end_date");
        Date currentDate = new Date();
        // First check if any announcement became available because of release date
        if (!fromDate.equals(new Date(0)) && releaseDate != null
                && fromDate.before(releaseDate) && currentDate.after(releaseDate)
                && (retractDate == null || currentDate.before(retractDate))) {
            LOG.info("Add announcement - release date" + announcement.getAttribute("id"));
            return true;
            // Check if any announcement should be retracted that has previously been checked
        } else if (!fromDate.equals(new Date(0)) && retractDate != null
                && fromDate.before(retractDate) && currentDate.after(retractDate)) {
            announcement.setAttribute("status", DELETED);
            LOG.info("Add announcement - DELETE for retract date" + announcement.getAttribute("id"));
            return true;
            // Check any new/changed resources - they should be within release/retract
        } else if ((comparisonDate == null || comparisonDate.after(fromDate))
                && ((releaseDate != null && currentDate.before(releaseDate))
                || (retractDate != null && currentDate.after(retractDate)))) {
                announcement.setAttribute("status", DELETED);
            LOG.info("Add announcement " + announcement.getAttribute("id"));
            return true;
        } else if ((comparisonDate == null || comparisonDate.after(fromDate))
                && (releaseDate == null || currentDate.after(releaseDate))
                && (retractDate == null || currentDate.before(retractDate))) {
            if (amh.getDraft()) {
                announcement.setAttribute("status", DELETED);
            }
            LOG.info("Add announcement " + announcement.getAttribute("id"));
            return true;
        }
        return false;
    }

    /**
     * Gets the Date object from a resource's string attribute. Returns first
     * non empty value or null if no valid entry found
     *
     * @param resource
     * @param attributes
     * @return
     */
    private Date getDateAttribute(Element resource, String... attributes) {
        for (String attribute : attributes) {
            if (!StringUtils.isEmpty(resource.getAttribute(attribute))) {
                return new Date(Long.parseLong(resource.getAttribute(attribute)));
            }
        }
        return null;
    }

    /**
     * The dates are of an abstract Sakai specific format, they can be empty or
     * contain a long value which must be reformatted to return a standard date
     * in long format
     *
     * @param sakaiTime
     * @return
     * @throws ParseException
     */
    private String convertSakaiTimeToDate(String sakaiTime) {
        try {
            if (sakaiTime == null || sakaiTime.trim().length() == 0) {
                return null;
            }
            DateFormat inputDate = new SimpleDateFormat("yyyyMMddHHmmssSSS");
            Date date = inputDate.parse(sakaiTime);
            return String.valueOf(date.getTime() + Calendar.getInstance().getTimeZone().getOffset(date.getTime()));
        } catch (ParseException ex) {
            return null;
        }
    }

    /**
     * Null safe conversion of Sakai Time to String representation of epoch
     * milliseconds value
     *
     * @param sakaiTime
     * @return todo
     * @throws ParseException
     */
    private String convertSakaiTimeToDate(Time sakaiTime) {
        if (sakaiTime == null) {
            return null;
        }
        return String.valueOf(sakaiTime.getTime());
    }

    /**
     * Helper method to construct a list of references for a siteId
     *
     * @param siteId
     * @return List of references or null
     */
    private List<String> makeReferenceAnnouncements(String siteId) {
        if (siteHasAnnouncementTool(siteId)) {
            List<String> refList = new ArrayList<>();
            List<String> idList = announcementService.getChannelIds(siteId);
            for (String id : idList) {
                refList.add(announcementService.channelReference(siteId, id));
            }
            return refList;
        } else {
            return null;
        }
    }

    /**
     * Helper method to determine whether a site has an announcement tool.
     *
     * @param siteId
     * @return
     */
    private boolean siteHasAnnouncementTool(String siteId) {
        try {
            return siteService.getSite(siteId).getToolForCommonId(ANNOUNCE_TOOL_ID) != null;
        } catch (IdUnusedException e) {
            return false;
        }
    }

    /**
     * Given a list of attachments parse through the list and return an XML
     * element.
     *
     * @param dom XML Document
     * @param attachments List of attachments
     * @param message XML Element
     * @return Element message
     */
    private Element getAttachments(Document dom, List<Reference> attachments) {
        Element attach = dom.createElement("attachments");
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
        return attach;
    }

    /**
     * @param dom XML Document
     * @param groupList list of group names which the announcement is for
     * @param message XML Element
     */
    private void addGroups(Document dom, List<String> groupList, Element message) {
        Element groups = dom.createElement("groups");
        for (String groupAnn : groupList) {
            Element group = dom.createElement("group");
            group.setTextContent(groupAnn);
            groups.appendChild(group);
        }
        message.appendChild(groups);
    }
}
