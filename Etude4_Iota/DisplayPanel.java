package iota;

import javax.swing.*;
import java.awt.*;

class DisplayPanel extends JPanel {

    Manager manager;

    int height;
    int width;

    public DisplayPanel(Manager m, int width, int height) {
        this.manager = m;
        setBorder(BorderFactory.createLineBorder(Color.BLACK));
        setBackground(Color.WHITE);
        this.width = width;
        this.height = height;
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(width, height);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        for (PlayedCard card :
                manager.getBoard()) {
            card.drawCard(g, 34);
        }

        if (manager.gameOver) {
            g.setColor(Color.BLACK);
            g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 48));
            g.drawString("GAME OVER!!", this.width / 2, this.height / 2);
        }
    }
}
