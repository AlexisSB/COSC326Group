import java.util.Objects;

/**
 *  Coordinate.java : Captures the coordinate on the board in the form of
 *  (x,y) considering origin as top-left corner, and going right as positive x-direction,
 *  and going down as positive y-direction.
 *
 */
public class Coordinate {
    private final int x; // x coordinate
    private final int y; // y coordinate

    public Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    /**
     * Adds a given coordinate to the base coordinate to obtain a new coordinate
     *
     * eg. Let's say current coordinate is (1,2) and you want to add coordinate(2,3) onto it,
     * so the result will be (3,5) obtained by adding x component and y component.
     * This method is useful for filling tiles with pentaminoes, as pentaminoes is
     * defined using (0,0) as base coordinate.
     */
    public Coordinate getAddedCoordinate(Coordinate coordinate) {
        return new Coordinate(this.x + coordinate.getX(), this.y + coordinate.getY());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coordinate that = (Coordinate) o;
        return x == that.x && y == that.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
