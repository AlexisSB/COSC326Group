package pentominoes;

import java.util.*;

/**
 * Node used for depth first search in pentomino solver.
 * Each node contains a representation of the board with
 * pentominoes placed on it, and a list of the pentominoes
 * that are left to place on the board.
 */
public class Node {
    Board puzzle;
    ArrayList<Type> leftOverPentominoes = new ArrayList<Type>();
    boolean visited = false;
    
    public Node(Board puzzle, ArrayList<Type> leftOverPentominoes) {
        this.puzzle = puzzle;
        this.leftOverPentominoes = leftOverPentominoes;
    }

    @Override
    public String toString() {
        StringBuilder output = new StringBuilder();
        output.append("\n" + puzzle.toString() + "\n");
        output.append(leftOverPentominoes + "\n");
        return output.toString();
    }

}
