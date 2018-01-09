/**
 * RollinApp.java
 */

package rollin;

import java.util.*;

public class RollinApp{
    // Create random instance and seed it for testing.
    private static final int SEED = 2018;
    private static Random r = new Random(SEED);
    private static int TEST_LIMIT = 9999;

    public static void test1(int numTests) {
        if (numTests < 1) {
            return;
        }

        // Testing statistics.
        int rolls = 0;
        int minRolls = 999;
        int maxRolls = 0;
        double avgRolls = 0;
        // Array containing the frequencies of number of rolls, the last element is for 
        // 10+ rolls.
        int[] rollFrequencies = new int[11];
        long execTime = 0;
        long minExecTime = 999;
        long maxExecTime = 0;
        double avgExecTime = 0;
        
        // Test variables
        int n; // How many rolls it took to get two sets.
        int replaceIndex; // Index of the die to replace or value indicating no replacement.
        int roll; // The value of the die from the die roll.
        long start, end, total;
        MyRollin testObj;
        
        for (int i = 0; i < numTests; i++) {
            // Create an array of six random dice rolls.
            int[] initialDice = new int[6];

            for (int j = 0; j < 6; j++) {
                initialDice[j] = r.nextInt(6) + 1;
            }

            testObj = new MyRollin(initialDice);
            testObj.r = r;

            n = 0;

            start = System.currentTimeMillis();

            // Roll until we get two sets or exceed the limit.
            while (!testObj.isComplete() && n < TEST_LIMIT) {
                roll = r.nextInt(6) + 1;
                replaceIndex = testObj.handleRoll(roll);

                if (replaceIndex != testObj.NO_REPLACE) {
                    testObj.replace(replaceIndex, roll);
                }

                n++;
            }

            end = System.currentTimeMillis();
            total = (end - start);

            if (n >= TEST_LIMIT) {
                System.out.println("Test exceeded maximum number of rolls!");
            }

            if (n >= 10) {
                rollFrequencies[10]++;
            } else {
                rollFrequencies[n]++;
            }

            rolls += n;
            minRolls = (n < minRolls) ? n : minRolls;
            maxRolls = (n > maxRolls) ? n : maxRolls;            
            execTime += total;            
            minExecTime = (total < minExecTime) ? total : minExecTime;
            maxExecTime = (total > maxExecTime) ? total : maxExecTime;
        }

        avgRolls = (double) rolls / numTests;
        avgExecTime = (double) execTime / numTests;

        System.out.println("Test results:\n" +
                            "Total # of rolls: " + rolls + "\n" +
                            "Min. # of rolls: " + minRolls + "\n" + 
                            "Max. # of rolls: " + maxRolls + "\n" +
                            "Avg. # of rolls: " + avgRolls + "\n" +
                            "Total exec. time: " + execTime + "\n" +
                            "Min. exec. time: " + minExecTime + "\n" + 
                            "Max. exec. time: " + maxExecTime + "\n" +
                            "Avg. exec. time: " + avgExecTime + "\n");

        System.out.format("Frequencies of # of Rolls:\n" + 
                            "0\t1\t2\t3\t4\t5\t6\t7\t8\t9\t10+\n" + 
                            "%.2f\t%.2f\t%.2f\t%.2f\t%.2f\t%.2f\t%.2f\t%.2f\t%.2f\t%.2f\t%.2f\n\n",
                            rollFrequencies[0] / (double) numTests, rollFrequencies[1] / (double) numTests,
                            rollFrequencies[2] / (double) numTests, rollFrequencies[3] / (double) numTests,
                            rollFrequencies[4] / (double) numTests, rollFrequencies[5] / (double) numTests,
                            rollFrequencies[6] / (double) numTests, rollFrequencies[7] / (double) numTests,
                            rollFrequencies[8] / (double) numTests, rollFrequencies[9] / (double) numTests,
                            rollFrequencies[10] / (double) numTests);
    }

