package com.example.ultssetup;
import javafx.scene.control.Alert;
import java.awt.Desktop;
import java.net.URI;
import javafx.scene.control.ButtonType;
import java.util.Optional;
import java.io.IOException;
import java.net.URISyntaxException;

/**
 * Write a description of class CheckLatest here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class ExceptionAlert
{
    // instance variables - replace the example below with your own
    private int x;

    /**
     * Constructor for objects of class CheckLatest
     */
    private ExceptionAlert()
    {
    }

    /**
     * Checks if we have the latest version of Ultimate S and directs us to the github if we don't!
     */
    public static void alert(String error)
    {
        Version localVer = VersionGetter.getLocalVer();
        Version remoteVer = VersionGetter.getRemoteVer();
        if (!remoteVer.greaterThan(localVer)) return;
        
        Alert a = new Alert(Alert.AlertType.ERROR);
        a.setContentText("There seems to be an error with: " + error + ".\nIf this persists, refer to help channel in the Ult S discord");
        a.setHeaderText("Error!");
        a.setTitle("Error");
        Optional<ButtonType> result = a.showAndWait();
    }
}
