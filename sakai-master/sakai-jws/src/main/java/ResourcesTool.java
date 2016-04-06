
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;
import org.apache.axis.AxisFault;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.sakaiproject.component.cover.ComponentManager;
import org.sakaiproject.content.api.ContentCollection;
import org.sakaiproject.content.api.ContentEntity;
import org.sakaiproject.tool.api.SessionManager;
import org.sakaiproject.content.api.ContentHostingService;
import org.sakaiproject.content.api.ContentResource;
import org.sakaiproject.content.api.GroupAwareEntity;
import org.sakaiproject.db.api.SqlService;
import org.sakaiproject.entity.api.ResourceProperties;
import org.sakaiproject.time.api.Time;
import org.sakaiproject.time.api.TimeService;
import org.sakaiproject.tool.api.Session;
import org.sakaiproject.user.api.User;
import org.sakaiproject.user.api.UserDirectoryService;
import org.sakaiproject.util.Xml;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 * This tool will be responsible for retrieving all resources for a module
 * specific resources for a tool
 *
 * @author OpenCollab
 * @since 1.0.0
 */
public class ResourcesTool {

    private static final Log LOG = LogFactory.getLog(ResourcesTool.class);
    private final SqlService sqlService;
    private SessionManager sessionManager;
    private ContentHostingService contentHostingService;
    private UserDirectoryService userDirectoryService;
    private TimeService timeService;
    private Map<String, String> users = new WeakHashMap<String, String>();
    private final String DELETED = "DELETED";

    /**
     * Setup dependencies
     */
    public ResourcesTool() {
        sqlService = (SqlService) ComponentManager.get(SqlService.class);
        sessionManager = (SessionManager) ComponentManager.get(SessionManager.class.getName());
        contentHostingService = (ContentHostingService) ComponentManager.get(ContentHostingService.class.getName());
        userDirectoryService = (UserDirectoryService) ComponentManager.get(UserDirectoryService.class.getName());
        timeService = (TimeService) ComponentManager.get(TimeService.class.getName());
    }

    /**
     * Get the content /resources for a site
     *
     * @param sessionId todo
     * @param siteId todo
     * @param fromDate todo
     * @return The xml result string
     * @throws org.apache.axis.AxisFault todo
     */
    public String getResourcesForSite(String sessionId, String siteId, String fromDate) throws AxisFault {
        this.establishSession(sessionId);
        Date filterDate;
        if (!StringUtils.isEmpty(fromDate)) {
            filterDate = new Date(Long.parseLong(fromDate));
        } else {
            filterDate = new Date(0);
        }
        Document dom = Xml.createDocument();
        Node list = dom.createElement("list");
        dom.appendChild(list);
        String collectionId = contentHostingService.getSiteCollection(siteId);
        try {
            LOG.debug(" CHECKING Resource SITEID : " + siteId);
            ContentCollection contentCollection = contentHostingService.getCollection(collectionId);
            if (!filterDate.equals(new Date(0))) {
                handleDeletedResources(dom, list, contentCollection, siteId, fromDate);
            }
            parseContentCollection(dom, list, contentCollection, siteId, filterDate);
        } catch (Exception e) {
            LOG.error("Could not retrieve the resources.", e);
            throw new AxisFault("Could not retrieve the resources.\n" + Arrays.toString(e.getStackTrace()), e);
        }
        return Xml.writeDocumentToString(dom);
    }

    /**
     * Get the Session related to the given session id
     *
     * @param sessionid	the id of the session to retrieve
     * @return	the session, if it is active
     * @throws AxisFault	if session is inactive
     */
    private Session establishSession(String sessionid) throws AxisFault {
        Session s = null;
        try {
            s = sessionManager.getSession(sessionid);

            if (s == null) {
                throw new AxisFault("Session \"" + sessionid + "\" is not active");
            }
            s.setActive();
            sessionManager.setCurrentSession(s);
        } catch (Exception e) {
            LOG.error(this.getClass().getName(), e);
        }
        return s;
    }

