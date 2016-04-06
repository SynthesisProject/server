package coza.opencollab.synthesis.push.service;

import java.util.List;

import coza.opencollab.synthesis.push.dbo.Device;
import coza.opencollab.synthesis.push.dbo.Preference;

/**
 * Interface for retrieving and persisting <code>Preference</code> for the purpose of sending
 * <code>Push</code> messages to <code>Device</code>.
 * 
 * Implementations of this service will typically provide for the access and checking of 
 * opt-out/opt-in push preferences for the user. 
 */
public interface PreferenceService {

	/**
	 * Find preference.
	 *
	 * @param deviceId the device id
	 * @param sender the sender
	 * @return the preference
	 */
	Preference findPreference(Long deviceId, String sender);

	/**
	 * Find and return preferences for a given username.
	 * @param id Id for the preference being sought.
	 * @return A <code>Preference</code> objects for a given user.
	 */
	Preference findPreference(long id);

	
	/**
	 * Find the preferences for a device.
	 *
	 * @param deviceId Id of the device to find preferences for
	 * @return Preferences for the device
	 */
	List<Preference> findPreferences(long deviceId);
	

	/**
	 * Finds a list of devices that alows a sender to send messages.
	 *
	 * @param sender the sender
	 * @return A list of Devices that allowed the sender
	 */
	List<Device> findDevicesThatAllowedSender(String sender);


	/**
	 * Persists a <code>Preference</code> object.
	 *
	 * @param preference Preference to persist.
	 * @return the preference
	 */
	Preference savePreference(Preference preference);
	
	/**
	 * Removes a <code>Sender</code> object.
	 * @param preferenceId Id of the sender to remove
	 */
	void removePreference(Long preferenceId);
	
	/**
	 * Remove all preferences for the specified device.
	 *
	 * @param deviceId ID of the device to delete all preferences for
	 */
	void removeDevicePreferences(long deviceId);

}
