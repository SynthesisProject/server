package coza.opencollab.synthesis.moodle.client.dao;

import java.util.HashMap;
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
    "url",
    "name",
    "description",
    "visible",
    "modicon",
    "modname",
    "modplural",
    "indent"
})
/**
 * 
 * @author OpenCollab
 */
public class MoodleModule {

    @JsonProperty("id")
    private int id;
    @JsonProperty("url")
    private String url;
    @JsonProperty("name")
    private String name;
    @JsonProperty("description")
    private String description;
    @JsonProperty("visible")
    private int visible;
    @JsonProperty("modicon")
    private String modicon;
    @JsonProperty("modname")
    private String modname;
    @JsonProperty("modplural")
    private String modplural;
    @JsonProperty("indent")
    private int indent;
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
     * @return The url
     */
    @JsonProperty("url")
    public String getUrl() {
        return url;
    }

    /**
     *
     * @param url The url
     */
    @JsonProperty("url")
    public void setUrl(String url) {
        this.url = url;
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
     * @return The description
     */
    @JsonProperty("description")
    public String getDescription() {
        return description;
    }

    /**
     *
     * @param description The description
     */
    @JsonProperty("description")
    public void setDescription(String description) {
        this.description = description;
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
     * @return The modicon
     */
    @JsonProperty("modicon")
    public String getModicon() {
        return modicon;
    }

    /**
     *
     * @param modicon The modicon
     */
    @JsonProperty("modicon")
    public void setModicon(String modicon) {
        this.modicon = modicon;
    }

    /**
     *
     * @return The modname
     */
    @JsonProperty("modname")
    public String getModname() {
        return modname;
    }

    /**
     *
     * @param modname The modname
     */
    @JsonProperty("modname")
    public void setModname(String modname) {
        this.modname = modname;
    }

    /**
     *
     * @return The modplural
     */
    @JsonProperty("modplural")
    public String getModplural() {
        return modplural;
    }

    /**
     *
     * @param modplural The modplural
     */
    @JsonProperty("modplural")
    public void setModplural(String modplural) {
        this.modplural = modplural;
    }

    /**
     *
     * @return The indent
     */
    @JsonProperty("indent")
    public int getIndent() {
        return indent;
    }

    /**
     *
     * @param indent The indent
     */
    @JsonProperty("indent")
    public void setIndent(int indent) {
        this.indent = indent;
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
        return "Module{" + "id=" + id + ", url=" + url + ", name=" + name + ", description=" + description + ", visible=" + visible + ", modicon=" + modicon + ", modname=" + modname + ", modplural=" + modplural + ", indent=" + indent + ", additionalProperties=" + additionalProperties + '}';
    }
}
