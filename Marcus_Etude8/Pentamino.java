import java.util.List;

/**
 * Pentamino.java: Defines a pentamino according to Conway's representation.
 * Each pentamino consists detail of the shape i.e. coordinate which will be occupied
 * by this pentamino relative to a given location and its type.
 */

public class Pentamino {
    private final PentaminoType pentaminoType;

    /*
     * mentions the coordinate of the five squares, assuming leftmost square at (0,0) and
     * using usual GUI indexing (top to bottom, left to right).
     *
     * So for pentamino #####, coordinates will be (0,0), (1,0), (2,0), (3,0) and (4,0)
     * and for ###, coordinates will be (0,0), (1,0), (2,0), (1,1) and (2,1)
      *         ##
      * and so on.
      * */
    private final List<Coordinate> coordinates;

    public Pentamino(PentaminoType pentaminoType, List<Coordinate> coordinates) {
        this.pentaminoType = pentaminoType;
        this.coordinates = coordinates;
    }

    public PentaminoType getPentaminoType() {
        return pentaminoType;
    }

    public List<Coordinate> getCoordinates() {
        return coordinates;
    }

}
