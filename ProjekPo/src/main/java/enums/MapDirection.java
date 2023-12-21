package enums;

import model.Vector2d;

public enum MapDirection {
    NORTH,
    EAST,
    SOUTH,
    WEST,
    NORTH_EAST,
    NORTH_WEST,
    SOUTH_EAST,
    SOUTH_WEST;

    public String toString(MapDirection direction) {
        return switch (direction) {
            case NORTH -> "Północ";
            case EAST -> "Wschód";
            case SOUTH -> "Południe";
            case WEST -> "Zachód";
            case NORTH_EAST -> "Północny wschód";
            case NORTH_WEST -> "Północny zachód";
            case SOUTH_EAST -> "Południowy wschód";
            case SOUTH_WEST -> "Południowy zachód";
        };
    }

    public MapDirection next(MapDirection direction) {
        return switch (direction) {
            case NORTH -> NORTH_EAST;
            case NORTH_EAST -> EAST;
            case EAST -> SOUTH_EAST;
            case SOUTH_EAST -> SOUTH;
            case SOUTH -> SOUTH_WEST;
            case SOUTH_WEST -> WEST;
            case WEST -> NORTH_WEST;
            case NORTH_WEST -> NORTH;
        };
    }

    public MapDirection previous(MapDirection direction) {
        return switch (direction) {
            case NORTH -> NORTH_WEST;
            case NORTH_WEST -> WEST;
            case WEST -> SOUTH_WEST;
            case SOUTH_WEST -> SOUTH;
            case SOUTH -> SOUTH_EAST;
            case SOUTH_EAST -> EAST;
            case EAST -> NORTH_EAST;
            case NORTH_EAST -> NORTH;
        };
    }

    public Vector2d toUnitVector(MapDirection direction) {
        return switch (direction) {
            case NORTH -> new Vector2d(0, 1);
            case EAST -> new Vector2d(1, 0);
            case SOUTH -> new Vector2d(0, -1);
            case WEST -> new Vector2d(-1, 0);
            case NORTH_EAST -> new Vector2d(1,1);
            case NORTH_WEST -> new Vector2d(-1,1);
            case SOUTH_EAST -> new Vector2d(1,-1);
            case SOUTH_WEST -> new Vector2d(-1,-1);
        };
    }

    public String toShortName(MapDirection direction) {
        return switch (direction) {
            case NORTH -> "N";
            case EAST -> "E";
            case SOUTH -> "S";
            case WEST -> "W";
            case NORTH_EAST -> "NE";
            case SOUTH_EAST -> "SE";
            case NORTH_WEST -> "NW";
            case SOUTH_WEST -> "SW";
        };
    }
}