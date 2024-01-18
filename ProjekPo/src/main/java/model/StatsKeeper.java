package model;


import model.animal.Animal;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class StatsKeeper {
    private final WorldMap map;
    private int numberOfPlants = 0;
    private final ArrayList<Animal> aliveAnimals = new ArrayList<>();
    private final LinkedList<Animal> deadAnimals = new LinkedList<>();
    private final ConcurrentHashMap<Vector2d, Integer> plantsOccurence = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<Genotype, Integer> genotypesPopularity = new ConcurrentHashMap<>();


    public StatsKeeper(WorldMap map) {
        this.map = map;
    }

    public void animalDied(Animal animal) {
        aliveAnimals.remove(animal);
        deadAnimals.add(animal);
        removeGenotype(animal.getGenotype());
    }

    public void animalBorn(Animal animal) {
        aliveAnimals.add(animal);
        addGenotype(animal.getGenotype());
    }

    public void plantGrown(Vector2d position) {
        if (plantsOccurence.get(position) != null) {
            plantsOccurence.put(position, plantsOccurence.remove(position) + 1);
        } else {
            plantsOccurence.put(position, 1);
        }
        numberOfPlants++;
    }

    public void plantEaten() {
        numberOfPlants--;
    }

    private void addGenotype(Genotype genotype) {
        genotypesPopularity.put(genotype, genotypesPopularity.getOrDefault(genotype,0)+1);
    }

    private void removeGenotype(Genotype genotype) {
        genotypesPopularity.put(genotype, genotypesPopularity.getOrDefault(genotype,1)-1);
    }

    public Genotype getMostPopularGenotype() {
        int popularityNumber = 0;
        Genotype mostPopularGenotype = null;
        for (Genotype genotype : genotypesPopularity.keySet()) {
            if (genotypesPopularity.get(genotype) > popularityNumber) {
                popularityNumber = genotypesPopularity.get(genotype);
                mostPopularGenotype = genotype;
            }
        }
        return mostPopularGenotype;
    }

    public List<Genotype> getAllMostPopularGenotypes(){
        Genotype mostPopularGenotype = getMostPopularGenotype();
        if (!Objects.isNull(mostPopularGenotype)){
            List<Genotype> mostPopularGenotypes = new ArrayList<>();
            int popularityLevel = genotypesPopularity.get(mostPopularGenotype);
            for(Genotype genotype : genotypesPopularity.keySet()){
                if(genotypesPopularity.get(genotype) == popularityLevel){
                    mostPopularGenotypes.add(genotype);
                }
            }
            return mostPopularGenotypes;
        }
        return null;
    }

    public float getAverageEnergyLevel() {
        float avgEnergy = 0;
        for (Animal animal : aliveAnimals) {
            avgEnergy += animal.getEnergyLevel();
        }
        return avgEnergy / aliveAnimals.size();
    }

    public int getNumberOfFreeFields() {
        int numberOfFreeFields = 0;
        Vector2d lowerBound = map.getLowerBoundary();
        Vector2d upperBound = map.getUpperBoundary();

        int width = upperBound.getX() - lowerBound.getX();
        int height = upperBound.getY() - lowerBound.getY();

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                Vector2d v = new Vector2d(i, j);
                if (!map.isOccupied(v)) {
                    numberOfFreeFields++;
                }
            }
        }
        return numberOfFreeFields;
    }

    public int getNumberOfPlants() {
        return numberOfPlants;
    }

    public float getAverageLifetime() {
        float avgLifetime = 0;
        for (Animal animal : deadAnimals) {
            avgLifetime += animal.getAge();
        }
        return avgLifetime / deadAnimals.size();
    }

    public float getAvgNumberOfKids() {
        float avgNumberOfKids = 0;
        for (Animal animal : aliveAnimals) {
            avgNumberOfKids += animal.getNumberOfChildren();
        }
        return avgNumberOfKids / aliveAnimals.size();
    }

    public List<Vector2d> getMostPopularPlantsFields() {
        List<Vector2d> output = new LinkedList<>();
        int biggestNumberOfOccurances = 0;
        for (Vector2d position : plantsOccurence.keySet()) {
            if (plantsOccurence.get(position) > biggestNumberOfOccurances) {
                biggestNumberOfOccurances = plantsOccurence.get(position);
            }
        }

        for (Vector2d position : plantsOccurence.keySet()) {
            if (plantsOccurence.get(position) == biggestNumberOfOccurances) {
                output.add(position);
            }
        }

        return output;
    }

    public int getNumberOfAliveAnimals() {
        return aliveAnimals.size();
    }

    public int getNumberOfDeadAnimals() {
        return deadAnimals.size();
    }


    public String[] stringArgumentsGet(){
        Genotype genotype = getMostPopularGenotype();
        String gen;
        if (genotype != null){
            gen = genotype.toString();
        }
        else {
            gen = "Brak";
        }
        return new String[]{Integer.toString(aliveAnimals.size()),Integer.toString(deadAnimals.size()),Integer.toString(numberOfPlants),
        Integer.toString(getNumberOfFreeFields()),Float.toString(getAverageEnergyLevel()),
        Float.toString(getAverageLifetime()),Float.toString(getAvgNumberOfKids()),gen};
    }

    public ConcurrentHashMap<Genotype, Integer> getGenotypesPopularity() {
        return genotypesPopularity;
    }
}
