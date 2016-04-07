package coza.opencollab.synthesis.moodle.client.impl;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.springframework.util.FileCopyUtils;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;

import coza.opencollab.synthesis.moodle.client.MoodleWS;
import coza.opencollab.synthesis.moodle.client.ResourcesService;
import coza.opencollab.synthesis.moodle.client.dao.file.MoodleFile;

/**
 *
 * @author OpenCollab
 */
public class ResourcesServiceImpl extends AbstractServiceImpl implements ResourcesService {

    private static String SITEBLOCKCONTENTFUNCTION = "core_course_get_contents";

    /**
     * {@inheritDoc}
     */
    public ResourcesServiceImpl() {
    }

    /**
     * {@inheritDoc}
     */
    public ResourcesServiceImpl(int timeout, String host, String baseRestStr,
            String appName) {
        super(timeout, host, baseRestStr, appName);
    }

    @Override
    public String getResourcesForSite(String defaultFormat, String sessionId, int siteId, Date fromDate) {
        return callService(sessionId, SITEBLOCKCONTENTFUNCTION, "courseid=" + siteId);
    }

    @Override
    public Collection<MoodleFile> retrieveMoodleFiles(String token, String component, String filearea, int instanceId, int itemId) {
        StringBuilder params = new StringBuilder();
        params.append("contextid=-1");
        params.append("&component=").append(component);
        params.append("&filearea=").append(filearea);
        params.append("&filepath");
        params.append("&filename");
        params.append("&contextlevel=module");
        params.append("&instanceid=").append(instanceId);
        params.append("&itemid=").append(itemId);

        String json = callService(token, MoodleWS.Function.GET_FILES, params.toString());
        Map<String, JsonArray> result = gson.fromJson(json, new TypeToken<Map<String, JsonArray>>() {
        }.getType());
        Collection<MoodleFile> moodleFiles = new ArrayList<MoodleFile>();

        for (JsonElement obj : result.get("files")) {
            MoodleFile file = gson.fromJson(obj, MoodleFile.class);
            moodleFiles.add(file);
        }

        return moodleFiles;

    }

    /**
     * Downloads the specified resource into a byte array
     */
    @Override
    public byte[] downloadResource(String urlString, String sessionId) {
        URLConnection conn = null;
        InputStream is = null;
        ByteArrayOutputStream os = null;
        try {
            urlString = urlString + "?token=" + sessionId;
            if (!urlString.contains("/webservice/pluginfile.php")) {
                urlString = urlString.replace("pluginfile.php", "webservice/pluginfile.php");
            }
            URL url = new URL(urlString);
            conn = url.openConnection();
            os = new ByteArrayOutputStream(1024);
            is = new BufferedInputStream(conn.getInputStream());
            FileCopyUtils.copy(is, os);
            return os.toByteArray();
        } catch (Exception e) {
            log.error("Unable to download resource with url : " + urlString, e);
            throw new RuntimeException("Failed downloading resource " + urlString);
        } finally {
            IOUtils.closeQuietly(is);
            IOUtils.closeQuietly(os);
            IOUtils.close(conn);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getDownloadResourceUrl(String urlString, String sessionId) {
        urlString = urlString + "?token=" + sessionId;
        if (!urlString.contains("/webservice/pluginfile.php")) {
            urlString = urlString.replace("pluginfile.php", "webservice/pluginfile.php");
        }
        return urlString;
    }

}
