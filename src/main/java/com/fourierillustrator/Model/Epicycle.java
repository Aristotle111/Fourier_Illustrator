package com.fourierillustrator.Model;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

public class Epicycle {
    private double startX;
    private double endX;
    private double startY;
    private double endY;
    private double radius;
    private double omega;
    private Line arrowShaft;
    private Circle circle;
    private double phase;
        
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

    public void update(double seconds, double newStartX, double newStartY) {
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

    public void changeParams(double frequency, double amplitude, double phaseCoefficient) {
        omega = frequency * 2 * Math.PI;
        radius = amplitude;
        phase = phaseCoefficient * Math.PI;

        Pane pane = (Pane) circle.getParent();
        pane.getChildren().remove(circle);
        circle = new Circle(radius);
        circle.setFill(null); circle.setStroke(Color.BLACK);
        pane.getChildren().add(circle);
    }

    public void toggleCircles(boolean showCircles) {
        if (!showCircles) circle.setStroke(null);
        else circle.setStroke(Color.BLACK);
    }

    public double getRadius() {
        return radius;
    }

    public double getEndX() {
        return endX;
    }

    public double getEndY() {
        return endY;
    }

    public Line getArrowShaft() {
        return arrowShaft;
    }

    public Circle getCircle() {
        return circle;
    }

    public double getFrequency() {
        return omega / (2 * Math.PI);
    }

    public double getPhase() {
        return phase;
    }

    public double getPhaseCoefficient() {
        return phase / Math.PI;
    }
}
