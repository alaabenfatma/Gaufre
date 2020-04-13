package Ihm;

import javax.swing.*;

import Ai.Brain;
import Ai.Coup;

import java.awt.*;
import java.awt.event.*;
import Backend.Jeu;
import Backend.Turn;

public class Ihm implements Runnable {
    public void run() {
		// Creation d'une fenetre
		JFrame frame = new JFrame("Gauffre");
		// Ajout de notre composant de dessin dans la fenetre
		UI ui = new UI();
		frame.add(ui);
		//Coup c = Brain.nextMove();
		//Jeu.occupe(c.i, c.j);
		//frame.addKeyListener(new EcouteurKey(j,ng));
		// Un clic sur le bouton de fermeture clos l'application
		

        // On fixe la taille et on demarre
		ui.setVisible(true);
		frame.addMouseListener(new mouseListener(ui));
		frame.setVisible(true);
		
        frame.setSize(500,500);
		frame.addKeyListener(new keyListener());
        //frame.add(ui.player, BorderLayout.NORTH);
		/*
		if (maximized) {
			device.setFullScreenWindow(null);
			maximized = false;
		} else {
			device.setFullScreenWindow(frame);
			maximized = true;
		}*/
	}
}