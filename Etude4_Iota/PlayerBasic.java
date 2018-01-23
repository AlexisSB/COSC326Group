package iota;

import java.util.*;
import java.util.stream.*;
import java.awt.Point;

public class PlayerBasic extends Player{

    private String playerName = "PlayerOne";
    private ArrayList<Card> hand;
    HashSet<MyPoint> occupiedCoordinates = new HashSet<MyPoint>();
    HashSet<MyPoint> validCoordinates = new HashSet<MyPoint>();

    public PlayerBasic(Manager m){
        super(m);
    }


    public void checkValidCoordinates(){
        ArrayList<PlayedCard> theBoard = m.getBoard();
        //Pick the x,y coordinates for the card to go.
        // Q? How do we know which coordinates are valid, without trial and error.
        //Have to check each of the already played cards.
        for(PlayedCard p : theBoard){
            MyPoint cardCoordinates = new MyPoint(p.x,p.y);
            occupiedCoordinates.add(cardCoordinates);
        }

        //Creat hash of possible coordinates
        for(PlayedCard card : theBoard){
            MyPoint above = new MyPoint(card.x,card.y+1);
            MyPoint below = new MyPoint(card.x,card.y-1);
            MyPoint left = new MyPoint(card.x+1,card.y);
            MyPoint right = new MyPoint(card.x-1,card.y);
            validCoordinates.add(above);
            validCoordinates.add(below);
            validCoordinates.add(left);
            validCoordinates.add(right);

            for (MyPoint p : occupiedCoordinates){
                if (p.equals(above)) validCoordinates.remove(above);
                if (p.equals(below)) validCoordinates.remove(below);
                if (p.equals(left)) validCoordinates.remove(left);
                if (p.equals(right)) validCoordinates.remove(right);
            }
        }
    }
      
    public ArrayList<PlayedCard> makeMove() {
        ArrayList<PlayedCard> theBoard = m.getBoard();
        System.err.println("The Board: " + theBoard);
        hand = m.getHand(this);
        System.err.println("My Hand : " + hand);
        checkValidCoordinates();
        System.err.println("Valid Coordinates : " + validCoordinates);
        //Pick a card
        for(Card cardToPlay : hand){
            //Check card for all valid coordinates
            for (Point p : validCoordinates){
                PlayedCard firstCardToPlay = new PlayedCard(cardToPlay,this,(int)p.getX(),(int)p.getY());
                ArrayList<PlayedCard> testPlay = new ArrayList<PlayedCard>();
                testPlay.add(firstCardToPlay);
                //TODO is Legal move doesn't check if the cards are in a  set.Yet?
                if(Utilities.isLegalMove(testPlay,theBoard)){
                    System.err.println("Cards to Play: " + testPlay);
                    System.err.println("Score : " + Utilities.scoreForMove(testPlay,theBoard));
                    return testPlay;
                }
            }
            
        }

        return new ArrayList<PlayedCard>();
        //Create a PlayedCard from a Card in our hand
        //See PlayedCard constructor
        //Do this for each card you want to play.
        //Pass the card to the manager.
    }

    public ArrayList<Card> discard(){
      
        //Discards Entire Hand
        ArrayList<Card> cardsToDiscard = new ArrayList<Card>();
        for(int i = 0 ; i < hand.size();i++){
            cardsToDiscard.add(hand.get(i));
        }
            
        return cardsToDiscard;
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

    public class MyPoint extends Point{

        public MyPoint(){
            super();
        }

        public MyPoint(int x, int y){
            super(x,y);
        }

        public String toString(){
            String output = " [" + this.x+ ", " + this.y +"]";
            return output;

        }
    }

        

    
}
