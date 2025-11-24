package com.fourierillustrator;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

public class Epicycle {
    double startX;
    double endX;
    double startY;
    double endY;
    double radius;
    double omega;
    Line arrowShaft;
    Circle circle;
    double phase;
        
    public Epicycle(double radius, double omega) {
        this.radius = radius;
        this.omega = omega;
        phase = 0;
        arrowShaft = new Line(0, 0, 0, 0);
        arrowShaft.setStrokeWidth(2);
        circle = new Circle(radius);
        circle.setFill(null); circle.setStroke(Color.BLACK);
    }

    public Epicycle(int frequency, double amplitude, double phase) {
        this.radius = amplitude;
        this.omega = frequency * 2 * Math.PI;
        this.phase = phase;
        arrowShaft = new Line(0, 0, 0, 0);
        arrowShaft.setStrokeWidth(2);
        circle = new Circle(radius);
        circle.setFill(null); circle.setStroke(Color.BLACK);
    }
        
    void update(double seconds, double newStartX, double newStartY, boolean showCircles) {
        startX = newStartX;
        startY = newStartY;
        endX = radius * Math.cos(omega * seconds + phase) + startX;
        endY = radius * Math.sin(omega * seconds + phase) + startY;

        if (!showCircles) circle.setStroke(null);
        else circle.setStroke(Color.BLACK);
        
        arrowShaft.setStartX(startX);
        arrowShaft.setStartY(startY);
        arrowShaft.setEndX(endX);
        arrowShaft.setEndY(endY);

        circle.setCenterX(newStartX);
        circle.setCenterY(newStartY);
    }

    void update(double seconds, double newStartX, double newStartY) {
        startX = newStartX;
        startY = newStartY;
        endX = radius * Math.cos(omega * seconds + phase) + startX;
        endY = radius * Math.sin(omega * seconds + phase) + startY;
            
        arrowShaft.setStartX(startX);
        arrowShaft.setStartY(startY);
        arrowShaft.setEndX(endX);
        arrowShaft.setEndY(endY);

        circle.setCenterX(newStartX);
        circle.setCenterY(newStartY);
    }

    public double getRadius() {
        return radius;
    }

    public void toggleCircles(boolean showCircles) {
        if (!showCircles) circle.setStroke(null);
        else circle.setStroke(Color.BLACK);
    }
}
