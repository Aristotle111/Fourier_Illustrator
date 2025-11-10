package com.fourierillustrator;

import javafx.fxml.FXML;
import javafx.scene.layout.Pane;

import com.fourierillustrator.DrawingVisualization;

public class PrimaryController {

    @FXML
    private Pane mainPane;

    @FXML
    public void initialize() {
        DrawingVisualization dv = new DrawingVisualization(mainPane);
        dv.setMultiplier(.5);
    }

    @FXML
    public void switchScene() {
        Main.switchScenes();
    }

}