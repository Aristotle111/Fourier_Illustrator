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
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

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
    @FXML private ImageView audioOnImage;
    @FXML private ImageView audioOffImage;

    private DrawingVisualization drawingVisualization;
    
    @FXML
    private void initialize() {
        drawingVisualization = new DrawingVisualization(mainPane);
        Rectangle clipRect = new Rectangle();
        clipRect.widthProperty().bind(mainPane.widthProperty());
        clipRect.heightProperty().bind(mainPane.heightProperty());
        mainPane.setClip(clipRect);
        
        handleEpicyclesToggled();
        handleDrawingSpeedChanged();

        playButton.setDisable(true);
        pauseButton.setDisable(true);
        resetButton.setDisable(true);

        mainPane.addEventHandler(MouseEvent.MOUSE_PRESSED, eh -> {
            drawingVisualization.clear();
            handleDrawingSpeedChanged();
        });

        mainPane.addEventHandler(MouseEvent.MOUSE_DRAGGED, eh -> {
            showStrokeToggle.setSelected(false);
            showStrokeToggle.setText("Hide Original Stroke");
            handleStrokeToggle();
            playButton.setDisable(true);
            pauseButton.setDisable(false);
            resetButton.setDisable(false);
            drawingSpeedSlider.setDisable(true);
            pointDensitySlider.setDisable(true);
            epicycleCount.setText(String.valueOf((drawingVisualization.getDrawingVisual().getPoints().size())/2));
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
            showStrokeToggle.setSelected(false);
            showStrokeToggle.setText("Hide Original Stroke");
            epicycleCount.setText("NONE");
            drawingVisualization.clear();
            handleDrawingSpeedChanged();
            handlePointDensityChanged();
            handleDrawingSpeedChanged();
            handleColorChanged();
            handleStrokeSizeChanged();
            handleOpacityChanged();
        });

        playButton.setOnAction(eh -> {
            drawingVisualization.getV().start();
            playButton.setDisable(true);
            pauseButton.setDisable(false);
        });

        pauseButton.setOnAction(eh -> {
            drawingVisualization.getV().pause();
            pauseButton.setDisable(true);
            playButton.setDisable(false);
        });
    }

    /**
     * Switches to scene 2
     */
    @FXML
    private void switchScene() {
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
        drawingVisualization.clear();
        handleDrawingSpeedChanged();
        handlePointDensityChanged();
        handleDrawingSpeedChanged();
    }

    /**
     * Toggles the stroke visibility of the original epicycle drawing
     */
    @FXML
    private void handleStrokeToggle() {
        if (showStrokeToggle.isSelected()) {
            drawingVisualization.getDrawingVisual().setOpacity(0);
            showStrokeToggle.setText("Show Original Stroke");
            return;
        }
        drawingVisualization.getDrawingVisual().setOpacity(1);
        showStrokeToggle.setText("Hide Original Stroke");
    }

    /**
     * Toggles the epicycle circle visibility of the epicycle drawing
     */
    @FXML
    private void handleEpicyclesToggled() {
        if (hideEpicyclesToggleButton.isSelected()) {
            drawingVisualization.setShowCircles(false);
            hideEpicyclesToggleButton.setText("Show Epicycles");
            return;
        }
        drawingVisualization.setShowCircles(true);
        hideEpicyclesToggleButton.setText("Hide Epicycles");
    }

    /**
     * Updates the stroke size
     */
    @FXML
    private void handleStrokeSizeChanged() {
        String size = ((RadioButton) strokeSizeGroup.getSelectedToggle()).getText();
        switch (size) {
            case "Small":
                drawingVisualization.getV().getPl().setStrokeWidth(2);
                break;
            case "Medium":
                drawingVisualization.getV().getPl().setStrokeWidth(5);
                break;
            case "Large":
                drawingVisualization.getV().getPl().setStrokeWidth(10);
                break;
        }
    }

    /**
     * Updates the drawing speed
     */
    @FXML 
    private void handleDrawingSpeedChanged() {
        double speed = drawingSpeedSlider.getValue();
        drawingVisualization.setMultiplier(speed);
    }

    /**
     * Updates the drawing point density
     */
    @FXML 
    private void handlePointDensityChanged() {
        double density = pointDensitySlider.getValue();
        drawingVisualization.setDT(200000000 - (density * 1.5));
    }

    /**
     * Updates the drawing opacity
     */
    @FXML 
    private void handleOpacityChanged() {
        double opacity = opacitySlider.getValue();
        drawingVisualization.getV().getPl().setOpacity(opacity);
    }

    /**
     * Updates the drawing color
     */
    @FXML 
    private void handleColorChanged() {
        Color color = colorSelector.getValue();
        drawingVisualization.getV().getPl().setStroke(color);
    }
}
