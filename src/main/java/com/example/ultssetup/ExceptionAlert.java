package com.example.ultssetup;
import javafx.scene.control.Alert;

/**
 * Shows error dialogs to the user.
 */
public class ExceptionAlert
{
    private ExceptionAlert()
    {
    }

    /**
     * Shows an error dialog for the given problem, pointing the user to the Ult S Discord.
     */
    public static void alert(String error)
    {
        Alert a = new Alert(Alert.AlertType.ERROR);
        a.setContentText("There seems to be an error with: " + error + ".\nIf this persists, refer to help channel in the Ult S discord");
        a.setHeaderText("Error!");
        a.setTitle("Error");
        a.showAndWait();
    }
}
