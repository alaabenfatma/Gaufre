import javax.swing.*;

import Backend.Jeu;
import Ihm.Ihm;

import java.awt.event.*;
import java.awt.*;

public class Game implements Runnable {

    @Override
    public void run() {
        JFrame frame = new JFrame("Gauffre");
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        JButton twoPlayers = new JButton("Player vs Player");
        twoPlayers.addMouseListener(new MouseAdapter() {

            public void mouseClicked(MouseEvent e) {
                Jeu.init(5, 5);
                Jeu.init(5, 5);
                SwingUtilities.invokeLater(new Ihm());
            }

        });
        JButton playerIA = new JButton("Player vs IA");
        JButton IA_IA = new JButton("IA vs IA");
        panel.add(twoPlayers);
        panel.add(playerIA);
        panel.add(IA_IA);
        frame.add(panel);
        frame.setSize(150, 130);
        frame.setVisible(true);
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((dimension.getWidth() - frame.getWidth()) / 2);
        int y = (int) ((dimension.getHeight() - frame.getHeight()) / 2);
        frame.setLocation(x, y);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Game());
    }

}