package iota;

import java.util.Objects;

/**
 * A played card in Iota (basically a card and a position)
 * @author Michael Albert
 */
public class PlayedCard {

    Card card;
    Player p;
    int x;
    int y;

    public PlayedCard(Card c, Player p, int x, int y) {
        this.card = c;
        this.p = p;
        this.x = x;
        this.y = y;
    }
    
    public PlayedCard copy() {
        return new PlayedCard(this.card, this.p, this.x, this.y);
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 47 * hash + Objects.hashCode(this.card);
        hash = 47 * hash + this.x;
        hash = 47 * hash + this.y;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final PlayedCard other = (PlayedCard) obj;
        if (!Objects.equals(this.card, other.card)) {
            return false;
        }
        if (this.x != other.x) {
            return false;
        }
        if (this.y != other.y) {
            return false;
        }
        return true;
    }
    
    public String toString() {
        return card + "(" + x + "," + y + ")";
    }
    
    
}
