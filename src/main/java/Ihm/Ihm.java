package Ihm;

import javax.swing.*;

import Ai.Brain;
import Ai.Coup;

import java.awt.*;
import java.awt.event.*;
import Backend.Jeu;
import Backend.Turn;
import Patterns.Observateur;

public class Ihm implements Runnable, Observateur {
	Jeu j;


	public Ihm(Jeu jeu) {
		j = jeu;
	}

	public void run() {
		// Creation d'une fenetre
		JFrame frame = new JFrame("Gaufre");
		// Ajout de notre composant de dessin dans la fenetre
		UI ui = new UI(j);
		frame.add(ui);
		// Coup c = Brain.nextMove();
		// Jeu.occupe(c.i, c.j);
		// frame.addKeyListener(new EcouteurKey(j,ng));
		// Un clic sur le bouton de fermeture clos l'application

		// Texte et contrôles à droite de la fenêtre
		Box boiteTexte = Box.createVerticalBox();

		// Annuler / refaire
		BoutonAnnuler annuler = new BoutonAnnuler(j);
		annuler.setFocusable(false);
		BoutonRefaire refaire = new BoutonRefaire(j);
		refaire.setFocusable(false);
		Box annulerRefaire = Box.createHorizontalBox();
		annulerRefaire.add(annuler);
		annulerRefaire.add(refaire);
		annulerRefaire.setAlignmentX(Component.CENTER_ALIGNMENT);
		boiteTexte.add(annulerRefaire);

		// Retransmission des évènements au contrôleur
		annuler.addActionListener(new AdaptateurAnnuler());
		refaire.addActionListener(new AdaptateurRefaire());

		// On fixe la taille et on demarre
		ui.setVisible(true);
		frame.addMouseListener(new mouseListener(ui));
		frame.setVisible(true);

		frame.setSize(500, 500);
		frame.addKeyListener(new keyListener());


		frame.add(boiteTexte, BorderLayout.EAST);
		frame.add(Jeu._ui.playerBar, java.awt.BorderLayout.SOUTH);
		j.ajouteObservateur(this);
		// frame.add(ui.player, BorderLayout.NORTH);
		/*
		 * if (maximized) { device.setFullScreenWindow(null); maximized = false; } else
		 * { device.setFullScreenWindow(frame); maximized = true; }
		 */

	}

	@Override
	public void miseAJour() {

	}
}