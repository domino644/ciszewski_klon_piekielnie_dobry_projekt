package interfaces;

import model.Vector2d;

public interface MoveValidator {
    boolean isOutOfBounds(Vector2d position);
}
