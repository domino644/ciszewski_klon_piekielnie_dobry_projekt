package model;

import interfaces.MapChangeListener;
import interfaces.MoveValidator;


import java.util.ArrayList;
import java.util.HashMap;


public class WorldMap implements MoveValidator, Runnable {
    private final Vector2d lowerBoundary = new Vector2d(0, 0);
    private final Vector2d upperBoundary;
    private final HashMap<Vector2d, ArrayList<Animal>> animals = new HashMap<>();
    private final HashMap<Vector2d, Plant> plants = new HashMap<>();
    private final RandomVectorGenerator randomVectorGenerator;
    private final Simulation simulation;
    private final ArrayList<MapChangeListener> listeners = new ArrayList<>();


    public WorldMap(int width, int height, int numberOfAnimals, int numberOfPlants,
                    int numberOfPlantsGrowPerDay, int startAnimalEnergy, int minimalEnergyToReproduction,
                    int reproducingEnergy, int minimalMutationNumber, int maximalMutationNumber,
                    int genomeLength, int plantEnergy, int lostEnergyPerDay) {
        if (width <= 0 || height <= 0) {
            throw new IllegalArgumentException("Width and height of map have to be greater than 0");
        }
        randomVectorGenerator = new RandomVectorGenerator(width, height);
        Vector2d[] animalsPositions = randomVectorGenerator.RandomVectorAnimal(numberOfAnimals).toArray(new Vector2d[0]);
        Vector2d[] plantsPositions = randomVectorGenerator.RandomVectorGrass(new Vector2d[0], numberOfPlants).toArray(new Vector2d[0]);
        this.upperBoundary = new Vector2d(width, height);
        prepareLists();
        for (Vector2d v : animalsPositions) {
            animals.get(v).add(new Animal(v, genomeLength, startAnimalEnergy));
        }
        for (Vector2d v : plantsPositions) {
            plants.put(v, new Plant(v));
        }
        simulation = new Simulation(this, numberOfPlantsGrowPerDay, minimalEnergyToReproduction
                , reproducingEnergy, minimalMutationNumber, maximalMutationNumber, plantEnergy
                , randomVectorGenerator, lostEnergyPerDay);
    }

    private void prepareLists() {
        Vector2d vector2d;
        for (int i = 0; i < upperBoundary.getX(); i++) {
            for (int j = 0; j < upperBoundary.getY(); j++) {
                vector2d = new Vector2d(i, j);
                animals.put(vector2d, new ArrayList<>());
            }
        }
    }

    public String objectAt(Vector2d position) {
        if (animals.get(position) != null && !animals.get(position).isEmpty()) {
            return "*";
        }
        if (plants.get(position) != null && plants.get(position) != null) {
            return "@";
        }
        return null;
    }

    public ArrayList<Animal> animalsAt(Vector2d position) {
        return animals.get(position);
    }

    public Plant plantAt(Vector2d position) {
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

    public HashMap<Vector2d, Plant> getPlants() {
        return plants;
    }

    public HashMap<Vector2d, ArrayList<Animal>> getAnimals() {
        return animals;
    }

    public void addListener(MapChangeListener listener) {
        listeners.add(listener);
    }

    public void mapChangedEmit(String message) {
        for (MapChangeListener listener : listeners) {
            listener.mapChanged(this, message);
        }
    }

    public Vector2d getLowerBoundary(){
        return lowerBoundary;
    }

    public Vector2d getUpperBoundary(){
        return upperBoundary;
    }

    @Override
    public void run() {
        MapVisualizer vis = new MapVisualizer(this);
        try {
            for (int i = 0; i < 10; i++) {
                System.out.println("Day: " + i);
                simulation.dailyMapChange();
                System.out.println(vis.draw(this.lowerBoundary, this.upperBoundary));
                Thread.sleep(500);
            }
        }catch (InterruptedException e){
            System.out.println(e.getMessage());
            throw new RuntimeException();
        }
    }
}
