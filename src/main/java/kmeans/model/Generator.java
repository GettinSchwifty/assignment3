package kmeans.model;

import org.apache.hadoop.yarn.webapp.hamlet.Hamlet;

import java.util.*;

public class Generator {

    private int nrOfClusters;
    private int nrOfPoints;
    private int dimension;
    private ArrayList<Cluster> clusters;
    private ArrayList<Point> points;
    private ArrayList<Random> startingPoints = new ArrayList<Random>();
    private ArrayList<Centroid> startingCentroids = new ArrayList<Centroid>();

    private Random randomX = new Random();
    private Random randomY = new Random();
    private double sigma = 0.03;

    /**
     * Default constructor
     */
    public Generator() {
        this.nrOfClusters = 2;
        this.nrOfPoints = 2;
        this.dimension = 2;
        this.clusters = new ArrayList<Cluster>();
        this.points = new ArrayList<Point>();
    }

    /**
     * @param nrOfClusters Number of clusters
     * @param nrOfPoints   Number of points
     * @param dimension    Desired dimension
     */
    public Generator(int nrOfClusters, int nrOfPoints, int dimension) {
        this.nrOfClusters = nrOfClusters;
        this.nrOfPoints = nrOfPoints;
        this.dimension = dimension;
        this.clusters = new ArrayList<Cluster>();
        this.points = new ArrayList<Point>();
    }

    public void generatePoints() {

        for (int i = 0; i < nrOfClusters; i++) {
            Point centroidPoint = new Point(Math.random()*0.6 + 0.2, Math.random()*0.6 + 0.2);

            for (int j = 0; j < nrOfPoints / nrOfClusters; j++) {
                Point newPoint = new Point((randomX.nextGaussian()*sigma + centroidPoint.getxCoord()), (randomY.nextGaussian()*sigma + centroidPoint.getyCoord()));
                System.out.println("x: " + newPoint.getxCoord() + " - y: " + newPoint.getyCoord() + "\n");
                addPoint(newPoint);
            }
        }


    }

    /**
     * This function creates a new Centroid
     */
    private Centroid newCentroid() {
        return new Centroid(Math.random(), Math.random());
    }

    /**
     * This function creates as many Clusters as specified and adds a random point as initial Centroid
     */
    public void generateClusters() {
        for (int i = 0; i < nrOfClusters; i++) {
            Cluster newCluster = new Cluster(newCentroid());
            clusters.add(newCluster);
        }
    }

    /**
     * Method used for adding point to the cluster
     *
     * @param point Point that needs to be added.
     */
    private void addPoint(Point point) {
        points.add(point);
    }

    /**
     *
     * @return Returns the list of points
     */
    public ArrayList<Point> getPoints(){
        return points;
    }

    /**
     * Method used for adding cluster to the list
     *
     * @param cluster cluster that needs to be added
     */
    private void addCluster(Cluster cluster) {
        clusters.add(cluster);
    }

    /**
     *
     * @return Returns the list of clusters
     */
    public ArrayList<Cluster> getClusters(){
        return clusters;
    }

}
