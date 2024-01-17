package model.plant;

import interfaces.WorldElement;
import model.Vector2d;

public class Plant implements WorldElement {
    private final Vector2d position;
    public Plant(Vector2d position){
        this.position = position;
    }

    @Override
    public Vector2d getPosition() {
        return position;
    }
}
