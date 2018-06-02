package kmeans.model;

import kmeans.model.Centroid;

import java.util.ArrayList;

public class KMeans {

    ArrayList<Point> points;
    ArrayList<Cluster> clusters;
    ArrayList<Centroid> centroids = new ArrayList<Centroid>();
    ArrayList<Centroid> newCentroids = new ArrayList<Centroid>();
    ArrayList<Integer> clusterLabels = new ArrayList<>();
    int dimensions;

    public KMeans(ArrayList<Point> points, ArrayList<Cluster> clusters, int dimensions) {
        this.points = points;
        this.clusters = clusters;
        this.dimensions = dimensions;
    }

    public ArrayList<Integer> getClusterLabels(){
        return clusterLabels;
    }

    public void getCentroids(){

        System.out.println("Getting centroids: \n");
        for (Cluster cluster : clusters){
            centroids.add(cluster.getCenterPt());
        }

        for (Centroid centroid : centroids) {
            double[] coordinates = centroid.getCoordinates();
            for (double c : coordinates) {
                int i = 0;
                System.out.println(i + ": " + c + ", ");
                i++;
            }
            //System.out.print(centroids.indexOf(centroid));
        }
    }

    public void updateLloyd() {

        double smallestCentroidPointDistance = -1;
        Integer smallestDistanceIndex = 0;

        for (Point point : points) {

            // TODO System.out.println("Point: " + points.indexOf(point) + "; Point: x -> " + point.getxCoord() + " ; y -> " + point.getyCoord() + " \n");

            for (Centroid centroid : centroids){

                // TODO System.out.println("    Centroid: " + centroids.indexOf(centroid) + "; Point: x -> " + centroid.getxCoord() + " ; y -> " + centroid.getyCoord() + " \n");

                Double result = computeDistance(centroid, point);
                Integer centroidIndex =  centroids.indexOf(centroid);

                //System.out.println("Result distance : " + result);
                //System.out.println("-------------------------------");

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
            /*System.out.println("=================================");
            System.out.println("Smallest distance: "  + smallestCentroidPointDistance);
            System.out.println("Chosen Cluster: "  + smallestDistanceIndex);
            System.out.println("=================================");*/
            clusterLabels.add(smallestDistanceIndex);
        }

        for (Cluster cluster : clusters){

            if(cluster.getPoints().size() != 0) {
                Centroid newCentroid = computeClusterMean(cluster.getPoints());

                cluster.changeCenterPoint(newCentroid);
                //TODO System.out.println("===================  new centroid = " + newCentroid.getxCoord() + ", " + newCentroid.getyCoord());
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

        double[] sum = new double[dimensions];
        double nrPoints = points.size();

        for (Point point : points){
            for(int i = 0; i < dimensions; i++)
                sum[i] += point.coordinates[i];
        }

        for(int i = 0; i < dimensions; i++)
            sum[i] /= nrPoints;

        Centroid meanPoint = new Centroid(sum);
        return meanPoint;
    }

    // test the points that are added to a cluster
    public void testCluster(){

        for (Cluster cluster : clusters){
            //System.out.println("Cluster: " + clusters.indexOf(cluster) + "; Points: ");
//            for (Point point : cluster.getPoints()){
//                int i;
//                for ()
//                System.out.print("x -> " + point.getxCoord() + " ; y -> " + point.getyCoord() + " \n");
//                i++;
//            }
        }
    }

    private double computeDistance(Centroid centroid, Point point){

        double distance = 0;
        double[] centroidCoordinates = centroid.getCoordinates();
        double[] pointCoordinates = point.getCoordinates();
        double tempSum = 0;
        for(int i = 0; i < dimensions; i++){
            tempSum += Math.pow((centroidCoordinates[i] - pointCoordinates[i]),2);
        }
        distance = Math.sqrt(tempSum);

        //System.out.println("Distance: " + distance + ", \n");

        return distance;
    }

    public ArrayList<Cluster> getClusters() {
        return clusters;
    }
}
