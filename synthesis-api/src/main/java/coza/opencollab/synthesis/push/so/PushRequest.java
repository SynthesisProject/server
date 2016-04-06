package coza.opencollab.synthesis.push.so;

import coza.opencollab.synthesis.service.api.ServiceObject;

/**
 * Created by charl on 2014/07/10.
 */
public class PushRequest extends ServiceObject{

	private static final long serialVersionUID = 7479646313271271643L;
	
	private String username;
	private String authToken;
	private Long pushId;

	public PushRequest(){}

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
	 * @return the pushId
	 */
	public Long getPushId() {
		return pushId;
	}

	/**
	 * @param pushId the pushId to set
	 */
	public void setPushId(Long pushId) {
		this.pushId = pushId;
	}
	
	

}
