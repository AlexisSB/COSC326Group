package iota;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Manager class for an Iota game
 *
 * @author Michael Albert
 */
public class Manager {

    private ArrayList<PlayedCard> board = new ArrayList<>();
    int playerNumber = 0;
    private HashMap<Player, ArrayList<Card>> hands = new HashMap<>();
    private HashMap<Player, Integer> score = new HashMap<>();
    private Deck deck;
    int passCounter = 0;
    int drawCounter = 0;
    boolean gameOver = false;
    boolean gameStarted = false;
    private ArrayList<Player> players = new ArrayList<>();

    public Manager() {
    }

    void addPlayer(Player player) {
        System.out.println(player.getName());
        this.players.add(player);
    }

    void addPlayers(Player... players) {
        for (Player player :
                players) {
            addPlayer(player);
        }
    }

    /**
     * Get (a copy of) the current state of the board.
     *
     * @return A copy of the board. Cards in the array list will be in the
     * order they were played.
     */
    public ArrayList<PlayedCard> getBoard() {
        ArrayList<PlayedCard> result = new ArrayList<>();
        for (PlayedCard c : board) {
            result.add(c.copy());
        }
        return result;
    }

    /**
     * Return the hand of the given player.
     *
     * @param p The player.
     * @return The hand of the given player.
     */
    public ArrayList<Card> getHand(Player p) {
        return hands.get(p);
    }

    /**
     * Compute the score of the given player.
     *
     * @param p the player.
     * @return The net score for the player.
     */

    public ArrayList<Integer> netScores(Player p) {
        ArrayList<Integer> scores = new ArrayList<>();
        for (Player opponent :
                players) {
            if (!opponent.equals(p))
                scores.add(score.get(p) - score.get(opponent));
        }
        return scores;
    }

    public int getRawScore(Player p) {
        return score.get(p);
    }

    /**
     * Compute the hand size of the player's opponent.
     *
     * @param p the player
     * @return the opponent's hand size.
     */
    public ArrayList<Integer> opponentsHandSize(Player p) {
        ArrayList<Integer> handSizes = new ArrayList<>();
        for (Player opponent :
                players) {
            if (!opponent.equals(p))
                handSizes.add(hands.get(opponent).size());
        }
        return handSizes;
    }

    private void dealHands() {
        for (Player player :
                players) {
            ArrayList<Card> h = new ArrayList<>();
            for (int i = 0; i < 4; i++) h.add(deck.dealCard());
            hands.put(player, h);
        }
    }

    private void seedBoard() {
        board.add(new PlayedCard(deck.dealCard(), null, 0, 0));
    }

    void resetScores() {
        for (Player player :
                players) {
            score.put(player, 0);
        }
    }

    void setup() {
        deck = new Deck();
        board = new ArrayList<>();
        resetScores();
        dealHands();
        seedBoard();
        gameOver = false;
    }

    void printSummary() {
        System.out.println("Final Score");
        for (Player player :
                players) {
            System.out.println((player.getName() + "          ").substring(0, 10) + " " + score.get(player));
        }
    }

    void play() {
        if (!gameStarted) {
            setup();
        }
        while (!gameOver) {
            step();
        }
    }

    void deal_to_player(Player player) {
        ArrayList<Card> smallhand = hands.get(player);
        int sizediff = 4 - smallhand.size();
        for (int i = 0; i < sizediff; i++) {
            if (deck.hasCard())
                smallhand.add(deck.dealCard());
        }
    }

    void discardStep(Player player, ArrayList<Card> discardPile) {
        deck.addCards(discardPile);
        hands.get(player).removeAll(discardPile);
    }

    boolean playerStep(Player player) {
        ArrayList<PlayedCard> proposedMove = player.makeMove();
        if (proposedMove.isEmpty()) {
            // move empty, make player discard.
            drawCounter++;
            ArrayList<Card> discardPile = player.discard();
            HashSet<Card> d = new HashSet<>();
            d.addAll(discardPile);
            if (d.size() == discardPile.size() && hands.get(player).containsAll(d)) {
                discardStep(player, discardPile);
                deal_to_player(player);
            } else {
                passCounter++;
                System.err.println("Some cards are not in " + player.getName() + "'s hand. Failed to Discard");
            }
        } else {
            if (Utilities.isLegalMove(proposedMove, board)) {
                int moveScore = Utilities.scoreForMove(proposedMove, board);
                List cards = proposedMove.stream().map(i -> i.card).collect(Collectors.toList());
                if (hands.get(player).containsAll(cards)) {
                    int multiplier = 1;
                    hands.get(player).removeAll(cards);
                    if (!deck.hasCard() && hands.get(player).isEmpty()) {
                        multiplier = 2;
                        gameOver = true;
                    }
                    score.replace(player, score.get(player) + multiplier * moveScore);
                    deal_to_player(player);
                    for (PlayedCard pc : proposedMove)
                        board.add(new PlayedCard(pc.card, player, pc.x, pc.y));
                    passCounter = 0;
                    drawCounter = 0;

                } else {
                    passCounter++;
                    System.err.println("Some cards are not in " + player.getName() + "'s hand. Failed to Discard");
                    System.err.flush();
                }

            } else {
                passCounter++;
                drawCounter++;
                System.err.println(player.getName() + " tried to play an illegal move.");
                System.err.flush();
            }
            if (drawCounter >= 4 || passCounter >= 2) {
                System.err.println("Game over");
                System.err.flush();
                gameOver = true;
            }

        }
        return gameOver;
    }

    void step() {
        if (!gameOver) {
            if (!gameStarted)
                gameStarted = true;
            Player current = players.get(playerNumber);
            playerStep(current);
            playerNumber = (playerNumber + 1) % players.size();
        }
        if (gameOver) {
            printSummary();
        }
    }


}
