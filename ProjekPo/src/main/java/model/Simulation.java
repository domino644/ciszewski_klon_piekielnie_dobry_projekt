package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Simulation{

    private final WorldMap map;
    private static final AnimalComparator ANIMAL_COMPARATOR = new AnimalComparator();
    private final int numberOfPlantsGrowPerDay;
    private final int minimalEnergyToReproduction;
    private final int reproducingEnergy;
    private final int minimalMutationNumber;
    private final int maximalMutationNumber;
    private final int plantEnergy;
    private final int lossEnergyPerDay;
    private final RandomVectorGenerator randomVectorGenerator;

    public Simulation (WorldMap map,int numberOfPlantsGrowPerDay,int minimalEnergyToReproduction
            ,int reproducingEnergy,int minimalMutationNumber,int maximalMutationNumber,
                       int plantEnergy, RandomVectorGenerator randomVectorGenerator,int lossEnergyPerDay){
        this.map = map;
        this.maximalMutationNumber = maximalMutationNumber;
        this.numberOfPlantsGrowPerDay = numberOfPlantsGrowPerDay;
        this.plantEnergy = plantEnergy;
        this.reproducingEnergy = reproducingEnergy;
        this.minimalMutationNumber = minimalMutationNumber;
        this.minimalEnergyToReproduction = minimalEnergyToReproduction;
        this.randomVectorGenerator = randomVectorGenerator;
        this.lossEnergyPerDay = lossEnergyPerDay;
    }

    public ArrayList<Animal> allAnimalsOnBoard(HashMap<Vector2d,ArrayList<Animal>> animals){
        ArrayList<Animal> allAnimals = new ArrayList<>();
        for (ArrayList<Animal> list : animals.values()) {
            allAnimals.addAll(list);
        }
        return allAnimals;
    }

    private void animalMoves(ArrayList<Animal> animalsOnBoard,HashMap<Vector2d,ArrayList<Animal>> animals){
        Vector2d prevMovePosition;
        Vector2d afterMovePosition;
        for (Animal animal : animalsOnBoard) {
            prevMovePosition = animal.getPosition();
            animal.move(map);
            animal.decreaseEnergyLevel(lossEnergyPerDay);
            afterMovePosition = animal.getPosition();
            if (prevMovePosition != afterMovePosition){
                animals.get(prevMovePosition).remove(animal);
                animals.get(afterMovePosition).add(animal);
            }
        }
    }

    private void animalEats(HashMap<Vector2d,ArrayList<Animal>> animals,HashMap<Vector2d,Plant> plants){
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
            }
        }
    }

    private void animalReproducing(HashMap<Vector2d,ArrayList<Animal>> animals){
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
                }
            }
        }
    }

    private void clearMap(HashMap<Vector2d,ArrayList<Animal>> animals,ArrayList<Animal> animalsOnBoard){
        for (Animal animal: animalsOnBoard){
            if (animal.getEnergyLevel()  < 0 ){
                animals.get(animal.getPosition()).remove(animal);
            }
        }
    }

    public void dailyMapChange(){
        HashMap<Vector2d,ArrayList<Animal>> animals = map.getAnimals();
        HashMap<Vector2d,Plant> plants = map.getPlants();
        ArrayList<Animal> animalsOnBoard = allAnimalsOnBoard(animals);
        animalMoves(animalsOnBoard,animals);
        animalEats(animals,plants);
        animalReproducing(animals);
        clearMap(animals,animalsOnBoard);
        ArrayList<Vector2d> newGrassPosition = randomVectorGenerator.RandomVectorGrass(plants.keySet().toArray(new Vector2d[0]), numberOfPlantsGrowPerDay);
        for (Vector2d vector2d : newGrassPosition){
            plants.put(vector2d,new Plant(vector2d));
        }
    }
}
