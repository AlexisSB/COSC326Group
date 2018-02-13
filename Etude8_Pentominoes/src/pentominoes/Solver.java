package pentominoes;

import java.util.*;

public class Solver {
    /* Global scanner object from stdin*/
    public static final Scanner in = new Scanner(System.in);
    /*Final solution to the puzzle*/
    public Node solution;

    /**
     * Solve manages the creation of root node and depth first search.
     * If there are not pentominoes read in then the standard
     * set of 12 will be used.
     * 
     * @param puzzle - the board to solve.
     * @param pentominoes - a list of pentominoes to use.
     */
    public void solve(Board puzzle, ArrayList<Type> pentominoes) {
        if (pentominoes.isEmpty()){
            pentominoes.add(Type.O);
            pentominoes.add(Type.P);
            pentominoes.add(Type.Y);
            pentominoes.add(Type.V);
            pentominoes.add(Type.X);
            pentominoes.add(Type.Q);
            pentominoes.add(Type.R);
            pentominoes.add(Type.Z);
            pentominoes.add(Type.U);
            pentominoes.add(Type.T);
            pentominoes.add(Type.W);
            pentominoes.add(Type.S);
        }
        
        Node root = new Node(puzzle, pentominoes);
        
        if (depthFirstSearch(root)) {
            System.out.println(solution.puzzle);
        } else {
            System.out.println("No Solution");
        }
    }

    /**
     * Recursive depth first search of the puzzle space.
     * Calls helper method DFSVisit.
     * 
     * @param root start of the search tree.
     * @return true if a solution found, false if no solution.
     */
    public boolean depthFirstSearch(Node root){
        if( DFSVisit(root)) {
            return true;
        } else {
            System.err.println("Failed");
            return false;
        }
    }

    /**
     * Recursive helper method for depth first search.
     * Expands nodes as they are found.
     * Checks the goal condition first.
     * In this case the goal condition is that there are no more
     * spaces to place a pentomino on the board i.e. its full.
     * 
     * @param node the node to explore.
     * @return true if the node meets the goal condition, false if no solution.
     */
    public boolean DFSVisit(Node node){
        node.visited = true;
        if(node.puzzle.getFirstBlankPlace() == null){
            solution = node;
            return true;
        }

        for(Node child : expandNode(node)){
            if(!child.visited){
                if(DFSVisit(child)){
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Explores the possible successors to a node.
     * Takes each pentomino from the node list and adds it to
     * the node board state at the first blank space to generate new nodes.
     * 
     * @param n node to expand
     * @return an ArrayList of the child nodes.
     */
    public ArrayList<Node> expandNode(Node n){
        Coordinate startLocation = n.puzzle.getFirstBlankPlace();
        int row = startLocation.y;
        int col = startLocation.x;
        ArrayList<Node> output = new ArrayList<Node>();

        for(Type type : n.leftOverPentominoes){
            for(Pentomino p : Type.PENTOMINOES.get(type)){
                if(n.puzzle.canPlace(row,col,p)){
                    Board clone = new Board(n.puzzle);
                    clone.place(row,col,p);
                    ArrayList<Type> clonedPento = new ArrayList<Type>();
                    clonedPento.addAll(n.leftOverPentominoes);
                    clonedPento.remove(type);
                    output.add(new Node(clone,clonedPento));
                }

            }
        }
        
        return output;
    }

    /**
     * Convert a line of char from the input into pentomino types.
     * List of types represents the pentominoes to be used in the
     * puzzle.
     * 
     * @param line - line of single characters o-z space seperated.
     * @return Collection of pentomino Types.
     */
    public static ArrayList<Type> inputPentominoes(String line){
        ArrayList<Type> output = new ArrayList<Type>();
        String pentominoString = line;
        Scanner charReader = new Scanner(pentominoString);

        while(charReader.hasNext()) {
            String pentominoChar = (charReader.next());

            Type type = Type.valueOf(pentominoChar.toUpperCase());
            output.add(type);
        }
        return output;
    }

    /**
     * Main method.
     * Reads in the list of pentominoes and the board description.
     * Passes this to the solve method for inspection.
     * 
     * @param args - no arguments used in this class.
     */
    public static void main(String[] args) {
        
        List<String> puzzle = new ArrayList<>();
        ArrayList<Type> inputPentominoes= new ArrayList<Type>();
        while (in.hasNextLine()) {
            String line = in.nextLine();

            if(line.matches("^[a-z ]+")) {
                inputPentominoes = inputPentominoes(line);
            } else {
                if (!line.equals("")) puzzle.add(line);
                        
                if (line.equals("") || !in.hasNextLine()) {
                    System.err.println(new Board(puzzle));
                    new Solver().solve(new Board(puzzle), inputPentominoes);
                    puzzle.clear();
                }
            }
        }

        in.close();
    }
}
