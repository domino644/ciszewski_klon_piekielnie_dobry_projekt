package model;

import java.util.ArrayList;
import java.util.HashMap;

public class AnimalReproducing {

    private static final AnimalComparator ANIMAL_COMPARATOR = new AnimalComparator();
    private final int minimalEnergyToReproduction;
    private final int minimalMutationNumber;
    private final int maximalMutationNumber;
    private final int reproducingEnergy;
    private final WorldMap map;

    public AnimalReproducing(WorldMap map,SimulationParameters simulationParameters){
        this.map = map;
        maximalMutationNumber = simulationParameters.maximalMutationNumber();
        minimalMutationNumber = simulationParameters.minimalMutationNumber();
        minimalEnergyToReproduction = simulationParameters.minimalEnergyToReproduction();
        reproducingEnergy = simulationParameters.reproducingEnergy();
    }

    public void animalReproducing(){
        HashMap<Vector2d, ArrayList<Animal>> animals = map.getAnimals();
        Animal firstCandidateToReproduce;
        Animal secondCandidateToReproduce;
        for (ArrayList<Animal> list : animals.values()) {
            if (list.size() > 1){
                if (ANIMAL_COMPARATOR.compare(list.get(0),list.get(1)) < 0){
                    firstCandidateToReproduce = list.get(0);
                    secondCandidateToReproduce = list.get(1);
                } else {
                    firstCandidateToReproduce = list.get(1);
                    secondCandidateToReproduce = list.get(0);
                }
                for (Animal animal : list) {
                    if (ANIMAL_COMPARATOR.compare(firstCandidateToReproduce, animal) > 0){
                        firstCandidateToReproduce = animal;
                    }
                }
                for (Animal animal : list) {
                    if (animal != firstCandidateToReproduce && ANIMAL_COMPARATOR.compare(firstCandidateToReproduce, animal) > 0){
                        secondCandidateToReproduce = animal;
                    }
                }
                if (secondCandidateToReproduce.getEnergyLevel() > minimalEnergyToReproduction){
                    Animal newAnimal = firstCandidateToReproduce.reproduceAnimal(secondCandidateToReproduce,reproducingEnergy,
                            minimalMutationNumber,maximalMutationNumber);
                    animals.get(newAnimal.getPosition()).add(newAnimal);
                    map.mapChangedEmit("Powstało nowe zwierze na pozycji: " + newAnimal.getPosition());
                }
            }
        }
    }
}
