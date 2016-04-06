package coza.opencollab.synthesis.push.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import coza.opencollab.synthesis.push.dao.PreferenceDao;
import coza.opencollab.synthesis.push.dbo.Device;
import coza.opencollab.synthesis.push.dbo.Preference;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

/**
 * Implementation of the Preference Data Access Object.
 * 
 */
@Repository("preferenceDao")
public class PreferenceDaoImpl implements PreferenceDao{

	/** A reference to a logger */
	private static final Logger LOG = LoggerFactory.getLogger(PreferenceDaoImpl.class);
	

	@PersistenceContext
	private EntityManager entityManager;
	
	/**
	 * Creates new instance of the <code>PreferenceDaoImpl</code>.
	 */
	public PreferenceDaoImpl(){}


	@Override
	public Preference findPreference(Long deviceId, String sender) {
		try{
			Query query = getEntityManager().createNamedQuery("Preference.findPreferenceForDeviceAndSender");
			query.setParameter("deviceId", deviceId);
			query.setParameter("sender", sender);
			return (Preference)query.getSingleResult();
		}
		catch(NoResultException nre){
			// If there is no result we return nothing
			return null;
		}
	}

	public Preference findPreference(Long id){
		return entityManager.find(Preference.class, id);
		
	}

	@Transactional
	public Preference savePreference(Preference preference){
		if(preference == null){
			return null;
		}
		if(preference.getId() == null){
			entityManager.persist(preference);
		}else{
			preference = entityManager.merge(preference);
		}
		return preference;
	}

	@Transactional
	public void removePreference(Long id){
		Preference p = this.findPreference(id);
		if(p!= null){
			entityManager.remove(p);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Device> findDeviceThatAllowSender(String sender) {
		// Get all the usernames of users that blocked senders, because we only save users that opted out
		Query query = getEntityManager().createNamedQuery("Preference.findDevicesThatAllowSender");
		query.setParameter("sender", sender);
		return (List<Device>)query.getResultList();
	}

	/** A reference to the <code>EntityManager</code> */ /**
	 * Returns the reference to the <code>EntityManager</code>
	 * @return The reference to the <code>EntityManager</code>
	 */
	public EntityManager getEntityManager() {
		return entityManager;
	}

	/**
	 * Sets the reference to the <code>EntityManager</code>
	 * @param entityManager The reference to the <code>EntityManager</code>
	 */
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}


	@Override
	public List<Preference> findPreferences(long deviceId) {
		Query query = entityManager.createNamedQuery("Preference.findPreferencesForDevice");
		query.setParameter("deviceId", deviceId);
		return query.getResultList();
	}


	@Override
	@Transactional
	public void removeDevicePreferences(long deviceId) {
		List<Preference> preferences = this.findPreferences(deviceId);
		for(Preference preference : preferences){
			entityManager.remove(preference);
		}
	}
}
