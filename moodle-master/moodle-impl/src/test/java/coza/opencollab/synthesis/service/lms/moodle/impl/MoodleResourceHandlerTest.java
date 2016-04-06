package coza.opencollab.synthesis.service.lms.moodle.impl;

import javax.activation.MimetypesFileTypeMap;

import org.junit.Test;

public class MoodleResourceHandlerTest {

	
	//@Test
	public void shouldHandleMimeTypes(){
		MimetypesFileTypeMap mimeTypesMap = new MimetypesFileTypeMap();
		
		String contentType = mimeTypesMap.getContentType("test.jpg");
		System.out.println(contentType);
	}
	
}
