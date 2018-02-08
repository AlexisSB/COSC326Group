package pentominoes;

import java.awt.Point;

/**
 *  A point representing a location in (x, y) coordinate space.
 */
public class Coordinate extends Point {
    public Coordinate(int x, int y) {
        super(x, y);
    }
    
    @Override
    public String toString() {
        return String.format("(%d, %d)", x, y);
    }
}
