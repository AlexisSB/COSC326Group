package iota;

import java.awt.*;
import java.util.Objects;

/**
 * A played card in Iota (basically a card and a position)
 *
 * @author Michael Albert
 */
public class PlayedCard {

    Card card;
    Player p;
    int x;
    int y;
    static final int SIZE = 90;
    private static final int BORDER_RADIUS = SIZE / 10;
    private static final int PIPSIZE = SIZE / 10;

    public PlayedCard(Card c, Player p, int x, int y) {
        this.card = c;
        this.p = p;
        this.x = x;
        this.y = y;
    }

    public PlayedCard copy() {
        return new PlayedCard(this.card, this.p, this.x, this.y);
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 47 * hash + Objects.hashCode(this.card);
        hash = 47 * hash + this.x;
        hash = 47 * hash + this.y;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final PlayedCard other = (PlayedCard) obj;
        if (!Objects.equals(this.card, other.card)) {
            return false;
        }
        if (this.x != other.x) {
            return false;
        }
        if (this.y != other.y) {
            return false;
        }
        return true;
    }

    public String toString() {
        return card + "(" + x + "," + y + ")";
    }

    private int getActual(int coord, int offset) {
        return (coord + offset) * SIZE;
    }


    public void drawCard(Graphics g, int offset) {
        int x = getActual(this.x, offset);
        int y = getActual(this.y, offset);
        drawBackground(g, x, y);
        drawColorShape(g, x, y);
        drawPips(g, x, y);
    }

    private void drawPips(Graphics g, int actualx, int actualy) {
        g.setColor(Color.WHITE);
        Point[] offsets = new Point[]{};
        switch (this.card.value) {
            case 1:
                offsets = new Point[]{new Point(SIZE / 2, SIZE / 2)};
                break;
            case 2:
                offsets = new Point[]{new Point(SIZE / 2 - SIZE / 8, SIZE / 2), new Point(SIZE / 2 + SIZE / 8, SIZE / 2)};
                break;
            case 3:
                offsets = new Point[]{new Point(SIZE / 2, SIZE / 2 - SIZE / 8),
                        new Point(SIZE / 2 + SIZE / 8, SIZE / 2 + SIZE / 15),
                        new Point(SIZE / 2 - SIZE / 8, SIZE / 2 + SIZE / 15)};
                break;
            case 4:
                offsets = new Point[]{new Point(SIZE / 2, SIZE / 2 - SIZE / 8),
                        new Point(SIZE / 2, SIZE / 2 + SIZE / 8),
                        new Point(SIZE / 2 - SIZE / 8, SIZE / 2),
                        new Point(SIZE / 2 + SIZE / 8, SIZE / 2)};
                break;
        }
        for (Point p : offsets
                ) {
            switch (this.card.shape) {

                case CIRCLE:
                    g.fillOval(actualx + p.x - PIPSIZE / 2, actualy + p.y - PIPSIZE / 2, PIPSIZE, PIPSIZE);
                    break;
                case CROSS:
                    g.fillRect(actualx + p.x - PIPSIZE / 6, actualy + p.y - PIPSIZE / 2, PIPSIZE / 3, PIPSIZE);
                    g.fillRect(actualx + p.x - PIPSIZE / 2, actualy + p.y - PIPSIZE / 6, PIPSIZE, PIPSIZE / 3);
                    break;
                case SQUARE:
                    g.fillRect(actualx + p.x - PIPSIZE / 2, actualy + p.y - PIPSIZE / 2, PIPSIZE, PIPSIZE);
                    break;
                case TRIANGLE:
                    g.fillPolygon(new int[]{actualx + p.x, actualx + p.x - PIPSIZE / 2, actualx + p.x + PIPSIZE / 2},
                            new int[]{actualy + p.y - PIPSIZE / 2 + PIPSIZE, actualy + p.y + PIPSIZE / 2 + PIPSIZE, actualy + p.y + PIPSIZE / 2 + PIPSIZE}, 3);
                    break;
            }

        }


    }

    private void drawColorShape(Graphics g, int actualx, int actualy) {
        g.setColor(this.card.colour.asColor);
        switch (this.card.shape) {
            case CIRCLE:
                g.fillOval(actualx + (SIZE / 6), actualy + (SIZE / 6), SIZE - (SIZE / 3), SIZE - (SIZE / 3));
                break;
            case CROSS:
                g.fillRect(actualx + (SIZE / 3), actualy + (SIZE / 6), SIZE / 3, SIZE - (SIZE / 3));
                g.fillRect(actualx + (SIZE / 6), actualy + (SIZE / 3), SIZE - (SIZE / 3), SIZE / 3);
                break;
            case SQUARE:
                g.fillRect(actualx + (SIZE / 6), actualy + (SIZE / 6), SIZE - (SIZE / 3), SIZE - (SIZE / 3));
                break;
            case TRIANGLE:
                g.fillPolygon(new int[]{actualx + SIZE / 2, actualx + SIZE / 6, actualx + SIZE - SIZE / 6},
                        new int[]{actualy + SIZE / 6, actualy + SIZE - SIZE / 6, actualy + SIZE - SIZE / 6}, 3);
                break;
        }
    }

    private void drawBackground(Graphics g, int actualx, int actualy) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(Color.WHITE);
        g2.fillRoundRect(actualx, actualy, SIZE, SIZE, BORDER_RADIUS, BORDER_RADIUS);
        g2.setColor(Color.BLACK);
        g2.setStroke(new BasicStroke(3));
        g2.drawRoundRect(actualx, actualy, SIZE, SIZE, BORDER_RADIUS, BORDER_RADIUS);
        g.fillRect(actualx + (SIZE / 10), actualy + (SIZE / 10), SIZE - (SIZE / 5), SIZE - SIZE / 5);
    }

}
