package pentominoes;

import java.util.*;

/**
 * Defines the different types of labels for pentaminoes according to 
 * Conways's representation. Refer to wikipedia
 * https://en.wikipedia.org/wiki/Pentomino for more information.
 */
public enum Type { 
    O, P, Q, R, S, T, U, V, W, X, Y, Z;

    public static void main(String[] args) {
        for (Type t : Type.values()) {
            for (Pentomino p : PENTOMINOES.get(t)) {
                System.out.println(p);
            }
        }
    }

    public static final Map<Type, List<Pentomino>> PENTOMINOES;
    static {
        PENTOMINOES = new HashMap<>();

        // Pentomino O
        List<Pentomino> O = new ArrayList<>();

        // O, Identity
        O.add(new Pentomino(Type.O, new Coordinate(0, 0), 
                                    new Coordinate(0, 1), 
                                    new Coordinate(0, 2), 
                                    new Coordinate(0, 3), 
                                    new Coordinate(0, 4)));

        //O, Rotated 90 degrees
        O.add(new Pentomino(Type.O, new Coordinate(0, 0), 
                                    new Coordinate(1, 0), 
                                    new Coordinate(2, 0), 
                                    new Coordinate(3, 0), 
                                    new Coordinate(4, 0)));

        PENTOMINOES.put(Type.O, O);

        // Pentomino P
        List<Pentomino> P = new ArrayList<>();

        // P, Identity
        P.add(new Pentomino(Type.P, new Coordinate(0, 0),
                                    new Coordinate(1, 0),
                                    new Coordinate(0, 1),
                                    new Coordinate(1, 1),
                                    new Coordinate(0, 2)));

        // P, Rotated 90 degrees
        P.add(new Pentomino(Type.P, new Coordinate(0, 0),
                                    new Coordinate(1, 0),
                                    new Coordinate(2, 0),
                                    new Coordinate(1, 1),
                                    new Coordinate(2, 1)));
        
        // P, Rotated 180 degrees
        P.add(new Pentomino(Type.P, new Coordinate(1, 0),
                                    new Coordinate(0, 1),
                                    new Coordinate(1, 1),
                                    new Coordinate(0, 2),
                                    new Coordinate(1, 2)));

        // P, Rotated 270 degrees
        P.add(new Pentomino(Type.P, new Coordinate(0, 0),
                                    new Coordinate(1, 0),
                                    new Coordinate(0, 1),
                                    new Coordinate(1, 1),
                                    new Coordinate(2, 1)));

        // P, Flipped, Identity
        P.add(new Pentomino(Type.P, new Coordinate(0, 0),
                                    new Coordinate(1, 0),
                                    new Coordinate(0, 1),
                                    new Coordinate(1, 1),
                                    new Coordinate(1, 2)));

        // P, Flipped, Rotated 90 degrees
        P.add(new Pentomino(Type.P, new Coordinate(1, 0),
                                    new Coordinate(2, 0),
                                    new Coordinate(0, 1),
                                    new Coordinate(1, 1),
                                    new Coordinate(2, 1)));
                                        
        // P, Flipped, Rotated 180 degrees
        P.add(new Pentomino(Type.P, new Coordinate(0, 0),
                                    new Coordinate(0, 1),
                                    new Coordinate(1, 1),
                                    new Coordinate(0, 2),
                                    new Coordinate(1, 2)));
        
        // P, Flipped, Rotated 270 degrees
        P.add(new Pentomino(Type.P, new Coordinate(0, 0),
                                    new Coordinate(1, 0),
                                    new Coordinate(2, 0),
                                    new Coordinate(0, 1),
                                    new Coordinate(1, 1)));
        
        PENTOMINOES.put(Type.P, P);

        // Pentomino Q
        List<Pentomino> Q = new ArrayList<>();

        // Q, Identity
        Q.add(new Pentomino(Type.Q, new Coordinate(0, 0),
                                    new Coordinate(1, 0),
                                    new Coordinate(1, 1),
                                    new Coordinate(1, 2),
                                    new Coordinate(1, 3)));

        // Q, Rotated 90 degrees
        Q.add(new Pentomino(Type.Q, new Coordinate(3, 0),
                                    new Coordinate(0, 1),
                                    new Coordinate(1, 1),
                                    new Coordinate(2, 1),
                                    new Coordinate(3, 1)));

        // Q, Rotated 180 degrees
        Q.add(new Pentomino(Type.Q, new Coordinate(0, 0),
                                    new Coordinate(0, 1),
                                    new Coordinate(0, 2),
                                    new Coordinate(0, 3),
                                    new Coordinate(1, 3)));

        // Q, Rotated 270 degrees
        Q.add(new Pentomino(Type.Q, new Coordinate(0, 0),
                                    new Coordinate(1, 0),
                                    new Coordinate(2, 0),
                                    new Coordinate(3, 0),
                                    new Coordinate(0, 1)));
        
        // Q, Flipped, Identity
        Q.add(new Pentomino(Type.Q, new Coordinate(0, 0),
                                    new Coordinate(1, 0),
                                    new Coordinate(0, 1),
                                    new Coordinate(0, 2),
                                    new Coordinate(0, 3)));                                
        
        // Q, Flipped, Rotated 90 degrees
        Q.add(new Pentomino(Type.Q, new Coordinate(0, 0),
                                    new Coordinate(1, 0),
                                    new Coordinate(2, 0),
                                    new Coordinate(3, 0),
                                    new Coordinate(3, 1)));                                
        
        // Q, Flipped, Rotated 180 degrees
        Q.add(new Pentomino(Type.Q, new Coordinate(1, 0),
                                    new Coordinate(1, 1),
                                    new Coordinate(1, 2),
                                    new Coordinate(0, 3),
                                    new Coordinate(1, 3)));                                
        
        // Q, Flipped, Rotated 270 degrees
        Q.add(new Pentomino(Type.Q, new Coordinate(0, 0),
                                    new Coordinate(0, 1),
                                    new Coordinate(1, 1),
                                    new Coordinate(2, 1),
                                    new Coordinate(3, 1)));                                

        PENTOMINOES.put(Type.Q, Q);

        // Pentomino R
        List<Pentomino> R = new ArrayList<>();

        // R, Identity
        R.add(new Pentomino(Type.R, new Coordinate(1, 0),
                                    new Coordinate(2, 0),
                                    new Coordinate(0, 1),
                                    new Coordinate(1, 1),
                                    new Coordinate(1, 2)));

        // R, Rotated 90 degrees
        R.add(new Pentomino(Type.R, new Coordinate(1, 0),
                                    new Coordinate(0, 1),
                                    new Coordinate(1, 1),
                                    new Coordinate(2, 1),
                                    new Coordinate(2, 2)));

        // R, Rotated 180 degrees
        R.add(new Pentomino(Type.R, new Coordinate(1, 0),
                                    new Coordinate(1, 1),
                                    new Coordinate(2, 1),
                                    new Coordinate(0, 2),
                                    new Coordinate(1, 2)));

        // R, Rotated 270 degrees
        R.add(new Pentomino(Type.R, new Coordinate(0, 0),
                                    new Coordinate(0, 1),
                                    new Coordinate(1, 1),
                                    new Coordinate(2, 1),
                                    new Coordinate(1, 2)));

        // R, Flipped, Identity
        R.add(new Pentomino(Type.R, new Coordinate(0, 0),
                                    new Coordinate(1, 0),
                                    new Coordinate(1, 1),
                                    new Coordinate(2, 1),
                                    new Coordinate(1, 2)));

        // R, Flipped, Rotated 90 degrees
        R.add(new Pentomino(Type.R, new Coordinate(0, 0),
                                    new Coordinate(1, 0),
                                    new Coordinate(1, 1),
                                    new Coordinate(2, 1),
                                    new Coordinate(1, 2)));

        // R, Flipped, Rotated 180 degrees
        R.add(new Pentomino(Type.R, new Coordinate(1, 0),
                                    new Coordinate(0, 1),
                                    new Coordinate(1, 1),
                                    new Coordinate(1, 2),
                                    new Coordinate(2, 2)));

        // R, Flipped, Rotated 270 degrees
        R.add(new Pentomino(Type.R, new Coordinate(0, 0),
                                    new Coordinate(0, 1),
                                    new Coordinate(1, 1),
                                    new Coordinate(2, 1),
                                    new Coordinate(1, 2)));

        PENTOMINOES.put(Type.R, R);

        // Pentomino S
        List<Pentomino> S = new ArrayList<>();

        // S, Identity
        S.add(new Pentomino(Type.S, new Coordinate(2, 0),
                                    new Coordinate(3, 0),
                                    new Coordinate(0, 1),
                                    new Coordinate(1, 1),
                                    new Coordinate(2, 1)));

        // S, Rotated 90 Degrees
        S.add(new Pentomino(Type.S, new Coordinate(0, 0),
                                    new Coordinate(0, 1),
                                    new Coordinate(0, 2),
                                    new Coordinate(1, 2),
                                    new Coordinate(1, 3)));

        // S, Rotated 180 Degrees
        S.add(new Pentomino(Type.S, new Coordinate(1, 0),
                                    new Coordinate(2, 0),
                                    new Coordinate(3, 0),
                                    new Coordinate(0, 1),
                                    new Coordinate(1, 1)));

        // S, Rotated 270 Degrees
        S.add(new Pentomino(Type.S, new Coordinate(0, 0),
                                    new Coordinate(0, 1),
                                    new Coordinate(1, 1),
                                    new Coordinate(1, 2),
                                    new Coordinate(1, 3)));

        // S, Flipped, Identity
        S.add(new Pentomino(Type.S, new Coordinate(0, 0),
                                    new Coordinate(1, 0),
                                    new Coordinate(1, 1),
                                    new Coordinate(2, 1),
                                    new Coordinate(3, 1)));

        // S, Flipped, Rotated 90 degrees
        S.add(new Pentomino(Type.S, new Coordinate(1, 0),
                                    new Coordinate(0, 1),
                                    new Coordinate(1, 1),
                                    new Coordinate(0, 2),
                                    new Coordinate(0, 3)));

        // S, Flipped, Rotated 180 degrees
        S.add(new Pentomino(Type.S, new Coordinate(0, 0),
                                    new Coordinate(1, 0),
                                    new Coordinate(2, 0),
                                    new Coordinate(2, 1),
                                    new Coordinate(3, 1)));

        // S, Flipped, Rotated 270 degrees
        S.add(new Pentomino(Type.S, new Coordinate(1, 0),
                                    new Coordinate(1, 1),
                                    new Coordinate(0, 2),
                                    new Coordinate(1, 2),
                                    new Coordinate(0, 3)));

        PENTOMINOES.put(Type.S, S);

        // Pentomino T
        List<Pentomino> T = new ArrayList<>();

        // T, Identity
        T.add(new Pentomino(Type.T, new Coordinate(0, 0),
                                    new Coordinate(1, 0),
                                    new Coordinate(2, 0),
                                    new Coordinate(1, 1),
                                    new Coordinate(1, 2)));

        // T, Rotated 90 degrees
        T.add(new Pentomino(Type.T, new Coordinate(2, 0),
                                    new Coordinate(0, 1),
                                    new Coordinate(1, 1),
                                    new Coordinate(2, 1),
                                    new Coordinate(2, 2)));

        // T, Rotated 180 degrees
        T.add(new Pentomino(Type.T, new Coordinate(1, 0),
                                    new Coordinate(1, 1),
                                    new Coordinate(0, 2),
                                    new Coordinate(1, 2),
                                    new Coordinate(2, 2)));

        // T, Rotated 270 degrees
        T.add(new Pentomino(Type.T, new Coordinate(0, 0),
                                    new Coordinate(0, 1),
                                    new Coordinate(1, 1),
                                    new Coordinate(2, 1),
                                    new Coordinate(0, 2)));

        PENTOMINOES.put(Type.T, T);

        // Pentomino U
        List<Pentomino> U = new ArrayList<>();

        // U, Identity
        U.add(new Pentomino(Type.U, new Coordinate(0, 0),
                                    new Coordinate(2, 0),
                                    new Coordinate(0, 1),
                                    new Coordinate(1, 1),
                                    new Coordinate(2, 1)));

        PENTOMINOES.put(Type.U, U);

        // U, Rotated 90 Degrees
        U.add(new Pentomino(Type.U, new Coordinate(0, 0),
                                    new Coordinate(1, 0),
                                    new Coordinate(0, 1),
                                    new Coordinate(0, 2),
                                    new Coordinate(1, 2)));

        // U, Rotated 180 Degrees
        U.add(new Pentomino(Type.U, new Coordinate(0, 0),
                                    new Coordinate(1, 0),
                                    new Coordinate(2, 0),
                                    new Coordinate(0, 1),
                                    new Coordinate(2, 1)));

        // U, Rotated 270 Degrees
        U.add(new Pentomino(Type.U, new Coordinate(0, 0),
                                    new Coordinate(1, 0),
                                    new Coordinate(1, 1),
                                    new Coordinate(0, 2),
                                    new Coordinate(1, 2)));

        PENTOMINOES.put(Type.U, U);

        // Pentomino V
        List<Pentomino> V = new ArrayList<>();

        // V, Identity
        V.add(new Pentomino(Type.V, new Coordinate(0, 0),
                                    new Coordinate(0, 1),
                                    new Coordinate(0, 2),
                                    new Coordinate(1, 2),
                                    new Coordinate(2, 2)));

        // V, Rotated 90 degrees
        V.add(new Pentomino(Type.V, new Coordinate(0, 0),
                                    new Coordinate(1, 0),
                                    new Coordinate(2, 0),
                                    new Coordinate(0, 1),
                                    new Coordinate(0, 2)));

        // V, Rotated 180 degrees
        V.add(new Pentomino(Type.V, new Coordinate(0, 0),
                                    new Coordinate(1, 0),
                                    new Coordinate(2, 0),
                                    new Coordinate(2, 1),
                                    new Coordinate(2, 2)));

        // V, Rotated 270 degrees
        V.add(new Pentomino(Type.V, new Coordinate(2, 0),
                                    new Coordinate(2, 1),
                                    new Coordinate(0, 2),
                                    new Coordinate(1, 2),
                                    new Coordinate(2, 2)));

        PENTOMINOES.put(Type.V, V);

        // Pentomino W
        List<Pentomino> W = new ArrayList<>();

        // W, Identity
        W.add(new Pentomino(Type.W, new Coordinate(0, 0),
                                    new Coordinate(0, 1),
                                    new Coordinate(1, 1),
                                    new Coordinate(1, 2),
                                    new Coordinate(2, 2)));

        // W, Rotated 90 degrees
        W.add(new Pentomino(Type.W, new Coordinate(1, 0),
                                    new Coordinate(2, 0),
                                    new Coordinate(0, 1),
                                    new Coordinate(1, 1),
                                    new Coordinate(0, 2)));

        // W, Rotated 180 degrees
        W.add(new Pentomino(Type.W, new Coordinate(0, 0),
                                    new Coordinate(1, 0),
                                    new Coordinate(1, 1),
                                    new Coordinate(2, 1),
                                    new Coordinate(2, 2)));

        // W, Rotated 270 degrees
        W.add(new Pentomino(Type.W, new Coordinate(2, 0),
                                    new Coordinate(1, 1),
                                    new Coordinate(2, 1),
                                    new Coordinate(0, 2),
                                    new Coordinate(2, 2)));

        PENTOMINOES.put(Type.W, W);

        // Pentomino X
        List<Pentomino> X = new ArrayList<>();

        // X, Identity
        X.add(new Pentomino(Type.X, new Coordinate(1, 0),
                                    new Coordinate(0, 1),
                                    new Coordinate(1, 1),
                                    new Coordinate(2, 1),
                                    new Coordinate(1, 2)));

        PENTOMINOES.put(Type.X, X);

        // Pentomino Y
        List<Pentomino> Y = new ArrayList<>();

        // Y, Identity
        Y.add(new Pentomino(Type.Y, new Coordinate(1, 0),
                                    new Coordinate(0, 1),
                                    new Coordinate(1, 1),
                                    new Coordinate(1, 2),
                                    new Coordinate(1, 3)));

        // Y, Rotated 90 degrees
        Y.add(new Pentomino(Type.Y, new Coordinate(2, 0),
                                    new Coordinate(0, 1),
                                    new Coordinate(1, 1),
                                    new Coordinate(2, 1),
                                    new Coordinate(3, 1)));

        // Y, Rotated 180 degrees
        Y.add(new Pentomino(Type.Y, new Coordinate(0, 0),
                                    new Coordinate(0, 1),
                                    new Coordinate(0, 2),
                                    new Coordinate(1, 2),
                                    new Coordinate(0, 3)));

        // Y, Rotated 270 degrees
        Y.add(new Pentomino(Type.Y, new Coordinate(0, 0),
                                    new Coordinate(1, 0),
                                    new Coordinate(2, 0),
                                    new Coordinate(3, 0),
                                    new Coordinate(1, 1)));

        // Y, Flipped, Identity
        Y.add(new Pentomino(Type.Y, new Coordinate(0, 0),
                                    new Coordinate(0, 1),
                                    new Coordinate(1, 1),
                                    new Coordinate(0, 2),
                                    new Coordinate(0, 3)));

        // Y, Flipped, Rotated 90 degrees
        Y.add(new Pentomino(Type.Y, new Coordinate(0, 0),
                                    new Coordinate(1, 0),
                                    new Coordinate(2, 0),
                                    new Coordinate(3, 0),
                                    new Coordinate(2, 1)));

        // Y, Flipped, Rotated 180 degrees
        Y.add(new Pentomino(Type.Y, new Coordinate(1, 0),
                                    new Coordinate(1, 1),
                                    new Coordinate(0, 2),
                                    new Coordinate(1, 2),
                                    new Coordinate(1, 3)));

        // Y, Flipped, Rotated 270 degrees
        Y.add(new Pentomino(Type.Y, new Coordinate(1, 0),
                                    new Coordinate(0, 1),
                                    new Coordinate(1, 1),
                                    new Coordinate(2, 1),
                                    new Coordinate(3, 1)));

        PENTOMINOES.put(Type.Y, Y);

        // Pentomino Z
        List<Pentomino> Z = new ArrayList<>();

        // Z, Identity
        Z.add(new Pentomino(Type.Z, new Coordinate(0, 0),
                                    new Coordinate(1, 0),
                                    new Coordinate(1, 1),
                                    new Coordinate(1, 2),
                                    new Coordinate(2, 2)));

        // Z, Rotated 90 degrees
        Z.add(new Pentomino(Type.Z, new Coordinate(2, 0),
                                    new Coordinate(0, 1),
                                    new Coordinate(1, 1),
                                    new Coordinate(2, 1),
                                    new Coordinate(0, 2)));

        // Z, Flipped, Identity
        Z.add(new Pentomino(Type.Z, new Coordinate(1, 0),
                                    new Coordinate(2, 0),
                                    new Coordinate(1, 1),
                                    new Coordinate(0, 2),
                                    new Coordinate(1, 2)));

        // Z, Flipped, Rotated 90 degrees
        Z.add(new Pentomino(Type.Z, new Coordinate(0, 0),
                                    new Coordinate(0, 1),
                                    new Coordinate(1, 1),
                                    new Coordinate(2, 1),
                                    new Coordinate(2, 2)));

        PENTOMINOES.put(Type.Z, Z);
    }
}