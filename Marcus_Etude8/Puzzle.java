import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Puzzle.java : Used to describe an instance of a puzzle for a game.
 * It captures all the information about the tiles whether they are filled, unfilled, blocked
 * in real time.
 *
 */
public class Puzzle {

    private int xDimension; // x-dimension of the puzzle, in other words, no. of vertical lines
    private int yDimension; // y-dimension of the puzzle, in other words, no. of horizontal lines
    private final List<PuzzlePiece> puzzlePieces; // list of puzzle pieces which constitute the puzzle
    private int tilesFilled = 0;  // keeps track of no. of tiles which gets filled
    private int tilesToBeFilled = 0; // keep track of the no. of tiles which is EMPTY intially, and needs to be filled

    public int getTilesFilled() {
        return tilesFilled;
    }

    public int getTilesToBeFilled() {
        return tilesToBeFilled;
    }

    public Puzzle(List<String> inputPuzzle) {
        this.puzzlePieces = new ArrayList<>();
        xDimension = 0;
        yDimension = 0;
        for (String input: inputPuzzle) {
            if (xDimension == 0) {
                xDimension = input.length(); // length of each row will be same
            }
            parsePuzzleInputLine(input, yDimension);
            yDimension++;
        }
    }

    // parsing the single line of puzzle from the STDIN
    private void parsePuzzleInputLine(String input, int yDimension) {
        int xCoordinate = 0;
        for (char puzzleTile : input.toCharArray()) {
            // if its a blocked tile
            if (puzzleTile == '*') {
                puzzlePieces.add(new PuzzlePiece(new Coordinate(xCoordinate++, yDimension), PuzzlePieceMarking.BLOCKED));
            }
            // if its an empty tile
            else {
                tilesToBeFilled++; // increment the counter as this empty tile needs to filled
                puzzlePieces.add(new PuzzlePiece(new Coordinate(xCoordinate++, yDimension), PuzzlePieceMarking.EMPTY));
            }
        }
    }

    // returns the puzzle piece of the given puzzle based on the given coordinate
    // it basically compares the x and y component of the coordinate with the puzzle pieces
    // and returns the matched one.
    public PuzzlePiece getPuzzlePiece(Coordinate coordinate) {
        return puzzlePieces.stream()
                .filter(puzzlePiece -> puzzlePiece.getCoordinate().equals(coordinate))
                .findFirst()
                .get();
    }

    public int getxDimension() {
        return xDimension;
    }

    public int getyDimension() {
        return yDimension;
    }

    /**
     * Tries to insert a given pentamino starting at a given coordinate in the puzzle
     * If successful, fills it and returns true (along with incrementing tilesFilled counter by 5)
     * otherwise, returns false leaving the puzzle unchanged.
     */
    public boolean insertPentamino(Pentamino pentamino, Coordinate baseCoordinate) {
        if (!insertionOfPentaminoPossible(pentamino, baseCoordinate))
            return false;
        for (Coordinate coordinate: pentamino.getCoordinates()) {
            Coordinate newCoordinate = baseCoordinate.getAddedCoordinate(coordinate);
            PuzzlePiece puzzlePiece = getPuzzlePiece(newCoordinate);
            puzzlePiece.setPentaminoType(pentamino.getPentaminoType());
            puzzlePiece.setPuzzlePieceMarking(PuzzlePieceMarking.FILLED);
        }
        tilesFilled += 5; // as each pentamino consists of 5 tiles
        return true;
    }

    // checks if the given coordinate is with-in the bounds for the given puzzle board
    private boolean validCoordinate(Coordinate coordinate) {
        return coordinate.getX() >= 0 && coordinate.getX() < xDimension
                && coordinate.getY() >= 0 && coordinate.getY() < yDimension;
    }

    /**
     *  Helper method to check if its possible to insert pentamino at a given coordinate.
     *  It DOES NOT actually do the insertion, so no change to puzzle, it just checks if its possible,
     *  by checking if the given coordinates and valid, and those tiles are empty.
     */
    private boolean insertionOfPentaminoPossible(Pentamino pentamino, Coordinate baseCoordinate) {
        for (Coordinate coordinate: pentamino.getCoordinates()) {
            Coordinate newCoordinate = baseCoordinate.getAddedCoordinate(coordinate);
            if (!validCoordinate(newCoordinate))
                return false;
            if (!getPuzzlePiece(newCoordinate).isEmpty())
                return false;
        }
        return true;
    }

    /**
     * Print the puzzle board for the user.
     */
    public void print() {
        int currentYCoordinate = 0; // store the current line no. of the puzzle to be printed

        /**
         * First puzzle pieces are sorted by their coordinates, so that pieces with lower y-coordinate
         * comes earlier than those of higher y-coordinates. Similarly, in case of same y-coordinate,
         * preference is given to lower x-coordinate. s
         */
        for (PuzzlePiece puzzlePiece : puzzlePieces.stream().sorted(
                (o1, o2) -> {
                    if (o1.getCoordinate().getY() == o2.getCoordinate().getY())
                        return o1.getCoordinate().getX() - o2.getCoordinate().getX();
                    return o1.getCoordinate().getY() - o2.getCoordinate().getY();
                }).collect(Collectors.toList())) {

            // if puzzlePiece is of next line than that of current line (currentYCoordinate)
            // then increments the currentYCoordinate
            if (puzzlePiece.getCoordinate().getY() > currentYCoordinate) {
                System.out.println();
                currentYCoordinate++;
            }

            // Prints the puzzle piece according to its state
            switch (puzzlePiece.getPuzzlePieceMarking()) {
                // if already filled with some pentamino, print the pentamino label
                case FILLED:
                    System.out.print(puzzlePiece.getPentaminoType());
                    break;
                // if in blocked state, print the block symbol
                case BLOCKED:
                    System.out.print("*");
                    break;
                // if neither of them, then it is in empty state, so print empty symbol
                default:
                    System.out.print(".");
                    break;
            }
        }
        // adds a new line after the end of printing the puzzle for clarity
        System.out.println();
    }
}
