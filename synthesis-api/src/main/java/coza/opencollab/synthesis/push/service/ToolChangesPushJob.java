/**
 * 
 */
package coza.opencollab.synthesis.push.service;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;

import coza.opencollab.synthesis.push.dbo.Device;
import coza.opencollab.synthesis.push.dbo.Push;
import coza.opencollab.synthesis.service.api.dbo.ManagedModule;
import coza.opencollab.synthesis.service.api.dbo.Tool;

/**
 * @author charl
 *
 */
@Scope
public class ToolChangesPushJob implements PushJob{

	/**
	 * The module the push job is for
	 */
	private ManagedModule module;
	
	/**
	 * The tool has the change
	 */
	private Tool tool;
	
	@Autowired
	@Qualifier("preferenceService")
	private PreferenceService preferencesService;
	
	@Autowired
	@Qualifier("pushService")
	private PushService pushService;
	

	public void setModule(ManagedModule module){
		this.module = module;
	}
	
	public void setTool(Tool tool) {
		this.tool = tool;
	}
	
	@Override
	public void run() {
		String senderId = "tool|" + module.getModuleId() + "|" + tool.getName();
		
		// First find devices that should receive notifications for this module
		List<Device> devices = preferencesService.findDevicesThatAllowedSender(senderId);
		
		// If there are no devices, we don't need to do anything
		if(devices.isEmpty()){
			return;
		}
		
		Push push = new Push();
		push.setTitle("New Content");
		push.setEmergency(true);
		push.setMessage(module.getModuleName() + " - " + tool.getName() + " update available");
		push.setPostedTimestamp(new Timestamp(System.currentTimeMillis()));
		push.setSender(senderId);
                push.setModuleId(module.getModuleId());
                push.setToolName(tool.getName());
                push.setMessageType(Push.CONTENT_CHANGE);
		push =  pushService.savePush(push);
		
		// Now queue the message to be sent
		pushService.queuePush(push, devices);
	}
}
