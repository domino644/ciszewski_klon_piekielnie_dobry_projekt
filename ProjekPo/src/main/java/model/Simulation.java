package model;

import java.util.ArrayList;
import java.util.HashMap;

public class Simulation implements Runnable{

    private final WorldMap map;
    private final MapVisualizer mapVisualizer;
    private final AnimalMove animalMove;
    private final AnimalEats animalEats;
    private final AnimalReproducing animalReproducing;
    private final AnimalClear animalClear;
    private final PlantsRegenerator plantsRegenerator;
    private boolean simulationPlay = false;
    private boolean killSimulation = false;

    public Simulation (WorldMap map,SimulationParameters simulationParameters){
        this.map = map;
        this.mapVisualizer = new MapVisualizer(map);
        animalMove = new AnimalMove(map,simulationParameters);
        animalEats = new AnimalEats(map,simulationParameters);
        animalReproducing = new AnimalReproducing(map,simulationParameters);
        animalClear = new AnimalClear(map);
        plantsRegenerator = new PlantsRegenerator(map,simulationParameters);
    }

    private ArrayList<Animal> allAnimalsOnBoard(){
        ArrayList<Animal> allAnimals = new ArrayList<>();
        for (ArrayList<Animal> list : map.getAnimals().values()) {
            allAnimals.addAll(list);
        }
        return allAnimals;
    }

    public void dailyMapChange(){
        ArrayList<Animal> allAnimalsOnBoard = allAnimalsOnBoard();
        animalMove.animalMoves(allAnimalsOnBoard);
        animalEats.animalEats();
        animalReproducing.animalReproducing();
        animalClear.clearMap(allAnimalsOnBoard);
        plantsRegenerator.plantsRegenerate();
    }

    @Override
    public void run() {
        while (!killSimulation) {
            try {
                while (simulationPlay) {
                    this.dailyMapChange();
                    System.out.println(mapVisualizer.draw(map.getLowerBoundary(), map.getUpperBoundary()));
                    Thread.sleep(500);
                }
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
                throw new RuntimeException();
            }
        }
    }

    public void setSimulationPlay(boolean simulationPlay) {
        this.simulationPlay = simulationPlay;
    }

    public void setKillSimulation(boolean killSimulation) {
        this.killSimulation = killSimulation;
    }
}
