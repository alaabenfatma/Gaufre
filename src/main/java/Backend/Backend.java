import java.util.Scanner;

//Définie les principales règles du jeu

public class Backend {

    private boolean[][] gaufre; // le terrain
    private int longueur;
    private int largeur;
    private boolean tour = false; // faux: player1, vrai: player2

    public Backend() {
        this.longueur = 10;
        this.largeur = 10;
        this.gaufre = new boolean[longueur][largeur];

        // initialise toutes les cases par true (non occupée)
        for (int i = 0; i < longueur; i++) {
            for (int j = 0; j < largeur; j++) {
                this.gaufre[i][j] = true;
            }
        }
    }

    public Backend(int longeur, int largeur) {
        this.longueur = longeur;
        this.largeur = largeur;
        this.gaufre = new boolean[longueur][largeur];
        // initialise toutes les cases par true (non occupée)
        for (int i = 0; i < longueur; i++) {
            for (int j = 0; j < largeur; j++) {
                this.gaufre[i][j] = true;
            }
        }
    }

    // renvoie vrai si la case x et y n'a pas encore été occupée
    public boolean isFree(int x, int y) throws RuntimeException {
        if (x > longueur) {
            throw new RuntimeException("Erreur isBusy: x > longueur du terrain");
        } else if (y > largeur) {
            throw new RuntimeException("Erreur isBusy: y > largeur du terrain");
        } else {
            return this.gaufre[x][y];
        }
    }

    // occupe une case x,y
    public void occupe(int x, int y) throws RuntimeException {
        if (x > longueur) {
            throw new RuntimeException("Erreur occupe: x > longueur du terrain");
        } else if (y > largeur) {
            throw new RuntimeException("Erreur occupe: y > largeur du terrain");
        } else {
            if (isFree(x, y)) {
                for (int i = x; i < longueur; i++) {
                    for (int j = y; j < largeur; j++) {
                        this.gaufre[i][j] = false;
                    }
                }
                tour = !tour; // tour du joueur suivant
            }
        }
        affiche();
    }

    // affiche la gaufre
    public void affiche() {
        for (int i = 0; i < longueur; i++) {
            for (int j = 0; j < largeur; j++) {
                if (isFree(j, i)) {
                    System.out.print("O");
                } else {
                    System.out.print("K");
                }
            }
            System.out.println("");
        }
    }

    public void joue() {
        boolean perdu = false;
        Scanner myInput = new Scanner(System.in);
        String ligne;
        String [] ligne_split;
        int x_input;
        int y_input;
        affiche();
        while (!perdu) {
            // on lis un coup
            if (!tour) {
                do {
                    System.out.print("Player 1 -- Entrez x et y: ");
                    ligne = myInput.nextLine();
                    ligne_split=ligne.split(" ");
                    x_input = Integer.parseInt(ligne_split[0]);
                    y_input = Integer.parseInt(ligne_split[1]);
                } while (!isFree(x_input, y_input));
            } else {
                do {
                    System.out.print("Player 2 -- Entrez x et y: ");
                    ligne = myInput.nextLine();
                    ligne_split=ligne.split(" ");
                    x_input = Integer.parseInt(ligne_split[0]);
                    y_input = Integer.parseInt(ligne_split[1]);
                } while (!isFree(x_input, y_input));
            }

            if ((x_input == 0) && (y_input == 0)) {
                // on a perdu
                if (tour) {
                    System.out.println("Player 2 a perdu");
                } else {
                    System.out.println("Player 1 a perdu");
                }
                perdu = true;
            } else {
                occupe(x_input, y_input);
            }

        }
        myInput.close();
    }

    //renvoi la longueur
    public int longeur(){
        return this.longueur;
    }

    //renvoi la largeur
    public int largeur(){
        return this.largeur;
    }

    public boolean tour(){
        return this.tour;
    }

    public static void main(String[] args) {
        Backend n = new Backend();
        n.joue();
    }
}
