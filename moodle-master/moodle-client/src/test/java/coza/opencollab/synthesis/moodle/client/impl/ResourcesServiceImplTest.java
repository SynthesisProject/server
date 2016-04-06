package coza.opencollab.synthesis.moodle.client.impl;

import coza.opencollab.synthesis.moodle.client.impl.MoodleLoginServiceImpl;
import coza.opencollab.synthesis.moodle.client.impl.ResourcesServiceImpl;
import java.util.Collection;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import coza.opencollab.synthesis.moodle.client.MoodleWS;
import coza.opencollab.synthesis.moodle.client.ResourcesService;
import coza.opencollab.synthesis.moodle.client.dao.file.MoodleFile;

public class ResourcesServiceImplTest {

	protected final Logger log = Logger.getLogger(getClass());
	protected final String host = "http://moodle.opencollab.co.za";
	protected String baseRestStr = host
			+ "/webservice/rest/server.php?moodlewsrestformat=json&wstoken={0}&wsfunction={1}&{2}";
	protected String appName = "moodle_mobile_app";
	protected int timeout = 30000;

	private MoodleLoginServiceImpl loginService;
	private ResourcesService service;

	@Before
	public void before() {
		loginService = new MoodleLoginServiceImpl(timeout, host, baseRestStr,
				appName);
		service = new ResourcesServiceImpl(timeout, host, baseRestStr,
				appName);
	}

	@Ignore
	@Test
	public void shouldGetAttachments() {
        String token = loginService.login("admin","Moodl3!s50cool");
        log.debug("Login token : " + token);
        Collection<MoodleFile> result = service.retrieveMoodleFiles(token, MoodleWS.Parameter.Component.FORUM,
        		MoodleWS.Parameter.FileArea.Attachment, 5, 5);
        
        log.debug(result);
	}
}
