package iota;

import java.util.*;

public class PlayerOne extends Player{

    private String playerName = "PlayerOne";

    public PlayerOne(Manager m){
        super(m);
    }
    

    
    public ArrayList<PlayedCard> makeMove(){
        ArrayList<Card> myHand = m.getHand(this);
        ArrayList<PlayedCard> theBoard = m.getBoard();
        
        //Pick a card
        Card cardToPlay = myHand.get(0);
        
        
        //Pick the x,y coordinates for the card to go.
        // Q? How do we know which coordinates are valid, without trial and error.
        //Have to check each of the already played cards.
        //Create a PlayedCard from a Card in our hand
        PlayedCard firstCard = new PlayedCard(cardToPlay, this, 0,1);
        ArrayList<PlayedCard> cardsToPlay = new ArrayList<PlayedCard>();
        cardsToPlay.add(firstCard);
        //See PlayedCard constructor
        System.err.println("The Board: " + theBoard);
        System.err.println("Cards to Play: " + cardsToPlay);
        System.err.println("Score : " + Utilities.scoreForMove(cardsToPlay,theBoard));

        return cardsToPlay;
                           
                           
        
       
        //Do this for each card you want to play.
        //Pass the card to the manager.
        
                
    }

    public ArrayList<Card> discard(){
        ArrayList<Card> myHand = m.getHand(this);
        //Pick a card
        Card cardToDiscard = myHand.get(0);
        
        ArrayList<Card> cardsToDiscard = new ArrayList<Card>();
        cardsToDiscard.add(cardToDiscard);
        return cardsToDiscard;
                
    }

    public String getName(){
        return playerName;
    }

    
}
