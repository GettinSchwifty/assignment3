package kmeans.controller;

import com.sun.org.apache.xpath.internal.operations.Bool;
import kmeans.model.Cluster;
import kmeans.model.Generator;
import kmeans.model.KMeans;
import kmeans.model.Point;
import kmeans.view.Plot;

import javax.swing.*;
import java.util.ArrayList;

public class MainController {

    public static void main(String []args) {



        int clustersCount = 5;
        int pointsCount = 10000;
        int dimension = 2;
        boolean updateStr = true;	// true = lloyd
                                    // false = macqueen
        boolean initStr = true;	// true = Random points as initial cluster centers
                                    // false = Random partition of the data points in a cluster

        if (args.length == 5) {
            clustersCount = Integer.parseInt(args[0]);
            pointsCount = Integer.parseInt(args[1]);
            dimension = Integer.parseInt(args[2]);
            updateStr = Boolean.parseBoolean(args[3]);
            initStr = Boolean.parseBoolean(args[4]);
        }

        Generator generator = new Generator(clustersCount, pointsCount, dimension);

        generator.generatePoints();
        ArrayList<Point> dataSet = generator.getPoints();

        generator.generateClusters();
        ArrayList<Cluster> clusters = generator.getClusters();

        KMeans kMeans = new KMeans(dataSet, clusters);
        kMeans.getCentroids();
        kMeans.updateLloyd();
        kMeans.testCluster();
        ArrayList<Cluster> resultedClusters = kMeans.getClusters();


        // Plot the results here
        SwingUtilities.invokeLater(() -> {
            Plot example = new Plot("k-means Team 1", resultedClusters);
            example.setSize(800, 800);
            example.setLocationRelativeTo(null);
            example.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            example.setVisible(true);
        });

    }
}
