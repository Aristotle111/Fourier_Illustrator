package com.fourierillustrator;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
//CREATE TWO SEPERATE FILES
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
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

       pl = new Polyline();

        group = new Epicycle[]{new Epicycle(70, 2 * Math.PI), new Epicycle(50, Math.PI)};
        Pane root = new Pane();
        for (Epicycle e : group) {
            root.getChildren().add(e.arrowShaft);
        }
        root.getChildren().add(pl);
        Scene scene = new Scene(root, 800, 800);
        stage.setScene(scene);
        stage.show();
        
        Animation a = new Animation();
        a.start();
    }
    
    class Animation extends AnimationTimer {
            @Override
            public void handle(long now) {
                double seconds = (double) now / 1_000_000_000.0;
                
                for (int i = 0; i < group.length; i++) {
                    if (i == 0) {
                        group[i].update(seconds, 450, 450);
                        continue;
                    }
                    
                    group[i].update(seconds, group[i-1].endX, group[i-1].endY);
                }
                
                pl.getPoints().addAll(new Double[]{
                group[group.length - 1].endX, group[group.length - 1].endY  // New Point 4 (x, y)
             });
                
        }
    }
    
    class Epicycle {
        double startX;
        double endX;
        double startY;
        double endY;
        double radius;
        double omega;
        Line arrowShaft;
        
        public Epicycle(double radius, double omega) {
            this.radius = radius;
            this.omega = omega;
            arrowShaft = new Line(0, 0, 0, 0);
            arrowShaft.setStrokeWidth(3);
        }
        
        void update(double seconds, double newStartX, double newStartY) {
            this.startX = newStartX;
            this.startY = newStartY;
            this.endX = radius * Math.cos(omega * seconds) + startX;
            this.endY = radius * Math.sin(omega * seconds) + startY;
            
            arrowShaft.setStartX(startX);
            arrowShaft.setStartY(startY);
            arrowShaft.setEndX(endX);
            arrowShaft.setEndY(endY);
        }
    }
}

