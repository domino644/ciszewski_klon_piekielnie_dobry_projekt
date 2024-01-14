package model;

import java.util.ArrayList;
import java.util.HashMap;

public class AnimalMove {

    private final WorldMap map;
    private final int lossEnergyPerDay;

    public AnimalMove(WorldMap map,SimulationParameters simulationParameters){
        this.map = map;
        lossEnergyPerDay = simulationParameters.lostEnergyPerDay();
    }
   public void animalMoves(ArrayList<Animal> animalsOnBoard){
        HashMap<Vector2d,ArrayList<Animal>> animals = map.getAnimals();
        Vector2d prevMovePosition;
        Vector2d afterMovePosition;
        for (Animal animal : animalsOnBoard) {
            prevMovePosition = animal.getPosition();
            animal.move(map);
            animal.decreaseEnergyLevel(lossEnergyPerDay);
            afterMovePosition = animal.getPosition();
            if (prevMovePosition != afterMovePosition){
                animals.get(prevMovePosition).remove(animal);
                animals.get(afterMovePosition).add(animal);
                map.mapChangedEmit("");
            }
        }
    }
}
