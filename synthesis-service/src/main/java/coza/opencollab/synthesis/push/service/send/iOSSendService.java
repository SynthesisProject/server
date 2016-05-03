package coza.opencollab.synthesis.push.service.send;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;
import java.util.List;

import javax.net.ssl.SSLSocket;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.io.IOUtils;
import org.apache.commons.pool.impl.GenericObjectPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import coza.opencollab.synthesis.push.dbo.Device;
import coza.opencollab.synthesis.push.dbo.Push;
import coza.opencollab.synthesis.push.dbo.PushDeviceTuple;
import coza.opencollab.synthesis.push.service.PushDeviceTupleService;
import coza.opencollab.synthesis.push.service.SendService;
import net.sf.json.JSONObject;

/**
 * An implementation of the SendService for iOS.
 * 
 * This implementation makes use of a connection pool that needs to be configured with spring.
 * The connection pool ensures that a maximum number of open connections can be controlled, as well as closing
 * unsed connections when not used for a period of time.
 * <br>
 * This implementation will attempt to send a message with three retries before giving up.
 *
 */
public class iOSSendService implements SendService {
	
	/** The Constant FIRST_BYTE. */
	private static final int FIRST_BYTE = 0;
	
	/** The Constant SECOND_BYTE. */
	private static final int SECOND_BYTE = 1;
	
	/** The Constant THIRD_BYTE. */
	private static final int THIRD_BYTE = 2;
	
	/** The Constant FORTH_BYTE. */
	private static final int FORTH_BYTE = 3;
	
	/** The Constant DEVICE_ID_LENGTH. */
	private static final int DEVICE_ID_LENGTH = 32;
	
	/** The Constant BATCH_SIZE. */
	private static final int BATCH_SIZE = 1;
	
	
	/**  Connection pool. */
	@Autowired
	private GenericObjectPool<SSLSocket> iOSConnectionPool;

	/** The push device tuple service. */
	@Autowired
	private PushDeviceTupleService pushDeviceTupleService;
	
	/**  Reference to a logger. */
	private static final Logger LOG = LoggerFactory.getLogger(iOSSendService.class);

	
	/* (non-Javadoc)
	 * @see coza.opencollab.synthesis.push.service.SendService#sendPush(java.util.List)
	 */
	@Override
	public void sendPush(List<PushDeviceTuple> tuples) {
		if(tuples.size() > BATCH_SIZE){
			throw new IllegalArgumentException("You are attempting to send to more devices in bulk than allowed");
		}

		if(tuples.size() == 0){
			LOG.info("No devices in tuple list, skipping..");
			return;
		}
		this.sendPush(tuples.get(0));
	}
	
	/**
	 * Sends the specified <code>Push</code> message to the specified <code>Device</code>.
	 * This implementation makes use of a connection pool. If there is currently no connection 
	 * available the current thread will block until a connection becomes available (unless 
	 * otherwise configured)
	 *
	 * @param tuple the tuple
	 */
	private void sendPush(PushDeviceTuple tuple) {
		
		Push push = tuple.getPush();
		Device device = tuple.getDevice();

		byte[] payload = preparePayload(push);
		byte[] deviceToken = createDeviceToken(device);

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			baos.write(1); 					// Command Byte: New format = 1
			baos.write(deviceToken[FIRST_BYTE]); 	// Identifier Byte 1
			baos.write(deviceToken[SECOND_BYTE]); 	// Identifier Byte 2
			baos.write(deviceToken[THIRD_BYTE]); 	// Identifier Byte 3
			baos.write(deviceToken[FORTH_BYTE]); 	// Identifier Byte 4
			baos.write(0); 					// Expiry Byte 1
			baos.write(0); 					// Expiry Byte 2
			baos.write(0); 					// Expiry Byte 3
			baos.write(1); 					// Expiry Byte 4
			baos.write(0); 					// Device ID Length
			baos.write(DEVICE_ID_LENGTH);
			baos.write(deviceToken); 		// Device ID
			baos.write(0); 					// Payload Length
			baos.write(payload.length);
			baos.write(payload); 			// Payload
		} catch ( IOException e ) {
			LOG.error("Failed Creating Payload", e);
			return;
		} 

		OutputStream out = null;//CodeReview could use chained streams here
		SSLSocket socket = null;
		try {
			socket= iOSConnectionPool.borrowObject();
			out = socket.getOutputStream();
			out.write(baos.toByteArray());
			if (LOG.isDebugEnabled()){
				LOG.debug(baos.toString());
			}
			out.flush(); // We do not close the output stream as it is reused
			
			tuple.setStatus(PushDeviceTuple.STATUS_SENT);
			tuple = pushDeviceTupleService.saveTuple(tuple);
		} catch (Exception e) {
			LOG.error("Exception while trying to write message over socket (Retry attempt : " + tuple.getRetries() + ")", e);
			IOUtils.closeQuietly(out); // Close potentially broken stream
			
			int retries = tuple.getRetries();
			if(retries >= MAX_RETRIES){
				LOG.warn("Max number of retries reached for a message, marking as failed");
				tuple.setStatus(PushDeviceTuple.STATUS_FAILED);
				tuple = pushDeviceTupleService.saveTuple(tuple);
			}
			else{
				retries++;
				tuple.setStatus(PushDeviceTuple.STATUS_WAITING_RETRY);
				tuple.setRetries(retries);
				tuple.setNextAttempt(new Timestamp(System.currentTimeMillis() + (retries * BACKOFF)));
				tuple = pushDeviceTupleService.saveTuple(tuple);
			}
		}
		finally{
			try {
				iOSConnectionPool.returnObject(socket);
			} catch (Exception e) {
				LOG.warn("Exception while trying to put Socket back into pool",e);
			}
		}
	}

	/**
	 * Creates a device token from the device object.
	 *
	 * @param device The device to create a token of
	 * @return The byte array of the token
	 */
	private static byte[] createDeviceToken(Device device){
		char[] t = device.getRegId().toCharArray();
		byte[] tokenBytes = null;
		try {
			tokenBytes = Hex.decodeHex(t);
		} catch (DecoderException e) {
			LOG.error("Failed decoding Token", e);
		}
		return tokenBytes;
	}

	/**
	 * This method returns a String in the format required by APNS.
	 *
	 * @param p The push message to create payload of.
	 * @return A string formatted ready to be sent to APNS
	 */
	private static byte[] preparePayload(Push p){
		String message;
		JSONObject pushJson = new JSONObject();
		
		// Build aps fields
		JSONObject aps = new JSONObject();
		aps.accumulate("badge", "0");
		aps.accumulate("sound", "default");
		if(p.getEmergency()){
			JSONObject alert = new JSONObject();
			alert.accumulate("title", p.getTitle());
			alert.accumulate("body", p.getMessage());
			aps.accumulate("alert", alert);
		}
		pushJson.accumulate("aps", aps);
		pushJson.accumulate("id", String.valueOf(p.getPushId()));
		pushJson.accumulate("e", (p.getEmergency()?"YES":"NO"));
		
		message = pushJson.toString();
		
		try {
			return message.getBytes("UTF-8");
		} catch (UnsupportedEncodingException e) {
			LOG.warn("Exception while converting device token from string to bytes", e);
			return null;
		}
	}
	
	/* (non-Javadoc)
	 * @see coza.opencollab.synthesis.push.service.SendService#getBulkSize()
	 */
	@Override
	public int getBulkSize() {
		return BATCH_SIZE;
	}

}
