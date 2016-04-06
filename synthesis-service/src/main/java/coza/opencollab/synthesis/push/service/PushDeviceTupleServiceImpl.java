
package coza.opencollab.synthesis.push.service;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import coza.opencollab.synthesis.push.dao.PushDeviceTupleDao;
import coza.opencollab.synthesis.push.dbo.PushDeviceTuple;

/**
 * Implementation of the <code>PushDeviceTupleService</code>.
 */
@Service("pushDeviceTupleService")
public class PushDeviceTupleServiceImpl implements PushDeviceTupleService{

	/**  Reference to the <code>PushDeviceTupleDao</code>. */
	@Autowired
	private PushDeviceTupleDao pdtDao;

	/*
	 * (non-Javadoc)
	 * @see coza.opencollab.synthesis.push.service.PushDeviceTupleService#markTupleAsSent(coza.opencollab.synthesis.push.dbo.PushDeviceTuple)
	 */
	@Override
	@Transactional
	public void markTupleAsSent(PushDeviceTuple tuple){
		pdtDao.markTupleAsSent(tuple);
	}

	/*
	 * (non-Javadoc)
	 * @see coza.opencollab.synthesis.push.service.PushDeviceTupleService#findUnsentTuples()
	 */
	@Override
	@Transactional
	public List<PushDeviceTuple> findUnsentTuples(){
		return pdtDao.findUnsentTuples();
	}

	/**
	 * Sets the push device tuple dao.
	 *
	 * @param dao the new push device tuple dao
	 */
	public void setPushDeviceTupleDao(PushDeviceTupleDao dao) {
		this.pdtDao = dao;
	}

	/**
	 * Gets the push device tuple dao.
	 *
	 * @return the push device tuple dao
	 */
	public PushDeviceTupleDao getPushDeviceTupleDao() {
		return pdtDao;
	}
		
	/*
	 * (non-Javadoc)
	 * @see coza.opencollab.synthesis.push.service.PushDeviceTupleService#findTuplesForPush(coza.opencollab.synthesis.push.dbo.Push)
	 */
	public List<PushDeviceTuple> findTuplesForPush(Long pushId){
		return pdtDao.findTuplesForPush(pushId);
	}

	/* (non-Javadoc)
	 * @see coza.opencollab.synthesis.push.service.PushDeviceTupleService#saveTuple(coza.opencollab.synthesis.push.dbo.PushDeviceTuple)
	 */
	@Override
	public PushDeviceTuple saveTuple(PushDeviceTuple tuple) {
		return pdtDao.saveTuple(tuple);
	}

	/* (non-Javadoc)
	 * @see coza.opencollab.synthesis.push.service.PushDeviceTupleService#findNextQueued(java.lang.String, int)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PushDeviceTuple> findNextQueued(String platform, int maxItem) {
		// Find the oldest unsent tuple for the platform
		PushDeviceTuple tuple = pdtDao.findNextQueuedTuple(platform);
		
		if(tuple == null){
			return Collections.EMPTY_LIST;
		}
		
		// Find other related tuples for that push id
		return pdtDao.findNextQueuedBatch(platform, tuple.getPush().getPushId(), maxItem);
	}

	/* (non-Javadoc)
	 * @see coza.opencollab.synthesis.push.service.PushDeviceTupleService#getQueue()
	 */
	@Override
	public List<PushDeviceTuple> getQueue() {
		return pdtDao.getQueue();
	}
}
