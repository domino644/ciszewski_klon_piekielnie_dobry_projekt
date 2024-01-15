package model;

import enums.MapDirection;

public record AnimalStatistic(Vector2d position,MapDirection orientation,int[] genotype,
                              int activeGenome, int energy, int eatenGrassesNumber, int children,
                              int successors, int age, int bornDate) {

}
