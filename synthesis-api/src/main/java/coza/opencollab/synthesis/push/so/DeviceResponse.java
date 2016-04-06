package coza.opencollab.synthesis.push.so;

import coza.opencollab.synthesis.push.dbo.Device;
import coza.opencollab.synthesis.service.api.ServiceObject;

/**
 * Created by charl on 2014/07/10.
 */
public class DeviceResponse extends ServiceObject{

	private Device device;
	
	public DeviceResponse (){
	}

	public DeviceResponse(ServiceObject so){
		super(so);
	}
	
	public DeviceResponse(Device device){
		this.device = device;
	}

	

	public Device getDevice() {
		return device;
	}

	public void setDevice(Device device) {
		this.device = device;
	}
}
