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
    final Alphabet alphabet;
    final List<Rule> rules;
    final List<Instance> instances;
    ArrayDeque<String> chars = new ArrayDeque<>();
    
    public PlayIce(Validator validator, List<Instance> instances) {
        this.validator = validator;
        this.alphabet = validator.alphabet;
        this.rules = validator.rules;
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
            System.out.println(count(Integer.parseInt(instance.value)));
        }
    }
    
    private long count(int targetLength) {
        if (rules.size() == 0) return (long) Math.pow(alphabet.length, targetLength);

        // Get max rule length.
        int maxRuleLength = 0;

        for (Rule rule : rules) {
            int ruleLength = rule.maxLength();

            if (ruleLength > maxRuleLength) {
                maxRuleLength = ruleLength;
            }
        }

        if (targetLength <= maxRuleLength) return count(targetLength, 0);
        
        // Get valid suffixes
        Set<Suffix> suffixes = new HashSet<>();
        // List<HashMap<String, Integer>> suffixCounts = new ArrayList<HashMap<String, Integer>>();
        getValidSuffixes(maxRuleLength, 0, maxRuleLength - 1, suffixes);//, suffixCounts);

        // for (Suffix s : suffixes) {
        //     System.out.println(s);
        // }

        HashMap<String, Integer> lastCol = new HashMap<>();
        
        for (Suffix suffix : suffixes) {
            lastCol.put(suffix.value, countSuffix(maxRuleLength, 0, suffix.value));
            // System.out.println(suffix.value + " appears " + lastCol.get(suffix.value) + " times at length " + (maxRuleLength));
        }
        
        
        HashMap<String, Integer> currCol = new HashMap<>();
        
        for (int i = maxRuleLength + 1; i < targetLength; i++) {
            for (Suffix suffix : suffixes) {
                int count = 0;
                
                // System.out.print(suffix.value + " appears ");
                for (Suffix other : suffixes) {
                    if (other.hasChild(suffix.value)) {
                        count += lastCol.get(other.value);
                        // System.out.print(lastCol.get(other.value) + " + ");
                    }
                }
                
                currCol.put(suffix.value, count);
                // System.out.println(" = " + currCol.get(suffix.value) + " times at length " + (i));
            }

            lastCol = new HashMap<String, Integer>(currCol);
        }

        long result = 0L;

        for (Suffix suffix : suffixes) {
            // System.out.println(suffix.value + " appears " + lastCol.get(suffix.value) + " times at length " + (targetLength - 1));
            result += (long) lastCol.get(suffix.value) * suffix.children.size();
        }

        System.out.println("Dynamic result: " + result);



        // return result;

        // Count how many times each suffix appears in first couple length strings.
        // for (int i = 1; i <= 2; i++) {
        //     suffixCounts.add(getSuffixCounts(i))
        // }

        // for (HashMap<String, Integer> col : suffixCounts) {
        //     for (Map.Entry<String, Integer> cell : col.entrySet()) {
        //         System.out.print(cell.getKey() + " appears " + cell.getValue() + " times. ");
        //     }

        //     System.out.println();
        // }

        // HashMap<String, Integer> lastCol = new HashMap<>(suffixes);
        
        // // for (int i = suffixCounts.size() + 1; i < targetLength; i++) {
        //     HashMap<String, Integer> counts = new HashMap<>(suffixes);

        //     for (Suffix suffix : suffixes) {
        //         for (String child : suffix.children) {
        //             counts.put(suffix, counts.get(suffix) + suffixCounts.get(i - 1).get(child));
        //         }
        //     }
        // }
        
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

    /**
     * Get the valid suffixes.
     * 
     * @param out Where to store the suffixes
     * @param counts Where to store the counts for each suffix per length string.
     */
    private void getValidSuffixes(int target, int depth, int suffixLength, Set<Suffix> out) {
        String curr = String.join("", chars);
        boolean valid = validator.isValid(curr);
        
        if (depth == target) {
            if (valid) {
                String suffix = curr.substring(curr.length() - suffixLength);
                out.add(new Suffix(suffix));
            }

            return;
        }

        if (!valid) return;
        
        for (int i = 0; i < alphabet.length(); i++) {
            chars.add(alphabet.get(i));
            getValidSuffixes(target, depth + 1, suffixLength, out);
            chars.removeLast();
        }
    }

    private int countSuffix(int targetLength, int depth, String suffix) {        
        String curr = String.join("", chars);
        boolean valid = validator.isValid(curr);

        if (depth == targetLength) {
            // System.out.println(suffix + " appears at end of " + curr + ": " + curr.endsWith(suffix));
            return (curr.endsWith(suffix) && valid) ? 1 : 0;
        }

        if (!valid) {
            return 0;
        } 
        
        int count = 0;
        
        for (int i = 0; i < alphabet.length(); i++) {
            chars.add(alphabet.get(i));
            //System.err.println(chars);
            count += countSuffix(targetLength, depth + 1, suffix);
            chars.removeLast();
        }

        return count;
    }

    private class Suffix{
        final String value;
        final List<String> children;
    
        public Suffix(String value) {
            this.value = value;
            this.children = getChildren();
        }
    
        private List<String> getChildren() {
            List<String> children = new ArrayList<>();
    
            for (int i = 0; i < alphabet.length(); i++) {
                String child = value + alphabet.get(i);
                boolean isRule = false;

                // for (Rule rule : rules) {
                //     if (value.equals(rule.prohibited)) isRule = true;
                // }

                if (validator.isValid(child) || isRule) {
                    children.add(child.substring(1));
                }
            }

            if (children.size() == 0) {
                for (int i = 0; i < alphabet.length(); i++) {
                    String child = value.substring(value.length() - 1) + alphabet.get(i);

                    if (validator.isValid(child)) {
                        children.add(value.substring(1) + alphabet.get(i));
                    }
                }
            }

            return children;
        }

        public boolean hasChild(String child) {
            return children.contains(child);
        }

        @Override
        public int hashCode() {
            int hash = 7;

            for (int i = 0; i < value.length(); i++) {
                hash = hash * 11 + value.charAt(i);
            }

            return hash;
        }

        @Override
        public boolean equals(Object o) {
            return this.value.equals(((Suffix) o).value);
        }
    
        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();

            sb.append(value);
            sb.append(" - ");
            sb.append(children.size());
            sb.append(" children:");
            
            for (String expansion : children) {
                sb.append(" " + expansion);
            }

            return sb.toString();
        }
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        
        // Process Input.
        Alphabet alphabet = new Alphabet(in.nextLine());
        
        String line = "";
        List<Rule> rules = new ArrayList<>();

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
}
