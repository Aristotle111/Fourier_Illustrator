package com.fourierillustrator.Demo;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
//CREATE TWO SEPERATE FILES

import com.fourierillustrator.Model.Epicycle;
import com.fourierillustrator.Model.Visualization;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Polyline;
import javafx.stage.Stage;

/**
 *
 * @author 6314396
 */
public class EpicycleDemo extends Application {
    Polyline pl;
    Epicycle[] group;
            
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
       launch(args);
    }
    
    @Override
    public void start(Stage stage) {

        group = new Epicycle[]{new Epicycle(150, Math.PI), new Epicycle(50, 2 * Math.PI)};
        
        Pane pane = new Pane();
        Visualization v = new Visualization(pane, group);
        Button start = new Button("Start");
        Button stop = new Button("stop");
        start.setOnAction(e -> v.start());
        stop.setOnAction(e -> v.pause());

        VBox root = new VBox(pane, start, stop);
        Scene scene = new Scene(root, 900, 900);
        stage.setScene(scene);      
        stage.show();
    }
}

