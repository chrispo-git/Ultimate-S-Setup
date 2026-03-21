package com.example.ultssetup;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.scene.control.ToggleButton;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.geometry.Pos;
import javafx.scene.control.TabPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.control.Separator;
import javafx.geometry.Orientation;

import java.io.File;

/**
 * Write a description of JavaFX class Display here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Display extends Application
{
    private static Stage stage;
    /**
     * The start method is the main entry point for every JavaFX application. 
     * It is called after the init() method has returned and after 
     * the system is ready for the application to begin running.
     *
     * @param  stage the primary stage for this application.
     */
    @Override
    public void start(Stage stage) {
        this.stage = stage;
        // Create a grid pane to hold the toggle buttons

        // Create a scene and set it on the stage
        TabPane tabPane = new TabPane();



        System.out.println(System.getProperty("user.dir"));
        System.out.println(new File("resources/logo.png").isFile());
        Image title = new Image("file:resources/logo.png", 420, 100, false, false);
        Label ver = new Label("Ultimate S " + VersionGetter.getLocalVer().toString());
        ver.setPadding(new Insets(5, 5, 5, 5));
        CharacterPane charPane = new CharacterPane();
        BorderPane border = new BorderPane();
        
        VBox verBox = new VBox(new ImageView(title), ver, new Separator(Orientation.HORIZONTAL));
        border.setCenter(verBox);
        border.setBottom(charPane.getPane());
        Scene scene = new Scene(border);
        stage.setTitle("Ultimate S Setup Tool");
        stage.setScene(scene);
        stage.show();
        stage.resizableProperty().setValue(false);
        stage.setMinHeight(stage.getHeight());
        stage.setMinWidth(stage.getWidth());
        stage.setMaxHeight(stage.getHeight());
        stage.setMaxWidth(stage.getWidth());
        
        
        CheckLatest.checkLatestVersion();
    }
    public static Stage getStage() {
        return stage;
    }
    public static void main(String[] args) {
        launch(args);
    }
}
