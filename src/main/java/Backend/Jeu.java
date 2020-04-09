package Backend;

import java.util.*;

import Ihm.UI;

public class Jeu {
    private static boolean[][] gaufre; // le terrain
    private static int longueur;
    private static int largeur;
    public static Turn tour; // faux: player1, vrai: player2
    private static Stack<boolean[][]> history = new Stack<boolean[][]>();
    public static UI _ui;
    public static void init() {
        longueur = 10;
        largeur = 10;
        tour = Turn.Player1;
        gaufre = new boolean[longueur][largeur];

        // initialise toutes les cases par true (non occupée)
        for (int i = 0; i < longueur; i++) {
            for (int j = 0; j < largeur; j++) {
                gaufre[i][j] = true;
            }
        }
    }

    public static void init(int _longueur, int _largeur) {
        longueur = _longueur;
        largeur = _largeur;
        tour = Turn.Player1;
        gaufre = new boolean[longueur][largeur];
        // initialise toutes les cases par true (non occupée)
        for (int i = 0; i < longueur; i++) {
            for (int j = 0; j < largeur; j++) {
                gaufre[i][j] = true;
            }
        }
    }

    // renvoie vrai si la case x et y n'a pas encore été occupée
    public static boolean isFree(int x, int y) throws RuntimeException {
        if (x > longueur) {
            throw new RuntimeException("Erreur isBusy: x > longueur du terrain");
        } else if (y > largeur) {
            throw new RuntimeException("Erreur isBusy: y > largeur du terrain");
        } else {
            return gaufre[x][y];
        }
    }
    static boolean wentbackintime = false;
    // occupe une case x,y
    public static void occupe(int x, int y) throws RuntimeException {
        wentbackintime = false;
        if (x > longueur) {
            throw new RuntimeException("Erreur occupe: x > longueur du terrain");
        } else if (y > largeur) {
            throw new RuntimeException("Erreur occupe: y > largeur du terrain");
        } else {
            boolean[][] saved = new boolean[longueur()][largeur()];
            for (int i = 0; i < largeur(); i++) {
                for (int j = 0; j < longueur(); j++) {
                    saved[i][j] = gaufre[i][j];
                }
            }
            history.add(saved);

            affiche();
            if (isFree(x, y)) {
                for (int i = x; i < longueur; i++) {
                    for (int j = y; j < largeur; j++) {
                        gaufre[i][j] = false;
                    }
                }
                // tour = !tour; // tour du joueur suivant
                if (tour == Turn.Player1) {
                    tour = Turn.Player2;
                    if(_ui!=null)
                        _ui.player.setText("Player 2");
                } else {
                    tour = Turn.Player1;
                    if(_ui!=null)
                        _ui.player.setText("Player 1");
                }
            }
        }
        affiche();

    }

    public static void CTRL_Z() {
        if (history.size() == 0 ) {
            System.out.println("0 coups a récuperer.\n");
            return;
        }
        affiche();
        boolean[][] saved = history.pop();
        for (int i = 0; i < largeur(); i++) {
            for (int j = 0; j < longueur(); j++) {
                gaufre[i][j] = saved[i][j];
            }
        }
        wentbackintime = true;
        if (tour == Turn.Player1) {
            tour = Turn.Player2;
            _ui.player.setText("Player 2");
        } else {
            tour = Turn.Player1;
            _ui.player.setText("Player 1");
        }
        _ui.repaint();

    }

    // affiche la gaufre
    public static void affiche() {
        for (int i = 0; i < longueur; i++) {
            for (int j = 0; j < largeur; j++) {
                if (Jeu.isFree(j, i)) {
                    System.out.print("-");
                } else {
                    System.out.print(" ");
                }
            }
            System.out.println("");
        }
    }

    public static int longueur() {
        return Jeu.longueur;
    }

    // renvoi la largeur
    public static int largeur() {
        return Jeu.largeur;
    }

    public static Turn tour() {
        return Jeu.tour;
    }

    public static boolean[][] terrain() {
        return gaufre;
    }

}
