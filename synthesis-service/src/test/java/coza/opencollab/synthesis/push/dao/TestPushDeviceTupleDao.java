package coza.opencollab.synthesis.push.dao;

import org.junit.Test;
import static org.junit.Assert.*;

import java.sql.Timestamp;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import coza.opencollab.synthesis.push.dbo.Device;
import coza.opencollab.synthesis.push.dbo.Push;
import coza.opencollab.synthesis.push.dbo.PushDeviceTuple;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = "classpath:TestSpringBeans.xml")
public class TestPushDeviceTupleDao {

	@Autowired
	private PushDeviceTupleDao dao;	
	
	@Autowired
	private DeviceDao deviceDao;

	@Autowired
	private PushDao pushDao;
	
	private Push createPush(){
		Push push = new Push();
		push.setEmergency(true);
		push.setMessage("Dummy message");
		push.setSender("UNIT_TEST");
		push.setTitle("TEST TITLE");
		return pushDao.savePush(push);
	}
	
	private Device createDevice(){
		Device device = new Device();
		device.setModel("UnitTestModel");
		device.setPlatform("Unit Test platform");
		device.setRegId("AABBCC");
		device.setUuid("XXYYZZ");
		return deviceDao.saveDevice(device);
	}
	
	@Test
	@DirtiesContext
	public void testPersistTuple(){
		Push push = createPush();
		Device device = createDevice();
		PushDeviceTuple tuple = new PushDeviceTuple();
		tuple.setDevice(device);
		tuple.setPush(push);
		tuple.setPostedTimestamp(new Timestamp(System.currentTimeMillis()));
		tuple = dao.saveTuple(tuple);
		assertNotNull(tuple.getId());
	}
	
	/**
	 * When deleting a device, the related tuples for the
	 * device should also be deleted
	 */
	@Test
	@DirtiesContext
	public void testDeleteDevice(){
		Push push = createPush();
		Device device = createDevice();
		PushDeviceTuple tuple = new PushDeviceTuple();
		tuple.setDevice(device);
		tuple.setPush(push);
		tuple.setPostedTimestamp(new Timestamp(System.currentTimeMillis()));
		tuple = dao.saveTuple(tuple);
		
		deviceDao.removeDevice(device.getId());
		
		// The device should be deleted
		assertNull(deviceDao.findDeviceById(device.getId()));
		
		// The Push should be still be there
		assertNotNull(pushDao.findPushById(push.getPushId()));
		
		// The tuple should be gone
		assertNull(dao.findTupleById(tuple.getId()));
	}
	
	/**
	 * When deleting a psuh, the related tuples for the
	 * push should also be deleted
	 */
	@Test
	@DirtiesContext
	public void testDeletePush(){
		Push push = createPush();
		Device device = createDevice();
		PushDeviceTuple tuple = new PushDeviceTuple();
		tuple.setDevice(device);
		tuple.setPush(push);
		tuple.setPostedTimestamp(new Timestamp(System.currentTimeMillis()));
		tuple = dao.saveTuple(tuple);
		
		pushDao.removePush(push.getPushId());
		
		// The Push should be gone
		assertNull(pushDao.findPushById(push.getPushId()));
		
		// The tuple should be gone
		assertNull(dao.findTupleById(tuple.getId()));
		
		// The device should not be deleted
		assertNotNull(deviceDao.findDeviceById(device.getId()));
	}
}
