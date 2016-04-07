package coza.opencollab.synthesis.moodle.client.dao;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;

@Generated("org.jsonschema2pojo")
public class UserSiteInfo {

    @Expose
    private String sitename;
    @Expose
    private String username;
    @Expose
    private String firstname;
    @Expose
    private String lastname;
    @Expose
    private String fullname;
    @Expose
    private String lang;
    @Expose
    private Integer userid;
    @Expose
    private String siteurl;
    @Expose
    private String userpictureurl;
    @Expose
    private List<WebserviceFunction> webserviceFunctions = new ArrayList<WebserviceFunction>();
    @Expose
    private Integer downloadfiles;
    @Expose
    private Integer uploadfiles;
    @Expose
    private String release;
    @Expose
    private String version;
    @Expose
    private String mobilecssurl;

    /**
     *
     * @return The sitename
     */
    public String getSitename() {
        return sitename;
    }

    /**
     *
     * @param sitename The sitename
     */
    public void setSitename(String sitename) {
        this.sitename = sitename;
    }

    /**
     *
     * @return The username
     */
    public String getUsername() {
        return username;
    }

    /**
     *
     * @param username The username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     *
     * @return The firstname
     */
    public String getFirstname() {
        return firstname;
    }

    /**
     *
     * @param firstname The firstname
     */
    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    /**
     *
     * @return The lastname
     */
    public String getLastname() {
        return lastname;
    }

    /**
     *
     * @param lastname The lastname
     */
    public void setLastname(String lastname) {
        this.lastname = lastname;
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
     * @return The lang
     */
    public String getLang() {
        return lang;
    }

    /**
     *
     * @param lang The lang
     */
    public void setLang(String lang) {
        this.lang = lang;
    }

    /**
     *
     * @return The userid
     */
    public Integer getUserid() {
        return userid;
    }

    /**
     *
     * @param userid The userid
     */
    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    /**
     *
     * @return The siteurl
     */
    public String getSiteurl() {
        return siteurl;
    }

    /**
     *
     * @param siteurl The siteurl
     */
    public void setSiteurl(String siteurl) {
        this.siteurl = siteurl;
    }

    /**
     *
     * @return The userpictureurl
     */
    public String getUserpictureurl() {
        return userpictureurl;
    }

    /**
     *
     * @param userpictureurl The userpictureurl
     */
    public void setUserpictureurl(String userpictureurl) {
        this.userpictureurl = userpictureurl;
    }

    /**
     *
     * @return The functions
     */
    public List<WebserviceFunction> getFunctions() {
        return webserviceFunctions;
    }

    /**
     *
     * @param webserviceFunctions The functions
     */
    public void setFunctions(List<WebserviceFunction> webserviceFunctions) {
        this.webserviceFunctions = webserviceFunctions;
    }

    /**
     *
     * @return The download files
     */
    public Integer getDownloadfiles() {
        return downloadfiles;
    }

    /**
     *
     * @param downloadfiles The downloadfiles
     */
    public void setDownloadfiles(Integer downloadfiles) {
        this.downloadfiles = downloadfiles;
    }

    /**
     *
     * @return The uploadfiles
     */
    public Integer getUploadfiles() {
        return uploadfiles;
    }

    /**
     *
     * @param uploadfiles The uploadfiles
     */
    public void setUploadfiles(Integer uploadfiles) {
        this.uploadfiles = uploadfiles;
    }

    /**
     *
     * @return The release
     */
    public String getRelease() {
        return release;
    }

    /**
     *
     * @param release The release
     */
    public void setRelease(String release) {
        this.release = release;
    }

    /**
     *
     * @return The version
     */
    public String getVersion() {
        return version;
    }

    /**
     *
     * @param version The version
     */
    public void setVersion(String version) {
        this.version = version;
    }

    /**
     *
     * @return The mobilecssurl
     */
    public String getMobilecssurl() {
        return mobilecssurl;
    }

    /**
     *
     * @param mobilecssurl The mobilecssurl
     */
    public void setMobilecssurl(String mobilecssurl) {
        this.mobilecssurl = mobilecssurl;
    }

    @Override
    public String toString() {
        return "UserSiteInfo{ sitename=" + sitename + ", username=" + username + ", firstname=" + firstname + ", lastname=" + lastname + ", fullname=" + fullname + ", lang=" + lang + ", userid=" + userid + ", siteurl=" + siteurl + ", userpictureurl=" + userpictureurl + ", webserviceFunctions=" + webserviceFunctions + ", downloadfiles=" + downloadfiles + ", uploadfiles=" + uploadfiles + ", release=" + release + ", version=" + version + ", mobilecssurl=" + mobilecssurl + '}';
    }
}
