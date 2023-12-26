package model;

import enums.MapDirection;
import interfaces.MoveValidator;
import interfaces.MoveableWorldElement;

public class Animal implements MoveableWorldElement {
    private Vector2d position;
    private final Genotype genotype;
    private int currentGen;
    private int energyLevel;
    private MapDirection orientation = MapDirection.NORTH;
    public Animal(Vector2d position,int numberOfGenes,int energyLevel){
        this.position = position;
        genotype = new Genotype(numberOfGenes);
        currentGen = 0;
        this.energyLevel = energyLevel;
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

    public void decreaseEnergyLevel(int x){
        energyLevel = energyLevel - x;
    }

    public int getEnergyLevel() {
        return energyLevel;
    }
}
