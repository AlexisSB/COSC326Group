import java.util.*;

public class SwapableSets{

	private ArrayList<Integer> dices = new ArrayList<Integer>();
	private ArrayList<ArrayList<Integer>> possibleSets = new ArrayList<ArrayList<Integer>>();
	private ArrayList<Integer> bestSet;
	
	public SwapableSets(int[] rolls, int lastRoll){
		for (int roll : rolls) dices.add(roll);
		dices.add(lastRoll);
	}

	public int[] getBestSet(){
		int size = bestSet.size();
		int[] setArray;
		bestSet.remove(size - 1);
		setArray = bestSet.stream().mapToInt(i->i).toArray();
		return setArray;
	}
		
	public void findSets(){
	
		Collections.sort(dices);

		findFirstSets();
		
		bestSet = selectSecondSet();
		
	}

	private ArrayList<Integer> selectSecondSet(){
		ArrayList<Integer> bestSet = new ArrayList<Integer>(); 
		int lastSet = 0, curSet = 0;		

		for (ArrayList<Integer> set : possibleSets){
			
			
				curSet = secondSetCheck(set);
				if(curSet == 3) return set;
				else if(curSet > lastSet){
			 		bestSet = set;
					lastSet = curSet;
				}	

		}
		return bestSet;
	}

	private int secondSetCheck(ArrayList<Integer> set){
		ArrayList<Integer> secondSet = new ArrayList<Integer>(set.subList(3, 7));
		int[] consec;
		int[] same;
		
		
		Collections.sort(secondSet);

		consec = conscSetArray(secondSet);
		same = sameSetArray(secondSet);


		if(arraylen(consec) == 3){
			swapSecondSet(secondSet, consec);
			updateSet(set, secondSet);
			return 3; 
		}else if(arraylen(same) == 3){
			swapSecondSet(secondSet, same);
			updateSet(set, secondSet);
			return 3; 
		}else if(arraylen(consec) == 2){
			swapSecondSet(secondSet, consec);
			updateSet(set, secondSet);
			return 2; 
		}else if(arraylen(same) == 2){
			swapSecondSet(secondSet, same);
			updateSet(set, secondSet);
			return 2; 
		}else return 1;
	}

	private void updateSet(ArrayList<Integer> firstSet, ArrayList<Integer> secondSet){
		for(int i = 0; i < secondSet.size(); i++){
			firstSet.set(i + 3, secondSet.get(i));
		}	
	}


	private void swapSecondSet(ArrayList<Integer> secondSet, int[] array){
		for(int i = 0; i < array.length; i++){
			if(array[i] != -1) Collections.swap(secondSet, i, array[i]);
		}
	} 

	private int arraylen(int [] array){
		if(array[2] != -1)return 3;
		else if(array[1] != -1)return 2;
		else if(array[0] != -1)return 1;
		else return 0;
	}

	private int[] sameSetArray(ArrayList<Integer> set){
		int e = 0, j = 1, i = 2;
		int[] same = {-1, -1, -1}; 
		
		while(i < set.size()){
			if(isSameSet(set.get(e), set.get(j), set.get(i))){
				same[0] = e;		
				same[1] = j;		
				same[2] = i;		
				return same;
			}else if(set.get(e) == set.get(j)){
				same[0] = e;		
				same[1] = j;		
				same[2] = -1;		
			}else if(set.get(j) == set.get(i)){
				same[0] = j;		
				same[1] = i;		
				same[2] = -1;	
			}else if(set.get(e) == set.get(i)){
				same[0] = e;		
				same[1] = i;		
				same[2] = -1;	
			}
			e++;
			j++;
			i++;
		} 

	return same;

	}


	private int[] conscSetArray(ArrayList<Integer> set){
		int e, j, i;
		int[] consecs = {-1, -1, -1}; 
	
		for(e = 0; e < set.size(); e++){
			j = hasConsec(set, e);
			if(j != -1){
				consecs[0] = e;
				consecs[1] = j;
				i = hasConsec(set, j);
				if(i != -1){
					consecs[2] = i;
					return consecs;
				}	
			} 
		}
		return consecs;

	} 



	private void findFirstSets(){
		findSameNumSets();
		findConsecSets();
	}


	private int findConsecSets(){
		int setCount = 0, e, j, i;
	
		for(e = 0; e < dices.size(); e++){
			j = hasConsec(dices, e);
			if(j != -1){
				i = hasConsec(dices, j);
				if(i != -1) possibleSetsAdd(e, j, i);
			} 
		}
		return setCount;
	}	 

	private int hasConsec(ArrayList<Integer> dices, int e){
		for(int i = 0; i < dices.size(); i++){
			if((dices.get(e) + 1) == dices.get(i)) return i;  

		}
		return -1;
	}


	private int findSameNumSets(){
		int setCount = 0, e = 0, j = 1, i = 2;
				
		while(i < dices.size()){
			if(isSameSet(dices.get(e), dices.get(j), dices.get(i))){
				setCount++;		
				possibleSetsAdd(dices, e, j, i);
			}
			e++;
			j++;
			i++;
		} 

	return setCount;
	}



	
	private void possibleSetsAdd(int e, int j, int i){
				ArrayList<Integer> newSet = new ArrayList<Integer>(dices);
				
				Collections.swap(newSet, 0, e);
				Collections.swap(newSet, 1, j);
				Collections.swap(newSet, 2, i);
				possibleSets.add(newSet); 

	}

	private void possibleSetsAdd(ArrayList<Integer> dices, int e, int j, int i){
				ArrayList<Integer> newSet = new ArrayList<Integer>(dices);
				
				Collections.swap(newSet, 0, e);
				Collections.swap(newSet, 1, j);
				Collections.swap(newSet, 2, i);
				possibleSets.add(newSet); 

	}


	private boolean isSameSet(int a, int b, int c){
		if( a == b && b == c) return true;
		else return false;
	}	 


	private void printPossibleSets(){
		System.out.println("possible Sets: \n" +  possibleSets);

	}

}
