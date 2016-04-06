package coza.opencollab.synthesis.shared;

import java.io.Serializable;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 *
 * @author OpenCollab
 */
public class Attachment implements Serializable {

    private static final long serialVersionUID = 943564355;
    /**
     * The attachment filename. e.g. IMG_00001.jpg
     */
    @JsonIgnore
    private String id;
    /**
     * The attachment filename. e.g. IMG_00001.jpg
     */
    private String name;
    /**
     * The hosted url to the attachment
     */
    private String url;
    /**
     * The file path
     */
    @JsonProperty("downloadPath")
    private String path;
    /**
     * The file size in bytes
     */
    private int size;
    /**
     * the file type e.g. image/jpeg
     */
    @JsonProperty("mime_type")
    private String mimeType;
    /**
     * The content of the resource in byte
     */
    @JsonIgnore
    private String contentURL;
    /**
     * The key to use to download this from Synthesis
     */
    private String downloadKey;
    /**
     * Whether this is a actual attachment or a link from the content.
     */
    private boolean link = false;

    /**
     * ================= Getters and Setters =================
     */
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public String getContentURL() {
        return contentURL;
    }

    public void setContentURL(String contentURL) {
        this.contentURL = contentURL;
    }

    public String getDownloadKey() {
        return downloadKey;
    }

    public void setDownloadKey(String downloadKey) {
        this.downloadKey = downloadKey;
    }

    public boolean isLink() {
        return link;
    }

    public void setLink(boolean link) {
        this.link = link;
    }

    /**
     * ================= To String =================
     */
    @JsonIgnore
    @Override
    public String toString() {
        return "Attachment{id=" + id + ",name=" + name + ", url=" + url + ", size=" + size + ", type=" + mimeType + '}';
    }
}
