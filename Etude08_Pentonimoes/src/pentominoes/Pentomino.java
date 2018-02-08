package pentominoes;

import java.util.Arrays;
import java.util.List;

/**
 * Pentamino.java: Defines a pentamino according to Conway's representation.
 * Each pentamino consists detail of the shape i.e. coordinate which will be occupied
 * by this pentamino relative to a given location and its type.
 */

public class Pentomino {
    final Encoding type;
    final List<Coordinate> coordinates;

    public Pentomino(Encoding type, List<Coordinate> coordinates) {
        this.type = type;
        this.coordinates = coordinates;
    }

    public Pentomino(Encoding type, Coordinate...coordinates) {
        this(type, Arrays.asList(coordinates));
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                boolean hasSquare = false;
                
                for(Coordinate c : coordinates) {
                    if (c.x == j && c.y == i) {
                        sb.append(type);
                        hasSquare = true;
                        break;
                    }
                }

                if (!hasSquare) sb.append(" ");
            }

            sb.append("\n");
        }
        
        return sb.toString();
    }
}
