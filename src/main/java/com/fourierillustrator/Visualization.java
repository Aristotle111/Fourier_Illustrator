package com.fourierillustrator;

import java.util.Arrays;
import java.util.LinkedList;

import javafx.animation.AnimationTimer;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polyline;
//

public class Visualization {
    Pane pane;
    Polyline pl;
    LinkedList<Epicycle> epicycles;
    VisualLogic animation;

    //class instance config
    double centerX = 450;
    double centerY = 450;
    
    //user customizable variables
    boolean showCircles = true;
    Color strokeColor = Color.BLACK;

    public Visualization(Pane pane) {
        this.pane = pane;
        this.epicycles = new LinkedList<Epicycle>();
        animation = new VisualLogic();
        
        pl = new Polyline();
        pl.setStrokeDashOffset(1);
        pane.getChildren().addAll(pl);
    }

    public Visualization(Pane pane, Epicycle[] epicycles) {
        this.pane = pane;
        this.epicycles = new LinkedList<Epicycle>(Arrays.asList(epicycles));
        animation = new VisualLogic();
        
        pl = new Polyline();
        pl.setStrokeDashOffset(100); pl.setStroke(Color.RED);
        pane.getChildren().addAll(pl);

        for (Epicycle e : epicycles) {
            pane.getChildren().addAll(e.arrowShaft, e.circle);
        }
    }

    public Boolean start() {
        if (epicycles.size() == 0) return false;
        
        animation.start();
        return true;
        
    }

    public void updateEpicycles(double seconds) {
        epicycles.get(0).update(seconds, centerX, centerY, showCircles);

        for (int i = 1; i < epicycles.size(); i++) {     
            epicycles.get(i).update(seconds, epicycles.get(i - 1).endX, epicycles.get(i - 1).endY, showCircles);
        }
    }

    public void pause() {
        animation.stop();
        animation.isPaused = true;
        animation.lastUpdate = System.nanoTime();
    }

    private class VisualLogic extends AnimationTimer {
        final double DELTA_T = 100000; //nanoseconds, sample frequency = 1000000000/delta_t 
        long lastUpdate = 0;
        long startTime = -1;
        //double startX;
        //double startY;
        long currentTime;
        boolean isPaused = false;
        

        @Override
        public void handle(long now) {
            if (isPaused) {
                isPaused = false;
                startTime += System.nanoTime() - lastUpdate;
            }
            
            if (startTime < 0) startTime = now;

            currentTime = now - startTime;
            long elapsed = now - lastUpdate;

            if (elapsed <= DELTA_T) return;
            lastUpdate = currentTime;

            double seconds = (double) currentTime / 1_000_000_000.0;
                
            updateEpicycles(seconds);
                
            pl.getPoints().addAll(new Double[]{
            epicycles.get(epicycles.size() - 1).endX, epicycles.get(epicycles.size() - 1).endY  // New Point 4 (x, y)
            });
                
        }
    }

    public void setShowCircles(Boolean showCircles) {
        this.showCircles = showCircles;
    }

    public void setStrokeColor(Color color) {
        strokeColor = color;
    }
}




