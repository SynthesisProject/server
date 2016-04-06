package coza.opencollab.synthesis.moodle.client.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Generated;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({
    "id",
    "name",
    "visible",
    "summary",
    "summaryformat",
    "modules"
})
/**
 * @author OpenCollab
 */
public class CourseActivities {

    @JsonProperty("id")
    private int id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("visible")
    private int visible;
    @JsonProperty("summary")
    private String summary;
    @JsonProperty("summaryformat")
    private int summaryformat;
    @JsonProperty("modules")
    private List<MoodleModule> modules = new ArrayList<MoodleModule>();
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     *
     * @return The id
     */
    @JsonProperty("id")
    public int getId() {
        return id;
    }

    /**
     *
     * @param id The id
     */
    @JsonProperty("id")
    public void setId(int id) {
        this.id = id;
    }

    /**
     *
     * @return The name
     */
    @JsonProperty("name")
    public String getName() {
        return name;
    }

    /**
     *
     * @param name The name
     */
    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @return The visible
     */
    @JsonProperty("visible")
    public int getVisible() {
        return visible;
    }

    /**
     *
     * @param visible The visible
     */
    @JsonProperty("visible")
    public void setVisible(int visible) {
        this.visible = visible;
    }

    /**
     *
     * @return The summary
     */
    @JsonProperty("summary")
    public String getSummary() {
        return summary;
    }

    /**
     *
     * @param summary The summary
     */
    @JsonProperty("summary")
    public void setSummary(String summary) {
        this.summary = summary;
    }

    /**
     *
     * @return The summaryformat
     */
    @JsonProperty("summaryformat")
    public int getSummaryformat() {
        return summaryformat;
    }

    /**
     *
     * @param summaryformat The summaryformat
     */
    @JsonProperty("summaryformat")
    public void setSummaryformat(int summaryformat) {
        this.summaryformat = summaryformat;
    }

    /**
     *
     * @return The modules
     */
    @JsonProperty("modules")
    public List<MoodleModule> getModules() {
        return modules;
    }

    /**
     *
     * @param modules The modules
     */
    @JsonProperty("modules")
    public void setModules(List<MoodleModule> modules) {
        this.modules = modules;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    @Override
    public String toString() {
        return "CourseActivity{" + "id=" + id + ", name=" + name + ", visible=" + visible + ", summary=" + summary + ", summaryformat=" + summaryformat + ", modules=" + modules + ", additionalProperties=" + additionalProperties + '}';
    }
}
