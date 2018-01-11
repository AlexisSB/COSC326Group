
import java.util.*;


public class RollinApp{

	public static void main (String[] args){
		
		int[] myDice = new int[6];
		Random rand = new Random();
		
		for(int i = 0; i < myDice.length; i++) myDice[i] = rand.nextInt(6) + 1;
		
  
		RollinDeep Roll = new RollinDeep(myDice);
		System.out.println("getdice():" + Arrays.toString(Roll.getDice())); 
		Roll.handleRoll(rand.nextInt(6) + 1);




	}
	
}

