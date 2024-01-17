package model;

import model.utils.RandomGrass;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class RandomGrassTest {

    @Test
    void randomVectorGrassCreateGrass() {
        RandomGrass randomGrass = new RandomGrass(4,4);
        randomGrass.setRandomSeed(1000);
        Vector2d[] occupiedPositions = new Vector2d[]{new Vector2d(0,0),new Vector2d(1,3)};
        ArrayList<Vector2d> result = new ArrayList<>();
        result.add(new Vector2d(0,1));
        result.add(new Vector2d(2,1));
        result.add(new Vector2d(1,1));
        result.add(new Vector2d(2,2));
        assertArrayEquals(result.toArray(),randomGrass.RandomVectorGrass(occupiedPositions,4).toArray());
    }

    @Test
    void randomVectorGrassCantGenerate() {
        RandomGrass randomGrass = new RandomGrass(2,2);
        randomGrass.setRandomSeed(1000);
        Vector2d[] occupiedPositions = new Vector2d[]{new Vector2d(0,0),new Vector2d(0,1),new Vector2d(1,1),new Vector2d(1,0)};
        ArrayList<Vector2d> result = new ArrayList<>();
        assertArrayEquals(result.toArray(),randomGrass.RandomVectorGrass(occupiedPositions,4).toArray());
    }

    @Test
    void randomVectorGrassMidRowsCheck() {
        RandomGrass randomGrass = new RandomGrass(5,5);
        randomGrass.setRandomSeed(1000);
        Vector2d[] occupiedPositions = new Vector2d[]{};
        ArrayList<Vector2d> result = new ArrayList<>();
        result.add(new Vector2d(0,2));
        result.add(new Vector2d(2,2));
        result.add(new Vector2d(4,2));
        result.add(new Vector2d(0,1));
        result.add(new Vector2d(1,2));
        assertArrayEquals(result.toArray(),randomGrass.RandomVectorGrass(occupiedPositions,5).toArray());
    }
}