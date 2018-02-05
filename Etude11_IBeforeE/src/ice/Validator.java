package ice;

import java.util.List;
import java.util.ArrayList;

/**
 * A validator that checks whether given strings meet the requirements as per
 * the given rules for an instance of the ice game.
 * 
 * @author Anthony Dickson
 */
public class Validator {
    final Alphabet alphabet;
    final List<Rule> rules;
    
    /**
     * Constructs a validator with the given alphabet and rules.
     * 
     * @param alphabet The characters that are allowed.
     * @param rules The rules (and their exceptions) that apply.
     */
    public Validator(Alphabet alphabet, List<Rule> rules) {
        this.alphabet = alphabet;
        this.rules = rules;
    }

    /**
     * Checks if the given string is valid.
     * 
     * @param str The string to check.
     * @return <code>true</code> if <code>str</code> only contains characters 
     * from the allowed alphabet and follows all the rules, false otherwise.
     */
    public boolean isValid(String str) {
        return alphabet.isValid(str) && Rule.isValid(rules, str);
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("Validator " + this.hashCode() + "\n");
        sb.append("Alphabet: " + alphabet + "\n");
        sb.append("Rules:\n");
        sb.append(Rule.toString(rules));
        sb.append("\n");

        return sb.toString();
    }
}