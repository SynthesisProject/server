package coza.opencollab.synthesis.moodle.client;

import coza.opencollab.synthesis.moodle.client.dao.Forum;

/**
 *
 * @author OpenCollab
 */
public interface AnnouncementService {
	
	public Forum getAnnouncements(String token, int courseId);

}