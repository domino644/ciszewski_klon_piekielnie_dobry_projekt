package model;

import interfaces.MoveValidator;
import interfaces.WorldElement;

import java.util.HashMap;

public class WorldMap implements MoveValidator {
    private final Vector2d lowerBoundary = new Vector2d(0, 0);
    private final Vector2d upperBoundary;
    private final HashMap<Vector2d, Animal> animals = new HashMap<>();
    private final HashMap<Vector2d, Plant> plants = new HashMap<>();

    public WorldMap(int width, int height, Vector2d[] animalsPositions, Vector2d[] plantsPositions) {
        if (width <= 0 || height <= 0) {
            throw new IllegalArgumentException("Width and height of map have to be greater than 0");
        }
        this.upperBoundary = new Vector2d(width, height);

        for (Vector2d v : animalsPositions) {
            animals.put(v, new Animal(v));
        }
        for (Vector2d v : plantsPositions) {
            plants.put(v, new Plant(v));
        }
    }

    public WorldElement objectAt(Vector2d position) {
        if (animals.get(position) != null) {
            return animals.get(position);
        }
        if (plants.get(position) != null) {
            return plants.get(position);
        }
        return null;
    }

    public boolean isOccupied(Vector2d position) {
        return animals.get(position) != null || plants.get(position) != null;
    }

    @Override
    public boolean isOutOfBounds(Vector2d position) {
        return position.getX() >= lowerBoundary.getX() &&
                position.getX() <= upperBoundary.getX() &&
                position.getY() >= lowerBoundary.getY() &&
                position.getY() <= upperBoundary.getY();
    }
}
