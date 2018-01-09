package rollin;

import java.util.*;

/**
 * Implementation of the abstract class Rollin.
 * @see Rollin
 */
public class MyRollin extends Rollin {
    /** Return value for handleRoll() if no dice are replaced. */
    public static final int NO_REPLACE = -1; 
    /** Random number generator for choosing which dice to replace.
     * Can be replaced if you want to use a seeded instance. */
    public static Random r = new Random();
    /** Controls whether or not to print debug text. */
    public static boolean DEBUG = false;
    /** Return value for a few methods when a set of indicies could
     * not be found. */
    private static final int[] NOT_FOUND = { -1, -1, -1 };
    /** Return value for suggestReplaceIndex indicating there was no suggestion
     * for which die to replace. */
    private static final int NO_SUGGESTION = NO_REPLACE - 1;
    
    public MyRollin(int[] dice){
        super(dice);
    }

    /**
     * Replaces the die at the given index with the given value.
     * @param i The index of the die to replace.
     * @param value The new value of the die. 
     */
    public void replace(int i, int value) {
        dice[i] = value;
    }

    /**
     * Given a die roll, determine whether or not to replace a dice and which
     * one to replace.
     *
     * @param roll The value of the die roll.
     * @return The index of the die whose value will be replaced by the roll, 
     * or any int outside of 0 to 5 if no replacement is made.
     */
    public int handleRoll(int roll){
        printDebug("Current dice: " + Arrays.toString(dice));

        // If we do not have two sets already...
        if (!isComplete()) {
            // Initially just choose a random die.
            int replaceIndex = randomDie(roll);

            // Try to find a set and the indicies of that set and the indicies
            // that do not belong to that set.            
            // Try to find a set and their indicies.
            int[] set = findSet();
            
            // If a set of indentical die values was found...
            if (set != NOT_FOUND) {
                printDebug("Set found.");
            } 
            else {
                // Otherwise if neither a set of identical die values or a set 
                // of consecutive die values were found...
                printDebug("No suitable candidates found. " + 
                           "Replacing die at index " + replaceIndex);
                // Return the randomly generated die index.
                return replaceIndex;
            } 

            // Find the non-set indicies.
            int[] nonSet = getNonSetIndicies(set);

            printDebug("Found set indicies: " + Arrays.toString(set));
            printDebug("Found non-set indicies: " + 
                        Arrays.toString(nonSet));
            
            int suggestedReplaceIndex = suggestReplaceIndex(nonSet, roll);

            printDebug(""); // Formatting.
            
            // If there was no suggestion, return the random die index. 
            // Otherwise return the suggested index.
            return (suggestedReplaceIndex == NO_SUGGESTION) ? 
                replaceIndex : suggestedReplaceIndex;
        }

        // Return value indicating that no dice should be replaced.
        return NO_REPLACE;
    }

    //////////////////////////// Helper functions ////////////////////////////

    /** 
     * Helper function that handles 'debug' logging to the console. 
     */
    private void printDebug(String msg) {
        if (DEBUG) {
            System.out.println(msg);
        }
    }

    /**
     * Chooses a random die index, where the die value at that index is not
     * the same as the roll die value.
     * @param roll The current roll.
     * @return The selected die index. */
    private int randomDie(int roll) {
        int i = r.nextInt(6);

        while (dice[i] == roll) {
            i = r.nextInt(6);
        }

        return i;
    }

    /**
     * Determine whether the current dice form a set and return the indicies
     * for that set.
     * @return set of indicies if the dice form a set, NOT_FOUND otherwise
     */
    public final int[] findSet() {
        for (int[][] si : setIndices) {
            if (isSet(si[0])) {
                return si[0];
            } else if (isSet(si[1])) {
                return si[1];
            }
        }
        
        return NOT_FOUND;
    }

    /**
     * Gets the indicies of all the dice, except for those that are
     * contained in the given set.
     * @param set The indicies of the set that was found.
     * @return The indicies of the dice that are not in set */
    private final int[] getNonSetIndicies(int[] set) {
        int[] nonSetIndicies = new int[3];

        // Construct the nonSetIndicies array by adding the 
        // indices that are not present in set
        int k = 0; // Index of the value in nonSetIndicies to set.
        boolean found;

        for (int i = 0; i < dice.length; i++) {
            found = false;

            for (int j = 0; j < set.length; j++) {
                if (i == set[j]) {
                    found = true;
                }
            }

            if (!found) {
                nonSetIndicies[k++] = i;
            }
        }

        return nonSetIndicies;
    }

