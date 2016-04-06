package coza.opencollab.synthesis.push.dao;

import java.util.List;

import coza.opencollab.synthesis.push.dbo.Device;
import coza.opencollab.synthesis.push.dbo.Push;

/**
 * Data Access Object for Push Objects.
 */
public interface PushDao {

	/**
	 * Persists a <code>Push</code> instance.
	 *
	 * @param push the push
	 * @return the push
	 */
	Push savePush(Push push);

	/**
	 * Removes a <code>Push</code> instance.
	 *
	 * @param pushId the push id
	 */
	void removePush(Long pushId);


	/**
	 * Returns the number of <code>Push</code> messages persisted.
	 * @return number of <code>Push</code> messages persisted.
	 */
	int countPushes();


	/**
	 * Finds a <code>Push</code> by its ID.
	 * @param id ID of the <code>Push</code> to find.
	 * @return The <code>Push</code> that has the specified ID.
	 */
	Push findPushById(Long id);


	/**
	 * Returns a list of <b>ALL</b> the <code>Push</code> messages
	 * that are persisted.<br>
	 * <b>WARNING: This method can return A LOT of data</b>
	 * @return A lsit of all the persisted <code>Push</code> messages.
	 */
	List<Push> findAllPush();


	/**
	 * Finds the devices a push message was sent too.
	 *
	 * @param push <code>Push</code> message to find devices of.
	 * @return the list
	 */
	List<Device> findDevicesForPush(Push push);

}
