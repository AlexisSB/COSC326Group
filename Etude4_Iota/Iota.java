package iota;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * Iota - The main application class that creates and runs a game of Iota.
 * 
 * @author Anthony Dickson.
 */
public class Iota {
    static boolean formSet(ArrayList<Card> cards) {
        HashSet<Shape> shapes = new HashSet<>();
        HashSet<Colour> colours = new HashSet<>();
        HashSet<Integer> values = new HashSet<>();

        for (Card c : cards) {
            colours.add(c.colour);
            shapes.add(c.shape);
            values.add(c.value);
        }

        int s = cards.size();
        return (colours.size() == 1 || colours.size() == s)
                && (shapes.size() == 1 || shapes.size() == s)
                && (values.size() == 1 || values.size() == s);

    }

    public static void main(String[] args) {
        cards.add(new Card(Colour.BLUE, Shape.CIRCLE, 1));
        cards.add(new Card(Colour.BLUE, Shape.TRIANGLE, 2));
        System.out.println(cards + " forms set? " + formSet(cards));
    }    
}