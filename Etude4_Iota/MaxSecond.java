package iota;

import java.util.*;
import java.util.stream.*;

import iota.Card;
import iota.PlayedCard;
import java.awt.Point;

/**
 * An AI Iota player that plays the move with the highest score.
 * Looks at all possible moves and picks the one with the highest score.
 * COSC326 Etude 4 SS 2018
 * @author Anthony Dickson
 */
public class MaxSecond extends Player {    
    static final Point UP = new Point(0, 1);
    static final Point DOWN = new Point(0, -1);
    static final Point LEFT = new Point(-1, 0);
    static final Point RIGHT = new Point(1, 0);
    static final Point[] DIRECTIONS = { UP, DOWN, LEFT, RIGHT };
    
    static playerName = "Max the Second";
    static Random rand = new Random();

    
    
    ArrayList<Card> hand = new ArrayList<>();
    ArrayList<PlayedCard> board = new ArrayList<>();
    /* Move to give back to the manager */
    ArrayList<PlayedCard> move = new ArrayList<>();
    /* HashMap of all the possible moves and there scores*/
    HashMap<ArrayList<PlayedCard>, Integer> moves = new HashMap<>();

    /**
     * Constructor
     * @param m - the manager for the iota game.
     */
    public MaxSecond(Manager m) {
        super(m);
    }

    /**
     * Picks the next move for the player.
     * Looks at all possible moves and picks the one
     * that gives the highest immediate score.
     */
    @Override
    public ArrayList<PlayedCard> makeMove() {
        ArrayList<Card> hand = m.getHand(this);
        ArrayList<PlayedCard> board = m.getBoard();

        getMoves(hand, board);          
        move = getMaxScoreMove(moves);

        if (move.size()==0){
            System.err.println("MaxFirst Empty Move");
            System.err.println(move);
            System.err.println(hand);
            return new ArrayList<PlayedCard>();
        }
        
        return move;
    }    

    /**
     * Get the move that gives the max score.
     * Looks through all the HashMap of all moves
     * and returns the one with the highest value.
     * Values of 3 and 4 card hands are doubled to limit the number of possible lots
     * that the opponent can make off 2 and 1 card plays.
     * @return ArrayList of Played Cards that show the highest scoring move.
     */
    private ArrayList<PlayedCard> getMaxScoreMove(HashMap<ArrayList<PlayedCard>, Integer> moves) {
        ArrayList<PlayedCard> maxScoreMove = new ArrayList<>();
        int maxScore = -1;

        

        
        // Find the move with the best score.
        for (Map.Entry<ArrayList<PlayedCard>, Integer>  mv : moves.entrySet()) {
            int rating = mv.getValue();
            if(mv.getKey().size() > 2){
                rating *=2;
            }
            if (rating > maxScore) {
                maxScore = rating;
                maxScoreMove = mv.getKey();
            }
        }

        return maxScoreMove;
    }

    /**
     * Get all available moves on the board.
     * 
     * @return true if valid moves were found, false otherwise.
     */
    private boolean getMoves(ArrayList<Card> hand, ArrayList<PlayedCard> board) { 
        boolean found = false;
        HashSet<Point> coords = new HashSet<>();

        move.clear();
        moves.clear();

        for (PlayedCard c : board) {
            for (Point dir : DIRECTIONS) {
                coords.add(new Point(c.x + dir.x, c.y + dir.y));
            }
        }

        for (Point coord : coords) {
            for (Point dir : DIRECTIONS) {
                found |= getMoves(coord.x, coord.y, hand, dir, board);
            }
        }

        return found;
    }
    
    /**
     * Get all available moves from given x,y coordinates in a given direction.
     * 
     * @param x The x coordinate where the card should be placed.
     * @param y The y coordinate where the card should be placed.
     * @param cards The cards to try play.
     * @param dir The direction we should try play in.
     * @return true if valid moves were found, false otherwise.
     */
    private boolean getMoves(int x, int y, ArrayList<Card> cards, Point dir, ArrayList<PlayedCard> board) {
        if (cards.size() == 0) return false;

        boolean found = false;

        for (int i = 0; i < cards.size(); i++) {
            move.add(new PlayedCard(cards.get(i), this, x, y));
            int score = Utilities.scoreForMove(move, board);
            
            if (score > -1) {
                found = true;
    
                ArrayList<PlayedCard> moveCopy = new ArrayList<>();
                for (PlayedCard c : move) {
                    moveCopy.add(c.copy());
                }
                moves.put(moveCopy, score);
    
                ArrayList<Card> cardsCopy = new ArrayList<>(cards);
                cardsCopy.remove(cards.get(i));

                getMoves(x + dir.x, y + dir.y, cardsCopy, dir, board);
            } 

            move.remove(move.size() - 1);
        }
        
        return found;
    }

    /**
     * Discard behaviour for the player.
     * Simply discards the entire hand when called.
     * @return Arraylist of cards to discard.
     */
    @Override
    public ArrayList<Card> discard() {
        return m.getHand(this);
    }

    /**
     * Accessor for the player name.
     * @return the player name.
     */
    @Override
    public String getName() {
        return playerName;
    }

    public boolean formsLot(ArrayList<Card> cards) {
        ArrayList<PlayedCard> playedCards = new ArrayList<>();

        for (Card c : cards) {
            playedCards.add(new PlayedCard(c, this, 0, 0));
        }
        
        return cards.size() == 4 && Utilities.formSet(playedCards);
    }
    
}
