package model.utils;

import model.Vector2d;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class RandomGrass {

    private final int width;
    private final int height;
    private ArrayList<Integer> bottomTopGrass;
    private ArrayList<Integer> centerGrass;
    private final int bottomRows;
    private final int topRows;
    private ArrayList<Vector2d> result;
    private final Random random =new Random();
    public RandomGrass(int width,int height){
        this.width = width;
        this.height = height;
        bottomRows = (int) Math.floor(height*0.4);
        topRows = bottomRows + (int) Math.round(height*0.2);
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

    private int getFromListIndex(ArrayList<Integer> list,int index){
        int result = list.get(index);
        list.remove(index);
        return result;
    }

    private Vector2d numberToVector(int n){
        return new Vector2d(n%width,n/width);
    }

    private void listChoice(int randomNumber){
        if (randomNumber < 2){
            result.add(numberToVector(getFromListIndex(bottomTopGrass,random.nextInt(bottomTopGrass.size()))));
        }
        else{
            result.add(numberToVector(getFromListIndex(centerGrass,random.nextInt(centerGrass.size()))));
        }
    }

    private void singleListGenerate(ArrayList<Integer> list,int n){
        for (int i = 0; i < n; i++) {
            result.add(numberToVector(getFromListIndex(list,random.nextInt(list.size()))));
            if (list.isEmpty()){
                break;
            }
        }
    }

    private void twoListGenerate(int n){
        for (int i = 0; i < n; i++) {
            listChoice(random.nextInt(10));
            if (bottomTopGrass.isEmpty()){
                singleListGenerate(centerGrass,n-1-i);
                break;
            }
            if (centerGrass.isEmpty()){
                singleListGenerate(bottomTopGrass,n-1-i);
                break;
            }
        }
    }

    public void setRandomSeed(int seed){
        random.setSeed(seed);
    }

    public ArrayList<Vector2d> RandomVectorGrass(Vector2d[] occupiedPositions, int n){
        result = new ArrayList<>();
        bottomTopGrass = new ArrayList<>();
        centerGrass = new ArrayList<>();
        completeDataInArray(bottomTopGrass,0,bottomRows,occupiedPositions);
        completeDataInArray(centerGrass,bottomRows,topRows,occupiedPositions);
        completeDataInArray(bottomTopGrass,topRows,height,occupiedPositions);
        if (bottomTopGrass.isEmpty() && centerGrass.isEmpty()){
            return result;
        }
        else if (bottomTopGrass.isEmpty()){
            singleListGenerate(centerGrass,n);
        }
        else if (centerGrass.isEmpty()){
            singleListGenerate(bottomTopGrass,n);
        }
        else{
            twoListGenerate(n);
        }
        return result;
    }
}
