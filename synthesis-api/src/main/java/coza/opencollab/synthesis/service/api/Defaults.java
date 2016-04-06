package coza.opencollab.synthesis.service.api;

import java.nio.charset.Charset;
import java.util.Locale;

/**
 * Just some random used defaults
 *
 * @author OpenCollab
 * @version 1.0.0
 */
public interface Defaults {
    /**
     * The default locale.
     */
    public static final Locale DEFAULT_LOCALE = Locale.getDefault();
    /**
     * The default charset.
     */
    public static final Charset UTF8 = Charset.forName("UTF-8");  
    /**
     * The query parameter.
     */
    public static final String PARAMETER_TOOL_NAME = "toolName";
    /**
     * The query parameter.
     */
    public static final String PARAMETER_TOOL_VERSION = "toolVersion";
    /**
     * The query parameter.
     */
    public static final String PARAMETER_MODULE_ID = "moduleId";
    /**
     * The query parameter.
     */
    public static final String PARAMETER_MODULE_REGISTRATION_ID = "moduleRegistrationId";
    /**
     * The query parameter.
     */
    public static final String PARAMETER_USERNAME = "username";

}
