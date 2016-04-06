package coza.opencollab.synthesis.push.service;

import java.util.List;

import coza.opencollab.synthesis.push.dbo.PushDeviceTuple;

/**
 * Interface for managing <code>PushDeviceTuple</code> instances.
 */
public interface PushDeviceTupleService{
	
	/**
	 * Service Method to mark a specific <code>PushDeviceTuple</code> as sent.
	 *
	 * @param tuple the tuple
	 */
	 void markTupleAsSent(PushDeviceTuple tuple);
	 
	 /**
 	 * Gets the queue.
 	 *
 	 * @return the queue
 	 */
	 List<PushDeviceTuple> getQueue();
	
	/**
	 * Service Method to save a given <code>PushDeviceTuple</code>. 
	 *
	 * @param tuple the tuple
	 * @return Long id of the saved <code>PushDeviceTuple</code>.
	 */
	 PushDeviceTuple saveTuple(PushDeviceTuple tuple);
	
	/**
	 * Service Method to find all unsent <code>PushDeviceTuple</code> and return them as a List PushDeviceTuple.
	 *
	 * @return List list of <code>PushDeviceTuple</code>
	 */
	 List<PushDeviceTuple> findUnsentTuples();
		
	/**
	 * Service Method to find all <code>PushDeviceTuple</code> associated with a given <code>Push</code>.
	 *
	 * @param pushId the push id
	 * @return List list of <code>PushDeviceTuple</code>
	 */
	 List<PushDeviceTuple> findTuplesForPush(Long pushId);

	 /**
 	 * Finds the next batch of unsent push notifications for a platform
 	 * The implementation of the service will make sure that the size of the
 	 * list is the size that can be handled by the platform sending implementation.
 	 *
 	 * @param platform Name of the platform to get the next queued items of
 	 * @param maxItem the max item
 	 * @return List of unsent tuples that are ready to be sent
 	 */
	 List<PushDeviceTuple> findNextQueued(String platform, int maxItem);
	
}
