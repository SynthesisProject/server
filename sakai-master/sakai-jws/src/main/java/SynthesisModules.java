
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import org.apache.axis.AxisFault;
import org.apache.axis.Constants;
import org.apache.axis.MessageContext;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.sakaiproject.authz.api.AuthzGroupService;
import org.sakaiproject.authz.api.Member;
import org.sakaiproject.authz.api.Role;
import org.sakaiproject.authz.api.SecurityService;
import org.sakaiproject.component.cover.ComponentManager;
import org.sakaiproject.event.api.EventTrackingService;
import org.sakaiproject.event.api.UsageSession;
import org.sakaiproject.event.api.UsageSessionService;
import org.sakaiproject.site.api.Site;
import org.sakaiproject.site.api.SitePage;
import org.sakaiproject.site.api.SiteService;
import org.sakaiproject.site.api.ToolConfiguration;
import org.sakaiproject.tool.api.Session;
import org.sakaiproject.tool.api.SessionManager;
import org.sakaiproject.tool.api.ToolManager;
import org.sakaiproject.user.api.User;
import org.sakaiproject.user.api.UserDirectoryService;
import org.sakaiproject.util.Xml;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
/**
 * This is the service script to handle the modules/sites
 * 
 * @author OpenCollab
 * @since 1.0.0
 */
public class SynthesisModules {
    private static final String SESSION_ATTR_NAME_ORIGIN = "origin";
    private static final String SESSION_ATTR_VALUE_ORIGIN_WS = "sakai-axis";
    private static final Log LOG = LogFactory.getLog(SynthesisModules.class);
    private AuthzGroupService authzGroupService;
    private EventTrackingService eventTrackingService;
    private SecurityService securityService;
    private SessionManager sessionManager;
    private SiteService siteService;
    private ToolManager toolManager;
    private UsageSessionService usageSessionService;
    private UserDirectoryService userDirectoryService;
    private String versionDateFormat = "yyyyMMddHHmmssSSS";
    
    /**
     * Setup dependencies
     */
    public SynthesisModules() {
        authzGroupService = (AuthzGroupService) ComponentManager.get(AuthzGroupService.class.getName());
        eventTrackingService = (EventTrackingService) ComponentManager.get(EventTrackingService.class.getName());
        securityService = (SecurityService) ComponentManager.get(SecurityService.class.getName());
        sessionManager = (SessionManager) ComponentManager.get(SessionManager.class.getName());
        siteService = (SiteService) ComponentManager.get(SiteService.class.getName());
        toolManager = (ToolManager) ComponentManager.get(ToolManager.class.getName());
        usageSessionService = (UsageSessionService) ComponentManager.get(UsageSessionService.class.getName());
        userDirectoryService = (UserDirectoryService) ComponentManager.get(UserDirectoryService.class.getName());
    }

    /**
     * Get the Session related to the given session id
     *
     * @param sessionid	the id of the session to retrieve
     * @return	the session, if it is active
     * @throws AxisFault if session is inactive
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
     * Creates and returns the session ID for a given user.
     *
     * The sessionId argument must be a valid session for a super user ONLY otherwise it will fail. 
     * The userId argument must be the EID (ie jsmith) of a valid user. This new sessionId can 
     * then be used with getSitesUserCanAccess() to get the sites for the given user.
     *
     * @param	sessionId The sessionId of a valid session for a super user
     * @param	eId	the eId of the user you want to create a session for
     * @param	wsOnly	should the session created be tied to the web services only? the initial 
     * implementation of this will just set an attribute on the Session that identifies it as originating 
     * from the web services. but in essence it will be just like a normal session. However this attribute 
     * is used elsewhere for filtering for these web service sessions.
     * 
     * @return	the sessionId for the user specified
     * @throws	AxisFault If any data is missing, not super user, or session cannot be established
     */
    public String getSessionForUser(String sessionId, String eId, boolean wsOnly) throws AxisFault {
        Session session = establishSession(sessionId);
        //check that ONLY super user's are accessing this	
        if (!securityService.isSuperUser(session.getUserId())) {
            LOG.warn("Permission denied. Restricted to super users.");
            throw new AxisFault("Permission denied. Restricted to super users.");
        }
        try {
            //check for empty userId
            if (StringUtils.isBlank(eId)) {
                LOG.warn("Param eId empty.");
                throw new AxisFault("Param eid empty.");
            }
            //if dealing with web service sessions, re-use is ok
            if (wsOnly) {
                //do we already have a web service session for the given user? If so, reuse it.
                List<Session> existingSessions = sessionManager.getSessions();
                for (Session existingSession : existingSessions) {
                    if (StringUtils.equals(existingSession.getUserEid(), eId)) {
                        //check if the origin attribute, if set, is set for web services
                        String origin = (String) existingSession.getAttribute(SESSION_ATTR_NAME_ORIGIN);
                        if (StringUtils.equals(origin, SESSION_ATTR_VALUE_ORIGIN_WS)) {
                            LOG.warn("Reusing existing session for: " + eId + ", session=" + existingSession.getId());
                            return existingSession.getId();
                        }
                    }
                }
            }
            //get ip address for establishing session
            MessageContext messageContext = MessageContext.getCurrentContext();
            String ipAddress = messageContext.getStrProp(Constants.MC_REMOTE_ADDR);
            //start a new session
            Session newsession = sessionManager.startSession();
            sessionManager.setCurrentSession(newsession);
            //inject this session with new user values
            User user = userDirectoryService.getUserByEid(eId);
            newsession.setUserEid(eId);
            newsession.setUserId(user.getId());
            //if wsonly, inject the origin attribute
            if (wsOnly) {
                newsession.setAttribute(SESSION_ATTR_NAME_ORIGIN, SESSION_ATTR_VALUE_ORIGIN_WS);
                LOG.warn("Set origin attribute on session: " + newsession.getId());
            }

            //register the session with presence
            UsageSession usagesession = usageSessionService.startSession(user.getId(), ipAddress, "SynthesisModules.jws getSessionForUser()");

            // update the user's externally provided realm definitions
            authzGroupService.refreshUser(user.getId());

            // post the login event
            eventTrackingService.post(eventTrackingService.newEvent(UsageSessionService.EVENT_LOGIN_WS, null, true));

            LOG.warn("OK. Established session for userid=" + eId + ", session=" + newsession.getId() + ", ipAddress=" + ipAddress);
            return newsession.getId();
        } catch (Exception e) {
            throw new AxisFault("Could not establish the new session", e);
        }
    }

