package model;

import java.util.ArrayList;
import java.util.HashMap;

public class AnimalClear {

    private final WorldMap map;

    public AnimalClear(WorldMap map){
        this.map = map;
    }

    public void clearMap(ArrayList<Animal> animalsOnBoard) {
        HashMap<Vector2d, ArrayList<Animal>> animals = map.getAnimals();
        for (Animal animal : animalsOnBoard) {
            if (animal.getEnergyLevel() < 0) {
                animals.get(animal.getPosition()).remove(animal);
                map.getStatsKeeper().animalDied(animal);
            }
        }
    }
}
