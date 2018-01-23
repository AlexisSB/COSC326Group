package iota;

import java.util.*;
import java.util.stream.Collectors;
import java.util.Collections;

import iota.Manager;
import iota.PlayedCard;
import iota.Utilities;

/**
 * An AI Iota player.
 * 
 * @author Anthony Dickson
 */
public class AIPlayer extends Player {
    static Random rand = new Random();

    Info info;
    ArrayList<Card> hand;
    ArrayList<PlayedCard> board;
    
    public AIPlayer(Manager m) {
        super(m);

        info = new Info(this, m);
    }
    
    @Override
    public ArrayList<PlayedCard> makeMove() {
        hand = m.getHand(this);
        board = m.getBoard();

        info.update(hand, board);
        System.out.println("\n" + getName() +  " is starting their move.");
        System.out.println("Board:");
        System.out.println(Utilities.boardToString(board));
        System.out.println("Hand: " + hand + "\n");
        
        ArrayList<PlayedCard> move = getMaxScoreMove();        
        
        System.out.println("Chose move: " + move);
        return move;
    }    
    
    /** Get the move that gives the max score. */
    private ArrayList<PlayedCard> getMaxScoreMove() {
        ArrayList<PlayedCard> move = new ArrayList<>();
        ArrayList<ArrayList<PlayedCard>> moves = new ArrayList<>();
        ArrayList<PlayedCard> cards;

        for (PlayedCard c : board) {
            cards = getVBlock(c);
            System.out.println("Block of picked cards: " + cards);
            move = getMoveVBlock(cards);
            
            if (!move.isEmpty()) {
                moves.add(move);
            }
            
            cards = getHBlock(c);
            System.out.println("Block of picked cards: " + cards);
            
            move = getMoveHBlock(cards);
            
            if (!move.isEmpty()) {
                moves.add(move);
            }
        }

        int maxScore = -1;

        for (ArrayList<PlayedCard> mv : moves) {
            int score = Utilities.scoreForMove(mv, board);
            
            if (score > maxScore) {
                score = maxScore;
                move = mv;
            }
        }

        return move;
    }

    /** Get random played card from the board. */
    private PlayedCard randCard() {
        return board.get(rand.nextInt(board.size()));
    }
    
    /** Chooses a random vertical block on the board. */
    private ArrayList<PlayedCard> getVBlock(PlayedCard c) {
        return Utilities.verticalBlock(c, board);
    }
    
    /** Chooses a random horizontal block on the board. */
    private ArrayList<PlayedCard> getHBlock(PlayedCard c) {
        return Utilities.horizontalBlock(c, board);
    }

    /** Finds the player max move for a given vertical block of played cards. */
    private ArrayList<PlayedCard> getMoveVBlock(ArrayList<PlayedCard> cards) {
        ArrayList<PlayedCard> move = new ArrayList<>();

        Collections.sort(cards, (a, b) -> a.y - b.y);

        PlayedCard start, end;
        start = cards.get(0);
        end = cards.get(cards.size() - 1);

        for (Card c : hand) {
            move.add(new PlayedCard(c, this, start.x, start.y - 1));
            int score = Utilities.scoreForMove(move, board);

            if (score > -1) {
                System.out.println("Score for move " + move + " is " + score);
                break;
            } else {
                move.remove(move.size() - 1);
            }
            
            move.add(new PlayedCard(c, this, end.x, end.y + 1));
            score = Utilities.scoreForMove(move, board);

            if (score > -1) {
                System.out.println("Score for move " + move + " is " + score);
                break;
            } else {
                move.remove(move.size() - 1);
            }            
        }

        return move;
    }

    /** Finds the player max move for a given vertical block of played cards. */
    private ArrayList<PlayedCard> getMoveHBlock(ArrayList<PlayedCard> cards) {
        ArrayList<PlayedCard> move = new ArrayList<>();

        Collections.sort(cards, (a, b) -> a.x - b.x);

        PlayedCard start, end;
        start = cards.get(0);
        end = cards.get(cards.size() - 1);

        for (Card c : hand) {
            move.add(new PlayedCard(c, this, start.x - 1, start.y ));
            int score = Utilities.scoreForMove(move, board);

            if (score > -1) {
                System.out.println("Score for move " + move + " is " + score);
                break;
            } else {
                move.remove(move.size() - 1);
            }
            
            move.add(new PlayedCard(c, this, end.x + 1, end.y));
            score = Utilities.scoreForMove(move, board);

            if (score > -1) {
                System.out.println("Score for move " + move + " is " + score);
                break;
            } else {
                move.remove(move.size() - 1);
            } 
        }

        return move;
    }
    
    @Override
    public ArrayList<Card> discard() {
        return null;
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
                System.out.println("Other hand: " + m.getHand(other));
                updateKnownCards(m.getHand(other));
            }

            updateCardProbabilities();        
            printInfo();
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
         * @param c The card to check.
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
         * @param c The card to check.
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
