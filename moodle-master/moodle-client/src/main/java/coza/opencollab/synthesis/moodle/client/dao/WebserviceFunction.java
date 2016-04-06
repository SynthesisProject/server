package coza.opencollab.synthesis.moodle.client.dao;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;

@Generated("org.jsonschema2pojo")
public class WebserviceFunction {

@Expose
private String name;
@Expose
private String version;

/**
* 
* @return
* The name
*/
public String getName() {
return name;
}

/**
* 
* @param name
* The name
*/
public void setName(String name) {
this.name = name;
}

/**
* 
* @return
* The version
*/
public String getVersion() {
return version;
}

/**
* 
* @param version
* The version
*/
public void setVersion(String version) {
this.version = version;
}

}