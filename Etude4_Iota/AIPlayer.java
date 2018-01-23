package iota;

import java.util.*;
import java.util.stream.*;

import iota.Card;
import iota.PlayedCard;

import java.awt.Point;

/**
 * An AI Iota player.
 * 
 * @author Anthony Dickson
 */
public class AIPlayer extends Player {    
    static final Point UP = new Point(0, 1);
    static final Point DOWN = new Point(0, -1);
    static final Point LEFT = new Point(-1, 0);
    static final Point RIGHT = new Point(1, 0);
    static final Point[] DIRECTIONS = { UP, DOWN, LEFT, RIGHT };
    
    static Random rand = new Random();
    
    Info info;
    ArrayList<Card> hand = new ArrayList<>();
    ArrayList<PlayedCard> board = new ArrayList<>();
    ArrayList<PlayedCard> move = new ArrayList<>();
    HashMap<ArrayList<PlayedCard>, Integer> moves = new HashMap<>();
    
    public AIPlayer(Manager m) {
        super(m);

        info = new Info(this, m);
    }
    
    @Override
    public ArrayList<PlayedCard> makeMove() {
        ArrayList<Card> hand = m.getHand(this);
        ArrayList<PlayedCard> board = m.getBoard();

        info.update(hand, board);

        getMoves(hand, board);

        if (info.other != null) {
            HashMap<ArrayList<PlayedCard>, Integer> movesCopy = new HashMap(moves);

            for (Map.Entry<ArrayList<PlayedCard>, Integer>  mv : movesCopy.entrySet()) {
                board.addAll(mv.getKey());
                
                getMoves(m.getHand(info.other), board);
                ArrayList<PlayedCard> otherBestMove = getMaxScoreMove(moves);
                int otherBestScore = Utilities.scoreForMove(otherBestMove, board);
                movesCopy.put(mv.getKey(), mv.getValue() - otherBestScore);
                
                board.removeAll(mv.getKey());
            }

            moves = movesCopy;
            
        }
        
        move = getMaxScoreMove(moves);
        
        return move;
    }    

    /** Get the move that gives the max score. */
    private ArrayList<PlayedCard> getMaxScoreMove(HashMap<ArrayList<PlayedCard>, Integer> moves) {
        ArrayList<PlayedCard> maxScoreMove = new ArrayList<>();
        int maxScore = -1;

        
        // Find the move with the best score.
        for (Map.Entry<ArrayList<PlayedCard>, Integer>  mv : moves.entrySet()) {            
            if (mv.getValue() > maxScore) {
                maxScore = mv.getValue();
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

    
    @Override
    public ArrayList<Card> discard() {
        return m.getHand(this);
    }
    
    @Override
    public String getName() {
        return "AI Player";
    }

    public boolean formsLot(ArrayList<Card> cards) {
        ArrayList<PlayedCard> playedCards = new ArrayList<>();

        for (Card c : cards) {
            playedCards.add(new PlayedCard(c, this, 0, 0));
        }
        
        return cards.size() == 4 && Utilities.formSet(playedCards);
    }

    /** All the info this player knows. */
    private class Info {
        Manager m;
        Player player, other;
        Set<Card> knownCards;
        Set<Card> unknownCards;
        Map<Card, Double> cardP; // Probability of a card being played.

        public Info(Player player, Manager m) {
            this.player = player;
            this.m = m;

            knownCards = new HashSet<>();
            unknownCards = new HashSet<>();
            cardP = new HashMap<>();
            Deck d = new Deck();
            
            while (d.hasCard()) {
                Card c = d.dealCard();
                unknownCards.add(c);
            }
    
            double p = 1.0 / unknownCards.size();  
    
            for (Card c : unknownCards) {
                cardP.put(c, p);
            }
        }

        /**
         * Update our information.
         * 
         * @param hand The player's hand.
         * @param board The current board.
         */
        void update(List<Card> hand, List<PlayedCard> board) {            
            findOther(board);
            updateKnownCards(hand);
            updateKnownCards(toCardList(board));

            if (other != null) {
                updateKnownCards(m.getHand(other));
            }

            updateCardProbabilities();        
            // printInfo();
        }

        private void findOther(List<PlayedCard> board) {
            if (other != null) return;

            for (PlayedCard c : board) {
                if (c.p != null && c.p != player) {
                    other = c.p;
                }
            }
        }

        /**
         * Update our list of known and unknown cards.
         * 
         * @param cards The cards we now know of.
         */
        private void updateKnownCards(List<Card> cards) {
            knownCards.addAll(cards);
            unknownCards.removeAll(cards);
        }
        
        /** Update the probability of being played for each card. */
        private void updateCardProbabilities() {
            for (Card c : knownCards) {
                cardP.put(c, 0.0);
            }
            
            for (Card c : unknownCards) {
                cardP.put(c, 1.0 / unknownCards.size());
            }
        }
        
        /**
         * Calculates the probaility of a given card being played in the future.
         * 
         * @param c The card to check.
         */
        double pCard(Card c) {
            return cardP.get(c);
        }
        
        /**
         * Calculates the probaility of any one of the given cards being played in the next move.
         * 
         * @param start The card to check.
         */
        double pOneOf(List<Card> cards) {
            double p = 0.0;
            
            for (Card c : cards) {
                p += pCard(c);
            }
            
            return p;
        }
        
        /**
         * Calculates the probaility of all of the given cards being played in the next move.
         * 
         * @param start The card to check.
         */
        double pAllOf(List<Card> cards) {
            if (cards.size() == 0) return 0.0;

            double p = 1.0;

            for (Card c : cards) {
                p *= pCard(c);
            }

            return p;
        }

        /** Print out all known info. at this point in time. */
        @Override
        public String toString() {
            return ((other != null) ? "\nOther hand: " + m.getHand(other) : "") + 
                   "\nKnown Cards: " + knownCards + 
                   "\nUnknown Cards: " + unknownCards + 
                   "\nCard Probabilities: " + cardP;
        }

        void printInfo() {
            System.out.println(this);
        }

        private List<Card> toCardList(List<PlayedCard> cards) {
            return cards.stream().map((c) -> c.card).collect(Collectors.toList());
        }
    }
    
}
