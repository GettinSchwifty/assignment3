import kmeans.model.Centroid;
import kmeans.model.Cluster;
import kmeans.model.KMeans;
import kmeans.model.Point;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Main {
    public static void main(String[] args){
        Random r = new Random();
        int numClusters = 15;
        CSVReader reader = new CSVReader();
        double[][] rawData = reader.extractDataFromCSV(null);
        int numPoints = rawData.length;
        int dimensions = rawData[0].length - 1;
        System.out.println(reader.getSolution(null));
        ArrayList<Point> dataSet = new ArrayList<Point>();
        for(int i = 0; i < numPoints; i++) {
            double[] coordinates = new double[dimensions];
            for (int axis = 0; axis < dimensions; axis++) {
                coordinates[axis] = rawData[i][axis];
            }
            Point currentPoint = new Point(coordinates);
            dataSet.add(currentPoint);
        }
        ArrayList<Cluster> clusters = new ArrayList<Cluster>();
        for(int i = 0; i < numClusters; i++) {
            int index = r.nextInt(dataSet.size());
            Centroid newCentroid = new Centroid(dataSet.get(index).getCoordinates());
            Cluster newCluster = new Cluster(newCentroid);
            newCluster.addPoint(dataSet.get(index));
            clusters.add(newCluster);
        }
        for(Point p : dataSet) {
            int index = r.nextInt(numClusters);
            clusters.get(index).addPoint(p);
        }

        long startTime = System.currentTimeMillis();
        KMeans kMeans = new KMeans(dataSet, clusters, dimensions);
        kMeans.getCentroids();
        kMeans.updateLloyd();
        long endTime = System.currentTimeMillis();
        //kMeans.testCluster();
        ArrayList<Cluster> resultedClusters = kMeans.getClusters();
        ArrayList<Integer> clusterLabels = kMeans.getClusterLabels();
//        for(int i = 0; i < clusterLabels.size(); ++i){
//            System.out.print(clusterLabels.get(i) + ", ");
//        }
        long elapsedTime = endTime - startTime;
        System.out.println("Elapsed time: " + ((double)elapsedTime / 1000) + " seconds");
        System.out.println("Labels size: " + clusterLabels.size());
        NMI(clusterLabels, reader.getSolution(null));

    }


    public static double NMI(ArrayList<Integer> one, ArrayList<Integer> two){
        if(one.size()!=two.size()){
            throw new IllegalArgumentException("Sizes don't match!");
        }
        int maxone = Collections.max(one);
        int maxtwo = Collections.max(two);

        double[][] count = new double[maxone+1][maxtwo+1];
        //System.out.println(count[1][2]);
        for(int i=0;i<one.size();i++){
            count[one.get(i)][two.get(i)]++;
        }
        //i<maxone=R
        //j<maxtwo=C
        double[] bj = new double[maxtwo+1];
        double[] ai = new double[maxone+1];

        for(int m=0;m<(maxtwo+1);m++){
            for(int l=0;l<(maxone+1);l++){
                bj[m]=bj[m]+count[l][m];
            }
        }
        for(int m=0;m<(maxone+1);m++){
            for(int l=0;l<(maxtwo+1);l++){
                ai[m]=ai[m]+count[m][l];
            }
        }

        double N=0;
        for(int i=0;i<ai.length;i++){
            N=N+ai[i];
        }
        double HU = 0;
        for(int l=0;l<ai.length;l++){
            double c=0;
            c=(ai[l]/N);
            if(c>0){
                HU=HU-c*Math.log(c);
            }
        }

        double HV = 0;
        for(int l=0;l<bj.length;l++){
            double c=0;
            c=(bj[l]/N);
            if(c>0){
                HV=HV-c*Math.log(c);
            }
        }
        double HUstrichV=0;
        for(int i=0;i<(maxone+1);i++){
            for(int j=0;j<(maxtwo+1);j++){
                if(count[i][j]>0){
                    HUstrichV=HUstrichV-count[i][j]/N*Math.log(((count[i][j])/(bj[j])));
                }
            }
        }
        double IUV = HU-HUstrichV;
        double reto = IUV/(Math.max(HU, HV));

        System.out.println("NMI: "+reto);
        return reto;
    }
}
