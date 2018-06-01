package kmeans.model;

public class Point {

    /**
     * Point will be 2 dimensional Points
     */
    protected double xCoord;
    protected double yCoord;

    /**
     * Default constructor
     *
     * @param  x coordinates of a point
     * @param  y coordinates of a point
     */
    public Point(double x, double y) {
            this.xCoord = x;
            this.yCoord = y;
    }

    public double getxCoord() {
        return xCoord;
    }

    public double getyCoord() {
        return yCoord;
    }

}
