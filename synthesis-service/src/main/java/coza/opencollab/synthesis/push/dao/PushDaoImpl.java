package coza.opencollab.synthesis.push.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import coza.opencollab.synthesis.push.dbo.Device;
import coza.opencollab.synthesis.push.dbo.Push;
import coza.opencollab.synthesis.push.dbo.PushDeviceTuple;

/**
 * Implementation of the Push Data Access Object for <code>Push</code> objects
 */
@Repository("pushDao")
public class PushDaoImpl implements PushDao {

	/** A reference to a logger */
	private static final Logger LOG = LoggerFactory.getLogger(PushDaoImpl.class);

	/** A reference to the <code>EntityManger</code> */
	@PersistenceContext
	private EntityManager entityManager;
	
	/**
	 * Creates a new instance of a <code>PushDaoImpl</code>
	 */
	public PushDaoImpl(){}

	/*
	 * (non-Javadoc)
	 * @see coza.opencollab.synthesis.push.dao.PushDao#findPushById(java.lang.Long)
	 */
	public Push findPushById(Long id){
		Query query = entityManager.createNamedQuery("Push.find");
		query.setParameter("pushId", id);
		Push result;
		try{
			result = (Push) query.getSingleResult();
		}catch(Exception e){
			LOG.info("Exception: " + e.getMessage());
			result = null;
		}
		return result;
	}

	/*
	 * (non-Javadoc)
	 * @see coza.opencollab.synthesis.push.dao.PushDao#findAllPush()
	 */
	@SuppressWarnings("unchecked")
	public List<Push> findAllPush(){
		Query query = entityManager.createNamedQuery("Push.findAll");
		return query.getResultList();
	}

	/*
	 * (non-Javadoc)
	 * @see coza.opencollab.synthesis.push.dao.PushDao#countPushes()
	 */
	public int countPushes(){
		Query query = entityManager.createNamedQuery("Push.countAll");
		return ((Long)query.getSingleResult()).intValue();
	}

	/*
	 * (non-Javadoc)
	 * @see coza.opencollab.synthesis.push.dao.PushDao#findUnsentPushTuples()
	 */
	@SuppressWarnings("unchecked")
	public List<PushDeviceTuple> findUnsentPushTuples(){
		Query query = entityManager.createNamedQuery("PushDeviceTuple.findUnsent");
		return query.getResultList();
	}
	
	public PushDeviceTuple findOldestUnsentTuple(){
		Query query = entityManager.createNamedQuery("PushDeviceTuple.findOldestUnsentTuple");
		query.setMaxResults(1);
		return (PushDeviceTuple)query.getSingleResult();
	}

	/*
	 * (non-Javadoc)
	 * @see coza.opencollab.synthesis.push.dao.PushDao#savePush(coza.opencollab.synthesis.push.dbo.Push)
	 */
	@Transactional
	public Push savePush(Push push){
		if(push == null){
			return null;
		}
		if(push.getPushId() == null){
			entityManager.persist(push);
            return push;
		}else{
			return entityManager.merge(push);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see coza.opencollab.synthesis.push.dao.PushDao#removePush(coza.opencollab.synthesis.push.dbo.Push)
	 */
	@Transactional
	public void removePush(Long pushId){
		Push push = this.findPushById(pushId);
		if(push == null) return;
		entityManager.remove(push);
	}	
	

	/*
	 * (non-Javadoc)
	 * @see coza.opencollab.synthesis.push.dao.PushDao#findDevicesForPush(coza.opencollab.synthesis.push.dbo.Push)
	 */
	@SuppressWarnings("unchecked")
	@Transactional
	public List<Device> findDevicesForPush(Push push){
		Query query = entityManager.createNamedQuery("PushDeviceTuple.findPushDevices");
		query.setParameter("pushId", push.getPushId());
		return query.getResultList();
	}

}
