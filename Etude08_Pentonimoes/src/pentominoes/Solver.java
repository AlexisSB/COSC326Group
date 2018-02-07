package pentominoes;

import java.util.*;

public class Solver {
    static final Map<Encoding, List<Pentomino>> pentominoes;
    static {
        pentominoes = new HashMap<>();

        // Pentomino O
        List<Pentomino> O = new ArrayList<>();
        // O, Identity
        List<Coordinate> pent = new ArrayList<>();
        pent.add(new Coordinate(0, 0));
        pent.add(new Coordinate(0, 1));
        pent.add(new Coordinate(0, 2));
        pent.add(new Coordinate(0, 3));
        pent.add(new Coordinate(0, 4));
        O.add(new Pentomino(Encoding.O, pent));

        //O, Rotated 90 degrees
        pent = new ArrayList<>();
        pent.add(new Coordinate(0, 0));
        pent.add(new Coordinate(1, 0));
        pent.add(new Coordinate(2, 0));
        pent.add(new Coordinate(3, 0));
        pent.add(new Coordinate(4, 0));
        O.add(new Pentomino(Encoding.O, pent));

        pentominoes.put(Encoding.O, O);

        // Pentomino P
        List<Pentomino> P = new ArrayList<>();
        // P, Identity
        pent = new ArrayList<>();
        pent.add(new Coordinate(0, 0));
        pent.add(new Coordinate(1, 0));
        pent.add(new Coordinate(0, 1));
        pent.add(new Coordinate(1, 1));
        pent.add(new Coordinate(0, 2));
        P.add(new Pentomino(Encoding.P, pent));

        // P, Rotated 90 degrees
        pent = new ArrayList<>();
        pent.add(new Coordinate(0, 0));
        pent.add(new Coordinate(1, 0));
        pent.add(new Coordinate(2, 0));
        pent.add(new Coordinate(1, 1));
        pent.add(new Coordinate(2, 1));
        P.add(new Pentomino(Encoding.P, pent));
        
        // P, Rotated 180 degrees
        pent = new ArrayList<>();
        pent.add(new Coordinate(1, 0));
        pent.add(new Coordinate(0, 1));
        pent.add(new Coordinate(1, 1));
        pent.add(new Coordinate(0, 2));
        pent.add(new Coordinate(1, 2));
        P.add(new Pentomino(Encoding.P, pent));

         
        // P, Rotated 270 degrees
        pent = new ArrayList<>();
        pent.add(new Coordinate(0, 0));
        pent.add(new Coordinate(1, 0));
        pent.add(new Coordinate(0, 1));
        pent.add(new Coordinate(1, 1));
        pent.add(new Coordinate(2, 1));
        P.add(new Pentomino(Encoding.P, pent));

        // P, Identity, Flipped
        pent = new ArrayList<>();
        pent.add(new Coordinate(0, 0));
        pent.add(new Coordinate(1, 0));
        pent.add(new Coordinate(0, 1));
        pent.add(new Coordinate(1, 1));
        pent.add(new Coordinate(1, 2));
        P.add(new Pentomino(Encoding.P, pent));

        // P, Rotated 90 degrees, Flipped
        pent = new ArrayList<>();
        pent.add(new Coordinate(0, 0));
        pent.add(new Coordinate(1, 0));
        pent.add(new Coordinate(2, 0));
        pent.add(new Coordinate(0, 1));
        pent.add(new Coordinate(1, 1));
        P.add(new Pentomino(Encoding.P, pent));
        
        // P, Rotated 180 degrees, Flipped
        pent = new ArrayList<>();
        pent.add(new Coordinate(0, 0));
        pent.add(new Coordinate(0, 1));
        pent.add(new Coordinate(1, 1));
        pent.add(new Coordinate(0, 2));
        pent.add(new Coordinate(1, 2));
        P.add(new Pentomino(Encoding.P, pent));

         
        // P, Rotated 270 degrees, Flipped
        pent = new ArrayList<>();
        pent.add(new Coordinate(1, 0));
        pent.add(new Coordinate(2, 0));
        pent.add(new Coordinate(0, 1));
        pent.add(new Coordinate(1, 1));
        pent.add(new Coordinate(2, 1));
        P.add(new Pentomino(Encoding.P, pent));

        pentominoes.put(Encoding.P, P);
    }

    public static void main(String[] args) {
        System.out.println(pentominoes.get(Encoding.O).get(0));
        System.out.println(pentominoes.get(Encoding.P).get(0));
    }
}
