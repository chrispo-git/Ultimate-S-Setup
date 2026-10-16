package com.example.ultssetup;
import java.util.HashMap;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

/**
 * Simple readers for the GitHub and GameBanana APIs.
 */
public class GithubAPIReader
{
    private GithubAPIReader()
    {
    }
    /**
     * Returns the file download URL from a GameBanana API response.
     */
    public static String getResultsGB(String urlString) throws IOException
    {
        try {
            URL url = new URI(urlString).toURL();
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(DependencyDownload.CONNECT_TIMEOUT_MS);
            connection.setReadTimeout(DependencyDownload.READ_TIMEOUT_MS);
            
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            String response = "";
            while ((inputLine = in.readLine()) != null) {
                response = response + inputLine;
            }
            in.close();
            System.out.println(response);
            response = response.replace("\n", "");
            response = response.replace("\\", "");
            response = response.replace(": ", "");
            response = response.replace(" ", "");
            String noQuotes = response.replace("\"","");
            noQuotes = noQuotes.split("_sDownloadUrl")[1];
            noQuotes = noQuotes.split(",")[0];
            System.out.println(noQuotes);
            return noQuotes;
        } catch (URISyntaxException e) {
            ExceptionAlert.alert("Invalid URL");
            return null;
        }
    }
    
    /**
     * Fetches a GitHub API URL and returns its fields as a simple key/value map.
     */
    public static HashMap<String, String> getResults(String urlString) throws IOException
    {
        try {
            URL url = new URI(urlString).toURL();
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(DependencyDownload.CONNECT_TIMEOUT_MS);
            connection.setReadTimeout(DependencyDownload.READ_TIMEOUT_MS);
            
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            String response = "";
            while ((inputLine = in.readLine()) != null) {
                response = response + inputLine;
            }
            in.close();
            response = response.replace("\n", "");
            String[] responseArr = response.split("\",\"");
            HashMap<String, String> map = new HashMap<>();
            for(String i : responseArr) {
                String noQuotes = i.replace("\"","");
                noQuotes = noQuotes.replace("{","");
                noQuotes = noQuotes.replace("}","");
                //System.out.println(noQuotes);
                String[] pair = noQuotes.split(":");
                if (pair.length < 2) continue;
                map.put(pair[0], pair[1]);
            }
            return map;
        } catch (URISyntaxException e) {
            ExceptionAlert.alert("Invalid URL");
            return null;
        }
    }
}
