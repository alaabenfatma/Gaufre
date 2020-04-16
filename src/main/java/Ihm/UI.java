package Ihm;

import Backend.Jeu;
import Patterns.Observateur;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.*;

public class UI extends JComponent implements Observateur  {
    Image gauffre;
    Image poison;
    Jeu j;
    public JLabel player = new JLabel("Player X");
    public bar playerBar = new bar(Jeu.getPlayer());
    public UI(Jeu jeu) {
        j = jeu;
        j.ajouteObservateur(this);
        try {
            InputStream img = UI.class.getResourceAsStream("gaufre.png");
            gauffre = ImageIO.read(img);
            img = UI.class.getResourceAsStream("poison.png");
            poison = ImageIO.read(img);
            Jeu._ui = this;
        } catch (IOException e) {
            System.out.println(e.toString());
        }

    }

    int counter = 0;

    public void clear() {
        Graphics2D drawable = (Graphics2D) this.getGraphics();
        int width = getSize().width;
        int height = getSize().height;
        drawable.clearRect(0, 0, width, height);
    }

    public Dimension cellSize;

    public void paintComponent(Graphics g) {
        Component[] components = getComponents();
        for (Component component : components) {
            if (component instanceof Cell || component instanceof bar|| component instanceof JPanel)
                this.remove(component);
        }
        Dimension d = this.getSize();
        if (d == null) {
            System.out.println("Couldn't get dimensions\n");
            System.exit(1);
        }
        int x = 0, y = 0;
        int w = d.width / Jeu.largeur();
        int h = d.height / Jeu.longueur();
        cellSize = new Dimension(w, h);
        try {
            for (int i = 0; i < Jeu.longueur(); i++) {
                x = 0;
                for (int j = 0; j < Jeu.largeur(); j++) {
                    if (i == 0 && j == 0) {
                        Cell c = new Cell(poison, i, j, w, h);
                        c.setLocation(x, y);
                        c.setSize(w, h);
                        this.add(c);
                    } else if (Jeu.terrain()[i][j] == true) {
                        // drawable.drawImage(gauffre, x, y, w, h, null);
                        Cell c = new Cell(gauffre, i, j, w, h);
                        c.setLocation(x, y);
                        c.setSize(w, h);
                        this.add(c);
                    } else if (Jeu.terrain()[i][j] == false) {
                        JPanel p = new JPanel();
                        p.setLocation(x, y);
                        p.setSize(w,h);
                        p.setBackground(Color.WHITE);
                        this.add(p);
                    }
                    x += w;

                }
                y += h;
            }
            
        } catch (Exception e) {
            // TODO: handle exception
        }

    }

    @Override
    public void miseAJour() {
        // TODO Auto-generated method stub

    }

}
