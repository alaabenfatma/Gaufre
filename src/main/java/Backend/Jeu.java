package Backend;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.*;

import Ai.Coup;
import Ihm.UI;
import Ihm.msgBox;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import Patterns.Observable;

public class Jeu extends Observable {
    private static boolean[][] gaufre; // le terrain
    private static int longueur;
    private static int largeur;
    public static Turn tour; // faux: player1, vrai: player2
    private static Stack<boolean[][]> history = new Stack<boolean[][]>();
    private static Stack<boolean[][]> Save = new Stack<boolean[][]>();
    public static UI _ui;
    public static GameLevel mode_IA = GameLevel.Easy;
    public static GameMode mode_JEU = GameMode.PVP;
    public static boolean GameOver = false;

    public static void init() {
        init(10, 10);
    }

    public static void init(int _longueur, int _largeur) {
        history.clear();
        Save.clear();
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

    public static void init(String filename) {
        GameOver = false;
        File f = new File(filename);
        Scanner myInput;
        String entry = new String();
        String[] entry_split;
        try {
            myInput = new Scanner(f);
            entry = myInput.nextLine();
            entry_split = entry.split(",");
            if (entry_split[0].equals("1")) {
                tour = Turn.Player1;
            } else {
                tour = Turn.Player2;
            }
            longueur = Integer.parseInt(entry_split[1]);
            largeur = Integer.parseInt(entry_split[2]);
            gaufre = new boolean[longueur][largeur];

            for (int i = 0; i < longueur; i++) {
                entry = myInput.nextLine();
                for (int j = 0; j < largeur; j++) {
                    if (entry.charAt(j) == '+') {
                        gaufre[i][j] = true;
                    } else {
                        gaufre[i][j] = false;
                    }
                }
            }

        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    // renvoie vrai si la case x et y n'a pas encore été occupée
    public static boolean isFree(int x, int y) throws RuntimeException {
        if (x < 0 || y < 0) {
            throw new RuntimeException("x ou y est inférieur à 0");
        }
        if (x >= longueur) {
            throw new RuntimeException("Erreur isBusy: x > longueur du terrain");
        } else if (y >= largeur) {
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
        if (x < 0 || y < 0) {
            throw new RuntimeException("occupe : x ou y est inférieur à 0");
        }
        if (x > largeur) {
            throw new RuntimeException("Erreur occupe: x > longueur du terrain");
        } else if (y > longueur) {
            throw new RuntimeException("Erreur occupe: y > largeur du terrain");
        } else {
            if ((x == 0) && (y == 0)) {
                // on a perdu
                if (Jeu.tour() == Turn.Player2) {
                    if (mode_JEU == GameMode.PVA) {
                        msgBox.MessageBox("You won against the AI.", "Gameover");

                    } else {
                        msgBox.MessageBox("Player 2 a perdu", "Gameover");
                    }

                } else {
                    if (mode_JEU == GameMode.PVA) {
                        msgBox.MessageBox("You lost against the AI", "Gameover");
                    } else {
                        msgBox.MessageBox("Player 1 a perdu", "Gameover");
                    }
                }
                GameOver = true;
                File f = new File("en_cours");
                f.delete();
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
        if (_ui != null) {
            _ui.repaint();
            metAJour();
        }
    }

    public static void occupe(int x, int y) throws RuntimeException {
        if (GameOver) {
            return;
        }
        wentbackintime = false;
        if (x < 0 || y < 0) {
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
                    if (mode_JEU == GameMode.PVA)
                        msgBox.MessageBox("You won against the AI.", "Gameover");
                    else
                        msgBox.MessageBox("Player 2 a perdu", "Gameover");
                } else {
                    if (mode_JEU == GameMode.PVA)
                        msgBox.MessageBox("You lost against the AI", "Gameover");
                    else
                        msgBox.MessageBox("Player 1 a perdu", "Gameover");
                }
                GameOver = true;
                File f = new File("en_cours");
                f.delete();
                JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(_ui);
                topFrame.setVisible(false);
                topFrame.dispose();
                return;
            }
            history.add(saved);
            metAJour();

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
                CTRL_S();
            }
        }
        // affiche();

        if (_ui != null) {
            _ui.repaint();
            metAJour();

        }
    }

    public static void CTRL_S() {
        BufferedWriter writer;
        try {
            File file = new File("en_cours");
            file.createNewFile();
            writer = new BufferedWriter(new FileWriter(file));
            if (tour == Turn.Player1) {
                writer.write("1," + longueur + "," + largeur);
                writer.newLine();
            } else {
                writer.write("2," + longueur + "," + largeur);
                writer.newLine();
            }
            for (int i = 0; i < longueur; i++) {
                for (int j = 0; j < largeur; j++) {
                    if (Jeu.isFree(i, j)) {
                        writer.write("+");
                    } else {
                        writer.write("-");
                    }
                }
                writer.newLine();
            }
            writer.flush();
            writer.close();
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    public static void CTRL_Z() {
        if (history.size() == 0) {
            System.out.println("0 coups a récuperer.\n");
            return;
        }
        affiche();
        Save.add(copyOfTerrain());
        boolean[][] saved = history.pop();
        for (int i = 0; i < longueur(); i++) {
            for (int j = 0; j < largeur(); j++) {
                gaufre[i][j] = saved[i][j];
            }
        }
        wentbackintime = true;
        if (tour == Turn.Player1) {
            tour = Turn.Player2;
            if (_ui != null) {
                _ui.playerBar.setText("Player 2");
            }
        } else {
            tour = Turn.Player1;
            if (_ui != null) {
                _ui.playerBar.setText("Player 1");
            }
        }
        if (_ui != null) {
            _ui.repaint();
            metAJour();
        }
    }

    public static void CTRL_Y() {
        if (Save.size() == 0) {
            System.out.println("Restauration du terrain atteint.\n");
            return;
        }
        affiche();
        history.add(copyOfTerrain());
        boolean[][] saved = Save.pop();
        for (int i = 0; i < longueur(); i++) {
            for (int j = 0; j < largeur(); j++) {
                gaufre[i][j] = saved[i][j];
            }
        }
        wentbackintime = true;
        if (tour == Turn.Player1) {
            tour = Turn.Player2;
            if (_ui != null) {
                _ui.playerBar.setText("Player 2");
            }
        } else {
            tour = Turn.Player1;
            if (_ui != null) {
                _ui.playerBar.setText("Player 1");
            }
        }
        if (_ui != null) {
            _ui.repaint();
            metAJour();
        }
    }

    // affiche la gaufre
    public static void affiche() {
        affiche(terrain());
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
        return remainingMoves(terrain());
    }

    public static int remainingMoves(boolean[][] map) {
        int c = 0;
        for (int i = 0; i < longueur; i++) {
            for (int j = 0; j < largeur; j++) {
                if (map[i][j] == true) {
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
                    if (i + j != 1)
                        coups.add(new Coup(i, j));
                }
            }
        }
        return coups;
    }

    public static boolean gameOver(boolean[][] map) {
        int x = remainingMoves(map);
        return x <= 1;
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
        return copyOfTerrain(terrain());
    }

    public static boolean[][] copyOfTerrain(boolean[][] map) {
        boolean[][] cpy = new boolean[longueur()][largeur()];
        for (int i = 0; i < longueur(); i++) {
            for (int j = 0; j < largeur(); j++) {
                cpy[i][j] = map[i][j];
            }
        }
        return cpy;
    }

    public static Stack<boolean[][]> pile() {
        return Jeu.history;
    }

    public static Stack<boolean[][]> pile_save() {
        return Jeu.Save;
    }

    public boolean peutRefaire() {
        return Save.size() != 0;
    }

    public boolean peutAnnuler() {
        return history.size() != 0;
    }

    public static String getPlayer() {
        if (Jeu.tour == Turn.Player1) {
            return "Player 1";
        }
        if (Jeu.tour == Turn.Player2 && mode_JEU == GameMode.PVA) {
            return "AI";
        }
        if (Jeu.tour == Turn.Player2 && mode_JEU == GameMode.PVP) {
            return "Player 2";
        }
        return "";
    }

    public static boolean canPlay = true;
}
