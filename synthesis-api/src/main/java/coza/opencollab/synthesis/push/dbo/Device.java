
package coza.opencollab.synthesis.push.dbo;

import javax.persistence.*;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Set;

/**
 * A class representing a Device registered which is able to receive push notifications
 */
@NamedQueries({
	/**
	 * Query to find a specific push by its ID
	 */
	@NamedQuery(
		name = "Device.findAll",
		query="SELECT d FROM Device d"
	),
	/**
	 * Query to find a specific push by its ID
	 */
	@NamedQuery(
		name = "Device.findDevicesById",
		query="SELECT d FROM Device d WHERE d.id = :id"
	),
	/**
	 * Query to find a specific push by its ID
	 */
	@NamedQuery(
		name = "Device.findDeviceByRegId",
		query="SELECT d FROM Device d WHERE d.regId = :regId"
	),
	/**
	 * Query to find a specific push by its ID
	 */
	@NamedQuery(
		name = "Device.findDevicesByDeviceId",
		query="SELECT d FROM Device d WHERE d.uuid = :deviceId"
	),
	/**
	 * Query to find devices with the specified username
	 */
	@NamedQuery(
		name = "Device.findByUsername",
		query="SELECT d FROM Device d where d.username = :username"
	),
	/**
	 * Query to find check if a device has a username set
	 */
	@NamedQuery(
		name = "Device.hasUsernameForDeviceId",
		query="SELECT COUNT(d) FROM Device d where d.uuid = :deviceId AND (d.username <> null OR d.username <> '')"
	),
	/**
	 * Finds all devices which does not have a username
	 */
	@NamedQuery(
		name = "Device.findDevicesWithoutUsername",
		query="SELECT d FROM Device d where d.username = null OR d.username = ''"
	),
	/**
	 * Finds all devices which does not have a username
	 */
	@NamedQuery(
		name = "Device.countDevicesWithoutUsername",
		query="SELECT COUNT(d) FROM Device d where d.username = null OR d.username = ''"
	),
	/**
	 * Finds devices of the specified type
	 */
	@NamedQuery(
		name = "Device.findDevicesForType",
		query="SELECT d FROM Device d where d.platform = :deviceType"
	),
	/**
	 * Counts devices of the specified type
	 */
	@NamedQuery(
		name = "Device.countDevicesForType",
		query="SELECT COUNT(d) FROM Device d where d.platform = :deviceType"
	),
	/**
	 * Counts devices of the specified type
	 */
	@NamedQuery(
		name = "Device.countDevicesBefore",
		query="SELECT COUNT(d) FROM Device d where d.postedTimestamp < :timeStamp"
	),
	/**
	 * Counts devices of the specified type
	 */
	@NamedQuery(
		name = "Device.countDevicesWithUsername",
		query="SELECT COUNT(d) FROM Device d where upper(d.username) = upper(:username)"
	),	
	/**
	 * Counts devices
	 */
	@NamedQuery(
		name = "Device.countDevices",
		query="SELECT COUNT(d) FROM Device d"
	),
	/**
	 * Get the usernames of users that has devices
	 */
	@NamedQuery(
			name = "Device.getDeviceUsernames",
			query="SELECT DISTINCT d.username FROM Device d"
	)
	
})
@Entity
@Table(name="PUSH_DEVICES",
	uniqueConstraints = {
		@UniqueConstraint(columnNames = "UUID")
	}
)
public class Device implements Serializable {

	/** Device type constant for Android devices */
	public static final String PLATFORM_ANDROID 	= "Android";

	/** Devices type constant for iOS devices */
	public static final String PLATFORM_IOS			= "iOS";

	/** Device type constant for BlackBerry devices */
	public static final String PLATFORM_BLACKBERRY 	= "BlackBerry";

	/** Device type constraint for Windows devices */
	public static final String PLATFORM_WINDOWS		= "WindowsMobile";

	/** Unique version UID for this class */
	private static final long serialVersionUID = -4157056311695870783L;

	/**
	 * ID for this <code>Device</code> instance.
	 */
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="ID")
	private Long id;

	/**
	 * Type for ths <code>Device</code> instance.
	 * The device type will be one of the following constants
	 * <ul>
	 *	<li><code>Device.PLATFORM_ANDROID</code></li>
	 * 	<li><code>Device.PLATFORM_BLACKBERRY</code></li>
	 * 	<li><code>Device.PLATFORM_IOS</code></li>
	 * 	<li><code>Device.PLATFORM_WINDOWS</code></li>
	 * </ul>
	 */
	@Column(name="PLATFORM")
	private String platform;

	/**
	 * Registration ID for push notifications for this <code>Device</code> instance.
	 * <br>For Android it will be the Registration ID received from GCM.
	 * <br>For iOS it will be the Device Token received from APNS
	 * <br>For BlackBerry it will be the BlackBerry PIN (in HEX) retrieved from the device.
	 */
	@Column(name="REGID")
	private String regId;

	/**
	 * The name retrieved from the actual native device.
	 * <br>On iOS this is the user set name.
	 * <br>On Android an Blackberry it is the model name of the device
	 */
	@Column(name="MODEL")
	private String model;

	/**
	 * Username of the person to who this native device belongs
	 */
	@Column(name="USR")
	private String username;

	/**
	 * Unique identifier for the native device.
	 */
	@Column(name="UUID")
	private String uuid;

	/**
	 * The timestamp of the last update for this <code>Device</code> details.
	 */
	@Column(name="PST_TS")
	private Timestamp postedTimestamp;
	

	/**
	 * Version number for this <code>Device</code> record
	 */
	@Version
	@Column(name="VER_NBR")
	private Long versionNumber;
	
	@OneToMany(fetch=FetchType.LAZY, cascade = CascadeType.REMOVE, mappedBy="device")
	@JoinColumn(name="DEVICE_ID")
	private Set<PushDeviceTuple> tuples;
	
	@OneToMany(fetch=FetchType.LAZY, cascade = CascadeType.REMOVE, mappedBy="device")
	@JoinColumn(name="DEVICE_ID")
	private Set<Preference> preferences;

	/**
	 * Creates a new instance of a <code>Device</code>
	 */
	public Device(){}

	/**
	 * Gets the ID for this <code>Device</code>
	 * @return ID for this <code>Device</code>
	 */
	public Long getId() {
		return id;
	}
	
	/**
	 * Sets the ID for this <code>Device</code>
	 * @param id for this <code>Device</code>
	 */
	public void setId(Long id) {
		this.id = id;
	}

	public Timestamp getPostedTimestamp() {
		return postedTimestamp;
	}
	
	public void setPostedTimestamp(Timestamp postedTimestamp) {
		this.postedTimestamp = postedTimestamp;
	}

	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	} 


	/**
	 * @return the platform
	 */
	public String getPlatform() {
		return platform;
	}

	/**
	 * @param platform the platform to set
	 */
	public void setPlatform(String platform) {
		this.platform = platform;
	}

	/**
	 * @return the regId
	 */
	public String getRegId() {
		return regId;
	}

	/**
	 * @param regId the regId to set
	 */
	public void setRegId(String regId) {
		this.regId = regId;
	}

	/**
	 * @return the model
	 */
	public String getModel() {
		return model;
	}

	/**
	 * @param model the model to set
	 */
	public void setModel(String model) {
		this.model = model;
	}

	/**
	 * @return the uuid
	 */
	public String getUuid() {
		return uuid;
	}

	/**
	 * @param uuid the uuid to set
	 */
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public Long getVersionNumber() {
		return versionNumber;
	}
	
	public void setVersionNumber(Long versionNumber) {
		this.versionNumber = versionNumber;
	}


}
