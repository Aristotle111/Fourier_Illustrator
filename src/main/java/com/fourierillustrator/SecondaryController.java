package com.fourierillustrator;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Slider;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;


public class SecondaryController {

    @FXML private Pane mainPane;
    @FXML private ChoiceBox<String> sceneSelector2;
    @FXML private VBox sceneSelectorBox2;
    @FXML private Button changeSceneButton2;
    @FXML private Button addTabButton;
    @FXML private Button addEpicycleButton;
    @FXML private TabPane epicycleTabPane;
    @FXML private ColorPicker epicycleStrokeColorPicker;
    @FXML private RadioButton epicycleStrokeSmall;
    @FXML private RadioButton epicycleStrokeMedium;
    @FXML private RadioButton epicycleStrokeLarge;
    @FXML private ToggleGroup epicycleStrokeToggleGroup;
    @FXML private Slider epicycleStrokeOpacitySlider;

    private int tabCount = 2;

    @FXML
    public void initialize() {
    }


    @FXML
    public void switchScene() {
        Main.switchScenes();
    }

    @FXML
    public void handleNewTab() {
        Tab newTab = new Tab("Epicycles " + tabCount++);
        epicycleTabPane.getTabs().add(newTab);
    }
    
    @FXML
    public void handleEpicycleStrokeSizeChanged() {
        String size = ((RadioButton) epicycleStrokeToggleGroup.getSelectedToggle()).getText();
        switch (size) {
            case "Small":
                // Logic to update stroke size to small in the selected tab
                break;
            case "Medium":
                // Logic to update stroke size to medium in the selected tab
                break;
            case "Large":
                // Logic to update stroke size to large in the selected tab
                break;
        }
    }

    @FXML
    public void handleEpicycleStrokeOpacityChanged() {
        double opacity = epicycleStrokeOpacitySlider.getValue();
        // Logic to update the opacity of the epicycles in the selected tab
    }

    @FXML
    public void handleEpicycleStrokeColorChanged() {
        Color selectedColor = epicycleStrokeColorPicker.getValue();
        // Logic to update the color of the epicycles in the selected tab
    }

    @FXML
    public void handleNewEpicycle() {
        Tab selectedTab = epicycleTabPane.getSelectionModel().getSelectedItem();
        if (selectedTab != null) {
            // Logic to add an epicycle to the selected tab
        }
    }
}