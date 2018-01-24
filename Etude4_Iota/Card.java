package iota;

/**
 * An Iota card.
 *
 * @author Michael Albert
 */
public class Card {

    Colour colour;
    Shape shape;
    int value;

    public Card(Colour colour, Shape shape, int value) {
        this.colour = colour;
        this.shape = shape;
        this.value = value;
    }

    @Override
    public int hashCode() {
        return (colour.ordinal() << 4) + (shape.ordinal() << 2) + value;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Card other = (Card) obj;
        if (this.colour != other.colour) {
            return false;
        }
        if (this.shape != other.shape) {
            return false;
        }
        if (this.value != other.value) {
            return false;
        }
        return true;
    }

    public String toString() {
        return "" + colour + shape + value;
    }


}
