package coza.opencollab.synthesis.service.util.impl;

import coza.opencollab.synthesis.SynthesisException;
import coza.opencollab.synthesis.service.api.LoginService;
import static coza.opencollab.synthesis.service.api.ErrorCodes.LMS_CONTENT;
import static coza.opencollab.synthesis.service.api.ErrorCodes.LMS_LOGIN;
import coza.opencollab.synthesis.service.api.lms.LMSParser;
import coza.opencollab.synthesis.service.api.util.SessionManager;
import java.util.Calendar;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Implementation of session manager that caches session ids
 * so that the login call to the service is not called unnecessarily.
 *
 * @author OpenCollab
 * @since 1.0.0
 * @version 1.0.1
 */
public class CachedSessionManager implements SessionManager{
    /**
     * One minute
     */
    private static final Long MINUTE = 1000L * 60L;
    /**
     * One Day
     */
    private static final Long DAY = MINUTE * 60L * 24L;
    /**
     * The session timeout in minutes so we can cache the sessions
     */
    private long sessionTimeout = MINUTE;
    /**
     * The timer used to clean sessions.
     */
    private Timer timer;
    /**
     * The current sessions.
     */
    private Map<String, String[]> sessions = new ConcurrentHashMap<String, String[]>();
    /**
     * A locking object.
     */
    private NamedLocker locker = new NamedLocker(1);
    /*
     * The LMS login service
     */
    @Autowired
    private LoginService loginService;
    /**
     * The parser for the lms data.
     */
    @Autowired
    private LMSParser parser;
    /**
     * The admin username.
     */
    private String adminUsername;
    /**
     * The admin password.
     */
    private String adminPassword;

    /**
     * The session timeout in minutes so we can cache the sessions
     *
     * @param sessionTimeout a long.
     */
    public void setSessionTimeout(long sessionTimeout) {
        this.sessionTimeout = sessionTimeout * MINUTE;
    }

    /*
     * The LMS login service
     */
    /**
     * <p>Setter for the field <code>loginService</code>.</p>
     *
     * @param loginService a {@link coza.opencollab.synthesis.service.api.LoginService} object.
     */
    public void setLoginService(LoginService loginService) {
        this.loginService = loginService;
    }

    /**
     * The parser for the lms data.
     *
     * @param parser a {@link coza.opencollab.synthesis.service.api.lms.LMSParser} object.
     */
    public void setParser(LMSParser parser) {
        this.parser = parser;
    }

    /**
     * The admin username.
     *
     * @param adminUsername a {@link java.lang.String} object.
     */
    public void setAdminUsername(String adminUsername) {
        this.adminUsername = adminUsername;
    }

    /**
     * The admin password.
     *
     * @param adminPassword a {@link java.lang.String} object.
     */
    public void setAdminPassword(String adminPassword) {
        this.adminPassword = adminPassword;
    }
    
    /**
     * A initiation method.
     */
    @PostConstruct
    public void init(){
        timer = new Timer();
        //get date for midnight to clean the sessions once a day...is this good? 
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_YEAR, 1);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        timer.scheduleAtFixedRate(new SessionCleaner(), cal.getTime(), DAY);
    }

    /**
     * A cleanup method.
     */
    @PreDestroy
    public void destroy(){
        timer.cancel();
    }
    
    /**
     * Adds a session. 
     */
    private void addSession(String username, String password, String sessionId) {
        if (sessionTimeout > 0) {
            timer.schedule(new SessionCleaner(username), sessionTimeout);
            sessions.put(username, new String[]{password, sessionId});
        }        
    }
    
    /**
     * Get a session.
     */
    private String getSession(String username, String password) {
        String[] session = sessions.get(username);
        if (session == null || sessionTimeout <= 0) {
            return null;
        } else if (session[0].equals(password)) {
            return session[1];
        } else {
            throw new SynthesisException(LMS_LOGIN, "Could not login the user with the given credentials.");
        }
    }
    
    /**
     * Removes the session.
     */
    private String removeSession(String username) {
        String[] passwordAndSession = sessions.remove(username);
        if (passwordAndSession != null && passwordAndSession.length > 1) {
            return passwordAndSession[1];
        } else {
            return null;
        }
    }
    
    /**
     * Remove all sessions.
     */
    private void removeAllSessions(){
        sessions.clear();
    }
    
    /** {@inheritDoc} */
    @Override
    public String login(String username, String password) {
        try {
            locker.acquireLock(username);
            String sessionId = getSession(username, password);
            if (sessionId != null) {
                return sessionId;
            }
            sessionId = parser.parseSessionId(loginService.login(username, password));
            if (sessionId != null) {
                addSession(username, password, sessionId);
            }
            return sessionId;
        } catch (Exception e) {          
            throw new SynthesisException(LMS_LOGIN, "Could not login to LMS for user " + username + ".", e);
        }finally{
            locker.releaseLock(username);
        }
    }

    /** {@inheritDoc} */
    @Override
    public String loginAsAdmin(){
        return login(adminUsername, adminPassword);
    }

    /** {@inheritDoc} */
    @Override
    public boolean logout(String username) {
        if(username == null || StringUtils.equals(adminUsername, username)){
            return true;
        }
        locker.acquireLock(username);
        try {
            String sessionId = removeSession(username);
            return loginService.logout(sessionId);
        } catch (Exception e) {
            return false;
        }finally{
            locker.releaseLock(username);
        }
    }
    
    /** {@inheritDoc} */
    @Override
    public Map<String, String> getUserDetails(String username){
        String sessionId = loginAsAdmin();
        try {
            return parser.parseUserDetails(loginService.getUserDetails(sessionId, username));
        } catch (Exception e) {
            throw new SynthesisException(LMS_CONTENT, "Could not retrieve the user details from LMS for " + username + ".", e);
        }
    }
    
    /**
     * The cleaner class for the sessions.
     * if the username is null if cleans all sessions
     */
    private class SessionCleaner extends TimerTask{
        private String username;
        
        SessionCleaner(){}
        
        SessionCleaner(String username){
            this.username = username;
        }

        @Override
        public void run() {
            if(username == null){
                removeAllSessions();
            }else{
                locker.acquireLock(username);
                try{
                    removeSession(username);
                    this.cancel();
                }finally{
                    locker.releaseLock(username);
                }
            }
        }
    }
}
