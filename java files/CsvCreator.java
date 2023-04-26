import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class CsvCreator {

    public static void main(String[] args) {
        //Creates the x values of the function
        double[] xValues = new double[21];
        for (int i = 0; i < 21; i++) {
            xValues[i] = i - 10;
        }
        
        //Creates the y values of the function
        double[] yValues = new double[21];
        for (int i = 0; i < 21; i++) {
            yValues[i] = 1.5 * xValues[i] + 1;
        }
        
        //Writes the normal data into a csv file
        writeCsvFile("normal.csv", xValues, yValues);
        
        //Writes the salted data into a csv file
        double[] saltedYValues = saltData(yValues);
        writeCsvFile("salt.csv", xValues, saltedYValues);
        
        //Smooths over the salted data twice, then prints the smoothed data into a csv file
        double[] smoothedYValues = smoothData(saltedYValues);
        for (int i = 0; i < 2; i++) {
            smoothedYValues = smoothData(smoothedYValues);
        }
        writeCsvFile("smooth.csv", xValues, smoothedYValues);
    }
    
    //Method that creates the csvFile
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
    
    //Function to salt through the data
    private static double[] saltData(double[] yValues) {
        double[] saltedYValues = new double[yValues.length];
        
        //This keeps the first and last values unchanged
        saltedYValues[0] = yValues[0];
        saltedYValues[yValues.length - 1] = yValues[yValues.length - 1];
    
        //Chooses a random number on the range [-2,2] to add to the function
        Random rand = new Random();
        for (int i = 1; i < yValues.length - 1; i++) {
            double randomValue = rand.nextDouble() * 4 - 2; // generate random number in [-2, 2]
            saltedYValues[i] = yValues[i] + randomValue;
        }
    
        //Returns only the y values for the salted function
        return saltedYValues;
    }
    
    //Function to smooth through the data
    private static double[] smoothData(double[] yValues) {
        double[] smoothedYValues = new double[yValues.length];
        
        //This keeps the first and last values unchanged
        smoothedYValues[0] = yValues[0];
        smoothedYValues[yValues.length - 1] = yValues[yValues.length - 1];
        
        //This finds the average of a specific window size, I chose to make the window size 3
        for (int i = 1; i < yValues.length - 1; i++) {
            smoothedYValues[i] = (yValues[i - 1] + yValues[i] + yValues[i + 1]) / 3.0;
        }
        
        //Returns only the y values for the smooth function
        return smoothedYValues;
    }

}
