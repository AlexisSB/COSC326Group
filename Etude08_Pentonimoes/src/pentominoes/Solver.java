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

        // P, Flipped, Identity
        P.add(new Pentomino(Encoding.P, new Coordinate(0, 0),
                                        new Coordinate(1, 0),
                                        new Coordinate(0, 1),
                                        new Coordinate(1, 1),
                                        new Coordinate(1, 2)));

        // P, Flipped, Rotated 90 degrees
        P.add(new Pentomino(Encoding.P, new Coordinate(1, 0),
                                        new Coordinate(2, 0),
                                        new Coordinate(0, 1),
                                        new Coordinate(1, 1),
                                        new Coordinate(2, 1)));
                                        
        // P, Flipped, Rotated 180 degrees
        P.add(new Pentomino(Encoding.P, new Coordinate(0, 0),
                                        new Coordinate(0, 1),
                                        new Coordinate(1, 1),
                                        new Coordinate(0, 2),
                                        new Coordinate(1, 2)));
        
        // P, Flipped, Rotated 270 degrees
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
        
        // Q, Flipped, Identity
        Q.add(new Pentomino(Encoding.Q, new Coordinate(0, 0),
                                        new Coordinate(1, 0),
                                        new Coordinate(0, 1),
                                        new Coordinate(0, 2),
                                        new Coordinate(0, 3)));                                
        
        // Q, Flipped, Rotated 90 degrees
        Q.add(new Pentomino(Encoding.Q, new Coordinate(0, 0),
                                        new Coordinate(1, 0),
                                        new Coordinate(2, 0),
                                        new Coordinate(3, 0),
                                        new Coordinate(3, 1)));                                
        
        // Q, Flipped, Rotated 180 degrees
        Q.add(new Pentomino(Encoding.Q, new Coordinate(1, 0),
                                        new Coordinate(1, 1),
                                        new Coordinate(1, 2),
                                        new Coordinate(0, 3),
                                        new Coordinate(1, 3)));                                
        
        // Q, Flipped, Rotated 270 degrees
        Q.add(new Pentomino(Encoding.Q, new Coordinate(0, 0),
                                        new Coordinate(0, 1),
                                        new Coordinate(1, 1),
                                        new Coordinate(2, 1),
                                        new Coordinate(3, 1)));                                

        PENTOMINOES.put(Encoding.Q, Q);

        // Pentomino R
        List<Pentomino> R = new ArrayList<>();

        // R, Identity
        R.add(new Pentomino(Encoding.R, new Coordinate(1, 0),
                                        new Coordinate(2, 0),
                                        new Coordinate(0, 1),
                                        new Coordinate(1, 1),
                                        new Coordinate(1, 2)));

        // R, Rotated 90 degrees
        R.add(new Pentomino(Encoding.R, new Coordinate(1, 0),
                                        new Coordinate(0, 1),
                                        new Coordinate(1, 1),
                                        new Coordinate(2, 1),
                                        new Coordinate(2, 2)));

        // R, Rotated 180 degrees
        R.add(new Pentomino(Encoding.R, new Coordinate(1, 0),
                                        new Coordinate(1, 1),
                                        new Coordinate(2, 1),
                                        new Coordinate(0, 2),
                                        new Coordinate(1, 2)));

        // R, Rotated 270 degrees
        R.add(new Pentomino(Encoding.R, new Coordinate(0, 0),
                                        new Coordinate(0, 1),
                                        new Coordinate(1, 1),
                                        new Coordinate(2, 1),
                                        new Coordinate(1, 2)));

        // R, Flipped, Identity
        R.add(new Pentomino(Encoding.R, new Coordinate(0, 0),
                                        new Coordinate(1, 0),
                                        new Coordinate(1, 1),
                                        new Coordinate(2, 1),
                                        new Coordinate(1, 2)));

        // R, Flipped, Rotated 90 degrees
        R.add(new Pentomino(Encoding.R, new Coordinate(0, 0),
                                        new Coordinate(1, 0),
                                        new Coordinate(1, 1),
                                        new Coordinate(2, 1),
                                        new Coordinate(1, 2)));

        // R, Flipped, Rotated 180 degrees
        R.add(new Pentomino(Encoding.R, new Coordinate(1, 0),
                                        new Coordinate(0, 1),
                                        new Coordinate(1, 1),
                                        new Coordinate(1, 2),
                                        new Coordinate(2, 2)));

        // R, Flipped, Rotated 270 degrees
        R.add(new Pentomino(Encoding.R, new Coordinate(0, 0),
                                        new Coordinate(0, 1),
                                        new Coordinate(1, 1),
                                        new Coordinate(2, 1),
                                        new Coordinate(1, 2)));

        PENTOMINOES.put(Encoding.R, R);

        // Pentomino S
        List<Pentomino> S = new ArrayList<>();

        // S, Identity
        S.add(new Pentomino(Encoding.S, new Coordinate(2, 0),
                                        new Coordinate(3, 0),
                                        new Coordinate(0, 1),
                                        new Coordinate(1, 1),
                                        new Coordinate(2, 1)));

        // S, Rotated 90 Degrees
        S.add(new Pentomino(Encoding.S, new Coordinate(0, 0),
                                        new Coordinate(0, 1),
                                        new Coordinate(0, 2),
                                        new Coordinate(1, 2),
                                        new Coordinate(1, 3)));

        // S, Rotated 180 Degrees
        S.add(new Pentomino(Encoding.S, new Coordinate(1, 0),
                                        new Coordinate(2, 0),
                                        new Coordinate(3, 0),
                                        new Coordinate(0, 1),
                                        new Coordinate(1, 1)));

        // S, Rotated 270 Degrees
        S.add(new Pentomino(Encoding.S, new Coordinate(0, 0),
                                        new Coordinate(0, 1),
                                        new Coordinate(1, 1),
                                        new Coordinate(1, 2),
                                        new Coordinate(1, 3)));

        // S, Flipped, Identity
        S.add(new Pentomino(Encoding.S, new Coordinate(0, 0),
                                        new Coordinate(1, 0),
                                        new Coordinate(1, 1),
                                        new Coordinate(2, 1),
                                        new Coordinate(3, 1)));

        // S, Flipped, Rotated 90 degrees
        S.add(new Pentomino(Encoding.S, new Coordinate(1, 0),
                                        new Coordinate(0, 1),
                                        new Coordinate(1, 1),
                                        new Coordinate(0, 2),
                                        new Coordinate(0, 3)));

        // S, Flipped, Rotated 180 degrees
        S.add(new Pentomino(Encoding.S, new Coordinate(0, 0),
                                        new Coordinate(1, 0),
                                        new Coordinate(2, 0),
                                        new Coordinate(2, 1),
                                        new Coordinate(3, 1)));

        // S, Flipped, Rotated 270 degrees
        S.add(new Pentomino(Encoding.S, new Coordinate(1, 0),
                                        new Coordinate(1, 1),
                                        new Coordinate(0, 2),
                                        new Coordinate(1, 2),
                                        new Coordinate(0, 3)));

        PENTOMINOES.put(Encoding.S, S);

        // Pentomino T
        List<Pentomino> T = new ArrayList<>();

        // T, Identity
        T.add(new Pentomino(Encoding.T, new Coordinate(0, 0),
                                        new Coordinate(1, 0),
                                        new Coordinate(2, 0),
                                        new Coordinate(1, 1),
                                        new Coordinate(1, 2)));

        // T, Rotated 90 degrees
        T.add(new Pentomino(Encoding.T, new Coordinate(2, 0),
                                        new Coordinate(0, 1),
                                        new Coordinate(1, 1),
                                        new Coordinate(2, 1),
                                        new Coordinate(2, 2)));

        // T, Rotated 180 degrees
        T.add(new Pentomino(Encoding.T, new Coordinate(1, 0),
                                        new Coordinate(1, 1),
                                        new Coordinate(0, 2),
                                        new Coordinate(1, 2),
                                        new Coordinate(2, 2)));

        // T, Rotated 270 degrees
        T.add(new Pentomino(Encoding.T, new Coordinate(0, 0),
                                        new Coordinate(0, 1),
                                        new Coordinate(1, 1),
                                        new Coordinate(2, 1),
                                        new Coordinate(0, 2)));

        PENTOMINOES.put(Encoding.T, T);
    }
                                    
    public static void main(String[] args) {
        for (Pentomino p : PENTOMINOES.get(Encoding.T)) {
            System.out.println(p);
        }
    }
}
