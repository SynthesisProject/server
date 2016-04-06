package coza.opencollab.synthesis.shared;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 *
 * @author OpenCollab
 */
public class Message {

    private String id;
    private String topic;
    private String content;
    @JsonProperty("create_date")
    private Date createDate;
    @JsonProperty("creator_name")
    private String creatorName;
    @JsonProperty("creator_id")
    private String creatorId;
    private int depth;
    @JsonProperty("discussion_id")
    private String discussionId;
    private String url;
    @JsonProperty("group_size")
    private int groupSize;
    private String parent;
    @JsonProperty("site_id")
    private String siteId;
    private String status;
    @JsonProperty("attachment_count")
    private int attachmentCount;
    @JsonProperty("reply_count")
    private int replyCount;
    private List<Attachment> attachments;

    /**
     * ====================== Getters and Setters =========================
     */
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreatorName() {
        return creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    public String getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(String creatorId) {
        this.creatorId = creatorId;
    }

    public int getDepth() {
        return depth;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getGroupSize() {
        return groupSize;
    }

    public void setGroupSize(int groupSize) {
        this.groupSize = groupSize;
    }

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public String getSiteId() {
        return siteId;
    }

    public void setSiteId(String siteId) {
        this.siteId = siteId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getAttachmentCount() {
        return attachmentCount;
    }

    public void setAttachmentCount(int attachmentCount) {
        this.attachmentCount = attachmentCount;
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

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getDiscussionId() {
        return discussionId;
    }

    public void setDiscussionId(String discussionId) {
        this.discussionId = discussionId;
    }

    public int getReplyCount() {
        return replyCount;
    }

    public void setReplyCount(int replyCount) {
        this.replyCount = replyCount;
    }

    @Override
    public String toString() {
        return "Message{" + "id=" + id + ", topic=" + topic + ", content=" + content + ", createDate=" + createDate + ", creatorName=" + creatorName + ", creatorId=" + creatorId + ", depth=" + depth + ", discussionId=" + discussionId + ", url=" + url + ", groupSize=" + groupSize + ", parent=" + parent + ", siteId=" + siteId + ", status=" + status + ", attachmentCount=" + attachmentCount + ", attachments=" + attachments + '}';
    }
}
