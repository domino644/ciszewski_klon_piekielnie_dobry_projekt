package model;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class StatsKeeper {
    private final WorldMap map;
    private int numberOfPlants = 0;
    private final ArrayList<Animal> aliveAnimals = new ArrayList<>();
    private final LinkedList<Animal> deadAnimals = new LinkedList<>();
    private final HashMap<Vector2d, Integer> plantsOccurence = new HashMap<>();
    private final HashMap<Genotype, Integer> genotypesPopularity = new HashMap<>();


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
        if (genotypesPopularity.get(genotype) != null) {
            genotypesPopularity.put(genotype, genotypesPopularity.remove(genotype) + 1);
        } else {
            genotypesPopularity.put(genotype, 1);
        }
    }

    private void removeGenotype(Genotype genotype) {
        if (genotypesPopularity.get(genotype) != null) {
            genotypesPopularity.put(genotype, genotypesPopularity.remove(genotype) - 1);
        }
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

        for(int i = 0; i < width; i++){
            for(int j = 0; j < height; j++){
                Vector2d v = new Vector2d(i,j);
                if(!map.isOccupied(v)){
                    numberOfFreeFields++;
                }
            }
        }
        return numberOfFreeFields;
    }

    public int getNumberOfPlants(){
        return numberOfPlants;
    }

    public float getAverageLifetime(){
        float avgLifetime = 0;
        for(Animal animal : deadAnimals){
            avgLifetime+=animal.getAge();
        }
        return avgLifetime/deadAnimals.size();
    }

    public float getAvgNumberOfKids(){
        float avgNumberOfKids = 0;
        for(Animal animal : aliveAnimals){
            avgNumberOfKids += animal.getNumberOfChildren();
        }
        return avgNumberOfKids/aliveAnimals.size();
    }

    public List<Vector2d> getMostPopularPlantsFields(){
        List<Vector2d> output = new LinkedList<>();
        int biggestNumberOfOccurances = 0;
        for(Vector2d position : plantsOccurence.keySet()){
            if(plantsOccurence.get(position) > biggestNumberOfOccurances){
                biggestNumberOfOccurances = plantsOccurence.get(position);
            }
        }

        for(Vector2d position : plantsOccurence.keySet()){
            if(plantsOccurence.get(position) == biggestNumberOfOccurances){
                output.add(position);
            }
        }

        return output;
    }

    public int getNumberOfAliveAnimals(){
        return aliveAnimals.size();
    }

    public int getNumberOfDeadAnimals(){
        return deadAnimals.size();
    }

}