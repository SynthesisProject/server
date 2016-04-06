package coza.opencollab.synthesis.service.log.impl;

import coza.opencollab.synthesis.service.api.log.LogService;
import org.apache.log4j.Logger;

/**
 * A log service that just write out to the log file.
 *
 * @author OpenCollab
 * @since 1.0.0
 * @version 1.0.1
 */
public class DefaultLoggerService implements LogService{
    /**
     * The logger.
     */
    private static final Logger LOG = Logger.getLogger(DefaultLoggerService.class);

    /** {@inheritDoc} */
    @Override
    public void log(String message) {
        LOG.info(message);
    }

    /** {@inheritDoc} */
    @Override
    public void log(String message, Priority priority) {
        if(Priority.High.equals(priority)){
            LOG.warn(message);
        }else if(Priority.Normal.equals(priority)){
            LOG.info(message);
        }else{
            LOG.debug(message);
        }
    }

    /** {@inheritDoc} */
    @Override
    public void log(String message, Throwable t) {
        LOG.error(message, t);
    }

    /** {@inheritDoc} */
    @Override
    public void log(String message, Throwable t, Priority priority) {
        if(Priority.High.equals(priority)){
            LOG.error(message, t);
        }else if(Priority.Normal.equals(priority)){
            LOG.warn(message, t);
        }else{
            LOG.info(message, t);
        }
    }
}
