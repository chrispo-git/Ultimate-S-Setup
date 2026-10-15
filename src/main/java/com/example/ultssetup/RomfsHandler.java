package com.example.ultssetup;
import java.io.FileWriter;
import java.io.IOException;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.List;
import java.nio.file.Path;

/**
 * Enables and disables fighters' mod files by renaming their folders.
 */
public class RomfsHandler
{
    /**
     * Constructor for objects of class RomfsHandler
     */
    private RomfsHandler()
    {
    }
    static List<String> paramException = (List.of(
            "donkey"
        ));
    /**
     * Ensures romfs folders are enabled/disabled correctly
     * @param   includedFighters    Fighters included
     * @param   excludedFighters    Fighters excluded
     */
    public static void updateRomfs(String[] includedFighters, String[] excludedFighters)
    {
        for(String i : excludedFighters) {
            File fighterDirectory = new File("ultimate/mods/Ultimate S Arcropolis/fighter/"+i+"/");
            if (!fighterDirectory.isDirectory()) continue;
            
            
            File paramDirectory = new File("ultimate/mods/Ultimate S Arcropolis/fighter/"+i+"/" + "/param/");
            if (paramDirectory.isDirectory() && !paramException.contains(i)) {
                paramDirectory.renameTo(new File("ultimate/mods/Ultimate S Arcropolis/fighter/"+i+"/" + "/.param/"));
            }
            try {
                Path[] filesList = Files.walk(Paths.get("ultimate/mods/Ultimate S Arcropolis/fighter/"+i+"/")).toArray(Path[]::new);
                for(Path p : filesList) {
                    if (!Files.isDirectory(p)) continue;
                    String directoryName = p.toString();
                    //System.out.println(directoryName);
                    if (directoryName.contains("c0") && !directoryName.contains(".c0")) {
                        Files.move(p, Paths.get(directoryName.replace("c0",".c0")));
                    }
                    
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        for(String i : includedFighters) {
            File fighterDirectory = new File("ultimate/mods/Ultimate S Arcropolis/fighter/"+i+"/");
            if (!fighterDirectory.isDirectory()) continue;
            
            
            File paramDirectory = new File("ultimate/mods/Ultimate S Arcropolis/fighter/"+i+"/" + "/.param/");
            if (paramDirectory.isDirectory()) {
                paramDirectory.renameTo(new File("ultimate/mods/Ultimate S Arcropolis/fighter/"+i+"/" + "/param/"));
            }
            try {
                Path[] filesList = Files.walk(Paths.get("ultimate/mods/Ultimate S Arcropolis/fighter/"+i+"/")).toArray(Path[]::new);
                for(Path p : filesList) {
                    if (!Files.isDirectory(p)) continue;
                    String directoryName = p.toString();
                    if (directoryName.contains(".c0")) {
                        Files.move(p, Paths.get(directoryName.replace(".c0","c0")));
                    }
                    
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
