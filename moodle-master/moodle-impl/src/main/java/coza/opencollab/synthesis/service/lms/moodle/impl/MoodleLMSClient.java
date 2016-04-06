package coza.opencollab.synthesis.service.lms.moodle.impl;

import static coza.opencollab.synthesis.service.api.ErrorCodes.*;
import coza.opencollab.synthesis.SynthesisException;
import coza.opencollab.synthesis.service.api.ModuleService;
import coza.opencollab.synthesis.service.api.lms.LMSClient;
import coza.opencollab.synthesis.service.api.lms.LMSParser;
import coza.opencollab.synthesis.service.api.lms.ToolContentService;
import coza.opencollab.synthesis.service.api.util.SessionManager;
import coza.opencollab.synthesis.shared.Module;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * The Moodle CLE implementation of a lms client for Synthesis.
 *
 * @author OpenCollab
 * @since 1.0.0
 */
public class MoodleLMSClient implements LMSClient {

    /**
     * The logger.
     */
    private static final Logger LOG = Logger.getLogger(MoodleLMSClient.class);
    /**
     * The parser for the lms data.
     */
    @Autowired
    private LMSParser parser;
    /*
     * The session manager.
     */
    @Autowired
    private SessionManager sessionManager;
    /*
     * The Moodle module service
     */
    private ModuleService moduleService;
    /**
     * The converters for tool data.
     */
    private Map<String, ToolContentService> toolDataConverters;

    /**
     * The converters for tool data.
     */
    public void setToolDataConverters(Map<String, ToolContentService> toolDataConverters) {
        this.toolDataConverters = toolDataConverters;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String login(String username, String password) {
        return sessionManager.login(username, password);
    }

    /**
     * A helper method to login as the administrator.
     * <p>
     * This method might log in to the lms again or just return a valid login.
     */
    private String loginAsAdmin() {
        return sessionManager.loginAsAdmin();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean logout(String username) {
        return sessionManager.logout(username);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Map<String, String> getUserDetails(String username) {
        return sessionManager.getUserDetails(username);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Module> getSitesUserCanAccess(String username) {
        String sessionId = loginAsAdmin();
        String json = null;
        try {
        	//XXX : note that the session id is the id of admin, hence the
        	// modules returned here are the modules admin are enrolled in
            json = moduleService.getSitesUserCanAccess(sessionId, username);
        } catch (Exception e) {
            throw new SynthesisException(LMS_MODULES, "Could not retrieve the modules from Moodle for user (" + username + ").", e);
        }
        return parser.parseModules(json);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Module> getSitesUserCanAccess(String username, String password) {
        String sessionId = login(username, password);
        String json = null;
        try {
        	
            json = moduleService.getSitesUserCanAccess(sessionId, username);
        } catch (Exception e) {
            throw new SynthesisException(LMS_MODULES, "Could not retrieve the modules from Moodle for user (" + username + ").", e);
        }
        return parser.parseModules(json);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public Object getToolContent(String moduleId, String toolName, Date fromDate) {
        //get tool content converter
        ToolContentService converter = toolDataConverters.get(toolName);
        if (converter == null) {
            LOG.warn("There is no converter for the tool (" + toolName + ").");
            return null;
        }
        return converter.getContent(loginAsAdmin(), moduleId, fromDate);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Object getUserContent(String username, String moduleId, String toolName, Date fromDate) {
        //get tool content converter
        ToolContentService converter = toolDataConverters.get(toolName);
        if (converter == null) {
            LOG.warn("No converter for tool (" + toolName + ").");
            return null;
        }
        return converter.getUserContent(loginAsAdmin(), username, moduleId, fromDate);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Map<String, ?> updateUserContent(String username, String moduleId, String toolName, Map<String, ?> content, String originalContent) {
        //get tool content converter
        ToolContentService converter = toolDataConverters.get(toolName);
        if (converter == null) {
            LOG.warn("No converter for tool (" + toolName + ").");
            return null;
        }
        return converter.updateUserContent(loginAsAdmin(), username, moduleId, content, originalContent);
    }

    public ModuleService getModuleService() {
        return moduleService;
    }

    public void setModuleService(ModuleService moduleService) {
        this.moduleService = moduleService;
    }
   
}
