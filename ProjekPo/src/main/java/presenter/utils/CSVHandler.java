package presenter.utils;

import com.opencsv.CSVWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class CSVHandler {
    public static boolean writeStatsToCSV(List<String[]> data, String fileName){
        File file = new File("ProjekPo/src/main/resources/stats/" + fileName +".csv");
        try{
            FileWriter outputFile = new FileWriter(file);

            CSVWriter writer = new CSVWriter(outputFile);
            writer.writeAll(data);
            writer.close();
            return true;
        }catch(IOException e){
            System.out.println(e.getMessage());
            return false;
        }
    }
}
