package com.example.ultssetup;
import javafx.scene.control.Alert;
import java.awt.Desktop;
import java.net.URI;
import javafx.scene.control.ButtonType;
import java.util.Optional;
import java.io.IOException;
import java.net.URISyntaxException;

public class CheckLatest
{
    private CheckLatest()
    {
    }

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
