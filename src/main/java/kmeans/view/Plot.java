package kmeans.view;

import kmeans.model.Centroid;
import kmeans.model.Cluster;
import kmeans.model.Point;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Plot extends JFrame{

    public Plot(String title, ArrayList<Cluster> clusters) {
        super(title);

        // Create dataset
        XYDataset dataset = createDataset(clusters);

        // Create chart
        JFreeChart chart = ChartFactory.createScatterPlot(
                "K-Means Clustering",
                "X-Axis", "Y-Axis", dataset, PlotOrientation.VERTICAL, true, false, false);


        // Create Panel
        ChartPanel panel = new ChartPanel(chart);
        setContentPane(panel);
    }

    private XYDataset createDataset(ArrayList<Cluster> clusters) {

        XYSeriesCollection dataset = new XYSeriesCollection();

        XYSeries centroids = new XYSeries("Centroids");

        for (Cluster cluster : clusters){

            XYSeries series = new XYSeries("Cluster " + clusters.indexOf(cluster));

            for (Point point : cluster.getPoints())
            series.add(point.getxCoord(), point.getyCoord());

            Centroid centroid = cluster.getCenterPt();
            centroids.add(centroid.getxCoord(),centroid.getyCoord());

            dataset.addSeries(series);
        }

        dataset.addSeries(centroids);


        return dataset;
    }

}
