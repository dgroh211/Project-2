// https://www.tutorialspoint.com/jfreechart/jfreechart_line_chart.htm
// https://commons.apache.org/proper/commons-math/javadocs/api-3.6.1/index.html
// https://www.javatpoint.com/jfreechart-line-chart
// https://www.baeldung.com/apache-commons-math
//These are all the sources I used to learn how to use the libraries

import java.awt.Color;
import java.util.Random;
import javax.swing.JFrame;
import org.apache.commons.math3.stat.descriptive.moment.Mean;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

public class ApacheJFree {

    public static void main(String[] args) {
        //Initializes the datasets
        XYSeries dataset = createDataset();
        XYSeries saltedDataset = saltDataset(dataset);
        XYSeries smoothedDataset1 = smoothDataset(saltedDataset);
        XYSeries smoothedDataset2 = smoothDataset(smoothedDataset1);

        //Initializes the collections for all the datasets to be printed out on the graph
        XYSeriesCollection datasetCollection = new XYSeriesCollection(dataset);
        XYSeriesCollection saltedDatasetCollection = new XYSeriesCollection(saltedDataset);
        XYSeriesCollection smoothedDatasetCollection = new XYSeriesCollection(smoothedDataset2);

        //This creates the graph and the dataset to be displayed for the normal function
        JFreeChart chart = ChartFactory.createXYLineChart("y=(3/2)x + 1", "X", "Y", datasetCollection, PlotOrientation.VERTICAL, true, true, false);
        
        //Initializes the ability to grab two more lines to plot on the graph as well
        XYPlot plot = chart.getXYPlot();
        plot.setDataset(1, saltedDatasetCollection);
        plot.setDataset(2, smoothedDatasetCollection);

        //Sets the normal function to blue
        XYLineAndShapeRenderer renderer1 = new XYLineAndShapeRenderer();
        renderer1.setSeriesPaint(0, Color.BLUE);
        plot.setRenderer(0, renderer1);

        //Sets the salted function to red
        XYLineAndShapeRenderer renderer2 = new XYLineAndShapeRenderer();
        renderer2.setSeriesPaint(0, Color.RED);
        plot.setRenderer(1, renderer2);

        //Sets the smoothed function to green
        XYLineAndShapeRenderer renderer3 = new XYLineAndShapeRenderer();
        renderer3.setSeriesPaint(0, Color.GREEN);
        plot.setRenderer(2, renderer3);

        //Initializes the window for the graph to be displayed
        ChartPanel panel = new ChartPanel(chart);
        panel.setPreferredSize(new java.awt.Dimension(800, 600));
        JFrame frame = new JFrame("Function Plot");
        frame.setContentPane(panel);
        frame.pack();
        frame.setVisible(true);
    }

    private static XYSeries createDataset() {
        //This initilizes the dataset in the XYSeries for the normal function
        XYSeries dataset = new XYSeries("Normal Function");
        //This is the formula for finding the data points of y= 3/2x + 1
        for (int x = -10; x <= 10; x++) {
            double y = 3.0/2 * x + 1;
            dataset.add(x, y);
        }
        //Returns the XY dataset
        return dataset;
    }

    private static XYSeries saltDataset(XYSeries dataset) {
        //Initializes the saltedDataset in the XYSeries for the salted function
        XYSeries saltedDataset = new XYSeries("Salted Function");

        //Initializes the random number generator
        Random rand = new Random();

        //For loop to set the salt to each y value
        for (int i = 0; i < dataset.getItemCount(); i++) {
            double x = dataset.getX(i).doubleValue();
            double y = dataset.getY(i).doubleValue();
            //This generates the random salt variables
            double salt = rand.nextDouble() * 4 - 2;
            saltedDataset.add(x, y + salt);
        }
        //Returns the XY saltedDataset
        return saltedDataset;
    }

    private static XYSeries smoothDataset(XYSeries dataset) {
        //Initializes the windowSize of the smooth function
        int windowSize = 3;
        //Initializes the first smoothDataset because we run the function twice to get a better smoothed result
        XYSeries smoothedDataset1 = new XYSeries("Smoothed Function");

        //For loop to smooth the salted dataset
        for (int i = 0; i < dataset.getItemCount(); i++) {
            double x = dataset.getX(i).doubleValue();
            double[] values = new double[windowSize];
            for (int j = 0; j < windowSize; j++) {
                int index = i - windowSize/2 + j;
                if (index >= 0 && index < dataset.getItemCount()) {
                    values[j] = dataset.getY(index).doubleValue();
                }
            }
            //Initializes the values for the first smoothed dataset
            double smoothedValue = new Mean().evaluate(values);
            smoothedDataset1.add(x, smoothedValue);
        }

        //Make sure that the first and last points of the smoothed dataset are the same as those in the original dataset
        //Without this my first and last points were very off course from the actual line graph
        smoothedDataset1.update(0, dataset.getY(0));
        smoothedDataset1.update(smoothedDataset1.getItemCount()-1, dataset.getY(dataset.getItemCount()-1));

        //Initializes the second smoothedDataset. This one will be displayed
        //I know there is probably an easier way to implement the smooth function, but I just ran it twice
        XYSeries smoothedDataset2 = new XYSeries("Smoothed Function");
        //This is the same for loop as above
        for (int i = 0; i < smoothedDataset1.getItemCount(); i++) {
            double x = smoothedDataset1.getX(i).doubleValue();
            double[] values = new double[windowSize];
            for (int j = 0; j < windowSize; j++) {
                int index = i - windowSize/2 + j;
                if (index >= 0 && index < smoothedDataset1.getItemCount()) {
                    values[j] = smoothedDataset1.getY(index).doubleValue();
                }
            }
            double smoothedValue = new Mean().evaluate(values);
            smoothedDataset2.add(x, smoothedValue);
        }
        
        //Sets the original numbers back to what they were at the start of the normal dataset
        smoothedDataset2.update(0, dataset.getY(0));
        smoothedDataset2.update(smoothedDataset2.getItemCount()-1, dataset.getY(dataset.getItemCount()-1));
        
        //Returns the dataset that has been smoothed over twice
        return smoothedDataset2;
    }
}