package coza.opencollab.synthesis.push.dao;

import java.sql.Timestamp;
import java.util.List;

import coza.opencollab.synthesis.push.dbo.Device;

/**
 * Interface for Accessing Device related data from a data source.
 */
public interface DeviceDao {

	/**
	 * Persists a <code>Device</code> instance.
	 *
	 * @param device the device
	 * @return  The device
	 */
	Device saveDevice(Device device);

	/**
	 * Removes a <code>Device</code> instance from persistence.
	 *
	 * @param id the id
	 */
	void removeDevice(Long id); 

	/**
	 * Removes the all devices with username.
	 *
	 * @param username the username
	 * @return True if all devices are deleted
	 */
	boolean removeAllDevicesWithUsername(String username);	

	/**
	 * Removes the all devices.
	 *
	 * @return True if all devices are deleted
	 */
	boolean removeAllDevices();

	/**
	 * Removes the all devices by type.
	 *
	 * @param type the type
	 * @return True if all devices are deleted
	 */
	boolean removeAllDevicesByType(String type);

	/**
	 * Removes the all devices before.
	 *
	 * @param t the t
	 * @return True if all devices are deleted
	 */
	boolean removeAllDevicesBefore(Timestamp t);

	/**
	 * Counts the number of all registered devices.
	 * This can included devices that does not have a Registration ID and Username.
	 * @return Number of devices
	 */
	Long countDevices();


	/**
	 * Counts the number of devices by device type.
	 * This can included devices that does not have a Registration ID and Username.
	 *
	 * @param deviceType the device type
	 * @return the long
	 */
	Long countDevices(String deviceType);


	/**
	 * Returns a count of all devices that does not have a username.
	 *
	 * @return the long
	 */
	Long countDevicesWithoutUsername();

	/**
	 * Count devices before.
	 *
	 * @param ts the ts
	 * @return the long
	 */
	Long countDevicesBefore(Timestamp ts);

	/**
	 * Count devices with username.
	 *
	 * @param username the username
	 * @return the long
	 */
	Long countDevicesWithUsername(String username);

	/**
	 * Find all devices that are registered.
	 * This can included devices that does not have a Registration ID and Username
	 *
	 * @return the list
	 */
	List<Device> findAllDevices();


	/**
	 * Finds all devices of a specific type.
	 * This can included devices that does not have a Registration ID and Username
	 *
	 * @param deviceType the device type
	 * @return the list
	 */
	List<Device> findAllDevices(String deviceType);


	/**
	 * Return tue if there is a username for the specifed deviceId.
	 *
	 * @param deviceid the deviceid
	 * @return true, if successful
	 */
	boolean doesDeviceHaveUsername(String deviceid);


	/**
	 * Returns a list of devices registed by a username.
	 *
	 * @param username the username
	 * @return the list
	 */
	List<Device> findDevicesByUsername(String username);


	/**
	 * Finds all device that does not have a username.
	 *
	 * @return the list
	 */
	List<Device> findDevicesWithoutUsername();


	/**
	 * Finds devices with the specified device id.
	 * In theory, this should only return on <code>Device</code>
	 *
	 * @param deviceid the deviceid
	 * @return the list
	 */
	List<Device> findDevicesByDeviceId(String deviceid);

	/**
	 * Find devices by type.
	 *
	 * @param type the type
	 * @return the list
	 */
	List<Device> findDevicesByType(String type);

	/**
	 * Finds devices with the specified device id.
	 *
	 * @param regid the regid
	 * @return the device
	 */
	Device findDeviceByRegId(String regid);

	/**
	 * Finds a device with the specified device ID.
	 *
	 * @param deviceid the deviceid
	 * @return the device
	 */
	Device findDeviceByDeviceId(String deviceid);

	/**
	 * Finds a List of <code>Device</code> based on the specified keyword.
	 *
	 * @param keyword the keyword
	 * @return Device List.
	 */
	List<Device> findDevicesByKeyword(String keyword);

	/**
	 * Finds a <code>Device</code> by the Object ID for the <code>Device</code>.
	 *
	 * @param id If of the <code>Device</code> to find.
	 * @return the device
	 */
	Device findDeviceById(Long id);

}
