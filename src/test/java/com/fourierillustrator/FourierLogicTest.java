package com.fourierillustrator;

import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.Test;

import com.fourierillustrator.Model.Epicycle;
import com.fourierillustrator.Model.FourierLogic;
import com.fourierillustrator.Model.FourierLogic.Transform;

public class FourierLogicTest {

    private static final double EPSILON = 1e-6;
        private static final double TEST_DT = 1.0; 
    
        private double callComponentsToAmplitude(double c1, double c2, int size) throws Exception {
            java.lang.reflect.Method method = FourierLogic.class.getDeclaredMethod(
                "componentsToAmplitude", double.class, double.class, int.class
            );
            method.setAccessible(true);
            return (double) method.invoke(null, c1, c2, size);
        }
        
        //Private Helper Method Tests
    
        @Test
        void componentsToAmplitude_CalculatesCorrectly() throws Exception {
            double c1 = 3.0;
            double c2 = 4.0;
            int size = 10;
            
            double expectedAmplitude = 0.5;
            
            double actualAmplitude = callComponentsToAmplitude(c1, c2, size);
            
            assertEquals(expectedAmplitude, actualAmplitude, EPSILON, 
                         "Amplitude should be magnitude divided by size.");
        }
        
        @Test
        void componentsToAmplitude_HandlesNegativeComponents() throws Exception {
            double c1 = -5.0;
            double c2 = 12.0;
            int size = 100;

            double expectedAmplitude = 0.13;            
            double actualAmplitude = callComponentsToAmplitude(c1, c2, size);
            
            assertEquals(expectedAmplitude, actualAmplitude, EPSILON, 
                         "Amplitude should correctly handle negative components via squaring.");
        }
    
        //Main Logic Test    
        @Test
        void complexTransform_HandlesSimpleConstantInput() {
            double[] data = new double[]{
                5.0, 10.0, 
                5.0, 10.0, 
                5.0, 10.0, 
                5.0, 10.0  
            };
    
            Transform transform = FourierLogic.complexTransform(data, TEST_DT);
            LinkedList<Epicycle> epicycles = transform.epicycles();
            
            int N = data.length / 2;
            
            assertEquals(5.0, transform.centerX(), EPSILON, "Center X should be the average X component.");
            assertEquals(10.0, transform.centerY(), EPSILON, "Center Y should be the average Y component.");
            assertEquals(N - 1, epicycles.size(), "Epicycle list size should be N - 1.");

            for (Epicycle epicycle : epicycles) {
                assertEquals(0.0, epicycle.getRadius(), EPSILON, 
                             "Radius for non-DC components should be zero for constant input.");
            }
        }
        
        @Test
        void complexTransform_EpicyclesAreSortedByRadius() {
            double[] data = new double[]{
                10.0, 0.0,
                20.0, 0.0,
            };
    
            Transform transform = FourierLogic.complexTransform(data, TEST_DT);
            LinkedList<Epicycle> epicycles = transform.epicycles();
            
            assertEquals(1, epicycles.size());
            assertEquals(15.0, transform.centerX(), EPSILON);
            assertEquals(0.0, transform.centerY(), EPSILON);
            assertEquals(5.0, epicycles.get(0).getRadius(), EPSILON);
        }
    
        //tests the sortDescending helper logic using Epicycle instances.
        @Test
        void sortDescending_SortsByRadiusCorrectly() {
            Epicycle eSmall = new Epicycle(1, 1.0, 0.0);
            Epicycle eMedium = new Epicycle(2, 5.0, 0.0);
            Epicycle eLarge = new Epicycle(3, 10.0, 0.0);
            
            LinkedList<Epicycle> list = new LinkedList<>();
            list.add(eMedium);
            list.add(eSmall);
            list.add(eLarge);
            
            try {
                java.lang.reflect.Method sortMethod = FourierLogic.class.getDeclaredMethod(
                    "sortDescending", LinkedList.class
                );
                sortMethod.setAccessible(true);
                sortMethod.invoke(null, list);
            } catch (Exception e) {
                fail("Failed to call private sortDescending method: " + e.getMessage());
            }
            assertEquals(eLarge.getRadius(), list.get(0).getRadius(), EPSILON, "Largest radius should be first.");
            assertEquals(eMedium.getRadius(), list.get(1).getRadius(), EPSILON, "Medium radius should be second.");
            assertEquals(eSmall.getRadius(), list.get(2).getRadius(), EPSILON, "Smallest radius should be last.");
        }
}
