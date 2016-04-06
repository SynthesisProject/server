package coza.opencollab.synthesis.moodle.client.dao;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author OpenCollab
 */
public class Discussion {
	private int id;
	private int course;
	private int forum;
	private String name;
	private int userid;
	private int groupid;
	private int assessed;
	private float timemodified;
	private int usermodified;
	private float timestart;
	private int timeend;
	private int firstpost;
	private String firstuserfullname;
	private String firstuserimagealt;
	private int firstuserpicture;
	private String firstuseremail;
	private String subject;
	private int numreplies;
	private String numunread;	
	private int lastpost;
	private int lastuserid;
	private String lastuserfullname;
	private String lastuserimagealt;
	private String lastuserpicture;
	private String lastuseremail;
    private List<PostWrapper> posts = new ArrayList<PostWrapper>();

	public Discussion() {}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getCourse() {
		return course;
	}

	public void setCourse(int course) {
		this.course = course;
	}

	public int getForum() {
		return forum;
	}

	public void setForum(int forum) {
		this.forum = forum;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getUserid() {
		return userid;
	}

	public void setUserid(int userid) {
		this.userid = userid;
	}

	public int getGroupid() {
		return groupid;
	}

	public void setGroupid(int groupid) {
		this.groupid = groupid;
	}

	public int getAssessed() {
		return assessed;
	}

	public void setAssessed(int assessed) {
		this.assessed = assessed;
	}

	public float getTimemodified() {
		return timemodified;
	}

	public void setTimemodified(float timemodified) {
		this.timemodified = timemodified;
	}

	public int getUsermodified() {
		return usermodified;
	}

	public void setUsermodified(int usermodified) {
		this.usermodified = usermodified;
	}

	public float getTimestart() {
		return timestart;
	}

	public void setTimestart(int timestart) {
		this.timestart = timestart;
	}

	public int getTimeend() {
		return timeend;
	}

	public void setTimeend(int timeend) {
		this.timeend = timeend;
	}

	public int getFirstpost() {
		return firstpost;
	}

	public void setFirstpost(int firstpost) {
		this.firstpost = firstpost;
	}

	public String getFirstuserfullname() {
		return firstuserfullname;
	}

	public void setFirstuserfullname(String firstuserfullname) {
		this.firstuserfullname = firstuserfullname;
	}

	public String getFirstuserimagealt() {
		return firstuserimagealt;
	}

	public void setFirstuserimagealt(String firstuserimagealt) {
		this.firstuserimagealt = firstuserimagealt;
	}

	public int getFirstuserpicture() {
		return firstuserpicture;
	}

	public void setFirstuserpicture(int firstuserpicture) {
		this.firstuserpicture = firstuserpicture;
	}

	public String getFirstuseremail() {
		return firstuseremail;
	}

	public void setFirstuseremail(String firstuseremail) {
		this.firstuseremail = firstuseremail;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public int getNumreplies() {
		return numreplies;
	}

	public void setNumreplies(int numreplies) {
		this.numreplies = numreplies;
	}

	public String getNumunread() {
		return numunread;
	}

	public void setNumunread(String numunread) {
		this.numunread = numunread;
	}

	public int getLastpost() {
		return lastpost;
	}

	public void setLastpost(int lastpost) {
		this.lastpost = lastpost;
	}

	public int getLastuserid() {
		return lastuserid;
	}

	public void setLastuserid(int lastuserid) {
		this.lastuserid = lastuserid;
	}

	public String getLastuserfullname() {
		return lastuserfullname;
	}

	public void setLastuserfullname(String lastuserfullname) {
		this.lastuserfullname = lastuserfullname;
	}

	public String getLastuserimagealt() {
		return lastuserimagealt;
	}

	public void setLastuserimagealt(String lastuserimagealt) {
		this.lastuserimagealt = lastuserimagealt;
	}

	public String getLastuserpicture() {
		return lastuserpicture;
	}

	public void setLastuserpicture(String lastuserpicture) {
		this.lastuserpicture = lastuserpicture;
	}

	public String getLastuseremail() {
		return lastuseremail;
	}

	public void setLastuseremail(String lastuseremail) {
		this.lastuseremail = lastuseremail;
	}

        public List<PostWrapper> getPosts() {
            return posts;
        }

        public void setPosts(List<PostWrapper> posts) {
            this.posts = posts;
        }

        
        
	@Override
	public String toString() {
		return "Discussion [id=" + id + ", course=" + course + ", forum="
				+ forum + ", name=" + name + ", userid=" + userid
				+ ", groupid=" + groupid + ", assessed=" + assessed
				+ ", timemodified=" + timemodified + ", usermodified="
				+ usermodified + ", timestart=" + timestart + ", timeend="
				+ timeend + ", firstpost=" + firstpost + ", firstuserfullname="
				+ firstuserfullname + ", firstuserimagealt="
				+ firstuserimagealt + ", firstuserpicture=" + firstuserpicture
				+ ", firstuseremail=" + firstuseremail + ", subject=" + subject
				+ ", numreplies=" + numreplies + ", numunread=" + numunread
				+ ", lastpost=" + lastpost + ", lastuserid=" + lastuserid
				+ ", lastuserfullname=" + lastuserfullname
				+ ", lastuserimagealt=" + lastuserimagealt
				+ ", lastuserpicture=" + lastuserpicture + ", lastuseremail="
				+ lastuseremail + "]";
	}
	
}
