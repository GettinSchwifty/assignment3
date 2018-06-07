package kmeans.model;

import java.util.ArrayList;

public class KMeans {

    ArrayList<Point> points;
    ArrayList<Cluster> clusters;
    ArrayList<Centroid> centroids = new ArrayList<Centroid>();
    ArrayList<Centroid> newCentroids = new ArrayList<Centroid>();
    ArrayList<Integer> clusterLabels = new ArrayList<>();
    int dimensions;
    int iteration = 0;
    int limit = 10;

    public KMeans(ArrayList<Point> points, ArrayList<Cluster> clusters, int dimensions) {
        this.points = points;
        this.clusters = clusters;
        this.dimensions = dimensions;
    }

    public ArrayList<Integer> getClusterLabels() {
        return clusterLabels;
    }

    public void getCentroids() {

        for (Cluster cluster : clusters) {
            centroids.add(cluster.getCenterPt());
        }
    }

    public void updateLloyd() {

        iteration++;

        double smallestCentroidPointDistance = -1;
        Integer smallestDistanceIndex = 0;

        clusterLabels.clear();

        for (Point point : points) {


            for (Centroid centroid : centroids) {

                Double result = computeDistance(centroid, point);
                Integer centroidIndex = centroids.indexOf(centroid);

                if (centroids.indexOf(centroid) == 0) {
                    smallestCentroidPointDistance = result;
                    smallestDistanceIndex = centroidIndex;
                }

                if (centroids.indexOf(centroid) > 0 && result < smallestCentroidPointDistance) {
                    smallestCentroidPointDistance = result;
                    smallestDistanceIndex = centroidIndex;
                }
            }

            clusters.get(smallestDistanceIndex).addPoint(point);
            clusterLabels.add(smallestDistanceIndex);
        }

        for (Cluster cluster : clusters) {

            if (cluster.getPoints().size() != 0) {
                Centroid newCentroid = computeClusterMean(cluster.getPoints());

                cluster.changeCenterPoint(newCentroid);
            }

            newCentroids.add(cluster.getCenterPt());

        }

        boolean resultConvergenceCheck = checkForConvergence();
        if (resultConvergenceCheck) {
            updateLloyd();
        }

    }

    private boolean checkForConvergence() {
        if (iteration > limit - 1) {
            return false;
        }

        return true;
    }

    private Centroid computeClusterMean(ArrayList<Point> points) {

        double[] sum = new double[dimensions];
        double nrPoints = points.size();

        for (Point point : points) {
            for (int i = 0; i < dimensions; i++)
                sum[i] += point.coordinates[i];
        }

        for (int i = 0; i < dimensions; i++)
            sum[i] /= nrPoints;

        Centroid meanPoint = new Centroid(sum);
        return meanPoint;
    }


    private double computeDistance(Centroid centroid, Point point) {

        double distance = 0;
        double[] centroidCoordinates = centroid.getCoordinates();
        double[] pointCoordinates = point.getCoordinates();
        double tempSum = 0;
        for (int i = 0; i < dimensions; i++) {
            tempSum += Math.pow((centroidCoordinates[i] - pointCoordinates[i]), 2);
        }
        distance = Math.sqrt(tempSum);


        return distance;
    }

    public ArrayList<Cluster> getClusters() {
        return clusters;
    }
}
