package iota;

import java.util.*;
import java.util.stream.*;

public class PlayerOne extends Player{

    private String playerName = "PlayerOne";

    private ArrayList<Card> hand;

    public PlayerOne(Manager m) {
        super(m);
    }

    public ArrayList<PlayedCard> makeMove() {
        hand = m.getHand(this);
        //Pick a card
        //Create a PlayedCard from a Card in our hand
        //See PlayedCard constructor
        //Pick the x,y coordinates for the card to go.
        // Q? How do we know which coordinates are valid, without trial and error.
        //Have to check each of the already played cards.
        //Do this for each card you want to play.
        //Pass the card to the manager.
        
        return null;
    }

    public ArrayList<Card> discard(){
        return null;
    }

    public String getName(){
        return playerName;
    }

    public boolean formsLot(ArrayList<Card> cards) {
        ArrayList<PlayedCard> playedCards = new ArrayList<>();

        for (Card c : cards) {
            playedCards.add(new PlayedCard(c, this, 0, 0));
        }
        
        return cards.size() == 4 && Utilities.formSet(playedCards);
    }

    public static void main(String[] args) {
        Manager m = new Manager();
        PlayerOne p = new PlayerOne(m);
        m.setPlayers(p, p);
        m.play();
        ArrayList<Card> cards = new ArrayList<>();
        cards.add(new Card(Colour.BLUE, Shape.CIRCLE, 1));
        cards.add(new Card(Colour.BLUE, Shape.TRIANGLE, 2));
        cards.add(new Card(Colour.BLUE, Shape.SQUARE, 3));
        System.out.println(cards + " forms lot? " + p.formsLot(cards));        
        cards.add(new Card(Colour.RED, Shape.CROSS, 4));
        System.out.println(cards + " forms lot? " + p.formsLot(cards));
        cards.remove(cards.size() - 1);
        cards.add(new Card(Colour.BLUE, Shape.CROSS, 4));        
        System.out.println(cards + " forms lot? " + p.formsLot(cards));  
    } 

    
}
