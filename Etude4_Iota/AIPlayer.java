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
    ArrayList<ArrayList<PlayedCard>> moves = new ArrayList<>();
    
    public AIPlayer(Manager m) {
        super(m);

        info = new Info(this, m);
    }
    
    @Override
    public ArrayList<PlayedCard> makeMove() {
        hand = m.getHand(this);
        board = m.getBoard();

        info.update(hand, board);
        // System.out.println("\n" + getName() +  " is starting their move.");
        // System.out.println("Board:");
        // System.out.println(Utilities.boardToString(board));
        // System.out.println("Hand: " + hand + "\n");

        ArrayList<PlayedCard> move = new ArrayList<>();

        if (getMoves()) {
            move = getMaxScoreMove();
            System.out.println("Num available moves: " + moves.size());
            System.out.println("Random move: " + getRandMove());
            System.out.println("Min score move: " + getMinScoreMove());
            System.out.println("Max score move: " + getMaxScoreMove());
            System.out.println("Min card move: " + getMinCardsMove());
            System.out.println("Max card move: " + getMaxCardsMove());
        }        
        
        return move;
    }    

    /** Choose a random move. */
    private ArrayList<PlayedCard> getRandMove() {
        if (moves.size() == 0) return new ArrayList<PlayedCard>();

        return moves.get(rand.nextInt(moves.size() - 1));
    }

    /** Get the move that gives the min score. */
    private ArrayList<PlayedCard> getMinScoreMove() {
        ArrayList<PlayedCard> move = new ArrayList<>();
        int minScore = Integer.MAX_VALUE;
        
        // Find the move with the best score.
        for (ArrayList<PlayedCard> mv : moves) {
            int score = Utilities.scoreForMove(mv, board);
            
            if (score < minScore) {
                minScore = score;
                move = mv;
            }
        }

        return move;
    }

    /** Get the move that gives the max score. */
    private ArrayList<PlayedCard> getMaxScoreMove() {
        ArrayList<PlayedCard> move = new ArrayList<>();
        int maxScore = -1;
        
        // Find the move with the best score.
        for (ArrayList<PlayedCard> mv : moves) {
            int score = Utilities.scoreForMove(mv, board);
            
            if (score > maxScore) {
                maxScore = score;
                move = mv;
            }
        }

        return move;
    }

    /** Get the move that uses the most cards. */
    private ArrayList<PlayedCard> getMinCardsMove() {
        ArrayList<PlayedCard> move = new ArrayList<>();
        int min = Integer.MAX_VALUE;

        // Find the move with the best score.
        for (ArrayList<PlayedCard> mv : moves) {
            int nCards = mv.size();
            
            if (nCards < min) {
                min = nCards;
                move = mv;
            }
        }

        return move;
    }

    /** Get the move that uses the most cards. */
    private ArrayList<PlayedCard> getMaxCardsMove() {
        ArrayList<PlayedCard> move = new ArrayList<>();
        int max = -1;

        // Find the move with the best score.
        for (ArrayList<PlayedCard> mv : moves) {
            int nCards = mv.size();
            
            if (nCards > max) {
                max = nCards;
                move = mv;
            }
        }

        return move;
    }

    /**
     * Get all available moves on the board.
     * 
     * @return true if valid moves were found, false otherwise.
     */
    private boolean getMoves() { 
        boolean found = false;

        for (PlayedCard c : board) {
            found |= getMoves(c);
        }

        return found;
    }

    /**
     * Get all available moves from the PlayedCard start.
     * 
     * @param start The card to search from.
     * @return true if valid moves were found, false otherwise.
     */
    private boolean getMoves(PlayedCard start) { 
        moves = new ArrayList<>();
        boolean found = false;

        for (Point dir : DIRECTIONS) {
            found |= getMoves(start.x + dir.x, start.y + dir.y);
        }

        return found;
    }

    /**
     * Get all available moves from the given x,y coordinates.
     * 
     * @param x The x coordinate where the first card should be placed.
     * @param y The y coordinate where the first card should be placed.
     * @return true if valid moves were found, false otherwise.
     */
    private boolean getMoves(int x, int y) { 
        
        boolean found = false;
        
        for (Point dir : DIRECTIONS) {
            found |= getMoves(x, y, hand, dir, new ArrayList<>());
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
     * @param move The current list of moves.
     * @return true if valid moves were found, false otherwise.
     */
    private boolean getMoves(int x, int y, ArrayList<Card> cards, Point dir, ArrayList<PlayedCard> move) {
        if (cards.size() == 0) return false;

        boolean found = false;

        // Find card in our hand that can be put on the board at (x, y).
        for (int i = 0; i < cards.size(); i++) {
            move.add(new PlayedCard(cards.get(i), this, x, y));
            // System.out.println("Search state:\tx=" + x + "\ty=" + y + "\tdir=" + dir + "\tmove=" + move);
            
            if (Utilities.isLegalMove(move, board)) {
                found = true;
                
                ArrayList<PlayedCard> moveCopy = new ArrayList<>();
                
                for (PlayedCard pc : move) {
                    moveCopy.add(pc.copy());
                }
                
                // System.out.println("Adding move: " + moveCopy);
                moves.add(moveCopy);
                ArrayList<Card> remainingHand = new ArrayList<>(cards);
                remainingHand.remove(i);
                getMoves(x + dir.x, y + dir.y, remainingHand, dir, move);
            } 

            move.remove(move.size() - 1);
        }
        
        return found;
    }

    
    @Override
    public ArrayList<Card> discard() {
        return hand;
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
            // Card c = new Card(Colour.BLUE, Shape.CIRCLE, 1);
            // System.out.println("Chance of " + c + " being played: " + pCard(c));

            // Card c1 = new Card(Colour.RED, Shape.TRIANGLE, 3);
            // Card c2 = new Card(Colour.GREEN, Shape.SQUARE, 4);
            // List<Card> cards = new ArrayList<>();
            // cards.add(c);
            // cards.add(c1);
            // cards.add(c2);
            // System.out.println("Chance of any one of " + cards + " being played: " + pOneOf(cards));
            // System.out.println("Chance of all of " + cards + " being played: " + pAllOf(cards));
        }

        private List<Card> toCardList(List<PlayedCard> cards) {
            return cards.stream().map((c) -> c.card).collect(Collectors.toList());
        }
    }
    
}
