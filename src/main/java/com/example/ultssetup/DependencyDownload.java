package com.example.ultssetup;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.BufferedOutputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLConnection;

/**
 * Downloads Skyline, ARCropolis and the other plugins Ultimate S needs.
 */
public class DependencyDownload
{
    public static final int BUFFER_SIZE = 4096;
    
    public static final int CONNECT_TIMEOUT_MS = 10_000;
    public static final int READ_TIMEOUT_MS = 20_000;

    private DependencyDownload()
    {
    }

    /**
     * Opens a download with connect and read timeouts.
     */
    private static InputStream openStream(String dlURL) throws IOException, URISyntaxException {
        URLConnection connection = new URI(dlURL).toURL().openConnection();
        connection.setConnectTimeout(CONNECT_TIMEOUT_MS);
        connection.setReadTimeout(READ_TIMEOUT_MS);
        return connection.getInputStream();
    }

    /**
     * Creates the downloads and plugin folders if they're missing.
     */
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
    
    /**
     * Clears out downloads/ and the previous atmosphere build.
     */
    public static void emptyFolders() {
        String[] directories = new String[]{
            "downloads",
            "atmosphere/contents/01006A800016E000",
            "ultimate/arcropolis"
        };
        File directory;
        for(String d : directories) {
            directory = new File(d);
            DirectoryDeleter.deleteDirectory(directory);
        }
        
    }
    
    
    /**
     * Downloads Skyline and extracts it into atmosphere/.
     */
    public static void getSkyline() throws IOException, URISyntaxException{
        String dlURL = "https://github.com/skyline-dev/skyline/releases/download/beta/skyline.zip";
        try (InputStream  in = openStream(dlURL)) {
            Files.copy(in, Paths.get("downloads/skyline.zip"), StandardCopyOption.REPLACE_EXISTING);
        }
        unzip("downloads/skyline.zip", "atmosphere/contents/01006A800016E000");
    }
    
    /**
     * Downloads the latest NRO Hook plugin.
     */
    public static void getNROHook() throws IOException, URISyntaxException {
        HashMap<String, String> apiMap = GithubAPIReader.getResults("https://api.github.com/repos/ultimate-research/nro-hook-plugin/releases/latest");
        String latestVer = apiMap.get("tag_name");
        String dlURL = "https://github.com/ultimate-research/nro-hook-plugin/releases/download/"+latestVer+"/libnro_hook.nro";
        try (InputStream  in = openStream(dlURL)) {
            Files.copy(in, Paths.get("downloads/libnro_hook.nro"), StandardCopyOption.REPLACE_EXISTING);
            Files.copy(Paths.get("downloads/libnro_hook.nro"), Paths.get("atmosphere/contents/01006A800016E000/romfs/skyline/plugins/libnro_hook.nro"), StandardCopyOption.REPLACE_EXISTING);
        }
    }
    
    /**
     * Downloads the latest Smashline plugin.
     */
    public static void getSmashlineHook() throws IOException, URISyntaxException {
        HashMap<String, String> apiMap = GithubAPIReader.getResults("https://api.github.com/repos/HDR-Development/smashline/releases/latest");
        String latestVer = apiMap.get("tag_name");
        String dlURL = "https://github.com/HDR-Development/smashline/releases/download/"+latestVer+"/libsmashline_plugin.nro";
        try (InputStream  in = openStream(dlURL)) {
            Files.copy(in, Paths.get("downloads/libsmashline_plugin.nro"), StandardCopyOption.REPLACE_EXISTING);
            Files.copy(Paths.get("downloads/libsmashline_plugin.nro"), Paths.get("atmosphere/contents/01006A800016E000/romfs/skyline/plugins/libsmashline_plugin.nro"), StandardCopyOption.REPLACE_EXISTING);
        }
    }
    
    /**
     * Downloads the latest param config plugin.
     */
    public static void getParamConfigHook() throws IOException, URISyntaxException {
        
        HashMap<String, String> apiMap = GithubAPIReader.getResults("https://api.github.com/repos/CSharpM7/lib_paramconfig/releases/latest");
        String latestVer = apiMap.get("tag_name");
        String dlURL = "https://github.com/CSharpM7/lib_paramconfig/releases/download/"+latestVer+"/libparam_config.nro";
        try (InputStream  in = openStream(dlURL)) {
            Files.copy(in, Paths.get("downloads/libparam_config.nro"), StandardCopyOption.REPLACE_EXISTING);
            Files.copy(Paths.get("downloads/libparam_config.nro"), Paths.get("atmosphere/contents/01006A800016E000/romfs/skyline/plugins/libparam_config.nro"), StandardCopyOption.REPLACE_EXISTING);
        }
    }
    
    /**
     * Downloads the latest stage config plugin.
     */
    public static void getStageConfigHook()  throws IOException, URISyntaxException{
        
        HashMap<String, String> apiMap = GithubAPIReader.getResults("https://api.github.com/repos/ThatNintendoNerd/stage_config/releases/latest");
        String latestVer = apiMap.get("tag_name");
        String dlURL = "https://github.com/ThatNintendoNerd/stage_config/releases/download/"+latestVer+"/release.zip";
        try (InputStream  in = openStream(dlURL)) {
            Files.copy(in, Paths.get("downloads/stageConfig.zip"), StandardCopyOption.REPLACE_EXISTING);
            File directory = new File("downloads/atmosphere/contents/01006A800016E000/romfs/skyline/plugins");
            if (!directory.isDirectory()) {
                directory.mkdirs();
            }
            unzip("downloads/stageConfig.zip", "downloads");
            Files.copy(Paths.get("downloads/atmosphere/contents/01006A800016E000/romfs/skyline/plugins/libstage_config.nro"), Paths.get("atmosphere/contents/01006A800016E000/romfs/skyline/plugins/libstage_config.nro"), StandardCopyOption.REPLACE_EXISTING);
        }
    }
    
