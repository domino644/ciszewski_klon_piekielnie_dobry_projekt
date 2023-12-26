package interfaces;

import model.Vector2d;

public interface MoveValidator extends GetRandomVector {
    boolean isOutOfBounds(Vector2d position);
}
