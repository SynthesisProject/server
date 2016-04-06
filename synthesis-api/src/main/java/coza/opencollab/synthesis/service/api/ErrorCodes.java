package coza.opencollab.synthesis.service.api;

/**
 * The error code manager.
 * <p>
 * Error Code Ranges:
 * <ol>
 *  <li>1000's: This is exceptions on the server</li>
 *  <li>2000's: This is normal flow problems like login details that are not valid.</li>
 *  <li>3000's: Admin tool</li>
 *  <li>4000's: Client base and tools</li>
 * </ol>
 *
 * @author OpenCollab
 * @since 1.0.0
 * @version 1.0.0
 */
public class ErrorCodes {
        /**
     * General (unset) error. 
     * <p>
     * Could be anything, anywhere.
     */
    public static final int GENERAL = 1000;
    /**
     * This comes from the JsonParser
     * <p>
     * Tried to parse json to a Object. Either the json is not valid or the json cannot be fitted to the object.
     * Look at the source of the json, it is sending invalid json.
     * Example Messages:
     * <ol>
     *  <li>The character encoding is not supported.</li>
     *  <li>Invalid content mapping.</li>
     *  <li>Could not parse the content.</li>
     *  <li>Could not write the object.</li>
     * </ol>
     */
    public static final int JSON_READ = 1001;
    /**
     * This comes from the BaseWeb JsonParser.writeJson
     * Tried to convert a Object to a json string. For some reason it was unsuccessful. Investigate the Object.
     * Example Messages:
     * <ol>
     *  <li>Invalid content mapping.</li>
     *  <li>Could not write content.</li>
     *  <li>Could not write the object.</li>
     * </ol>
     */
    public static final int JSON_WRITE = 1002;
    /**
     * This comes from the EventService.addEvent/addEventForSession /addEventForUser /addEventForUserSession.
     * Either the Sakai service is down or the login credentials is not valid. 
     * Check Sakai service and login credentials.
     * Example Messages:
     * <ol>
     *  <li>Could not post event 'some-event'.</li>
     * </ol>
     */
    public static final int EVENT_SERVICE = 1003;
    /**
     * File manipulation failed. The settings could be incorrect. Check that the code location property is correct.
     * This comes from the CodeService and ContentService.
     * Example Messages:
     * <ol>
     *  <li>Could not create the directory structure for the code location.</li>
     *  <li>Could not copy the tool code to the code location.</li>
     * </ol>
     */
    public static final int FILE_MANIPULATION = 1004;
    /**
     * This comes from the SakaiLMSClient.login.
     * Either the Sakai service is down or the login credentials is not valid. 
     * Check Sakai service and login credentials.
     * Example Messages:
     * <ol>
     *  <li>Could not login to Sakai for user username.</li>
     * </ol>
     */
    public static final int LMS_LOGIN = 1005;
    /**
     * This comes from the SakaiLMSClient.getModules.
     * Either the Sakai service is down or there was a problem retrieving the modules. Check Sakai service.
     * Example Messages: 
     * <ol>
     *  <li>Could not retrieve the modules from Sakai (year-semester-moduleCode).</li>
     * </ol>
     */
    public static final int LMS_MODULES = 1006;
    /**
     * This comes from the SakaiLMSClient.parseModules /parseTools
     * There is a problem reading the sites xml. This is a bug, fix it.
     * Example Messages:
     * <ol>
     *  <li>Could not compile the xpath expression.</li>
     *  <li>No site data for the site.</li>
     * </ol>
     */
    public static final int XML_PARSING = 1007;
    /**
     * This comes from the SakaiLMSClient.getTools.
     * Either the Sakai service is down or there was a problem retrieving the tools. Check Sakai service.
     * Example Messages:
     * <ol>
     *  <li>Could not retrieve the pages and tool from Sakai (moduleId).</li>
     * </ol>
     */
    public static final int LMS_TOOLS = 1008;
    /**
     * This comes from the JPADao.getDeviceRegistration /getMasterDeviceRegistration
     * There is no data in the database for this device. Check that the device is registered and 
     * valid on the Synthesis Service.
     * Example Messages:
     * <ol>
     *  <li>Could not find the device registration for 'username:deviceId'.</li>
     * </ol>
     */
    public static final int DEVICE_REGISTRATION = 1011;
    /**
     * This comes from the getToolVersion
     * The user device is registered but the tool not. Check that the tool is registered and valid on the Synthesis Service
     * Example Messages:
     * <ol>
     *  <li>No tool registration found for 'username:deviceId:toolName'.</li>
     * </ol>
     */
    public static final int TOOL_VERSION = 1012;
    /**
     * This comes from the JPADao.getModuleRegistration
     * The device is registered but the module for the device is not. Check that the module 
     * is registered and valid on the Synthesis Service.
     * Example Messages:
     * <ol>
     *  <li>Could not find the module registration for 'username:deviceId:moduleId'.</li>
     * </ol>
     */
    public static final int MODULE_REGISTRATION = 1013;
    /**
     * This comes from the getContentVersion
     * The user module is registered but the content not. Check that the content is registered 
     * and valid on the Synthesis Service.
     * Example Messages:
     * <ol>
     *  <li>No tool registration found for 'username:deviceId:toolName'.</li>
     * </ol>
     */
    public static final int CONTENT_VERSION = 1015;
    /**
     * The configuration is not correct. Check the spring config.
     * Example Messages:
     * <ol>
     *  <li>The schedule executor is not set.</li>
     * </ol>
     */
    public static final int INCORRECT_CONFIG = 1016;
    /**
     * This is used in the mail service.
     * <p>
     * The mail sender is not configured correctly of failed.
     */
    public static final int MAIL_SERVICE = 1017;
    /**
     * This comes from the SynthesisService service calls.
     * <p>
     * Some service is marked for deprecation. Update the caller to not use that service call.
     * Example Messages:
     * <ol>
     *  <li>"Something" is deprecated and must not be used, it will be removed.</li>
     * </ol>
     */
    public static final int DEPRECATED = 2001;
    /**
     * This comes from the AuthenticationService.login
     * The user credentials is not valid. Use valid LMS Credentials
     * Example Messages:
     * <ol>
     *  <li>Not a valid login.</li>
     * </ol>
     */
    public static final int INVALID_CREDENTIALS = 2002;
    /**
     * This comes from the CodeService.loadTool
     * The values provided is invalid. Give correct values.
     * Example Messages:
     * <ol>
     *  <li>Invalid values....</li>
     * </ol>
     */
    public static final int INVALID_VALUE = 2005;
    /**
     * This comes from the CodeService.loadTool
     * The location provided for a new tool is invalid. The location must be accessible to the server and it must be a directory or zip file containing the tool in the root.
     * Example Messages:
     * <ol>
     *  <li>The location is not valid.</li>
     *  <li>It must be a directory or zip file containing the tool code in the root.</li>
     * </ol>
     */
    public static final int INVALID_LOCATION = 2006;
    /**
     * This comes from the CodeService.loadTool
     * The version is not greater then the previous version.
     * Example Messages:
     * <ol>
     *  <li>The tool version (1.0.2) is not greater then the previous version (1.0.21).</li>
     * </ol>
     */
    public static final int INVALID_VERSION = 2007;
    /**
     * This comes from the AnnouncemnetHandler.
     * A problem getting all the required data for the announcement. This is a bug. please report.
     * Example Messages:
     * <ol>
     *  <li>Invalid or insufficient announcement data.</li>
     * </ol>
     */
    public static final int LMS_CONTENT = 2008;
    /**
     * This comes from the AuthenticationService.register
     * The user is a lms user but he/she is not registered for the module. Send valid user and module data.
     * Example Messages:
     * <ol>
     *  <li>User is not registered for the module.</li>
     * </ol>
     */
    public static final int USER_NOT_REGISTERED_FOR_MODULE = 2009;
    /**
     * This comes from the AuthenticationService.register.
     * The user was registered for the module but for some reason the registration was deactivated.
     * Example Messages:
     * <ol>
     *  <li>User was deactivated for the module.</li>
     * </ol>
     */
    public static final int USER_DEACTIVATED_FOR_MODULE = 2010;
}
