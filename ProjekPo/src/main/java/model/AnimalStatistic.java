package model;

import enums.MapDirection;

public record AnimalStatistic(Vector2d position,MapDirection orientation,int[] genotype,
                              int activeGenome, int energy, int eatenGrassesNumber, int children,
                              int successors, int age, int bornDate) {

    public String[] animalStringStats(){
        return new String[]{position.toString(),orientation.toString(),genotype.toString(),
        Integer.toString(activeGenome),Integer.toString(energy),Integer.toString(eatenGrassesNumber),
                Integer.toString(children),Integer.toString(successors),Integer.toString(age),
                Integer.toString(bornDate)};
    }

}
