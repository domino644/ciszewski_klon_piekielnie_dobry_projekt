package model;

import enums.MapDirection;
import interfaces.MoveValidator;
import interfaces.MoveableWorldElement;

import java.util.ArrayList;
import java.util.Random;

public class Animal implements MoveableWorldElement {
    private Vector2d position;
    private final Genotype genotype;
    private int currentGen;
    private int energyLevel;
    private MapDirection orientation;
    private final int bornDate;
    private int dieDate = 0;
    private int age = 0;
    private int numberOfChildren = 0;
    private int eatenGrassNumber = 0;
    private final ArrayList<Animal> children = new ArrayList<>();
    private final Random random= new Random();

    public Animal(Vector2d position,int numberOfGenes,int energyLevel,int bornDate){
        this.bornDate = bornDate;
        setPositionAndEnergyLevel(position,energyLevel);
        genotype = new Genotype(numberOfGenes);
        randomGene(numberOfGenes);
    }

    public Animal(Vector2d position,Genotype genotype,int energyLevel,int bornDate){
        this.bornDate = bornDate;
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

    public AnimalStatistic getAnimalStats(){
        return new AnimalStatistic(position,orientation,genotype.getGenes(),genotype.geneAt(currentGen),energyLevel,
                eatenGrassNumber,children.size(),successorsCounter(),age,bornDate);
    }

    public Animal reproduceAnimal(Animal animal,int reproducingEnergy,
                                  int minimalMutationNumber,int maximalMutationNumber,int date){
        Animal children = NewAnimalProductionProcess.reproduceAnimal(this,animal,reproducingEnergy,minimalMutationNumber,maximalMutationNumber,date);
        this.addNewChild(children);
        animal.addNewChild(children);
        return children;
    }

    public void decreaseEnergyLevel(int x){
        energyLevel = energyLevel - x;
    }

    public void increaseEnergyLevel(int x){
        energyLevel = energyLevel + x;
    }

    public void eat(){
        eatenGrassNumber++;
    }

    public void die(int date){
        dieDate = date;
    }

    public void addNewChild(Animal child){
        children.add(child);
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

    private int successorsCounter(){
        int result = 0;
        for (Animal child: children) {
            result = result + child.successorsCounter();
        }
        return result;
    }
}
