package model;

public record WorldParameters(int width, int height, int numberOfAnimals, int numberOfPlants,
                              int startAnimalEnergy, int minimalEnergyToReproduction,
                              int genomeLength) {
}
