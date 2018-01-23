package iota;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * Utility methods available to Iota players and managers
 *
 * @author Michael Albert
 */
public class Utilities {

    public static final int ILLEGAL = -1;

    /**
     * Checks if the cards supplied form a proper line. Note there is no
     * checking against a board.
     *
     * @param cards the cards
     * @return true if the cards form a line
     */
    static boolean properLine(ArrayList<PlayedCard> cards) {
        if (cards.size() <= 2) {
            return true;
        }
        if (cards.size() > 4) {
            return false;
        }
        return inLine(cards) && formSet(cards);
    }

    /**
     * Checks if the cards supplied are in a line (note no checking against a
     * board)
     *
     * @param cards the cards
     * @return true if the cards are in a horizontal or vertical line.
     */
    static boolean inLine(ArrayList<PlayedCard> cards) {
        int xMin = Integer.MAX_VALUE;
        int xMax = Integer.MIN_VALUE;
        int yMin = Integer.MAX_VALUE;
        int yMax = Integer.MIN_VALUE;
        HashSet<Integer> xValues = new HashSet<>();
        HashSet<Integer> yValues = new HashSet<>();
        for (PlayedCard c : cards) {
            xMin = Math.min(c.x, xMin);
            yMin = Math.min(c.y, yMin);
            xMax = Math.max(c.x, xMax);
            yMax = Math.max(c.y, yMax);
            xValues.add(c.x);
            yValues.add(c.y);
        }
        if (xValues.size() == 1) {
            return yValues.size() == cards.size() && yMax - yMin == cards.size() - 1;
        }
        if (yValues.size() == 1) {
            return xValues.size() == cards.size() && xMax - xMin == cards.size() - 1;
        }
        return false;
    }

    /**
     * Checks if the cards form a set (each attribute the same or different for
     * each card. Note verification of distinct cards for a legal move is
     * handled elsewhere, so this returns 'true' if all the cards are the same.
     *
     * @param cards the cards
     * @return true if the cards form a set
     */
    static boolean formSet(ArrayList<PlayedCard> cards) {
        HashSet<Shape> shapes = new HashSet<>();
        HashSet<Colour> colours = new HashSet<>();
        HashSet<Integer> values = new HashSet<>();
        for (PlayedCard pc : cards) {
            colours.add(pc.card.colour);
            shapes.add(pc.card.shape);
            values.add(pc.card.value);
        }
        int s = cards.size();
        return (colours.size() == 1 || colours.size() == s)
                && (shapes.size() == 1 || shapes.size() == s)
                && (values.size() == 1 || values.size() == s);

    }

    static int scoreForMove(ArrayList<PlayedCard> cards, ArrayList<PlayedCard> board) {
        if (!isLegalMove(cards, board)) {
            return ILLEGAL;
        }
        return score(cards, board);
    }

    static boolean inRow(ArrayList<PlayedCard> cards) {
        HashSet<Integer> yv = new HashSet<>();
        for (PlayedCard c : cards) {
            yv.add(c.y);
        }
        return yv.size() == 1;
    }

    static boolean inCol(ArrayList<PlayedCard> cards) {
        HashSet<Integer> xv = new HashSet<>();
        for (PlayedCard c : cards) {
            xv.add(c.x);
        }
        return xv.size() == 1;
    }

