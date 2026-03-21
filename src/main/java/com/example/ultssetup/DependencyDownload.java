package com.example.ultssetup;
import java.io.File;
import java.net.URL;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.BufferedOutputStream;
import java.util.HashMap;
import java.net.URI;
import java.net.URISyntaxException;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;

/**
 * Write a description of class DependencyDownload here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class DependencyDownload
{
    public static final int BUFFER_SIZE = 4096;
    /**
     * Constructor for objects of class DependencyDownload
     */
    private DependencyDownload()
    {
    }
    
    public static void setupFolders() {
        String[] directories = new String[]{
            "downloads",
            "atmosphere/contents/01006A800016E000/romfs/skyline/plugins"
        };
        File directory;
        for(String d : directories) {
            directory = new File(d);
            if (!directory.isDirectory()) {
                directory.mkdirs();
            }
        }
        
    }
    
    public static void emptyFolders() {
        String[] directories = new String[]{
            "downloads",
            "atmosphere/contents/01006A800016E000"
        };
        File directory;
        for(String d : directories) {
            directory = new File(d);
            DirectoryDeleter.deleteDirectory(directory);
        }
        
    }
    
    
    public static void getSkyline() throws IOException, URISyntaxException{
        String dlURL = "https://github.com/skyline-dev/skyline/releases/download/beta/skyline.zip";
        try (InputStream  in = new URI(dlURL).toURL().openStream()) {
            Files.copy(in, Paths.get("downloads/skyline.zip"), StandardCopyOption.REPLACE_EXISTING);
        }
        unzip("downloads/skyline.zip", "atmosphere/contents/01006A800016E000");
    }
    
    public static void getNROHook() throws IOException, URISyntaxException {
        HashMap<String, String> apiMap = GithubAPIReader.getResults("https://api.github.com/repos/ultimate-research/nro-hook-plugin/releases/latest");
        String latestVer = apiMap.get("tag_name");
        String dlURL = "https://github.com/ultimate-research/nro-hook-plugin/releases/download/"+latestVer+"/libnro_hook.nro";
        try (InputStream  in = new URI(dlURL).toURL().openStream()) {
            Files.copy(in, Paths.get("downloads/libnro_hook.nro"), StandardCopyOption.REPLACE_EXISTING);
            Files.copy(Paths.get("downloads/libnro_hook.nro"), Paths.get("atmosphere/contents/01006A800016E000/romfs/skyline/plugins/libnro_hook.nro"), StandardCopyOption.REPLACE_EXISTING);
        }
    }
    
    public static void getSmashlineHook() throws IOException, URISyntaxException {
        HashMap<String, String> apiMap = GithubAPIReader.getResults("https://api.github.com/repos/HDR-Development/smashline/releases/latest");
        String latestVer = apiMap.get("tag_name");
        String dlURL = "https://github.com/HDR-Development/smashline/releases/download/"+latestVer+"/libsmashline_plugin.nro";
        try (InputStream  in = new URI(dlURL).toURL().openStream()) {
            Files.copy(in, Paths.get("downloads/libsmashline_plugin.nro"), StandardCopyOption.REPLACE_EXISTING);
            Files.copy(Paths.get("downloads/libsmashline_plugin.nro"), Paths.get("atmosphere/contents/01006A800016E000/romfs/skyline/plugins/libsmashline_plugin.nro"), StandardCopyOption.REPLACE_EXISTING);
        }
    }
    
    public static void getParamConfigHook() throws IOException, URISyntaxException {
        
        HashMap<String, String> apiMap = GithubAPIReader.getResults("https://api.github.com/repos/CSharpM7/lib_paramconfig/releases/latest");
        String latestVer = apiMap.get("tag_name");
        String dlURL = "https://github.com/CSharpM7/lib_paramconfig/releases/download/"+latestVer+"/libparam_config.nro";
        try (InputStream  in = new URI(dlURL).toURL().openStream()) {
            Files.copy(in, Paths.get("downloads/libparam_config.nro"), StandardCopyOption.REPLACE_EXISTING);
            Files.copy(Paths.get("downloads/libparam_config.nro"), Paths.get("atmosphere/contents/01006A800016E000/romfs/skyline/plugins/libparam_config.nro"), StandardCopyOption.REPLACE_EXISTING);
        }
    }
    
    public static void getStageConfigHook()  throws IOException, URISyntaxException{
        
        HashMap<String, String> apiMap = GithubAPIReader.getResults("https://api.github.com/repos/ThatNintendoNerd/stage_config/releases/latest");
        String latestVer = apiMap.get("tag_name");
        String dlURL = "https://github.com/ThatNintendoNerd/stage_config/releases/download/"+latestVer+"/release.zip";
        try (InputStream  in = new URI(dlURL).toURL().openStream()) {
            Files.copy(in, Paths.get("downloads/stageConfig.zip"), StandardCopyOption.REPLACE_EXISTING);
            File directory = new File("downloads/atmosphere/contents/01006A800016E000/romfs/skyline/plugins");
            if (!directory.isDirectory()) {
                directory.mkdirs();
            }
            unzip("downloads/stageConfig.zip", "downloads");
            Files.copy(Paths.get("downloads/atmosphere/contents/01006A800016E000/romfs/skyline/plugins/libstage_config.nro"), Paths.get("atmosphere/contents/01006A800016E000/romfs/skyline/plugins/libstage_config.nro"), StandardCopyOption.REPLACE_EXISTING);
        }
    }
    
    public static void getArcropolis()  throws IOException, URISyntaxException {
        
        HashMap<String, String> apiMap = GithubAPIReader.getResults("https://api.github.com/repos/Raytwo/ARCropolis/releases/latest");
        String latestVer = apiMap.get("tag_name");        
        String dlURL = "https://github.com/Raytwo/ARCropolis/releases/download/"+latestVer+"/release.zip";
        try (InputStream  in = new URI(dlURL).toURL().openStream()) {
            Files.copy(in, Paths.get("downloads/arcropolis.zip"), StandardCopyOption.REPLACE_EXISTING);
            File directory = new File("downloads/atmosphere/contents/01006A800016E000/romfs/skyline/plugins");
            if (!directory.isDirectory()) {
                directory.mkdirs();
            }
            unzip("downloads/arcropolis.zip", "downloads");
            Files.copy(Paths.get("downloads/atmosphere/contents/01006A800016E000/romfs/skyline/plugins/libarcropolis.nro"), Paths.get("atmosphere/contents/01006A800016E000/romfs/skyline/plugins/libarcropolis.nro"), StandardCopyOption.REPLACE_EXISTING);
        }
    }
    
    public static void getOSE()  throws IOException, URISyntaxException {
        
        HashMap<String, String> apiMap = GithubAPIReader.getResults("https://api.github.com/repos/blu-dev/one-slot-effects/releases/latest");
        String latestVer = apiMap.get("tag_name");
        String dlURL = "https://github.com/blu-dev/one-slot-effects/releases/download/"+latestVer+"/libone_slot_eff.nro";
        try (InputStream  in = new URI(dlURL).toURL().openStream()) {
            Files.copy(in, Paths.get("downloads/libone_slot_eff.nro"), StandardCopyOption.REPLACE_EXISTING);
            Files.copy(Paths.get("downloads/libone_slot_eff.nro"), Paths.get("atmosphere/contents/01006A800016E000/romfs/skyline/plugins/libone_slot_eff.nro"), StandardCopyOption.REPLACE_EXISTING);
        }
    }
    
    public static void getCSK()  throws IOException, URISyntaxException {
        String dlURL =  GithubAPIReader.getResultsGB("https://api.gamebanana.com/Core/Item/Data?itemtype=Mod&itemid=499008&fields=Files().aFiles()");
        
        try (InputStream  in = new URI(dlURL).toURL().openStream()) {
            Files.copy(in, Paths.get("downloads/cskCollection.zip"), StandardCopyOption.REPLACE_EXISTING);
            File directory = new File("downloads/atmosphere/contents/01006A800016E000/romfs/skyline/plugins");
            if (!directory.isDirectory()) {
                directory.mkdirs();
            }
            unzip("downloads/cskCollection.zip", "downloads");
            Files.copy(Paths.get("downloads/atmosphere/contents/01006A800016E000/romfs/skyline/plugins/libthe_csk_collection.nro"), Paths.get("atmosphere/contents/01006A800016E000/romfs/skyline/plugins/libthe_csk_collection.nro"), StandardCopyOption.REPLACE_EXISTING);
        }
    }
    private static void unzip(String zipFilePath, String destDirectory)  throws IOException, URISyntaxException {
            File destDir = new File(destDirectory);
            if (!destDir.exists()) {
                destDir.mkdir();
            }
            ZipInputStream zipIn = new ZipInputStream(new FileInputStream(zipFilePath));
            ZipEntry entry = zipIn.getNextEntry();
            // iterates over entries in the zip file
            while (entry != null) {
                String filePath = destDirectory + File.separator + entry.getName();
                if (!entry.isDirectory()) {
                    // if the entry is a file, extracts it
                    extractFile(zipIn, filePath);
                } else {
                    // if the entry is a directory, make the directory
                    File dir = new File(filePath);
                    dir.mkdirs();
                }
                zipIn.closeEntry();
                entry = zipIn.getNextEntry();
            }
            zipIn.close();
        
    }
    private static void extractFile(ZipInputStream zipIn, String filePath) throws IOException {
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(filePath));
        byte[] bytesIn = new byte[BUFFER_SIZE];
        int read = 0;
        while ((read = zipIn.read(bytesIn)) != -1) {
            bos.write(bytesIn, 0, read);
        }
        bos.close();
    }
}
