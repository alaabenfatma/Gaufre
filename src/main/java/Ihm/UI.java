package Ihm;

import Backend.Jeu;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.*;

public class UI extends JComponent {
    Image gauffre;
    Image poison;

    public UI() {
        try {
            InputStream img = UI.class.getResourceAsStream("gaufre.png");
            gauffre = ImageIO.read(img);
            img =  UI.class.getResourceAsStream("poison.png");
            poison = ImageIO.read(img);
            
        } catch (IOException e) {
            System.out.println(e.toString());
        }

    }
    int counter = 0;
    public void clear(){
        Graphics2D drawable = (Graphics2D) this.getGraphics();
        int width = getSize().width;
		int height = getSize().height;
		drawable.clearRect(0, 0, width, height);
    }
    public void paintComponent(Graphics g) {
        clear();
        Dimension d = this.getSize();
        if(d==null){
            System.out.println("Couldn't get dimensions\n");
            System.exit(1);
        }
        int x = 0, y = 0;
        int w = d.width / Jeu.largeur();
        int h = d.height / Jeu.longeur();
        System.out.println(h + " " + w);
        Graphics2D drawable = (Graphics2D) g;
        try {
            for (int i = 0; i < Jeu.longeur(); i++) {
                x = 0;
                System.out.printf("Drawn %d times\n",counter++);

                for (int j = 0; j < Jeu.largeur(); j++) {
                    if(i==0 && j==0){
                        drawable.drawImage(poison, x, y, w, h, null);
                    }
                    else if (Jeu.terrain()[i][j] == true) {
                        drawable.drawImage(gauffre, x, y, w, h, null);
                    } else if (Jeu.terrain()[i][j] == false) {
                        drawable.drawRect( x, y, w, h);
                        drawable.setColor(Color.RED);
                        
                    }
                    x += w;

                }
                y += h;
            }

        } catch (Exception e) {
            // TODO: handle exception
        }

    }

}
