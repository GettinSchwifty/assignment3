package kmeans.model;

public class Point {

    /**
     * An array stores the coordinates
     */
    protected double[] coordinates;

    /**
     * Default constructor
     *
     * @param  x coordinates of a point
     * @param  y coordinates of a point
     */
    public Point(double[] x) {
            this.coordinates = x;
    }

    public double[] getCoordinates() {
        return coordinates;
    }

}
