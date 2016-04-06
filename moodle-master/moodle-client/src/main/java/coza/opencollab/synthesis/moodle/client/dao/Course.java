package coza.opencollab.synthesis.moodle.client.dao;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;

@Generated("org.jsonschema2pojo")
public class Course {

    @Expose
    private Integer id;
    @Expose
    private String shortname;
    @Expose
    private String fullname;
    @Expose
    private Integer enrolledusercount;
    @Expose
    private String idnumber;
    @Expose
    private Integer visible;

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
     * @return The shortname
     */
    public String getShortname() {
        return shortname;
    }

    /**
     *
     * @param shortname The shortname
     */
    public void setShortname(String shortname) {
        this.shortname = shortname;
    }

    /**
     *
     * @return The fullname
     */
    public String getFullname() {
        return fullname;
    }

    /**
     *
     * @param fullname The fullname
     */
    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    /**
     *
     * @return The enrolledusercount
     */
    public Integer getEnrolledusercount() {
        return enrolledusercount;
    }

    /**
     *
     * @param enrolledusercount The enrolledusercount
     */
    public void setEnrolledusercount(Integer enrolledusercount) {
        this.enrolledusercount = enrolledusercount;
    }

    /**
     *
     * @return The idnumber
     */
    public String getIdnumber() {
        return idnumber;
    }

    /**
     *
     * @param idnumber The idnumber
     */
    public void setIdnumber(String idnumber) {
        this.idnumber = idnumber;
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

    @Override
    public String toString() {
        return "Course{" + "id=" + id + ", shortname=" + shortname + ", fullname=" + fullname + ", enrolledusercount=" + enrolledusercount + ", idnumber=" + idnumber + ", visible=" + visible + '}';
    }
    
}
