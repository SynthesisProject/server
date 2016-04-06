package coza.opencollab.synthesis.push.dbo;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlTransient;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Set;

// TODO: Auto-generated Javadoc
/**
 * A class representing a Push Notification message. A single push notification
 * instance is shared for many users and devices.
 *
 * The {@link coza.opencollab.synthesis.push.dbo.PushDeviceTuple} class
 * represent the status of a push message being sent to a device.
 *
 */
@NamedQueries({
    /**
     * Query to find a specific push by its ID
     */
    @NamedQuery(
            name = "Push.find",
            query = "SELECT p FROM Push p WHERE p.pushId = :pushId"
    ),
    /**
     * Query to find all Push objects
     */
    @NamedQuery(
            name = "Push.findAll",
            query = "SELECT p FROM Push p ORDER BY p.postedTimestamp DESC"
    ),
    /**
     * Counts the number of push objects persisted
     */
    @NamedQuery(
            name = "Push.countAll",
            query = "SELECT COUNT(p) FROM Push p"
    )

})
@Entity
@Table(name = "PUSH_MSG")
public class Push implements Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = -9158722924017383328L;

    /** Used for sending test messages to device. */
    public static final int TEST_MESSAGE = 1;
    
    /** Content updates on tools. */
    public static final int CONTENT_CHANGE = 2;
    
    /** Instance id for this <code>Push</code> instance. */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Long pushId;

    /**
     * Title for this <code>Push</code> instance.
     */
    @Column(name = "TITLE")
    private String title;

    /**
     * Message for this <code>Push</code> instance.
     */
    @Column(name = "MSG")
    private String message;

    /**
     * Timestamp this <code>Push</code> was last persisted.
     */
    @Column(name = "PST_TS")
    @XmlTransient
    private Timestamp postedTimestamp;

    /**
     * Sender for this <code>Push</code> instance.
     */
    @Column(name = "SNDR")
    private String sender;

    /**
     * URL for this <code>Push</code> instance.
     */
    @Column(name = "URL")
    private String url;

    /**
     * Flag if this <code>Push</code> is an emergency message.
     */
    @Column(name = "EMR")
    private boolean emergency;

    /** Version number. */
    @Version
    @Column(name = "VER_NBR")
    @XmlTransient
    private Long versionNumber;

    /**
     * Module id for this <code>Push</code> instance.
     */
    @Column(name = "MODULE_ID")
    private String moduleId;

    /**
     * Tool name for this <code>Push</code> instance.
     */
    @Column(name = "TOOL_NAME")
    private String toolName;

    /**
     * Message type for this <code>Push</code> instance.
     */
    @Column(name = "MSG_TYPE")
    private Integer messageType;

    /** The tuples. */
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, mappedBy = "push")
    @JoinColumn(name = "PUSH_ID")
    private Set<PushDeviceTuple> tuples;

    /**
     * Creates a new instance of a <code>Push</code>.
     */
    public Push() {
    }

    /**
     * Gets the timestamp this <code>Push</code> was last persisted.
     *
     * @return the timestamp this <code>Push</code> was last persisted
     */
    public Timestamp getPostedTimestamp() {
        return postedTimestamp;
    }

    /**
     * Sets the timestamp this <code>Push</code> was last persisted.
     *
     * @param postedTimestamp the new timestamp this <code>Push</code> was last persisted
     */
    public void setPostedTimestamp(Timestamp postedTimestamp) {
        this.postedTimestamp = postedTimestamp;
    }

    /**
     * Gets the flag if this <code>Push</code> is an emergency message.
     *
     * @return the flag if this <code>Push</code> is an emergency message
     */
    public boolean getEmergency() {
        return emergency;
    }

    /**
     * Sets the flag if this <code>Push</code> is an emergency message.
     *
     * @param emergency the new flag if this <code>Push</code> is an emergency message
     */
    public void setEmergency(boolean emergency) {
        this.emergency = emergency;
    }

    /**
     * Gets the instance id for this <code>Push</code> instance.
     *
     * @return the instance id for this <code>Push</code> instance
     */
    public Long getPushId() {
        return pushId;
    }

    /**
     * Sets the instance id for this <code>Push</code> instance.
     *
     * @param pushId the new instance id for this <code>Push</code> instance
     */
    public void setPushId(Long pushId) {
        this.pushId = pushId;
    }

    /**
     * Gets the sender for this <code>Push</code> instance.
     *
     * @return the sender for this <code>Push</code> instance
     */
    public String getSender() {
        return sender;
    }

    /**
     * Sets the sender for this <code>Push</code> instance.
     *
     * @param sender the new sender for this <code>Push</code> instance
     */
    public void setSender(String sender) {
        this.sender = sender;
    }

    /**
     * Gets the uRL for this <code>Push</code> instance.
     *
     * @return the uRL for this <code>Push</code> instance
     */
    public String getUrl() {
        return url;
    }

    /**
     * Sets the uRL for this <code>Push</code> instance.
     *
     * @param url the new uRL for this <code>Push</code> instance
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * Gets the title for this <code>Push</code> instance.
     *
     * @return the title for this <code>Push</code> instance
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the title for this <code>Push</code> instance.
     *
     * @param title the new title for this <code>Push</code> instance
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Gets the message for this <code>Push</code> instance.
     *
     * @return the message for this <code>Push</code> instance
     */
    public String getMessage() {
        return message;
    }

    /**
     * Sets the message for this <code>Push</code> instance.
     *
     * @param message the new message for this <code>Push</code> instance
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * Gets the version number.
     *
     * @return the version number
     */
    public Long getVersionNumber() {
        return versionNumber;
    }

    /**
     * Sets the version number.
     *
     * @param versionNumber the new version number
     */
    public void setVersionNumber(Long versionNumber) {
        this.versionNumber = versionNumber;
    }

    /**
     * Gets the module id for this <code>Push</code> instance.
     *
     * @return the module id for this <code>Push</code> instance
     */
    public String getModuleId() {
        return moduleId;
    }

    /**
     * Sets the module id for this <code>Push</code> instance.
     *
     * @param moduleId the new module id for this <code>Push</code> instance
     */
    public void setModuleId(String moduleId) {
        this.moduleId = moduleId;
    }

    /**
     * Gets the tool name for this <code>Push</code> instance.
     *
     * @return the tool name for this <code>Push</code> instance
     */
    public String getToolName() {
        return toolName;
    }

    /**
     * Sets the tool name for this <code>Push</code> instance.
     *
     * @param toolName the new tool name for this <code>Push</code> instance
     */
    public void setToolName(String toolName) {
        this.toolName = toolName;
    }

    /**
     * Gets the message type for this <code>Push</code> instance.
     *
     * @return the message type for this <code>Push</code> instance
     */
    public Integer getMessageType() {
        return messageType;
    }

    /**
     * Sets the message type for this <code>Push</code> instance.
     *
     * @param messageType the new message type for this <code>Push</code> instance
     */
    public void setMessageType(Integer messageType) {
        this.messageType = messageType;
    }

    /*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        String newline = "\r\n";
        String str = newline + "PushID:     " + this.getPushId();
        str = str + newline + "Title:      " + this.getTitle();
        str = str + newline + "Message:    " + this.getMessage();
        str = str + newline + "Emergency:  " + this.getEmergency();
        str = str + newline + "URL:        " + this.getUrl();
        str = str + newline + "Sender:     " + this.getSender();
        str = str + newline + "Timestamp:  " + this.getPostedTimestamp();
        str = str + newline + "Module ID:  " + this.getModuleId();
        str = str + newline + "Tool Name:  " + this.getToolName();
        str = str + newline + "Message Type:  " + this.getMessageType();
        return str;
    }

}
