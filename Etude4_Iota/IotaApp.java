package iota;

import java.util.*;

/**
 * Iota - The main application class that creates and runs a game of Iota.
 * 
 * @author Anthony Dickson.
 */
public class IotaApp {
    public static void main(String[] args) {
        Manager m = new Manager();
        Player p1 = new PlayerOne(m);
        Player p2 = new PlayerOne(m);
        m.setPlayers(p1, p2);
        m.play();
        ArrayList<PlayedCard> nextPlayedCards = p1.makeMove();
        
    }    
}
