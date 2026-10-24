package com.example.ultssetup;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.control.ToggleButton;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.geometry.Pos;
import javafx.scene.text.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.control.Separator;
import javafx.geometry.Orientation;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.concurrent.Task;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.Priority;
import atlantafx.base.theme.Styles;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import javafx.stage.DirectoryChooser;
import java.io.File;

/**
 * The main panel: fighter toggles, help text, and the dependency and install actions.
 */
public class CharacterPane
{
    private final CharHandler charHandler = CharHandler.getInstance();
    private static final double iconScale = 50.0;

    
    private boolean isLock = false;
    private static final int STEP_TIMEOUT_SECONDS = 30;
    private final ProgressBar progressBar = new ProgressBar();

    public CharacterPane()
    {
        
    }
    /**
     * Builds the panel's layout.
     */
    public Pane getPane() {
        GridPane pane = new GridPane();
        pane.setPadding(new Insets(10, 10, 10, 10));
        pane.setVgap(10);
        pane.setHgap(10);

        // Create 89 toggle buttons and add them to the grid
        String[] charactersList = {
            "mario", "donkey", "link", "samus", "samusd", "yoshi", "kirby", "fox", "pikachu", "luigi", "ness", "captain", "purin",
            "peach", "daisy", "koopa", "popo", "sheik", "zelda", "mariod", "pichu", "falco", "marth", "lucina", "younglink", "ganon",
            "mewtwo", "roy", "chrom", "gamewatch", "metaknight", "pit", "pitb", "szerosuit", "wario", "snake", "ike", "ptrainer", "diddy",
            "lucas", "sonic", "dedede", "pikmin", "lucario", "robot", "toonlink", "wolf", "murabito", "rockman", "wiifit", "rosetta", "littlemac",
            "gekkouga", "palutena", "pacman", "reflet", "shulk", "koopajr", "duckhunt", "ryu", "ken", "kamui", "cloud", "bayonetta", "inkling",
            "ridley", "simon", "richter", "krool", "shizue", "gaogaen", "packun", "jack", "brave", "buddy", "dolly", "master", "tantan",
            "pickel", "edge", "element", "demon", "trail", "miifighter", "miiswordsman", "miigunner"
        };
        for (int i = 0; i < 6*13; i++) {
            pane.add(createFighterToggle(charactersList[i]), i % 13, i / 13); // Arrange in a grid (13 columns)
        }
        for (int i = 6*13; i < charactersList.length; i++) {
            pane.add(createFighterToggle(charactersList[i]), 2+(i % 13), i / 13); // Arrange in a grid (13 columns)
        }
        
        Label statusText = new Label();
        statusText.getStyleClass().add("status-text");
        statusText.setMinWidth(0);
        statusText.setMaxWidth(Double.MAX_VALUE);
        // long errors get cut off with "...", so show the full text on hover
        Tooltip statusTip = new Tooltip();
        statusTip.textProperty().bind(statusText.textProperty());
        statusText.setTooltip(statusTip);

        Button updateButton = new Button("Update");
        updateButton.getStyleClass().add(Styles.ACCENT);
        updateButton.setAlignment(Pos.CENTER);
        updateButton.setOnAction(event -> updateChars(statusText));
        pane.add(updateButton, 7, 8);
        Button setOnButton = new Button("ALL On");
        setOnButton.setAlignment(Pos.CENTER);
        setOnButton.setOnAction(event -> setAllOn(pane.getChildren()));
        pane.add(setOnButton, 5, 8);
        Button setOffButton = new Button("ALL Off");
        setOffButton.setAlignment(Pos.CENTER);
        setOffButton.setOnAction(event -> setAllOff(pane.getChildren()));
        pane.add(setOffButton, 6, 8);
        
        BorderPane root = new BorderPane();
        root.setLeft(pane);
        
        Text chrNable = new Text("Enable and Disable Ult S Characters");
        chrNable.setFont(Font.font(null, FontWeight.BOLD, 18));
        Text help = new Text(
        "Choose to enable and disable Ultimate S custom movesets.\n"+
        "When you're done, press 'Update' to update the install with \nyour new settings.\n"
        );
        Text depend = new Text("Update Dependencies");
        depend.setFont(Font.font(null, FontWeight.BOLD, 18));
        Text dependHelp = new Text(
        "Automatically grabs the latest versions of dependencies.\n"+
        "Requires an internet connection!\n"
        );
        Text install = new Text("Install Ultimate S To Folder");
        install.setFont(Font.font(null, FontWeight.BOLD, 18));
        Text installHelp = new Text(
        "Automatically installs Ultimate S to a given folder.\n"+
        "If on switch, this is your SD Card's root.\n" +
        "If on emulator, this is the sdmc folder.\n"
        );
        VBox rightHelp = new VBox(chrNable, help, depend, dependHelp, install, installHelp);
        rightHelp.setPadding(new Insets(10, 10, 10, 10));
        root.setRight(rightHelp);
        root.setCenter(new Separator(Orientation.VERTICAL));
        
        Button dependenciesButton = new Button("Update Dependencies");
        dependenciesButton.setOnAction(event -> grabDependencies(statusText));

        Button installerButton = new Button("Install Ultimate S To Folder");
        installerButton.getStyleClass().add(Styles.ACCENT);
        installerButton.setOnAction(event -> installToFolder(statusText));

        progressBar.getStyleClass().add(Styles.SMALL);
        progressBar.setPrefWidth(160);
        progressBar.setVisible(false);

        HBox.setHgrow(statusText, Priority.ALWAYS);
        HBox bottomPane = new HBox(dependenciesButton, installerButton, statusText, progressBar);
        bottomPane.getStyleClass().add("status-bar");
        bottomPane.setAlignment(Pos.CENTER_LEFT);
        root.setBottom(bottomPane);
        
        return root;
    }
    /**
     * Creates an enabled toggle button for one fighter.
     */
    private ToggleButton createFighterToggle(String fighter) {
        ToggleButton toggleButton = new ToggleButton();
        toggleButton.getStyleClass().add("fighter-toggle");
        try {
            Image icon = new Image("file:resources/on_icon/"+fighter+".png", iconScale, iconScale, false, false);
            toggleButton.setGraphic(new ImageView(icon));
        } catch(IllegalArgumentException e) {
            System.out.println("resources/on_icon/"+fighter+".png not found");
        }
        toggleButton.setSelected(true);
        toggleButton.setOnAction(event -> handleToggle(fighter, toggleButton.isSelected(), toggleButton));
        return toggleButton;
    }

