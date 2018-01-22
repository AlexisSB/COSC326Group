package iota;

import java.util.*;

public class PlayerOne implements Player{

    private String playerName = "PlayerOne";

    private ArrayList<Card> myCards = getHand(this);

    public ArrayList<PlayedCard> makeMove(){
        //Pick a card
        //Create a PlayedCard from a Card in our hand
        //See PlayedCard constructor
        //Pick the x,y coordinates for the card to go.
        // Q? How do we know which coordinates are valid, without trial and error.
        //Have to check each of the already played cards.
        //Do this for each card you want to play.
        //Pass the card to the manager.
        
                
    }

    public ArrayList<Card> discard(){
                
    }

    public String getName(){
        return playerName;
    }

    
}
