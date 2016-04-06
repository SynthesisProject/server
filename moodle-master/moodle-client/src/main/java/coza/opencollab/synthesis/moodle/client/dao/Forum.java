package coza.opencollab.synthesis.moodle.client.dao;

import java.util.List;
/**
 * 
 * @author OpenCollab
 */
public class Forum {
	
	public Forum() {
		super();
	}
	private int id;
    private int course;
    private String type;
    private String name;
    private String intro;
    private int introformat;
    private int assessed;
    private int assesstimestart;
    private int assesstimefinish;
    private int scale;
    private int maxbytes;
    private int maxattachments;
    private int forcesubscribe;
    private int trackingtype;
    private int rsstype;
    private int rssarticles;
    private long timemodified;
    private int warnafter;
    private int blockafter;
    private int blockperiod;
    private int completiondiscussions;
    private int completionreplies;
    private int completionposts;
    private int cmid;
    private List<Discussion> discussions;
    
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
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getIntro() {
		return intro;
	}
	public void setIntro(String intro) {
		this.intro = intro;
	}
	public int getIntroformat() {
		return introformat;
	}
	public void setIntroformat(int introformat) {
		this.introformat = introformat;
	}
	public int getAssessed() {
		return assessed;
	}
	public void setAssessed(int assessed) {
		this.assessed = assessed;
	}
	public int getAssesstimestart() {
		return assesstimestart;
	}
	public void setAssesstimestart(int assesstimestart) {
		this.assesstimestart = assesstimestart;
	}
	public int getAssesstimefinish() {
		return assesstimefinish;
	}
	public void setAssesstimefinish(int assesstimefinish) {
		this.assesstimefinish = assesstimefinish;
	}
	public int getScale() {
		return scale;
	}
	public void setScale(int scale) {
		this.scale = scale;
	}
	public int getMaxbytes() {
		return maxbytes;
	}
	public void setMaxbytes(int maxbytes) {
		this.maxbytes = maxbytes;
	}
	public int getMaxattachments() {
		return maxattachments;
	}
	public void setMaxattachments(int maxattachments) {
		this.maxattachments = maxattachments;
	}
	public int getForcesubscribe() {
		return forcesubscribe;
	}
	public void setForcesubscribe(int forcesubscribe) {
		this.forcesubscribe = forcesubscribe;
	}
	public int getTrackingtype() {
		return trackingtype;
	}
	public void setTrackingtype(int trackingtype) {
		this.trackingtype = trackingtype;
	}
	public int getRsstype() {
		return rsstype;
	}
	public void setRsstype(int rsstype) {
		this.rsstype = rsstype;
	}
	public int getRssarticles() {
		return rssarticles;
	}
	public void setRssarticles(int rssarticles) {
		this.rssarticles = rssarticles;
	}
	public long getTimemodified() {
		return timemodified;
	}
	public void setTimemodified(long timemodified) {
		this.timemodified = timemodified;
	}
	public int getWarnafter() {
		return warnafter;
	}
	public void setWarnafter(int warnafter) {
		this.warnafter = warnafter;
	}
	public int getBlockafter() {
		return blockafter;
	}
	public void setBlockafter(int blockafter) {
		this.blockafter = blockafter;
	}
	public int getBlockperiod() {
		return blockperiod;
	}
	public void setBlockperiod(int blockperiod) {
		this.blockperiod = blockperiod;
	}
	public int getCompletiondiscussions() {
		return completiondiscussions;
	}
	public void setCompletiondiscussions(int completiondiscussions) {
		this.completiondiscussions = completiondiscussions;
	}
	public int getCompletionreplies() {
		return completionreplies;
	}
	public void setCompletionreplies(int completionreplies) {
		this.completionreplies = completionreplies;
	}
	public int getCompletionposts() {
		return completionposts;
	}
	public void setCompletionposts(int completionposts) {
		this.completionposts = completionposts;
	}
	public int getCmid() {
		return cmid;
	}
	public void setCmid(int cmid) {
		this.cmid = cmid;
	}
	
	public List<Discussion> getDiscussions() {
		return discussions;
	}
	public void setDiscussions(List<Discussion> discussions) {
		this.discussions = discussions;
	}
	@Override
	public String toString() {
		return "Forum [id=" + id + ", course=" + course + ", type=" + type
				+ ", name=" + name + ", intro=" + intro + ", introformat="
				+ introformat + ", assessed=" + assessed + ", assesstimestart="
				+ assesstimestart + ", assesstimefinish=" + assesstimefinish
				+ ", scale=" + scale + ", maxbytes=" + maxbytes
				+ ", maxattachments=" + maxattachments + ", forcesubscribe="
				+ forcesubscribe + ", trackingtype=" + trackingtype
				+ ", rsstype=" + rsstype + ", rssarticles=" + rssarticles
				+ ", timemodified=" + timemodified + ", warnafter=" + warnafter
				+ ", blockafter=" + blockafter + ", blockperiod=" + blockperiod
				+ ", completiondiscussions=" + completiondiscussions
				+ ", completionreplies=" + completionreplies
				+ ", completionposts=" + completionposts + ", cmid=" + cmid
				+ "]";
	}
}
