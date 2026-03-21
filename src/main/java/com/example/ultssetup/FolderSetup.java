package com.example.ultssetup;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

/**
 * Write a description of class TroubleShooter here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class FolderSetup
{
    // instance variables - replace the example below with your own
    private int x;

    /**
     * Constructor for objects of class TroubleShooter
     */
    private FolderSetup()
    {
    }
    
    public static void setup(String pathRoot) throws IOException
    {
        String issues = "";
        if (pathRoot.endsWith("/")) pathRoot = pathRoot.substring(0,pathRoot.length()-1);
        File disabledPlugins = new File(pathRoot+"/atmosphere/contents/01006A800016E000/romfs/skyline/disabled_plugins");
        if (!disabledPlugins.isDirectory()) {
            disabledPlugins.mkdirs();
        }
        if(new File(pathRoot+"/atmosphere/contents/01006A800016E000/romfs/skyline/plugins/libtraining_modpack.nro").isFile()) {
            Files.move(Paths.get(pathRoot+"/atmosphere/contents/01006A800016E000/romfs/skyline/plugins/libtraining_modpack.nro"), 
            Paths.get(pathRoot+"/atmosphere/contents/01006A800016E000/romfs/skyline/disabled_plugins/libtraining_modpack.nro"), StandardCopyOption.REPLACE_EXISTING);
        }
        if(new File(pathRoot+"/atmosphere/contents/01006A800016E000/romfs/skyline/plugins/libparam_hook.nro").isFile()) {
            Files.move(Paths.get(pathRoot+"/atmosphere/contents/01006A800016E000/romfs/skyline/plugins/libparam_hook.nro"), 
            Paths.get(pathRoot+"/atmosphere/contents/01006A800016E000/romfs/skyline/disabled_plugins/libparam_hook.nro"), StandardCopyOption.REPLACE_EXISTING);
        }
        if(new File(pathRoot+"/atmosphere/contents/01006A800016E000/romfs/skyline/plugins/libacmd_hook.nro").isFile()) {
            Files.move(Paths.get(pathRoot+"/atmosphere/contents/01006A800016E000/romfs/skyline/plugins/libacmd_hook.nro"), 
            Paths.get(pathRoot+"/atmosphere/contents/01006A800016E000/romfs/skyline/disabled_plugins/libacmd_hook.nro"), StandardCopyOption.REPLACE_EXISTING);
        }
        if(new File(pathRoot+"/atmosphere/contents/01006A800016E000/romfs/skyline/plugins/libsmashline_hook.nro").isFile()) {
            Files.move(Paths.get(pathRoot+"/atmosphere/contents/01006A800016E000/romfs/skyline/plugins/libsmashline_hook.nro"), 
            Paths.get(pathRoot+"/atmosphere/contents/01006A800016E000/romfs/skyline/disabled_plugins/libsmashline_hook.nro"), StandardCopyOption.REPLACE_EXISTING);
        }
        
        CopyDir.copyDirectory(Paths.get("atmosphere/"), Paths.get(pathRoot+"/atmosphere/"));
        DirectoryDeleter.deleteDirectory(new File(pathRoot+"/ultimate/ult-s"));
        DirectoryDeleter.deleteDirectory(new File(pathRoot+"/ultimate/mods/Ultimate S Arcropolis"));
        DirectoryDeleter.deleteDirectory(new File(pathRoot+"/ultimate/mods/Ultimate S Stages"));
        CopyDir.copyDirectory(Paths.get("ultimate/"), Paths.get(pathRoot+"/ultimate/"));
        System.out.println("done");
    }
}
