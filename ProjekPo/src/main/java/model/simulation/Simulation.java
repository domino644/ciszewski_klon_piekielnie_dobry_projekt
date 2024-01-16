package model.simulation;

import model.utils.MapVisualizer;
import model.plant.PlantsRegenerator;
import model.records.SimulationParameters;
import model.WorldMap;
import model.animal.*;
import presenter.utils.CSVHandler;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Simulation implements Runnable {

    private final WorldMap map;
    private final AnimalMove animalMove;
    private final AnimalEats animalEats;
    private final AnimalReproducing animalReproducing;
    private final AnimalClear animalClear;
    private final PlantsRegenerator plantsRegenerator;
    private boolean simulationPlay = false;
    private boolean killSimulation = false;
    public List<String[]> stats = new LinkedList<>();
    public final boolean saveStats;
    public final String fileName;

    public Simulation(WorldMap map, SimulationParameters simulationParameters) {
        this.map = map;
        animalMove = new AnimalMove(map, simulationParameters);
        animalEats = new AnimalEats(map, simulationParameters);
        animalReproducing = new AnimalReproducing(map, simulationParameters);
        animalClear = new AnimalClear(map);
        plantsRegenerator = new PlantsRegenerator(map, simulationParameters);
        saveStats = simulationParameters.saveStats();
        fileName = simulationParameters.filename();
        if(saveStats){
            stats.add(new String[]{"Liczba zwierząt", "Liczba roślin", "Liczba wolnych pól", "Średni poziom energii", "Średnia długość życia", "Średnia liczba dzieci", "Najpopularniejszy genotyp"});
        }
    }

    private ArrayList<Animal> allAnimalsOnBoard() {
        ArrayList<Animal> allAnimals = new ArrayList<>();
        for (ArrayList<Animal> list : map.getAnimals().values()) {
            allAnimals.addAll(list);
        }
        return allAnimals;
    }

    public void dailyMapChange(int date) {
        ArrayList<Animal> allAnimalsOnBoard = allAnimalsOnBoard();
        animalMove.animalMoves(allAnimalsOnBoard);
        animalEats.animalEats();
        animalReproducing.animalReproducing(date);
        animalClear.clearMap(allAnimalsOnBoard, date);
        plantsRegenerator.plantsRegenerate();
        if (saveStats) {
            stats.add(map.getStatsKeeper().getDailtyStatsAsString());
        }
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
