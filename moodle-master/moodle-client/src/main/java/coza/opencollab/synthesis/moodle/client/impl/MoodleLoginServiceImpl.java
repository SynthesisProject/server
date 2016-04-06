package coza.opencollab.synthesis.moodle.client.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.MessageFormat;

import com.google.gson.Gson;

import coza.opencollab.synthesis.moodle.client.dao.Token;
import coza.opencollab.synthesis.service.api.LoginService;

/**
 * Implements the login service.
 *
 * @author OpenCollab
 * @since 1.0.0
 */
public class MoodleLoginServiceImpl extends AbstractServiceImpl implements LoginService {

	/**
	 * {$inheritDoc}
	 */
	public MoodleLoginServiceImpl() {
	}

	/**
	 * {$inheritDoc}
	 */	
	public MoodleLoginServiceImpl(int timeout, String host, String baseRestStr,
			String appName) {
		super(timeout, host, baseRestStr, appName);
	}



	/**
     * {@inheritDoc}
     */
    @Override
    public String login(String username, String password) {
        String tokenStr = "";
        try {
            tokenStr = getToken(username,password, getAppName());
        } catch (Exception ex) {
            log.error("Exception retrieving token", ex);
            throw new RuntimeException("Unable to login");
        }
        Token token = new Gson().fromJson(tokenStr, Token.class);
        return token.getToken();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean logout(String sessionId) throws Exception {
        //TODO: logout
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getUserDetails(String sessionId, String username) throws Exception {
        String wsfunction = "core_user_get_course_user_profiles";
        String params = "userlist[0][courseid]={0}&userlist[0][userid]=3";
        return callService(sessionId, wsfunction, MessageFormat.format(params, 2));
    }
    
    public String getToken(String username, String password, String appName) throws IOException {
    	String loginStr = getHost().endsWith("/") ? getHost() : getHost() + "/";
    	loginStr += "login/token.php?username=" + username + "&password=" + password + "&service=" + appName;

        URL url = new URL(loginStr);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        if (conn.getResponseCode() != 200) {
            throw new IOException(conn.getResponseMessage());
        }

        // Buffer the result into a string
        BufferedReader rd = new BufferedReader(
                new InputStreamReader(conn.getInputStream()));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = rd.readLine()) != null) {
            sb.append(line);
        }
        rd.close();

        conn.disconnect();
        return sb.toString();
    }
}
