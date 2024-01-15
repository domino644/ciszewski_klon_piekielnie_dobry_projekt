package model.animal;


import model.records.SimulationParameters;
import model.Vector2d;
import model.WorldMap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;

public class AnimalReproducing {

    private static final AnimalComparator ANIMAL_COMPARATOR = new AnimalComparator();
    private final int minimalEnergyToReproduction;
    private final int minimalMutationNumber;
    private final int maximalMutationNumber;
    private final int reproducingEnergy;
    private final WorldMap map;

    public AnimalReproducing(WorldMap map, SimulationParameters simulationParameters){
        this.map = map;
        maximalMutationNumber = simulationParameters.maximalMutationNumber();
        minimalMutationNumber = simulationParameters.minimalMutationNumber();
        minimalEnergyToReproduction = simulationParameters.minimalEnergyToReproduction();
        reproducingEnergy = simulationParameters.reproducingEnergy();
    }

    private Optional<Animal[]> findPairToReproducing(ArrayList<Animal> animalOnPositionList){
        Animal firstCandidateToReproduce;
        Animal secondCandidateToReproduce;
        if (ANIMAL_COMPARATOR.compare(animalOnPositionList.get(0),animalOnPositionList.get(1)) < 0){
            firstCandidateToReproduce = animalOnPositionList.get(0);
            secondCandidateToReproduce = animalOnPositionList.get(1);
        } else {
            firstCandidateToReproduce = animalOnPositionList.get(1);
            secondCandidateToReproduce = animalOnPositionList.get(0);
        }
        for (Animal animal : animalOnPositionList) {
            if (ANIMAL_COMPARATOR.compare(firstCandidateToReproduce, animal) > 0){
                firstCandidateToReproduce = animal;
            }
        }
        for (Animal animal : animalOnPositionList) {
            if (animal != firstCandidateToReproduce && ANIMAL_COMPARATOR.compare(firstCandidateToReproduce, animal) > 0){
                secondCandidateToReproduce = animal;
            }
        }
        return Optional.of(new Animal[]{firstCandidateToReproduce,secondCandidateToReproduce});
    }

    private void reproduceIfItPossible(Optional<Animal[]> pair){
        if (pair.isPresent()){
            Animal firstCandidateToReproduce = pair.get()[0];
            Animal secondCandidateToReproduce = pair.get()[1];
            if (secondCandidateToReproduce.getEnergyLevel() > minimalEnergyToReproduction){
                Animal newAnimal = firstCandidateToReproduce.reproduceAnimal(secondCandidateToReproduce,reproducingEnergy,
                        minimalMutationNumber,maximalMutationNumber);
                firstCandidateToReproduce.increaseChildNumber();
                secondCandidateToReproduce.increaseChildNumber();
                map.getAnimals().get(newAnimal.getPosition()).add(newAnimal);
                map.mapChangedEmit("Powstało nowe zwierze na pozycji: " + newAnimal.getPosition());
                map.getStatsKeeper().animalBorn(newAnimal);
            }
        }
    }

    private void reproducingOnPosition(ArrayList<Animal> animalOnPositionList){
        if (animalOnPositionList.size() > 1){
            Optional<Animal[]> pairToReproduce = findPairToReproducing(animalOnPositionList);
            reproduceIfItPossible(pairToReproduce);
        }
    }

    public void animalReproducing(){
        HashMap<Vector2d, ArrayList<Animal>> animals = map.getAnimals();
        for (ArrayList<Animal> list : animals.values()) {
            reproducingOnPosition(list);
        }
    }
}
