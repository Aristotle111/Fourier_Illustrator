package com.fourierillustrator;

import javafx.animation.AnimationTimer;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Polyline;

public class DrawingVisualization {
    Pane pane;
    Polyline drawingVisual;
    DrawingLogic dl;
    Visualization v;

    public DrawingVisualization(Pane pane) {
        this.pane = pane;
        drawingVisual = new Polyline();
        dl = new DrawingLogic();
        v = new Visualization(pane);

        pane.setOnMouseDragged(event -> dl.update(event));
        pane.setOnMousePressed(event -> dl.start(event));
        pane.setOnMouseReleased(event -> dl.stop(event));

        drawingVisual = new Polyline();
        pane.getChildren().addAll(drawingVisual);
    }



    private class DrawingLogic extends AnimationTimer {
        //final 
        double DELTA_T = 10000; //nanoseconds, sample frequency = 1000000000/delta_t 
        long lastUpdate = 0;
        long startTime;
        double startX;
        double startY;
        double x;
        double y;
        //Polyline drawingVisual = Main.drawingVisual;

        
        public void update(MouseEvent event) {
            x = event.getX();
            y = event.getY();
        }

        public void start(MouseEvent event) {
            startTime = -1; 
            lastUpdate = 0;
            startX = event.getX();
            startY = event.getY();

            update(event);
            drawingVisual.getPoints().clear();
            drawingVisual.getPoints().addAll(startX, startY);
            super.start();
        }

        public void stop(MouseEvent event) {
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
            
            v.setEpicycles(epicycles);
            v.setCenter(transform.centerX(), transform.centerY());
            v.start();
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

    public void setMultiplier(double multiplier) {
        v.setMultiplier(multiplier);
    }

    public void setShowCircles(boolean showCircles) {
        v.setShowCircles(showCircles);
    }

    public void clear() {
        pane.getChildren().clear();
        v = new Visualization(pane);
        drawingVisual = new Polyline();
        pane.getChildren().addAll(drawingVisual);
    }

    public void setDT(double DELTA_T) {
        dl.DELTA_T = DELTA_T;
    }
}
