package coza.opencollab.synthesis.push.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import coza.opencollab.synthesis.push.dao.PreferenceDao;
import coza.opencollab.synthesis.push.dbo.Device;
import coza.opencollab.synthesis.push.dbo.Preference;
import coza.opencollab.synthesis.push.service.PreferenceService;

import java.util.List;

/**
 * Implementation of the Sender Service.
 */
@Service("preferenceService")
public class PreferenceServiceImpl implements PreferenceService{
	
	/**  A reference to the <code>PreferenceDao</code>. */
	@Autowired
	private PreferenceDao preferenceDao;

	/* (non-Javadoc)
	 * @see coza.opencollab.synthesis.push.service.PreferenceService#findPreference(java.lang.Long, java.lang.String)
	 */
	@Override
	public Preference findPreference(Long deviceId, String sender) {
		return preferenceDao.findPreference(deviceId, sender);
	}

	/* (non-Javadoc)
	 * @see coza.opencollab.synthesis.push.service.PreferenceService#findPreference(long)
	 */
	@Override
	public Preference findPreference(long id){
		return preferenceDao.findPreference(id);
	}

	/* (non-Javadoc)
	 * @see coza.opencollab.synthesis.push.service.PreferenceService#findDevicesThatAllowedSender(java.lang.String)
	 */
	@Override
	public List<Device> findDevicesThatAllowedSender(String senderKey) {
		return preferenceDao.findDeviceThatAllowSender(senderKey);
	}

	/* (non-Javadoc)
	 * @see coza.opencollab.synthesis.push.service.PreferenceService#savePreference(coza.opencollab.synthesis.push.dbo.Preference)
	 */
	@Override
	public Preference savePreference(Preference preference){
		return preferenceDao.savePreference(preference);
	}

	/* (non-Javadoc)
	 * @see coza.opencollab.synthesis.push.service.PreferenceService#removePreference(java.lang.Long)
	 */
	@Override
	public void removePreference(Long id){
		preferenceDao.removePreference(id);
	}

	/**
	 * Gets the a reference to the <code>PreferenceDao</code>.
	 *
	 * @return the a reference to the <code>PreferenceDao</code>
	 */
	public PreferenceDao getPreferenceDao() {
		return preferenceDao;
	}

	/**
	 * Sets the a reference to the <code>PreferenceDao</code>.
	 *
	 * @param preferenceDao the new a reference to the <code>PreferenceDao</code>
	 */
	@Autowired
	public void setPreferenceDao(PreferenceDao preferenceDao) {
		this.preferenceDao = preferenceDao;
	}

	/* (non-Javadoc)
	 * @see coza.opencollab.synthesis.push.service.PreferenceService#findPreferences(long)
	 */
	@Override
	public List<Preference> findPreferences(long deviceId) {
		return preferenceDao.findPreferences(deviceId);
	}

	/* (non-Javadoc)
	 * @see coza.opencollab.synthesis.push.service.PreferenceService#removeDevicePreferences(long)
	 */
	@Override
	public void removeDevicePreferences(long deviceId) {
		preferenceDao.removeDevicePreferences(deviceId);
	}

}