    /**
     * Downloads the latest ARCropolis and moves its files into atmosphere/ and ultimate/.
     */
    public static void getArcropolis()  throws IOException, URISyntaxException {
        
        HashMap<String, String> apiMap = GithubAPIReader.getResults("https://api.github.com/repos/Raytwo/ARCropolis/releases/latest");
        String latestVer = apiMap.get("tag_name");        
        String dlURL = "https://github.com/Raytwo/ARCropolis/releases/download/"+latestVer+"/release.zip";
        try (InputStream  in = openStream(dlURL)) {
            Files.copy(in, Paths.get("downloads/arcropolis.zip"), StandardCopyOption.REPLACE_EXISTING);
            List<File> directories = Arrays.asList(
                new File("downloads/atmosphere/contents/01006A800016E000/romfs/skyline/plugins"),
                new File("downloads/ultimate/arcropolis/resources/ui/layout"),
                new File("downloads/ultimate/arcropolis/resources/ui/message")
            );
            for (File d : directories) {
                if (!d.isDirectory()) {
                    d.mkdirs();
                }
            }
            unzip("downloads/arcropolis.zip", "downloads");
            Path downloads = Paths.get("downloads");
            for (File d : directories) {
                Path src = d.toPath();
                moveContents(src, downloads.relativize(src));
            }
        }
    }

    /**
     * Moves every file under srcDir to the same relative path under destDir.
     */
    private static void moveContents(Path srcDir, Path destDir) throws IOException {
        List<Path> files;
        try (Stream<Path> walk = Files.walk(srcDir)) {
            files = walk.filter(Files::isRegularFile).collect(Collectors.toList());
        }
        for (Path file : files) {
            Path target = destDir.resolve(srcDir.relativize(file));
            Files.createDirectories(target.getParent());
            Files.move(file, target, StandardCopyOption.REPLACE_EXISTING);
        }
    }
    
    /**
     * Downloads the One Slot Effects plugin from GameBanana.
     */
    public static void getOSE()  throws IOException, URISyntaxException {
        String dlURL =  GithubAPIReader.getResultsGB("https://api.gamebanana.com/Core/Item/Data?itemtype=Mod&itemid=549058&fields=Files().aFiles()");
        
        try (InputStream  in = openStream(dlURL)) {
            Files.copy(in, Paths.get("downloads/ose.zip"), StandardCopyOption.REPLACE_EXISTING);
            File directory = new File("downloads/atmosphere/contents/01006A800016E000/romfs/skyline/plugins");
            if (!directory.isDirectory()) {
                directory.mkdirs();
            }
            unzip("downloads/ose.zip", "downloads");
            Path nro = Files.walk(Paths.get("downloads"))
                .filter(p -> p.getFileName().toString().equals("libone_slot_eff.nro"))
                .findFirst()
                .orElseThrow(() -> new IOException("libone_slot_eff.nro not found in zip"));

            Files.copy(nro,
                Paths.get("atmosphere/contents/01006A800016E000/romfs/skyline/plugins/libone_slot_eff.nro"),
                StandardCopyOption.REPLACE_EXISTING);
        }
    }
    
    /**
     * Downloads The CSK Collection plugin from GameBanana.
     */
    public static void getCSK()  throws IOException, URISyntaxException {

        File check_for_CSK = new File("resources/libthe_csk_collection.nro");
        if (check_for_CSK.exists()) {
            Files.copy(Paths.get("resources/libthe_csk_collection.nro"), Paths.get("atmosphere/contents/01006A800016E000/romfs/skyline/plugins/libthe_csk_collection.nro"), StandardCopyOption.REPLACE_EXISTING);
            return;
        }
        String dlURL =  GithubAPIReader.getResultsGB("https://api.gamebanana.com/Core/Item/Data?itemtype=Mod&itemid=499008&fields=Files().aFiles()");
        
        try (InputStream  in = openStream(dlURL)) {
            Files.copy(in, Paths.get("downloads/cskCollection.zip"), StandardCopyOption.REPLACE_EXISTING);
            File directory = new File("downloads/atmosphere/contents/01006A800016E000/romfs/skyline/plugins");
            if (!directory.isDirectory()) {
                directory.mkdirs();
            }
            unzip("downloads/cskCollection.zip", "downloads");
            Files.copy(Paths.get("downloads/atmosphere/contents/01006A800016E000/romfs/skyline/plugins/libthe_csk_collection.nro"), Paths.get("atmosphere/contents/01006A800016E000/romfs/skyline/plugins/libthe_csk_collection.nro"), StandardCopyOption.REPLACE_EXISTING);
        }
    }
    /**
     * Extracts a zip file into destDirectory.
     */
    private static void unzip(String zipFilePath, String destDirectory)  throws IOException, URISyntaxException {
            File destDir = new File(destDirectory);
            if (!destDir.exists()) {
                destDir.mkdir();
            }
            ZipInputStream zipIn = new ZipInputStream(new FileInputStream(zipFilePath));
            ZipEntry entry = zipIn.getNextEntry();
            while (entry != null) {
                String filePath = destDirectory + File.separator + entry.getName();
                if (!entry.isDirectory()) {
                    new File(filePath).getParentFile().mkdirs();
                    extractFile(zipIn, filePath);
                } else {
                    File dir = new File(filePath);
                    dir.mkdirs();
                }
                zipIn.closeEntry();
                entry = zipIn.getNextEntry();
            }
            zipIn.close();
        
    }
    /**
     * Writes the current zip entry to filePath.
     */
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
