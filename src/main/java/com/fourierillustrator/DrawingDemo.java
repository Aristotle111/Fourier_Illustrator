package com.fourierillustrator;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Polyline;
import javafx.stage.Stage;

//CUSTOM IMPORTS
import org.jtransforms.fft.DoubleFFT_1D;


public class DrawingDemo extends Application {
    public static void main(String[] args) {
        launch(args);
    }
    
    @Override
    public void start(Stage stage) {
        Pane canvas = new Pane();
        /* 
        DrawingLogic dl = new DrawingLogic();
        canvas.setOnMouseDragged(event -> dl.update(event));
        canvas.setOnMousePressed(event -> dl.start(event));
        canvas.setOnMouseReleased(event -> dl.stop(event));
        */
        DrawingVisualization dv = new DrawingVisualization(canvas);
        dv.setMultiplier(.5);
        Scene scene = new Scene(canvas, 900, 900);
        stage.setScene(scene);
        stage.show();
    }
}


