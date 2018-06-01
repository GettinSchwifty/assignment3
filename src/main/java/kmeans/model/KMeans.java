package kmeans.model;

import kmeans.model.Centroid;

import java.util.ArrayList;

public class KMeans {

    ArrayList<Point> points;
    ArrayList<Cluster> clusters;
    ArrayList<Centroid> centroids = new ArrayList<Centroid>();
    ArrayList<Centroid> newCentroids = new ArrayList<Centroid>();

    public KMeans(ArrayList<Point> points, ArrayList<Cluster> clusters) {
        this.points = points;
        this.clusters = clusters;
    }

    public void getCentroids(){

        System.out.println("Getting centroids: \n");
        for (Cluster cluster : clusters){
            centroids.add(cluster.getCenterPt());
        }

        for (Centroid centroid : centroids) {

            System.out.println(centroid.getxCoord() + ", " + centroid.getyCoord() + " <<<<<<<<<<<<<<<<<< " + centroids.indexOf(centroid));
        }
    }

    public void updateLloyd() {

        double smallestCentroidPointDistance = -1;
        Integer smallestDistanceIndex = 0;

        for (Point point : points) {

            System.out.println("Point: " + points.indexOf(point) + "; Point: x -> " + point.getxCoord() + " ; y -> " + point.getyCoord() + " \n");

            for (Centroid centroid : centroids){

                System.out.println("    Centroid: " + centroids.indexOf(centroid) + "; Point: x -> " + centroid.getxCoord() + " ; y -> " + centroid.getyCoord() + " \n");

                Double result = computeDistance(centroid,point);
                Integer centroidIndex =  centroids.indexOf(centroid);

                System.out.println("Result distance : " + result);
                System.out.println("-------------------------------");

                if (centroids.indexOf(centroid) == 0){
                    smallestCentroidPointDistance = result;
                    smallestDistanceIndex = centroidIndex;
                }

                if (centroids.indexOf(centroid) > 0 && result < smallestCentroidPointDistance) {
                    smallestCentroidPointDistance = result;
                    smallestDistanceIndex = centroidIndex;
                }
            }

            clusters.get(smallestDistanceIndex).addPoint(point);
            System.out.println("=================================");
            System.out.println("Smallest distance: "  + smallestCentroidPointDistance);
            System.out.println("Chosen Cluster: "  + smallestDistanceIndex);
            System.out.println("=================================");
        }

        for (Cluster cluster : clusters){

            if(cluster.getPoints().size() != 0) {
                Centroid newCentroid = computeClusterMean(cluster.getPoints());

                cluster.changeCenterPoint(newCentroid);
                System.out.println("===================  new centroid = " + newCentroid.getxCoord() + ", " + newCentroid.getyCoord());
            }

            newCentroids.add(cluster.getCenterPt());

        }

        boolean resultConvergenceCheck = checkForConvergence();
        if (resultConvergenceCheck){
            updateLloyd();
        }

        System.out.println("The algorithm has converged !!");
    }

    private boolean checkForConvergence(){
        for ( Centroid oldCentroid : centroids){

            Integer currentIndex = centroids.indexOf(oldCentroid);

            if ( !oldCentroid.equals(newCentroids.get(currentIndex))){
                return false;
            }
        }
        return true;
    }

    private Centroid computeClusterMean(ArrayList<Point> points){

        double xSum = 0;
        double ySum = 0;
        double nrPoints = points.size();

        for (Point point : points){
            xSum += point.getxCoord();
            ySum += point.getyCoord();
        }



        Centroid meanPoint = new Centroid(xSum/nrPoints, ySum/nrPoints);
        return meanPoint;
    }

    // test the points that are added to a cluster
    public void testCluster(){

        for (Cluster cluster : clusters){
            System.out.println("Cluster: " + clusters.indexOf(cluster) + "; Points: ");
            for (Point point : cluster.getPoints()){
                System.out.println("x -> " + point.getxCoord() + " ; y -> " + point.getyCoord() + " \n");
            }
        }
    }

    private double computeDistance(Centroid centroid, Point point){

        double distance = Math.sqrt( ((double) Math.pow((centroid.getxCoord() - point.getxCoord()),2)
                + (double) Math.pow((centroid.getyCoord() - point.getyCoord()),2)));

        System.out.println("Distance: " + distance + ", \n");

        return distance;
    }

    public ArrayList<Cluster> getClusters() {
        return clusters;
    }
}