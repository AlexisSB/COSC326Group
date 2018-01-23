package iota;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

class HandPanel extends JPanel {

    Manager manager;

    int height;
    int width;
    Player player;

    public HandPanel(Manager m, int width, int height, Player p) {
        this.manager = m;
        setBorder(BorderFactory.createLineBorder(Color.BLACK));
        setBackground(Color.WHITE);
        this.width = width;
        this.height = height;
        this.player = p;
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(width, height);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        ArrayList<Card> hand = manager.getHand(player);
        ArrayList<PlayedCard> visibleHand = new ArrayList<>();
        int i = 0;
        for (Card c :
                hand) {
            visibleHand.add(new PlayedCard(c, player, i, 0));
            i++;
        }
        for (PlayedCard pc :
                visibleHand) {
            pc.drawCard(g, 0);
        }
        g.setColor(Color.BLACK);
        g.drawString("Player: " + player.getName(), 10, 110);
        g.drawString("Score: " + manager.getRawScore(player), 10, 130);
    }
}
