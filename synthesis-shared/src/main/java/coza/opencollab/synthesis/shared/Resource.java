package coza.opencollab.synthesis.shared;

import java.util.Date;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 *
 * @author OpenCollab
 */
public class Resource {

    /**
     * Resource id, the resource entities are represented as the query string
     * e.g. /group/ACN203S-12-S1/Graphics/
     */
    private String id;

    /**
     * parent id , needed for hierachy building
     */
    @JsonProperty("parent_id")
    private String parentId;

    /**
     * The id used for the tree.
     */
    private String treeId;
    /**
     * The parent id used for the tree.
     */
    private String treeParentId;
    /**
     * Resource name as given in the LMS user
     */
    private String name;
    /**
     * The resource title.
     */
    private String title;
    /**
     * Resource description as provided by the LMS user
     */
    private String description;

    /**
     * The reference used for this resource.
     */
    private String reference;

    /**
     * The type of resource basically a mime-type but we have added none
     * standard e.g. image/x-png type to represent a directory e.g.
     * resource/collection
     */
    @JsonProperty("mime_type")
    private String mimeType;

    /**
     * The resources type as the lms knows it.
     */
    private String resourceType;

    /**
     * boolean is this resource a collection or an individual value e.g.
     * true/false
     */
    @JsonProperty("directory")
    private boolean collection;

    /**
     * The date when the resource was created e.g. 20120119101204781
     */
    @JsonProperty("created_on")
    private Date created;

    /**
     * the access mode data e.g. inherited etc.
     */
    private String access;

    /**
     * UID representation of the user who created the resource e.g.
     * 73738c53-e717-488e-b686-f2f5f7956bc6
     */
    @JsonProperty("created_by")
    private String createdBy;

    /**
     * Human readable version of the user who created the resource e.g. Mr. John
     * Black
     */
    private String createdByDisplayName;

    /**
     * The date when the resource was last modified e.g. 20120119101204781
     */
    @JsonProperty("modified_on")
    private Date lastChanged;

    /**
     * UID representation of the user who last modified the resource e.g.
     * 73738c53-e717-488e-b686-f2f5f7956bc6
     */
    private String lastChangedBy;

    /**
     * Human readable version of the user who last modified the resource e.g.
     * Mr. John Black
     */
    private String lastChangedByDisplayName;
    /**
     * The date the resource should be released. Null means it is always
     * available.
     */
    private Date releaseDate;
    /**
     * The date the resource should be retracted. Null means it must not be
     * retracted.
     */
    private Date retractDate;

    /**
     * The size of the resource in bytes
     */
    private long size;

    /**
     * The content of the resource in byte, will be empty if it is a collection
     */
    @JsonIgnore
    private byte[] content = new byte[0];

    /**
     * The Sakai URL for the resource download
     */
    @JsonIgnore
    private String contentURL;

    /**
     * URL to the resource
     */
    private String url;

    /**
     * The key to use to download this from Synthesis
     */
    private String downloadKey;

    /**
     * The status of the resource. Only used to indicate 'DELETED' can be empty
     * if not deleted
     */
    private String status;

    /**
     * The link URL for text/url types
     */
    private String linkURL;

    /*
     * =========Setters and Getters ==========
     */
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getTreeId() {
        return treeId;
    }

    public void setTreeId(String treeId) {
        this.treeId = treeId;
    }

    public String getTreeParentId() {
        return treeParentId;
    }

    public void setTreeParentId(String treeParentId) {
        this.treeParentId = treeParentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public String getResourceType() {
        return resourceType;
    }

    public void setResourceType(String resourceType) {
        this.resourceType = resourceType;
    }

    public boolean isCollection() {
        return collection;
    }

    public void setCollection(boolean collection) {
        this.collection = collection;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public String getCreatedByDisplayName() {
        return createdByDisplayName;
    }

    public void setCreatedByDisplayName(String createdByDisplayName) {
        this.createdByDisplayName = createdByDisplayName;
    }

    public Date getLastChanged() {
        return lastChanged;
    }

    public void setLastChanged(Date lastChanged) {
        this.lastChanged = lastChanged;
    }

    public String getLastmodifiedByDisplayName() {
        return lastChangedByDisplayName;
    }

    public void setLastChangedByDisplayName(String lastChangedByDisplayName) {
        this.lastChangedByDisplayName = lastChangedByDisplayName;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public Date getRetractDate() {
        return retractDate;
    }

    public void setRetractDate(Date retractDate) {
        this.retractDate = retractDate;
    }

    public String getLastChangedBy() {
        return lastChangedBy;
    }

    public void setLastChangedBy(String lastChangedBy) {
        this.lastChangedBy = lastChangedBy;
    }

    public String getAccess() {
        return access;
    }

    public void setAccess(String access) {
        this.access = access;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }

    public String getContentURL() {
        return contentURL;
    }

    public void setContentURL(String contentURL) {
        this.contentURL = contentURL;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDownloadKey() {
        return downloadKey;
    }

    public void setDownloadKey(String downloadKey) {
        this.downloadKey = downloadKey;
    }

    @Override
    public String toString() {
        return "Resource{ id=" + id + ", parentId=" + parentId + ", name=" + name
                + ", description=" + description + ", mimeType=" + mimeType + ", collection=" + collection
                + ", created=" + created + ", access=" + access + ", createdBy=" + createdBy
                + ", createdByDisplayName=" + createdByDisplayName + ", lastChanged=" + lastChanged
                + ", lastChangedBy=" + lastChangedBy + ", lastChangedByDisplayName=" + lastChangedByDisplayName
                + ", size=" + size + ", url=" + url + '}';
    }

    /**
     * @return the status
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * @return the linkURL
     */
    public String getLinkURL() {
        return linkURL;
    }

    /**
     * @param linkURL the linkURL to set
     */
    public void setLinkURL(String linkURL) {
        this.linkURL = linkURL;
    }
}