    /**
     * Compute the score for the play cards on the board. Note this is private
     * as it is supposed to be called only after the play has been checked for
     * legality.
     *
     * @param cards The cards played
     * @param oldBoard The board
     * @return The score of the play
     */
    private static int score(ArrayList<PlayedCard> cards, ArrayList<PlayedCard> oldBoard) {

        int sum = 0;
        int multiplier = 1;
        if (cards.size() == 4) {
            multiplier *= 2;
        }

        ArrayList<PlayedCard> board = (ArrayList<PlayedCard>) oldBoard.clone();
        board.addAll(cards);

        if (inRow(cards)) {
            ArrayList<PlayedCard> mainLine = horizontalBlock(cards.get(0), board);
            if (mainLine.size() == 4) {
                multiplier *= 2;
            }
            if (mainLine.size() > 1) {
                for (PlayedCard c : mainLine) {
                    sum += c.card.value;
                }
            }
            for (PlayedCard p : cards) {
                ArrayList<PlayedCard> vertLine = verticalBlock(p, board);
                if (vertLine.size() > 1) {
                    if (vertLine.size() == 4) {
                        multiplier *= 2;
                    }
                    for (PlayedCard c : vertLine) {
                        sum += c.card.value;
                    }
                }
            }
            return sum * multiplier;
        } else {
            ArrayList<PlayedCard> mainLine = verticalBlock(cards.get(0), board);
            if (mainLine.size() == 4) {
                multiplier *= 2;
            }
            if (mainLine.size() > 1) {
                for (PlayedCard c : mainLine) {
                    sum += c.card.value;
                }
            }
            for (PlayedCard p : cards) {
                ArrayList<PlayedCard> horiLine = horizontalBlock(p, board);
                if (horiLine.size() > 1) {
                    if (horiLine.size() == 4) {
                        multiplier *= 2;
                    }
                    for (PlayedCard c : horiLine) {
                        sum += c.card.value;
                    }
                }
            }
            return sum * multiplier;
        }
    }

    static boolean isLegalMove(ArrayList<PlayedCard> cards, ArrayList<PlayedCard> board) {

        // First check that there are no duplicated locations or cards between
        // the cards and the board, or among the cards.
        for (int i = 0; i < cards.size(); i++) {
            PlayedCard ci = cards.get(i);
            for (int j = i + 1; j < cards.size(); j++) {
                PlayedCard cj = cards.get(j);
                if (ci.x == cj.x && ci.y == cj.y || ci.card.equals(cj.card)) {
                    return false;
                }
            }
        }

        for (PlayedCard c : cards) {
            for (PlayedCard b : board) {
                if (c.x == b.x && c.y == b.y || c.card.equals(b.card)) {
                    return false;
                }
            }
        }

        // Add the cards to the board
        ArrayList<PlayedCard> newBoard = (ArrayList<PlayedCard>) board.clone();
        newBoard.addAll(cards);

        if (inRow(cards)) {
            ArrayList<PlayedCard> block = horizontalBlock(cards.get(0), newBoard);

            if (!block.containsAll(cards)) {
                return false;
            }
            if (!properLine(block)) {
                return false;
            }
            return checkCols(cards, newBoard);
        }

        if (inCol(cards)) {
            ArrayList<PlayedCard> block = verticalBlock(cards.get(0), newBoard);

            if (!block.containsAll(cards)) {
                return false;
            }
            if (!properLine(block)) {
                return false;
            }
            return checkRows(cards, newBoard);
        }
        return false;
    }

    static PlayedCard cardAt(ArrayList<PlayedCard> board, int x, int y) {
        for (PlayedCard c : board) {
            if (c.x == x && c.y == y) {
                return c;
            }
        }
        return null;
    }

    static ArrayList<PlayedCard> horizontalBlock(PlayedCard card, ArrayList<PlayedCard> board) {
        ArrayList<PlayedCard> block = new ArrayList<>();
        block.add(card);
        int x = card.x + 1;
        int y = card.y;
        do {
            PlayedCard c = cardAt(board, x, y);
            if (c != null) {// && Math.abs(c.x - block.get(block.size() - 1).x) == 1) {
                block.add(c);
            } else {
                break;
            }
            x++;
        } while (true);
        x = card.x - 1;
        do {
            PlayedCard c = cardAt(board, x, y);
            if (c != null) {// && Math.abs(c.x - block.get(block.size() - 1).x) == 1) {
                block.add(c);
            } else {
                break;
            }
            x--;
        } while (true);
        return block;
    }

