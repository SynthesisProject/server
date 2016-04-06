package coza.opencollab.synthesis.moodle.client.dao.file;

/**
 * Representation of a Moodle File 
 * @author OpenCollab
 *
 */
public class MoodleFile {
	
	private String contextid;
	private String component;
	private String filearea;
	private Integer itemid;
	private String filepath;
	private String filename;
	private boolean isdir;
	private String url;
	private int timeModified;
	
	public String getContextId() {
		return contextid;
	}
	public String getComponent() {
		return component;
	}
	public String getFilearea() {
		return filearea;
	}
	public Integer getItemid() {
		return itemid;
	}
	public String getFilepath() {
		return filepath;
	}
	public String getFilename() {
		return filename;
	}
	public boolean isIsdir() {
		return isdir;
	}
	public String getUrl() {
		return url;
	}
	public int getTimeModified() {
		return timeModified;
	}
	protected void setContextId(String contextId) {
		this.contextid = contextId;
	}
	protected void setComponent(String component) {
		this.component = component;
	}
	protected void setFilearea(String filearea) {
		this.filearea = filearea;
	}
	protected void setItemid(Integer itemid) {
		this.itemid = itemid;
	}
	protected void setFilepath(String filepath) {
		this.filepath = filepath;
	}
	protected void setFilename(String filename) {
		this.filename = filename;
	}
	protected void setIsdir(boolean isdir) {
		this.isdir = isdir;
	}
	protected void setUrl(String url) {
		this.url = url;
	}
	protected void setTimeModified(int timeModified) {
		this.timeModified = timeModified;
	}
	@Override
	public String toString() {
		return "MoodleFile [contextId=" + contextid + ", component="
				+ component + ", filearea=" + filearea + ", itemid=" + itemid
				+ ", filepath=" + filepath + ", filename=" + filename
				+ ", isdir=" + isdir + ", url=" + url + ", timeModified="
				+ timeModified + "]";
	}
	
	

}
