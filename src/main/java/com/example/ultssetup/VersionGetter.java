package com.example.ultssetup;
import java.io.FileWriter;
import java.io.IOException;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.io.BufferedReader;
import java.util.List;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.InputStreamReader;
import java.util.HashMap;

public class VersionGetter
{
    // instance variables - replace the example below with your own

    /**
     * Constructor for objects of class VersionHandler
     */
    private VersionGetter()
    {
    }
    
    public static Version getLocalVer() {
        try {
            List<String> versionTxt = Files.readAllLines(Paths.get("ultimate/mods/Ultimate S Arcropolis/version.txt"));
            return Version.parse(versionTxt.get(0));
        } catch(IOException e) {
            return new Version(0,0,1);
        }
    }
    
    public static Version getRemoteVer() {
        String urlString = "https://api.github.com/repos/chrispo-git/ult-s/releases/latest";
        HashMap<String, String> apiResponse = GithubAPIReader.getResults(urlString);
        if (apiResponse == null) return new Version(0);
        
        return Version.parse(apiResponse.get("tag_name"));
    }
}
