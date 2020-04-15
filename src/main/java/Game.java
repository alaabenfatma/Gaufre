import javax.swing.*;

import Backend.*;
import Ihm.Ihm;
import Ihm.msgBox;

import java.awt.event.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Hashtable;
import java.awt.*;

public class Game implements Runnable {
    private int customLargeur = -1, customLongueur = -1;
    private int longueur = 6, largeur = 8;

    @Override
    public void run() {
        JFrame frame = new JFrame("Start Menu");
        JPanel panel = new JPanel();
        JLabel jLabel1 = new JLabel();
        JLabel jLabel2 = new JLabel();

        // panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setLayout(new GridLayout(8, 0));

        // Champs largeur/longueur
        jLabel1.setText("Entrez la largeur :");
        jLabel1.setAlignmentX(Component.CENTER_ALIGNMENT);

        JTextField largeurField = new JTextField(1);
        largeurField.setPreferredSize(new Dimension(20, 20));

        largeurField.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                String getValue = largeurField.getText();
                try {
                    customLargeur = Integer.parseInt(getValue);
                    System.out.println("Largeur : " + customLargeur);
                } catch (Exception e1) {
                }

            }
        });

        jLabel2.setText("Entrez la longueur :");
        jLabel2.setAlignmentX(Component.CENTER_ALIGNMENT);

        JTextField longueurField = new JTextField();
        longueurField.setPreferredSize(new Dimension(20, 20));
        longueurField.addFocusListener(new FocusListener() {
            public void focusGained(FocusEvent e) {
            }

            public void focusLost(FocusEvent e) {
                String getValue2 = longueurField.getText();
                try {
                    customLongueur = Integer.parseInt(getValue2);
                    System.out.println("Longueur : " + customLongueur);
                } catch (Exception e1) {
                }

            }
        });

        // Selection du mode de jeu
        JButton twoPlayers = new JButton("Player vs Player");
        twoPlayers.setAlignmentX(Component.CENTER_ALIGNMENT);
        twoPlayers.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                File f = new File("en_cours");
                if (f.exists()) {
                    int dialogButton = JOptionPane.YES_NO_OPTION;
                    int dialogResult = JOptionPane.showConfirmDialog(null,
                            "Une partie existe déjà, voulez-vous la continuer?", "Partie en cours", dialogButton);
                    if (dialogResult == JOptionPane.YES_OPTION) {
                        Jeu.init("en_cours");
                        Jeu.mode_JEU = GameMode.PVP;
                        SwingUtilities.invokeLater(new Ihm());
                    } else {
                        f.delete();
                        get_dim();
                        Jeu.init(longueur, largeur);
                        Jeu.mode_JEU = GameMode.PVP;
                        SwingUtilities.invokeLater(new Ihm());
                    }
                } else {
                    get_dim();
                    Jeu.init(longueur, largeur);
                    Jeu.mode_JEU = GameMode.PVP;
                    SwingUtilities.invokeLater(new Ihm());
                }
            }
        });

        JButton playerIA = new JButton("Player vs IA");
        playerIA.setAlignmentX(Component.CENTER_ALIGNMENT);
        playerIA.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                File f = new File("en_cours");
                if (f.exists()) {
                    int dialogButton = JOptionPane.YES_NO_OPTION;
                    int dialogResult = JOptionPane.showConfirmDialog(null,
                            "Une partie existe déjà, voulez-vous la continuer?", "Partie en cours", dialogButton);
                    if (dialogResult == JOptionPane.YES_OPTION) {
                        Jeu.init("en_cours");
                        Jeu.mode_JEU = GameMode.PVA;
                        SwingUtilities.invokeLater(new Ihm());
                    } else {
                        f.delete();
                        get_dim();
                        Jeu.init(longueur, largeur);
                        Jeu.mode_JEU = GameMode.PVA;
                        SwingUtilities.invokeLater(new Ihm());
                    }
                } else {
                    get_dim();
                    Jeu.init(longueur, largeur);
                    Jeu.mode_JEU = GameMode.PVA;
                    SwingUtilities.invokeLater(new Ihm());
                }
            }
        });

        // IA MODE combo box
        JLabel diffL = new JLabel("Niveau d'IA : ");
        diffL.setAlignmentX(Component.CENTER_ALIGNMENT);

        String[] iaStrings = { "Aleatoire", "Coup gagnant", "MiniMax" };
        JComboBox ia_List = new JComboBox(iaStrings);
        ia_List.setSelectedIndex(0);
        ia_List.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JComboBox cb = (JComboBox) e.getSource();
                String diff = (String) cb.getSelectedItem();
                switch (diff) {
                    case "Aleatoire":
                        Jeu.mode_IA = GameLevel.Easy;
                        break;
                    case "Coup gagnant":
                        Jeu.mode_IA = GameLevel.Medium;
                        break;
                    case "MiniMax":
                        Jeu.mode_IA = GameLevel.Hard;
                        break;
                }
            }
        });

        panel.add(jLabel1);
        panel.add(largeurField);
        panel.add(jLabel2);
        panel.add(longueurField);
        panel.add(twoPlayers);
        panel.add(playerIA);
        panel.add(diffL);
        panel.add(ia_List);
        frame.add(panel);

        frame.setSize(300, 300);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((dimension.getWidth() - frame.getWidth()) / 2);
        int y = (int) ((dimension.getHeight() - frame.getHeight()) / 2);
        frame.setLocation(x, y);
    }

    private void get_dim() {
        if (customLargeur != -1 && customLongueur != -1) {
            longueur = customLongueur;
            largeur = customLargeur;
        } else {
            msgBox.MessageBox("Dimension par defaut 6x8.", "Dimension");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Game());
    }

}