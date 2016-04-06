package coza.opencollab.synthesis.moodle.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.MessageFormat;

/**
 * 
 * @author OpenCollab
 */
@Deprecated
public class MoodleAuthenticator {

    private String username;
    private String password;

    public MoodleAuthenticator() {
    }
    
    /**
     * Get the authorization token from Moodle
     * @param urlString
     * @param appName
     * @return
     * @throws IOException 
     */
    
    public String getToken(String urlString, String appName) throws IOException {
    	
        urlString = MessageFormat.format(urlString, username, password, appName);
        URL url = new URL(urlString);
        HttpURLConnection conn
                = (HttpURLConnection) url.openConnection();

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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
