package model;

import interfaces.GetRandomVector;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;

public class RandomVectorGenerator implements GetRandomVector {

    private final int width;
    private final int height;

    public RandomVectorGenerator(int width,int height){
        this.width = width;
        this.height = height;
    }

    @Override
    public Vector2d RandomVector(){
        Random random = new Random();
        return new Vector2d(random.nextInt(width), random.nextInt(height));
    }

    public ArrayList<Vector2d> RandomVectorGrass(Vector2d[] occupiedPositions,int n){
        Random random = new Random();
        ArrayList<Vector2d> result = new ArrayList<>();
        HashSet<Integer> occupiedNumbers = new HashSet<>();
        ArrayList<Integer> uniqueNumbers = new ArrayList<>();
        int area = height*width;
        int temporaryIndex;
        int temporaryNumber;
        int centralRowsNumber = (int) ((height - (height%5))* 0.2);
        int leftCentral = (int) Math.floor(0.4*height) * width;
        int rightCentral = leftCentral + centralRowsNumber*width;
        int sector;
        int possibleFirstDraw = 10;
        int possibleSecondDrawLeft = 0;
        int possibleSecondDrawRight = 2;
        for (Vector2d v:occupiedPositions ) {
            occupiedNumbers.add(v.getX()*height+v.getY());
        }
        for (int i = 0; i < area; i++) {
            if (!occupiedNumbers.contains(i)){
                uniqueNumbers.add(i);
            }
            else{
                if (i <= leftCentral){
                    leftCentral--;
                    rightCentral--;
                }
                else if (i <= rightCentral){
                    rightCentral--;
                }
            }
        }
        if (leftCentral == 0){
            possibleSecondDrawLeft = 1;
        }
        if (rightCentral == uniqueNumbers.size()-1){
            possibleSecondDrawRight = 1;
        }
        if (leftCentral == rightCentral){
            possibleFirstDraw = 2;
        }
        for(int i=0; i<n;i++){
            sector = random.nextInt(possibleFirstDraw);
            if (sector < 2){
                sector = random.nextInt(possibleSecondDrawLeft,possibleSecondDrawRight);
                if (sector == 0){
                    temporaryIndex = random.nextInt(leftCentral);
                    temporaryNumber = uniqueNumbers.get(temporaryIndex);
                    leftCentral--;
                    rightCentral--;
                    if (leftCentral == 0){
                        possibleSecondDrawLeft = 1;
                    }
                }
                else {
                    temporaryIndex = random.nextInt(rightCentral,uniqueNumbers.size());
                    temporaryNumber = uniqueNumbers.get(temporaryIndex);
                    if (rightCentral == uniqueNumbers.size()-1){
                        possibleSecondDrawRight = 1;
                    }
                }
            }
            else{
                temporaryIndex = random.nextInt(leftCentral,rightCentral);
                temporaryNumber = uniqueNumbers.get(temporaryIndex);
                rightCentral--;
                if (leftCentral == rightCentral){
                    possibleFirstDraw = 2;
                }
            }
            result.add(new Vector2d(temporaryNumber%width,temporaryNumber/width));
            uniqueNumbers.remove(temporaryIndex);
            if (uniqueNumbers.size() == 0){
                break;
            }
        }
        return result;
    }
}