    /**
     * Add folder resources to the return dom if they are new/changed/released
     * 
     * From here we will also add files or sub folders recursively
     * 
     * @param dom
     * @param list
     * @param contentCollection
     * @param parentId
     * @param fromDate
     * @return  
     */
    private int parseContentCollection(Document dom, Node list, ContentCollection contentCollection, String parentId, Date fromDate) {
        int count = 0;
        Element resource = dom.createElement("resource");
        resource.setAttribute("id", contentCollection.getId());
        resource.setAttribute("parent-id", parentId);
        resource.setAttribute("reference", contentCollection.getReference());
        String[] ref = contentCollection.getReference().split("/");
        resource.setAttribute("name", ref[ref.length - 1]);
        resource.setAttribute("access", getAccess(contentCollection));
        resource.setAttribute("resource-type", contentCollection.getResourceType());
        resource.setAttribute("url", contentCollection.getUrl());
        resource.setAttribute("release-date", convertSakaiTimeToDate(contentCollection.getReleaseDate()));
        resource.setAttribute("retract-date", convertSakaiTimeToDate(contentCollection.getRetractDate()));
        ResourceProperties properties = contentCollection.getProperties();
        resource.setAttribute("title", properties.getProperty(properties.getNamePropDisplayName()));
        resource.setAttribute("description", properties.getProperty(properties.getNamePropDescription()));
        String userId = properties.getProperty(properties.getNamePropCreator());
        resource.setAttribute("created-by", userId);
        resource.setAttribute("created-by-display-name", getUserDisplayName(userId));
        resource.setAttribute("created", convertSakaiTimeToDate(properties.getProperty(properties.getNamePropCreationDate())));
        resource.setAttribute("last-changed", convertSakaiTimeToDate(properties.getProperty(properties.getNamePropModifiedDate())));
        userId = properties.getProperty(properties.getNamePropModifiedBy());
        resource.setAttribute("last-changed-by", userId);
        resource.setAttribute("last-changed-by-display-name", getUserDisplayName(userId));
        resource.setAttribute("is-collection", "true");
        resource.setAttribute("type", "resource/collection");
        resource.setAttribute("size", String.valueOf(contentCollection.getMemberCount()));
        // Tree walk into all children
        List<ContentEntity> memberResource = contentCollection.getMemberResources();
        for (ContentEntity contentEntity : memberResource) {
            if (contentEntity.isCollection()) {
                count = count + parseContentCollection(dom, list, (ContentCollection) contentEntity, contentCollection.getId(), fromDate);
            } else {
                count = count + parseContentResource(dom, list, (ContentResource) contentEntity, contentCollection.getId(), fromDate);
            }
        }

        // Check if folder itself has changed
        if (addResource(list, resource, fromDate, contentCollection) == 0 && count > 0) {
            list.appendChild(resource);
        }
        return count;
    }

    /**
     * Add files resources to the return dom if they are new/changed/released
     * 
     * @param dom
     * @param list
     * @param contentResource
     * @param parentId
     * @param fromDate
     * @return 
     */
    private int parseContentResource(Document dom, Node list, ContentResource contentResource, String parentId, Date fromDate) {
        Element resource = dom.createElement("resource");
        resource.setAttribute("id", contentResource.getId());
        resource.setAttribute("parent-id", parentId);
        resource.setAttribute("reference", contentResource.getReference());
        String[] ref = contentResource.getReference().split("/");
        resource.setAttribute("name", ref[ref.length - 1]);
        resource.setAttribute("access", getAccess(contentResource));
        resource.setAttribute("resource-type", contentResource.getResourceType());
        resource.setAttribute("url", contentResource.getUrl());
        resource.setAttribute("release-date", convertSakaiTimeToDate(contentResource.getReleaseDate()));
        resource.setAttribute("retract-date", convertSakaiTimeToDate(contentResource.getRetractDate()));
        ResourceProperties properties = contentResource.getProperties();
        resource.setAttribute("description", properties.getProperty(properties.getNamePropDescription()));
        String userId = properties.getProperty(properties.getNamePropCreator());
        resource.setAttribute("created-by", userId);
        resource.setAttribute("created-by-display-name", getUserDisplayName(userId));
        resource.setAttribute("created", convertSakaiTimeToDate(properties.getProperty(properties.getNamePropCreationDate())));
        resource.setAttribute("last-changed", convertSakaiTimeToDate(properties.getProperty(properties.getNamePropModifiedDate())));
        resource.setAttribute("title", properties.getProperty(properties.getNamePropDisplayName()));
        userId = properties.getProperty(properties.getNamePropModifiedBy());
        resource.setAttribute("last-changed-by", userId);
        resource.setAttribute("last-changed-by-display-name", getUserDisplayName(userId));
        resource.setAttribute("is-collection", "false");
        resource.setAttribute("type", contentResource.getContentType());
        if ("text/url".equals(contentResource.getContentType())) {
            try {
                String linkURL = new String(contentResource.getContent(), StandardCharsets.UTF_8);
                resource.setAttribute("linkURL", linkURL);
            }catch(Exception olEx){
                LOG.error(this.getClass().getName(), olEx);
            }
        }
        resource.setAttribute("size", String.valueOf(contentResource.getContentLength()));

        return addResource(list, resource, fromDate, contentResource);
    }
    