    public static void test2(int numTests) {
        if (numTests < 1) {
            return;
        }

        // Testing statistics.
        int rolls = 0;
        int minRolls = 999;
        int maxRolls = 0;
        double avgRolls = 0;
        // Array containing the frequencies of number of rolls, the last element is for 
        // 10+ rolls.
        int[] rollFrequencies = new int[11];
        long execTime = 0;
        long minExecTime = 999;
        long maxExecTime = 0;
        double avgExecTime = 0;
        
        // Test variables
        int n; // How many rolls it took to get two sets.
        int replaceIndex; // Index of the die to replace or value indicating no replacement.
        int roll; // The value of the die from the die roll.
        long start, end, total;
        MyRollinBrute testObj;
        
        for (int i = 0; i < numTests; i++) {
            // Create an array of six random dice rolls.
            int[] initialDice = new int[6];

            for (int j = 0; j < 6; j++) {
                initialDice[j] = r.nextInt(6) + 1;
            }

            testObj = new MyRollinBrute(initialDice);
            testObj.r = r;

            n = 0;

            start = System.currentTimeMillis();

            // Roll until we get two sets or exceed the limit.
            while (!testObj.isComplete() && n < TEST_LIMIT) {
                roll = r.nextInt(6) + 1;
                replaceIndex = testObj.handleRoll(roll);

                if (replaceIndex != testObj.NO_REPLACE) {
                    testObj.replace(replaceIndex, roll);
                }

                n++;
            }

            end = System.currentTimeMillis();
            total = (end - start);

            if (n >= TEST_LIMIT) {
                System.out.println("Test exceeded maximum number of rolls!");
            }

            if (n >= 10) {
                rollFrequencies[10]++;
            } else {
                rollFrequencies[n]++;
            }

            rolls += n;
            minRolls = (n < minRolls) ? n : minRolls;
            maxRolls = (n > maxRolls) ? n : maxRolls;
            execTime += total;            
            minExecTime = (total < minExecTime) ? total : minExecTime;
            maxExecTime = (total > maxExecTime) ? total : maxExecTime;
        }

        avgRolls = (double) rolls / numTests;
        avgExecTime = (double) execTime / numTests;

        System.out.println("Test results:\n" +
                            "Total # of rolls: " + rolls + "\n" +
                            "Min. # of rolls: " + minRolls + "\n" + 
                            "Max. # of rolls: " + maxRolls + "\n" +
                            "Avg. # of rolls: " + avgRolls + "\n" +
                            "Total exec. time: " + execTime + "\n" +
                            "Min. exec. time: " + minExecTime + "\n" + 
                            "Max. exec. time: " + maxExecTime + "\n" +
                            "Avg. exec. time: " + avgExecTime + "\n");

        System.out.format("Frequencies of # of Rolls:\n" + 
                            "0\t1\t2\t3\t4\t5\t6\t7\t8\t9\t10+\n" + 
                            "%.2f\t%.2f\t%.2f\t%.2f\t%.2f\t%.2f\t%.2f\t%.2f\t%.2f\t%.2f\t%.2f\n\n",
                            rollFrequencies[0] / (double) numTests, rollFrequencies[1] / (double) numTests,
                            rollFrequencies[2] / (double) numTests, rollFrequencies[3] / (double) numTests,
                            rollFrequencies[4] / (double) numTests, rollFrequencies[5] / (double) numTests,
                            rollFrequencies[6] / (double) numTests, rollFrequencies[7] / (double) numTests,
                            rollFrequencies[8] / (double) numTests, rollFrequencies[9] / (double) numTests,
                            rollFrequencies[10] / (double) numTests);
    }

    public static void main (String[] args){
        try {
            System.out.println("Running test for myRollin");
            test1(Integer.parseInt(args[0]));

            // Reset the random instance
            r = new Random(SEED);
            System.out.println("Running test for myRollinBrute");
            test2(Integer.parseInt(args[0]));
        }
        catch (Exception e) {
            e.printStackTrace();
            System.out.println("Usage: java RollinApp <numTests>");
        }
    }
}


