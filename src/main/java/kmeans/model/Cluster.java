package kmeans.model;

import java.util.ArrayList;
import java.util.Random;

public class Cluster {

    private Centroid centerPt;
    private ArrayList<Point> points;

    /**
     *
     * @param centroid point that will move to the mean of the cluster
     *
     */
    public Cluster(Centroid centroid) {
        this.centerPt = centroid;
        this.points = new ArrayList<>();
    }

    public Centroid getCenterPt() {
        return centerPt;
    }

    public ArrayList<Point> getPoints() {
        return points;
    }

    /**
     * This method is for changing the value of the center point coordinates
     * @param centroid centroid that needs updated
     */
    public void changeCenterPoint(Centroid centroid) {
        this.centerPt = centroid;
    }

    /**
     * Method used for adding point to the cluster
     *
     * @param point Point that needs to be added.
     */
    public void addPoint(Point point) {
        points.add(point);
    }

    /**
     *
     * @return Size of the Cluster
     */
    public int getPointsNr(){
        return points.size();
    }
}
