package com.fourierillustrator;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.Pane;

import com.fourierillustrator.DrawingVisualization;

public class PrimaryController {

    @FXML private Pane mainPane;
    @FXML private ToggleButton showStrokeToggle;
    @FXML private RadioButton strokeSizeSmall;
    @FXML private RadioButton strokeSizeMedium;
    @FXML private RadioButton strokeSizeLarge;
    @FXML private ChoiceBox<String> sceneSelector;

    @FXML
    public void initialize() {
        DrawingVisualization dv = new DrawingVisualization(mainPane);
        dv.setMultiplier(.5);

        ObservableList<String> choices = FXCollections.observableArrayList("Scene 1", "Scene 2");
            sceneSelector.setItems(choices);
            sceneSelector.getSelectionModel().selectFirst();
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