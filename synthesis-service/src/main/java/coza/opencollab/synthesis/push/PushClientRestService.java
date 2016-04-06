package coza.opencollab.synthesis.push;

import java.sql.Timestamp;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import coza.opencollab.synthesis.push.dbo.Device;
import coza.opencollab.synthesis.push.dbo.Preference;
import coza.opencollab.synthesis.push.dbo.Push;
import coza.opencollab.synthesis.push.service.DeviceService;
import coza.opencollab.synthesis.push.service.PreferenceService;
import coza.opencollab.synthesis.push.service.PushService;
import coza.opencollab.synthesis.push.so.DeviceResponse;
import coza.opencollab.synthesis.push.so.MapResponse;
import coza.opencollab.synthesis.push.so.PreferenceRequest;
import coza.opencollab.synthesis.push.so.PreferenceResponse;
import coza.opencollab.synthesis.push.so.PushRequest;
import coza.opencollab.synthesis.push.so.PushResponse;
import coza.opencollab.synthesis.push.so.RegisterDeviceRequest;
import coza.opencollab.synthesis.service.BaseController;
import coza.opencollab.synthesis.service.auth.so.Login;
import net.sf.json.JSONObject;

/**
 * Rest service for the devices.
 * This rest service is only used by the devices connecting to synthesis, not the front end administration tool
 *
 * @author charl
 */
@Controller
public class PushClientRestService extends BaseController{

	/** The device service. */
	@Autowired
	@Qualifier("deviceService")
	private DeviceService deviceService;

	/** The preference service. */
	@Autowired
	@Qualifier("preferenceService")
	private PreferenceService preferenceService;
	
	/** The push service. */
	@Autowired
	@Qualifier("pushService")
	private PushService pushService;
	
	/** The Constant LOG. */
	private static final Logger LOG = LoggerFactory.getLogger(PushClientRestService.class);

