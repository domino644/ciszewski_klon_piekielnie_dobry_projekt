package model;

import java.util.ArrayList;

public class Simulation implements Runnable{

    private final WorldMap map;
    private final AnimalMove animalMove;
    private final AnimalEats animalEats;
    private final AnimalReproducing animalReproducing;
    private final AnimalClear animalClear;
    private final PlantsRegenerator plantsRegenerator;
    private boolean simulationPlay = false;
    private boolean killSimulation = false;

    public Simulation (WorldMap map,SimulationParameters simulationParameters){
        this.map = map;
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

    public void dailyMapChange(int date){
        ArrayList<Animal> allAnimalsOnBoard = allAnimalsOnBoard();
        animalMove.animalMoves(allAnimalsOnBoard);
        animalEats.animalEats();
        animalReproducing.animalReproducing(date);
        animalClear.clearMap(allAnimalsOnBoard,date);
        plantsRegenerator.plantsRegenerate();
    }

    @Override
    public void run() {
        int date = 0;
        while (!killSimulation) {
            try {
                while (simulationPlay) {
                    this.dailyMapChange(date);
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
