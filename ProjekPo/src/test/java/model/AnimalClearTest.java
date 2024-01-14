package model;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class AnimalClearTest {

    @Test
    void clearMapRemoveAll() {
        WorldParameters worldParameters = new WorldParameters(4,4,4,4,-2,1,1);
        WorldMap map = new WorldMap(worldParameters);
        AnimalClear animalClear = new AnimalClear(map);
        ArrayList<Animal> allAnimals = new ArrayList<>();
        for (ArrayList<Animal> list : map.getAnimals().values()) {
            allAnimals.addAll(list);
        }
        animalClear.clearMap(allAnimals);
        allAnimals = new ArrayList<>();
        for (ArrayList<Animal> list : map.getAnimals().values()) {
            allAnimals.addAll(list);
        }
        assertEquals(0,allAnimals.size());
    }
    @Test
    void clearMapNotRemoveAll() {
        WorldParameters worldParameters = new WorldParameters(4,4,4,4,5,1,1);
        WorldMap map = new WorldMap(worldParameters);
        AnimalClear animalClear = new AnimalClear(map);
        ArrayList<Animal> allAnimals = new ArrayList<>();
        for (ArrayList<Animal> list : map.getAnimals().values()) {
            allAnimals.addAll(list);
        }
        animalClear.clearMap(allAnimals);
        allAnimals = new ArrayList<>();
        for (ArrayList<Animal> list : map.getAnimals().values()) {
            allAnimals.addAll(list);
        }
        assertEquals(4,allAnimals.size());
    }
}