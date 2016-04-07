package coza.opencollab.synthesis.moodle.client.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.MessageFormat;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;

import com.google.gson.Gson;

/**
 * A abstract implementation that handle the service url and timeout.
 *
 * @author OpenCollab
 * @since 1.0.0
 */
public class AbstractServiceImpl {

	
	protected Logger log = Logger.getLogger(getClass());	
	
    /**
     * The call timeout.
     */
    private int timeout;
    private String host;
    private String baseRestStr;
    private String appName;
    
    /**
     * The main Gson object constructed here for efficiency.
     */
    protected Gson gson = new Gson();
    
    /**
     * Constructor for Spring
     */
    public AbstractServiceImpl() {
	}
    
    /**
     * Constructor for testing
     * @param timeout timeout
     * @param host host
     * @param baseRestStr base URL
     * @param appName name of the application
     */
    public AbstractServiceImpl(int timeout, String host, String baseRestStr,
			String appName) {
		super();
		this.timeout = timeout;
		this.host = host;
		this.baseRestStr = baseRestStr;
		this.appName = appName;
	}



    /**
     * Calls the web service with the specified function to retrieve data from the moodle web service
     *
     * @param token returned when login with admin
     * @param wsfunction web service function name
     * @param params additional parameters in url format
     * @return the service response
     */
    public String callService(String token, String wsfunction, String params) {
    	
        String urlString = MessageFormat.format(getBaseRestStr(), token, wsfunction, params);
        log.trace(wsfunction + " url : " + urlString);
        
        return queryService(urlString);
    }

    private String queryService(String urlString)  {
    	BufferedReader reader = null;
    	HttpURLConnection conn = null;
		
		try {
			
			URL url = new URL(urlString);
			conn = (HttpURLConnection) url.openConnection();
			conn.setConnectTimeout(timeout);
			if (conn.getResponseCode() != 200) {
				throw new IOException(conn.getResponseMessage());
			}

			// Buffer the result into a string
			reader = new BufferedReader(new InputStreamReader(
					conn.getInputStream()));
			StringBuilder sb = new StringBuilder();
			String line;
			while ((line = reader.readLine()) != null) {
				sb.append(line);
			}
			log.trace("response : " + sb.toString());
			return sb.toString();
		} catch (Exception e) {
			log.error("Unable to call service with url " + urlString, e);
			throw new RuntimeException("Unable to call service with url " + urlString);
		} finally {
			IOUtils.closeQuietly(reader);
			IOUtils.close(conn);
		}

    }

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public String getBaseRestStr() {
        return baseRestStr;
    }

    public void setBaseRestStr(String baseRestStr) {
        this.baseRestStr = baseRestStr;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}
    
}
