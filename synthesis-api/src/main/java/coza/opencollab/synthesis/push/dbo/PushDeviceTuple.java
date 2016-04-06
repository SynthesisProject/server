package coza.opencollab.synthesis.push.dbo;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * The Class PushDeviceTuple.
 *
 * @author charl
 */
@NamedQueries({
	/**
	 * Query to find all PushDeviceTuple instances that needs to be sent
	 */
	@NamedQuery(
		name = "PushDeviceTuple.findUnsent",
		query="SELECT t FROM PushDeviceTuple t WHERE t.status = 0 OR t.status = 2"
	),
	/**
	 * Query to find all PushDeviceTuple instances that needs to be sent
	 */
	@NamedQuery(
		name = "PushDeviceTuple.findAll",
		query="SELECT t FROM PushDeviceTuple t"
	),
	/**
	 * Query to find devices that was linked to a specific push
	 */
	@NamedQuery(
		name = "PushDeviceTuple.findPushDevices",
		query="SELECT t.device FROM PushDeviceTuple t WHERE t.push.id = :pushId"
	),
	@NamedQuery(
		name="PushDeviceTuple.countUnsent",
		query="select count(t) FROM PushDeviceTuple t WHERE t.status = 0"
	),
	@NamedQuery(
		name="PushDeviceTuple.findTuplesForPush",
		query="select t FROM PushDeviceTuple t WHERE t.push.id = :pushId"
	),
	@NamedQuery(
		name="PushDeviceTuple.deleteForStatus",
		query="DELETE PushDeviceTuple t WHERE t.status = :status"
	),
	@NamedQuery(
		name="PushDeviceTuple.findNextQueued",
		query="SELECT t FROM PushDeviceTuple t WHERE t.device.platform = :platform AND (t.status=0 OR t.status=2) AND t.nextAttempt <= :timestamp ORDER BY t.nextAttempt ASC"
	),
	@NamedQuery(
		name="PushDeviceTuple.findNextQueuedBatch",
		query="SELECT t FROM PushDeviceTuple t WHERE t.device.platform = :platform AND (t.status=0 OR t.status=2) AND t.nextAttempt <= :timestamp AND t.push.id = :pushId"
	)


})
@Entity
@Table(name="PUSH_TUPLE")
public class PushDeviceTuple implements Serializable {

	/** Status indicating that the message is pending to be sent, i.e a message just added */
	public static final int STATUS_PENDING = 0;

	/**  Status indicating that the message has been sent. */
	public static final int STATUS_SENT = 1;

	/**  Status indicating that sending the message has failed once, but waiting for a retry attempt. */
	public static final int STATUS_WAITING_RETRY = 2;

	/**   Status indicating the a maximum number of retries has failed, therefore the message is  seen as failed, and no further retries will be attempted. */
	public static final int STATUS_FAILED = 3;

	/**  Serial version UID. */
	private static final long serialVersionUID = 9083352553678796701L;

