package com.fourierillustrator;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Slider;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
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

    private DrawingVisualization dv;
    

    @FXML
    public void initialize() {
        dv = new DrawingVisualization(mainPane);
        handleEpicyclesToggled();
        handleDrawingSpeedChanged();

        playButton.setDisable(true);
        pauseButton.setDisable(true);
        resetButton.setDisable(true);

        mainPane.addEventHandler(MouseEvent.MOUSE_DRAGGED, eh -> {
            playButton.setDisable(false);
            pauseButton.setDisable(false);
            resetButton.setDisable(false);
            drawingSpeedSlider.setDisable(true);
        });

        resetButton.setOnAction(eh -> {
            resetButton.setDisable(true);
            playButton.setDisable(true);
            pauseButton.setDisable(true);
            drawingSpeedSlider.setDisable(false);
            dv.clear();
        });
    }

    @FXML
    public void switchScene() {
        Main.switchScenes();
    }

    @FXML
    public void handleStrokeToggle() {
        if (showStrokeToggle.isSelected()) {
            dv.drawingVisual.setOpacity(0);
            showStrokeToggle.setText("Show Original Stroke");
            return;
        }
        dv.drawingVisual.setOpacity(1);
        showStrokeToggle.setText("Hide Original Stroke");
    }

    @FXML
    public void handleEpicyclesToggled() {
        if (hideEpicyclesToggleButton.isSelected()) {
            dv.setShowCircles(false);
            hideEpicyclesToggleButton.setText("Show Epicycles");
            return;
        }
        dv.setShowCircles(true);
        hideEpicyclesToggleButton.setText("Hide Epicycles");
    }

    @FXML
    public void handleStrokeSizeChanged() {
        String size = ((ToggleButton) strokeSizeGroup.getSelectedToggle()).getText();
        switch (size) {
            case "Small":
                dv.v.pl.setStrokeWidth(2);
                break;
            case "Medium":
                dv.v.pl.setStrokeWidth(4);
                break;
            case "Large":
                dv.v.pl.setStrokeWidth(7);
                break;
        }
    }

    @FXML 
    public void handleDrawingSpeedChanged() {
        double speed = drawingSpeedSlider.getValue();
        dv.setMultiplier(speed);
    }

    @FXML 
    public void handlePointDensityChanged() {
        double density = pointDensitySlider.getValue();
        //set point density
    }

    @FXML 
    public void handleOpacityChanged() {
        double opacity = opacitySlider.getValue();
        dv.v.pl.setOpacity(opacity);
    }

    @FXML 
    public void handleColorChanged() {
        Color color = colorSelector.getValue();
        dv.v.pl.setStroke(color);
    }
}