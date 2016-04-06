package coza.opencollab.synthesis.moodle.client.dao;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;

@Generated("org.jsonschema2pojo")
public class Event {

    @Expose
    private Integer id;
    @Expose
    private String name;
    @Expose
    private String description;
    @Expose
    private Integer format;
    @Expose
    private Integer courseid;
    @Expose
    private Integer groupid;
    @Expose
    private Integer userid;
    @Expose
    private Integer repeatid;
    @Expose
    private String modulename;
    @Expose
    private Integer instance;
    @Expose
    private String eventtype;
    @Expose
    private Integer timestart;
    @Expose
    private Integer timeduration;
    @Expose
    private Integer visible;
    @Expose
    private String uuid;
    @Expose
    private Integer sequence;
    @Expose
    private Integer timemodified;
    @Expose
    private Object subscriptionid;

    /**
     *
     * @return The id
     */
    public Integer getId() {
        return id;
    }

    /**
     *
     * @param id The id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     *
     * @return The name
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @param name The name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @return The description
     */
    public String getDescription() {
        return description;
    }

    /**
     *
     * @param description The description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     *
     * @return The format
     */
    public Integer getFormat() {
        return format;
    }

    /**
     *
     * @param format The format
     */
    public void setFormat(Integer format) {
        this.format = format;
    }

    /**
     *
     * @return The courseid
     */
    public Integer getCourseid() {
        return courseid;
    }

    /**
     *
     * @param courseid The courseid
     */
    public void setCourseid(Integer courseid) {
        this.courseid = courseid;
    }

    /**
     *
     * @return The groupid
     */
    public Integer getGroupid() {
        return groupid;
    }

    /**
     *
     * @param groupid The groupid
     */
    public void setGroupid(Integer groupid) {
        this.groupid = groupid;
    }

    /**
     *
     * @return The userid
     */
    public Integer getUserid() {
        return userid;
    }

    /**
     *
     * @param userid The userid
     */
    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    /**
     *
     * @return The repeatid
     */
    public Integer getRepeatid() {
        return repeatid;
    }

    /**
     *
     * @param repeatid The repeatid
     */
    public void setRepeatid(Integer repeatid) {
        this.repeatid = repeatid;
    }

    /**
     *
     * @return The modulename
     */
    public String getModulename() {
        return modulename;
    }

    /**
     *
     * @param modulename The modulename
     */
    public void setModulename(String modulename) {
        this.modulename = modulename;
    }

    /**
     *
     * @return The instance
     */
    public Integer getInstance() {
        return instance;
    }

    /**
     *
     * @param instance The instance
     */
    public void setInstance(Integer instance) {
        this.instance = instance;
    }

    /**
     *
     * @return The eventtype
     */
    public String getEventtype() {
        return eventtype;
    }

    /**
     *
     * @param eventtype The eventtype
     */
    public void setEventtype(String eventtype) {
        this.eventtype = eventtype;
    }

    /**
     *
     * @return The timestart
     */
    public Integer getTimestart() {
        return timestart;
    }

    /**
     *
     * @param timestart The timestart
     */
    public void setTimestart(Integer timestart) {
        this.timestart = timestart;
    }

    /**
     *
     * @return The timeduration
     */
    public Integer getTimeduration() {
        return timeduration;
    }

    /**
     *
     * @param timeduration The timeduration
     */
    public void setTimeduration(Integer timeduration) {
        this.timeduration = timeduration;
    }

    /**
     *
     * @return The visible
     */
    public Integer getVisible() {
        return visible;
    }

    /**
     *
     * @param visible The visible
     */
    public void setVisible(Integer visible) {
        this.visible = visible;
    }

    /**
     *
     * @return The uuid
     */
    public String getUuid() {
        return uuid;
    }

    /**
     *
     * @param uuid The uuid
     */
    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    /**
     *
     * @return The sequence
     */
    public Integer getSequence() {
        return sequence;
    }

    /**
     *
     * @param sequence The sequence
     */
    public void setSequence(Integer sequence) {
        this.sequence = sequence;
    }

    /**
     *
     * @return The timemodified
     */
    public Integer getTimemodified() {
        return timemodified;
    }

    /**
     *
     * @param timemodified The timemodified
     */
    public void setTimemodified(Integer timemodified) {
        this.timemodified = timemodified;
    }

    /**
     *
     * @return The subscriptionid
     */
    public Object getSubscriptionid() {
        return subscriptionid;
    }

    /**
     *
     * @param subscriptionid The subscriptionid
     */
    public void setSubscriptionid(Object subscriptionid) {
        this.subscriptionid = subscriptionid;
    }

    @Override
    public String toString() {
        return "Event{" + "id=" + id + ", name=" + name + ", description=" + description + ", format=" + format + ", courseid=" + courseid + ", groupid=" + groupid + ", userid=" + userid + ", repeatid=" + repeatid + ", modulename=" + modulename + ", instance=" + instance + ", eventtype=" + eventtype + ", timestart=" + timestart + ", timeduration=" + timeduration + ", visible=" + visible + ", uuid=" + uuid + ", sequence=" + sequence + ", timemodified=" + timemodified + ", subscriptionid=" + subscriptionid + '}';
    }
}
