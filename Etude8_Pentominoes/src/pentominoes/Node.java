package pentominoes;

import java.util.*;

public class Node{

    Board puzzle;
    ArrayDeque<Type> leftOverPentominoes = new ArrayDeque<Type>();
    boolean visited = false;
    
    public Node(Board puzzle, ArrayDeque<Type> leftOverPentominoes){
        this.puzzle = puzzle;
        this.leftOverPentominoes = leftOverPentominoes;
    }

    public String toString(){
        StringBuilder output = new StringBuilder();
        output.append("\n" +puzzle.toString()+ "\n");
        
        output.append(leftOverPentominoes+ "\n");

        return output.toString();
    }

}
