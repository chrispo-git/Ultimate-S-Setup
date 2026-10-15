package com.example.ultssetup;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.control.Separator;
import javafx.geometry.Orientation;

import java.io.File;

public class Display extends Application
{
    private static Stage stage;

    @Override
    public void start(Stage stage) {
        this.stage = stage;


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
