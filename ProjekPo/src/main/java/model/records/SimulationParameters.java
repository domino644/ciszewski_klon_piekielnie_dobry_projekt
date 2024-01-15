package model.records;

public record SimulationParameters(int numberOfPlantsGrowPerDay,int minimalEnergyToReproduction,
                                   int reproducingEnergy,int minimalMutationNumber, int maximalMutationNumber,
                                   int plantEnergy, int lostEnergyPerDay) {
}
