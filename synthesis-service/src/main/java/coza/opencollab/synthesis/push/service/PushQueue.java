package coza.opencollab.synthesis.push.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import coza.opencollab.synthesis.push.dbo.PushDeviceTuple;


/**
 * A Task that will check for unsent notifications and send them.
 */
public class PushQueue {

	/**  A reference to a logger. */
	private static final Logger LOG = Logger.getLogger(PushQueue.class);
	
	/**  A list of senders that this service can use. */
	@Resource(name="pushSenders")
	private Map<String, SendService> senders;
	
	/** A reference to the <code>PushService</code>. */
	@Autowired
	@Qualifier("pushService")
	private PushService pushService;
	
	/** The push device tuple service. */
	@Autowired
	@Qualifier("pushDeviceTupleService")
	private PushDeviceTupleService pushDeviceTupleService;
	
	/**
	 * Gets the a list of senders that this service can use.
	 *
	 * @return the a list of senders that this service can use
	 */
	private Map<String, SendService> getSenders(){
		return this.senders;
	}
	
	/**
	 * Runs the task to send notifications.
	 */
	public void checkPushes() {
		LOG.info("Checking push Queue");
		// Get the list of senders we have
		for(String platform : getSenders().keySet()){
			SendService sender = getSenders().get(platform);
			List<PushDeviceTuple> tuples = pushDeviceTupleService.findNextQueued(platform, sender.getBulkSize());
			LOG.info("Found " + tuples.size() + " queued messages for " + platform);
			// Loop while we still have tuples ready to be sent for this platform
			while(tuples.size() > 0){

				// Let the sender handle the pushes
				sender.sendPush(tuples);
				
				// Get the next batch of tuples for this platform
				tuples = pushDeviceTupleService.findNextQueued(platform, sender.getBulkSize());
			}
		}
	}
	
	/**
	 * Sets the a reference to the <code>PushService</code>.
	 *
	 * @param pushService the new a reference to the <code>PushService</code>
	 */
	public void setPushService(PushService pushService){
		this.pushService = pushService;
	}
}
