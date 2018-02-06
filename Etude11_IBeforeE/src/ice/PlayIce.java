package ice;

import java.util.*;

/**
 * An application that plays the 'ice game'.
 * 
 * Description:
 * I Before E
 * English spelling has a lot of rules and a lot of exceptions. For instance 
 * “I before E except after C or . . . ”. Martians find these very quaint and 
 * have made up a game involving variations on the rules. The game is to 
 * identify from a set of rules, which of a list of words are correctly spelled
 * according to the rules. More advanced players are asked to figure out how 
 * many possible words there are of a given length. In a particularly simple 
 * version of the game the alphabet used consists of the three characters 
 * i, c, and e, and there is only one rule – “ i before e except after c”. In 
 * this version of the game, all of the words ice, iiiiiiiii, iiiieeeeecei are 
 * valid, but the words eic, ieceiei, and apple are not. The number of valid 
 * words of length three is 22 – of the 27 unrestricted words of length three 
 * using only the allowed characters there are three invalid ones beginning ei, 
 * and two ending ei. Note that scientists have observed that just because ei is 
 * permitted after c (while otherwise forbidden) this does not imply that ie is 
 * forbidden after c. More complex versions of the game also exist – and the 
 * whole family of them are referred to as “the ice game”
 * 
 * @author Anthony Dickson
 */
public class PlayIce {
    final Validator validator;
    final String alphabetString;
    List<Instance> instances = new ArrayList<>();
    ArrayDeque<String> chars = new ArrayDeque<>();
    static List<Rule> rules = new ArrayList<>();
    static Alphabet alphabet;
    /*Suffix list variables for the dynamic calculation methods */
    ArrayList<Suffix> initialListOfSuffixes = new ArrayList<Suffix>();
    ArrayList<Suffix> previousSuffixes = new ArrayList<Suffix>();
    ArrayList<Suffix> currentSuffixes = new ArrayList<Suffix>();

    public PlayIce(Validator validator, List<Instance> instances) {
        this.validator = validator;
        this.instances = instances;
        this.alphabetString = validator.alphabet.toString();
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
            System.err.println("Target Length: " + targetLength);
            System.err.println("Rule Length : " + ruleLength);
            System.err.println("Exception Length : " + exceptionLength);
            //System.err.println("Total Length : " + totalLength);
            if(maxRuleLength(rules) == -1 || targetLength <= totalLength-1){
                System.out.println(count(targetLength));
            }else{

                if(initialListOfSuffixes.isEmpty()){
                    createInitialList(totalLength-1);
                    System.err.println("Created Initial List");
                    findChildren(initialListOfSuffixes);
                    System.err.println("Found Children");
                }
                
                /*Figure out how many more steps are needed to get to the target length.*/
                int remainingLength = targetLength-totalLength;
                //System.err.println("Remaining Length : " + remainingLength);
                
                int steps = 0;
                for (int currentLength = 0; currentLength < remainingLength+1 ;currentLength++){
                    //System.err.println("currentLength : " + (currentLength+totalLength));
                    
                    step(currentLength);
                    steps++;
                    //System.err.println("Previous : " + previousSuffixes);
                }
                //System.err.println("Steps: " + steps);
                //System.err.println("Current");
                
                /*Print Answers*/
                System.out.println("Brute Force: " + count(Integer.parseInt(instance.value)));
                System.out.println("Dynamic : " + total(currentSuffixes) );

                /*Clean up*/
                previousSuffixes.clear();
                currentSuffixes.clear();
                
            }
        }
    }

    
    /**
     * Updates the count of strings with a given suffix.
     * Increases the length of string by 1.
     * @author Alexis Barltrop
     */
    private void step(int currentLength){
        
        /*Copy list of suffixs into current list*/
        if (currentSuffixes.isEmpty()){
            //System.err.println("First Step");
            
            previousSuffixes = initialListOfSuffixes;
            for(Suffix s : previousSuffixes){
                Suffix copy = new Suffix(s.suffixString,s.branchingOptions,s.count);
                copy.addChildren(s.possibleChildren);
                currentSuffixes.add(copy);
            }
            
        }else{
            /*make a copy of currentSuffixes and set to previous.*/
            previousSuffixes.clear();
            for(Suffix s : currentSuffixes){
                Suffix copy = new Suffix(s.suffixString,s.branchingOptions,s.count);
                copy.addChildren(s.possibleChildren);
                previousSuffixes.add(copy);
            }
            //System.err.println("Previous : " + previousSuffixes);
        }

        /*Reset count for current lsit of suffixes*/
        for(Suffix current : currentSuffixes){
            current.count =0;
        }

        /* Add the count of each suffix in the previous array
         * to the count of its children in the current suffix list*/
        for(Suffix previous: previousSuffixes ){
            for(String child: previous.possibleChildren){
                for(Suffix next: currentSuffixes){
                    if(next.suffixString.equals(child)){
                        next.count += previous.count;
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
            for (int i = 0; i < alphabetString.length(); i++) {
                String nextString = s.suffixString + alphabetString.charAt(i);
                //System.err.println(nextString);
                
                /* For each rule, check the last n characters to see if it violates them.
                 * where n is the length of the rule.
                 * If yes then check against any exceptions for that rule.
                 * If it passes with exception then its okay to add.*/
                boolean rejectChild = false;
                for (Rule r : rules){
                    int totalRuleLength = r.lengthOfRule()+r.maxExceptionLength();
                    if(r.isValid(nextString.substring(nextString.length()-r.lengthOfRule()))){
                        //System.err.println("Rule is Valid");
                                               
                    }else if(r.isValid(nextString.substring(nextString.length()-totalRuleLength))){
                        //System.err.println("Rule is Valid with exception" );
                        
                    }else{
                        //System.err.println("Rejecting : " + nextString);
                        rejectChild = true;
                    }
                }
                
                if (!rejectChild){
                    for(Suffix searchSuffix : suffixes){
                        if (searchSuffix.suffixString.equals(nextString.substring(1))){
                            s.addChild(searchSuffix.suffixString);
                        }
                    }
                }
                
            }
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
            total += (s.count);
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

    private void createInitialList(int targetLength, int depth){
        String curr = String.join("", chars);
        boolean valid = validator.isValid(curr);

        /**
         * If the current string length is the same as the target,
         * i.e. the max length rule then add it to the list of suffixes.
         * @author Alexis
         */
        if(curr.length() == targetLength){
            Suffix s;
            if(valid){
                s = new Suffix(curr,0,1);
                initialListOfSuffixes.add(s);
            }else{
                s = new Suffix(curr,0,0);
                initialListOfSuffixes.add(s);
            }
            return;
        }
        
        for (int i = 0; i < alphabetString.length(); i++) {
            chars.add(alphabetString.substring(i, i + 1));
            //System.err.println(chars);
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
        
        for (int i = 0; i < alphabetString.length(); i++) {
            chars.add(alphabetString.substring(i, i + 1));
            //System.err.println(chars);
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
        //Calculate max length of rule
        //System.err.println("Max Rule Length :" + maxRuleLength(rules));
        
        
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
