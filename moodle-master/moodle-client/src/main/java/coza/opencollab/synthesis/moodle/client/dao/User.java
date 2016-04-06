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
    "username",
    "firstname",
    "lastname",
    "fullname",
    "email",
    "department",
    "idnumber",
    "firstaccess",
    "lastaccess",
    "auth",
    "confirmed",
    "lang",
    "theme",
    "timezone",
    "mailformat",
    "description",
    "descriptionformat",
    "profileimageurlsmall",
    "profileimageurl",
    "preferences"
})
public class User {

    @JsonProperty("id")
    private Integer id;
    @JsonProperty("username")
    private String username;
    @JsonProperty("firstname")
    private String firstname;
    @JsonProperty("lastname")
    private String lastname;
    @JsonProperty("fullname")
    private String fullname;
    @JsonProperty("email")
    private String email;
    @JsonProperty("department")
    private String department;
    @JsonProperty("idnumber")
    private String idnumber;
    @JsonProperty("firstaccess")
    private Integer firstaccess;
    @JsonProperty("lastaccess")
    private Integer lastaccess;
    @JsonProperty("auth")
    private String auth;
    @JsonProperty("confirmed")
    private Integer confirmed;
    @JsonProperty("lang")
    private String lang;
    @JsonProperty("theme")
    private String theme;
    @JsonProperty("timezone")
    private String timezone;
    @JsonProperty("mailformat")
    private Integer mailformat;
    @JsonProperty("description")
    private String description;
    @JsonProperty("descriptionformat")
    private Integer descriptionformat;
    @JsonProperty("profileimageurlsmall")
    private String profileimageurlsmall;
    @JsonProperty("profileimageurl")
    private String profileimageurl;
    @JsonProperty("preferences")
    private List<Preference> preferences = new ArrayList<Preference>();
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     *
     * @return The id
     */
    @JsonProperty("id")
    public Integer getId() {
        return id;
    }

    /**
     *
     * @param id The id
     */
    @JsonProperty("id")
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     *
     * @return The username
     */
    @JsonProperty("username")
    public String getUsername() {
        return username;
    }

    /**
     *
     * @param username The username
     */
    @JsonProperty("username")
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     *
     * @return The firstname
     */
    @JsonProperty("firstname")
    public String getFirstname() {
        return firstname;
    }

    /**
     *
     * @param firstname The firstname
     */
    @JsonProperty("firstname")
    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    /**
     *
     * @return The lastname
     */
    @JsonProperty("lastname")
    public String getLastname() {
        return lastname;
    }

    /**
     *
     * @param lastname The lastname
     */
    @JsonProperty("lastname")
    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    /**
     *
     * @return The fullname
     */
    @JsonProperty("fullname")
    public String getFullname() {
        return fullname;
    }

    /**
     *
     * @param fullname The fullname
     */
    @JsonProperty("fullname")
    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    /**
     *
     * @return The email
     */
    @JsonProperty("email")
    public String getEmail() {
        return email;
    }

    /**
     *
     * @param email The email
     */
    @JsonProperty("email")
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     *
     * @return The department
     */
    @JsonProperty("department")
    public String getDepartment() {
        return department;
    }

    /**
     *
     * @param department The department
     */
    @JsonProperty("department")
    public void setDepartment(String department) {
        this.department = department;
    }

    /**
     *
     * @return The idnumber
     */
    @JsonProperty("idnumber")
    public String getIdnumber() {
        return idnumber;
    }

    /**
     *
     * @param idnumber The idnumber
     */
    @JsonProperty("idnumber")
    public void setIdnumber(String idnumber) {
        this.idnumber = idnumber;
    }

    /**
     *
     * @return The firstaccess
     */
    @JsonProperty("firstaccess")
    public Integer getFirstaccess() {
        return firstaccess;
    }

    /**
     *
     * @param firstaccess The firstaccess
     */
    @JsonProperty("firstaccess")
    public void setFirstaccess(Integer firstaccess) {
        this.firstaccess = firstaccess;
    }

    /**
     *
     * @return The lastaccess
     */
    @JsonProperty("lastaccess")
    public Integer getLastaccess() {
        return lastaccess;
    }

    /**
     *
     * @param lastaccess The lastaccess
     */
    @JsonProperty("lastaccess")
    public void setLastaccess(Integer lastaccess) {
        this.lastaccess = lastaccess;
    }

    /**
     *
     * @return The auth
     */
    @JsonProperty("auth")
    public String getAuth() {
        return auth;
    }

    /**
     *
     * @param auth The auth
     */
    @JsonProperty("auth")
    public void setAuth(String auth) {
        this.auth = auth;
    }

    /**
     *
     * @return The confirmed
     */
    @JsonProperty("confirmed")
    public Integer getConfirmed() {
        return confirmed;
    }

    /**
     *
     * @param confirmed The confirmed
     */
    @JsonProperty("confirmed")
    public void setConfirmed(Integer confirmed) {
        this.confirmed = confirmed;
    }

    /**
     *
     * @return The lang
     */
    @JsonProperty("lang")
    public String getLang() {
        return lang;
    }

    /**
     *
     * @param lang The lang
     */
    @JsonProperty("lang")
    public void setLang(String lang) {
        this.lang = lang;
    }

    /**
     *
     * @return The theme
     */
    @JsonProperty("theme")
    public String getTheme() {
        return theme;
    }

    /**
     *
     * @param theme The theme
     */
    @JsonProperty("theme")
    public void setTheme(String theme) {
        this.theme = theme;
    }

    /**
     *
     * @return The timezone
     */
    @JsonProperty("timezone")
    public String getTimezone() {
        return timezone;
    }

    /**
     *
     * @param timezone The timezone
     */
    @JsonProperty("timezone")
    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    /**
     *
     * @return The mailformat
     */
    @JsonProperty("mailformat")
    public Integer getMailformat() {
        return mailformat;
    }

    /**
     *
     * @param mailformat The mailformat
     */
    @JsonProperty("mailformat")
    public void setMailformat(Integer mailformat) {
        this.mailformat = mailformat;
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
     * @return The descriptionformat
     */
    @JsonProperty("descriptionformat")
    public Integer getDescriptionformat() {
        return descriptionformat;
    }

    /**
     *
     * @param descriptionformat The descriptionformat
     */
    @JsonProperty("descriptionformat")
    public void setDescriptionformat(Integer descriptionformat) {
        this.descriptionformat = descriptionformat;
    }

    /**
     *
     * @return The profileimageurlsmall
     */
    @JsonProperty("profileimageurlsmall")
    public String getProfileimageurlsmall() {
        return profileimageurlsmall;
    }

    /**
     *
     * @param profileimageurlsmall The profileimageurlsmall
     */
    @JsonProperty("profileimageurlsmall")
    public void setProfileimageurlsmall(String profileimageurlsmall) {
        this.profileimageurlsmall = profileimageurlsmall;
    }

    /**
     *
     * @return The profileimageurl
     */
    @JsonProperty("profileimageurl")
    public String getProfileimageurl() {
        return profileimageurl;
    }

    /**
     *
     * @param profileimageurl The profileimageurl
     */
    @JsonProperty("profileimageurl")
    public void setProfileimageurl(String profileimageurl) {
        this.profileimageurl = profileimageurl;
    }

    /**
     *
     * @return The preferences
     */
    @JsonProperty("preferences")
    public List<Preference> getPreferences() {
        return preferences;
    }

    /**
     *
     * @param preferences The preferences
     */
    @JsonProperty("preferences")
    public void setPreferences(List<Preference> preferences) {
        this.preferences = preferences;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
