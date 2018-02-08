package pentominoes;

import java.util.*;

public class Solver {
    static final Map<Encoding, List<Pentomino>> PENTOMINOES;
    static {
        PENTOMINOES = new HashMap<>();

        // Pentomino O
        List<Pentomino> O = new ArrayList<>();

        // O, Identity
        O.add(new Pentomino(Encoding.O, new Coordinate(0, 0), 
                                        new Coordinate(0, 1), 
                                        new Coordinate(0, 2), 
                                        new Coordinate(0, 3), 
                                        new Coordinate(0, 4)));

        //O, Rotated 90 degrees
        O.add(new Pentomino(Encoding.O, new Coordinate(0, 0), 
                                        new Coordinate(1, 0), 
                                        new Coordinate(2, 0), 
                                        new Coordinate(3, 0), 
                                        new Coordinate(4, 0)));

        PENTOMINOES.put(Encoding.O, O);

        // Pentomino P
        List<Pentomino> P = new ArrayList<>();

        // P, Identity
        P.add(new Pentomino(Encoding.P, new Coordinate(0, 0),
                                        new Coordinate(1, 0),
                                        new Coordinate(0, 1),
                                        new Coordinate(1, 1),
                                        new Coordinate(0, 2)));

        // P, Rotated 90 degrees
        P.add(new Pentomino(Encoding.P, new Coordinate(0, 0),
                                        new Coordinate(1, 0),
                                        new Coordinate(2, 0),
                                        new Coordinate(1, 1),
                                        new Coordinate(2, 1)));
        
        // P, Rotated 180 degrees
        P.add(new Pentomino(Encoding.P, new Coordinate(1, 0),
                                        new Coordinate(0, 1),
                                        new Coordinate(1, 1),
                                        new Coordinate(0, 2),
                                        new Coordinate(1, 2)));

        // P, Rotated 270 degrees
        P.add(new Pentomino(Encoding.P, new Coordinate(0, 0),
                                        new Coordinate(1, 0),
                                        new Coordinate(0, 1),
                                        new Coordinate(1, 1),
                                        new Coordinate(2, 1)));

        // P, Identity, Flipped
        P.add(new Pentomino(Encoding.P, new Coordinate(0, 0),
                                        new Coordinate(1, 0),
                                        new Coordinate(0, 1),
                                        new Coordinate(1, 1),
                                        new Coordinate(1, 2)));

        // P, Rotated 90 degrees, Flipped
        P.add(new Pentomino(Encoding.P, new Coordinate(1, 0),
                                        new Coordinate(2, 0),
                                        new Coordinate(0, 1),
                                        new Coordinate(1, 1),
                                        new Coordinate(2, 1)));
                                        
        // P, Rotated 180 degrees, Flipped
        P.add(new Pentomino(Encoding.P, new Coordinate(0, 0),
                                        new Coordinate(0, 1),
                                        new Coordinate(1, 1),
                                        new Coordinate(0, 2),
                                        new Coordinate(1, 2)));
        
        // P, Rotated 270 degrees, Flipped
        P.add(new Pentomino(Encoding.P, new Coordinate(0, 0),
                                        new Coordinate(1, 0),
                                        new Coordinate(2, 0),
                                        new Coordinate(0, 1),
                                        new Coordinate(1, 1)));
        
        PENTOMINOES.put(Encoding.P, P);

        // Pentomino Q
        List<Pentomino> Q = new ArrayList<>();

        // Q, Identity
        Q.add(new Pentomino(Encoding.Q, new Coordinate(0, 0),
                                        new Coordinate(1, 0),
                                        new Coordinate(1, 1),
                                        new Coordinate(1, 2),
                                        new Coordinate(1, 3)));

        // Q, Rotated 90 degrees
        Q.add(new Pentomino(Encoding.Q, new Coordinate(3, 0),
                                        new Coordinate(0, 1),
                                        new Coordinate(1, 1),
                                        new Coordinate(2, 1),
                                        new Coordinate(3, 1)));

        // Q, Rotated 180 degrees
        Q.add(new Pentomino(Encoding.Q, new Coordinate(0, 0),
                                        new Coordinate(0, 1),
                                        new Coordinate(0, 2),
                                        new Coordinate(0, 3),
                                        new Coordinate(1, 3)));

        // Q, Rotated 270 degrees
        Q.add(new Pentomino(Encoding.Q, new Coordinate(0, 0),
                                        new Coordinate(1, 0),
                                        new Coordinate(2, 0),
                                        new Coordinate(3, 0),
                                        new Coordinate(0, 1)));
        
        // Q, Identity, Flipped
        Q.add(new Pentomino(Encoding.Q, new Coordinate(0, 0),
                                        new Coordinate(1, 0),
                                        new Coordinate(0, 1),
                                        new Coordinate(0, 2),
                                        new Coordinate(0, 3)));                                
        
        // Q, Rotated 90 degrees, Flipped
        Q.add(new Pentomino(Encoding.Q, new Coordinate(0, 0),
                                        new Coordinate(1, 0),
                                        new Coordinate(2, 0),
                                        new Coordinate(3, 0),
                                        new Coordinate(3, 1)));                                
        
        // Q, Rotated 180 degrees, Flipped
        Q.add(new Pentomino(Encoding.Q, new Coordinate(1, 0),
                                        new Coordinate(1, 1),
                                        new Coordinate(1, 2),
                                        new Coordinate(0, 3),
                                        new Coordinate(1, 3)));                                
        
        // Q, Rotated 270 degrees, Flipped
        Q.add(new Pentomino(Encoding.Q, new Coordinate(0, 0),
                                        new Coordinate(0, 1),
                                        new Coordinate(1, 1),
                                        new Coordinate(2, 1),
                                        new Coordinate(3, 1)));                                

        PENTOMINOES.put(Encoding.Q, Q);
    }
                                    
    public static void main(String[] args) {
        for (Pentomino p : PENTOMINOES.get(Encoding.O)) {
            System.out.println(p);
        }

        for (Pentomino p : PENTOMINOES.get(Encoding.P)) {
            System.out.println(p);
        }

        for (Pentomino p : PENTOMINOES.get(Encoding.Q)) {
            System.out.println(p);
        }
    }
}
