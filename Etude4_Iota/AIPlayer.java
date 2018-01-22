package iota;

import java.util.*;
import java.util.stream.Collectors;

/**
 * An AI Iota player.
 * 
 * @author Anthony Dickson
 */
public class AIPlayer extends Player {
    Info info;
    
    public AIPlayer(Manager m) {
        super(m);

        info = new Info(this, m);
    }
    
    @Override
    public ArrayList<PlayedCard> makeMove() {
        info.update(m.getHand(this),  m.getBoard());
         
        return null;
    }    
    
    @Override
    public ArrayList<Card> discard() {
        return null;
    }
    
    @Override
    public String getName() {
        return "AI Player";
    }

    /** All the info this player knows. */
    private class Info {
        Manager m;
        Player player;
        List<Card> hand, other;
        List<PlayedCard> board;
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
            this.hand = hand;
            this.board = board;
            
            findOther();
            updateKnownCards(hand);
            updateKnownCards(toCardList(board));
            updateCardProbabilities();        
            printInfo();
        }

        private void findOther() {
            for (PlayedCard c : board) {
                if (c.p != null && c.p != player) {
                    System.out.println("Other hand: " + m.getHand(c.p));
                    updateKnownCards(m.getHand(c.p));
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
            return "Board: " + board + 
                   "\nHand: " + hand + 
                   "\nKnown Cards: " + knownCards + 
                   "\nUnknown Cards: " + unknownCards + 
                   "\nCard Probabilities: " + cardP;
        }

        void printInfo() {
            System.out.println(this);
            Card c = new Card(Colour.BLUE, Shape.CIRCLE, 1);
            System.out.println("Chance of " + c + " being played: " + pCard(c));

            Card c1 = new Card(Colour.RED, Shape.TRIANGLE, 3);
            Card c2 = new Card(Colour.GREEN, Shape.SQUARE, 4);
            List<Card> cards = new ArrayList<>();
            cards.add(c);
            cards.add(c1);
            cards.add(c2);
            System.out.println("Chance of any one of " + cards + " being played: " + pOneOf(cards));
            System.out.println("Chance of all of " + cards + " being played: " + pAllOf(cards));
        }

        private List<Card> toCardList(List<PlayedCard> cards) {
            return cards.stream().map((c) -> c.card).collect(Collectors.toList());
        }
    }
    
}
