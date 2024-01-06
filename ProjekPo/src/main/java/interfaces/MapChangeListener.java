package interfaces;

import model.WorldMap;

public interface MapChangeListener {

    void mapChanged(WorldMap worldMap,String message);
}
