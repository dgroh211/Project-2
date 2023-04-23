import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class CsvCreator {

    public static void main(String[] args) {
        // create the x values
        double[] xValues = new double[21];
        for (int i = 0; i < 21; i++) {
            xValues[i] = i - 10;
        }
        
        // create the y values
        double[] yValues = new double[21];
        for (int i = 0; i < 21; i++) {
            yValues[i] = 1.5 * xValues[i] + 1;
        }
        
        // write the normal data to a CSV file
        writeCsvFile("normal.csv", xValues, yValues);
        
        // salt the data
        double[] saltedYValues = saltData(yValues);
        writeCsvFile("salt.csv", xValues, saltedYValues);
        
        // smooth the data
        double[] smoothedYValues = smoothData(saltedYValues);
        writeCsvFile("smooth.csv", xValues, smoothedYValues);
    }
    
    // function to write the x and y values to a CSV file
    private static void writeCsvFile(String filename, double[] xValues, double[] yValues) {
        try {
            FileWriter writer = new FileWriter(filename);
            
            for (int i = 0; i < xValues.length; i++) {
                writer.append(Double.toString(xValues[i]));
                writer.append(",");
                writer.append(Double.toString(yValues[i]));
                writer.append("\n");
            }
            
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    // function to salt the data
    private static double[] saltData(double[] yValues) {
        double[] saltedYValues = new double[yValues.length];
        
        // keep the first and last values unchanged
        saltedYValues[0] = yValues[0];
        saltedYValues[yValues.length - 1] = yValues[yValues.length - 1];
    
        // add or subtract a random value from [-2, 2] to the middle values
        Random rand = new Random();
        for (int i = 1; i < yValues.length - 1; i++) {
            double randomValue = rand.nextDouble() * 4 - 2; // generate random number in [-2, 2]
            saltedYValues[i] = yValues[i] + randomValue;
        }
    
        return saltedYValues;
    }
    
    // function to smooth the data
    private static double[] smoothData(double[] yValues) {
        double[] smoothedYValues = new double[yValues.length];
        
        // copy the first and last values to the new array
        smoothedYValues[0] = yValues[0];
        smoothedYValues[yValues.length - 1] = yValues[yValues.length - 1];
        
        // smooth the middle values
        for (int i = 1; i < yValues.length - 1; i++) {
            smoothedYValues[i] = (yValues[i - 1] + yValues[i] + yValues[i + 1]) / 3.0;
        }
        
        return smoothedYValues;
    }

}
