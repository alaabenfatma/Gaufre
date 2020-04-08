package Ihm;

import javax.swing.*;

import Backend.Jeu;

public class Ihm implements Runnable {
    public void run() {
		// Creation d'une fenetre
		JFrame frame = new JFrame("Gauffre");
		// Ajout de notre composant de dessin dans la fenetre
		UI ui = new UI();
		
		frame.add(ui);
		//frame.addMouseListener(new EcouteurDeSouris(j));
		//frame.addKeyListener(new EcouteurKey(j,ng));
		// Un clic sur le bouton de fermeture clos l'application
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // On fixe la taille et on demarre
        ui.setVisible(true);
		frame.setVisible(true);
		
		frame.setSize(500,500);
		/*
		if (maximized) {
			device.setFullScreenWindow(null);
			maximized = false;
		} else {
			device.setFullScreenWindow(frame);
			maximized = true;
		}*/
	}

	public static void main(String[] args) {
		// Swing s'exécute dans un thread séparé. En aucun cas il ne faut accéder directement
		// aux composants graphiques depuis le thread principal. Swing fournit la méthode
        // invokeLater pour demander au thread de Swing d'exécuter la méthode run d'un Runnable.
        Jeu.init(5,5);
		SwingUtilities.invokeLater(new Ihm ());
	}
}