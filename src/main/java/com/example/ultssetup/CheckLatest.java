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
public class CheckLatest
{
    // instance variables - replace the example below with your own
    private int x;

    /**
     * Constructor for objects of class CheckLatest
     */
    private CheckLatest()
    {
        // initialise instance variables
        x = 0;
    }

    /**
     * Checks if we have the latest version of Ultimate S and directs us to the github if we don't!
     */
    public static void checkLatestVersion()
    {
        Version localVer = VersionGetter.getLocalVer();
        Version remoteVer = VersionGetter.getRemoteVer();
        if (!remoteVer.greaterThan(localVer)) return;
        
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setContentText("There is a new version of Ultimate S (" + remoteVer.toString() + ") available.\nGo to downloads page?");
        a.setHeaderText("New Version Available!");
        a.setTitle("Ultimate S " + remoteVer.toString() + " available!");
        a.getDialogPane().getButtonTypes().setAll(
            ButtonType.YES, 
            ButtonType.NO
        );
        Optional<ButtonType> result = a.showAndWait();
        if (result.get() == ButtonType.YES && Desktop.isDesktopSupported()) {
            Desktop desktop = Desktop.getDesktop();
            try {
                URI uri = new URI("https://github.com/chrispo-git/ult-s/releases/latest");
                desktop.browse(uri);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (URISyntaxException  e) {
                e.printStackTrace();
            }
        }
    }
}
