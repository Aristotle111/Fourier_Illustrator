package com.fourierillustrator.Controller;

import java.io.IOException;

import com.fourierillustrator.Main.Main;
import com.fourierillustrator.Model.DrawingVisualization;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Slider;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class PrimaryController {

    @FXML private BorderPane scene1BorderPane;
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
    @FXML private Label epicycleCount;

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
            playButton.setDisable(true);
            pauseButton.setDisable(false);
            resetButton.setDisable(false);
            drawingSpeedSlider.setDisable(true);
            pointDensitySlider.setDisable(true);
            epicycleCount.setText(String.valueOf((dv.getDrawingVisual().getPoints().size())/2));
            handleColorChanged();
            handleStrokeSizeChanged();
            handleOpacityChanged();
        });

        resetButton.setOnAction(eh -> {
            resetButton.setDisable(true);
            playButton.setDisable(true);
            pauseButton.setDisable(true);
            drawingSpeedSlider.setDisable(false);
            pointDensitySlider.setDisable(false);
            epicycleCount.setText("NONE");
            dv.clear();
            handlePointDensityChanged();
            handleDrawingSpeedChanged();
            handleColorChanged();
            handleStrokeSizeChanged();
            handleOpacityChanged();
        });

        playButton.setOnAction(eh -> {
            dv.getV().start();
            playButton.setDisable(true);
            pauseButton.setDisable(false);
        });

        pauseButton.setOnAction(eh -> {
            dv.getV().pause();
            pauseButton.setDisable(true);
            playButton.setDisable(false);
        });
    }

    @FXML
    public void switchScene() {
        try {
            Main.setScene2();
        } catch (IOException e) {
            e.printStackTrace();
        }
        resetButton.setDisable(true);
        playButton.setDisable(true);
        pauseButton.setDisable(true);
        drawingSpeedSlider.setDisable(false);
        pointDensitySlider.setDisable(false);
        epicycleCount.setText("NONE");
        dv.clear();
        handlePointDensityChanged();
        handleDrawingSpeedChanged();
    }

    

    @FXML
    public void handleStrokeToggle() {
        if (showStrokeToggle.isSelected()) {
            dv.getDrawingVisual().setOpacity(0);
            showStrokeToggle.setText("Show Original Stroke");
            return;
        }
        dv.getDrawingVisual().setOpacity(1);
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
        String size = ((RadioButton) strokeSizeGroup.getSelectedToggle()).getText();
        switch (size) {
            case "Small":
                dv.getV().getPl().setStrokeWidth(2);
                break;
            case "Medium":
                dv.getV().getPl().setStrokeWidth(5);
                break;
            case "Large":
                dv.getV().getPl().setStrokeWidth(10);
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
        dv.setDT(200000000 - (density * 1.5));
    }

    @FXML 
    public void handleOpacityChanged() {
        double opacity = opacitySlider.getValue();
        dv.getV().getPl().setOpacity(opacity);
    }

    @FXML 
    public void handleColorChanged() {
        Color color = colorSelector.getValue();
        dv.getV().getPl().setStroke(color);
    }
}