package iota;

/**
 * Iota - The main application class that creates and runs a game of Iota.
 * 
 * @author Anthony Dickson.
 */
public class Iota {
    public static void main(String[] args) {
        Manager m = new Manager();
        Player p1 = new TestPlayer(m);
        Player p2 = new TestPlayer(m);
        m.setPlayers(p1, p2);
        m.play();
    }    
}