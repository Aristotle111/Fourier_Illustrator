package com.fourierillustrator;

import java.util.Arrays;
import java.util.LinkedList;

import javafx.animation.AnimationTimer;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polyline;

public class Visualization {
    Pane pane;
    Polyline pl;
    LinkedList<Epicycle> epicycles;
    VisualLogic animation;
    double multiplier = 1;
    double period;
    double periodProgress = 0;

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
        pl.setStroke(Color.RED);
        pane.getChildren().addAll(pl);
    }

    public Visualization(Pane pane, Epicycle[] epicycles) {
        this.pane = pane;
        setEpicycles(epicycles);
        animation = new VisualLogic();
        
        pl = new Polyline();
        pl.setStroke(Color.RED);
        pane.getChildren().add(pl);
    }

    public void setEpicycles(Epicycle[] epicycles) {
        if (epicycles != null) {
            for (Epicycle e : epicycles) {
                pane.getChildren().remove(e.arrowShaft);
                pane.getChildren().remove(e.circle);
            }
        }
        this.epicycles = new LinkedList<Epicycle>(Arrays.asList(epicycles));
        
        for (Epicycle e : epicycles) {
            pane.getChildren().addAll(e.arrowShaft, e.circle);
        }
    }

    public Boolean start() {
        pl.getPoints().clear();

        if (epicycles.size() == 0) return false;
        
        animation.start();
        return true;
        
    }

    public void updateEpicycles(double seconds) {
        epicycles.get(0).update(seconds, centerX, centerY, showCircles);
        
        for (int i = 1; i < epicycles.size(); i++) {     
            epicycles.get(i).update(seconds, epicycles.get(i - 1).endX, epicycles.get(i - 1).endY, showCircles);
        }
        System.out.println();
        if ((seconds % period) + animation.DELTA_T / 1_000_000_000.0 >= period) pl.getPoints().clear();
    }

    public void pause() {
        animation.stop();
        animation.isPaused = true;
        animation.lastUpdate = System.nanoTime();
    }

    private class VisualLogic extends AnimationTimer {
        final double DELTA_T = 1000; //nanoseconds, sample frequency = 1000000000/delta_t 
        long lastUpdate = 0;
        long startTime = -1;
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

            double seconds = (double) multiplier * currentTime / 1_000_000_000.0;

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

    public void setCenter(double centerX, double centerY) {
        this.centerX = centerX;
        this.centerY = centerY;
    }

    public void setMultiplier(double multiplier) {
        this.multiplier = multiplier;
    }
}




