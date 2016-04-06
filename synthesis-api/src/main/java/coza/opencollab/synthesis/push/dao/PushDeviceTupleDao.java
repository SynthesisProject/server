package coza.opencollab.synthesis.push.dao;

import java.util.List;

import coza.opencollab.synthesis.push.dbo.PushDeviceTuple;

/**
 * The Interface PushDeviceTupleDao.
 */
public interface PushDeviceTupleDao {

	/**
	 * DAO Method to mark a specific <code>PushDeviceTuple</code> as sent. 
	 *
	 * @param tuple the tuple
	 */
	void markTupleAsSent(PushDeviceTuple tuple);
	
	/**
	 * Finds a <code>Push</code> by its ID.
	 * @param id ID of the <code>Push</code> to find.
	 * @return The <code>Push</code> that has the specified ID.
	 */
	PushDeviceTuple findTupleById(Long id);

	/**
	 * DAO Method to save a given <code>PushDeviceTuple</code>. 
	 *
	 * @param tuple the tuple
	 * @return Long id of the saved <code>PushDeviceTuple</code>.
	 */
	PushDeviceTuple saveTuple(PushDeviceTuple tuple);

	/**
	 * DAO Method to find all unsent <code>PushDeviceTuple</code> and return them as a List PushDeviceTuple.
	 *
	 * @return List list of <code>PushDeviceTuple</code>
	 */
	List<PushDeviceTuple> findUnsentTuples();
	
	 /**
 	 * Gets the queue.
 	 *
 	 * @return the queue
 	 */
	 List<PushDeviceTuple> getQueue();

	/**
	 * DAO Method to find all <code>PushDeviceTuple</code> associated with a given <code>Push</code>.
	 *
	 * @param pushId the push id
	 * @return List list of <code>PushDeviceTuple</code>
	 */
	List<PushDeviceTuple> findTuplesForPush(Long pushId);

	/**
	 * Find the single oldest device tuple for the specified platform.
	 * This includes newly unsent tuples, and tuples waiting for a retry
	 *
	 * @param platform the platform
	 * @return the push device tuple
	 */
	PushDeviceTuple findNextQueuedTuple(String platform);
	
	/**
	 * Find the single oldest device tuple for the specified platform.
	 * This includes newly unsent tuples, and tuples waiting for a retry
	 *
	 * @param platform the platform
	 * @param pushId the push id
	 * @param limit the limit
	 * @return the list
	 */
	List<PushDeviceTuple> findNextQueuedBatch(String platform, Long pushId, int limit);



}
