package ice;

/**
 * Represents the valid characters for a instance of the ice game.
 * 
 * @author Anthony Dickson
 */
public class Alphabet {
    final String validChars;
    final int length;

    /** 
     * @param letters The letters of the alphabet that should be considered
     * valid.
    */
    public Alphabet(String letters) {
        this.validChars = letters; 
        this.length = letters.length();
    }

    /**
     * Get the character at position <code>i</code> in the alphabet.
     * 
     * @param i The index position of the character to get.
     * @return The character (as a String) at position <code>i</code>.
     */
    public String get(int i) {
        return validChars.substring(i, i + 1);
    }

    /**
     * Get the length of the alphabet.
     * 
     * @return the length of the alphabet.
     */
    public int length() {
        return validChars.length();
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