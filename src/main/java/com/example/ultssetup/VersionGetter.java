package com.example.ultssetup;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.HashMap;

/**
 * Gets the installed and latest released versions of Ultimate S.
 */
public class VersionGetter
{
    private VersionGetter()
    {
    }
    
    /**
     * Returns the installed version from version.txt, or v.0.0.1 if it can't be read.
     */
    public static Version getLocalVer() {
        try {
            List<String> versionTxt = Files.readAllLines(Paths.get("ultimate/mods/Ultimate S Arcropolis/version.txt"));
            return Version.parse(versionTxt.get(0));
        } catch(IOException e) {
            return new Version(0,0,1);
        }
    }
    
    /**
     * Returns the latest release version on GitHub, or v.0.0.0 if it can't be fetched.
     */
    public static Version getRemoteVer() {
        String urlString = "https://api.github.com/repos/chrispo-git/ult-s/releases/latest";
        try {
            HashMap<String, String> apiResponse = GithubAPIReader.getResults(urlString);
            if (apiResponse == null) return new Version(0);

            return Version.parse(apiResponse.get("tag_name"));
        } catch(IOException e) {
            return new Version(0);
        }
    }
}
