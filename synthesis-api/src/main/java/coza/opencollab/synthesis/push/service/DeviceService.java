
package coza.opencollab.synthesis.push.service;

import java.util.List;

import coza.opencollab.synthesis.push.dbo.Device;

/**
 * Interface for managing <code>Device</code> instances.
 */
public interface DeviceService {

	/**
	 * Persists a <code>Device</code> instance.
	 *
	 * @param device The device to persist
	 * @return the device
	 */
    Device saveDevice(Device device);


	/**
	 * Removes a <code>Device</code> instance from persistence.
	 *
	 * @param id the id
	 */
    void removeDevice(Long id); 

	/**
	 * Removes the all devices.
	 *
	 * @return true, if successful
	 */
	boolean removeAllDevices();
	

	/**
	 * Counts the number of all registered devices.
	 * This can included devices that does not have a Registration ID and Username.
	 *
	 * @return the long
	 */
	Long countDevices();


	
	/**
	 * Counts the number of devices by device type.
	 * This can included devices that does not have a Registration ID and Username.
	 *
	 * @param platform the platform
	 * @return the long
	 */
	Long countDevices(String platform);
	

	/**
	 * Finds all devices of a specific type.
	 * This can included devices that does not have a Registration ID and Username
	 *
	 * @param platform the platform
	 * @return the list
	 */
	List<Device> findDevicesByPlatform(String platform);


	/**
	 * Find all devices that are registered.
	 * This can included devices that does not have a Registration ID and Username
	 *
	 * @return the list
	 */
	List<Device> findAllDevices();


	/**
	 * Returns a list of devices registed by a username.
	 *
	 * @param username the username
	 * @return the list
	 */
	List<Device> findDevicesByUsername(String username);


	/**
	 * Finds device with the specified device id.
	 * In theory, this should only return on <code>Device</code>
	 *
	 * @param uuid the uuid
	 * @return the device
	 */
	Device findDeviceByUUID(String uuid);
	
	/**
	 * Finds a device by the registrations id.
	 *
	 * @param regid Registration id of the device
	 * @return The device for that registration ID
	 */
	Device findDeviceByRegistrationId(String regid);


	/**
	 * Finds a <code>Device</code> by the Object ID for the <code>Device</code>.
	 *
	 * @param id If of the <code>Device</code> to find.
	 * @return the device
	 */
	Device findDeviceById(Long id);

}
