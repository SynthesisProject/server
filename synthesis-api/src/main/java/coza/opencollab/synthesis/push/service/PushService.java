package coza.opencollab.synthesis.push.service;

import java.util.Collection;
import java.util.List;

import coza.opencollab.synthesis.push.dbo.Device;
import coza.opencollab.synthesis.push.dbo.Push;
import coza.opencollab.synthesis.service.api.dbo.ManagedModule;
import coza.opencollab.synthesis.service.api.dbo.Tool;

/**
 * Interface for sending and persisting <code>Push</code> messages to
 * <code>Device</code>.
 *
 * Implementations of this service will typically persist <code>Push</code>
 * messages from a database, retrieve devices from a service, and send the
 * message to the devices using an implementation of the
 * <code>SendService</code> interface.
 */
public interface PushService {

    /**
     * Persists a <code>Push</code> message.
     *
     * @param push Push message to persist.
     * @return the push
     */
    Push savePush(Push push);

    /**
     * Removes a <code>Push</code> message.
     *
     * @param pushId the push id
     */
    void removePush(Long pushId);

    /**
     * Queues a push to many devices to be sent.
     *
     * @param push Push message to be sent.
     * @param Devices Devices the Push message should be sent to.
     */
    void queuePush(Push push, Collection<Device> Devices);

    /**
     * Schedule an outgoing push notification for a tool that changed.
     *
     * @param module the module
     * @param tool the tool
     */
    void sheduleToolChangePush(ManagedModule module, Tool tool);

    /**
     * Adds a push for a single device to the queue. This function should only
     * be used for single (test) messages. When sending messages in bulk, it
     * should rather be run in a separate Thread and tuples should be saved
     * using the PushDeviceTupleService
     *
     * @param push Push message to send
     * @param device Device to send the message to
     */
    void queuePush(Push push, Device device);

    /**
     * Returns the number of <code>Push</code> messages persisted.
     *
     * @return number of <code>Push</code> messages persisted.
     */
    int countPushes();

    /**
     * Finds a <code>Push</code> by its ID.
     *
     * @param id ID of the <code>Push</code> to find.
     * @return The <code>Push</code> that has the specified ID.
     */
    Push findPushById(Long id);

    /**
     * Returns a list of <b>ALL</b> the <code>Push</code> messages that are
     * persisted.<br>
     * <b>WARNING: This method can return A LOT of data</b>
     *
     * @return A list of all the persisted <code>Push</code> messages.
     */
    List<Push> findAllPush();

    /**
     * Finds a list of <code>Devices</code> that was linked to a
     * <code>Push</code>.
     *
     * @param push <code>Push</code> to find devices of.
     * @return List of <code>Devices</code> that was linked.
     */
    List<Device> findDevicesForPush(Push push);

}
