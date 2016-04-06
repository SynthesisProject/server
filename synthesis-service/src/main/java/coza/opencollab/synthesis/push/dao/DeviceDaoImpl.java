package coza.opencollab.synthesis.push.dao;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import coza.opencollab.synthesis.push.dao.DeviceDao;
import coza.opencollab.synthesis.push.dbo.Device;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.sql.Timestamp;
import java.util.List;

/**
 * Implementation of the Device Data Access Object.
 */
@Repository("deviceDao")
public class DeviceDaoImpl implements DeviceDao {

	/** A reference to a logger */
	private static final Logger LOG = Logger.getLogger(DeviceDaoImpl.class);

	@PersistenceContext
	private EntityManager entityManager;

	/**
	 * Creates new instance of the <code>DeviceDaoImpl</code>.
	 */
	public DeviceDaoImpl(){}


	@SuppressWarnings("unchecked")
	@Transactional
	public List<Device> findAllDevices(){
		Query query = getEntityManager().createNamedQuery("Device.findAll");
		return query.getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<Device> findDevicesByUsername(String username){
		Query query = getEntityManager().createNamedQuery("Device.findByUsername");
		query.setParameter("username", username);
		return query.getResultList();
	}

	public boolean doesDeviceHaveUsername(String deviceid){
		Query query = getEntityManager().createNamedQuery("Device.hasUsernameForDeviceId");
		query.setParameter("deviceId", deviceid);
		Long matches = (Long)query.getSingleResult();
		return (matches.intValue() != 0);
	}

	@SuppressWarnings("unchecked")
	public List<Device> findDevicesWithoutUsername(){
		Query query = getEntityManager().createNamedQuery("Device.findDevicesWithoutUsername");
		return query.getResultList();
	}



	@SuppressWarnings("unchecked")
	public Device findDeviceById(Long id){
		Query query = getEntityManager().createNamedQuery("Device.findDevicesById");
		query.setParameter("id", id);
		Device result;
		try{
			result = (Device) query.getSingleResult();
		}catch(Exception e){
			LOG.info("Exception while trying to find device", e);
			result = null;
		}
		return result;
	}


	@SuppressWarnings("unchecked")
	public Device findDeviceByRegId(String regid){
		Query query = getEntityManager().createNamedQuery("Device.findDeviceByRegId");
		query.setParameter("regId", regid);
		Device result;
		try {
			result = (Device) query.getSingleResult();
		} catch (Exception e) {
			LOG.info("query.getSingleResult() in FindDeviceByRegId failed to return a specific Device.");
			result = null;
		}
		return result;
	}


	@SuppressWarnings("unchecked")
	public List<Device> findDevicesByDeviceId(String deviceid){
		// TODO why would there ever by more than one device per Device ID - it should be unique
		Query query = getEntityManager().createNamedQuery("Device.findDevicesByDeviceId");
		query.setParameter("deviceId", deviceid);
		return query.getResultList();
	}



	@SuppressWarnings("unchecked")
	public Device findDeviceByDeviceId(String deviceid){
		// TODO this method is confusion when looking at the method above, both use the same query but
		// the above expects a list of results, and this method expects only one result.
		// If more than one results is returned this method will throw an exception
		Query query = getEntityManager().createNamedQuery("Device.findDevicesByDeviceId");
		query.setParameter("deviceId", deviceid);
		Device result = null;
		try{
			result = (Device) query.getSingleResult();
		}catch(NoResultException e){
			LOG.info("Device with specified id does not exist");
		}
		return result;
	}


	@SuppressWarnings("unchecked")
	public List<Device> findDevicesByKeyword(String keyword){
		/*
		 * Sample Queries:
		 * 		"mtwagner"
		 * 		"u:mtwagner"
		 * 		"user:mtwagner"
		 * 		"user:mtwagner :ios" or "u:mtwagner :i"
		 * 		"user:mtwagner :android" or "u:mtwagner :a"
		 * 		"user:mtwagner device:iPad"
		 * 		"u:mtwagner d:iPad"
		 * 
		 * Creates a Dynamically generated SQL/HQL statement, but still uses bound variables so as to avoid SQL/HQL injection. 
		 * 
		 */

		// Separate the keywords.
		String[] tokens = keyword.split(" ");
		LOG.info("Searching for " + tokens.length + " tokens...");

//    	// Concatenate sub-wheres
//    	int i = 0;
//    	String where = "";
//    	for(String s: tokens){
//    		if(!"".equals(s)){
//
//    			if(s.startsWith("user:") || s.startsWith("u:")){
//    				where += "(upper(d.username) like upper(:token" + i + ")) and ";    				
//    	    		i++;
//    			}else if(s.equals(":ios") || s.equals(":i")){
//    				where += "(d.type = 'iOS') and";
//    			}else if(s.equals(":android") || s.equals(":a")){
//    				where += "(d.type = 'Android') and";    				
//    			}else if(s.startsWith("device:") || s.startsWith("d:")){
//    				where += "(upper(d.deviceName) like upper(:token" + i + ")) and";    				
//    	    		i++;
//    			}else{
//        			where += "(d.deviceId like :token" + i + " or d.regId like :token" + i + " or upper(d.deviceName) like upper(:token" + i + ") or upper(d.username) like upper(:token" + i + ")) and ";    				
//            		i++;
//    			}
//    		}else{
//    			
//    		}
//    	}

		String where = generateWhereClause(keyword);

		// Remove last "and "
		String searchString = "select d from Device d where " + where.substring(0, where.length() - "and ".length()) + " order by d.deviceName";
		LOG.info("Query: " + searchString);


		// Create Query
		Query query = entityManager.createQuery(searchString);

		// Bind the HQL variables.
		int i = 0;
		for(String s: tokens){
			if(!"".equals(s)){
				if(s.startsWith("user:")){
					query.setParameter("token" + i, "%" + s.substring("user:".length(), s.length()) + "%");
					LOG.info("Param: " + "%" + s.substring("user:".length(), s.length()) + "%");
					i++;
				}else if(s.startsWith("u:")){
					query.setParameter("token" + i, "%" + s.substring("u:".length(), s.length()) + "%");
					LOG.info("Param: " + "%" + s.substring("u:".length(), s.length()) + "%");
					i++;
				}else if(s.startsWith("device:")){
					query.setParameter("token" + i, "%" + s.substring("device:".length(), s.length()) + "%");
					LOG.info("Param: " + "%" + s.substring("device:".length(), s.length()) + "%");
					i++;
				}else if(s.startsWith("d:")){
					query.setParameter("token" + i, "%" + s.substring("d:".length(), s.length()) + "%");
					LOG.info("Param: " + "%" + s.substring("d:".length(), s.length()) + "%");
					i++;
				}else if(s.equals(":ios") || s.equals(":i") || s.equals(":android") || s.equals(":a")){
					LOG.info("No need to setParameter.");
				}else{
					query.setParameter("token" + i, "%"+s+"%");
					i++;
				}
			}
		}
		return query.getResultList();
	}

	private String generateWhereClause(String keyword){
		// Separate the keywords.
		String[] tokens = keyword.split(" ");
		LOG.info("Searching for " + tokens.length + " tokens...");

		// Concatenate sub-wheres
		int i = 0;
		String where = "";
		for(String s: tokens){
			if(!"".equals(s)){

				if(s.startsWith("user:") || s.startsWith("u:")){
					where += "(upper(d.username) like upper(:token" + i + ")) and ";
					i++;
				}else if(s.equals(":ios") || s.equals(":i")){
					where += "(d.type = 'iOS') and";
				}else if(s.equals(":android") || s.equals(":a")){
					where += "(d.type = 'Android') and";
				}else if(s.startsWith("device:") || s.startsWith("d:")){
					where += "(upper(d.deviceName) like upper(:token" + i + ")) and";
					i++;
				}else{
					where += "(d.deviceId like :token" + i + " or d.regId like :token" + i + " or upper(d.deviceName) like upper(:token" + i + ") or upper(d.username) like upper(:token" + i + ")) and ";
					i++;
				}
			}
		}
		return where;
	}

	@SuppressWarnings("unchecked")
	@Transactional
	public void removeDevice(Long id){
		Device device = this.findDeviceById(id);
		if(device == null) return;
		
		entityManager.remove(device);
	}

	@Transactional
	public boolean removeAllDevicesWithUsername(String username){
		boolean result = true;
		List<Device> devices = this.findDevicesByUsername(username);
		Query query = entityManager.createQuery("delete from Device where username = :username");
		query.setParameter("username", username);
		result = (query.executeUpdate() > 0) ? true : false;

		LOG.info("****** Found " + devices.size() + " devices with username " + username + " to delete.");
		return result;
	}

	@Transactional
	public boolean removeAllDevices(){
		boolean result = true;
		List<Device> devices = this.findAllDevices();
		LOG.info("****** Found " + devices.size() + " devices to delete.");
		Query query = entityManager.createQuery("delete from Device");
		result = (query.executeUpdate() > 0) ? true : false;

		LOG.info("****** Deleted " + result + " devices.");
		return result;
	}

	@Transactional
	public boolean removeAllDevicesByType(String type){
		boolean result = true;
		List<Device> devices = this.findAllDevices(type);
		LOG.info("****** Found " + devices.size() + " Android devices to delete.");
		Query query = entityManager.createQuery("delete from Device where type = :type");
		query.setParameter("type", type);
		result = (query.executeUpdate() > 0) ? true : false;

		LOG.info("****** Deleted " + result + " " + type + " devices.");
		return result;
	}

	@Transactional
	public boolean removeAllDevicesBefore(Timestamp ts){
		boolean result = true;

		Query query = entityManager.createQuery("select d from Device d where d.postedTimestamp < :ts");
		query.setParameter("ts", ts);
		List<Device> devices = query.getResultList();
		LOG.info("****** Found " + devices.size() + " devices registered or updated before " + ts);

		query = entityManager.createQuery("delete from Device where postedTimestamp < :ts");
		query.setParameter("ts", ts);
		result = (query.executeUpdate() > 0) ? true : false;

		LOG.info("****** Deleted " + result + " devices registered or updated before " + ts);
		return result;
	}


	@Transactional
	public Device saveDevice(Device device){
		if(device == null){
			return null;
		} else {
			LOG.debug("saveDevice: Device ID is "+device.getId());
			if(null == device.getId()){
				LOG.debug("saveDevice: performing a persist.");
				getEntityManager().persist(device);

			}else{
				LOG.debug("saveDevice: performing a merge.");
				device = getEntityManager().merge(device);
			}
			LOG.debug("saveDevice: Device ID is "+device.getId()+" post operation.");
		}
		return device;
	}


	public Long countDevices(){
		Query query = getEntityManager().createNamedQuery("Device.countDevices");
		return (Long)query.getSingleResult();
	}


	@SuppressWarnings("unchecked")
	public Long countDevicesWithoutUsername(){
		Query query = getEntityManager().createNamedQuery("Device.countDevicesWithoutUsername");
		return (Long)query.getSingleResult();
	}

	@Override
	public Long countDevices(String deviceType) {
		Query query = this.getEntityManager().createNamedQuery("Device.countDevicesForType");
		query.setParameter("deviceType", deviceType);
		return (Long)query.getSingleResult();
	}


	public Long countDevicesBefore(Timestamp ts){
		Query query = this.getEntityManager().createNamedQuery("Device.countDevicesBefore");
		query.setParameter("timeStamp", ts);
		return (Long)query.getSingleResult();
	}

	public Long countDevicesWithUsername(String username){
		Query query = this.getEntityManager().createNamedQuery("Device.countDevicesWithUsername");
		query.setParameter("username", username);
		return (Long)query.getSingleResult();
	}

	@Override
	public List<Device> findAllDevices(String deviceType) {
		Query query = this.getEntityManager().createNamedQuery("Device.findDevicesForType");
		query.setParameter("deviceType", deviceType);
		return query.getResultList();
	}

	@Override
	public List<Device> findDevicesByType(String type){
		return this.findAllDevices(type);
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

}
