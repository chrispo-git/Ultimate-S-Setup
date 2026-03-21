package com.example.ultssetup;
import java.io.File;
import java.io.IOException;

/**
 * Write a description of class FlagHandler here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class FlagHandler
{
    // instance variables - replace the example below with your own
    private int x;

    /**
     * Constructor for objects of class FighterParamHandler
     */
    private FlagHandler()
    {
    }
    
    
    /**
     * Populates the ult-s/flags folder
     * @param   includedFighters    Fighters included from the prcxml
     */
    public static void updateFlags(String[] includedFighters)
    {
        DirectoryDeleter.deleteDirectory(new File("ultimate/ult-s"));
        File directory = new File("ultimate/ult-s");
        if (!directory.isDirectory()) {
            directory.mkdir();
        }
        try {
            for(String i : includedFighters) {
                File newFlag = new File("ultimate/ult-s/"+i+".flag");
                newFlag.createNewFile();
                //System.out.println();
            }
        } catch(IOException e) {
            e.printStackTrace();
        }
        
    }
}
