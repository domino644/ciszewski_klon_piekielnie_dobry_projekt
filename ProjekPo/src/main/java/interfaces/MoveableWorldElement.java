package interfaces;
import model.WorldMap;

public interface MoveableWorldElement extends WorldElement{
    void move(MoveValidator moveValidator);
}
