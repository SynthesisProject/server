package coza.opencollab.synthesis.moodle.client.dao;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;

@Generated("org.jsonschema2pojo")
public class CourseBlock {

    @Expose
    private Integer id;
    @Expose
    private String name;
    @Expose
    private Integer visible;
    @Expose
    private String summary;
    @Expose
    private Integer summaryformat;
    @Expose
    private List<Object> modules = new ArrayList<Object>();

    /**
     *
     * @return The id
     */
    public Integer getId() {
        return id;
    }

    /**
     *
     * @param id The id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     *
     * @return The name
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @param name The name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @return The visible
     */
    public Integer getVisible() {
        return visible;
    }

    /**
     *
     * @param visible The visible
     */
    public void setVisible(Integer visible) {
        this.visible = visible;
    }

    /**
     *
     * @return The summary
     */
    public String getSummary() {
        return summary;
    }

    /**
     *
     * @param summary The summary
     */
    public void setSummary(String summary) {
        this.summary = summary;
    }

    /**
     *
     * @return The summaryformat
     */
    public Integer getSummaryformat() {
        return summaryformat;
    }

    /**
     *
     * @param summaryformat The summaryformat
     */
    public void setSummaryformat(Integer summaryformat) {
        this.summaryformat = summaryformat;
    }

    /**
     *
     * @return The modules
     */
    public List<Object> getModules() {
        return modules;
    }

    /**
     *
     * @param modules The modules
     */
    public void setModules(List<Object> modules) {
        this.modules = modules;
    }

    @Override
    public String toString() {
        return "CourseBlock{id=" + id + ", name=" + name + ", visible=" + visible + ", summary=" + summary + ", summaryformat=" + summaryformat + ", modules=" + modules + '}';
    }
}
