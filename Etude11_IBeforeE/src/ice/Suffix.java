package ice;
import java.util.*;

public class Suffix{

    String suffixString;
    int branchingOptions;
    int count;
    HashSet<String> possibleChildren = new HashSet<String>();

    public Suffix(String suffixString, int branches, int count){
        this.suffixString = suffixString;
        this.branchingOptions = branches;
        this.count = count;
    }

    public String toString(){
        
        return this.suffixString  +" "+ count + " "+ possibleChildren;
    }

    public void addChild(String child){
        this.possibleChildren.add(child);
    }

    
        

    public void addChildren(HashSet<String> children){
        this.possibleChildren.addAll(children);
    }

}
    


    
