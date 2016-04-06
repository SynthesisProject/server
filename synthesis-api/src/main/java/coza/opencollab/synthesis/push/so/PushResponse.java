package coza.opencollab.synthesis.push.so;

import coza.opencollab.synthesis.push.dbo.Push;
import coza.opencollab.synthesis.service.api.ServiceObject;

public class PushResponse extends ServiceObject{

	private Push push;

	public PushResponse(){
		
	}
	
	public PushResponse(ServiceObject so){
		super(so);
	}
	
	public PushResponse(Push push){
		this.push = push;
	}

	public Push getPush() {
		return push;
	}

	public void setPush(Push push) {
		this.push = push;
	}
}