    /**
     * Return XML document listing all sites for the module code.
     *
     * @param sessionid the session id of a admin user
     * @param criteria The criteria, ex. module code to filter the names on.
     * @return	The return XML format is below:
     * @throws org.apache.axis.AxisFault axis fault
     */
    public String getSitesForCriteria(String sessionid, String criteria) throws AxisFault {
        establishSession(sessionid);
        try {
            List<Site> sites = siteService.getSites(SiteService.SelectionType.ANY, null, criteria, null, SiteService.SortType.TITLE_ASC, null);
            
            return buildSitesDocument(sites);
        } catch (Exception e) {
            LOG.error("Could not get the sites for the module code.", e);
            throw new AxisFault("Could not get the sites for the module code.", e);
        }
    }

    /**
     * Return XML document listing all sites the given user has read access to.
     *
     * @param sessionId The session id of a super user
     * @param userId eid (eg jsmith26) if the user you want the list for
     * @return See getSitesUserCanAccess(sessionId)
     * @throws AxisFault If not super user or any other error occurs from main method
     */
    public String getSitesUserCanAccess(String sessionId, String userId) throws AxisFault {
        //get a session for the other user, reuse if possible
        String newSessionId = getSessionForUser(sessionId, userId, true);
        //might be an exception that was returned, so check session is valid.
        establishSession(newSessionId);
        //ok, so hand over to main method to get the list for us
        return getSitesUserCanAccess(newSessionId);
    }

    /**
     * Return XML document listing all sites user has read access based on their session id.
     *
     * @param sessionId The session id of a user who's list of sites you want to retrieve
     * @return	xml or an empty list
     * @throws org.apache.axis.AxisFault axis fault
     */
    public String getSitesUserCanAccess(String sessionId) throws AxisFault {
        establishSession(sessionId);
        try {
            List sites = siteService.getSites(SiteService.SelectionType.ACCESS, null, null, null, SiteService.SortType.TITLE_ASC, null);

            return buildSitesDocument(sites);
        } catch (Exception e) {
            LOG.error("Could not get the sites the user can access.", e);
            throw new AxisFault("Could not get the sites the user can access.", e);
        }
    }

