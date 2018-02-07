package pentominoes;

import java.awt.Point;

/**
 *  Captures the coordinate on the board in the form of
 *  (x,y) considering origin as top-left corner, and going right as positive x-direction,
 *  and going down as positive y-direction.
 *
 */
public class Coordinate extends Point{
    public Coordinate(int x, int y) {
        super(x, y);
    }
    
    @Override
    public String toString() {
        return String.format("(%d, %d)", x, y);
    }
}
