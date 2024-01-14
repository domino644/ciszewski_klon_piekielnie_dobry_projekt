package model;

import java.util.ArrayList;
import java.util.HashMap;

public class PlantsRegenerator {

    private final WorldMap map;
    private final int numberOfPlantsGrowPerDay;

    public PlantsRegenerator(WorldMap map,SimulationParameters simulationParameters){
        this.map = map;
        numberOfPlantsGrowPerDay = simulationParameters.numberOfPlantsGrowPerDay();
    }

    public void plantsRegenerate(){
        HashMap<Vector2d,Plant> plants = map.getPlants();
        ArrayList<Vector2d> newGrassPosition = map.getRandomVectorGenerator().RandomVectorGrass(plants.keySet().toArray(new Vector2d[0]), numberOfPlantsGrowPerDay);
        for (Vector2d vector2d : newGrassPosition){
            plants.put(vector2d,new Plant(vector2d));
            map.getStatsKeeper().plantGrown(vector2d);
        }
        map.mapChangedEmit("");
    }
}
