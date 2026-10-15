package com.example.ultssetup;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

public class ConfigParamHandler {

    private ConfigParamHandler()
    {
    }
    public static void updateConfigParam() {
        updateConfigParam(new String[0]);
    }

    /**
     * Removes certain characters from the param_config.toml based on the param
     * @param   excludedFighters    Fighters excluded from the prcxml
     */
    public static void updateConfigParam(String[] excludedFighters)
    {
        if (!(new File("resources/config_param.toml").isFile())) {
            try {
                Files.copy(
                        Paths.get("ultimate/mods/Ultimate S Arcropolis/config_param.toml"),
                        Paths.get("resources/config_param.toml"), StandardCopyOption.REPLACE_EXISTING
                );
            } catch(IOException e) {
                e.printStackTrace();
            }
        }
        try {
            List<String> fighterParam = Files.readAllLines(Paths.get("resources/config_param.toml"));
            boolean deleting = false;
            for(int i = 0; i < fighterParam.size(); i++ ) {
                String currLine = fighterParam.get(i);
                if(currLine.contains("#")) {
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
                        } else if (f == "diddy") {
                            if (currLine.contains("DIDDY")) {
                                beginRemoval = true;
                            }
                        } else if (f == "pit") {
                            if (currLine.contains("PIT") && !currLine.contains("PITB")) {
                                beginRemoval = true;
                            }
                        } else {
                            String fighter = f.toUpperCase();
                            if (currLine.equals("# " + fighter)
                                    || currLine.equals("#"+fighter)
                                    || currLine.contains("/ " + fighter)
                                    || currLine.contains("# "+ fighter + " /")
                                    || currLine.contains("#"+ fighter + " /")) {
                                beginRemoval = true;
                            }
                        }
                        if (beginRemoval) {
                            deleting = true;
                            toContinue = true;
                            fighterParam.set(i, "\n");
                            continue;
                        }
                    }
                    if (!toContinue) {
                        deleting = false;
                    }
                }
                currLine = fighterParam.get(i);
                if (deleting) {
                    fighterParam.set(i, "");
                }
            }
            try(FileWriter writer = new FileWriter("ultimate/mods/Ultimate S Arcropolis/config_param.toml")) {
                for(String i : fighterParam) {
                    writer.write(i+"\n");
                }
            }
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
}
