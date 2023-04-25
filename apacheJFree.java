// https://www.tutorialspoint.com/jfreechart/jfreechart_line_chart.htm
// https://commons.apache.org/proper/commons-math/javadocs/api-3.6.1/index.html

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
        XYSeries dataset = createDataset();
        XYSeries saltedDataset = saltDataset(dataset);
        XYSeries smoothedDataset1 = smoothDataset(saltedDataset);
        XYSeries smoothedDataset2 = smoothDataset(smoothedDataset1);
        XYSeriesCollection datasetCollection = new XYSeriesCollection(dataset);
        XYSeriesCollection saltedDatasetCollection = new XYSeriesCollection(saltedDataset);
        XYSeriesCollection smoothedDatasetCollection = new XYSeriesCollection(smoothedDataset2);
        JFreeChart chart = ChartFactory.createXYLineChart("y=(3/2)x + 1", "X", "Y", datasetCollection, PlotOrientation.VERTICAL, true, true, false);
        XYPlot plot = chart.getXYPlot();
        plot.setDataset(1, saltedDatasetCollection);
        plot.setDataset(2, smoothedDatasetCollection);

        XYLineAndShapeRenderer renderer1 = new XYLineAndShapeRenderer();
        renderer1.setSeriesPaint(0, Color.BLUE);
        plot.setRenderer(0, renderer1);

        XYLineAndShapeRenderer renderer2 = new XYLineAndShapeRenderer();
        renderer2.setSeriesPaint(0, Color.RED);
        plot.setRenderer(1, renderer2);

        XYLineAndShapeRenderer renderer3 = new XYLineAndShapeRenderer();
        renderer3.setSeriesPaint(0, Color.GREEN);
        plot.setRenderer(2, renderer3);

        ChartPanel panel = new ChartPanel(chart);
        panel.setPreferredSize(new java.awt.Dimension(800, 600));
        JFrame frame = new JFrame("Function Plot");
        frame.setContentPane(panel);
        frame.pack();
        frame.setVisible(true);
    }

    private static XYSeries createDataset() {
        XYSeries dataset = new XYSeries("Normal Function");
        for (int x = -10; x <= 10; x++) {
            double y = 3.0/2 * x + 1;
            dataset.add(x, y);
        }
        return dataset;
    }

    private static XYSeries saltDataset(XYSeries dataset) {
        XYSeries saltedDataset = new XYSeries("Salted Function");
        Random rand = new Random();
        for (int i = 0; i < dataset.getItemCount(); i++) {
            double x = dataset.getX(i).doubleValue();
            double y = dataset.getY(i).doubleValue();
            double salt = rand.nextDouble() * 4 - 2; // generate random salt
            saltedDataset.add(x, y + salt);
        }
        return saltedDataset;
    }

    private static XYSeries smoothDataset(XYSeries dataset) {
        int windowSize = 3;
        XYSeries smoothedDataset1 = new XYSeries("Smoothed Function");
        for (int i = 0; i < dataset.getItemCount(); i++) {
            double x = dataset.getX(i).doubleValue();
            double[] values = new double[windowSize];
            for (int j = 0; j < windowSize; j++) {
                int index = i - windowSize/2 + j;
                if (index >= 0 && index < dataset.getItemCount()) {
                    values[j] = dataset.getY(index).doubleValue();
                }
            }
            double smoothedValue = new Mean().evaluate(values);
            smoothedDataset1.add(x, smoothedValue);
        }

        // Make sure that the first and last points of the smoothed dataset are the same as those in the original dataset
        smoothedDataset1.update(0, dataset.getY(0));
        smoothedDataset1.update(smoothedDataset1.getItemCount()-1, dataset.getY(dataset.getItemCount()-1));

        XYSeries smoothedDataset2 = new XYSeries("Smoothed Function");
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
        
        smoothedDataset2.update(0, dataset.getY(0));
        smoothedDataset2.update(smoothedDataset2.getItemCount()-1, dataset.getY(dataset.getItemCount()-1));
        return smoothedDataset2;
    }
}