package coza.opencollab.synthesis.service.event;

/**
 * All available events.
 * <p>
 * Events may be used that is not in this class, this is
 * just for convenience.
 *
 * @author OpenCollab
 * @since 1.0.0
 * @version 1.0.1
 */
public interface EventCodes {
    /** Constant <code>USER_DEVICE_REGISTRATION="uni.user.device.reg"</code> */
    public static final String USER_DEVICE_REGISTRATION = "uni.user.device.reg";
    /** Constant <code>USER_DEVICE_REACTIVATE="uni.user.device.reactivate"</code> */
    public static final String USER_DEVICE_REACTIVATE = "uni.user.device.reactivate";
    /** Constant <code>USER_MODULE_REGISTRATION="uni.user.module.reg"</code> */
    public static final String USER_MODULE_REGISTRATION = "uni.user.module.reg";
    /** Constant <code>USER_MODULE_REACTIVATE="uni.user.module.reactivate"</code> */
    public static final String USER_MODULE_REACTIVATE = "uni.user.module.reactivate";
    /** Constant <code>TOOL_VERSION_SET="uni.tool.version.set"</code> */
    public static final String TOOL_VERSION_SET = "uni.tool.version.set";
    /** Constant <code>TOOL_VERSION_UPDATE="uni.tool.version.update"</code> */
    public static final String TOOL_VERSION_UPDATE = "uni.tool.version.update";
    /** Constant <code>CONTENT_VERSION_SET="uni.content.version.set"</code> */
    public static final String CONTENT_VERSION_SET = "uni.content.version.set";
    /** Constant <code>CONTENT_VERSION_UPDATE="uni.content.version.update"</code> */
    public static final String CONTENT_VERSION_UPDATE = "uni.content.version.update";
    /** Constant <code>CREATOR_RETRIEVE_MODULES="uni.creator.modules"</code> */
    public static final String CREATOR_RETRIEVE_MODULES = "uni.creator.modules";
    /** Constant <code>CREATOR_RETRIEVE_TOOLS="uni.creator.tools"</code> */
    public static final String CREATOR_RETRIEVE_TOOLS = "uni.creator.tools";
    /** Constant <code>CREATOR_ADD_MANAGED_MODULE="uni.add.managed.module"</code> */
    public static final String CREATOR_ADD_MANAGED_MODULE = "uni.add.managed.module";
    /** Constant <code>SYNCH_TOOL_REQUEST="uni.tool.synch.request"</code> */
    public static final String SYNCH_TOOL_REQUEST = "uni.tool.synch.request";
    /** Constant <code>SYNCH_CONTENT_REQUEST="uni.content.synch.request"</code> */
    public static final String SYNCH_CONTENT_REQUEST = "uni.content.synch.request";
}
