import javax.swing.*;

import Backend.*;
import Ihm.Ihm;
import Ihm.msgBox;

import java.awt.event.*;
import java.util.Hashtable;
import java.awt.*;

public class Game implements Runnable {
    private int customLargeur = -1, customLongueur = -1;
    private int longueur = 6, largeur = 8;

    @Override
    public void run() {
        JFrame frame = new JFrame("Gaufre");
        JPanel panel = new JPanel();
        JLabel jLabel1 = new JLabel();
        JLabel jLabel2 = new JLabel();

        //Champs largeur/longueur
        jLabel1.setText("Entrez la largeur :");
        panel.add(jLabel1);
        
        JTextField largeurField = new JTextField(1);
        largeurField.setPreferredSize(new Dimension(20, 20));
        panel.add(largeurField);
        largeurField.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                String getValue = largeurField.getText();
                customLargeur = Integer.parseInt(getValue);
                System.out.println("Largeur : " + customLargeur);
            }
        });

        jLabel2.setText("Entrez la longueur :");
        panel.add(jLabel2);
        
        JTextField longueurField = new JTextField();
        longueurField.setPreferredSize(new Dimension(20, 20));
        panel.add(longueurField);
        longueurField.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                String getValue2 = longueurField.getText();
                customLongueur = Integer.parseInt(getValue2);
                System.out.println("Longueur : " + customLongueur);
            }
        });

        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        
        //Selection du mode de jeu
        JButton twoPlayers = new JButton("Player vs Player");
        twoPlayers.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                get_dim();
                Jeu.init(longueur, largeur);
                Jeu.mode_JEU = GameMode.PVP;
                SwingUtilities.invokeLater(new Ihm());
            }
        });

        JButton playerIA = new JButton("Player vs IA");
        playerIA.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                get_dim();
                Jeu.init(longueur, largeur);
                Jeu.mode_JEU = GameMode.PVA;
                SwingUtilities.invokeLater(new Ihm());
            }
        });

        // IA MODE SLIDE
        JSlider ia_Slider = new JSlider(JSlider.HORIZONTAL, 0, 100, 10);
        ia_Slider.setMinorTickSpacing(50);
        ia_Slider.setMajorTickSpacing(50);
        ia_Slider.setPaintTicks(true);

        Hashtable<Integer, JLabel> levels = new Hashtable<Integer, JLabel>();
        levels.put(0, new JLabel("Facile"));
        levels.put(50, new JLabel("Normale"));
        levels.put(100, new JLabel("Difficile"));
        ia_Slider.setLabelTable(levels);
        ia_Slider.setPaintLabels(true);
        JLabel diff = new JLabel("Niveau d'IA : ");
        // Set the label to be drawn
        ia_Slider.setLabelTable(levels);
        panel.add(twoPlayers);
        panel.add(playerIA);
        panel.add(diff);
        panel.add(ia_Slider);
        frame.add(panel);

        frame.setSize(250, 270);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((dimension.getWidth() - frame.getWidth()) / 2);
        int y = (int) ((dimension.getHeight() - frame.getHeight()) / 2);
        frame.setLocation(x, y);
    }

    private void get_dim(){
        if (customLargeur != -1 && customLongueur != -1) {
            longueur = customLongueur;
            largeur = customLargeur;
        }
        else{
            msgBox.MessageBox("Dimension par défaut 6x8","Dimension");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Game());
    }

}