package rollin;

import java.util.Random;
import java.util.Arrays;

/**
 * Implementation of the abstract class Rollin. 
 * @author Alexis Barltrop, Marcus Lee, Anthony Dickson, and Topic Goran
 */
public class MyRollin extends Rollin {

    /*Random generator for the class.*/
    public static Random r = new Random();
    /* Variable indicates no dice should be replaced.*/
    private static final int NO_REPLACE = -1;
    /* The number of sides on the dice.*/
    private static final int DICE_SIZE = 6;
    /* Variable indicates a set of indicies could not be found. */
    private static final int[] NOT_FOUND = { -1, -1, -1 };
    
    /**
     * Constructor for MyRollin.
     * 
     * @param dice An array of six values from 1 to 6.
     */
    public MyRollin(int[] dice){
        super(dice);
    }
    
    /**
     * Based on a given die roll, determine whether or not replace
     * a die and if we decide to replace a die, also determine which die 
     * to replace.
     *
     * @param roll The value of the die roll
     * @return The index of the die whose value will be replaced by the roll, 
     * or any int outside of 0 to 5 if no replacement is made.
     */
    public int handleRoll(int roll){
        if (isComplete()) {
            return NO_REPLACE;
        }

        // If we do not have a complete hand, we first start by simulating
        // the replacement of each of the dice with the roll, and check if
        // that makes a complete hand.
        int tempDie;
        
        for (int i = 0; i < dice.length; i++){
            tempDie = dice[i];
            dice[i] = roll;

            if (isComplete()) {
                dice[i] = tempDie; // Reset die back to its original value.
                return i;
            }

            dice[i] = tempDie; // Reset die back to its original value.
        }
        
        // If we couldn't make a complete hand in the above steps, then we 
        // check if there is at least one set in our hand...
        int[] set = findSet();
        // If a set was found we...
        if (set != NOT_FOUND) {          
            // Get the set of indices for the remaining dice that are not part
            // of the above set.
            int[] remainingDice = getNonSetIndicies(set);
            
            // Check if we have the optimal case (the remaining dice can make 
            // two pairs), and if we do, we wait for one of the dice that we
            // need to complete the set. We do this because when we have two 
            // pairs, we have a 2/6 chance of rolling a die that we need on 
            // the next turn (we can make either a set of 3 identical values
            // or 3 consecutive values).
            if ((findPairIdentical(remainingDice) != NOT_FOUND
                 && findPairConsecutive(remainingDice) != NOT_FOUND)
                ||(findPairIdentical(remainingDice) != NOT_FOUND
                   && findPairSeparated(remainingDice) != NOT_FOUND)) {
                return NO_REPLACE;
            } 

            // Otherwise if we do not have the optimal case but we have at 
            // least one set in our hand, we are guaranteed to have a pair 
            // among the remaining dice.
            int[] pair = findPair(remainingDice); 

            // After finding the pair, we should try to make that pair into a
            // a set of 3. So we choose the index of the die that is not part 
            // of that pair. 
            if (pair[0] == remainingDice[0] && pair[1] == remainingDice[1]) {
                return remainingDice[2];
            } 
            else if (pair[0] == remainingDice[0] && pair[1] == remainingDice[2]) {
                return remainingDice[1];
            } 
            // else if (pair[0] == remainingDice[1] && pair[1] == remainingDice[2]) {
            else {
                return remainingDice[0];                
            }
        } 
        // Otherwise if there are no sets in the current hand we choose 
        // a random die index...
        int index = r.nextInt(DICE_SIZE);
        // But avoid replacing a die with another die of the same value.
        while (dice[index] == roll){
            index = r.nextInt(DICE_SIZE);
        }

        return index;  
    }

    /**
     * Attempt to find a set among the dice and return the indicies of the
     * dice in that set.
     *
     * @return Set of indicies if the dice form a set, NOT_FOUND otherwise.
     */
    public final int[] findSet() {
        for (int[][] si : setIndices) {
            if (isSet(si[0])) {
                return si[0];
            } 
            else if (isSet(si[1])) {
                return si[1];
            }
        }
        
        return NOT_FOUND;
    }

    /**
     * Creates an array of indicies up to dice.length, but excludes the 
     * indicies in the given set. 
     *
     * @param set The indicies of the set that was found.
     * @return The indicies of the dice that are not in the given set
     */
    private final int[] getNonSetIndicies(int[] set) {
        int[] nonSetIndicies = new int[dice.length - set.length];

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
     * Given a set of 3 dice indicies, try to find any of the following:
     * a) two indentical die values.
     * b) two consecutive die values. 
     * c) two die values that have a difference of two.
     * and return the pair of indicies of those dice.
     *
     * @param set The set of three indicies to look at.
     * @return The indicies of the dice in the pair if found, otherwise returns
     * NOT_FOUND.
     */
    private final int[] findPair(int[] set) {
        int[] pair = findPairIdentical(set);

        if (pair != NOT_FOUND) {
            return pair;
        }

        pair = findPairConsecutive(set);
        return (pair != NOT_FOUND) ? pair : findPairSeparated(set);
    }

    /** 
     * Given a set of 3 dice indicies, try to find two consecutive die 
     * values and return the pair of indicies of those dice.
     *
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
    
    /** 
     * Given a set of 3 dice indicies, try to find two die values that have 
     * a difference of two and return the pair of indicies of those dice.
     *
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
    
    /** Given a set of 3 indicies, try to find a separated pair,
     * (where a separated pair is a set of two numbers that have a difference
     * of two and given the right third number, would create a set of three
     * consecutive numbers. e.g. { 1, 3}) and return the pair of indicies 
     * of those dice. 
     *
     * @param set The set of three indicies to look at.
     * @return The indicies of the dice in the pair if found, otherwise returns
     * NOT_FOUND.
     */
    private final int[] findPairSeparated(int[] set) {
        // Check if there are 2 consecutive die values...
        if (Math.abs(dice[set[0]] - dice[set[1]]) == 2) {
            return new int[] { set[0], set[1] };
        } 
        else if (Math.abs(dice[set[0]] - dice[set[2]]) == 2) {
            return new int[] { set[0], set[2] };
        } 
        else if (Math.abs(dice[set[1]] - dice[set[2]]) == 2) {
            return new int[] { set[1], set[2] };
        }

        // Return not found value.
        return NOT_FOUND;
    }
    
} 
