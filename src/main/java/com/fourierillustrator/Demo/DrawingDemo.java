package com.fourierillustrator.Demo;

import com.fourierillustrator.Model.DrawingVisualization;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class DrawingDemo extends Application {
    public static void main(String[] args) {
        launch(args);
    }
    
    @Override
    public void start(Stage stage) {
        Pane canvas = new Pane();
        DrawingVisualization dv = new DrawingVisualization(canvas);
        dv.setMultiplier(0.5);
        Scene scene = new Scene(canvas, 900, 900);
        stage.setScene(scene);
        stage.show();

        dv.setShowCircles(true);
    }
}


