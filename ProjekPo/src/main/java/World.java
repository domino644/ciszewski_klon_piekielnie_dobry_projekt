import model.Vector2d;
import model.WorldMap;

public class World {

    public static void main(String[] args) {
        Vector2d[] p = {new Vector2d(1,1)};
        Vector2d[] a = {new Vector2d(1,1)};
        WorldMap m = new WorldMap(-1, -1, p, a);
    }
}