package model;

import enums.MapDirection;
import interfaces.MoveValidator;
import interfaces.MoveableWorldElement;

import java.util.Random;

public class Animal implements MoveableWorldElement {
    private Vector2d position;
    private final Genotype genotype;
    private int currentGen;
    private int energyLevel;
    private MapDirection orientation;
    private int age = 0;
    private int numberOfChildren = 0;
    public Animal(Vector2d position,int numberOfGenes,int energyLevel){
        this.position = position;
        genotype = new Genotype(numberOfGenes);
        this.energyLevel = energyLevel;
        randomOrientation();
        randomGene(numberOfGenes);
    }

    public Animal(Vector2d position,int energyLevel){
        this(position,5,energyLevel);
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
    }

    @Override
    public Vector2d getPosition() {
        return position;
    }

    public Animal reproduceAnimal(Animal animal){
        this.getEnergyLevel();
    }

    public void decreaseEnergyLevel(int x){
        energyLevel = energyLevel - x;
    }
    public void increaseEnergyLevel(int x){
        energyLevel = energyLevel + x;
    }

    public int getEnergyLevel() {
        return energyLevel;
    }

    public int getAge() { return age;}

    public int getNumberOfChildren() { return numberOfChildren;}

    private void randomOrientation(){
        Random random = new Random();
        this.orientation = MapDirection.numberToMapDirection(random.nextInt(8));
    }
    private void randomGene(int numberOfGenes){
        Random random = new Random();
        this.currentGen = random.nextInt(numberOfGenes);
    }

}
