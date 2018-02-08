/**
 * PuzzlePiece: Encapsulates all the details about a single Puzzle piece
 * from the entire puzzle. It consists of the state of the piece like filled, unfilled,
 * blocked, and if filled already, which pentamino fills it.
 *
 */
public class PuzzlePiece {
    private final Coordinate coordinate;   // stores the current piece coordinate on the puzzle board
    private PuzzlePieceMarking puzzlePieceMarking; // stores the piece marking as one of the EMPTY, FILLED or BLOCKED
    private PentaminoType pentaminoType; // stores the pentamino label in case it is FILLED by a pentamino

    // Initially, the puzzle piece marking will be either EMPTY or BLOCKED, and thus
    // pentaminoType will be null. If it gets filled in future, changes will be done accordingly.
    public PuzzlePiece(Coordinate coordinate, PuzzlePieceMarking puzzlePieceMarking) {
        this.coordinate = coordinate;
        this.puzzlePieceMarking = puzzlePieceMarking;
        this.pentaminoType = null;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public PuzzlePieceMarking getPuzzlePieceMarking() {
        return puzzlePieceMarking;
    }

    public void setPuzzlePieceMarking(PuzzlePieceMarking puzzlePieceMarking) {
        this.puzzlePieceMarking = puzzlePieceMarking;
    }

    public boolean isEmpty() {
        return this.puzzlePieceMarking == PuzzlePieceMarking.EMPTY;
    }

    public PentaminoType getPentaminoType() {
        return pentaminoType;
    }

    public void setPentaminoType(PentaminoType pentaminoType) {
        this.pentaminoType = pentaminoType;
    }
}
