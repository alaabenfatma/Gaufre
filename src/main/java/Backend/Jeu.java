package Backend;

enum Turn{
    Player1,
    Player2
}
public  class Jeu {
    private static boolean[][] gaufre; // le terrain
    private static int longueur;
    private static int largeur;
    private static Turn tour = Turn.Player1; // faux: player1, vrai: player2

    public static void init() {
        longueur = 10;
        largeur = 10;
        gaufre = new boolean[longueur][largeur];

        // initialise toutes les cases par true (non occupée)
        for (int i = 0; i < longueur; i++) {
            for (int j = 0; j < largeur; j++) {
                gaufre[i][j] = true;
            }
        }
    }


    public static void init(int _longeur, int _largeur) {
        longueur = _longeur;
        largeur = _largeur;
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

    // occupe une case x,y
    public static void occupe(int x, int y) throws RuntimeException {
        if (x > longueur) {
            throw new RuntimeException("Erreur occupe: x > longueur du terrain");
        } else if (y > largeur) {
            throw new RuntimeException("Erreur occupe: y > largeur du terrain");
        } else {
            if (isFree(x, y)) {
                for (int i = x; i < longueur; i++) {
                    for (int j = y; j < largeur; j++) {
                        gaufre[i][j] = false;
                    }
                }
                //tour = !tour; // tour du joueur suivant
                if(tour== Turn.Player1){
                    tour = Turn.Player2;
                }
                else{
                    tour = Turn.Player1;
                }
            }
        }
        affiche();
    }

    // affiche la gaufre
    public static void affiche() {
        for (int i =0; i < longueur; i++) {
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

    //renvoi la longueur
    public static int longueur(){
        return Jeu.longueur;
    }

    //renvoi la largeur
    public static int largeur(){
        return Jeu.largeur;
    }

    public static Turn tour(){
        return Jeu.tour;
    }

    public static boolean[][] terrain(){
        return Jeu.gaufre;
    }

}