    /**
     * Return XML document listing all pages and tools in those pages for a
     * given site. The session id must be of a valid, active user in that site,
     * or a super user, or it will throw an exception. If a page is hidden in a
     * site, the page and all tools in that page will be skipped from the
     * returned document, as they are in the portal. Super user's can request
     * any site to retrieve the full list.
     *
     * @param sessionid the session id of a user in a site, or a super user
     * @param siteid the site to retrieve the information for
     * @return	xml or an empty list. The return XML format is below:
     * @throws	AxisFault if not a super user and the user attached to the
     * session is not in the site, if site does not exist
     */
    public String getPagesAndToolsForSite(String sessionid, String siteid) throws AxisFault {
        Session session = establishSession(sessionid);

        //check if site exists
        Site site;
        try {
            site = siteService.getSite(siteid);
        } catch (Exception e) {
            LOG.warn("Error looking up site: " + siteid, e);
            throw new AxisFault("Error looking up site: " + siteid, e);
        }

        String userId = session.getUserId();

        //check if super user
        boolean isSuperUser = false;
        if (securityService.isSuperUser(userId)) {
            isSuperUser = true;
        }

        //if not super user, check user is a member of the site, and get their Role
        Role role;
        if (!isSuperUser) {
            Member member = site.getMember(userId);
            if (member == null || !member.isActive()) {
                LOG.warn("User: " + userId + " does not exist in site : " + siteid);
                throw new AxisFault("User: " + userId + " does not exist in site : " + siteid);
            }
            role = member.getRole();
        }



        //get list of pages in the site, if none, return empty list
        List<SitePage> pages = site.getPages();
        if (pages.isEmpty()) {
            return "<site id=\"" + site.getId() + "\"><site>";
        }

        //site node
        Document dom = Xml.createDocument();
        Element siteNode = dom.createElement("site");
        Attr siteIdAttr = dom.createAttribute("id");
        siteIdAttr.setNodeValue(site.getId());
        siteNode.setAttributeNode(siteIdAttr);

        //pages node
        Element pagesNode = dom.createElement("pages");

        for (SitePage page : pages) {

            //page node
            Element pageNode = dom.createElement("page");
            Attr pageIdAttr = dom.createAttribute("id");
            pageIdAttr.setNodeValue(page.getId());
            pageNode.setAttributeNode(pageIdAttr);

            //pageTitle
            Element pageTitleNode = dom.createElement("page-title");
            pageTitleNode.appendChild(dom.createTextNode(page.getTitle()));

            //get tools in page
            List<ToolConfiguration> tools = page.getTools();

            Element toolsNode = dom.createElement("tools");

            boolean includePage = true;
            for (ToolConfiguration toolConfig : tools) {
                //if we not a superAdmin, check the page properties
                //if any tool on this page is hidden, skip the rest of the tools and exclude this page from the output
                //this makes the behaviour consistent with the portal

                //if not superUser, process  tool function requirements
                if (!isSuperUser) {

                    //skip processing tool if we've skipped tools previously on this page
                    if (!includePage) {
                        continue;
                    }

                    //skip this tool if not visible, ultimately hiding the whole page
                    if (!toolManager.isVisible(site, toolConfig)) {
                        includePage = false;
                        break;
                    }
                }

                //if we got this far, add the details about the tool to the document
                Element toolNode = dom.createElement("tool");

                //tool uuid
                Attr toolIdAttr = dom.createAttribute("id");
                toolIdAttr.setNodeValue(toolConfig.getId());
                toolNode.setAttributeNode(toolIdAttr);

                //registration (eg sakai.profile2)
                Element toolIdNode = dom.createElement("tool-id");
                toolIdNode.appendChild(dom.createTextNode(toolConfig.getToolId()));
                toolNode.appendChild(toolIdNode);

                Element toolTitleNode = dom.createElement("tool-title");
                toolTitleNode.appendChild(dom.createTextNode(toolConfig.getTitle()));
                toolNode.appendChild(toolTitleNode);

                toolsNode.appendChild(toolNode);

            }

            //if the page is not hidden, add the elements
            if (includePage) {
                pageNode.appendChild(pageTitleNode);
                pageNode.appendChild(toolsNode);
                pagesNode.appendChild(pageNode);
            }
        }

        //add the main nodes
        siteNode.appendChild(pagesNode);
        dom.appendChild(siteNode);

        return Xml.writeDocumentToString(dom);
    }
    
    private String buildSitesDocument(List<Site> sitesList) {
        if (sitesList == null || sitesList.isEmpty()) {
            return "<sites></sites>";
        }
        Document dom = Xml.createDocument();
        Node sites = dom.createElement("sites");
        dom.appendChild(sites);

        for (Site siteObj : sitesList) {
            Node site = dom.createElement("site");
            Node siteId = dom.createElement("id");
            siteId.appendChild(dom.createTextNode(siteObj.getId()));
            Node siteTitle = dom.createElement("title");
            siteTitle.appendChild(dom.createTextNode(siteObj.getTitle()));
            Node siteDescription = dom.createElement("description");
            siteDescription.appendChild(dom.createTextNode(siteObj.getDescription()));
            Node siteShortDescription = dom.createElement("shortDescription");
            siteShortDescription.appendChild(dom.createTextNode(siteObj.getShortDescription()));
            Node siteCreatedDate = dom.createElement("createdDate");
            String createdDate = siteObj.getCreatedDate()==null?"0":(new SimpleDateFormat(versionDateFormat)).format(siteObj.getCreatedDate());
            siteCreatedDate.appendChild(dom.createTextNode(String.valueOf(createdDate)));

            site.appendChild(siteId);
            site.appendChild(siteTitle);
            site.appendChild(siteDescription);
            site.appendChild(siteShortDescription);
            site.appendChild(siteCreatedDate);
            sites.appendChild(site);
        }

        return Xml.writeDocumentToString(dom);
    }
}
