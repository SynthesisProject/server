package coza.opencollab.synthesis.push.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import coza.opencollab.synthesis.push.dao.DeviceDao;
import coza.opencollab.synthesis.push.dbo.Device;

/**
 * Implementation of the Device Service.
 */
@Service("deviceService")
public class DeviceServiceImpl implements DeviceService {

	/**  A reference to a logger for this service. */
	private static final Logger LOG = Logger.getLogger(DeviceServiceImpl.class);
	
	/**  A reference to the <code>DeviceDao</code>. */
	@Autowired
	private DeviceDao deviceDao;

	/* (non-Javadoc)
	 * @see coza.opencollab.synthesis.push.service.DeviceService#saveDevice(coza.opencollab.synthesis.push.dbo.Device)
	 */
	@Override
	public Device saveDevice(Device device){
		return deviceDao.saveDevice(device);
	}


	/* (non-Javadoc)
	 * @see coza.opencollab.synthesis.push.service.DeviceService#findAllDevices()
	 */
	@Override
	public List<Device> findAllDevices() {
		return deviceDao.findAllDevices();
	}


	/* (non-Javadoc)
	 * @see coza.opencollab.synthesis.push.service.DeviceService#findDevicesByUsername(java.lang.String)
	 */
	@Override
	public List<Device> findDevicesByUsername(String username) {
		return deviceDao.findDevicesByUsername(username);
	}

	/* (non-Javadoc)
	 * @see coza.opencollab.synthesis.push.service.DeviceService#findDeviceById(java.lang.Long)
	 */
	@Override
	public Device findDeviceById(Long id) {
		return deviceDao.findDeviceById(id);
	}

	/* (non-Javadoc)
	 * @see coza.opencollab.synthesis.push.service.DeviceService#findDevicesByPlatform(java.lang.String)
	 */
	@Override
	public List<Device> findDevicesByPlatform(String platform){
		 return deviceDao.findDevicesByType(platform);
	 }

	/* (non-Javadoc)
	 * @see coza.opencollab.synthesis.push.service.DeviceService#removeDevice(java.lang.Long)
	 */
	@Override
	public void removeDevice(Long id){
		deviceDao.removeDevice(id);
	}

	
	/* (non-Javadoc)
	 * @see coza.opencollab.synthesis.push.service.DeviceService#removeAllDevices()
	 */
	@Override
	public boolean removeAllDevices(){
		return deviceDao.removeAllDevices();
	}

	/* (non-Javadoc)
	 * @see coza.opencollab.synthesis.push.service.DeviceService#countDevices()
	 */
	@Override
	public Long countDevices(){
		return deviceDao.countDevices();
	}

	/* (non-Javadoc)
	 * @see coza.opencollab.synthesis.push.service.DeviceService#countDevices(java.lang.String)
	 */
	@Override
	public Long countDevices(String deviceType) {
		return this.deviceDao.countDevices(deviceType);
	}


	/**
	 * Sets the a reference to the <code>DeviceDao</code>.
	 *
	 * @param dao the new a reference to the <code>DeviceDao</code>
	 */
	public void setDeviceDao(DeviceDao dao) {
		this.deviceDao = dao;
	}


	/**
	 * Gets the a reference to the <code>DeviceDao</code>.
	 *
	 * @return the a reference to the <code>DeviceDao</code>
	 */
	public DeviceDao getDeviceDao() {
		return this.deviceDao;
	}


	/* (non-Javadoc)
	 * @see coza.opencollab.synthesis.push.service.DeviceService#findDeviceByUUID(java.lang.String)
	 */
	@Override
	public Device findDeviceByUUID(String uuid) {
		return deviceDao.findDeviceByDeviceId(uuid);
	}


	/* (non-Javadoc)
	 * @see coza.opencollab.synthesis.push.service.DeviceService#findDeviceByRegistrationId(java.lang.String)
	 */
	@Override
	public Device findDeviceByRegistrationId(String regid) {
		return deviceDao.findDeviceByRegId(regid);
	}
	
}
