package model;

import model.animal.Animal;
import model.animal.AnimalEats;
import model.records.SimulationParameters;
import model.records.WorldParameters;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class AnimalEatsTest {

    @Test
    void animalEatPlant() {
        WorldParameters worldParameters = new WorldParameters(4,4,1,16,5,1,1);
        WorldMap map = new WorldMap(worldParameters);
        SimulationParameters simulationParameters = new SimulationParameters(1,1,1,1,1,1,1);
        AnimalEats testClass = new AnimalEats(map,simulationParameters);
        testClass.animalEats();
        ArrayList<Animal> allAnimals = new ArrayList<>();
        for (ArrayList<Animal> list : map.getAnimals().values()) {
            allAnimals.addAll(list);
        }
        assertEquals(6,allAnimals.get(0).getEnergyLevel());
    }
    @Test
    void animalDontEatPlant() {
        WorldParameters worldParameters = new WorldParameters(4,4,1,0,5,1,1);
        WorldMap map = new WorldMap(worldParameters);
        SimulationParameters simulationParameters = new SimulationParameters(1,1,1,1,1,1,1);
        AnimalEats testClass = new AnimalEats(map,simulationParameters);
        testClass.animalEats();
        ArrayList<Animal> allAnimals = new ArrayList<>();
        for (ArrayList<Animal> list : map.getAnimals().values()) {
            allAnimals.addAll(list);
        }
        assertEquals(5,allAnimals.get(0).getEnergyLevel());
    }
}