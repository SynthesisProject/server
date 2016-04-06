package coza.opencollab.synthesis.service.util.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Map;

import org.apache.log4j.Logger;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.util.FileCopyUtils;

import coza.opencollab.synthesis.service.api.LoginService;
import coza.opencollab.synthesis.service.api.util.LinkFileConsumer;
import coza.opencollab.synthesis.service.lms.sakai.impl.SakaiLMSParser;
import coza.opencollab.synthesis.util.JsonParser;

/**
 *
 * @author JaquesS
 */
@Ignore
public class HTMLLinkDownLoaderTest {
    private static final Logger LOG = Logger.getLogger(HTMLLinkDownLoaderTest.class);
    private static final HtmlLinkDownLoader loader = new HtmlLinkDownLoader();
    private static final CachedSessionManager sessionManager = new CachedSessionManager();
    
    @BeforeClass
    public static void setUpClass() {
        sessionManager.setParser(new SakaiLMSParser());
        sessionManager.setAdminUsername("oc_admin");
        sessionManager.setAdminPassword("SpringCl@w");
        sessionManager.setLoginService(new LoginService() {

            @Override
            public String login(String username, String password) throws Exception {
                return "4946e019-9885-458d-a4e0-9ff6d50dac5f";
            }

            @Override
            public boolean logout(String sessionId) throws Exception {
                return true;
            }

            @Override
            public String getUserDetails(String sessionId, String username) throws Exception {
                return "";
            }
        });
        sessionManager.init();
        loader.setSessionManager(sessionManager);
        loader.setLMSContentService(null);
        loader.setLmsContentBaseURL(null);
    }
    
    
    @Test
    public void test2() throws FileNotFoundException, IOException {
        FileReader in = new FileReader("src/test/resources/announcements.json");
        String allContent = FileCopyUtils.copyToString(in);
        Map<String, Map<String, String>> map = (Map<String, Map<String, String>>) JsonParser.parseJsonToMap(allContent);
        String[] updatedContent = new String[map.size()];
        int i = 0;
        
        for(Map.Entry<String, Map<String, String>> entry: map.entrySet()){
            updatedContent[i++] = loader.update(String.valueOf(i), entry.getValue().get("body"), new LinkFileConsumer() {
                
                @Override
                public String addFile(String name, String directory, String originalUrl, byte[] data) {
                    LOG.info(directory + "/" + name);
                    new File("c:/aaa/").mkdirs();
                    try {
                        FileCopyUtils.copy(data, new File("c:/aaa/", name));
                    } catch (IOException ex) {
                        LOG.warn("Exception while trying to copy files", ex);
                    }
                    return directory + "/" + name;
                }
            });
        }
        LOG.info(Arrays.toString(updatedContent));
        
    }
}