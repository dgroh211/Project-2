// https://www.tutorialspoint.com/jfreechart/jfreechart_line_chart.htm
// https://commons.apache.org/proper/commons-math/javadocs/api-3.6.1/index.html

import java.io.File;
import java.io.IOException;
import org.apache.commons.math3.stat.descriptive.moment.Mean;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.function.Function2D;
import org.jfree.data.function.FunctionDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

public class RollingAverageExample {

    public static void main(String[] args) throws IOException {
        
        // Define the function as a Function2D object
        Function2D function = new Function2D() {
            @Override
            public double getValue(double x) {
                return 1.5*x + 1;
            }
        };
        
        // Define the domain of the function
        double minX = -10.0;
        double maxX = 10.0;
        
        // Define the rolling window size (number of points to include in the average)
        int windowSize = 5;
        
        // Create an array to hold the function points and rolling averages
        double[] functionPoints = new double[101];
        double[] rollingAverages = new double[101];
        
        // Calculate the function points and rolling averages
        Mean mean = new Mean();
        for (int i = 0; i <= 100; i++) {
            double x = minX + (maxX - minX) * i / 100.0;
            double y = function.getValue(x);
            functionPoints[i] = y;
            if (i >= windowSize-1) {
                double[] subset = new double[windowSize];
                for (int j = 0; j < windowSize; j++) {
                    subset[j] = functionPoints[i-j];
                }
                rollingAverages[i] = mean.evaluate(subset);
            }
        }
        
        // Create a dataset for the function points and rolling averages
        XYSeriesCollection dataset = new XYSeriesCollection();
        XYSeries functionSeries = new XYSeries("Function");
        XYSeries rollingAvgSeries = new XYSeries("Rolling Average");
        for (int i = 0; i <= 100; i++) {
            double x = minX + (maxX - minX) * i / 100.0;
            if (i < windowSize-1) {
                functionSeries.add(x, functionPoints[i]);
            } else {
                functionSeries.add(x, functionPoints[i]);
                rollingAvgSeries.add(x, rollingAverages[i]);
            }
        }
        dataset.addSeries(functionSeries);
        dataset.addSeries(rollingAvgSeries);
        
        // Create a chart and display it using JFreeChart
        ChartFrame frame = new ChartFrame("Rolling Average Example", 
                ChartFactory.createXYLineChart("Function and Rolling Average", "X", "Y", 
                        dataset, PlotOrientation.VERTICAL, true, true, false));
        frame.pack();
        frame.setVisible(true);
        
        // Save the chart as a JPEG image
        File file = new File("rolling_average_example.jpg");
        ChartUtilities.saveChartAsJPEG(file, frame.getChart(), 800, 600);
    }
}
