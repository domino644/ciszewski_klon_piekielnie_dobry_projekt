package model;

import com.sun.scenario.animation.shared.AnimationAccessor;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StatsKeeperTest {

    @Test
    void animalDied() {
        WorldParameters worldParameters = new WorldParameters(10,10,4,8,10,4,8);
        WorldMap map = new WorldMap(worldParameters);
        StatsKeeper statsKeeper = map.getStatsKeeper();
        Animal a = null;
        for(Vector2d v : map.getAnimals().keySet()){
            if(!map.getAnimals().get(v).isEmpty()){
                a = (Animal) map.getAnimals().get(v).toArray()[0];
                break;
            }
        }

        statsKeeper.animalDied(a);

        assertEquals(3,statsKeeper.getNumberOfAliveAnimals());
        assertEquals(1, statsKeeper.getNumberOfDeadAnimals());
    }

    @Test
    void animalBorn() {
        WorldParameters worldParameters = new WorldParameters(10,10,4,8,10,4,8);
        WorldMap map = new WorldMap(worldParameters);
        StatsKeeper statsKeeper = map.getStatsKeeper();

        assertEquals(4,statsKeeper.getNumberOfAliveAnimals());
        assertEquals(0, statsKeeper.getNumberOfDeadAnimals());
    }

    @Test
    void plantGrown() {
        WorldParameters worldParameters = new WorldParameters(10,10,4,8,10,4,8);
        WorldMap map = new WorldMap(worldParameters);
        StatsKeeper statsKeeper = map.getStatsKeeper();

        statsKeeper.plantGrown(new Vector2d(0,0));

        assertEquals(9,statsKeeper.getNumberOfPlants());
    }

    @Test
    void plantEaten() {
        WorldParameters worldParameters = new WorldParameters(10,10,4,8,10,4,8);
        WorldMap map = new WorldMap(worldParameters);
        StatsKeeper statsKeeper = map.getStatsKeeper();

        statsKeeper.plantEaten();

        assertEquals(7, statsKeeper.getNumberOfPlants());
    }

    @Test
    void getMostPopularGenotype() {
        //TODO funkcja do testowania losowych funkcji
    }

    @Test
    void getAverageEnergyLevel() {
        WorldParameters worldParameters = new WorldParameters(10,10,4,8,10,4,8);
        WorldMap map = new WorldMap(worldParameters);
        StatsKeeper statsKeeper = map.getStatsKeeper();

        assertEquals(10.0, statsKeeper.getAverageEnergyLevel());
    }

    @Test
    void getNumberOfFreeFields() {
        //TODO funkcja do testowania losowych funkcji
    }

    @Test
    void getNumberOfPlants() {
        WorldParameters worldParameters = new WorldParameters(10,10,4,8,10,4,8);
        WorldMap map = new WorldMap(worldParameters);
        StatsKeeper statsKeeper = map.getStatsKeeper();

        assertEquals(8, statsKeeper.getNumberOfPlants());
    }

    @Test
    void getAverageLifetime() {
        WorldParameters worldParameters = new WorldParameters(10,10,4,8,10,4,8);
        WorldMap map = new WorldMap(worldParameters);
        StatsKeeper statsKeeper = map.getStatsKeeper();
        Animal a = null;
        for(Vector2d v : map.getAnimals().keySet()){
            if(!map.getAnimals().get(v).isEmpty()){
                a = (Animal) map.getAnimals().get(v).toArray()[0];
                break;
            }
        }

        statsKeeper.animalDied(a);


        assertEquals(0 , statsKeeper.getAverageLifetime());
    }

    @Test
    void getAvgNumberOfKids() {
        WorldParameters worldParameters = new WorldParameters(10,10,4,8,10,4,8);
        WorldMap map = new WorldMap(worldParameters);
        StatsKeeper statsKeeper = map.getStatsKeeper();

        assertEquals(0, statsKeeper.getAvgNumberOfKids());
    }

    @Test
    void getMostPopularPlantsFields() {
        //TODO funkcja do testowania losowych funkcji
    }

    @Test
    void getNumberOfAliveAnimals() {
        WorldParameters worldParameters = new WorldParameters(10,10,4,8,10,4,8);
        WorldMap map = new WorldMap(worldParameters);
        StatsKeeper statsKeeper = map.getStatsKeeper();

        assertEquals(4, statsKeeper.getNumberOfAliveAnimals());
    }

    @Test
    void getNumberOfDeadAnimals() {
        WorldParameters worldParameters = new WorldParameters(10,10,4,8,10,4,8);
        WorldMap map = new WorldMap(worldParameters);
        StatsKeeper statsKeeper = map.getStatsKeeper();

        assertEquals(0, statsKeeper.getNumberOfDeadAnimals());
    }
}