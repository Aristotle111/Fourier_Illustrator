package com.fourierillustrator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import com.fourierillustrator.Model.Epicycle;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class EpicycleTest {
    private static final double EPSILON = 1e-9;
    private static final double TWO_PI = 2 * Math.PI;

    //Constructor Tests
    @Test
    void constructor_RadiusOmega_InitializesCorrectly() {
        double radius = 15.0;
        double omega = Math.PI / 4.0;
        
        Epicycle epicycle = new Epicycle(radius, omega);
        
        assertEquals(radius, epicycle.getRadius(), EPSILON, "Radius should be set correctly.");
        assertEquals(omega, epicycle.getOmega(), EPSILON, "Omega should be set correctly.");
        assertEquals(0.0, epicycle.getPhase(), EPSILON, "Phase should default to 0.");
        assertNotNull(epicycle.getArrowShaft(), "Arrow shaft Line should be initialized.");
        assertNotNull(epicycle.getCircle(), "Circle should be initialized.");
    }

    @Test
    void constructor_FrequencyAmplitudePhase_InitializesCorrectly() {
        int frequency = 3;
        double amplitude = 8.5;
        double phase = Math.PI;
        
        Epicycle epicycle = new Epicycle(frequency, amplitude, phase);
        
        assertEquals(amplitude, epicycle.getRadius(), EPSILON, "Amplitude (radius) should be set correctly.");
        assertEquals(frequency * TWO_PI, epicycle.getOmega(), EPSILON, "Omega should be calculated correctly from frequency.");
        assertEquals(phase, epicycle.getPhase(), EPSILON, "Phase should be set correctly.");
        assertEquals(frequency, epicycle.getFrequency(), EPSILON, "Frequency getter should return correct value.");
        assertEquals(1.0, epicycle.getPhaseCoefficient(), EPSILON, "Phase coefficient getter should return correct value.");
    }

    //Update Method Tests
    @Test
    void update_CalculatesPositionAtZeroTime() {
        double R = 5.0;
        double startX = 10.0;
        double startY = 20.0;
        double phase = Math.PI / 2.0;

        Epicycle epicycle = new Epicycle(1, R, phase);

        epicycle.update(0.0, startX, startY);
        double expectedEndX = R * Math.cos(phase) + startX;
        double expectedEndY = R * Math.sin(phase) + startY;
        
        assertEquals(expectedEndX, epicycle.getEndX(), EPSILON, "EndX should be calculated correctly at t=0.");
        assertEquals(expectedEndY, epicycle.getEndY(), EPSILON, "EndY should be calculated correctly at t=0.");
        assertEquals(startX, epicycle.getArrowShaft().getStartX(), EPSILON, "Line StartX should be updated.");
        assertEquals(epicycle.getEndX(), epicycle.getArrowShaft().getEndX(), EPSILON, "Line EndX should be updated.");
        assertEquals(startX, epicycle.getCircle().getCenterX(), EPSILON, "Circle CenterX should be updated.");
    }
    
    @Test
    void update_CalculatesPositionAtHalfPeriod() {
        double R = 10.0;
        double startX = 0.0;
        double startY = 0.0;
        int freq = 1;

        Epicycle epicycle = new Epicycle(freq, R, 0.0);
        epicycle.update(0.5, startX, startY);

        double expectedEndX = R * Math.cos(Math.PI) + startX;
        double expectedEndY = R * Math.sin(Math.PI) + startY;
        
        assertEquals(expectedEndX, epicycle.getEndX(), EPSILON, "EndX should be calculated correctly at half period.");
        assertEquals(expectedEndY, epicycle.getEndY(), EPSILON, "EndY should be calculated correctly at half period.");
    }

    //Parameter Change Tests
    @Test
    void changeParams_UpdatesAllMathematicalParameters() {
        Epicycle epicycle = new Epicycle(1, 5.0, 0.0);
        
        double newFreq = 0.5;
        double newAmplitude = 20.0;
        double newPhaseCoefficient = -0.5;

        javafx.scene.layout.Pane mockPane = new javafx.scene.layout.Pane();
        mockPane.getChildren().add(epicycle.getCircle());
        
        epicycle.changeParams(newFreq, newAmplitude, newPhaseCoefficient);

        assertEquals(newAmplitude, epicycle.getRadius(), EPSILON, "Radius should be updated.");
        assertEquals(newFreq * TWO_PI, epicycle.getOmega(), EPSILON, "Omega should be recalculated and updated.");
        assertEquals(newPhaseCoefficient * Math.PI, epicycle.getPhase(), EPSILON, "Phase should be recalculated and updated.");
        assertEquals(newFreq, epicycle.getFrequency(), EPSILON, "Frequency getter should reflect change.");
        assertEquals(newPhaseCoefficient, epicycle.getPhaseCoefficient(), EPSILON, "Phase coefficient getter should reflect change.");
    }

    @Test
    void changeParams_ReplacesCircleInPane() {
        Epicycle epicycle = new Epicycle(1, 5.0, 0.0);
        Circle oldCircle = epicycle.getCircle();

        javafx.scene.layout.Pane mockPane = new javafx.scene.layout.Pane();
        mockPane.getChildren().add(oldCircle);

        epicycle.changeParams(2, 10.0, 0.0);
        
        Circle newCircle = epicycle.getCircle();

        assertNotEquals(oldCircle, newCircle, "The Circle object should be replaced with a new instance.");
        assertEquals(1, mockPane.getChildren().size(), "Pane should contain exactly one circle.");
        assertTrue(mockPane.getChildren().contains(newCircle), "Pane should contain the new Circle object.");
        assertFalse(mockPane.getChildren().contains(oldCircle), "Pane should no longer contain the old Circle object.");
        assertEquals(10.0, newCircle.getRadius(), EPSILON, "New Circle should have the new radius.");
    }

    //Toggle Visibility Test
    @Test
    void toggleCircles_HidesAndShowsCircleStroke() {
        Epicycle epicycle = new Epicycle(10.0, 1.0);
        Circle circle = epicycle.getCircle();

        assertEquals(Color.BLACK, circle.getStroke(), "Circle should initially have a black stroke.");

        epicycle.toggleCircles(false);
        assertNull(circle.getStroke(), "Circle stroke should be null when toggled to hide.");

        epicycle.toggleCircles(true);
        assertEquals(Color.BLACK, circle.getStroke(), "Circle stroke should be black when toggled to show.");
    }
}
