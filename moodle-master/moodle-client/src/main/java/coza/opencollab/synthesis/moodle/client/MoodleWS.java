package coza.opencollab.synthesis.moodle.client;

import java.util.Date;

/**
 * Interface to hold Moodle web service function call names.
 * Being an interface all values are implicitly public static and final.
 * @author mare
 *
 */
public interface MoodleWS {
	
	
	public interface Function {
		/**
		 * Moodle service to get files associated to a module and an item
		 */
		String GET_FILES = "core_files_get_files";
	}
	
	public interface Parameter {
		public interface Component {
			String FORUM = "mod_forum";
		}
		
		public interface FileArea {
			String Attachment = "attachment";
			String Post = "post";
		}
	}
	
	class Converter {
		public static final Date convertToDate(Number intDate){
			return new Date(intDate.longValue() * 1000);
		}
		
	}

}
