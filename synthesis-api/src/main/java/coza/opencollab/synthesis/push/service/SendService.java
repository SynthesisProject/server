package coza.opencollab.synthesis.push.service;

import java.util.List;

import coza.opencollab.synthesis.push.dbo.PushDeviceTuple;

/**
 * This is an interface for an service that can send Push messages to devices.
 * The implementation of this service should understand how to send a message to each.
 * 
 */
public interface SendService {
	
	/** Amount to backoff before re-attempting. */
	public static final int BACKOFF = 60000;
	
	/** Maximum number of retry attempts before a message is  marked as failed. */
	public static final int MAX_RETRIES = 3;

	/**
	 * Send push.
	 *
	 * @param tuples the tuples
	 * @throws IllegalArgumentException the illegal argument exception
	 */
	void sendPush(List<PushDeviceTuple> tuples) throws IllegalArgumentException;

	/**
	 * Gets the bulk size.
	 *
	 * @return the bulk size
	 */
	int getBulkSize();

}
