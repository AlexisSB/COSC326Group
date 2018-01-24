package iota;

import java.util.*;
import java.util.stream.*;
import java.awt.Point;


/**
 * A Basic Player for Testing in the Iota Game.
 * The PlayerBasic strategy is to play a single card in the first
 * valid spot that it finds.
 * Not a good strategy overall but useful for testing.
 * COSC 326 Etude 4 SS 2018
 * @author Alexis Barltrop
 */
public class PlayerBasic extends Player{

    /* Hold the name of the player to return in toString */
    private String playerName = "Basic Bob Player";
    /* Holds the current cards in hand*/
    private ArrayList<Card> hand;
    /* Hold the coordinates occupied by cards on the board */
    HashSet<MyPoint> occupiedCoordinates = new HashSet<MyPoint>();
    /* Holds coordinates at which a play may be made, coordinates
     * that are adjacent to cards already on the board*/
    HashSet<MyPoint> validCoordinates = new HashSet<MyPoint>();

    /**
     * Constructor
     * @param m - the manager for the iota game
     */
    public PlayerBasic(Manager m){
        super(m);
    }

    /**
     * Updates the HashMap of valid positions to play a card.
     * Sets all the coordinates of cards on the board to being occupied.
     * Checks all the coordinates adjacent to cards on the board.
     * If there is no card at that adjacent coordinate then flag it as
     * a possible valid position to play.
     */
    public void checkValidCoordinates(){
        ArrayList<PlayedCard> theBoard = m.getBoard();
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
    
    
    /**
     * Constructs HashSet of valid coordinates to play a card at.
     * Checks the cards in its hand to see if it can play them anywhere valid.
     * Returns the card to play to the manager class.
     * @return ArrayList<PlayedCard> the cards to play on the board.
     */
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
                PlayedCard firstCardToPlay =
                    new PlayedCard(cardToPlay,this,(int)p.getX(),(int)p.getY());
                ArrayList<PlayedCard> testPlay = new ArrayList<PlayedCard>();
                testPlay.add(firstCardToPlay);
                if(Utilities.isLegalMove(testPlay,theBoard)){
                    System.err.println("Cards to Play: " + testPlay);
                    System.err.println("Score : " + Utilities.scoreForMove(testPlay,theBoard));
                    return testPlay;
                }
            }
            
        }

        return new ArrayList<PlayedCard>();
    }

    /**
     * Method for chosing what cards to discard
     * This discards entire hand if neccesary
     * @return ArrayList<Card> of cards to discard.
     */
    public ArrayList<Card> discard(){
      
        //Discards Entire Hand
        return m.getHand(this);
    }

    /** Returns name of the player.
     *  @return the name of the player.
     */
    public String getName(){
        return playerName;
    }

    /**
     * Determines if four cards form a valid lot.
     * The four cards must form a set that has all the colours,
     * values and shapes either all the same or all different.
     * @return true if set is a lot, false otherwise.
     */
    public boolean formsLot(ArrayList<Card> cards) {
        ArrayList<PlayedCard> playedCards = new ArrayList<>();

        for (Card c : cards) {
            playedCards.add(new PlayedCard(c, this, 0, 0));
        }
        
        return cards.size() == 4 && Utilities.formSet(playedCards);
    }

    /**
     * MyPoint Class is private child of java Point class.
     * Made so can change the toString out put for Points
     */
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