    /**
     * Tries to make a set of 2 identical or consecutive die values, and then
     * suggests the remaining die value as the die to replace.
     * @param x The resulting array from getNonSetIndicies().
     * @param roll The current roll that was given to handleRoll().
     * @return The index of the die that is suggested to be replaced, 
     * NO_REPLACE if no die should be replaced, or NO_SUGGESTION.
     */
    private int suggestReplaceIndex(int[] x, int roll) {
        int replaceIndex = NO_SUGGESTION;

        // Try to find a swap that will result in a higher 
        // probability of forming a set than the current selected index.
        // I.e. If we have 2 pairs (identical & consecutive.), 
        // e.g. { 1, 2, 2 }, and replacing one of these with the current roll
        // would form a set, we should replace that die. Otherwise, we should
        // not replace any dice.  
        if (findPairIdentical(x) != NOT_FOUND &&
            findPairConsecutive(x) != NOT_FOUND) {
            if (isSet(roll, dice[x[1]], dice[x[2]])) {
                return x[0];
            } 
            else if (isSet(dice[x[0]], roll, dice[x[2]])) {
                return x[1];
            }
            else if (isSet(dice[x[0]], dice[x[1]], roll)) {
                return x[2];
            }

            return NO_REPLACE;
        }

        // Pairs of indicies which indicate the index of the pairs if
        // found.
        int[] pair = findPair(x); 
        
        if (pair == NOT_FOUND) {
            return NO_SUGGESTION;
        }

        // If a pair was found, we should try to make that pair into a
        // a set of 3. So for replaceIndex we choose the index of the die 
        // that was not in the pair.
        if (pair[0] == x[0] && pair[1] == x[1]) {
            replaceIndex = x[2];
        } 
        else if (pair[0] == x[0] && pair[1] == x[2]) {
            replaceIndex = x[1];
        } 
        else if (pair[0] == x[1] && pair[1] == x[2]) {
            replaceIndex = x[0];
        }

        return replaceIndex;
    }   

    /** Given a set of 3 indicies, tries to find either:
     * a) two indentical die values.
     * b) two consecutive die values. 
     * and returns the set of indicies associated with those die values.
     * @param set The set of three indicies to look at.
     * @return The indicies of the dice in the pair if found, otherwise returns
     * NOT_FOUND.
     */
    private final int[] findPair(int[] set) {
        // TODO: find pairs that have a difference of two e.g. { 1, 3}
        int[] pair = findPairIdentical(set);
        return (pair != NOT_FOUND) ? pair : findPairConsecutive(set);
    }

    /** Given a set of 3 indicies, tries to find two indentical die values.
     * and returns the set of indicies associated with those die values.
     * @param set The set of three indicies to look at.
     * @return The indicies of the dice in the pair if found, otherwise returns
     * NOT_FOUND.
     */
    private final int[] findPairIdentical(int[] set) {
        // Check if there are 2 identical die values.
        if (dice[set[0]] == dice[set[1]]) {
            return new int[] { set[0], set[1] };
        } 
        else if (dice[set[0]] == dice[set[2]]) {
            return new int[] { set[0], set[2] };
        } 
        else if (dice[set[1]] == dice[set[2]]) {
            return new int[] { set[1], set[2] };
        }

        // Return not found value.
        return NOT_FOUND;
    }
    
    /** Given a set of 3 indicies, tries to find two consecutive die values. 
     * and returns the set of indicies associated with those die values.
     * @param set The set of three indicies to look at.
     * @return The indicies of the dice in the pair if found, otherwise returns
     * NOT_FOUND.
     */
    private final int[] findPairConsecutive(int[] set) {
        // Check if there are 2 consecutive die values...
        if (Math.abs(dice[set[0]] - dice[set[1]]) == 1) {
            return new int[] { set[0], set[1] };
        } 
        else if (Math.abs(dice[set[0]] - dice[set[2]]) == 1) {
            return new int[] { set[0], set[2] };
        } 
        else if (Math.abs(dice[set[1]] - dice[set[2]]) == 1) {
            return new int[] { set[1], set[2] };
        }

        // Return not found value.
        return NOT_FOUND;
    }

    /**
     * Determine whether the given dice form a set.
     * 
     * @param a The first dice value.
     * @param b The second dice value.
     * @param c The third dice value.
     * @return true if the dice form a set, false otherwise.
     */
    public boolean isSet(int a, int b, int c) {
        // All three dice the same is a set
        if (a == b && b == c) {
            return true;
        }
        // If not all three are the same, then any two the same is not a set
        if (a == b || a == c || b == c) {
            return false;
        }
        
        // If all three are different and largest minus smallest is 2 then it
        // is a set, otherwise not.
        int max = Math.max(a, Math.max(b, c));
        int min = Math.min(a, Math.min(b, c));
        return max - min == 2;
    }

} 


