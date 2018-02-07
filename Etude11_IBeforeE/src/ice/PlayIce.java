package ice;

import java.util.*;

/**
 * COSC326 Etude 11 I Before E SS 2018
 *
 * An application that plays the 'ice game'.
 * 
 * Description for etude availible at:
 * http://www.cs.otago.ac.nz/cosc326/etudes2018ss/11.pdf
 *
 * 
 * @author Anthony Dickson, Alexis Barltrop, Marcus Lee, Topic Goran
 */
public class PlayIce {
    final Validator validator;
    List<Instance> instances = new ArrayList<>();
    ArrayDeque<String> chars = new ArrayDeque<>();
    static List<Rule> rules = new ArrayList<>();
    static Alphabet alphabet;
    /*Suffix list variables for the dynamic calculation methods */
    //ArrayList<Suffix> initialListOfSuffixes = new ArrayList<Suffix>();
    //ArrayList<Suffix> previousSuffixes = new ArrayList<Suffix>();
    ArrayList<Suffix> currentSuffixes = new ArrayList<Suffix>();

    public PlayIce(Validator validator, List<Instance> instances) {
        this.validator = validator;
        this.instances = instances;
    }

    public void play() {        
        for (Instance instance : instances) {
            play(instance);
        }
    }

    public void play(Instance instance) {
        if (instance.type == Instance.Type.SEQUENCE) {
            System.out.println((validator.isValid(instance.toString())) ? 
                "Valid" : "Invalid");
        } else {

            /**
             * Check if there are no rules to consider,
             * or if the desired length is less than the rule length.
             * If true for either then just brute force it.
             * NB could change this to just return n^l
             * where n is the alphabet size and l is the desired length.
             * @author Alexis Barltrop
             */

            int targetLength = Integer.parseInt(instance.value);
            int ruleLength = maxRuleLength(rules);
            int exceptionLength = maxExceptionLength(rules);
            int totalLength = ruleLength + exceptionLength;
            
            if (rules.size() == 0) {
                System.out.println((long) Math.pow(alphabet.length(), targetLength));
            } else if(targetLength <= totalLength - 1) {
                System.out.println(count(targetLength));
            } else {
                if(currentSuffixes.isEmpty()){
                    createInitialList(totalLength - 1);
                    System.err.println("Done initial list");
                    findChildren(currentSuffixes);
                    System.err.println("Done children");
                }else{
                    setCountToZero(currentSuffixes);
                }
                
                /*Figure out how many more steps are needed to get to the target length.*/
                int remainingLength = targetLength - totalLength;
                
                for (int currentLength = 0; currentLength < remainingLength + 1; currentLength++) {
                    step();
                }
                
                /*Print Answer*/
                System.out.println( "Brute Force: " + count(targetLength));
                System.out.println(total(currentSuffixes));

                /*Clean up*/
                //previousSuffixes.clear();
                //currentSuffixes.clear();   
                
            }
            
        }
    }

    public static void setCountToZero(ArrayList<Suffix> suffixes){
        for (Suffix s: suffixes){
            s.previousCount =0;
            s.currentCount = s.initialCount;
        }
    }

    /**
     * Updates the count of strings with a given suffix.
     * Increases the length of string by 1.
     * @author Alexis Barltrop
     */
    private void step() {        
        /*Copy list of suffixs into current list*/

        // currentSuffixes = initialListOfSuffixes;
        for(Suffix s : currentSuffixes){
            s.previousCount = s.currentCount;
            s.currentCount = 0;
        }
        
        for(Suffix s : currentSuffixes){
            for (String child: s.possibleChildren){
                for(Suffix next: currentSuffixes) {
                    if(next.suffixString.equals(child)) {
                        next.currentCount += s.previousCount;
                    }
                }
            }
        }

           }

