package model;

import interfaces.GetRandomVector;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;

public class RandomVectorGenerator implements GetRandomVector {

    private final int width;
    private final int height;
    private final Random random =new Random();
    private final RandomGrass randomGrass;

    public RandomVectorGenerator(int width,int height){
        this.width = width;
        this.height = height;
        randomGrass = new RandomGrass(width, height);
    }

    @Override
    public Vector2d RandomVector(){
        return new Vector2d(random.nextInt(width), random.nextInt(height));
    }

    public ArrayList<Vector2d> RandomVectorAnimal(int n){
        ArrayList<Vector2d> result = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            result.add(this.RandomVector());
        }
        return result;
    }

    private void completeDataInArray(ArrayList<Integer> arrayList,int bottomRowList, int topRowList,Vector2d[] occupiedPositions){
        for (int i = bottomRowList*width; i < topRowList*width; i++) {
            int finalI = i;
            boolean exist = Arrays.stream(occupiedPositions).anyMatch(val -> (val.getX()+val.getY()*width) == finalI);
            if (!exist){
                arrayList.add(i);
            }
        }

    }



    public ArrayList<Vector2d> RandomVectorGrass(Vector2d[] occupiedPositions,int n){
        return randomGrass.RandomVectorGrass(occupiedPositions, n);
    }
}
