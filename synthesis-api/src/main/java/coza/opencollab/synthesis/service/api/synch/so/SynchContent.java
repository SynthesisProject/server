package coza.opencollab.synthesis.service.api.synch.so;

import coza.opencollab.synthesis.service.api.ServiceCallStatus;
import coza.opencollab.synthesis.service.api.ServiceObject;
import java.util.Arrays;

/**
 * This represents any content transfered/synched between server
 * and client.
 *
 * @author OpenCollab
 * @since 1.0.0
 * @version 1.0.0
 */
public class SynchContent extends ServiceObject{
    /**
     * What content mime type this is.
     * See: http://webdesign.about.com/od/multimedia/a/mime-types-by-content-type.htm
     */
    private String mimeType;
    /**
     * The content name. This will usually be a file name.
     */
    private String contentName;
    /**
     * The size of the content.
     */
    private long size;
    /**
     * The content being transfered
     */
    private byte[] content;
    /**
     * The content being transfered in String format if zipped binary not used
     */
    private String contentString;
    /**
     * The content version
     */
    private String version;
    
    /**
     * Default constructor
     */
    public SynchContent(){}
    
    /**
     * Constructor setting the status.
     *
     * @param statusCode a {@link coza.opencollab.synthesis.service.api.ServiceCallStatus} object.
     */
    public SynchContent(ServiceCallStatus statusCode){
        super(statusCode);
    }
    
    /**
     * Copy constructor
     *
     * @param other a {@link coza.opencollab.synthesis.service.api.ServiceObject} object.
     */
    public SynchContent(ServiceObject other){
        super(other);
    }

    /**
     * What content mime type this is.
     *
     * @return a {@link java.lang.String} object.
     */
    public String getMimeType() {
        return mimeType;
    }

    /**
     * What content mime type this is.
     *
     * @param mimeType a {@link java.lang.String} object.
     */
    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    /**
     * The content name. This will usually be a file name.
     *
     * @return a {@link java.lang.String} object.
     */
    public String getContentName() {
        return contentName;
    }

    /**
     * The content name. This will usually be a file name.
     *
     * @param contentName a {@link java.lang.String} object.
     */
    public void setContentName(String contentName) {
        this.contentName = contentName;
    }

    /**
     * The size of the content.
     *
     * @return a long.
     */
    public long getSize() {
        return size;
    }

    /**
     * The size of the content.
     *
     * @param size a long.
     */
    public void setSize(long size) {
        this.size = size;
    }
    
    /**
     * The content being transfered
     *
     * @return an array of byte.
     */
    public byte[] getContent() {
        return content == null?null:Arrays.copyOf(content, content.length);
    }

    /**
     * The content being transfered
     *
     * @param content an array of byte.
     */
    public void setContent(byte[] content) {
        if (content != null) {
            this.content = Arrays.copyOf(content, content.length);
        }else{
            this.content = null;
        }
    }

    /**
     * The content version
     *
     * @return a {@link java.lang.String} object.
     */
    public String getVersion() {
        return version;
    }

    /**
     * The content version
     *
     * @param version a {@link java.lang.String} object.
     */
    public void setVersion(String version) {
        this.version = version;
    }

    /**
     * <p>Getter for the field <code>contentString</code>.</p>
     *
     * @return the contentString
     */
    public String getContentString() {
        return contentString;
    }

    /**
     * <p>Setter for the field <code>contentString</code>.</p>
     *
     * @param contentString the contentString to set
     */
    public void setContentString(String contentString) {
        this.contentString = contentString;
    }
    
}
