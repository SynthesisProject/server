/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coza.opencollab.synthesis.moodle.client;

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

import coza.opencollab.synthesis.moodle.client.dao.file.MoodleFile;

/**
 *
 * @author OpenCollab
 */
public interface ResourcesService {
    
    /**
     * Get all resource tool data for a module (Sakai site) given the sessionId
     * , siteId and the fromDate. If the fromDate is null all resource tool data
     * will be returned
     *
     * @param sessionId
     * @param siteId
     * @param fromDate
     * @return
     * @throws Exception
     */
    public String getResourcesForSite
        (String defaultFormat,String sessionId, int siteId, Date fromDate) throws Exception;

    /**
     * Get a specifc resource tool item, when given the session Id & the
     * resource id
     *
     * @param sessionId
     * @param resourceId
     * @return
     * @throws Exception
     */
    public String getResource(String sessionId, String resourceId) throws Exception;

    /**
     * To get the actual resource data (file,link,etc) , provide the sessionId and resourceId
     * @param sessionId
     * @param resourceId
     * @return
     * @throws Exception
     */
    public String getResourceData(String sessionId, String resourceId) throws Exception;

    /**
     * Retrieves MoodleFiles from Moodle
     * @param token
     * @param component
     * @param filearea
     * @param instanceId
     * @param itemId
     * @return
     */
	Collection<MoodleFile> retrieveMoodleFiles(String token, String component, String filearea, int instanceId, int itemId);

	public abstract byte[] downloadResource(String urlString, String sessionId);
    
    /**
     * Given the raw URL for the resource, add the session information in the re-
     * quired format. 
     * @param urlString
     * @param sessionId
     * @return Sessionized resource url
     */
    public String getDownloadResourceUrl(String urlString, String sessionId);
}
