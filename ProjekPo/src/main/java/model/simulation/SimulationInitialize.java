package model.simulation;

import model.records.SimulationParameters;
import model.WorldMap;
import model.records.WorldParameters;
import presenter.utils.JSONHandler;

public class SimulationInitialize {

    private final WorldMap worldMap;
    private final Simulation simulation;

    public SimulationInitialize(int width, int height, int numberOfAnimals, int numberOfPlants,
                                int numberOfPlantsGrowPerDay, int startAnimalEnergy, int minimalEnergyToReproduction,
                                int reproducingEnergy, int minimalMutationNumber, int maximalMutationNumber,
                                int genomeLength, int plantEnergy, int lostEnergyPerDay){
        WorldParameters worldParameters = new WorldParameters(width, height, numberOfAnimals,
                numberOfPlants, startAnimalEnergy, minimalEnergyToReproduction, genomeLength);
        SimulationParameters simulationParameters = new SimulationParameters(numberOfPlantsGrowPerDay,
                minimalEnergyToReproduction, reproducingEnergy, minimalMutationNumber,
                maximalMutationNumber, plantEnergy, lostEnergyPerDay);
        JSONHandler.objectToFile(worldParameters,"test3");
        worldMap = new WorldMap(worldParameters);
        simulation = new Simulation(worldMap,simulationParameters);
    }

    public Simulation getSimulation() {
        return simulation;
    }

    public WorldMap getWorldMap() {
        return worldMap;
    }
}
