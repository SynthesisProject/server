package coza.opencollab.synthesis.shared;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * This represents an announcement
 *
 * @author OpenCollab
 * @since 1.0.0
 */
public class Announcement implements Serializable {

    private static final long serialVersionUID = 564356356;
    /**
     * The Message id, which is a string representation of the Sakai UID e.g.
     * 3f434ed5-b567-4f0f-9f53-e12490f9eed6
     */
    private String id;
    /**
     * The date on which the announcement was created. String for display e.g.
     * 31-Jan-2013 13:50
     */
    @JsonProperty("create_date")
    private Date date;
    /**
     * Who the announcement is from String display name of the sakai user e.g.
     * Sakai Admin
     */
    @JsonProperty("saved_by")
    private String from;
    /**
     * The message subject or headline text
     */
    private String subject;
    /**
     * The url to the actual message. String e.g.
     * http://domain/access/announcement/msg/AFL1501-13-S1-43T/main/3f434ed5-b567-4f0f-9f53-e12490f9eed6
     */
    private String url;
    /**
     * The body text of the announcement.
     */
    private String body;
    /**
     * The sequence number of the announcement
     */
    private String order;
    /**
     * if the announcement is only if an announcement is for a specific period
     * will this field will populated
     */
    @JsonProperty("begin_date")
    private Date beginDate;
    /**
     * if the announcement is only if an announcement is for a specific period
     * will this field will populated
     */
    @JsonProperty("end_date")
    private Date endDate;
    /**
     * if the announcement has been modified this field will be populated.
     */
    @JsonProperty("mod_date")
    private Date modDate;
    /**
     * List of
     */
    private List<String> groups;
    /**
     * List of attachments associated with the message.
     */
    private List<Attachment> attachments;
    /**
     * The status of the resource. Only used to indicate 'DELETED' can be empty
     * if not deleted
     */
    private String status;

    /**
     * ================ Getters and Setters ================
     */
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public Date getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(Date beginDate) {
        this.beginDate = beginDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Date getModDate() {
        return modDate;
    }

    public void setModDate(Date modDate) {
        this.modDate = modDate;
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

    public List<String> getGroups() {
        return groups;
    }

    public void setGroups(List<String> groups) {
        this.groups = groups;
    }

    @JsonIgnore
    public void addToGroups(String group) {
        if (this.groups == null) {
            this.groups = new ArrayList<String>();
        }
        if (group != null) {
            this.groups.add(group);
        }
    }

    public List<Attachment> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<Attachment> attachments) {
        this.attachments = attachments;
    }

    @JsonIgnore
    public void addToAttachments(Attachment attachment) {
        if (this.attachments == null) {
            this.attachments = new ArrayList<Attachment>();
        }
        if (attachment != null) {
            this.attachments.add(attachment);
        }
    }

    @JsonIgnore
    public boolean hasAttachments() {
        return (this.attachments != null) ? true : false;
    }        

    @JsonIgnore
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        if (obj instanceof Announcement) {
            Announcement other = (Announcement) obj;
            return id.equals(other.id);
        } else {
            return false;
        }
    }

    /**
     * {@inheritDoc}
     */
    @JsonIgnore
    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    /**
     * {@inheritDoc}
     */
    @JsonIgnore
    @Override
    public String toString() {
        return "Announcement{id=" + id + ", date=" + date + ", from=" + from + ", subject=" + subject + ", url=" + url + ", body=" + body + ", order=" + order + ", beginDate=" + beginDate + ", endDate=" + endDate + ", modDate=" + modDate + ", groups=" + groups + ", attachments=" + attachments + '}';
    }
}