    /**
     * Enables or disables a fighter and swaps its icon to match.
     */
    private void handleToggle(String fighter, boolean isSelected, ToggleButton btn) {
        Image icon;
        if (isSelected) {
            charHandler.include(fighter);
            icon = new Image("file:resources/on_icon/"+fighter+".png", iconScale, iconScale, false, false);
        } else {
            charHandler.exclude(fighter);
            icon = new Image("file:resources/off_icon/"+fighter+".png", iconScale, iconScale, false, false);
        }
        btn.setGraphic(new ImageView(icon));
    }

    /**
     * Enables every fighter toggle in the list.
     */
    private void setAllOn(ObservableList<Node> buttons) {
        for(Node i : buttons) {
            if (!(i instanceof ToggleButton)) continue;
            ToggleButton toggle = (ToggleButton) i;
            if (!toggle.isSelected()) toggle.fire();
        }
    }

    /**
     * Disables every fighter toggle in the list.
     */
    private void setAllOff(ObservableList<Node> buttons) {
        for(Node i : buttons) {
            if (!(i instanceof ToggleButton)) continue;
            ToggleButton toggle = (ToggleButton) i;
            if (toggle.isSelected()) toggle.fire();
        }
    }
    
    /**
     * One step of the dependency download.
     */
    private interface DependencyStep {
        void run() throws IOException, URISyntaxException;
    }

