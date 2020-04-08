package Backend;

import java.util.Scanner;

//Définie les principales règles du jeu

public class Backend {


    public Backend(){
        
    }
    public void joue() {
        boolean perdu = false;
        Scanner myInput = new Scanner(System.in);
        String ligne;
        String [] ligne_split;
        int x_input;
        int y_input;
        Jeu.affiche();
        while (!perdu) {
            // on lis un coup
            if (Jeu.tour==Turn.Player1) {
                do {
                    System.out.print("Player 1 : Entrez x et y: ");
                    ligne = myInput.nextLine();
                    ligne_split=ligne.split(" ");
                    x_input = Integer.parseInt(ligne_split[0]);
                    y_input = Integer.parseInt(ligne_split[1]);
                } while (!Jeu.isFree(x_input, y_input));
            } else {
                do {
                    System.out.print("Player 2 : Entrez x et y: ");
                    ligne = myInput.nextLine();
                    ligne_split=ligne.split(" ");
                    x_input = Integer.parseInt(ligne_split[0]);
                    y_input = Integer.parseInt(ligne_split[1]);
                } while (!Jeu.isFree(x_input, y_input));
            }

            if ((x_input == 0) && (y_input == 0)) {
                // on a perdu
                if (Jeu.tour == Turn.Player2) {
                    System.out.println("Player 2 a perdu");
                } else {
                    System.out.println("Player 1 a perdu");
                }
                perdu = true;
            } else {
                Jeu.occupe(x_input, y_input);
            }

        }
        myInput.close();
    }

    public static void main(String[] args) {
        Jeu.init();
        Backend n = new Backend();
        n.joue();
    }
    //renvoi la longueur

}
