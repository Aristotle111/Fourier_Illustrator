package com.fourierillustrator;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;


public class SecondaryController {

    @FXML private Pane mainPane;
    @FXML private ChoiceBox<String> sceneSelector2;
    @FXML private VBox sceneSelectorBox2;
    @FXML private Button changeSceneButton2;

    @FXML
    public void initialize() {
    }


    @FXML
    public void switchScene() {
        Main.switchScenes();
    }

}