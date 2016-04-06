package coza.opencollab.synthesis.push.so;

import java.util.List;


import coza.opencollab.synthesis.push.dbo.Device;
import coza.opencollab.synthesis.service.api.ServiceObject;


public class DevicesResponse extends ServiceObject{

	private List<Device> devices;

	public DevicesResponse(){};

	public DevicesResponse(List<Device> devices){
		this.devices = devices;
	}

	public List<Device> getDevices() {
		return devices;
	}

	public void setDevices(List<Device> devices) {
		this.devices = devices;
	}
}
