package model;

import interfaces.MoveableWorldElement;

public class Animal implements MoveableWorldElement {
    private final Vector2d position;
    public Animal(Vector2d position){
        this.position = position;
    }

    @Override
    public void move(WorldMap map) {
        //TODO
    }

    @Override
    public Vector2d getPosition() {
        return position;
    }
}
