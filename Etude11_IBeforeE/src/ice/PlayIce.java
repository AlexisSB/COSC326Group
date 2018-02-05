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
    //Do we need this string? Can we store alphaet object and use to string?
    final String alphabetString;
    List<Instance> instances = new ArrayList<>();
    ArrayDeque<String> chars = new ArrayDeque<>();

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

    /* Suffix list variables for the dynamic calculation methods */
    ArrayList<Suffix> initialListOfSuffixes = new ArrayList<Suffix>();
    ArrayList<Suffix> previousSuffixes = new ArrayList<Suffix>();
    ArrayList<Suffix> currentSuffixes = new ArrayList<Suffix>();
    
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
            if(maxRuleLength(rules) == -1 ||Integer.parseInt(instance.value) < maxRuleLength(rules)){
                System.out.println(count(Integer.parseInt(instance.value)));
            }else{

                //Count method will generate initial list of suffixes
                System.out.println(count(maxRuleLength(rules)));
                
                //Generate list of children links between suffixes
                findBranches(initialListOfSuffixes);
                
                //Figure out how many more steps are needed to get to the target length.
                int remainingLength = Integer.parseInt(instance.value)- maxRuleLength(rules);
                System.err.println("Remaining Length : " + remainingLength);
                
                //Step through the algortihm that many times.
                for (int currentLength = 1;currentLength < remainingLength+1;currentLength++){
                    System.err.println("currentLength : " + currentLength);
                    step(currentLength);
                    System.err.println("Current : " + currentSuffixes);

                }

                //Print Answers
                System.out.println("Brute Force: " + count(Integer.parseInt(instance.value)));
                System.out.println("Dynamic : " + total(currentSuffixes) );

                //Clean up
                initialListOfSuffixes.clear();
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
        
        //Create copy of suffixes and reset the count.
        
        if (currentLength ==1){
            previousSuffixes = initialListOfSuffixes;
            for(Suffix s : previousSuffixes){
                Suffix copy = new Suffix(s.suffixString,s.branchingOptions,0);
                copy.addChildren(s.possibleChildren);
                currentSuffixes.add(copy);
            }
            
        }else{
            //make a copt of current Suffixes and set to previous.
            previousSuffixes.clear();
            for(Suffix s : currentSuffixes){
                Suffix copy = new Suffix(s.suffixString,s.branchingOptions,s.count);
                copy.addChildren(s.possibleChildren);
                previousSuffixes.add(copy);
            }
            System.err.println("Previous : " + previousSuffixes);
        }
        
        for(Suffix current : currentSuffixes){
            current.count =0;
        }
        
        for(Suffix previous: previousSuffixes ){
            for(String child: previous.possibleChildren){
                for(Suffix next: currentSuffixes){
                    if(next.suffixString.equals(child)){
                        next.count += previous.count;
                    }
                }
            }
        }
        //for each child add the count of that suffix to the count of the child.
        //Update the count for the other suffixes based on what expansion are allowed.
    }

    /**
     * Creates the list of valid substrings that can be created from a given suffix.
     * Directly modifies the suffixes.
     * @param suffixes - list of suffixes to find substrings from.
     * @author Alexis Barltrop
     */
    private void findBranches(ArrayList<Suffix> suffixes){
        
        for (Suffix s: suffixes){
            //System.err.println("Suffix : " + s);
            
            //Add each one of the letters from the alphabet to the end of the suffix
            for (int i = 0; i < alphabetString.length(); i++) {
                String nextString = s.suffixString + alphabetString.charAt(i);
                //System.err.println(nextString);
                
                //Check if it forms a valid substring//
                if(validator.isValid(nextString)){
                    //System.err.println("Valid");
                    
                    //if yes add the child created to the list of possible children.//
                    s.branchingOptions++;
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
    private int total(ArrayList<Suffix> suffixes){
        int total = 0;
        for (Suffix s : suffixes){
            total += (s.count);
        }
        return total;
    }

    
    private long count(int targetLength) {
        return count(targetLength, 0);
    }

    private long count(int target, int depth) {
        String curr = String.join("", chars);
        boolean valid = validator.isValid(curr);

        /**
         * If the current string length is the same as the target,
         * i.e. the max length rule then add it to the list of suffixes.
         * @author Alexis
         */
        if(curr.length() == target){
            //System.err.println(curr);
            Suffix s = new Suffix(curr,0,1); //Have to change alphabet size.
            initialListOfSuffixes.add(s);
       
        }

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

    static List<Rule> rules = new ArrayList<>();
    static Alphabet alphabet;
    
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
        System.err.println("Max Rule Length :" + maxRuleLength(rules));
        
        
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
}
