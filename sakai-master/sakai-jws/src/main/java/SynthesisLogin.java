
import org.apache.axis.AxisFault;
import org.apache.axis.Constants;
import org.apache.axis.MessageContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.sakaiproject.component.api.ServerConfigurationService;
import org.sakaiproject.component.cover.ComponentManager;
import org.sakaiproject.event.api.UsageSessionService;
import org.sakaiproject.tool.api.Session;
import org.sakaiproject.tool.api.SessionManager;
import org.sakaiproject.user.api.User;
import org.sakaiproject.user.api.UserDirectoryService;
import org.sakaiproject.user.api.UserNotDefinedException;
import org.sakaiproject.util.Xml;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * SynthesisLogin.jws

 The main authentication web service, performs remote login and logout
 functions for Sakai.
 *
 */
public class SynthesisLogin {

    private static final Log LOG = LogFactory.getLog("org.sakaiproject.axis.SynthesisLogin");
    private ServerConfigurationService serverConfigurationService;
    private SessionManager sessionManager;
    private UsageSessionService usageSessionService;
    private UserDirectoryService userDirectoryService;

    public SynthesisLogin() {
        serverConfigurationService = (ServerConfigurationService) ComponentManager.get(ServerConfigurationService.class.getName());
        sessionManager = (SessionManager) ComponentManager.get(SessionManager.class.getName());
        usageSessionService = (UsageSessionService) ComponentManager.get(UsageSessionService.class.getName());
        userDirectoryService = (UserDirectoryService) ComponentManager.get(UserDirectoryService.class.getName());
    }

    /**
     * Login with the supplied credentials and return the session string which
     * can be used in subsequent web service calls, ie via SakaiScript
     *
     * @param id	eid, eg jsmith26
     * @param pw	password for the user
     * @return	session string
     * @throws AxisFault axis fault
     */
    public String login(String id, String pw) throws AxisFault {

        MessageContext messageContext = MessageContext.getCurrentContext();
        String ipAddress = messageContext.getStrProp(Constants.MC_REMOTE_ADDR);

        boolean allowLogin = serverConfigurationService.getBoolean("webservices.allowlogin", false);

        if (!allowLogin) {
            throw new AxisFault("Web Services Login Disabled");
        }

        User user = userDirectoryService.authenticate(id, pw);
        if (user != null) {
            Session s = sessionManager.startSession();
            sessionManager.setCurrentSession(s);
            if (s == null) {
                LOG.warn("Web Services Login failed to establish session for id=" + id + " ip=" + ipAddress);
                throw new AxisFault("Unable to establish session");
            } else {

                // We do not care too much on the off-chance that this fails - folks simply won't show up in presense
                // and events won't be trackable back to people / IP Addresses - but if it fails - there is nothing
                // we can do anyways.

                usageSessionService.login(user.getId(), id, ipAddress, "SakaiLogin.jws", UsageSessionService.EVENT_LOGIN_WS);

                if (LOG.isDebugEnabled()) {
                    LOG.debug("Sakai Web Services Login id=" + id + " ip=" + ipAddress + " session=" + s.getId());
                }
                return s.getId();
            }
        }
        LOG.warn("Failed Web Services Login id=" + id + " ip=" + ipAddress);
        throw new AxisFault("Unable to login");
    }

    /**
     * Logout of the given session
     *
     * @param sessionid	sessionid to logout
     * @return flag
     * @throws AxisFault axis fault
     * @throws InterruptedException interuppted
     */
    public boolean logout(String sessionid) throws AxisFault, InterruptedException {

        Session s = sessionManager.getSession(sessionid);

        if (s == null) {
            throw new AxisFault("Session " + sessionid + " is not active");
        }

        sessionManager.setCurrentSession(s);
        usageSessionService.logout();

        return true;
    }

    /**
     * Login
     * 
     * @param id id
     * @param pw pw
     * @return todo
     * @throws AxisFault axis fault
     */
    public String loginToServer(String id, String pw) throws AxisFault {
        return login(id, pw) + "," + serverConfigurationService.getString("webservices.directurl", serverConfigurationService.getString("serverUrl"));
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

    /**
     * Retrieve the user details. Email and internal id
     *
     * @param sessionid todo
     * @param id todo
     * @return A xml with property keys and values
     * @throws org.apache.axis.AxisFault axis fault
     */
    public String getUserDetails(String sessionid, String id) throws AxisFault{
        establishSession(sessionid);
        User user;
        try {
            user = userDirectoryService.getUserByEid(id);
        } catch (UserNotDefinedException e) {
            LOG.error("WS getUserDetails() failed for user: " + id, e);
            throw new AxisFault("Could not find user: " + id);
        }
        Document dom = Xml.createDocument();
        Element userNode = dom.createElement("user");
        dom.appendChild(userNode);
        Attr userIdAttr = dom.createAttribute("id");
        userIdAttr.setNodeValue(id);
        userNode.setAttributeNode(userIdAttr);
        Element element = dom.createElement("lms-id");
        element.setTextContent(user.getId());
        userNode.appendChild(element);
        element = dom.createElement("display-name");
        element.setTextContent(user.getDisplayName());
        userNode.appendChild(element);
        element = dom.createElement("first-name");
        element.setTextContent(user.getFirstName());
        userNode.appendChild(element);
        element = dom.createElement("last-name");
        element.setTextContent(user.getLastName());
        userNode.appendChild(element);
        element = dom.createElement("email");
        element.setTextContent(user.getEmail());
        userNode.appendChild(element);
        return Xml.writeDocumentToString(dom);
    }
}
