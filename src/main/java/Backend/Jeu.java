package Backend;

import java.util.*;

import Ai.Coup;
import Ihm.UI;
import Ihm.msgBox;

public class Jeu {
    private static boolean[][] gaufre; // le terrain
    private static int longueur;
    private static int largeur;
    public static Turn tour; // faux: player1, vrai: player2
    private static Stack<boolean[][]> history = new Stack<boolean[][]>();
    public static UI _ui;
    public static GameLevel mode_IA = GameLevel.Easy;
    public static GameMode mode_JEU = GameMode.PVP;
    public static boolean GameOver = false;

    public static void init() {
        GameOver = false;
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
        GameOver = false;
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
        if (x < 0 || y < 0){
            throw new RuntimeException("x ou y est inférieur à 0");
        }
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
    public static void occupe(boolean[][] map, int x, int y) throws RuntimeException {
        if (GameOver) {
            return;
        }
        wentbackintime = false;
        if (x < 0 || y < 0){
            throw new RuntimeException("occupe : x ou y est inférieur à 0");
        }
        if (x > longueur) {
            throw new RuntimeException("Erreur occupe: x > longueur du terrain");
        } else if (y > largeur) {
            throw new RuntimeException("Erreur occupe: y > largeur du terrain");
        } else {
            if ((x == 0) && (y == 0)) {
                // on a perdu
                if (Jeu.tour() == Turn.Player2) {
                    msgBox.MessageBox("Player 2 a perdu", "Gameover");
                } else {
                    msgBox.MessageBox("Player 1 a perdu", "Gameover");
                }
                GameOver = true;
                return;
            }
            if (isFree(x, y)) {
                for (int i = x; i < longueur(); i++) {
                    for (int j = y; j < largeur(); j++) {
                        map[i][j] = false;
                    }
                }
                // tour = !tour; // tour du joueur suivant
                if (tour == Turn.Player1) {
                    tour = Turn.Player2;
                    if (_ui != null)
                        _ui.player.setText("Player 2");
                } else {
                    tour = Turn.Player1;
                    if (_ui != null)
                        _ui.player.setText("Player 1");
                }
            }
        }
        if(_ui != null){ 
            _ui.repaint();
        }
    }

    public static void occupe(int x, int y) throws RuntimeException {
        if (GameOver) {
            return;
        }
        wentbackintime = false;
        if (x < 0 || y < 0){
            throw new RuntimeException("occupe : x ou y est inférieur à 0");
        }
        if (x > longueur) {
            throw new RuntimeException("Erreur occupe: x > longueur du terrain");
        } else if (y > largeur) {
            throw new RuntimeException("Erreur occupe: y > largeur du terrain");
        } else {
            boolean[][] saved = new boolean[longueur()][largeur()];
            for (int i = 0; i < longueur(); i++) {
                for (int j = 0; j < largeur(); j++) {
                    saved[i][j] = gaufre[i][j];
                }
            }
            if ((x == 0) && (y == 0)) {
                // on a perdu
                if (Jeu.tour() == Turn.Player2) {
                    msgBox.MessageBox("Player 2 a perdu", "Gameover");
                } else {
                    msgBox.MessageBox("Player 1 a perdu", "Gameover");
                }
                GameOver = true;
                return;
            }
            history.add(saved);

            if (isFree(x, y)) {
                for (int i = x; i < longueur(); i++) {
                    for (int j = y; j < largeur(); j++) {
                        gaufre[i][j] = false;
                    }
                }
                // tour = !tour; // tour du joueur suivant
                if (tour == Turn.Player1) {
                    tour = Turn.Player2;
                    if (_ui != null)
                        _ui.player.setText("Player 2");
                } else {
                    tour = Turn.Player1;
                    if (_ui != null)
                        _ui.player.setText("Player 1");
                }
            }
        }
        affiche();

        if(_ui != null){
            _ui.repaint();
        }
    }

    public static void CTRL_Z() {
        if (history.size() == 0) {
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
            if (_ui != null) {
                _ui.player.setText("Player 2");
            }
        } else {
            tour = Turn.Player1;
            if (_ui != null) {
                _ui.player.setText("Player 1");
            }
        }
        if (_ui != null) {
            _ui.repaint();
        }
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
    // affiche la gaufre
    public static void affiche(boolean[][] map) {
        for (int i = 0; i < longueur; i++) {
            for (int j = 0; j < largeur; j++) {
                if (map[i][j]) {
                    System.out.print("-");
                } else {
                    System.out.print(" ");
                }
            }
            System.out.println("");
        }
    }

    public static int remainingMoves() {
        int c = 0;
        for (int i = 0; i < longueur; i++) {
            for (int j = 0; j < largeur; j++) {
                if (gaufre[i][j] == true) {
                    c++;
                }
            }
        }
        return c;
    }

    public static ArrayList<Coup> coupsPossibles(boolean[][] map) {
        ArrayList<Coup> coups = new ArrayList<Coup>();
        for (int i = 0; i < longueur; i++) {
            for (int j = 0; j < largeur; j++) {
                if (map[i][j] == true) {
                    if (i + j != 0)
                        coups.add(new Coup(i, j));
                }
            }
        }
        //Collections.reverse(coups);
        return coups;
    }

    public static boolean gameOver(boolean[][] map) {
        if(map[0][0]==true){
            if(map[0][1]==true){
                return false;
            }
            if(map[1][0]==true){
                return false;
            }
        }
        return true;
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

    public static boolean[][] copyOfTerrain() {
        boolean[][] cpy = new boolean[longueur()][largeur()];
        for (int i = 0; i < longueur(); i++) {
            for (int j = 0; j < largeur(); j++) {
                cpy[i][j] = gaufre[i][j];
            }
        }
        return cpy;
    }

    public static Stack<boolean[][]> pile() {
        return Jeu.history;
    }
}
