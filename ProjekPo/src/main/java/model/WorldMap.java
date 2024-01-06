package model;

import interfaces.MoveValidator;
import interfaces.WorldElement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class WorldMap implements MoveValidator {
    private final Vector2d lowerBoundary = new Vector2d(0, 0);
    private final Vector2d upperBoundary;
    private final HashMap<Vector2d, ArrayList<Animal>> animals = new HashMap<>();
    private final HashMap<Vector2d, Plant> plants = new HashMap<>();
    private final RandomVectorGenerator randomVectorGenerator;
    private final int numberOfPlantsGrowPerDay;
    private final int minimalEnergyToReproduction;
    private final int reproducingEnergy;
    private final int minimalMutationNumber;
    private final int maximalMutationNumber;
    private final int plantEnergy;
    private static final AnimalComparator ANIMAL_COMPARATOR = new AnimalComparator();



    public WorldMap(int width, int height, Vector2d[] animalsPositions, Vector2d[] plantsPositions,
                    int numberOfPlantsGrowPerDay,int startAnimalEnergy, int minimalEnergyToReproduction,
                    int reproducingEnergy ,int minimalMutationNumber, int maximalMutationNumber,
                    int genomeLength,int plantEnergy) {
        if (width <= 0 || height <= 0) {
            throw new IllegalArgumentException("Width and height of map have to be greater than 0");
        }
        this.numberOfPlantsGrowPerDay = numberOfPlantsGrowPerDay;
        this.minimalEnergyToReproduction = minimalEnergyToReproduction;
        this.reproducingEnergy = reproducingEnergy;
        this.minimalMutationNumber = minimalMutationNumber;
        this.maximalMutationNumber = maximalMutationNumber;
        this.plantEnergy = plantEnergy;
        randomVectorGenerator = new RandomVectorGenerator(width, height);
        this.upperBoundary = new Vector2d(width, height);
        prepareLists();
        for (Vector2d v : animalsPositions) {
            animals.get(v).add(new Animal(v,genomeLength,startAnimalEnergy));
        }
        for (Vector2d v : plantsPositions) {
            plants.put(v, new Plant(v));
        }
    }

    private void prepareLists(){
        Vector2d vector2d;
        for (int i = 0; i < upperBoundary.getX(); i++) {
            for (int j = 0; j < upperBoundary.getY(); j++) {
                vector2d = new Vector2d(i,j);
                animals.put(vector2d,new ArrayList<>());
            }
        }
    }

    public ArrayList<Animal> animalsAt(Vector2d position){
        return animals.get(position);
    }
    public Plant plantAt(Vector2d position){
        if (plants.get(position) != null) {
            return plants.get(position);
        }
        return null;
    }

    public boolean isOccupied(Vector2d position) {
        return animals.get(position) != null || plants.get(position) != null;
    }

    @Override
    public boolean isOutOfBounds(Vector2d position) {
        return position.precedes(upperBoundary) && position.follows(lowerBoundary);
    }

    @Override
    public Vector2d RandomVector() {
        return randomVectorGenerator.RandomVector();
    }

    public ArrayList<Animal> allAnimalsOnBoard(){
        ArrayList<Animal> allAnimals = new ArrayList<>();
        for (ArrayList<Animal> list : animals.values()) {
            allAnimals.addAll(list);
        }
        return allAnimals;
    }

    private void animalMoves(ArrayList<Animal> animalsOnBoard){
        Vector2d prevMovePosition;
        Vector2d afterMovePosition;
        for (Animal animal : animalsOnBoard) {
            prevMovePosition = animal.getPosition();
            animal.move(this);
            afterMovePosition = animal.getPosition();
            if (prevMovePosition != afterMovePosition){
                animals.get(prevMovePosition).remove(animal);
                animals.get(afterMovePosition).add(animal);
            }
        }
    }
    private void animalEats(){
        Animal maxEnergyAnimal;
        for (ArrayList<Animal> list : animals.values()) {
            if (!list.isEmpty() && plantAt(list.get(0).getPosition()) != null){
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
    private void animalReproducing(){
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
//                    TODO następuje rozmnażanie zwierząt
                }
            }
        }
    }
    public void dailyMapChange(){
        ArrayList<Animal> animalsOnBoard = allAnimalsOnBoard();
        animalMoves(animalsOnBoard);
        animalEats();
        animalReproducing();
        ArrayList<Vector2d> newGrassPosition = randomVectorGenerator.RandomVectorGrass(plants.keySet().toArray(new Vector2d[0]), numberOfPlantsGrowPerDay);
        for (Vector2d vector2d : newGrassPosition){
            plants.put(vector2d,new Plant(vector2d));
        }
    }
}