	/** Id for this PushDeviceTuple. */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="ID")
	private Long tupleId;

	/**
	 * ID of the <code>Push</code> messages this class relates to.
	 */
	@ManyToOne(optional=false)
	@JoinColumn(name="PUSH_ID",nullable=false)
	private Push push;

	/**
	 * ID of the devices this message should be sent too.
	 */
	@OneToOne(optional=false)
	@JoinColumn(name="DEVICE_ID",nullable=false)
	private Device device;

	/** Last time the status of this object has changed. */
	@Column(name="PST_TS")
	private Timestamp postedTimestamp;

	/** Time the next attempt may be tried. */
	@Column(name="NEXT_ATTEMPT")
	private Timestamp nextAttempt;

	/** Number of retries that has been attempted. */
	@Column(name="RETRIES")
	private int retries;

	/** The current status of this <code>PushDeviceTuple</code>. */
	@Column(name="STATUS")
	private int status;

	/** The incremental version number for this. */
	@Version
	@Column(name="VER_NBR")
	private Long versionNumber;

	/**
	 * Creates a new instance of a <code>PushDeviceTuple</code>.
	 */
	public PushDeviceTuple(){}

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public Long getId() {
		return tupleId;
	}


	/**
	 * Sets the id.
	 *
	 * @param tupleId the new id
	 */
	public void setId(Long tupleId) {
		this.tupleId = tupleId;
	}


	/**
	 * Gets the iD of the <code>Push</code> messages this class relates to.
	 *
	 * @return the iD of the <code>Push</code> messages this class relates to
	 */
	public Push getPush() {
		return push;
	}


	/**
	 * Sets the iD of the <code>Push</code> messages this class relates to.
	 *
	 * @param push the new iD of the <code>Push</code> messages this class relates to
	 */
	public void setPush(Push push) {
		this.push = push;
	}


	/**
	 * Gets the iD of the devices this message should be sent too.
	 *
	 * @return the iD of the devices this message should be sent too
	 */
	public Device getDevice() {
		return device;
	}


	/**
	 * Sets the iD of the devices this message should be sent too.
	 *
	 * @param device the new iD of the devices this message should be sent too
	 */
	public void setDevice(Device device) {
		this.device = device;
	}

	/**
	 * Gets the last time the status of this object has changed.
	 *
	 * @return the last time the status of this object has changed
	 */
	public Timestamp getPostedTimestamp() {
		return postedTimestamp;
	}


	/**
	 * Sets the last time the status of this object has changed.
	 *
	 * @param postedTimestamp the new last time the status of this object has changed
	 */
	public void setPostedTimestamp(Timestamp postedTimestamp) {
		this.postedTimestamp = postedTimestamp;
	}


	/**
	 * Sets the current status of this <code>PushDeviceTuple</code>.
	 *
	 * @param status the new current status of this <code>PushDeviceTuple</code>
	 */
	public void setStatus(int status){
		this.status = status;
	}

	/**
	 * Gets the current status of this <code>PushDeviceTuple</code>.
	 *
	 * @return the current status of this <code>PushDeviceTuple</code>
	 */
	public int getStatus(){
		return this.status;
	}


	/**
	 * Sets the status of this <code>PushDeviceTuple</code> to 
	 * <code>PushDeviceTuple.STATUS_SENT</code>
	 */
	public void setSent() {
		this.setStatus(STATUS_SENT);
	}

	/**
	 * Checks if is sent.
	 *
	 * @return true, if is sent
	 */
	public boolean isSent() {
		return this.status == STATUS_SENT;
	}


	/**
	 * Checks if is waiting retry.
	 *
	 * @return true, if is waiting retry
	 */
	public boolean isWaitingRetry(){
		return this.status == STATUS_WAITING_RETRY;
	}


	/**
	 * Sets the status of this <code>PushDeviceTuple</code> to 
	 * <code>PushDeviceTuple.STATUS_WAITING_RETRY</code>
	 */
	public void setWaitingRetry(){
		this.setStatus(STATUS_WAITING_RETRY);
	}


	/**
	 * Gets the incremental version number for this.
	 *
	 * @return the incremental version number for this
	 */
	public Long getVersionNumber() {
		return versionNumber;
	}


	/**
	 * Sets the incremental version number for this.
	 *
	 * @param versionNumber the new incremental version number for this
	 */
	public void setVersionNumber(Long versionNumber) {
		this.versionNumber = versionNumber;
	}




	/**
	 * Gets the time the next attempt may be tried.
	 *
	 * @return the time the next attempt may be tried
	 */
	public Timestamp getNextAttempt() {
		return nextAttempt;
	}

	/**
	 * Sets the time the next attempt may be tried.
	 *
	 * @param next_attempt the new time the next attempt may be tried
	 */
	public void setNextAttempt(Timestamp next_attempt) {
		this.nextAttempt = next_attempt;
	}

	/**
	 * Gets the number of retries that has been attempted.
	 *
	 * @return the number of retries that has been attempted
	 */
	public int getRetries() {
		return retries;
	}

	/**
	 * Sets the number of retries that has been attempted.
	 *
	 * @param retries the new number of retries that has been attempted
	 */
	public void setRetries(int retries) {
		this.retries = retries;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		String newline = "\r\n";

		String str = newline + "ID:          " + this.getId();
		str = str + newline + "Sent:       " + (this.isSent() ? "True" : "False");
		str = str + newline + "Timestamp:  " + this.getPostedTimestamp();    
		return str;
	}


}
