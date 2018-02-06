package ice;

import java.util.List;
import java.util.ArrayList;
import java.util.regex.*;

/**
 * Represents a rule in the ice game.
 * A rule consists of a prohibited string, and optionally exception prefix(es).
 * The exception prefix states after which character sequence the prohibited
 * character sequence is allowed.
 * 
 * @author Anthony Dickson
 */
public class Rule {
    private final String prohibited;
    private final List<String> exceptions = new ArrayList<>();
    private final Pattern pattern;

    /**
     * <p>Construct a rule from a string that contains the prohibited character
     * sequence followed by the exception prefix(es). The string should be 
     * space separated in the following format:</p>
     * prohibited_string exception1  exception2 . . . exceptionN
     * 
     * @param str The string that conforms to the above format.
     */
    public Rule(String str) {
        String[] items = str.split("\\s");

        this.prohibited = items[0];

        for (int i = 1; i < items.length; i++) {
            this.exceptions.add(items[i]);
        }
        
        this.pattern = Pattern.compile(buildPatternString());
    }
    
    private String buildPatternString() {
        StringBuilder sb = new StringBuilder();

            
        for (String exception : exceptions) {
            sb.append("(?<!");
            sb.append(exception);
            sb.append(")");
        }

        sb.append(prohibited);
        return sb.toString();
    }

    /**
     * Construct a rule with the given prohibited string and exceptions.
     * 
     * @param prohibited The character sequence that should be prohibited.
     * @param exceptions The character sequences for which 
     * <code>prohibited</code> may succeed.
     */
    public Rule(String prohibited, List<String> exceptions) {
        this.prohibited = prohibited;
        this.exceptions.addAll(exceptions);
        this.pattern = Pattern.compile(buildPatternString());
    }

    /**
     * Check <code>str</code> conforms to this rule.
     * 
     * @param str The string to check.
     * @return <code>true</code> if <code>str</code> conforms to this rule, 
     * <code>false</code> otherwise.
     */
    public boolean isValid(String str) {
        return !pattern.matcher(str).find();
    }

    /**
     * Check if <code>str</code> conforms to all the rules in 
     * <code>rules</code>.
     * 
     * @param str The string to check.
     * @param rules The list of rules to check against.
     */
    public static boolean isValid(List<Rule> rules, String str) {
        for (Rule rule : rules) {
            if (!rule.isValid(str)) return false;
        }

        return true;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Prohibited: " + prohibited);
        sb.append(" Exceptions: ");

        for (String exception : exceptions) {
            sb.append(exception);
        }

        if (exceptions.size() == 0) sb.append("-");

        return sb.toString();
    }

    public static String toString(List<Rule> rules) {
        StringBuilder sb = new StringBuilder();

        for (Rule rule : rules) {
            sb.append(rule + "\n");
        }

        return sb.toString();
    }

    public int totalLengthOfRule(){
        int length = maxExceptionLength() + lengthOfRule();
        
        //System.err.println("Rule length: " + (l+maxExceptionLength));
        return length;
    }

    public int maxExceptionLength(){
        int maxExceptionLength = -1;
        
        for (String s : exceptions){
            if(s.length() > maxExceptionLength){
                maxExceptionLength = s.length();
            }
        }
        return maxExceptionLength;
    }
    
    public int lengthOfRule(){
        return prohibited.length();
    }

}

