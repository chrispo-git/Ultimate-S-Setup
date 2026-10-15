package com.example.ultssetup;
import java.io.FileWriter;
import java.io.IOException;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

/**
 * Edits fighter_param.prcxml to remove entries for disabled fighters.
 */
public class FighterParamHandler
{

    private FighterParamHandler()
    {
    }
    
    /**
     * Rewrites fighter_param.prcxml with no fighters removed.
     */
    public static void updateFighterParam() {
        updateFighterParam(new String[0]);
    }
    
    /**
     * Removes certain characters from the fighter_param.prcxml based on the param
     * @param   excludedFighters    Fighters excluded from the prcxml
     */
    public static void updateFighterParam(String[] excludedFighters)
    {
        if (!(new File("resources/fighter_param.prcxml").isFile())) {
            try {
                Files.copy(
                    Paths.get("ultimate/mods/Ultimate S Arcropolis/fighter/common/param/fighter_param.prcxml"), 
                    Paths.get("resources/fighter_param.prcxml"), StandardCopyOption.REPLACE_EXISTING
                );                
            } catch(IOException e) {
                e.printStackTrace();
            }
        }
        try {
            List<String> fighterParam = Files.readAllLines(Paths.get("resources/fighter_param.prcxml"));
            boolean deleting = false;
            for(int i = 0; i < fighterParam.size(); i++ ) {
                String currLine = fighterParam.get(i);
                if(currLine.contains("<!--")) {
                    boolean toContinue = false;
                    for(String f : excludedFighters) {
                        boolean beginRemoval = false;
                        if (f == "element") {
                            if (currLine.contains("EFLAME") || currLine.contains("ELIGHT")) {
                                beginRemoval = true;
                            }
                        } else if (f == "ptrainer") {
                            if (currLine.contains("PFUSHIGISOU") || currLine.contains("PLIZARDON") || currLine.contains("PZENIGAME")) {
                                beginRemoval = true;
                            }
                        } else {
                            String fighter = f.toUpperCase();
                            if ( (currLine.contains(fighter+"-") || currLine.contains(fighter+" ")) && !(currLine.contains(fighter+" /"))) {
                                beginRemoval = true;
                            }
                        }
                        if (beginRemoval) {
                                deleting = true;
                                toContinue = true;
                                String nextVal = fighterParam.get(i+1);
                                nextVal = nextVal.replace("<struct index=\"", "");
                                nextVal = nextVal.replace(" ", "");
                                nextVal = nextVal.replace("\">", "");
                                nextVal = nextVal.replace("\n", "");
                                fighterParam.set(i, ("    <hash40 index=\""+nextVal+"\">dummy</hash40>"));
                                continue;
                        }
                    }
                    if (!toContinue) {
                        deleting = false;
                    }
                }
                currLine = fighterParam.get(i);
                if (deleting && !currLine.contains("\">dummy</hash40>")) {
                    fighterParam.set(i, "\n");
                }
            }
            try(FileWriter writer = new FileWriter("ultimate/mods/Ultimate S Arcropolis/fighter/common/param/fighter_param.prcxml")) {
                for(String i : fighterParam) {
                    writer.write(i+"\n");
                }
            }
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
}
