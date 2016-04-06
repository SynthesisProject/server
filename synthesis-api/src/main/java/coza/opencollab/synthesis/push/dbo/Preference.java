package coza.opencollab.synthesis.push.dbo;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * A class representing a Push Notification Opt-out Preference.
 * 
 */
@NamedQueries({
	@NamedQuery(
		name="Preference.findPreferenceForDeviceAndSender",
		query="SELECT p FROM Preference p WHERE p.device.id = :deviceId AND p.sender = :sender"
	),
	@NamedQuery(
		name="Preference.findPreferencesForDevice",
		query="SELECT p FROM Preference p WHERE p.device.id = :deviceId"
	),
	@NamedQuery(
		name="Preference.findPreferenceWithId",
		query="SELECT p FROM Preference p WHERE p.id = :id"
	),
	@NamedQuery(
		name="Preference.findDevicesThatAllowSender",
		query="SELECT p.device FROM Preference p WHERE p.sender = :sender AND p.enabled=true"
	)
})
@Entity
@Table(name="PUSH_PREF")
@XmlRootElement
public class Preference implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8566828971044365068L;

	/**
	 * ID for this <code>Preference</code> instance.
	 */
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="ID")
	private Long id;
	
	/**
	 * Device this preference is linked too
	 */
	@ManyToOne(optional=false)
	@JoinColumn(name="DEVICE_ID")
	private Device device;

	/**
	 * Enabled denotes whether the whether the user wants to receive
	 * Push from a given module. If an entry does not exist in the table
	 * the user should not receive a notification
	 */
	@Column(name="ENB")
	private boolean enabled;
	
	/**
	 * Id of the sender of the message.
	 * The sender can be a module, a tool, or any level implemented
	 */
	@Column(name="SENDER")
	private String sender;
	
	/**
	 * PostedTimestamp at which the preference was set and inserted into the database.  
	 */
	@Column(name="PST_TS")
	private Timestamp postedTimestamp;
	
	/**
	 * VersionNumber is the versionNumber of the preference.  
	 */
	@Version
	@Column(name="VER_NBR")
	private Long versionNumber;

	/**
	 * Creates a new instance of a <code>Preference</code>
	 */
	public Preference(){}
	
	/**
	 * Gets the ID for this <code>Preference</code>
	 * @return ID for this <code>Preference</code>
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Sets the id
	 * @param id The ID
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the device
	 */
	public Device getDevice() {
		return device;
	}

	/**
	 * @param device the device to set
	 */
	public void setDevice(Device device) {
		this.device = device;
	}

	

	/**
	 * @return the sender
	 */
	public String getSender() {
		return sender;
	}

	/**
	 * @param sender the sender to set
	 */
	public void setSender(String sender) {
		this.sender = sender;
	}

	/**
	 * Gets the boolean for enabled for this <code>Preference</code>
	 * @return hidden for this <code>Preference</code>
	 */
	public boolean isEnabled() {
		return enabled;
	}

	/**
	 * Gets the boolean for enabled for this <code>Preference</code>
	 * @return whether the sender for this <code>Preference</code> is blocked.
	 */
	public boolean isSenderBlocked() {
		return !enabled;
	}
	
	/**
	 * Sets the hidden
	 * @param enabled The Hidden
	 */
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	/**
	 * Gets the postedTimestamp for this <code>Preference</code>
	 * @return postedTimestamp for this <code>Preference</code>
	 */
	@XmlTransient
	public Timestamp getPostedTimestamp() {
		return postedTimestamp;
	}

	/**
	 * Sets the PostedTimestamp
	 * @param postedTimestamp The PostedTimestamp
	 */
	public void setPostedTimestamp(Timestamp postedTimestamp) {
		this.postedTimestamp = postedTimestamp;
	}

	/**
	 * Gets the versionNumber for this <code>Preference</code>
	 * @return versionNumber for this <code>Preference</code>
	 */
	public Long getVersionNumber() {
		return versionNumber;
	}


}
