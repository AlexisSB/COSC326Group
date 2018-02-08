import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Game.java : Captures a single instance of game which consists of a
 * Board puzzle, and Pentaminoes available to fill the board.
 * Also, contains the driver program (main method).
 *
 */
public class Game {

    private final Puzzle puzzle; // store the puzzle assosicated with current game
    private final List<Pentamino> pentaminoes; // store the different types of pentaminoes

    public Game(List<String> inputPuzzle) {
        this.puzzle = new Puzzle(inputPuzzle);
        this.pentaminoes = new ArrayList<>();
        createPentaminoes();
    }

    /*
    * initiate the solving of puzzle by starting from top-left corner of the puzzle
    * board, and recursivly travels toward the bottom-right end. If it reaches the bottom-right
    * end of the board, by abiding to given constraints, it implies the board is fillable, and thus
    * prints the result.
    * */
    public void solveGame() {
        solvePuzzle(0, 0);
        puzzle.print();
    }

    /*
    * Recursive method to solve the game. It takes the coordinate from where start to fill the board,
    * and proceeds in right and down direction (recursivily) to fill the entire puzzle (if possible).
    *
    * */
    private boolean solvePuzzle(int xCoordinate, int yCoordinate){
        // check if all the empty tiles are already filled, it means puzzle is solved
        if (this.puzzle.getTilesFilled() == this.puzzle.getTilesToBeFilled())
            return true;

        // if the current tile is already filled or is blocked, skips the current tile,
        // and traverse to right and down tile.
        PuzzlePiece puzzlePiece = this.puzzle.getPuzzlePiece(new Coordinate(xCoordinate, yCoordinate));
        if (puzzlePiece.getPentaminoType() != null ||
                puzzlePiece.getPuzzlePieceMarking() == PuzzlePieceMarking.BLOCKED) {
            if (xCoordinate + 1 < this.puzzle.getxDimension())
                if (solvePuzzle(xCoordinate+1, yCoordinate)) {
                    return true;
                }
            if (yCoordinate + 1 < this.puzzle.getyDimension()) {
                if (solvePuzzle(xCoordinate, yCoordinate + 1)) {
                    return true;
                }
            }
        }

        // if the current piece is empty, try to fill it with all the pentaminoes, one-by-one
        // and if succeeds, proceed similar with traversing to right and down (using recursion).
        for (Pentamino pentamino : pentaminoes) {
            if (puzzle.insertPentamino(pentamino, new Coordinate(xCoordinate, yCoordinate))) {
                if (xCoordinate + 1 < this.puzzle.getxDimension()) {
                    if (solvePuzzle(xCoordinate + 1, yCoordinate)) {
                        return true;
                    }
                }
                if (yCoordinate + 1 < this.puzzle.getyDimension()) {
                    if (solvePuzzle(xCoordinate, yCoordinate + 1)) {
                        return true;
                    }
                }
            }
        }

        // if nothing of the above holds true, it means the puzzle is not solvable
        return false;
    }

    // create all the 12 pentaminoes by Conway's representations
    // each pentamino consists of a list of 5 coordinates relative to base coordinate (0,0)
    // and a label.
    private void createPentaminoes() {
        List<PentaminoType> pentaminoTypes = Arrays.asList(PentaminoType.values());
        List<List<Coordinate>> pentaminoCoordinates = new ArrayList<>();

        // Pentamino O
        pentaminoCoordinates.add(Arrays.asList(
                new Coordinate(0,0),
                new Coordinate(0,1),
                new Coordinate(0,2),
                new Coordinate(0,3),
                new Coordinate(0,4)));

        // Pentamino P
        pentaminoCoordinates.add(Arrays.asList(
                new Coordinate(0,0),
                new Coordinate(0,1),
                new Coordinate(0,2),
                new Coordinate(1,0),
                new Coordinate(1,1)));

        // Pentamino Q
        pentaminoCoordinates.add(Arrays.asList(
                new Coordinate(0,0),
                new Coordinate(1,0),
                new Coordinate(1,1),
                new Coordinate(1,2),
                new Coordinate(1,3)));

        // Pentamino R
        pentaminoCoordinates.add(Arrays.asList(
                new Coordinate(0,0),
                new Coordinate(1,-1),
                new Coordinate(1,0),
                new Coordinate(1,1),
                new Coordinate(2,-1)));

        // Pentamino S
        pentaminoCoordinates.add(Arrays.asList(
                new Coordinate(0,0),
                new Coordinate(1,0),
                new Coordinate(2,0),
                new Coordinate(2,-1),
                new Coordinate(3,-1)));

        // Pentamino T
        pentaminoCoordinates.add(Arrays.asList(
                new Coordinate(0,0),
                new Coordinate(1,0),
                new Coordinate(2,0),
                new Coordinate(1,1),
                new Coordinate(1,2)));

        // Pentamino U
        pentaminoCoordinates.add(Arrays.asList(
                new Coordinate(0,0),
                new Coordinate(0,1),
                new Coordinate(1,1),
                new Coordinate(2,1),
                new Coordinate(3,0)));

        // Pentamino V
        pentaminoCoordinates.add(Arrays.asList(
                new Coordinate(0,0),
                new Coordinate(0,1),
                new Coordinate(0,2),
                new Coordinate(1,2),
                new Coordinate(2,2)));

        // Pentamino W
        pentaminoCoordinates.add(Arrays.asList(
                new Coordinate(0,0),
                new Coordinate(0,1),
                new Coordinate(1,1),
                new Coordinate(1,2),
                new Coordinate(2,2)));

        // Pentamino X
        pentaminoCoordinates.add(Arrays.asList(
                new Coordinate(0,0),
                new Coordinate(1,0),
                new Coordinate(2,0),
                new Coordinate(1,-1),
                new Coordinate(1,1)));

        // Pentamino Y
        pentaminoCoordinates.add(Arrays.asList(
                new Coordinate(0,0),
                new Coordinate(0,1),
                new Coordinate(0,2),
                new Coordinate(0,3),
                new Coordinate(0,4)));

        // Pentamino Z
        pentaminoCoordinates.add(Arrays.asList(
                new Coordinate(0,0),
                new Coordinate(1,-1),
                new Coordinate(1,0),
                new Coordinate(1,1),
                new Coordinate(2,2)));

        for (int i = 0; i < pentaminoCoordinates.size(); i++) {
            pentaminoes.add(new Pentamino(pentaminoTypes.get(i), pentaminoCoordinates.get(i)));
        }
    }

    // driver method
    // it basically tried to read puzzle one by one (separated by a blank line),
    // and call a game instance, and get the resultant puzzle (either solved or unsolved)
    public static void main(String[] args)throws IOException {
        List<String> inputPuzzle = new ArrayList<>();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        String input = null;
        while((input = bufferedReader.readLine()) != null) {
            if (input.length() == 0) {
                new Game(inputPuzzle).solveGame();
                inputPuzzle.clear();
            } else {
                inputPuzzle.add(input);
            }
        }
    }
}
