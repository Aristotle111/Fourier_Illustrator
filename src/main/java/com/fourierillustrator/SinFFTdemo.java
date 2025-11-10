package com.fourierillustrator;

import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

import org.jtransforms.fft.DoubleFFT_1D;

public class SinFFTdemo {
    static double dt;
    static int size;
    public static void main(String[] args) {
        dt = 0.02; //interval between sampled points, sample frequency = 50 Hz
        double totalTime = 10;
        int t = 0;
        size = (int) (totalTime/dt);
        
        double[] data = new double[size];
        
        while (t < totalTime/dt) {
            data[t] = Math.sin(4 * Math.PI * (t*dt)); //angular velocity = 1 -> f = 1/2pi
            t++;
        }
       /*
       for (double num : data) {
        System.out.print(num + ", ");
       }
        */
       DoubleFFT_1D fft = new DoubleFFT_1D(data.length);
       fft.realForward(data);
        
       Map<Double, Double> spectrum = calculateSpectrum(data);
       System.out.println(sortByValueDescending(spectrum));

    }

    static void showArray(double[] data, Boolean removeDecimals) {
        for (double num : data) {
            if (removeDecimals) System.out.print( (int) num + ", "); //cast double to int to remove decimals
            else System.out.print(num + ", ");
        }
    }

    static Map<Double, Double> calculateSpectrum(double[] data) {
        Map<Double, Double> spectrum = new TreeMap<>(); //map from frequency to amplitude
        int size = data.length;

        if (size % 2 != 0) {
            System.out.println("DATA MUST BE EVEN");
            return null;
        }
        
        spectrum.put(0.0, data[0]); //DC component
        spectrum.put(binToFrequency(1), data[1]); //nyquist component
        
        for (int i = 2; i < size/2; i += 2) {
            double fk = binToFrequency(i/2);
            double amplitude = componentsToAmplitude(data[i], data[i + 1]);
            spectrum.put(fk, (double) Math.round(amplitude));
        }

        return spectrum;
    }

    static double binToFrequency(int k) {
        return k * (1/dt) / size;
    }

    static double componentsToAmplitude(double c1, double c2) {
        double frequencyAmplitude = Math.sqrt(Math.pow(c1, 2) + Math.pow(c2, 2));
        return frequencyAmplitude * 2 / size;
    }

    //written by gemini, was too lazy to sort transform map by amplitude
    static Map<Double, Double> sortByValueDescending(Map<Double, Double> originalMap) {
        return originalMap.entrySet()
                .stream()
                // Use comparingByValue() with Comparator.reverseOrder() for descending sort
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                // Collect results into a LinkedHashMap to maintain insertion order (the sorted order)
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1, // Merge function (not used here)
                        LinkedHashMap::new // Ensure order is maintained
                ));
    }
}