    /**
     * Creates the list of valid substrings that can be created from a given suffix.
     * Directly modifies the suffixes.
     * @param suffixes - list of suffixes to find substrings from.
     * @author Alexis Barltrop
     */
    private void findChildren(ArrayList<Suffix> suffixes){
        int ruleLength = maxRuleLength(rules);
        
        for (Suffix s: suffixes){
            /*Add each one of the letters from the alphabet to the end of the suffix*/
            for (int i = 0; i < alphabet.length(); i++) {
                String nextString = s.suffixString + alphabet.get(i);
                
                /* For each rule, check the last n characters to see if it violates them.
                 * where n is the length of the rule.
                 * If yes then check against any exceptions for that rule.
                 * If it passes with exception then its okay to add.*/
                boolean rejectChild = false;
                for (Rule r : rules){
                    int totalRuleLength = r.totalLengthOfRule();
                    if(r.isValid(nextString.substring(nextString.length() - r.lengthOfRule()))) {
                                               
                    }else if(r.isValid(nextString.substring(nextString.length() - totalRuleLength))) {
                        
                    }else{
                        rejectChild = true;
                    }
                }
                
                if (!rejectChild){
                    s.addChild(nextString.substring(1));
                }
                
            }
            System.err.println(s.possibleChildren);
        }
    }
    
    /**
     * Calculates the sum of the suffix count to get the final answer.
     * @param suffixes - list of suffixes to sum.
     * @return the sum of all the counts in the suffixes.
     */
    private long total(ArrayList<Suffix> suffixes){
        long total = 0;
        for (Suffix s : suffixes){
            total += (s.currentCount);
        }
        return total;
    }

    /**
     * Create a list of all the possible combinations of letters.
     * Does it for a given length using the global alphabet.
     */
    private void createInitialList(int targetLength){
        createInitialList(targetLength,0);          
    }

    private void createInitialList(int targetLength, int depth) {
        String curr = String.join("", chars);
        boolean valid = validator.isValid(curr);

        /**
         * If the current string length is the same as the target,
         * i.e. the max length rule then add it to the list of suffixes.
         * @author Alexis
         */
        if(curr.length() == targetLength) {
            Suffix s;
            
            if(valid) {
                s = new Suffix(curr, 0, 1);
                currentSuffixes.add(s);
            } else {
                s = new Suffix(curr, 0, 0);
                currentSuffixes.add(s);
            }
            
            return;
        }
        
        for (int i = 0; i < alphabet.length(); i++) {
            chars.add(alphabet.get(i));
            createInitialList(targetLength, depth + 1);
            chars.removeLast();
        }

    }
    
    private long count(int targetLength) {
        return count(targetLength, 0);
    }

    private long count(int target, int depth) {
        String curr = String.join("", chars);
        boolean valid = validator.isValid(curr);

        if (depth == target) {
            return (valid) ? 1 : 0;
        }

        if (!valid) {
            return 0;
        } 
        
        long count = 0;
        
        for (int i = 0; i < alphabet.length(); i++) {
            chars.add(alphabet.get(i));
            count += count(target, depth + 1);
            chars.removeLast();
        }

        return count;
    }

    
    
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        
        // Process Input.
        alphabet = new Alphabet(in.nextLine());
   

        String line = "";

        //add the rules
        do {
            line = in.nextLine();
            
            if (!line.equals("")) rules.add(new Rule(line));
        } while (!line.equals(""));        
        
        Validator validator = new Validator(alphabet, rules);        
        List<Instance> instances = new ArrayList<>();

        while (in.hasNextLine()) {
            instances.add(new Instance(in.nextLine()));
        }

        // Create ice game.
        PlayIce iceGame = new PlayIce(validator, instances);
        // Play the game.
        iceGame.play();

        in.close();
    }
    

    public static int maxRuleLength(List<Rule> rules){
        int maxLength = -1;
        for (Rule r: rules){
            if( r.lengthOfRule() > maxLength){
                maxLength = r.lengthOfRule();
            }
        }
        return maxLength;
    }

    public static int maxExceptionLength(List<Rule> rules){
        int maxLength = -1;
        for (Rule r: rules){
            if( r.maxExceptionLength() > maxLength){
                maxLength = r.maxExceptionLength();
            }
        }
        return maxLength;
    }

    
}
