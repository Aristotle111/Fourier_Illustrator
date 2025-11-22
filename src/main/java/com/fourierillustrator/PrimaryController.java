package com.fourierillustrator;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class PrimaryController {

    @FXML private Button changeSceneButton1;
    @FXML private Pane mainPane;
    @FXML private ToggleButton showStrokeToggle;
    @FXML private RadioButton strokeSizeSmall;
    @FXML private RadioButton strokeSizeMedium;
    @FXML private RadioButton strokeSizeLarge;
    @FXML private ChoiceBox<String> sceneSelector;
    @FXML private Button playButton;
    @FXML private Button pauseButton;
    @FXML private Button resetButton;
    @FXML private VBox sceneSelectorBox1;

    @FXML
    public void initialize() {
        DrawingVisualization dv = new DrawingVisualization(mainPane);
        dv.setMultiplier(.5);
    }



    @FXML
    public void switchScene() {
        Main.switchScenes();
    }

    @FXML
    public void handleStrokeToggle() {
        
    }

    @FXML
    public void handleStrokeSize() {
        
    }

}