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
        Player p1 = new AIPlayer(m);
        Player p2 = new AIPlayer(m);
        m.setPlayers(p1, p2);
        m.play();
    }    
}
