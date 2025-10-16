package com.fourierillustrator;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Polyline;
import javafx.stage.Stage;

//CUSTOM IMPORTS
import org.jtransforms.fft.DoubleFFT_1D;

public class Main extends Application {
    static Scene scene;
    static Pane canvas;
    static double x;
    static double y;
    static Polyline drawingVisual = new Polyline();
    static Map<Long, double[]> drawingPoints = new LinkedHashMap<Long,double[]>();
    public static void main(String[] args) {
        launch(args);
    }
    
    @Override
    public void start(Stage stage) {
        
        DrawingLogic dl = new DrawingLogic();

        canvas = new Pane(drawingVisual);
        canvas.setOnMouseDragged(event -> dl.update(event));

        canvas.setOnMousePressed(event -> dl.start(event));
        canvas.setOnMouseReleased(event -> dl.stop(event));

        scene = new Scene(canvas);
        stage.setScene(scene);
        stage.show();
    }
}


class DrawingLogic extends AnimationTimer {
    static final double DELTA_T = 1000000000; //nanoseconds, sample frequency = 1000000000/delta_t 
    static long lastUpdate = 0;
    static long startTime;
    static double startX;
    static double startY;
    static double x;
    static double y;
    static Polyline drawingVisual = Main.drawingVisual;
    static Map<Long, double[]> drawingPoints = Main.drawingPoints;

    
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
        drawingPoints.clear();
        drawingPoints.put((long) 0, new double[]{startX, startY});
        drawingVisual.getPoints().addAll(startX, startY);
        super.start();
    }

    public void stop(MouseEvent event) {
        super.stop();
        drawingPoints.put(lastUpdate + (long) DELTA_T, new double[]{startX, startY});
        drawingVisual.getPoints().addAll(startX, startY);
        printData();
        FourierLogic.transform(drawingPoints);
    }

    @Override
    public void handle(long now) {
        //double seconds = (double) now / 1_000_000_000.0
        if (startTime < 0) startTime = now;
        long currentTime = now - startTime;
        long elapsed = currentTime - lastUpdate;
        if (elapsed <= DELTA_T) return;

        double[] newPos = new double[]{x, y};
        //System.out.println("Effective delta_t: " + (elapsed));
        lastUpdate = currentTime;
        drawingPoints.put(currentTime, newPos);
        //System.out.println(currentTime);
        drawingVisual.getPoints().addAll(x, y);
    }

    void printData() {
        for (Map.Entry<Long, double[]> entry : drawingPoints.entrySet()) {
            System.out.printf("Time: %d | Pos: (%2f, %3f)%n", 
           entry.getKey(), entry.getValue()[0], entry.getValue()[1]);
        }
    }
}

class FourierLogic {
    static int n; //number of samples in input signal
    static double[] xData; //X COMPONENT OF SIGNAL
    static double[] yData; //Y COMPONENT OF SIGNAL
    static double sampleFrequency;

    public static double[] transform(Map<Long, double[]> inputSignal) {
        sampleFrequency = 1000000000 / (DrawingLogic.DELTA_T); //Hz, converts from nanosecs to secs
        formatData(inputSignal);

        DoubleFFT_1D fft = new DoubleFFT_1D(n);
        System.out.println("Fs = " + sampleFrequency);
        for (double x : xData) System.out.print(x + ", ");
        System.out.println();
        fft.realForward(xData);

        for (double x : xData) System.out.print(x + ", ");
        
        fft.realForward(yData);

        //NEXT: USE FOURIER TRANSFORM DATA ARRAYS TO CREATE CIRCLE
        //
        return new double[]{};
    }

    private static void formatData(Map<Long, double[]> inputSignal) {
        n = inputSignal.size();
        xData = new double[n];
        yData = new double[n];
        int index = 0;
        for (double[] coordinate : inputSignal.values()) {
            xData[index] = coordinate[0];
            yData[index] = (coordinate[1]);
            index++;
        }

        //Collections.sort(xData); //sorting is redundant since linkedhashmap
        //Collections.sort(xData);
    }
}
