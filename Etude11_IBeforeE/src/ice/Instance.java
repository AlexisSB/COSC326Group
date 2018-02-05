package ice;

/**
 * Represents an 'instance' in an ice game.
 * An instance is either a sequence of characters to validate or an integer
 * indicating the length string for which we should count the number of valid
 * strings for.
 * 
 * @author Anthony Dickson
 */
public class Instance {
    public static enum Type { SEQUENCE, COUNT };
    
    final String value;
    final Type type;

    public Instance(String value) {
        this.value = value;

        if (this.value.matches("[\\d]+")) {
            this.type = Type.COUNT;
        } else {
            this.type = Type.SEQUENCE;
        }
    }

    @Override
    public String toString() {
        return value;
    }
}