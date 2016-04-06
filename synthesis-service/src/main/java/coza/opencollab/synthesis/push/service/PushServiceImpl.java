package coza.opencollab.synthesis.push.service;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import coza.opencollab.synthesis.push.dao.PushDao;
import coza.opencollab.synthesis.push.dbo.Device;
import coza.opencollab.synthesis.push.dbo.Push;
import coza.opencollab.synthesis.push.dbo.PushDeviceTuple;
import coza.opencollab.synthesis.service.api.dbo.ManagedModule;
import coza.opencollab.synthesis.service.api.dbo.Tool;

/**
 * Implementation of the <code>PushService</code>.
 */
@Service("pushService")
public class PushServiceImpl implements PushService {

	/** The Constant LOG. */
	private static final Logger LOG = LoggerFactory.getLogger(PushServiceImpl.class);
	
	/**  A cached thread pool which allows us to schedule jobs. */
	private final ExecutorService exeService = Executors.newCachedThreadPool();
	
	/** A reference to the application context which we will use to get instances of jobs. */
	@Autowired
	private ApplicationContext applicationContext;

	/** A reference to the <code>PushDeviceTupleService</code> object used by this ServiceImpl. */
	@Autowired
	private PushDeviceTupleService pdtService;

	/**  A reference to the <code>PushDao</code>. */
	@Autowired
	private PushDao pushDao;


	/* (non-Javadoc)
	 * @see coza.opencollab.synthesis.push.service.PushService#savePush(coza.opencollab.synthesis.push.dbo.Push)
	 */
	@Override
	@Transactional
	public Push savePush(Push push) {
		return pushDao.savePush(push);
	}

	/* (non-Javadoc)
	 * @see coza.opencollab.synthesis.push.service.PushService#removePush(java.lang.Long)
	 */
	@Override
	@Transactional
	public void removePush(Long pushId) {
		pushDao.removePush(pushId);
	}

	/* (non-Javadoc)
	 * @see coza.opencollab.synthesis.push.service.PushService#findPushById(java.lang.Long)
	 */
	@Override
	public Push findPushById(Long id) {
		return pushDao.findPushById(id);
	}

	/* (non-Javadoc)
	 * @see coza.opencollab.synthesis.push.service.PushService#findDevicesForPush(coza.opencollab.synthesis.push.dbo.Push)
	 */
	@Override
	public List<Device> findDevicesForPush(Push push) {
		return pushDao.findDevicesForPush(push);
	}

	/* (non-Javadoc)
	 * @see coza.opencollab.synthesis.push.service.PushService#findAllPush()
	 */
	@Override
	public List<Push> findAllPush() {
		return pushDao.findAllPush();
	}

	/* (non-Javadoc)
	 * @see coza.opencollab.synthesis.push.service.PushService#countPushes()
	 */
	@Override
	public int countPushes() {
		return pushDao.countPushes();
	}

	/**
	 * Send push.
	 *
	 * @param tuples the tuples
	 */
	@Transactional
	public void sendPush(List<PushDeviceTuple> tuples) {
		Iterator<PushDeviceTuple> i = tuples.iterator();
		while (i.hasNext()) {
			PushDeviceTuple pdt = i.next();
			pdtService.markTupleAsSent(pdt);
		}
	}


	/* (non-Javadoc)
	 * @see coza.opencollab.synthesis.push.service.PushService#queuePush(coza.opencollab.synthesis.push.dbo.Push, java.util.Collection)
	 */
	@Override
	@Transactional
	public void queuePush(Push push, Collection<Device> devices) {
		if(push == null){
			return;
		}
		push = this.savePush(push);
		
		Iterator<Device> i = devices.iterator();
		while(i.hasNext()){
			Device device = i.next();
            // Somehow the iterator gives a null even though there is no null entries
            if(device == null) continue;
            
			this.queuePush(push, device);
		}
	}
	
	/* (non-Javadoc)
	 * @see coza.opencollab.synthesis.push.service.PushService#queuePush(coza.opencollab.synthesis.push.dbo.Push, coza.opencollab.synthesis.push.dbo.Device)
	 */
	@Override
	@Transactional
	public void queuePush(Push push, Device device) {
		/*
		 *  If we don't know the device platform, we'll never know which sending implementation we
		 *  need to use to send it with. Even if we at a later stage get this information, it's fine if 
		 *  we skip THIS notification, because the guy is probably not even registered yet
		 *  
		 *   If we don't have the registration id, we cannot talk to the device in any case.
		 *   Attempting to send to it, will cause the push provider to indicate to us that
		 *   the device is invalid, and we will end up deleting it - which might not be what we want
		 *   the person could still be in progress registering for push notifications
		 */
		if(device.getPlatform() == null || device.getRegId() == null){
			LOG.warn("Not scheduling push for an invalid device");
			return;
		}
		PushDeviceTuple pdt = new PushDeviceTuple();
		pdt.setPush(push);
		pdt.setDevice(device);
		pdt.setPostedTimestamp(new Timestamp(System.currentTimeMillis()));
		pdt.setNextAttempt(new Timestamp(System.currentTimeMillis()));
		pdt.setStatus(PushDeviceTuple.STATUS_PENDING);
		pdtService.saveTuple(pdt);
	}

	/* (non-Javadoc)
	 * @see coza.opencollab.synthesis.push.service.PushService#sheduleToolChangePush(coza.opencollab.synthesis.service.api.dbo.ManagedModule, coza.opencollab.synthesis.service.api.dbo.Tool)
	 */
	@Override
	public void sheduleToolChangePush(ManagedModule module, Tool tool) {
		ToolChangesPushJob job = applicationContext.getBean(ToolChangesPushJob.class);
		job.setModule(module);
		job.setTool(tool);
		exeService.execute(job);
	}

	

}
