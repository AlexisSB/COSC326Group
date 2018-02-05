package ice;

import java.util.*;
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
    final String prohibited;
    final List<String> exceptions = new ArrayList<>();
    final Pattern pattern;
    final Pattern negativePattern;
    
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
        // this = new Rule(items[0], Arrays.copyOf(items, 1, items.length));

        this.prohibited = items[0];

        for (int i = 1; i < items.length; i++) {
            this.exceptions.add(items[i]);
        }
        
        this.pattern = Pattern.compile(buildPatternString(true));
        this.negativePattern = Pattern.compile(buildPatternString(false));
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
            this.pattern = Pattern.compile(buildPatternString(true));
            this.negativePattern = Pattern.compile(buildPatternString(false));
        }
    
    private String buildPatternString(boolean usePositiveLookbehind) {
        StringBuilder sb = new StringBuilder();

        for (String exception : exceptions) {
            if (usePositiveLookbehind) {
                sb.append("(?<=");
            } else {
                sb.append("(?<!");
            }
            sb.append(exception);
            sb.append(")");
        }

        sb.append(prohibited);
        return sb.toString();
    }

    /**
     * Check <code>str</code> conforms to this rule.
     * 
     * @param str The string to check.
     * @return <code>true</code> if <code>str</code> conforms to this rule, 
     * <code>false</code> otherwise.
     */
    public boolean isValid(String str) {
        /** TODO: fix false negatives for input: 
         * abc
         * ab c
         * bc aa
         * 
         * cabaabc
         * aabc
         */
        return !negativePattern.matcher(str).find();
    }

    /**
     * Check if <code>str</code> conforms to all the rules in 
     * <code>rules</code>.
     * 
     * @param str The string to check.
     * @param rules The list of rules to check against.
     */
    public static boolean isValid(List<Rule> rules, String str) {
        // Collections.sort(rules, new Comparator<Rule>() {
        //     @Override
        //     public int compare(Rule r1, Rule r2) {
        //         return r1.maxLength() - r2.maxLength();
        //     }
        // });
        // String original = str;
        // // Remove prohibited strings that occur after an exception.
        // for (Rule rule : rules) {
        //     str = rule.pattern.matcher(str).replaceAll("*");
            
        // }        
        // Check that the remaining string conforms to all rules.
        for (Rule rule : rules) {
            if (!rule.isValid(str)) {
                // System.out.print(original + " => ");
                // System.out.println(str);
                return false;
            }
        }
        
        return true;
    }

    /**
     * Gets the length of the prohibited string plus the length of the longest
     * exception for this rule.
     * 
     * @return the length of the prohibited string plus the length of the longest
     * exception for this rule.
     */
    public int maxLength() {
        int length = prohibited.length();
        int maxExceptionLength = 0;
        
        for (String s : exceptions) {
            if(s.length() > maxExceptionLength) {
                maxExceptionLength = s.length();
            }
        }

        return length + maxExceptionLength;
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

 }
