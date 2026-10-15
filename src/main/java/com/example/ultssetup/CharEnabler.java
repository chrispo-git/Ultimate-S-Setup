package com.example.ultssetup;
/**
 * Applies the current fighter selection to the installed mod files.
 */
public class CharEnabler
{
    /**
     * Constructor for objects of class CharEnabler
     */
    private CharEnabler()
    {
    }

    /**
     * Updates the characters
     */
    public static void update()
    {
        CharHandler characters = CharHandler.getInstance();
        //FighterParamHandler.updateFighterParam(characters.getExcluded());
        //ConfigParamHandler.updateConfigParam(characters.getExcluded());
        //FlagHandler.updateFlags(characters.getIncluded());
        RomfsHandler.updateRomfs(characters.getIncluded(), characters.getExcluded());
    }
}
