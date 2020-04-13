import javax.swing.*;

import Backend.*;
import Ihm.Ihm;

import java.awt.event.*;
import java.util.Hashtable;
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
                Jeu.init(5,2);
                Jeu.mode_JEU = GameMode.PVP;
                SwingUtilities.invokeLater(new Ihm());
            }

        });
        JButton playerIA = new JButton("Player vs IA");
        playerIA.addMouseListener(new MouseAdapter() {

            public void mouseClicked(MouseEvent e) {
                Jeu.init(4,6);
                Jeu.mode_JEU = GameMode.PVA;
                SwingUtilities.invokeLater(new Ihm());
            }

        });
        JButton IA_IA = new JButton("IA vs IA");
        IA_IA.addMouseListener(new MouseAdapter() {

            public void mouseClicked(MouseEvent e) {
                Jeu.init(5, 5);
                Jeu.mode_JEU = GameMode.AVA;
                SwingUtilities.invokeLater(new Ihm());
            }

        });
        // IA MODE SLIDE
        JSlider ia_Slider = new JSlider( JSlider.HORIZONTAL,0,100,10);
        ia_Slider.setMinorTickSpacing(50);
        ia_Slider.setMajorTickSpacing(50);
        ia_Slider.setPaintTicks(true);
       
        Hashtable<Integer, JLabel> levels = new Hashtable<Integer, JLabel>();
        levels.put(0, new JLabel("Facile"));
        levels.put(50, new JLabel("Normale"));
        levels.put(100, new JLabel("Impossible"));
        ia_Slider.setLabelTable(levels);
        ia_Slider.setPaintLabels(true);
        JLabel diff = new JLabel("Niveau d'IA : ");
        // Set the label to be drawn
        ia_Slider.setLabelTable(levels);
        panel.add(twoPlayers);
        panel.add(playerIA);
        panel.add(IA_IA);
        panel.add(diff);
        panel.add(ia_Slider);
        frame.add(panel);

        frame.setSize(250, 200);
        frame.setLocationRelativeTo(null);
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