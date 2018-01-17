package iota;

import java.util.ArrayList;

/**
 * An abstract Iota player
 * @author Michael Albert
 */
public abstract class Player {
    
    private final Manager m;
    
    public Player(Manager m) {
        this.m = m;
    }
    
    /**
     * Make a move as requested by the manager. Note that as a player you
     * will be able to query the manager about the current state of the board
     * and your hand.
     * 
     * If the move returned is invalid, you will be deemed to have passed 
     * (i.e., done nothing).
     * 
     * @return The move you intend to make.
     * 
     */
    public abstract ArrayList<PlayedCard> makeMove();
    
    /**
     * Announce your name.
     * 
     * @return The name of this player.
     */
    public abstract String getName();
    
}
