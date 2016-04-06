package coza.opencollab.synthesis.service.event.impl;

import static coza.opencollab.synthesis.service.api.ErrorCodes.*;
import coza.opencollab.synthesis.SynthesisException;
import coza.opencollab.synthesis.service.event.EventService;
import coza.opencollab.synthesis.service.api.util.SessionManager;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * {@inheritDoc}
 *
 * @author OpenCollab
 * @since 1.0.0
 * @version 1.0.1
 */
public class LMSEventServiceImpl implements EventService{
    /**
     * The logger
     */
    private static final Logger log = Logger.getLogger("EVENT_SERVICE");
    /**
     * The message to use in exceptions.
     */
    private static final String EXCEPTION_MESSAGE = "Could not post the event {2}. [ref={3}:context={4}:modified={5}:username={0}:sessionId={1}]";
    /*
     * The Sakai event service
     */
    @Autowired
    private coza.opencollab.synthesis.service.api.client.EventService eventService;
    /**
     * The session manager.
     */
    @Autowired
    private SessionManager sessionManager;
    /**
     * The date pattern to use for formatting.
     */
    private String datePattern;

    /**
     * The date pattern to use for formatting.
     *
     * @param datePattern a {@link java.lang.String} object.
     */
    public void setDatePattern(String datePattern) {
        this.datePattern = datePattern;
    }

    /** {@inheritDoc} */
    @Override
    public void addEvent(String event, String ref){
        addEvent(event, ref, null);
    }
    
    /** {@inheritDoc} */
    @Override
    public void addEvent(String event, String ref, String context){
        addEvent(event, ref, context, false);
    }
    
    /** {@inheritDoc} */
    @Override
    public void addEvent(String event, String ref, String context, boolean modified){
        String sessionId = sessionManager.loginAsAdmin();
        if(sessionId != null){
            addEventForSession(sessionId, event, ref, context, modified);
        }else{
            throw new SynthesisException(EVENT_SERVICE, getExceptionMessage(event, ref, context, modified));
        }
    }
    
    /** {@inheritDoc} */
    @Override
    public void addEventForSession(String sessionId, String event, String ref){
        addEventForSession(sessionId, event, ref, null, false);
    }
    
    /** {@inheritDoc} */
    @Override
    public void addEventForSession(String sessionId, String event, String ref, String context){
        addEventForSession(sessionId, event, ref, context, false);
    }
    
    /** {@inheritDoc} */
    @Override
    public void addEventForSession(String sessionId, String event, String ref, String context, boolean modified) {
        try {
            eventService.postEvent(sessionId, event, ref, context, modified);
            if(log.isDebugEnabled()){
                log.debug(event + ":" + ref + ":" + context + ":" + sessionId + ":" + modified);
            }
        } catch (Exception e) {
            throw new SynthesisException(EVENT_SERVICE, getExceptionMessage(sessionId, event, ref, context, modified), e);
        }
    }

    /** {@inheritDoc} */
    @Override
    public void addEventForSession(String sessionId, Date date, String event, String ref, String context){
        addEventForSession(sessionId, event, getReference(ref, date), context, false);
    }
    
    /** {@inheritDoc} */
    @Override
    public void addEventForSession(String sessionId, Date date, String event, String ref, String context, boolean modified) {
        addEventForSession(sessionId, event, getReference(ref, date), context, modified);
    }

    /** {@inheritDoc} */
    @Override
    public void addEventForUser(String username, String event, String ref){
        addEventForUser(username, event, ref, null);
    }
    
    /** {@inheritDoc} */
    @Override
    public void addEventForUser(String username, String event, String ref, String context){
        addEventForUser(username, event, ref, context, false);
    }
    
    /** {@inheritDoc} */
    @Override
    public void addEventForUser(String username, String event, String ref, String context, boolean modified){
        String sessionId = sessionManager.loginAsAdmin();
        if(sessionId != null){
            addEventForUserSession(username, sessionId, event, ref, context, modified);
        }else{
            throw new SynthesisException(EVENT_SERVICE, getExceptionMessage(username, null, event, ref, context, modified));
        }
    }
    
    /** {@inheritDoc} */
    @Override
    public void addEventForUserSession(String username, String sessionId, String event, String ref, String context){
        addEventForUserSession(username, sessionId, event, ref, context, false);
    }
    
    /** {@inheritDoc} */
    @Override
    public void addEventForUserSession(String username, String sessionId, String event, String ref, String context, boolean modified) {
        try {
            eventService.postEvent(sessionId, username, event, ref, context, modified);
            if(log.isDebugEnabled()){
                log.debug(event + ":" + ref + ":" + context + ":" + sessionId + ":" + modified);
            }
        } catch (Exception e) {
            throw new SynthesisException(EVENT_SERVICE, getExceptionMessage(username, sessionId, event, ref, context, modified), e);
        }
    }

    /** {@inheritDoc} */
    @Override
    public void addEventForUserSession(String username, String sessionId, Date date, String event, String ref, String context){
        addEventForUserSession(username, sessionId, event, getReference(ref, date), context, false);
    }
    
    /** {@inheritDoc} */
    @Override
    public void addEventForUserSession(String username, String sessionId, Date date, String event, String ref, String context, boolean modified) {
        addEventForUserSession(username, sessionId, event, getReference(ref, date), context, modified);
    }

    /**
     * Combines the reference and the date.
     * 
     * @param ref The reference.
     * @param date The date
     * @return A concatenated ref and date
     */
    private String getReference(String ref, Date date) {
        return (ref==null?"":ref) + new SimpleDateFormat(datePattern).format(date);
    }
    
    /**
     * Helper to build a exception message.
     */
    private String getExceptionMessage(String event, String ref, String context, boolean modified){
        return getExceptionMessage(null, null, event, ref, context, modified);
    }
    
    /**
     * Helper to build a exception message.
     */
    private String getExceptionMessage(String sessionId, String event, String ref, String context, boolean modified){
        return getExceptionMessage(null, sessionId, event, ref, context, modified);
    }
    
    /**
     * Helper to build a exception message.
     */
    private String getExceptionMessage(String username, String sessionId, String event, String ref, String context, boolean modified){
        return MessageFormat.format(EXCEPTION_MESSAGE, username, sessionId, event, ref, context, modified);
    }
}
