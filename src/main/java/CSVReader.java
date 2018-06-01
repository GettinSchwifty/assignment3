import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.List;

public class CSVReader {


    /**
     * Method used for extracting the data from the input CSV file and storing it in a 2-dim array
     * @param path Path to the CSV file
     * @return the values extracted from the CSV
     */
    public double[][] extractDataFromCSV(String path){
        if(path == null){
            path = "/Users/milan/java/assignment3/src/assets/LSH-nmi.csv";
        }
        FileReader CSVinput = null;
        CSVParser csvParser = null;
        char delimiter = ',';

        try {
            File fileToRead = new File(path);
            //exception handling regarding the availability and readability of the CSV file, respectively
            if (!fileToRead.exists())
                throw new FileNotFoundException("Could not find file " + path);

            if (!fileToRead.canRead())
                throw new FileNotFoundException("Can't read file " + path);

            CSVinput = new FileReader(fileToRead);
            CSVFormat format = CSVFormat.newFormat(delimiter);
            csvParser = new CSVParser(CSVinput, format);

            List<CSVRecord> datalist = csvParser.getRecords();
            double[][] data = new double[datalist.size()][11];

            int row = 0;
            for (CSVRecord record: datalist){
                for (int i = 0; i < 10; i++){
                    data[row][i] = Double.parseDouble(record.get(i));
                }
                //adds a new row for the cluster labels
                data[row][10] = -1;
                ++row;
            }
            return data;
        }
        catch (Exception e){
            throw new IllegalArgumentException("Error while reading file " + path);
        }
        finally {
            try {
                csvParser.close();
                CSVinput.close();
            }
            catch (IOException e){
                e.printStackTrace();
            }
        }

    }

    /**
     * Method used for getting the cluster labels only -- later on used for comparison to our implementation
     * @param path The path to the CSV file
     * @return an ArrayList consisting of the each row's cluster label
     */
    public ArrayList<Integer> getSolution(String path){
        if (path == null){
            path="/Users/milan/java/assignment3/src/assets/LSH-nmi.csv";
        }

        FileReader CSVinput = null;
        CSVParser parser = null;
        char delimiter=',';

        try {
            File fileToRead = new File(path);

            //exception handling -- see previous method for explanation
            if (!fileToRead.exists())
                throw new FileNotFoundException("Could not find file " + path);
            if (!fileToRead.canRead())
                throw new FileNotFoundException("Can't read file " + path);

            CSVinput = new FileReader(fileToRead);
            CSVFormat CSVformat = CSVFormat.newFormat(delimiter);
            parser = new CSVParser(CSVinput, CSVformat);

            ArrayList<Integer> solution = new ArrayList<Integer>();
            DecimalFormat format = new DecimalFormat();
            DecimalFormatSymbols decimalSymbols = new DecimalFormatSymbols();

            decimalSymbols.setDecimalSeparator('.');
            format.setDecimalFormatSymbols(decimalSymbols);

            for (CSVRecord record: parser){
                solution.add(format.parse(record.get(10).substring(5)).intValue());
            }
            return solution;
        }
        catch (Exception e){
            e.printStackTrace();
            throw new IllegalArgumentException("Error while reading file " + path);
        }
        finally {
            try {
                parser.close();
                CSVinput.close();
            }
            catch (IOException e){
                e.printStackTrace();
            }
        }

    }

    public static void main(String[] args){
        CSVReader reader = new CSVReader();
        System.out.println(reader.extractDataFromCSV(null).length);
        System.out.println(reader.getSolution(null));
    }



}
