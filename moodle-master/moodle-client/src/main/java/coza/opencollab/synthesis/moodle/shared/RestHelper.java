package coza.opencollab.synthesis.moodle.shared;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.MessageFormat;

/**
 * 
 * @author OpenCollab
 */
public class RestHelper {

    private String baseUrl;
    
    public String getJson(String baseUrl, String defaultFormat, String token, String wsfunction, String params, int id) throws Exception {
        String urlString = MessageFormat.format(
                baseUrl,
                defaultFormat,
                token,
                wsfunction,
                MessageFormat.format(params, String.valueOf(id)));
        URL url = new URL(urlString);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setConnectTimeout(5000);
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

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }
}
