import kmeans.model.KMeans;
import kmeans.model.Point;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args){
        int clusters = 10;
        CSVReader reader = new CSVReader();
        double[][] rawData = reader.extractDataFromCSV(null);
        int numPoints = rawData.length;
        int dimensions = rawData[0].length - 1;
        System.out.println(reader.getSolution(null));
        ArrayList<Point> dataSet = new ArrayList<Point>;
        for(int i = 0; i < numPoints; i++) {
            double[] coordinates = new double[0];
            for (int axis = 0; axis < dimensions; axis++) {
                coordinates[axis] = rawData[i][axis];
            }
            Point currentPoint = new Point(coordinates);
            dataSet.add(currentPoint)
        }
        KMeans kMeans = new KMeans(dataSet, clusters, dimensions);
    }
}
