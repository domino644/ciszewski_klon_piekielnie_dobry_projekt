import model.RandomVectorGenerator;
import model.Vector2d;
import model.WorldMap;

public class World {

    public static void main(String[] args) {
        Vector2d[] p = {new Vector2d(1,1)};
        Vector2d[] a = {new Vector2d(1,1),new Vector2d(0,0),new Vector2d(2,2),new Vector2d(3,3)};
//        WorldMap m = new WorldMap(-1, -1, p, a);
        RandomVectorGenerator x = new RandomVectorGenerator(5,10);
//        for (int i = 0; i < 10; i++) {
//            System.out.println(x.RandomVector());
//        }
        System.out.println(x.RandomVectorGrass(p,10));
    }
}