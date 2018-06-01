package kmeans.model;

public class Centroid extends Point {
    /**
     * Default constructor
     *
     * @param x x coordinates of a point
     * @param y y coordinates of a point
     */
    public Centroid(double x, double y) {
        super(x, y);
    }

    public void setxCoord(double xCoord) {
        this.xCoord = xCoord;
    }

    public void setyCoord(double yCoord) {
        this.yCoord = yCoord;
    }

}
