package coza.opencollab.synthesis.shared;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Schedule object, Synthesis representation of the SAKAI Schedule tool data
 *
 * @author OpenCollab
 */
public class Schedule {
    /**
     * String UID returned from the LMS e.g. 61bd5951-6daa-492a-b659-c43d14df6500
     */
    private String id;
    
    /**
     * String title of the schedule item e.g. Meeting regarding test
     */
    private String title;
    
    /**
     * String Description, long form of the title with additional information
     */
    private String description;
    
    /**
     * Date range of the event e.g. 20130814120000000]20130814210000000
     */
    private String range;
    
    /**
     * Displayable start date and time 14-Aug-2013 14:00
     */
    private Date start;
    
    /**
     * Displayable end date and time 22:59
     */
    private Date end;
    
    /**
     * String displayable text of the frequency of the event.
     */
    private String frequency;
    
    /**
     * String displayable text of the frequency of the event.
     */
    @JsonProperty("frequency_description")
    private String frequencyDescription;
    
     /**
     * String displayable text of the interval between a recurrence
     */
    @JsonProperty("recurrence_interval")
    private String recurrenceInterval;
    
    /**
     * String displayable text of the interval between a recurrence
     */
    @JsonProperty("recurrence_count")
    private String recurrenceCount;
    
    /**
     * String displayable text of the interval between a recurrence
     */
    @JsonProperty("recurs_until")
    private Date recursUntil;
    
    /**
     * A boolean value describing whether the event is all day
     */
    private boolean allDay;
    
    /**
     * String displayable value of the schedule event type
     */
    private String type;
    
    /**
     * The current site to which the schedule announcement belongs
     */
    private String site;
    
    /**
     * String moduleCode of the site to which this schedule event belongs.
     */
    @JsonProperty("from_site")
    private String fromSite;

    /**
     * String event location
     */
    private String location;
    
    /**
     * the date upon which the event was created.
     */
    @JsonIgnore
    private Date createDate;
    
    /**
     * the date upon which the event was modified.
     */
    @JsonIgnore
    private Date modifiedDate;
    
    /**
     * Creator of the schedule event
     */
    @JsonProperty("owner")
    private String creator;
    
    /*
     * Attachements for an event
     */
    private List<Attachment> attachments;
    
    /**
     * ============ Getters and Setters =================
     */
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getRange() {
        return range;
    }

    public void setRange(String range) {
        this.range = range;
    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public boolean isAllDay() {
        return allDay;
    }

    public void setAllDay(boolean allDay) {
        this.allDay = allDay;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFromSite() {
        return fromSite;
    }

    public void setFromSite(String fromSite) {
        this.fromSite = fromSite;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Date modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }
    
    public List<Attachment> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<Attachment> attachments) {
        this.attachments = attachments;
    }

    @JsonIgnore
    public void addToAttachments(Attachment attachmentObject) {
        if (this.attachments == null) {
            this.attachments = new ArrayList<Attachment>();
        }
        if (attachmentObject != null) {
            this.attachments.add(attachmentObject);
        }
    }

    public String getRecurrenceInterval() {
        return recurrenceInterval;
    }

    public void setRecurrenceInterval(String recurrenceInterval) {
        this.recurrenceInterval = recurrenceInterval;
    }

    public String getRecurrenceCount() {
        return recurrenceCount;
    }

    public void setRecurrenceCount(String recurrenceCount) {
        this.recurrenceCount = recurrenceCount;
    }

    public Date getRecursUntil() {
        return recursUntil;
    }

    public void setRecursUntil(Date recursUntil) {
        this.recursUntil = recursUntil;
    }

    public String getFrequencyDescription() {
        return frequencyDescription;
    }

    public void setFrequencyDescription(String frequencyDescription) {
        this.frequencyDescription = frequencyDescription;
    }

    @Override
    public String toString() {
        return "Schedule{ id=" + id + ", title=" + title + ", description=" + description + ", range=" + range + ", start=" + start + ", end=" + end + ", frequency=" + frequency + ", frequencyDescription=" + frequencyDescription + ", recurrenceInterval=" + recurrenceInterval + ", recurrenceCount=" + recurrenceCount + ", recursUntil=" + recursUntil + ", allDay=" + allDay + ", type=" + type + ", site=" + site + ", fromSite=" + fromSite + ", location=" + location + ", createDate=" + createDate + ", modifiedDate=" + modifiedDate + ", creator=" + creator + ", attachments=" + attachments + '}';
    }
}
