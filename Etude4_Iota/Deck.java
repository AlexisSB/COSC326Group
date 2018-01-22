package iota;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 * A pre-shuffled deck of Iota cards which keeps track of the next card and allows
 * for it to be dealt.
 * 
 * @author Michael Albert
 */
public class Deck {
    
    private ArrayDeque<Card> deck = new ArrayDeque<>();
    
    public Deck() {
        ArrayList<Card> d = new ArrayList<Card>();
        int i = 0;
        for(Colour c : Colour.values()) {
            for(Shape s : Shape.values()) {
                for(int v = 1; v <= 4; v++) {
                    d.add(new Card(c, s, v));
                }
            }
        }
        Collections.shuffle(d);
        this.deck.addAll(d);
    }
    
    public boolean hasCard() {
        return !deck.isEmpty();
    }
    
    public Card dealCard() {
        return deck.poll();
    }
    
    void addCards(Collection<Card> cards) {
        for(Card c : cards) {
            deck.addLast(c);
        }
    }
    
    public static void main(String[] args) {
        Deck d = new Deck();
        for(Card c : d.deck) System.out.println(c);
    }

}
