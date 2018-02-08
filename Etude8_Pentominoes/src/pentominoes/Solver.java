package pentominoes;

import java.util.*;

public class Solver {
    static ArrayDeque<State> states = new ArrayDeque<>();
    static StringBuilder ticker = new StringBuilder();
    static long updated = System.currentTimeMillis();

    public static void solve(Board puzzle) {
        System.out.println(puzzle);

        states.clear();
        states.add(new State(puzzle, new HashSet<>()));
        
        if (!search()) System.out.println("Impossible.");
        
        for (State s : states) {
            System.err.println(s.board);
        }

        System.out.println(states.peekLast().board);
    }

    private static boolean search() {
        State state = states.peekLast();
        Board board = state.board;
        Set<Type> placed = state.placed;

        if (System.currentTimeMillis() - updated > 1000) {
            updated = System.currentTimeMillis();
            if (ticker.length() > 2) ticker = new StringBuilder();
            ticker.append(".");
            System.out.format("\r%-3s", ticker.toString());
            System.err.println(board);
        }
        
        for (Type t : Type.values()) {
            if (placed.contains(t)) continue;

            for (Pentomino p : Pentomino.PENTOMINOES.get(t)) {
                if (place(p, board)) {
                    placed.add(p.type);
                    states.add(new State(board, placed));
                    System.err.println(board);
                    
                    if (search()) {
                        return true;
                    } else {
                        states.pollLast(); 
                        placed.remove(p.type);   
                        board.remove(p);
                    }
                }
            }
        }
        
        return false;
    }

    private static boolean place(Pentomino p, Board board) {
        for (int row = 0; row < board.values.length; row++) {
            for (int col = 0; col < board.values[row].length; col++) {
                if (board.place(row, col, p)) {
                    if (hasHole(board)) {
                        board.remove(row, col, p);
                    } else {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    private static boolean hasHole(Board board) {
        int visitedVal = -3;
        int numEmpty = 0;
        int total = 0;

        for (int row = 0; row < board.values.length; row++) {
            for (int col = 0; col < board.values[row].length; col++) {
                int count = countEmpty(board, row, col, visitedVal);
                if (count != 0) {
                    visitedVal--;
                    total += count;
                } else {
                    continue;
                } 
                if (count % 5 != 0) {
                    System.err.print(board);
                    System.err.println(total);
                    cleanup(board);
                    return true;
                }
            }
        }

        cleanup(board);
        return false;
    }

    private static int countEmpty(Board board, int row, int col, int visitedVal) {
        int count = 0;

        if (board.get(row, col) == Board.EMPTY) {
            board.values[row][col] = visitedVal;
            count++;

            count += countEmpty(board, row, col - 1, visitedVal);
            count += countEmpty(board, row, col + 1, visitedVal);
            count += countEmpty(board, row - 1, col, visitedVal);
            count += countEmpty(board, row + 1, col, visitedVal);
        }

        return count;
    }

    private static void cleanup(Board board) {
        for (int row = 0; row < board.values.length; row++) {
            for (int col = 0; col < board.values[row].length; col++) {
                if (board.get(row, col) < -2) {
                    board.values[row][col] = Board.EMPTY;
                }
            }
        }
    }
    
    private static class State {
        final Board board;
        final Set<Type> placed;

        public State(Board board, Set<Type> placed) {
            this.board = new Board(board);
            this.placed = new HashSet<>(placed);
        }
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        List<String> puzzle = new ArrayList<>();
        
        while (in.hasNextLine()) {
            String line = in.nextLine();
            
            if (!line.equals("")) puzzle.add(line);

            if (line.equals("") || !in.hasNextLine()) {
                Solver.solve(new Board(puzzle));
            }
        }

        in.close();
    }
}
