package presenter.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import model.records.AllParameters;
import model.records.SimulationParameters;
import model.records.WorldParameters;

import java.io.File;
import java.io.IOException;

public class JSONHandler {
    public static boolean objectToFile(Object object, String filename){
        ObjectMapper mapper = new ObjectMapper();
        File file = new File("ProjekPo/src/main/resources/configurations/" + filename);
        try{
        mapper.writeValue(file,object);
        return true;
        }catch(IOException e){
            System.out.println(e.getMessage());
            return false;
        }
    }

    public static AllParameters allParametersFromFile(String filename){
        ObjectMapper mapper = new ObjectMapper();
        File file = new File("ProjekPo/src/main/resources/configurations/"+filename);
        try {
            return mapper.readValue(file, AllParameters.class);
        }catch(IOException e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    public static WorldParameters worldParametersFromFile(String filename){
        ObjectMapper mapper = new ObjectMapper();
        File file = new File("ProjekPo/src/main/resources/configurations/"+filename);
        try {
            return mapper.readValue(file, WorldParameters.class);
        }catch(IOException e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    public static SimulationParameters simulationParametersFromFile(String filename){
        ObjectMapper mapper = new ObjectMapper();
        File file = new File("ProjekPo/src/main/resources/configurations/"+filename);
        try{
            return mapper.readValue(file, SimulationParameters.class);
        }catch(IOException e){
            System.out.println(e.getMessage());
            return null;
        }
    }
}
