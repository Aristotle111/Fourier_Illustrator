package com.fourierillustrator.Model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

import javafx.animation.AnimationTimer;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polyline;

public class Visualization {
    private Pane pane;
    private Polyline polyline;
    private LinkedList<Epicycle> epicycles;
    private VisualLogic animation;
    private double multiplier = 1;

    private double centerX = 450;
    private double centerY = 450;

    public Visualization(Pane pane) {
        this.pane = pane;
        this.epicycles = new LinkedList<Epicycle>();
        animation = new VisualLogic();
        
        polyline = new Polyline();
        polyline.setStroke(Color.BLACK);
        polyline.setStrokeWidth(5);
        pane.getChildren().addAll(polyline);
    }

    public Visualization(Pane pane, Epicycle[] epicycles) {
        this.pane = pane;
        setEpicycles(epicycles);
        animation = new VisualLogic();
        
        polyline = new Polyline();
        polyline.setStroke(Color.BLACK);
        polyline.setStrokeWidth(4);
        pane.getChildren().add(polyline);
    }

    public Visualization(Pane pane, ArrayList<Epicycle> epicycles) {
        this.pane = pane;
        setEpicycles(epicycles);
        animation = new VisualLogic();
        
        polyline = new Polyline();          
        polyline.setStroke(Color.BLACK);
        polyline.setStrokeWidth(5);
        pane.getChildren().add(polyline);
    }

    /**
     * Sets the epicycles to be used by the visualization
     * @param epicycles the epicycles to be visualized
     */
    public void setEpicycles(Epicycle[] epicycles) {
        if (this.epicycles != null) {
            for (Epicycle e : this.epicycles) {
                pane.getChildren().remove(e.getArrowShaft());
                pane.getChildren().remove(e.getCircle());
            }
        }
        this.epicycles = new LinkedList<Epicycle>(Arrays.asList(epicycles));
        
        for (Epicycle e : epicycles) {
            pane.getChildren().addAll(e.getArrowShaft(), e.getCircle());
        }
    }

    /**
     * Sets the epicycles to be used by the visualization
     * @param epicycles the arraylist of epicycles to be visualized
     */
    public void setEpicycles(ArrayList<Epicycle> epicycles) {
        if (this.epicycles != null) {
            for (Epicycle e : this.epicycles) {
                pane.getChildren().remove(e.getArrowShaft());
                pane.getChildren().remove(e.getCircle());
            }
        }
        this.epicycles = new LinkedList<Epicycle>(epicycles);
        
        for (Epicycle e : epicycles) {
            pane.getChildren().addAll(e.getArrowShaft(), e.getCircle());
        }
    }

    /**
     * Starts the visualization
     * @return whether the start was successful
     */
    public boolean start() {
        if (epicycles.size() == 0) return false;
        if (animation.isPaused == false) {
            animation.resetTime();
            polyline.getPoints().clear();
        }
        animation.start();
        return true;
        
    }

    /**
     * Updates the epicycles currently being displayed
     * @param seconds the current simulation time
     */
    public void updateEpicycles(double seconds) {
        if (epicycles.isEmpty()) return;
        
        epicycles.get(0).update(seconds, centerX, centerY);
        
        for (int i = 1; i < epicycles.size(); i++) {     
            epicycles.get(i).update(seconds, epicycles.get(i - 1).getEndX(), epicycles.get(i - 1).getEndY());
        }
    }

    /**
     * Pauses the simulation
     */
    public void pause() {
        animation.stop();
        animation.isPaused = true;
        animation.lastUpdate = System.nanoTime();
    }

    private class VisualLogic extends AnimationTimer {
        private final double DELTA_T = 100000000; //nanoseconds, sample frequency = 1000000000/delta_t 
        private long lastUpdate = 0;
        private long startTime = -1;
        private long currentTime;
        private boolean isPaused = false;
        private double seconds;
        
        /**
         * Handles timing logic for animation
         */
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

            seconds = (double) multiplier * currentTime / 1_000_000_000.0;
            updateEpicycles(seconds);
            if (seconds <= 1.1) {
               polyline.getPoints().addAll(new Double[]{
                epicycles.get(epicycles.size() - 1).getEndX(), epicycles.get(epicycles.size() - 1).getEndY()  // New Point 4 (x, y)
                }); 
            }
        }

        public void resetTime() {
            startTime = -1;
        }
    }

    /**
     * Resets the simulation
     */
    public void reset() {
        javafx.scene.paint.Paint currentStroke = polyline.getStroke();
        double currentWidth = polyline.getStrokeWidth();
        double currentOpacity = polyline.getOpacity();

        pane.getChildren().remove(polyline);
        polyline = new Polyline();
        polyline.setStroke(currentStroke);
        polyline.setStrokeWidth(currentWidth);
        polyline.setOpacity(currentOpacity);
        pane.getChildren().add(0, polyline);

        updateEpicycles(0);
        animation.stop();
        animation.isPaused = false;
        animation.startTime = -1;
    }

    public void setCenter(double centerX, double centerY) {
        this.centerX = centerX;
        this.centerY = centerY;
    }
    public AnimationTimer getAnimation() {
        return animation;
    }

    public void setMultiplier(double multiplier) {
        this.multiplier = multiplier;
    }

    public LinkedList<Epicycle> getEpicycles() {
        return epicycles;
    }

    public Polyline getPl() {
        return polyline;
    }
}
