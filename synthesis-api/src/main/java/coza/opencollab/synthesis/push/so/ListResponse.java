package coza.opencollab.synthesis.push.so;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;

import coza.opencollab.synthesis.service.api.ServiceObject;

public class ListResponse<T> extends ServiceObject {

	@XmlElementWrapper(name="list")
	@XmlElement(name = "item")
	private List<T> data;

	public ListResponse(List<T> data){
		this.data = data;
	}

	public ListResponse(){
		this.data = new ArrayList<T>();
	}

	public List<T> getList(){
		return this.data;
	}

	public void setList(List<T> data){
		this.data = data;
	}
}
