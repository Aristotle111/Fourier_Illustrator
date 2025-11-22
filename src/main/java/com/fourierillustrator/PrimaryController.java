package com.fourierillustrator;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Slider;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

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
    @FXML private ToggleGroup strokeSizeGroup;
    @FXML private Slider drawingSpeedSlider;
    @FXML private Slider pointDensitySlider;
    @FXML private Slider opacitySlider;
    @FXML private ToggleButton hideEpicyclesToggleButton;
    @FXML private ColorPicker colorSelector;

    DrawingVisualization dv = new DrawingVisualization(mainPane);

    @FXML
    public void initialize() {
        handleDrawingSpeed();

        playButton.setDisable(true);
        pauseButton.setDisable(true);
        resetButton.setDisable(true);
    }

    @FXML
    public void switchScene() {
        Main.switchScenes();
    }

    @FXML
    public void handleStrokeToggle() {
        
    }

    @FXML
    public void handleEpicyclesToggled() {
        boolean hide = hideEpicyclesToggleButton.isSelected();
        dv.setShowCircles(!hide);
    }

    @FXML
    public void handleStrokeSize() {
        String size = strokeSizeGroup.getSelectedToggle().toString();
        switch (size) {
            case "Small":
                //set stroke size to small
                break;
            case "Medium":
                //set stroke size to medium
                break;
            case "Large":
                //set stroke size to large
                break;
        }
    }

    @FXML 
    public void handleDrawingSpeed() {
        double speed = drawingSpeedSlider.getValue();
        dv.setMultiplier(speed);
    }

    @FXML 
    public void handlePointDensity() {
        double density = pointDensitySlider.getValue();
        //set point density
    }

    @FXML 
    public void handleOpacityChanged() {
        double opacity = opacitySlider.getValue();
        //set opacity
    }

    @FXML 
    public void handleColorChange() {
        Color color = colorSelector.getValue();
        //set color
    }
}