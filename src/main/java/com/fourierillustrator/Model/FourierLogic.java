package com.fourierillustrator.Model;

import java.util.Comparator;
import java.util.LinkedList;

import org.jtransforms.fft.DoubleFFT_1D;

public class FourierLogic {
    private FourierLogic(){}

    /**
     * Takes an array of x and y coordinates represented as complex numbers and returns
     * the transform 
     */
    public static Transform complexTransform(double data[], double dt) {
        LinkedList<Epicycle> epicycles = new LinkedList<>();
        int N = data.length / 2;

        DoubleFFT_1D fft = new DoubleFFT_1D(N);
        fft.complexForward(data);

        int f;
        for (int k = 1; k < N; k++) {

            if (k < N / 2) {
                f = k;
            } 
            else f = k - N;
           
            double amplitude = componentsToAmplitude(data[2 * k], data[2 * k + 1], N);
            double phase = Math.atan2(data[2 * k + 1], data[2 * k]);

            epicycles.add(new Epicycle(f, amplitude, phase));
        }

        sortDescending(epicycles);
        
        return new Transform(epicycles, data[0] / N, data[1] / N);
    }

    static double componentsToAmplitude(double c1, double c2, int size) {
        double amplitude = Math.sqrt(Math.pow(c1, 2) + Math.pow(c2, 2));
        return amplitude / size;
    }

    public static void sortDescending(LinkedList<Epicycle> epicycleList) {
    
        // Sort the list using the List.sort() method with a custom Comparator.
        epicycleList.sort(
            // Comparator.comparingDouble is efficient for double values.
            Comparator.comparingDouble(Epicycle::getRadius) 
                // .reversed() flips the default ascending order to descending.
                .reversed()
        );
    }

    public record Transform(LinkedList<Epicycle> epicycles, double centerX, double centerY) {}
}
