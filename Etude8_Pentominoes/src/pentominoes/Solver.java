package pentominoes;

import java.util.*;

public class Solver {


    
    public void solve(Board puzzle) {
        ArrayDeque<Type> pentominoesToUse= new ArrayDeque<Type>();
        pentominoesToUse.add(Type.O);
        pentominoesToUse.add(Type.P);
        pentominoesToUse.add(Type.Y);
        pentominoesToUse.add(Type.V);
        pentominoesToUse.add(Type.X);
        pentominoesToUse.add(Type.Q);
        pentominoesToUse.add(Type.R);
        pentominoesToUse.add(Type.Z);
        pentominoesToUse.add(Type.U);
        pentominoesToUse.add(Type.T);
        pentominoesToUse.add(Type.W);
        pentominoesToUse.add(Type.S);
        
        Node root = new Node(puzzle, pentominoesToUse);

        Node debugNode;
        ArrayDeque<Type> debugPentominoes = new ArrayDeque<Type>();
        debugPentominoes.add(Type.X);
        debugPentominoes.add(Type.Q);
        debugPentominoes.add(Type.R);
        debugPentominoes.add(Type.Z);
        debugPentominoes.add(Type.U);
        debugPentominoes.add(Type.T);
        debugPentominoes.add(Type.W);
        debugPentominoes.add(Type.S);
        Board debugPuzzle  = new Board(puzzle);
        debugPuzzle.place(0,0,Type.PENTOMINOES.get(Type.O).get(0));
        //System.err.println(Type.PENTOMINOES.get(Type.P).get(0));
        debugPuzzle.place(0,1,Type.PENTOMINOES.get(Type.P).get(0));
        debugPuzzle.place(0,3,Type.PENTOMINOES.get(Type.Y).get(3));
        debugPuzzle.place(0,7,Type.PENTOMINOES.get(Type.V).get(2));
        //System.err.println("DebugPuzzle \n" +debugPuzzle);
        //System.err.println(expandNode(root));
        /*
        if (solve(puzzle, pentominoesToUse)){
            System.err.println("Success");
        }else {
            System.err.println("Failed");
        }
        */
        debugNode = new Node(debugPuzzle,debugPentominoes);
        depthFirstSearch(root);
        //depthFirstSearch(root);
        System.err.println(solution);
    }

    Node solution;
    public boolean depthFirstSearch(Node root){

        if( DFSVisit(root)){
            return true;
        }else{
            System.err.println("Failed");
            return false;
        }
    }

    
    public boolean DFSVisit(Node node){
        /*
        if(debugCount < 100){
            debugCount++;
        }else{
            System.exit(0);
        }
        */
        //System.out.println(node);
        node.visited = true;
        if(node.puzzle.getFirstBlankPlace() == null){
            solution = node;
            return true;
        }
        ArrayList<Node> children = expandNode(node);
        //System.err.println(children);
        for(Node child : children){
            //System.err.println(child);
            if(!child.visited){
                if(DFSVisit(child)){
                    return true;
                }
            }
        }
        return false;
    }

    

    
    int debugCount = 0;
    HashSet<String> seenBoardStates = new HashSet<String>();

    public ArrayList<Node> expandNode(Node n){

        int[] startLocation = n.puzzle.getFirstBlankPlace();

        int row = startLocation[0];
        int col = startLocation[1];
        //System.err.println("Start Coordinates :  Row " + row + " Col " +col);
        ArrayList<Node> output = new ArrayList<Node>();
        for(Type type :n.leftOverPentominoes){
            for(Pentomino p : Type.PENTOMINOES.get(type)){
                if(n.puzzle.canPlace(row,col,p)){
                    Board clone = new Board(n.puzzle);
                    clone.place(row,col,p);
                    ArrayDeque<Type> clonedPento = new ArrayDeque<Type>();
                    clonedPento.addAll(n.leftOverPentominoes);
                    clonedPento.remove(type);
                    Node child = new Node(clone,clonedPento);
                    output.add(child);
                }
                //System.err.println("Cannot place : " + p.type);
            }
        }
        
        return output;
    }
     
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        List<String> puzzle = new ArrayList<>();
        
        while (in.hasNextLine()) {
            String line = in.nextLine();
            
            if (!line.equals("")) puzzle.add(line);
                        
            if (line.equals("") || !in.hasNextLine()) {
                System.err.println(new Board(puzzle));
               new Solver().solve(new Board(puzzle));
                puzzle.clear();
            }
            
        }

        in.close();
    }
}
