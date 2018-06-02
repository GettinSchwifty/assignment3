import kmeans.model.Centroid;
import kmeans.model.Cluster;
import kmeans.model.KMeans;
import kmeans.model.Point;

import java.util.ArrayList;
import java.util.Random;

public class Main {
    public static void main(String[] args){
        Random r = new Random();
        int numClusters = 10;
        CSVReader reader = new CSVReader();
        double[][] rawData = reader.extractDataFromCSV(null);
        int numPoints = rawData.length;
        int dimensions = rawData[0].length - 1;
        System.out.println(reader.getSolution(null));
        ArrayList<Point> dataSet = new ArrayList<Point>();
        for(int i = 0; i < numPoints; i++) {
            double[] coordinates = new double[dimensions];
            for (int axis = 0; axis < dimensions; axis++) {
                coordinates[axis] = rawData[i][axis];
            }
            Point currentPoint = new Point(coordinates);
            dataSet.add(currentPoint);
        }
        ArrayList<Cluster> clusters = new ArrayList<Cluster>();
        for(int i = 0; i < numClusters; i++) {
            int index = r.nextInt(dataSet.size());
            Centroid newCentroid = new Centroid(dataSet.get(index).getCoordinates());
            Cluster newCluster = new Cluster(newCentroid);
            newCluster.addPoint(dataSet.get(index));
            clusters.add(newCluster);
        }
        for(Point p : dataSet) {
            int index = r.nextInt(numClusters);
            clusters.get(index).addPoint(p);
        }
        KMeans kMeans = new KMeans(dataSet, clusters, dimensions);
        kMeans.getCentroids();
        kMeans.updateLloyd();
        kMeans.testCluster();
        ArrayList<Cluster> resultedClusters = kMeans.getClusters();
        ArrayList<Integer> clusterLabels = kMeans.getClusterLabels();
        for(int i = 0; i < clusterLabels.size(); ++i){
            System.out.print(clusterLabels.get(i) + ", ");
        }
        System.out.println();
        System.out.println("Labels size: " + clusterLabels.size());

    }
}
