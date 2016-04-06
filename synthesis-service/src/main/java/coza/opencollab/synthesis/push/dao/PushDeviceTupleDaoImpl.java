package coza.opencollab.synthesis.push.dao;

import java.util.Calendar;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TemporalType;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import coza.opencollab.synthesis.push.dbo.PushDeviceTuple;

@Repository("pushDeviceTupleDao")
public class PushDeviceTupleDaoImpl implements PushDeviceTupleDao {

	/**
	 * A reference to the <code>EntityManager</code>
	 */
	@PersistenceContext
	private EntityManager entityManager;

	/**
	 * Creates a new instance of a <code>PushDeviceTupleDaoImpl</code>
	 */
	public PushDeviceTupleDaoImpl(){
	}

	public void markTupleAsSent(PushDeviceTuple tuple){
		tuple.setSent();
		this.saveTuple(tuple);
	}

	@Transactional
	public PushDeviceTuple saveTuple(PushDeviceTuple tuple){
		if(tuple.getId() == null){
			entityManager.persist(tuple);
			return tuple;
		}else{
			return entityManager.merge(tuple);
		}
	}

	@SuppressWarnings("unchecked")
	public List<PushDeviceTuple> findUnsentTuples(){
		Query query = entityManager.createNamedQuery("PushDeviceTuple.findUnsent");
		return query.getResultList();
	}

	
	@SuppressWarnings("unchecked")
	public List<PushDeviceTuple> findTuplesForPush(Long pushId){
		Query query = entityManager.createNamedQuery("PushDeviceTuple.findTuplesForPush");
		query.setParameter("pushId", pushId);
		return query.getResultList();
	}


	@Override
	public PushDeviceTuple findNextQueuedTuple(String platform) {
		Query query = entityManager.createNamedQuery("PushDeviceTuple.findNextQueued");
		query.setParameter("timestamp", Calendar.getInstance(), TemporalType.TIMESTAMP);
		query.setParameter("platform", platform);
		query.setMaxResults(1);
		PushDeviceTuple tuple = null;
		try{
			tuple = (PushDeviceTuple)query.getSingleResult();
		}catch (NoResultException nre){
		}
		return tuple;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PushDeviceTuple> findNextQueuedBatch(String platform, Long pushId, int limit) {
		Query query = entityManager.createNamedQuery("PushDeviceTuple.findNextQueuedBatch");
		query.setParameter("pushId", pushId);
		query.setParameter("platform", platform);
		query.setParameter("timestamp", Calendar.getInstance(), TemporalType.TIMESTAMP);
		query.setMaxResults(limit);
		return query.getResultList();
	}

	@Override
	public PushDeviceTuple findTupleById(Long id) {
		return entityManager.find(PushDeviceTuple.class, id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PushDeviceTuple> getQueue() {
		Query query = entityManager.createNamedQuery("PushDeviceTuple.findAll");
		return query.getResultList();
	}
}
