package presenter.utils;

import com.opencsv.CSVWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class CSVHandler {
    public static void writeStatsToCSV(List<String[]> data, String fileName){
        File file = new File("stats/" + fileName +".csv");
        try{
            FileWriter outputFile = new FileWriter(file);

            CSVWriter writer = new CSVWriter(outputFile);
            writer.writeAll(data);
            writer.close();
        }catch(IOException e){
            System.out.println(e.getMessage());
        }
    }
}
