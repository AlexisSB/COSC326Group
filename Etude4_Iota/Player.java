package iota;

import java.util.ArrayList;

/**
 * An abstract Iota player
 * @author Michael Albert
 */
public abstract class Player {
    
    final Manager m;
    final static ArrayList<PlayedCard> PASS = new ArrayList<PlayedCard>();
    
    public Player(Manager m) {
        this.m = m;
    }
    
    /**
     * Make a move as requested by the manager. Note that as a player you
     * will be able to query the manager about the current state of the board
     * and your hand.
     * 
     * If the move returned is invalid, you will be deemed to have passed 
     * (i.e., done nothing) and will not be asked for a discard.
     * 
     * If you genuinely want to make a pass and discard, then return an empty
     * list which will trigger the manager to call your discard() method. Any
     * discards that are not actually in your hand will be ignored!
     * 
     * @return The move you intend to make.
     * 
     */
    public abstract ArrayList<PlayedCard> makeMove();
    
    /**
     * Discard some cards from your hand as requested by the manager. The
     * cards returned are added to the bottom of the deck.
     * 
     * @return the cards you wish to discard
     */
    public abstract ArrayList<Card> discard();
    
    
    /**
     * Announce your name.
     * 
     * @return The name of this player.
     */
    public abstract String getName();
    
}
