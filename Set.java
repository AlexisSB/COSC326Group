import java.util.*;

public class Set{

	int[] set = new int[3];
	int completeness, bestSwap;

	public Set(int[] s){
		System.arraycopy(s, 0, set, 0, s.length);
		completeness = checkCompleness(set);
		bestSwap = -1;	
		}


	public int getNeed(int roll){
		int best = -1, curNeed, mostNeed = -5;
		int[] workSet = new int[3];
	
		for(int i = 0; i < set.length; i++){
			System.arraycopy(set, 0, workSet, 0, set.length);
			workSet[i] = roll;
			curNeed = checkCompleness(workSet) - completeness;
			
			if(curNeed > mostNeed){
				mostNeed = curNeed;
				best = i;
			}		
		}

		if(mostNeed > 0) bestSwap = best;
		else bestSwap = -1;

		return mostNeed;
	}

	public int getSwapPos(){
		return bestSwap;
	}

	private int checkCompleness(int[] set){
		int complete = 0;
		if(isComplete(set)) return 4;
		else{
	
			if(hasEndSeq(set)) complete += 1;
			else if(hasMiddleSeq(set)) complete +=2;
			if(hasSame(set)) complete += 1;

			return complete;
			
		}
	}	

	private boolean hasSame(int[] set){
		return set[0] == set[1] || set[1] == set[2] || set[2] == set[0];
	}


	private boolean hasMiddleSeq(int[] set){
		for(int i = 0; i < set.length; i++){
			if(set[i] != 1 && set[i] != 5) 
				if(hasInt(set[i] + 1, set)) return true; 
		}
	return false;
	}


	private boolean hasInt(int target, int[] set){
		for(int i = 0; i < set.length; i++){
			if(set[i] == target) return true;
		}
		return false;
	}

	private boolean hasEndSeq(int [] set){
		if(hasInt(6, set) && hasInt(5, set)) return true;
		else if(hasInt(1, set) && hasInt(2, set)) return true;
		else return false;
	}


	private boolean isComplete(int[] set) {
		int a = set[0];
    int b = set[1];
    int c = set[2];

    if (a == b && b == c) return true;    
    if (a == b || a == c || b == c)  return false;
         
    int max = Math.max(a, Math.max(b, c));
    int min = Math.min(a, Math.min(b, c));
    return max - min == 2;
	}

	public String toString(){
		return "Comp: " + completeness + "\n"  
						+ Arrays.toString(set);
	}

}
