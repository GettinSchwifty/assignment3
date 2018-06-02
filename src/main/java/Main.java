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
            Centroid newCentroid = (Centroid)dataSet.get(index);
            dataSet.remove(index);
            Cluster newCluster = new Cluster(newCentroid);
            clusters.add(newCluster);
        }
        for(Point p : dataSet) {
            int index = r.nextInt(numClusters);
            clusters.get(index).addPoint(p);
            dataSet.remove(p);
        }
        KMeans kMeans = new KMeans(dataSet, clusters, dimensions);

    }
}
