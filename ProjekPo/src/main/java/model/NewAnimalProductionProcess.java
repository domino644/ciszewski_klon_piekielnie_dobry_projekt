package model;

import model.animal.Animal;

import java.util.ArrayList;
import java.util.Random;

public class NewAnimalProductionProcess {

    public static Animal reproduceAnimal(Animal animalStronger, Animal animal, int reproducingEnergy,
                                         int minimalMutationNumber, int maximalMutationNumber, int date){
        Random random= new Random();
        Genotype genotype1 = prepareGenotype(animalStronger,animal);
        animalStronger.decreaseEnergyLevel(reproducingEnergy);
        animal.decreaseEnergyLevel(reproducingEnergy);
        int modificationNumber = minimalMutationNumber + random.nextInt(1+maximalMutationNumber-minimalMutationNumber);
        for (int i = 0; i < modificationNumber; i++) {
            genotype1.changeGene();
        }
        return new Animal(animalStronger.getPosition(),genotype1,2*reproducingEnergy,date);
    }

    private static Genotype prepareGenotype(Animal animalStronger,Animal animal){
        Random random= new Random();
        int sumOfEnergy = animal.getEnergyLevel() + animal.getEnergyLevel();
        int strongerAnimalGenesNumber = Math.round(((float) animal.getEnergyLevel()  /sumOfEnergy) * animalStronger.getGenotype().getGeneLength());
        int animalGenesNumber = Math.round(((float) animal.getEnergyLevel() /sumOfEnergy) * animalStronger.getGenotype().getGeneLength());
        int[] strongerAnimalGenes = animalStronger.getGenotype().getGenes();
        int[] animalGenes = animal.getGenotype().getGenes();
        ArrayList<Integer> futureAnimalGenes;
        // Wylosowanie 0 oznacza lewą stronę genów mocniejszego zwierzaka a wylosowanie 1 prawą stronę genów
        if (random.nextInt(2) == 0){
            futureAnimalGenes = genesToList(strongerAnimalGenes,animalGenes,strongerAnimalGenesNumber, strongerAnimalGenes.length);
        }
        else {
            futureAnimalGenes = genesToList(animalGenes,strongerAnimalGenes,animalGenesNumber,strongerAnimalGenes.length);
        }
        return new Genotype(futureAnimalGenes.stream().mapToInt(Integer::intValue).toArray());
    }

    private static ArrayList<Integer> genesToList (int[] gentype1, int[] genotype2,int genesNumber1,int genesNumberTotal){
        ArrayList<Integer> futureAnimalGenes = new ArrayList<>();
        for (int i = 0; i < genesNumber1; i++) {
            futureAnimalGenes.add(gentype1[i]);
        }
        for (int i = genesNumber1; i < genesNumberTotal; i++) {
            futureAnimalGenes.add(genotype2[i]);
        }
        return futureAnimalGenes;
    }
}
