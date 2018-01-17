package iota;

import java.util.Arrays;
import java.util.Collections;

/**
 * A pre-shuffled deck of Iota cards which keeps track of the next card and allows
 * for it to be dealt.
 * 
 * @author Michael Albert
 */
public class Deck {
    
    private Card[] deck;
    private int index;
    
    public Deck() {
        this.deck = new Card[64];
        this.index = 0;
        int i = 0;
        for(Colour c : Colour.values()) {
            for(Shape s : Shape.values()) {
                for(int v = 1; v <= 4; v++) {
                    deck[i++] = new Card(c, s, v);
                }
            }
        }
        Collections.shuffle(Arrays.asList(deck));
    }
    
    public boolean hasCard() {
        return index < 64;
    }
    
    public Card dealCard() {
        return deck[index++];
    }

}
