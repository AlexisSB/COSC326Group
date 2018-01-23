package iota;

import java.util.ArrayList;
import java.util.HashMap;

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
        this.p2 = p2;
        score.put(p1, new Integer(0));
        score.put(p2, new Integer(0));
    
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
        // More stuff

        //Code Added By Alexis
        int count = 0;
        final int TURN_LIMIT = 4;
        while(count <TURN_LIMIT){
            //How does it handle discard??
            // Needs to update hand of player
            
            ArrayList<PlayedCard> player1Move = p1.makeMove();
            int player1Score = Utilities.scoreForMove(player1Move,this.board);
            if (Utilities.isLegalMove(player1Move,this.board)){
                this.board.addAll(player1Move);
                int newScore = score.get(p1)+player1Score;
                score.put(p1,newScore);
            }else{
                System.err.println("Illegal Move");
            }
            ArrayList<PlayedCard> player2Move = p2.makeMove();
            int player2Score = Utilities.scoreForMove(player2Move,this.board);
            if (Utilities.isLegalMove(player2Move,this.board)){
                this.board.addAll(player2Move);
                int newScore = score.get(p2)+player2Score;
                score.put(p2,newScore);
            }else{
                System.err.println("Illegal Move");
            }
        

            //Print Scores
            System.err.println( "Player 1 Score : " + getScore(p1));
            System.err.println( "Player 2 Score : " + getScore(p2));
            
            count++;
        }
    }
    
    
}
