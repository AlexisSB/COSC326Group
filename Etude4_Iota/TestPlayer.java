package iota;

import java.util.ArrayList;

/**
 * An empty placeholder for a player.
 * @author Anthony Dickson
 */
public class TestPlayer extends Player {
    
    public TestPlayer(Manager m) {
        super(m);
    }
    
    @Override
    public ArrayList<PlayedCard> makeMove() {
        return null;
    }
    
    @Override
    public ArrayList<Card> discard() {
        return null;
    }
    
    
    @Override
    public String getName() {
        return "Test Player";
    }
    
}
