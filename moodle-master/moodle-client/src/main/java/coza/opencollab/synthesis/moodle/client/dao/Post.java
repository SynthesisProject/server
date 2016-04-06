package coza.opencollab.synthesis.moodle.client.dao;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.Expose;

/**
 * Corresponds to a Moodle Post saved in the Moodle db under mdl_forum_posts
 * @author OpenCollab
 */
public class Post {

    @Expose
    private int id;
    @Expose
    private int discussion;
    @Expose
    private int parent;
    @Expose
    private int userid;
    @Expose
    private int created;
    @Expose
    private int modified;
    @Expose
    private int mailed;
    @Expose
    private String subject;
    @Expose
    private String message;
    @Expose
    private int messageformat;
    @Expose
    private int messagetrust;
    @Expose
    private String attachment;
    @Expose
    private int totalscore;
    @Expose
    private int mailnow;
    @Expose
    private List<Object> children = new ArrayList<Object>();
    @Expose
    private boolean canreply;
    @Expose
    private boolean postread;
    @Expose
    private String userfullname;

    /**
     *
     * @return The id
     */
    public int getId() {
        return id;
    }

    /**
     *
     * @param id The id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     *
     * @return The discussion
     */
    public int getDiscussion() {
        return discussion;
    }

    /**
     *
     * @param discussion The discussion
     */
    public void setDiscussion(int discussion) {
        this.discussion = discussion;
    }

    /**
     *
     * @return The parent
     */
    public int getParent() {
        return parent;
    }

    /**
     *
     * @param parent The parent
     */
    public void setParent(int parent) {
        this.parent = parent;
    }

    /**
     *
     * @return The userid
     */
    public int getUserid() {
        return userid;
    }

    /**
     *
     * @param userid The userid
     */
    public void setUserid(int userid) {
        this.userid = userid;
    }

    /**
     *
     * @return The created
     */
    public int getCreated() {
        return created;
    }

    /**
     *
     * @param created The created
     */
    public void setCreated(int created) {
        this.created = created;
    }

    /**
     *
     * @return The modified
     */
    public int getModified() {
        return modified;
    }

    /**
     *
     * @param modified The modified
     */
    public void setModified(int modified) {
        this.modified = modified;
    }

    /**
     *
     * @return The mailed
     */
    public int getMailed() {
        return mailed;
    }

    /**
     *
     * @param mailed The mailed
     */
    public void setMailed(int mailed) {
        this.mailed = mailed;
    }

    /**
     *
     * @return The subject
     */
    public String getSubject() {
        return subject;
    }

    /**
     *
     * @param subject The subject
     */
    public void setSubject(String subject) {
        this.subject = subject;
    }

    /**
     *
     * @return The message
     */
    public String getMessage() {
        return message;
    }

    /**
     *
     * @param message The message
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     *
     * @return The messageformat
     */
    public int getMessageformat() {
        return messageformat;
    }

    /**
     *
     * @param messageformat The messageformat
     */
    public void setMessageformat(int messageformat) {
        this.messageformat = messageformat;
    }

    /**
     *
     * @return The messagetrust
     */
    public int getMessagetrust() {
        return messagetrust;
    }

    /**
     *
     * @param messagetrust The messagetrust
     */
    public void setMessagetrust(int messagetrust) {
        this.messagetrust = messagetrust;
    }

    /**
     *
     * @return The attachment
     */
    public String getAttachment() {
        return attachment;
    }

    /**
     *
     * @param attachment The attachment
     */
    public void setAttachment(String attachment) {
        this.attachment = attachment;
    }

    /**
     *
     * @return The totalscore
     */
    public int getTotalscore() {
        return totalscore;
    }

    /**
     *
     * @param totalscore The totalscore
     */
    public void setTotalscore(int totalscore) {
        this.totalscore = totalscore;
    }

    /**
     *
     * @return The mailnow
     */
    public int getMailnow() {
        return mailnow;
    }

    /**
     *
     * @param mailnow The mailnow
     */
    public void setMailnow(int mailnow) {
        this.mailnow = mailnow;
    }

    /**
     *
     * @return The children
     */
    public List<Object> getChildren() {
        return children;
    }

    /**
     *
     * @param children The children
     */
    public void setChildren(List<Object> children) {
        this.children = children;
    }

    /**
     *
     * @return The canreply
     */
    public boolean isCanreply() {
        return canreply;
    }

    /**
     *
     * @param canreply The canreply
     */
    public void setCanreply(boolean canreply) {
        this.canreply = canreply;
    }

    /**
     *
     * @return The postread
     */
    public boolean isPostread() {
        return postread;
    }

    /**
     *
     * @param postread The postread
     */
    public void setPostread(boolean postread) {
        this.postread = postread;
    }

    /**
     *
     * @return The userfullname
     */
    public String getUserfullname() {
        return userfullname;
    }

    /**
     *
     * @param userfullname The userfullname
     */
    public void setUserfullname(String userfullname) {
        this.userfullname = userfullname;
    }

    @Override
    public String toString() {
        return "PostEntry [id=" + id + ", discussion=" + discussion
                + ", parent=" + parent + ", userid=" + userid + ", created="
                + created + ", modified=" + modified + ", mailed=" + mailed
                + ", subject=" + subject + ", message=" + message
                + ", messageformat=" + messageformat + ", messagetrust="
                + messagetrust + ", attachment=" + attachment + ", totalscore="
                + totalscore + ", mailnow=" + mailnow + ", children="
                + children + ", canreply=" + canreply + ", postread="
                + postread + ", userfullname=" + userfullname + "]";
    }

}
