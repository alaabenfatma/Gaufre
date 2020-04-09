package Ai;
import java.util.Random;

/*Fonctions a implementer ailleurs

plateau.hauteur() recupere la hauteur du plateau de jeu
plateau.largeur() recupere la largeur du plateau de jeu
plateau.libre(i,j) revoir si la case de coordonnée (i,j) n'est PAS libre
plateau.jouer(i,j) joue le coup a la case de coordonnée (i,j) et supprime les cases en bas a droite en conséquence, si cette case est la case poison alors le joueur a perdu

*/



public class Ai {
  /*  Random r;

    boolean IARandom() {
		int i, j;
        //On choisi aléatoirement une case, puis on regarde si elle est libre(on peux la selectionner)
		i = r.nextInt(Jeu..hauteur());
        j = r.nextInt(plateau.largeur());
		while (!plateau.libre(i, j)) {
            //Si elle n'est pas libre on en choisit une autre
			i = r.nextInt(plateau.hauteur());
            j = r.nextInt(plateau.largeur());            
		}
		plateau.jouer(i, j);
		return true;
    }
    
    boolean IAcoup() {
        //Version améliorée de random, qui choist un coup gagnant quand celui ci se presente
        int i, j;

		i = r.nextInt(plateau.hauteur());
        j = r.nextInt(plateau.largeur());

		while (!plateau.libre(i, j)) {
			i = r.nextInt(plateau.hauteur());
            j = r.nextInt(plateau.largeur());
        }

        //On analyse les coups gagnant ici
        if (plateau.libre(0,1)){
            //si la case juste en bas de la case empoisonné n'est pas libre alors il ne reste que la ligne de la case empoisonnée de libre, on supprime toutes les case de la ligne sauf la case empoisonnée
            plateau.jouer(0,1);
            plateau.jouer(1,0);
		    return true;
        }
        if (plateau.libre(1,0)){
            //si la case juste a droite de la case empoisonné n'est pas libre alors il ne reste que la colonne de la case empoisonnée de libre, on supprime toutes les case de la colonne sauf la case empoisonnée
            plateau.jouer(0,1);
		    return true;
        }

		plateau.jouer(i, j);
		return true;
    }


    boolean IAgagnante() {
        //Version améliorée ++ qui fait les meilleurs coups

        int i, j;

        //Pour que le joueur 1 gagne a tous les coups il doit se retouver avec juste la ligne et la colonne de la case poison
        if(!plateau.libre(1, 1)){
            plateau.jouer(1, 1);
            return true;
        }
        for (int i = 1; i < plateau.hauteur(); i++) {
            if(plateau.libre(0, i) && !plateau.libre(i, 0)){
                plateau.jouer(i,0);
                return true;
            }
            if(plateau.libre(i, 0) && !plateau.libre(0, i)){
                plateau.jouer(0,i);
                return true;
            }
        }
        return false;
    }*/
}