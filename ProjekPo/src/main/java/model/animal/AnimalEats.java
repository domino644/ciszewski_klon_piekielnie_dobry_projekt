package model.animal;

import model.records.SimulationParameters;
import model.Vector2d;
import model.WorldMap;

import java.util.ArrayList;
import java.util.HashMap;

public class AnimalEats {

    private final WorldMap map;
    private final int plantEnergy;

    public AnimalEats(WorldMap map, SimulationParameters simulationParameters){
        this.map = map;
        this.plantEnergy = simulationParameters.plantEnergy();
    }

    public void animalEats(){
        HashMap<Vector2d, ArrayList<Animal>> animals = map.getAnimals();
        Animal maxEnergyAnimal;
        for (ArrayList<Animal> list : animals.values()) {
            if (!list.isEmpty() && map.plantAt(list.get(0).getPosition()) != null){
                maxEnergyAnimal = map.findStrongestAnimal(list);
                maxEnergyAnimal.increaseEnergyLevel(plantEnergy);
                maxEnergyAnimal.eat();
                map.getPlants().remove(maxEnergyAnimal.getPosition());
                map.mapChangedEmit("");
                map.getStatsKeeper().plantEaten();
            }
        }
    }
}
