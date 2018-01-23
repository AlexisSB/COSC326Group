package iota;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class IotaDisplay extends JPanel {

    JScrollPane sp;
    private DisplayPanel dp;

    public IotaDisplay() {

        /*
          Put the 5 lines below in a test class to use the play method of the Manager,
          add in your own Player implementations in the p1, p2 lines.
         */
        Manager m = new Manager();
        Player p1 = new AIPlayer(m);
        Player p2 = new AIPlayer(m);

        m.addPlayers(p1, p2);
        m.setup();
        /*
         * Below this is GUI stuff only
         */

        setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
        dp = new DisplayPanel(m, 68 * PlayedCard.SIZE, 68 * PlayedCard.SIZE);
        sp = new JScrollPane(dp);
        sp.setPreferredSize(new Dimension(19 * PlayedCard.SIZE, 15 * PlayedCard.SIZE));
        sp.setMinimumSize(new Dimension(19 * PlayedCard.SIZE, 15 * PlayedCard.SIZE));
        add(sp);
        add(Box.createHorizontalGlue());
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        sp.getViewport().setViewPosition(new Point(dp.width / 2 - 9 * PlayedCard.SIZE, dp.height / 2 - 7 * PlayedCard.SIZE));
        JPanel hands = new JPanel();
        add(hands);
        hands.setLayout(new BoxLayout(hands, BoxLayout.PAGE_AXIS));
        ArrayList<HandPanel> handPanels = new ArrayList<>();

        for (Player p : m.players) {
            JPanel jp = new JPanel();
            jp.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
            HandPanel hp = new HandPanel(m, 5 * PlayedCard.SIZE, 140, p);
            hp.setMaximumSize(new Dimension(5 * PlayedCard.SIZE, 150));
            jp.add(hp);
            handPanels.add(hp);
            hands.add(jp);
            hands.add(Box.createVerticalGlue());
        }
        JPanel bp = new JPanel();
        bp.setLayout(new BoxLayout(bp, BoxLayout.LINE_AXIS));
        JButton stepButton = new JButton("Step");
        bp.add(stepButton);
        JButton centerButton = new JButton("Center");
        bp.add(centerButton);
        hands.add(bp);
        JButton runButton = new JButton("Run");
        bp.add(runButton);
        JButton resetButton = new JButton("Reset");
        bp.add(resetButton);
        stepButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!m.gameOver) {
                    m.step();
                    dp.repaint();
                    for (HandPanel hp : handPanels) {
                        hp.repaint();
                    }
                }
            }
        });
        centerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sp.getViewport().setViewPosition(new Point(dp.width / 2 - 4 * PlayedCard.SIZE, dp.height / 2 - 3 * PlayedCard.SIZE));
            }
        });
        runButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!m.gameOver) {
                    m.play();
                    dp.repaint();
                    for (HandPanel hp : handPanels) {
                        hp.repaint();
                    }
                } else {
                    m.setup();
                    m.play();
                    dp.repaint();
                    for (HandPanel hp : handPanels) {
                        hp.repaint();
                    }
                }
            }
        });
        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                m.setup();
                dp.repaint();
                for (HandPanel hp : handPanels) {
                    hp.repaint();
                }
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                createAndShowGui();
            }
        });
    }

    private static void createAndShowGui() {
        JFrame frame = new JFrame("Iota Display");
        frame.setResizable(false);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        JComponent newContentPane = new IotaDisplay();
        newContentPane.setOpaque(true);
        frame.setContentPane(newContentPane);
        frame.pack();
        frame.setVisible(true);


    }
}
