import model.RandomVectorGenerator;
import model.Vector2d;
import model.WorldMap;

public class World {

    public static void main(String[] args) {
        Vector2d[] p = {new Vector2d(1,1)};
        Vector2d[] a = {new Vector2d(1,1),new Vector2d(0,0),new Vector2d(2,2),new Vector2d(3,3)};
        WorldMap map = new WorldMap(10,10,4, 8,
                2,15,5,3,4,7,8,3,3);
        map.run();
    }
}