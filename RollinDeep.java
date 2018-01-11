
import java.util.*;

public class RollinDeep extends Rollin{


	public RollinDeep(int[] dices){
		super(dices);
	}


	public int handleRoll(int roll){
		System.out.println("Roll:" + roll);
		
		Set set1 = new Set(new int[]{dice[0], dice[1], dice[2]});
		Set set2 = new Set(new int[]{dice[3], dice[4], dice[5]});

		if(set1.getNeed(roll) > set2.getNeed(roll)){
			return set1.getSwapPos();
		}else return set2.getSwapPos() + 3;

	}

}
