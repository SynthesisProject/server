package coza.opencollab.synthesis.push.so;

import coza.opencollab.synthesis.push.dbo.Device;

/**
 * Created by charl on 2014/07/10.
 */

public class RegisterDeviceRequest{

	private Device device;
	
	private String authToken;
	
	private String username;

	public RegisterDeviceRequest(){};

	public Device getDevice() {
		return device;
	}

	public void setDevice(Device device) {
		this.device = device;
	}

	/**
	 * @return the authToken
	 */
	public String getAuthToken() {
		return authToken;
	}

	/**
	 * @param authToken the authToken to set
	 */
	public void setAuthToken(String authToken) {
		this.authToken = authToken;
	}

	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}
	
}
