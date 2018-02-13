package pentominoes;

import java.util.*;

/**
 * Represents a board for a pentomino puzzle.
 */
public class Board {
    static final int INVALID = -2;
    static final int EMPTY = -1;

    int[][] values;
 
    public Board(List<String> puzzle) {
        this(puzzle.toArray(new String[0]));
    }
    
    public Board(String... puzzle) {
        values = new int[puzzle.length][puzzle[0].length()];
        
        for (int row = 0; row < puzzle.length; row++) {
            for (int col = 0; col < puzzle[row].length(); col++) {
                values[row][col] = (puzzle[row].charAt(col) == '.') ? EMPTY : INVALID;
            }
        }
    }
    
    /** Copy constructor. */
    public Board(Board other) {
        values = Arrays.stream(other.values).map(int[]::clone).toArray(int[][]::new);
    }
    
    /**
     * Get the value of the board at position <code>row</code>, <code>col</code>.
     * 
     * @param row The row index of the value to get.
     * @param col The column index of the value to get.
     * @return The value at <code>row</code>, <code>col</code>. Returns invalid
     * if the value of the cell was <code>INVALID</code> or indicies were out 
     * of bounds.
     */
    public int get(int row, int col) {
        try {
            return values[row][col];
        } catch (ArrayIndexOutOfBoundsException e) {
            // System.err.println("Indicies " + row + " " + col + " out of bounds.");
        }
        
        return INVALID;
    }

    /**
     * Sets the value of the board at position <code>row</code>, 
     * <code>col</code> to the value of <code>type</code>.
     * 
     * @param row The row index of the value to set.
     * @param col The column index of the value to set.
     * @param type The value, or type of pentomino, to set the cell to.
     * @return <code>true</code> if the value was successfully set. 
     * Returns <code>false</code> if indicies are out of bounds, the cell
     * is not empty, or the cell is invalid.
     */
    public boolean set(int row, int col, Type type) {
        try {
            if (values[row][col] == EMPTY) {
                values[row][col] = type.ordinal();
                return true;
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            // System.err.println("Indicies " + row + " " + col + " out of bounds.");
        }

        return false; 
    }

    /**
     * Places the pentomino <code>p</code> on the board at position 
     * <code>row</code>, <code>col</code>.
     * 
     * @param row The row index of where to place the top left corner of 
     * the pentomino <code>p</code>.
     * @param col The column index of where to place the top left corner of 
     * the pentomino <code>p</code>.
     * @param p The pentomino to place.
     * @return <code>true</code> if the pentomino was successfully placed. 
     * Returns <code>false</code> otherwise.
     */
    public boolean place(int row, int col, Pentomino p) {
        // Calculate a column offset to take into account blank spaces.
        int offset = Integer.MAX_VALUE;

        for(Coordinate c : p.coordinates){
            if(c.x < offset && c.y == 0){
                offset = c.x;
            }
        }

        for (Coordinate c : p.coordinates) {
            if (!set(row + c.y, col + c.x - offset, p.type)) {
                remove(row, col, p);
                return false;
            }
        }

        return true;
    }

    /**
     * Finds the first blank space in the current state of the board.
     * Scans each row left to right to find a space.
     * 
     * @return a coordinate specifying the location of the first space.
     */
    public Coordinate getFirstBlankPlace(){
        for(int row = 0; row < values.length; row++) {
            for(int col = 0; col < values[row].length ; col++) {
                if(get(row, col) == EMPTY) {
                    return new Coordinate(col, row);
                }
            }
        }

        return null;
    }
    
    /**
     * Check if pentomino <code>p</code> can be placed at <code>row</code>, 
     * <code>col</code>.
     * 
     * @param row The row to place the pentomino at.
     * @param col The col to place the pentomino at.
     * @param p The pentomino to place.
     */
    public boolean canPlace(int row, int col, Pentomino p) {
        int offset = Integer.MAX_VALUE;

        for(Coordinate c : p.coordinates){
            if (c.x < offset && c.y == 0){
                offset = c.x;
            }
        }
               
        for (Coordinate c : p.coordinates) {
            if (get(row + c.y, col + c.x - offset) != EMPTY) {
                return false;
            }
        }

        return true;
    }

    /**
     * Removes <code>p</code> from the position <code>row</code>, 
     * <code>col</code> on the board.
     * 
     * @param row The row to remove from.
     * @param col The col to remove from.
     * @param p The pentomino to remove.
     */
    public void remove(int row, int col, Pentomino p) {
        for (Coordinate c : p.coordinates) {
            try {
                if (values[row + c.y][col + c.x] == p.type.ordinal()) {
                    values[row + c.y][col + c.x] = EMPTY;
                }
            } catch (ArrayIndexOutOfBoundsException e) {
                // System.err.println("Indicies " + row + " " + col + " out of bounds.");
            }
        }
    }

    /** 
     * Clears the squares on the board that are equal to the type of the passed in pentomino. 
     *
     * @param p The pentomino to remove from the board. 
     */
    public void remove(Pentomino p) {
        for (int[] row : values) {
            for (int col = 0; col < row.length; col++) {
                if (row[col] == p.type.ordinal()) {
                    row[col] = EMPTY;
                }
            }
        }
    }
      
    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();

        for (int[] row : values) {
            for (int col : row) {
                if (col == INVALID) {
                    str.append(" ");
                } else if (col == EMPTY) {
                    str.append("_");
                } else if (col < -2) {
                    str.append(col);
                } else {
                    str.append(Type.values()[col]);
                }

                str.append(" ");   
            }

            str.append("\n");
        }

        return str.toString();
    }
}
