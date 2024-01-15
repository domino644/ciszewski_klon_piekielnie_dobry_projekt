package model;

import model.utils.RandomVectorGenerator;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class RandomVectorGeneratorTest {

    @Test
    void randomVectorAnimalAllVectorsInsideMap() {
        RandomVectorGenerator randomVectorGenerator = new RandomVectorGenerator(10,10);
        ArrayList<Vector2d> vectorList = randomVectorGenerator.RandomVectorAnimal(1000);
        Boolean result = vectorList.stream().allMatch(vector2d -> vector2d.precedes(new Vector2d(10,10)) && vector2d.follows(new Vector2d(0,0)));
        assertEquals(true,result);
    }
}