package pentominoes;

import java.util.*;

public class Solver {
    public void solve(Board puzzle) {
       
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        List<String> puzzle = new ArrayList<>();
        
        while (in.hasNextLine()) {
            String line = in.nextLine();
            
            if (!line.equals("")) puzzle.add(line);

            if (line.equals("") || !in.hasNextLine()) {
                new Solver().solve(new Board(puzzle));
            }
        }

        in.close();
    }
}
