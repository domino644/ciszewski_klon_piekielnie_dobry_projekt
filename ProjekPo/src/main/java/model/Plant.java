package model;

import interfaces.WorldElement;

public class Plant implements WorldElement {
    private final Vector2d position;
    public Plant(Vector2d position){
        this.position = position;
    }

    @Override
    public Vector2d getPosition() {
        return null;
    }
}
