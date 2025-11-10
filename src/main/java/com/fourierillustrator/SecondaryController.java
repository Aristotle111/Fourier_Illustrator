package com.fourierillustrator;

import javafx.fxml.FXML;
import javafx.scene.layout.Pane;

import com.fourierillustrator.DrawingVisualization;

public class SecondaryController {

    @FXML
    private Pane mainPane;

    @FXML
    public void initialize() {
        
        
    }

    @FXML
    public void switchScene() {
        Main.switchScenes();
    }

}