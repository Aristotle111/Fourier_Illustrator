package com.fourierillustrator.Model;

import javafx.animation.AnimationTimer;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Polyline;

public class DrawingVisualization {
    private Pane pane;
    private Polyline drawingVisual;
    private DrawingLogic drawingLogic;
    private Visualization visualization;

    public DrawingVisualization(Pane pane) {
        this.pane = pane;
        drawingVisual = new Polyline();
        drawingLogic = new DrawingLogic();
        visualization = new Visualization(pane);

        pane.setOnMouseDragged(event -> drawingLogic.update(event));
        pane.setOnMousePressed(event -> drawingLogic.start(event));
        pane.setOnMouseReleased(event -> drawingLogic.stop(event));

        drawingVisual = new Polyline();
        pane.getChildren().addAll(drawingVisual);
    }

    private class DrawingLogic extends AnimationTimer {
        //final 
        private double DELTA_T = 10000; //nanoseconds, sample frequency = 1000000000/delta_t 
        private long lastUpdate = 0;
        private long startTime;
        private double startX;
        private double startY;
        private double x;
        private double y;

        /**
         * Updates internal mouse position variables with new mouse position
         * @param event the mouse event containg the new positions
         */
        private void update(MouseEvent event) {
            x = event.getX();
            y = event.getY();
        }

        /**
         * Starts recording the user drawing
         * @param event the initial mouse event
         */
        private void start(MouseEvent event) {
            startTime = -1; 
            lastUpdate = 0;
            startX = event.getX();
            startY = event.getY();

            update(event);
            drawingVisual.getPoints().clear();
            drawingVisual.getPoints().addAll(startX, startY);
            super.start();
        }

        /**
         * Stops recording the user drawing
         * @param event the final mouse event
         */
        private void stop(MouseEvent event) {
            super.stop();
            drawingVisual.getPoints().addAll(startX, startY);
            
            //show circles
            double dt = DELTA_T / 1_000_000_000.0; 

            double[] data = new double[drawingVisual.getPoints().size()];
            
            for (int i = 0; i < data.length; i++) {
                data[i] = drawingVisual.getPoints().get(i);
            }
            FourierLogic.Transform transform = FourierLogic.complexTransform(data, dt);
            Epicycle[] epicycles = transform.epicycles().toArray(new Epicycle[0]);
            
            visualization.setEpicycles(epicycles);
            visualization.setCenter(transform.centerX(), transform.centerY());
            visualization.start();
        }

        @Override
        public void handle(long now) {
            //double seconds = (double) now / 1_000_000_000.0
            if (startTime < 0) startTime = now;
            long currentTime = now - startTime;
            long elapsed = currentTime - lastUpdate;
            if (elapsed <= DELTA_T) return;

            lastUpdate = currentTime;
            drawingVisual.getPoints().addAll(x, y);
        }
    }

    /**
     * Clears current drawing
     */
    public void clear() {
        pane.getChildren().clear();
        visualization = new Visualization(pane);
        drawingVisual = new Polyline();
        pane.getChildren().addAll(drawingVisual);
    }

    public void setMultiplier(double multiplier) {
        visualization.setMultiplier(multiplier);
    }

    public void setShowCircles(boolean showCircles) {
        for (Object e : visualization.getEpicycles()) {
            if (e instanceof Epicycle epicycle) epicycle.toggleCircles(showCircles);
        }
    }

    public void setDT(double DELTA_T) {
        drawingLogic.DELTA_T = DELTA_T;
    }

    public Polyline getDrawingVisual() {
        return drawingVisual;
    }

    public Visualization getV() {
        return visualization;
    }
}
