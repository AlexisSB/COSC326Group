

/**
 * An abstract class for the Rollin' etude used in COCSC326 SS 2018.
 *
 * @author Michael Albert
 */
public abstract class Rollin {

    /*
    A convenience array used in determining whether or not six dice form
    two sets -- it represents all the possible partitions of the indices 0
    through 5 into two groups of three.
    */
    static final int[][][] setIndices = new int[][][]{
        {{0, 1, 2}, {3, 4, 5}},
        {{0, 1, 3}, {2, 4, 5}},
        {{0, 1, 4}, {2, 3, 5}},
        {{0, 1, 5}, {2, 3, 4}},
        {{0, 2, 3}, {1, 4, 5}},
        {{0, 2, 4}, {1, 3, 5}},
        {{0, 2, 5}, {1, 3, 4}},
        {{0, 3, 4}, {1, 2, 5}},
        {{0, 3, 5}, {1, 2, 4}},
        {{0, 4, 5}, {1, 2, 3}}
    };

    int[] dice;

    /**
     * Implementing classes should just call this superclass constructor which
     * simply initialises the array of dice values. You may assume that it will
     * only ever be called with arrays of six values from 1 to 6.
     *
     * @param dice
     */
    public Rollin(int[] dice) {
        this.dice = dice;
    }

    /**
     * Obvious accessor method. Can be used by the 'supervisor' program to
     * ensure that you are maintaining your dice values correctly.
     *
     * @return The current array of dice values.
     */
    public final int[] getDice() {
        return dice;
    }

    /**
     * This is the method you need to implement. It should decide what to do
     * with a given roll and the current dice. If using the roll to substitute
     * for an existing die, it should return the index of the die that is being
     * replaced, i.e, if the roll is replacing the value dice[2], then it should
     * return 2. If the roll is not being used, then it may return any value
     * outside of the range from 0 to 5 inclusive.
     * 
     * Note that while your class may have other methods, none of them should
     * modify the array dice, e.g., by sorting it, since the supervisor program
     * will expect the results of handleRoll to be applied to the original set
     * of dice (and subsequently to its modified versions if replacements have
     * been made).
     *
     * @param roll The value of the die roll
     * @return The index of the die whose value will be replaced by the roll, or
     * any int outside of 0 to 5 if no replacement is made.
     */
    public abstract int handleRoll(int roll);

    /**
     * Determine whether the current dice form two sets.
     * @return true if the dice form two sets, false otherwise
     */
    public final boolean isComplete() {
        for (int[][] si : setIndices) {
            if (isSet(si[0]) && isSet(si[1])) {
                return true;
            }
        }
        return false;
    }

    /**
     * Determine whether the dice at a given triple of indices form a set.
     * 
     * @param indices the indices
     * @return true if the dice at those indices form a set, false otherwise.
     */
    public boolean isSet(int[] indices) {
        // First just get the values at those indices to save typing.
        int a = dice[indices[0]];
        int b = dice[indices[1]];
        int c = dice[indices[2]];
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
