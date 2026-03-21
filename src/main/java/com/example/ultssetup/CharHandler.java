package com.example.ultssetup;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Write a description of class CharHandler here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class CharHandler
{
    // instance variables - replace the example below with your own
    private static CharHandler instance;
    private List<String> included;
    private List<String> excluded;
    /**
     * Constructor for objects of class CharHandler
     */
    private CharHandler()
    {
        resetChars();
    }
    public void resetChars() {
        
        included = new ArrayList<>(List.of(
            "bayonetta", "brave", "buddy",
            "captain", "chrom", "cloud",
            "daisy", "dedede", "demon", "diddy", "dolly", "donkey", "duckhunt",
            "edge", "element",
            "falco", "fox",
            "gamewatch", "ganon", "gaogaen", "gekkouga", 
            "ike", "inkling", 
            "jack",
            "kamui", "ken", "kirby", "koopa", "koopajr", "krool",
            "link", "littlemac", "lucario", "lucas", "lucina", "luigi",
            "mario", "mariod", "marth", "master", "metaknight", "mewtwo", "miifighter", "miigunner", "miiswordsman", "murabito",
            "ness", 
            "packun", "pacman", "palutena", "peach", "pikachu", "pichu", "pit", "pitb", "pikmin", "popo", "ptrainer", "purin",
            "reflet", "richter", "ridley", "robot", "rockman", "rosetta", "roy", "ryu",
            "samus", "samusd", "sheik", "shizue", "shulk", "simon", "snake", "szerosuit", "sonic",
            "tantan", "toonlink", "trail", "pickel",
            "wario", "wiifit", "wolf", "younglink", "yoshi", 
            "zelda"
        ));
        excluded = new ArrayList<>();
    }
    public static CharHandler getInstance() {
        if (instance == null) instance = new CharHandler();
        
        return instance;
    }

    public String[] getIncluded() {
        String[] arr = new String[included.size()];
        arr = included.toArray(arr);
        return arr;
    }

    public String[] getExcluded() {
        String[] arr = new String[excluded.size()];
        arr = excluded.toArray(arr);
        return arr;
    }

    public void include(String... includeList) {
        for(String i : includeList) {
            if (excluded.contains(i) && !included.contains(i)) {
                excluded.remove(i);
                included.add(i);
            }    
        }
    }

    public void exclude(String... includeList) {
        for(String i : includeList) {
            if (included.contains(i) && !excluded.contains(i)) {
                included.remove(i);
                excluded.add(i);
            }    
        }
    }
}