    static ArrayList<PlayedCard> verticalBlock(PlayedCard card, ArrayList<PlayedCard> board) {
        ArrayList<PlayedCard> block = new ArrayList<>();
        block.add(card);
        int x = card.x;
        int y = card.y + 1;
        do {
            PlayedCard c = cardAt(board, x, y);
            if (c != null) {// && Math.abs(c.y - block.get(block.size() - 1).y) == 1) {
                block.add(c);
            } else {
                break;
            }
            y++;
        } while (true);
        y = card.y - 1;
        do {
            PlayedCard c = cardAt(board, x, y);
            if (c != null) {// && Math.abs(c.y - block.get(block.size() - 1).y) == 1) {
                block.add(c);
            } else {
                break;
            }
            y--;
        } while (true);
        return block;
    }

    // Checks that each row block of a card from cards is a proper line.
    static boolean checkRows(ArrayList<PlayedCard> cards, ArrayList<PlayedCard> board) {
        for (PlayedCard c : cards) {
            if (!properLine(horizontalBlock(c, board))) {
                return false;
            }
        }
        return true;
    }

    // Checks that each column block of a card from cards is a proper line.
    static boolean checkCols(ArrayList<PlayedCard> cards, ArrayList<PlayedCard> board) {
        for (PlayedCard c : cards) {
            if (!properLine(verticalBlock(c, board))) {
                return false;
            }
        }
        return true;
    }

    /**
     * Produce a string representation suitable for printing of a board.
     *
     * @param board the board
     * @return a String representing the board
     */
    static String boardToString(ArrayList<PlayedCard> board) {
        int xMin = Integer.MAX_VALUE;
        int yMin = Integer.MAX_VALUE;
        int xMax = Integer.MIN_VALUE;
        int yMax = Integer.MIN_VALUE;
        for (PlayedCard c : board) {
            xMin = (xMin < c.x) ? xMin : c.x;
            xMax = (xMax > c.x) ? xMax : c.x;
            yMin = (yMin < c.y) ? yMin : c.y;
            yMax = (yMax > c.y) ? yMax : c.y;
        }
        int cols = xMax - xMin + 1;
        int rows = yMax - yMin + 1;
        Card[][] cards = new Card[cols][rows];
        for (PlayedCard c : board) {
            cards[c.x - xMin][c.y - yMin] = c.card;
        }
        StringBuffer result = new StringBuffer();
        for (int r = rows - 1; r >= 0; r--) {
            for (int c = 0; c < cols; c++) {
                if (cards[c][r] == null) {
                    result.append("     ");
                } else {
                    result.append(cards[c][r]);
                    result.append(" ");
                }
            }
            result.append('\n');
        }
        return result.toString();
    }

    public static void main(String[] args) {

        ArrayList<PlayedCard> b = new ArrayList<>();
        b.add(new PlayedCard(new Card(Colour.BLUE, Shape.CROSS, 1), null, 0, 0));
        b.add(new PlayedCard(new Card(Colour.GREEN, Shape.CIRCLE, 2), null, 1, 0));
        b.add(new PlayedCard(new Card(Colour.BLUE, Shape.TRIANGLE, 1), null, 1, -1));
        b.add(new PlayedCard(new Card(Colour.YELLOW, Shape.SQUARE, 4), null, 1, -2));
        System.out.println(boardToString(b));

        ArrayList<PlayedCard> p = new ArrayList<>();
        p.add(new PlayedCard(new Card(Colour.GREEN, Shape.CROSS, 2), null, 0, 1));
        p.add(new PlayedCard(new Card(Colour.RED, Shape.CROSS, 3), null, 1, 1));
        p.add(new PlayedCard(new Card(Colour.YELLOW, Shape.CROSS, 1), null, 2, 1));
        p.add(new PlayedCard(new Card(Colour.BLUE, Shape.CROSS, 4), null, 3, 1));

        System.out.println(isLegalMove(p, b));
        System.out.println(scoreForMove(p, b));
        
        b.addAll(p);
        System.out.println(boardToString(b));

    }
}
