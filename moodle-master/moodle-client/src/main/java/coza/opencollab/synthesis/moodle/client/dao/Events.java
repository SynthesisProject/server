package coza.opencollab.synthesis.moodle.client.dao;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;

@Generated("org.jsonschema2pojo")
public class Events {

@Expose
private List<Event> events = new ArrayList<Event>();
@Expose
private List<Object> warnings = new ArrayList<Object>();

/**
* 
* @return
* The events
*/
public List<Event> getEvents() {
return events;
}

/**
* 
* @param events
* The events
*/
public void setEvents(List<Event> events) {
this.events = events;
}

/**
* 
* @return
* The warnings
*/
public List<Object> getWarnings() {
return warnings;
}

/**
* 
* @param warnings
* The warnings
*/
public void setWarnings(List<Object> warnings) {
this.warnings = warnings;
}

}