    /**
     * Check if resource must be deleted or shown according to release retract
     * dates and previous checked date
     * 
     * @param list
     * @param resource
     * @param fromDate
     * @return 1 if resource added, 0 if resource skipped
     */
    private int addResource(Node list, Element resource, Date fromDate, ContentEntity contentEntity) {
        Date comparisonDate = getDateAttribute(resource, "last-changed", "created");
        Date releaseDate = getDateAttribute(resource, "release-date");
        Date retractDate = getDateAttribute(resource, "retract-date");
        Date currentDate = new Date();
        // First check if any resource became available because of release date
        if (!fromDate.equals(new Date(0)) && releaseDate != null 
                && fromDate.before(releaseDate) && currentDate.after(releaseDate) 
                && (retractDate == null || currentDate.before(retractDate))) {
            list.appendChild(resource);
            LOG.debug("Added Resource - release date" + resource.getAttribute("id"));
            return 1;
        // Check if any resource should be retracted that has previously been checked
        } else if (!fromDate.equals(new Date(0)) && retractDate != null 
                && fromDate.before(retractDate) && currentDate.after(retractDate)) {
            resource.setAttribute("status", DELETED);
            list.appendChild(resource);
            LOG.debug("Added Resource - DELETE for retract date" + resource.getAttribute("id"));
            return 1;
        // Check any new/changed resources - they should be within release/retract
        } else if ((comparisonDate == null || comparisonDate.after(fromDate)) &&
                (releaseDate == null || currentDate.after(releaseDate)) && 
                (retractDate == null || currentDate.before(retractDate))) {
            if (contentEntity.isHidden()) {
                resource.setAttribute("status", DELETED);
            }
            list.appendChild(resource);
            LOG.debug("Added Resource " + resource.getAttribute("id"));
            return 1;
        }
        return 0;
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
     * Handles deleted resources. Adds a resource entry to the dom with the
     * deleted resource id and a status of DELETED. Check on modified date to
     * filter.
     *
     * NOTE : We would have liked to use the Sakai API. But were forced to query
     * the DB directly to get DELETED_DATE. This might break in future Sakai
     * releases if the DB structure changes
     *
     * @param dom
     * @param list
     * @param contentCollection
     * @param parentId
     * @param fromDate
     */
    private void handleDeletedResources(Document dom, Node list, ContentCollection contentCollection, String siteId, String fromDate) throws AxisFault {
        if (!fromDate.isEmpty()) {

            Connection conn = null;
            PreparedStatement prepStmtDeletions = null;
            String collectionId = contentCollection.getId();

            try {
                fromDate = fromDate.trim().length() == 0 ? "0" : fromDate;
                conn = sqlService.borrowConnection();
                prepStmtDeletions = conn.prepareStatement("select RESOURCE_ID, RESOURCE_TYPE_ID from CONTENT_RESOURCE_DELETE "
                        + "where CONTEXT = ? AND IN_COLLECTION like ? AND DELETE_DATE >= ?");
                prepStmtDeletions.setString(1, siteId);
                prepStmtDeletions.setString(2, collectionId + "%");
                prepStmtDeletions.setTimestamp(3, new java.sql.Timestamp(Long.parseLong(fromDate)));
                ResultSet rset = prepStmtDeletions.executeQuery();
                while (rset.next()) {
                    Element resource = dom.createElement("resource");
                    resource.setAttribute("id", getDeletedID(rset.getString("RESOURCE_ID"), rset.getString("RESOURCE_TYPE_ID")));
                    resource.setAttribute("parent-id", collectionId);
                    resource.setAttribute("status", DELETED);
                    list.appendChild(resource);
                    LOG.debug("ADDED Deleted Resource " + rset.getString("RESOURCE_ID"));
                }
            } catch (Exception e) {
                LOG.error("Error retrieving the deleted resources : " + e.getMessage() + " : " + Thread.currentThread().getStackTrace()[1], e);
                throw new AxisFault("Error retrieving the Resource deletions data.", e);
            } finally {
                closeSqlObject(prepStmtDeletions);
                closeSqlObject(conn);
            }
        }
    }

    /**
     * Sakai stores a different id for some resource types in the deleted table.
     * We have to fix this
     *
     * @param id
     * @param resourceType
     * @return
     */
    private String getDeletedID(String id, String resourceType) {
        if (resourceType == null || resourceType.contains("TextDocumentType")
                || resourceType.contains("HtmlDocumentType")) {
            id = id.substring(0, id.lastIndexOf("."));
        }
        return id;
    }

    /**
     * Checks if object is null and closes it
     *
     * @param sqlObject
     */
    private void closeSqlObject(Object sqlObject) {
        try {
            if (sqlObject != null) {
                if (sqlObject instanceof Statement) {
                    ((Statement) sqlObject).close();
                }
                if (sqlObject instanceof Connection) {
                    ((Connection) sqlObject).close();
                }
            }
        } catch (Exception e) {
            LOG.error("Error closing sql object in WelcomeTool.", e);
        }
    }

    /**
     * The dates are of an abstract Sakai specific
     * format, they can be empty or contain a long value which must be
     * reformatted to return a standard date in long format
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
     * Retrieve the actual resource data when given the following
     *
     * @param sessionId todo
     * @param resourceId todo
     * @return XML string with data base 64 encoded
     * @throws org.apache.axis.AxisFault todo
     */
    public String getResourceData(String sessionId, String resourceId) throws AxisFault {
        this.establishSession(sessionId);
        Document dom = Xml.createDocument();
        Node resource = dom.createElement("resource");
        dom.appendChild(resource);
        try {
            ContentResource contentResource = contentHostingService.getResource(resourceId);
            Element eleContent = dom.createElement("content");
            eleContent.setAttribute("id", resourceId);
            resource.appendChild(eleContent);
            eleContent.setTextContent(new String(Base64.encodeBase64(contentResource.getContent())));
        } catch (Exception e) {
            LOG.error("Resource is invalid or missing.", e);
            throw new AxisFault("Resource is invalid or missing.", e);
        }
        return Xml.writeDocumentToString(dom);
    }

    /**
     * Retrieve the resource URL when given the following
     *
     * @param sessionId todo
     * @param resourceId todo
     * @return XML string with data base 64 encoded
     * @throws org.apache.axis.AxisFault todo
     */
    public String getResourceURL(String sessionId, String resourceId) throws AxisFault {
        this.establishSession(sessionId);
        Document dom = Xml.createDocument();
        Node resource = dom.createElement("resource");
        dom.appendChild(resource);
        try {
            ContentResource contentResource = contentHostingService.getResource(resourceId);
            Element eleContent = dom.createElement("url");
            eleContent.setAttribute("id", resourceId);
            resource.appendChild(eleContent);
            eleContent.setTextContent(contentResource.getUrl());
        } catch (Exception e) {
            LOG.error("Resource is invalid or missing.", e);
            throw new AxisFault("Resource is invalid or missing.", e);
        }
        return Xml.writeDocumentToString(dom);
    }

    /**
     * Gets the display name for a given user
     *
     * Differs from original above as that one uses the session to get the
     * displayname hence you must know this in advance or be logged in to the
     * web services with that user. This uses a userid as well so we could be
     * logged in as admin and retrieve the display name for any user.
     *
     * @param	userid	the login username (ie jsmith26) of the user you want the
     * display name for
     * @return	the display name for the user
     * @throws	AxisFault
     *
     */
    private String getUserDisplayName(String userid) {
        String name = users.get(userid);
        if (name != null) {
            return name;
        }
        try {
            User user = userDirectoryService.getUser(userid);
            name = user.getDisplayName();
        } catch (Exception e) {
            LOG.warn("Could not get the display name: " + userid, e);
            name = "";
        }
        users.put(userid, name);
        return name;
    }

    private String getAccess(ContentEntity contentEntity) {
        GroupAwareEntity.AccessMode accessMode = contentEntity.getAccess();
        if (GroupAwareEntity.AccessMode.INHERITED.toString().equals(accessMode.toString())) {
            accessMode = contentEntity.getInheritedAccess();
        }
        return accessMode.toString();
    }
}