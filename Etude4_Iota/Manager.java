package iota;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.stream.*;

/**
 * Manager class for an Iota game
 * 
 * @author Michael Albert
 */
public class Manager {
    
    private ArrayList<PlayedCard> board = new ArrayList<>();
    private Player p1;
    private Player p2;
    private HashMap<Player, ArrayList<Card>> hands = new HashMap<>();
    private HashMap<Player, Integer> score = new HashMap<>();
    private Deck deck;
    
    public Manager() {
        deck = new Deck();
    }
    
    void setPlayers(Player p1, Player p2) {
        this.p1 = p1;
        score.put(p1, 0);

        this.p2 = p2;
        score.put(p2, 0);
    }  
    
    /**
     * Get (a copy of) the current state of the board.
     * @return A copy of the board. Cards in the array list will be in the
     * order they were played.
     */
    public ArrayList<PlayedCard> getBoard() {
        ArrayList<PlayedCard> result = new ArrayList<>();
        for(PlayedCard c : board) {
            result.add(c.copy());
        }
        return result;
    }
    
    /**
     * Return the hand of the given player.
     * 
     * @param p The player.
     * @return The hand of the given player.
     */
    public ArrayList<Card> getHand(Player p) {
        return hands.get(p);
    }
    
    /**
     * Compute the net score of the given player.
     * @param p the player.
     * @return The net score for the player.
     */
    public int getScore(Player p) {
        if (p == p1) {
            return score.get(p1) - score.get(p2);
        } else {
            return score.get(p2) - score.get(p1);
        }
    }
   
    /**
     * Compute the hand size of the player's opponent.
     * @param p the player
     * @return the opponent's hand size.
     */
    public int opponentsHandSize(Player p) {
        if (p == p1) {
            return hands.get(p2).size();
        } else {
            return hands.get(p1).size();
        }
    }

    private void dealHands() {
        ArrayList<Card> h = new ArrayList<Card>();
        for(int i = 0; i < 4; i++) h.add(deck.dealCard());
        hands.put(p1, h);
        h = new ArrayList<Card>();
        for(int i = 0; i < 4; i++) h.add(deck.dealCard());
        hands.put(p2, h);
    }

    private void seedBoard() {
        board.add(new PlayedCard(deck.dealCard(), null, 0, 0));
    }

    void play() {
        dealHands();
        seedBoard();
        int i = 0;

        while (i++ < 5) {//deck.hasCard()) {
            handleMove(p1);
            handleMove(p2);
        }
    }

    private void handleMove(Player p) {
        ArrayList<PlayedCard> move = p.makeMove();
        int moveScore = Utilities.scoreForMove(move, board);

        if (moveScore > -1) {
            hands.get(p).removeAll(move.stream().map((c) -> c.card).collect(Collectors.toList()));
            board.addAll(move);
            score.put(p, score.get(p) + moveScore);
            System.out.println(p.getName() +  "'s move: " + move);
            System.out.println(p.getName() +  "'s score for that move: " + moveScore);
        } else if (move.isEmpty()) {
            deck.addCards(hands.get(p));
            hands.get(p).clear();
        }
        
        System.out.println(p.getName() +  "'s total score: " + score.get(p));
        // Replenish cards.
        while (hands.get(p).size() < 4 && deck.hasCard()) {
            hands.get(p).add(deck.dealCard());
        }
    }
    
    
}
