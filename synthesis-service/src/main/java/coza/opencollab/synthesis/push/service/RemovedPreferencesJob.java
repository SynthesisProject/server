package coza.opencollab.synthesis.push.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;

import coza.opencollab.synthesis.push.dbo.Device;
import coza.opencollab.synthesis.push.dbo.Preference;
import coza.opencollab.synthesis.service.api.lms.LMSClient;
import coza.opencollab.synthesis.shared.Module;

/**
 * This is a job that will periodically check the LMS if the user is still linked
 * to the sites for which the user has preferences.
 * If the user is no longer a member of the site the preferences for the site 
 * will be removed
 * @author Charl Thiem
 *
 */
public class RemovedPreferencesJob {

	
	@Value("${push.preferenceDeleteJob.enabled}")
	private boolean enabled;
	
	 /**
     * The client
     */
    @Autowired
    private LMSClient lmsClient;
    
    @Autowired
    @Qualifier("deviceService")
    private DeviceService deviceService;
    
    @Autowired
    @Qualifier("preferenceService")
    private PreferenceService preferenceService;
    

	/** The Constant LOG. */
	private static final Logger LOG = LoggerFactory.getLogger(RemovedPreferencesJob.class);
    
    /**
     * Returns true if the user is linked to the specified site ID
     * @param siteId Id of the site we want to check for
     * @param modules List of modules the user is linked too
     * @return True if the user is linked to the site.
     */
    private boolean hasSite(String siteId, List<Module> modules){
    	for(Module module : modules){
    		if(module.getId().equals(siteId)){
    			return true;
    		}
    	}
    	
    	return false;
    }
	
	public void removeUnlinkedPreferences(){
		
		if(!enabled){
			LOG.info("This job is disabled, ignoring the trigger to execute");
			return;
		}
		
		LOG.debug("Checking for preferences to delete");
		// Get all the devices
		List<Device> devices = deviceService.findAllDevices();
		LOG.debug("Checking for preferences for " + devices.size() + " devices.");
		for(Device device : devices){
				
			
			// Get the sites the user can access
			List<Module> modules = lmsClient.getSitesUserCanAccess(device.getUsername());
			List<Preference> preferences = preferenceService.findPreferences(device.getId());
			
			// Get the preferences the user has for senders that contains
			for(Preference preference : preferences){
				// Find only the tool sender preferences
				String senderId = preference.getSender();
				if(senderId.startsWith("tool|")){
					String[] parts = senderId.split("\\|");
					// Expected format tool|moduleId|toolId
					if(parts.length < 3){
						LOG.warn("Got a tool sender, but didn't have enough parts : \"" + senderId + "\"");
						continue;
					}
					String module = parts[1];
					
					// If the user does not have access to the site, delete the preference
					if(!hasSite(module, modules)){
						LOG.warn("Deleting preference for user : \"" + device.getUsername() + "\", device: \"" + device.getId() + "\", sender: \"" + senderId + "\"");
						preferenceService.removePreference(preference.getId());
					}
				}
			}
		
		}
	}
}
