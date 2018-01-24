package iota;

import java.awt.*;

/**
 * Captures the colour of an Iota card
 *
 * @author MichaelAlbert
 */
public enum Colour {

    BLUE(new Color(65, 105, 225)) {
        @Override
        public String toString() {
            return "B";
        }
    },
    GREEN(new Color(60, 179, 113)) {
        @Override
        public String toString() {
            return "G";
        }
    },
    RED(new Color(220, 20, 60)) {
        @Override
        public String toString() {
            return "R";
        }
    },
    YELLOW(new Color(218, 165, 32)) {
        @Override
        public String toString() {
            return "Y";
        }
    };
    protected Color asColor;

    Colour(Color c) {
        this.asColor = c;
    }

}
