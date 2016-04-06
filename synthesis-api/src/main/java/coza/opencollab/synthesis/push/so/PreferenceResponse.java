package coza.opencollab.synthesis.push.so;

import coza.opencollab.synthesis.push.dbo.Preference;
import coza.opencollab.synthesis.service.api.ServiceObject;

/**
 * Created by charl on 2014/07/10.
 */
public class PreferenceResponse extends ServiceObject{

	private static final long serialVersionUID = 7479646313271271643L;
	
	private Preference preference;

	public PreferenceResponse(){};
	
	public PreferenceResponse(ServiceObject so){
		super(so);
	};

	public PreferenceResponse (Preference p){
		this.preference = p;
	}

	public Preference getPreference() {
		return preference;
	}

	public void setPreference(Preference preference) {
		this.preference = preference;
	}
}