	/**
	 * Request when a device registers itself for push notifications.
	 *
	 * @param deviceRequest the device request
	 * @return the device response
	 */
	@RequestMapping(value="service-push/register", method=RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody DeviceResponse registerDevice(@RequestBody RegisterDeviceRequest deviceRequest){

		//must be able to log in
		Login login = testLoginWithToken(deviceRequest.getUsername(), deviceRequest.getAuthToken());
		if (!login.isSuccessful()) {
			return new DeviceResponse(login);
		}

		// Get the device object from the request object
		Device requestDevice = deviceRequest.getDevice();

		// First check if the device does not already exist
		Device originalDevice = deviceService.findDeviceByUUID(requestDevice.getUuid());
		if(originalDevice != null){
			requestDevice.setId(originalDevice.getId());
			requestDevice.setVersionNumber(originalDevice.getVersionNumber());
		}
		requestDevice.setUsername(deviceRequest.getUsername());
		requestDevice.setPostedTimestamp(new Timestamp(System.currentTimeMillis()));

		requestDevice = deviceService.saveDevice(requestDevice);
		
		// Reply back with the new/updated device
		return new DeviceResponse(requestDevice);
	}
	
	/**
	 * Request when a device registers itself for push notifications.
	 *
	 * @param request the request
	 * @return the push
	 */
	@RequestMapping(value="service-push/getPush", method=RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody PushResponse getPush(@RequestBody PushRequest request){
		
		//must be able to log in
		Login login = testLoginWithToken(request.getUsername(), request.getAuthToken());
		if (!login.isSuccessful()) {
			return new PushResponse(login);
		}
		
		Push p = pushService.findPushById(request.getPushId());
		return new PushResponse(p);
	}
	
	
	/**
	 * Request when a device registers itself for push notifications.
	 *
	 * @param uuid the uuid
	 * @return the preferences
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value="service-push/preferences/{uuid}", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody MapResponse getPreferences(@PathVariable String uuid){
		MapResponse response = new MapResponse<>();
		HashMap rootMap = new HashMap<>();
		HashMap<String, Map> modulesMap = new HashMap<>();
		
		// Find the device
		Device device = deviceService.findDeviceByUUID(uuid);
		List<Preference> preferences;
		if(device == null){
			preferences = Collections.emptyList();
		}else{
			preferences = preferenceService.findPreferences(device.getId());
		}
		for(Preference preference : preferences){
			String sender = preference.getSender();
			if(sender.startsWith("tool|")){
				String[] parts = sender.split("\\|");
				// Expected format tool|moduleId|toolId
				if(parts.length < 3){
					LOG.warn("Got a tool sender, but didn't have enough parts : \"" + sender + "\"");
					continue;
				}
				String module = parts[1], tool = parts[2];
				Map moduleMap;
				if(modulesMap.containsKey(module)){
					moduleMap = modulesMap.get(module);
				}
				else{
					moduleMap = new HashMap<String, Map>();
					modulesMap.put(module, moduleMap);
				}
				moduleMap.put(tool, preference.isEnabled());
				
			}
		}
		rootMap.put("toolPreferences", modulesMap);
		response.setMap(rootMap);
		return response;
	}
	
	private Device getDevice(String uuid, String username){
		Device device = deviceService.findDeviceByUUID(uuid);
		/* If the device does not exist yet, we need to create it with the info we have
		 * This can happen if the user registers for a module, before the registration to the 
		 * cloud service has returned a token
		 */ 
		if(device == null){
			device = new Device();
			device.setUuid(uuid);
			device.setUsername(username);
			device.setPostedTimestamp(new Timestamp(System.currentTimeMillis()));
			device = deviceService.saveDevice(device);
		}
		return device;
	}
	
	/**
	 * Request to get the preferences for a device
	 *
	 * @param uuid the uuid
	 * @param json the json
	 * @return the map response
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value="service-push/preferences/{uuid}", method=RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody MapResponse savePreferences(@PathVariable String uuid, @RequestBody String json){
		
		JSONObject root = JSONObject.fromObject(json);
		
		//must be able to log in
		Login login = testLoginWithToken(root.getString("username"), root.getString("authToken"));
		if (!login.isSuccessful()) {
			return new MapResponse(login);
		}
		
		
		Device device = getDevice(uuid, root.getString("username"));
		
		// Remove all previous preferences for the device, and persist the new ones
		preferenceService.removeDevicePreferences(device.getId());
		
		// Now look at the object that we received, we need to get the preferences for each tool
		JSONObject toolPreferences = root.getJSONObject("toolPreferences");
		Iterator<String> moduleKeys = toolPreferences.keys();
		while(moduleKeys.hasNext()){
			String moduleId = moduleKeys.next();
			JSONObject modulePreferences = toolPreferences.getJSONObject(moduleId);
			
			Iterator<String> toolKeys = modulePreferences.keys();
			while(toolKeys.hasNext()){
				String toolId = toolKeys.next();
				boolean enabled = modulePreferences.getBoolean(toolId);
				String senderId = "tool|" + moduleId + "|" + toolId;
				
				// If such a preference does not exist, we will create it
				Preference preference = new Preference();
				preference.setDevice(device);
				preference.setSender(senderId);
				preference.setEnabled(enabled);
				preference.setPostedTimestamp(new Timestamp(System.currentTimeMillis()));
				preference = preferenceService.savePreference(preference);
			}
		}
		
		return getPreferences(uuid);
	}
	
	/**
	 * Request to delete the preferences for a device
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value="service-push/preferences/{uuid}", method=RequestMethod.DELETE, produces=MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody MapResponse deletePreferences(@PathVariable String uuid, @RequestBody String json){
		
		JSONObject root = JSONObject.fromObject(json);
		
		//must be able to log in
		Login login = testLoginWithToken(root.getString("username"), root.getString("authToken"));
		if (!login.isSuccessful()) {
			return new MapResponse(login);
		}
		
		
		Device device = getDevice(uuid, root.getString("username"));
				
		// Now look at the object that we received, we need to get the preferences for each tool
		JSONObject toolPreferences = root.getJSONObject("toolPreferences");
		Iterator<String> moduleKeys = toolPreferences.keys();
		while(moduleKeys.hasNext()){
			String moduleId = moduleKeys.next();
			JSONObject modulePreferences = toolPreferences.getJSONObject(moduleId);
			
			Iterator<String> toolKeys = modulePreferences.keys();
			while(toolKeys.hasNext()){
				String toolId = toolKeys.next();
				boolean enabled = modulePreferences.getBoolean(toolId);
				String senderId = "tool|" + moduleId + "|" + toolId;
				Preference preference = preferenceService.findPreference(device.getId(), senderId);
				preferenceService.removePreference(preference.getId());
			}
		}
		
		return getPreferences(uuid);
	}

	/**
	 * Request when a device registers itself for push notifications.
	 *
	 * @param preferenceRequest the preference request
	 * @return the preference response
	 */
	@RequestMapping(value="service-push/preference", method=RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody PreferenceResponse savePreference(@RequestBody PreferenceRequest preferenceRequest){

		//must be able to log in
		Login login = testLoginWithToken(preferenceRequest.getUsername(), preferenceRequest.getAuthToken());
		if (!login.isSuccessful()) {
			return new PreferenceResponse(login);
		}

		// Find the device object we are working with from the device UUID
		Device device = getDevice(preferenceRequest.getUuid(), preferenceRequest.getUsername());
		
		// Find the current preference for the device and the sender
		Preference preference = preferenceService.findPreference(device.getId(), preferenceRequest.getSender());

		// If such a preference does not exist, we will create it
		if(preference == null){
			preference = new Preference();
			preference.setDevice(device);
			preference.setSender(preferenceRequest.getSender());
		}
		preference.setEnabled(preferenceRequest.isEnabled());
		preference.setPostedTimestamp(new Timestamp(System.currentTimeMillis()));
		preference = preferenceService.savePreference(preference);

		// Reply back with the new/updated preference
		return new PreferenceResponse(preference);
	}
	
	
}
