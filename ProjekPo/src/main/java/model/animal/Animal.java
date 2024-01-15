package model.animal;

import enums.MapDirection;
import interfaces.MoveValidator;
import interfaces.MoveableWorldElement;
import model.Genotype;
import model.Vector2d;

import java.util.ArrayList;
import java.util.Random;

public class Animal implements MoveableWorldElement {
    private Vector2d position;
    private final Genotype genotype;
    private int currentGen;
    private int energyLevel;
    private MapDirection orientation;
    private int age = 0;
    private int numberOfChildren = 0;
    private final Random random= new Random();

    public Animal(Vector2d position,int numberOfGenes,int energyLevel){
        setPositionAndEnergyLevel(position,energyLevel);
        genotype = new Genotype(numberOfGenes);
        randomGene(numberOfGenes);
    }

    public Animal(Vector2d position,Genotype genotype,int energyLevel){
        setPositionAndEnergyLevel(position,energyLevel);
        this.genotype = genotype;
    }

    private void setPositionAndEnergyLevel(Vector2d position,int energyLevel){
        this.position = position;
        this.energyLevel = energyLevel;
        randomOrientation();
    }

    @Override
    public void move(MoveValidator moveValidator) {
        int changeOrientation = genotype.geneAt(currentGen);
        currentGen++;
        currentGen = currentGen%genotype.getGeneLength();
        for (int i = 0; i < changeOrientation; i++){
            orientation = orientation.next();
        }
        Vector2d moveVector = orientation.toUnitVector();
        Vector2d newPosition = position.add(moveVector);
        if (!moveValidator.isOutOfBounds(newPosition)){
            newPosition = moveValidator.RandomVector();
        }
        position = newPosition;
        age ++;
    }

    @Override
    public Vector2d getPosition() {
        return position;
    }

    public Animal reproduceAnimal(Animal animal,int reproducingEnergy,
                                  int minimalMutationNumber,int maximalMutationNumber){
        Genotype genotype1 = prepareGenotype(animal);
        this.decreaseEnergyLevel(reproducingEnergy);
        animal.decreaseEnergyLevel(reproducingEnergy);
        int modificationNumber = minimalMutationNumber + random.nextInt(1+maximalMutationNumber-minimalMutationNumber);
        for (int i = 0; i < modificationNumber; i++) {
            genotype1.changeGene();
        }
        return new Animal(position,genotype1,2*reproducingEnergy);
    }

    private Genotype prepareGenotype(Animal animal){
        int sumOfEnergy = energyLevel + animal.getEnergyLevel();
        int thisAnimalGenesNumber = Math.round(((float) energyLevel /sumOfEnergy) * genotype.getGeneLength());
        int animalGenesNumber = Math.round(((float) animal.getEnergyLevel() /sumOfEnergy) * genotype.getGeneLength());
        int[] thisAnimalGenes = genotype.getGenes();
        int[] animalGenes = animal.genotype.getGenes();
        ArrayList<Integer> futureAnimalGenes = new ArrayList<>();
        // Wylosowanie 0 oznacza lewą stronę genów mocniejszego zwierzaka a wylosowanie 1 prawą stronę genów
        if (random.nextInt(2) == 0){
            for (int i = 0; i < thisAnimalGenesNumber; i++) {
                futureAnimalGenes.add(thisAnimalGenes[i]);
            }
            for (int i = thisAnimalGenesNumber; i < thisAnimalGenesNumber+animalGenesNumber; i++) {
                futureAnimalGenes.add(animalGenes[i]);
            }
        }
        else {
            for (int i = 0; i < animalGenesNumber; i++) {
                futureAnimalGenes.add(animalGenes[i]);
            }
            for (int i = animalGenesNumber; i < thisAnimalGenesNumber+animalGenesNumber; i++) {
                futureAnimalGenes.add(thisAnimalGenes[i]);
            }
        }
        return new Genotype(futureAnimalGenes.stream().mapToInt(Integer::intValue).toArray());
    }

    public void decreaseEnergyLevel(int x){
        energyLevel = energyLevel - x;
    }

    public void increaseEnergyLevel(int x){
        energyLevel = energyLevel + x;
    }

    public void increaseChildNumber(){
        numberOfChildren++;
    }

    public int getEnergyLevel() { return energyLevel;}

    public int getAge() { return age;}

    public int getNumberOfChildren() { return numberOfChildren;}

    public Genotype getGenotype() { return genotype;}

    private void randomOrientation(){
        Random random = new Random();
        this.orientation = MapDirection.numberToMapDirection(random.nextInt(8));
    }
    private void randomGene(int numberOfGenes){
        Random random = new Random();
        this.currentGen = random.nextInt(numberOfGenes);
    }


}