    /**
     * Downloads all dependencies in the background, with a timeout on each step.
     */
    private void grabDependencies(Label lbl) {
        Task<Void> task = new Task<>() {
            private final ExecutorService stepExecutor = Executors.newSingleThreadExecutor(r -> {
                Thread t = new Thread(r);
                t.setDaemon(true);
                return t;
            });
            private final int totalSteps = 9;
            private int stepsDone = 0;

            /**
             * Runs one step with a timeout. On failure, reports which step failed and returns false.
             */
            private boolean runStep(String name, DependencyStep step, String doneMsg) {
                Future<?> future = stepExecutor.submit(() -> { step.run(); return null; });
                try {
                    future.get(STEP_TIMEOUT_SECONDS, TimeUnit.SECONDS);
                    updateMessage(doneMsg);
                    updateProgress(++stepsDone, totalSteps);
                    return true;
                } catch (TimeoutException e) {
                    future.cancel(true);
                    updateMessage("Timed out after " + STEP_TIMEOUT_SECONDS + "s while " + name);
                } catch (ExecutionException e) {
                    updateMessage("Error while " + name + "! " + e.getCause());
                } catch (InterruptedException e) {
                    future.cancel(true);
                    Thread.currentThread().interrupt();
                    updateMessage("Cancelled while " + name);
                }
                return false;
            }

            @Override public Void call() {
                if(isLock) return null;
                isLock = true;
                try {
                    updateMessage("Setting Up Folders...");
                    boolean ok = runStep("setting up folders", () -> {
                                DependencyDownload.emptyFolders();
                                DependencyDownload.setupFolders();
                            }, "Folders Set Up!")
                        && runStep("installing Ozone", DependencyDownload::getOzone, "Ozone Installed!")
                        && runStep("installing Arcropolis", DependencyDownload::getArcropolis, "Arcropolis Installed!")
                        && runStep("installing NRO Hook", DependencyDownload::getNROHook, "NRO Hook Installed!")
                        && runStep("installing Smashline Plugin", DependencyDownload::getSmashlineHook, "Smashline Plugin Installed!")
                        && runStep("installing Param Config Plugin", DependencyDownload::getParamConfigHook, "Param Config Plugin Installed!")
                        && runStep("installing Stage Config Plugin", DependencyDownload::getStageConfigHook, "Stage Config Plugin Installed!")
                        && runStep("installing One Slot Effects Plugin", DependencyDownload::getOSE, "One Slot Effects Plugin Installed!")
                        && runStep("installing CSK Collection Plugin", DependencyDownload::getCSK, "CSK Collection Plugin Installed!");
                    if (ok) updateMessage("Finished grabbing dependencies.");
                } finally {
                    stepExecutor.shutdownNow();
                    isLock = false;
                }
                return null;
            }
        };
        if(isLock) return;
        trackTask(task, lbl);
        Thread dependencyTask = new Thread(task);
        dependencyTask.setDaemon(true);
        dependencyTask.start();
    }
    
    /**
     * Shows a task's messages in the status label, and its progress in the bar while it runs.
     */
    private void trackTask(Task<?> task, Label lbl) {
        lbl.textProperty().bind(task.messageProperty());
        progressBar.progressProperty().bind(task.progressProperty());
        progressBar.visibleProperty().bind(task.runningProperty());
    }

    /**
     * Saves the fighter selection to the mod files in the background.
     */
    private void updateChars(Label lbl) {
        Task<Void> task = new Task<>() {
            @Override public Void call() {
                if(isLock) return null;
                isLock = true;
                updateMessage("Saving settings...");
                CharEnabler.update();
                updateMessage("Finished saving settings."); 
                isLock = false;
                return null;
            }
        };
        if(isLock) return;
        trackTask(task, lbl);
        new Thread(task).start();
    }
    
    /**
     * Asks for an install folder, then installs Ultimate S there in the background.
     */
    private void installToFolder(Label lbl) {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Choose install directory (root of SD Card / sdmc folder)");
        File selectedDirectory = directoryChooser.showDialog(Display.getStage());
        if (selectedDirectory == null) return; // user cancelled the chooser
        Task<Void> task = new Task<>() {
            @Override public Void call() {
                if(isLock) return null;
                isLock = true;
                String[] current = {"starting install"};
                try {
                    updateMessage("Installing to folder...");
                    FolderSetup.setup(selectedDirectory.getPath(), msg -> {
                        current[0] = msg;
                        updateMessage(msg);
                    });
                    updateMessage("Finished installing.");
                } catch(IOException | RuntimeException e) {
                    updateMessage("Error installing to folder! Stopped at: " + current[0] + " (" + e + ")");
                } finally {
                    isLock = false;
                }
                return null;
            }
        };
        if(isLock) return;
        trackTask(task, lbl);
        new Thread(task).start();
    }
    
}
