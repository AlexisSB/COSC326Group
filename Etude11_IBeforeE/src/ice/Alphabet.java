package ice;

/**
 * Represents the valid characters for a instance of the ice game.
 * 
 * @author Anthony Dickson
 */
public class Alphabet {
    private final String validChars;

    /** 
     * @param letters The letters of the alphabet that should be considered
     * valid.
    */
    public Alphabet(String letters) {
        this.validChars = letters; 
    }
    
    /**
     * Check if a given string contains only valid characters. Empty strings
     * are considered valid.
     * 
     * @param str The string to check.
     * @return <code>true</code> if the str only contains valid characters,
     * <code>false</code> otherwise.
     */
    public boolean isValid(String str) {
        return str.matches("[" + validChars + "]*");
    }

    @Override
    public String toString() {
        return validChars;
    }
}