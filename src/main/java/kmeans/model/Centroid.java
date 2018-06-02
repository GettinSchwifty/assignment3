package kmeans.model;

public class Centroid extends Point {
    /**
     * Default constructor
     *
     * @param x x coordinates of a point
     * @param y y coordinates of a point
     */
    public Centroid(double[] x) {
        super(x);
    }

    public void setCoordinates(double[] coordinates) {
        this.coordinates = coordinates;
    }

    @Override
    public double[] getCoordinates() {
        return super.getCoordinates();
    }
}
