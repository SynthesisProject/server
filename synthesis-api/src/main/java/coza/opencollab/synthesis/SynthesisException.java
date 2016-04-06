package coza.opencollab.synthesis;

import coza.opencollab.synthesis.service.api.ErrorCodes;

/**
 * The base of Synthesis web exceptions, A unchecked exception.
 * <p>
 * This gets thrown whenever there is a problem with the web context.
 * This include a source for invalid data structures and parsing exceptions.
 * This is not for instances where the data is just not valid (normal flow).
 *
 * @author OpenCollab
 * @since 1.0.0
 * @version 1.0.0
 */
public class SynthesisException extends RuntimeException {
    /**
     * The source of the exception
     */
    private Object source;
    /**
     * The error code for what happened.
     * 1000 means error code not set.
     */
    private int errorCode = ErrorCodes.GENERAL;

    /**
     * Constructs an instance of
     * <code>SynthesisException</code> with the specified detail message.
     *
     * @param errorCode The error code.
     * @param message the detail message.
     */
    public SynthesisException(int errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    /**
     * Constructs a new runtime exception with the specified detail message and
     * cause.  <p>Note that the detail message associated with
     * {@code cause} is <i>not</i> automatically incorporated in
     * this runtime exception's detail message.
     *
     * @param errorCode The error code.
     * @param  message the detail message (which is saved for later retrieval
     *         by the {@link #getMessage()} method).
     * @param  cause the cause (which is saved for later retrieval by the
     *         {@link #getCause()} method).  (A <tt>null</tt> value is
     *         permitted, and indicates that the cause is nonexistent or
     *         unknown.)
     */
    public SynthesisException(int errorCode, String message, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
    }

    /**
     * Constructs a new runtime exception with the specified detail message and
     * cause.  <p>Note that the detail message associated with
     * {@code cause} is <i>not</i> automatically incorporated in
     * this runtime exception's detail message.
     *
     * @param errorCode The error code.
     * @param  message the detail message (which is saved for later retrieval
     *         by the {@link #getMessage()} method).
     * @param source the source of the exception
     * @param  cause the cause (which is saved for later retrieval by the
     *         {@link #getCause()} method).  (A <tt>null</tt> value is
     *         permitted, and indicates that the cause is nonexistent or
     *         unknown.)
     */
    public SynthesisException(int errorCode, String message, Object source, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
        this.source = source;
    }
    
    /**
     * The source of the exception
     *
     * @return a {@link java.lang.Object} object.
     */
    public Object getSource() {
        return source;
    }

    /**
     * The source of the exception
     *
     * @param source a {@link java.lang.Object} object.
     */
    public void setSource(Object source) {
        this.source = source;
    }

    /**
     * The error code for what happened.
     *
     * @return a int.
     */
    public int getErrorCode() {
        return errorCode;
    }

    /**
     * The error code for what happened.
     *
     * @param errorCode a int.
     */
    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    /**
     * This will add the source to the message if there is a source.
     * {@inheritDoc}
     */
    @Override
    public String getMessage() {
        return super.getMessage() + (source==null?"":" [Source: "+ source + "]");
    }
    
    /** {@inheritDoc} */
    @Override
    public String getLocalizedMessage() {
        return getMessage();
    }
}
