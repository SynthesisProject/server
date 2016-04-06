package coza.opencollab.synthesis.push.so;

import java.util.HashMap;
import java.util.Map;

import coza.opencollab.synthesis.service.api.ServiceObject;

/**
 */
public class MapResponse<T, E> extends ServiceObject {

	private HashMap<T, E> data;

	public MapResponse(ServiceObject so){
		super(so);
	}
	
	public MapResponse(Map<T, E> data){
		if(data instanceof HashMap){
			this.data = (HashMap)data;
		}
		else{
			this.data = new HashMap<T, E>(data);
		}
	}

	public MapResponse(){
		this.data = null;
	}

	public HashMap<T, E> getMap(){
		return this.data;
	}

	public void setMap(HashMap<T, E> data){
		this.data = data;
	}
}
