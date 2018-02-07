package ice;
import java.util.*;

public class Suffix{

    String suffixString;
    int branchingOptions;
    long currentCount;
    long previousCount;
    HashSet<String> possibleChildren = new HashSet<String>();

    public Suffix(String suffixString, int branches, long count){
        this.suffixString = suffixString;
        this.branchingOptions = branches;
        this.currentCount = count;
    }

    public String toString(){
        
        return this.suffixString  +" "+ currentCount + " "+ possibleChildren;
    }

    public void addChild(String child){
        //System.err.println("Adding child: " + child + " to " + this.suffixString);
        this.possibleChildren.add(child);
    }

    
        

    public void addChildren(HashSet<String> children){
        this.possibleChildren.addAll(children);
    }

}
    


    
