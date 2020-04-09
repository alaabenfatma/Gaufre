package Ai;

import java.util.Random;
import Backend.Jeu;
import Ihm.msgBox;

/*Fonctions a implementer ailleurs

Backend.Jeu.longueur() recupere la longueur du Jeu
Jeu.largeur() recupere la largeur du Jeu
Jeu.isFree(i,j) revoir si la case de coordonnée (i,j) n'est PAS isFree
Backend.Backend.Backend.jouer(i,j) joue le coup a la case de coordonnée (i,j) et supprime les cases en bas a droite en conséquence, si cette case est la case poison alors le joueur a perdu

*/

public class Ai {
    Random r;

    boolean IARandom() {
        int i, j;
        // On choisi aléatoirement une case, puis on regarde si elle est isFree(on peux
        // la selectionner)
        i = r.nextInt(Jeu.longueur());
        j = r.nextInt(Jeu.largeur());
        while (!Jeu.isFree(i, j)) {
            // Si elle n'est pas isFree on en choisit une autre
            i = r.nextInt(Jeu.longueur());
            j = r.nextInt(Jeu.largeur());
        }
        Backend.Backend.jouer(i, j);
        return true;
    }
    /*
     * boolean IAcoup() { //Version améliorée de random, qui choist un coup gagnant
     * quand celui ci se presente int i, j;
     * 
     * i = r.nextInt(Jeu.longueur()); j = r.nextInt(Jeu.largeur()); + while
     * (Jeu.isFree(i, j)) { i = r.nextInt(Jeu.longueur()); j =
     * r.nextInt(Jeu.largeur()); }
     * 
     * //On analyse les coups gagnant ici if (!Jeu.isFree(0,1)){ //si la case juste
     * en bas de la case empoisonné n'est pas isFree alors il ne reste que la ligne
     * de la case empoisonnée de isFree, on supprime toutes les case de la ligne
     * sauf la case empoisonnée Backend.Backend.jouer(0,1);
     * Backend.Backend.jouer(1,0); return true; } if (!Jeu.isFree(1,0)){ //si la
     * case juste a droite de la case empoisonné n'est pas isFree alors il ne reste
     * que la colonne de la case empoisonnée de isFree, on supprime toutes les case
     * de la colonne sauf la case empoisonnée Backend.Backend.jouer(0,1); return
     * true; }
     * 
     * Backend.Backend.jouer(i, j); return true; }
     */

    public static boolean IAgagnante() {
        // Version améliorée ++ qui fait les meilleurs coups
        if (Jeu.gameOver() || Jeu.GameOver){
            msgBox.MessageBox("IA LOST", "game over");
            return false;
        }
        // Pour que le joueur 1 gagne a tous les coups il doit se retouver avec juste la
        // ligne et la colonne de la case poison
        if (Jeu.isFree(1, 1)) {
            Jeu.occupe(1, 1);
            return true;
        }
        for (int k = 1; k < Jeu.longueur(); k++) {
            if (!Jeu.isFree(0, k) && Jeu.isFree(k, 0)) {
                Jeu.occupe(k, 0);
                System.out.println("AI is playing. " + k);
                return true;
            }
            if (!Jeu.isFree(k, 0) && Jeu.isFree(0, k)) {
                Jeu.occupe(0, k);
                System.out.println("AI is playing.! " + k);
                return true;
            }

        }

        return false;
    }

}