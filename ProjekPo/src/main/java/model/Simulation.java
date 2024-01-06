package model;

import java.util.List;

public class Simulation implements Runnable{

    private final WorldMap map;

    public Simulation (int width, int height, int numberOfPlants, int numberOfPlantsGrowPerDay,
                       int numberOfAnimals, int startAnimalEnergy, int minimalEnergyToReproduction,
                       int reproducingEnergy ,int minimalMutationNumber, int maximalMutationNumber,
                       int genomeLength,int plantEnergy){
        RandomVectorGenerator randomVectorGenerator = new RandomVectorGenerator(width, height);
        List<Vector2d> animalPositions = randomVectorGenerator.RandomVectorAnimal(numberOfAnimals);
        List<Vector2d> grassPositions = randomVectorGenerator.RandomVectorGrass(new Vector2d[0],numberOfPlants);
        this.map = new WorldMap(width,height,animalPositions.toArray(new Vector2d[0])
                ,grassPositions.toArray(new Vector2d[0]), numberOfPlantsGrowPerDay, startAnimalEnergy,
                minimalEnergyToReproduction, reproducingEnergy, minimalMutationNumber, maximalMutationNumber,
                genomeLength,plantEnergy);

    }

    @Override
    public void run() {

    }
}
