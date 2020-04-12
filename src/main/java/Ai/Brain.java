package Ai;

import java.util.ArrayList;

import Backend.Jeu;

public class Brain {
    private static Coup nextMove = null;
    public static Coup nextMove() {
        if(Jeu.gameOver(Jeu.terrain())){
            nextMove = new Coup(0, 0);
         }
        minimax(Jeu.copyOfTerrain(), true);
        System.out.println("AI is going to play : " + nextMove);
        return nextMove;
    }

    public static int minimax(boolean[][] terrain, boolean maximizing) {
        if (maximizing) {
            return maximize(terrain);
        } else {
            return minimize(terrain);
        }
    }

    public static int maximize(boolean[][] terrain) {
        int bestScore = Integer.MIN_VALUE;
        ArrayList<Coup> coups = Jeu.coupsPossibles(terrain);
        for (Coup coup : coups) {
            terrain = futureTerrain(terrain,coup);

            if (Jeu.gameOver(terrain)) {
                nextMove = coup;
                bestScore = 10;
                return bestScore;
            }
           
            if ((bestScore = Integer.max(minimax(terrain, false), bestScore)) == 10) {
                nextMove = coup;
                return bestScore;
            }
            
        }
        return bestScore;
    }

    public static int minimize(boolean[][] terrain) {
        int bestScore = Integer.MAX_VALUE;
        ArrayList<Coup> coups = Jeu.coupsPossibles(terrain);
        for (Coup coup : coups) {
            terrain = futureTerrain(terrain,coup);

            if (Jeu.gameOver(terrain)) {
                nextMove = coup;
                bestScore = -10;
                return bestScore;
            }
        
            if ((bestScore = Integer.min(minimax(terrain, true), bestScore)) == -10) {
                nextMove = coup;
                return bestScore;
            }

        }
        return bestScore;
    }

    private static boolean[][] futureTerrain(boolean[][] terrain,Coup coup) {
        // La fonction occupe(x,y) change la classe Jeu directement, il faut
        // re-implementer le mechanisme içi.
        for (int i = coup.i; i < Jeu.longueur(); i++) {
            for (int j = coup.j; j < Jeu.largeur(); j++) {
                terrain[i][j] = false;
            }
        }

        return terrain;
    }
}