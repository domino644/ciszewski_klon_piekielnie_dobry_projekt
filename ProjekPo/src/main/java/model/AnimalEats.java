package model;

import java.util.ArrayList;
import java.util.HashMap;

public class AnimalEats {

    private static final AnimalComparator ANIMAL_COMPARATOR = new AnimalComparator();
    private final WorldMap map;
    private final int plantEnergy;

    public AnimalEats(WorldMap map,SimulationParameters simulationParameters){
        this.map = map;
        this.plantEnergy = simulationParameters.plantEnergy();
    }

    public void animalEats(){
        HashMap<Vector2d, ArrayList<Animal>> animals = map.getAnimals();
        HashMap<Vector2d,Plant> plants = map.getPlants();
        Animal maxEnergyAnimal;
        for (ArrayList<Animal> list : animals.values()) {
            if (!list.isEmpty() && map.plantAt(list.get(0).getPosition()) != null){
                maxEnergyAnimal = list.get(0);
                for (Animal animal : list) {
                    if (ANIMAL_COMPARATOR.compare(maxEnergyAnimal, animal) > 0){
                        maxEnergyAnimal = animal;
                    }
                }
                maxEnergyAnimal.increaseEnergyLevel(plantEnergy);
                plants.remove(maxEnergyAnimal.getPosition());
                map.mapChangedEmit("Zwierze z pozycji: " + maxEnergyAnimal.getPosition() + " zjadlo rosline");
            }
        }
    }
}
