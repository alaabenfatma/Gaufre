package Ai;

import java.io.*;
import java.util.ArrayList;

import Backend.Jeu;
import Backend.Turn;

public class Brain {
    private static Coup nextMove = null;
    private static InputStream is;
    private static Turn tempPlayer = Jeu.tour;

    public static Coup nextMove() {
        tempPlayer = Jeu.tour;
        try {
            System.setOut(new PrintStream(new File("tries.txt")));
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        if (Jeu.gameOver(Jeu.terrain())) {
            nextMove = new Coup(0, 0);
            return nextMove;
        }
        else{
            if(!Jeu.isFree(1, 0)){
                Jeu.occupe(0, 1);
                return new Coup(0,0);
            }
            else if(!Jeu.isFree(0, 1)){
                Jeu.occupe(1, 0);
                return new Coup(0,0);
            }
            
        }
        minimax(Jeu.copyOfTerrain(), 10, Integer.MIN_VALUE, Integer.MAX_VALUE, true);
        System.out.println("AI is going to play : " + nextMove);
        return nextMove;
    }

    public static int minimax(boolean[][] terrain, int depth, int alpha, int beta, boolean maximizing) {
        System.out.println("Terrain : ");

        Jeu.affiche(terrain);
        if (Jeu.gameOver(terrain)) {
            if (tempPlayer != Turn.Player1) {
                System.out.println("AI HAS WON.");

                return -10;
            } else {
                System.out.println("Player HAS WON.");

                return 10;
            }
        }

        if (maximizing) {
            int bestScore = Integer.MIN_VALUE;
            ArrayList<Coup> coups = Jeu.coupsPossibles(terrain);

            System.out.println("COUPS MAX : " + coups);
            if(Jeu.gameOver(terrain)){
               // nextMove = coup;
                return 0;
            }
            for (Coup coup : coups) {
                Jeu.affiche(terrain);
                boolean[][] xterrain = Jeu.copyOfTerrain(terrain);
                terrain = futureTerrain(terrain, coup);
                int score = minimax(terrain, depth - 1, alpha, beta, false);
                System.out.println("SCORE MAX : " + score);
               
                alpha = Math.max(alpha, score);
                if (score >= bestScore) {
                    bestScore = score;
                    
                        nextMove = coup;
                }
                if (beta <= alpha) {
                    break;
                }
            }
            return bestScore;
        } else {
            int bestScore = Integer.MAX_VALUE;
            ArrayList<Coup> coups = Jeu.coupsPossibles(terrain);

            System.out.println("COUPS MIN : " + coups);
            if(Jeu.gameOver(terrain)){
                // nextMove = coup;
                 return 0;
             }
            for (Coup coup : coups) {
                Jeu.affiche(terrain);
                boolean[][] xterrain = Jeu.copyOfTerrain(terrain);
                terrain = futureTerrain(terrain, coup);
                int score = minimax(terrain, depth - 1, alpha, beta, true);
                System.out.println("SCORE MIN : " + score);
                
                beta = Math.min(beta, score);
                if (score <= bestScore) {
                    bestScore = score;
                        nextMove = coup;
                }

                if (beta <= alpha) {
                    break;
                }
            }
            return bestScore;
        }
    }

    /*
     * public static int maximize(boolean[][] terrain) { int bestScore =
     * Integer.MIN_VALUE; ArrayList<Coup> coups = Jeu.coupsPossibles(terrain); for
     * (Coup coup : coups) { boolean[][] xterrain = futureTerrain(terrain,coup);
     * 
     * System.out.println("We will try coup : "+coup);
     * 
     * int score = minimax(xterrain, false); if(score>=bestScore){ nextMove = coup;
     * bestScore = score; System.out.println("score after MAX : "+bestScore); return
     * bestScore; }
     * 
     * 
     * } return bestScore; }
     * 
     * public static int minimize(boolean[][] terrain) { int bestScore =
     * Integer.MAX_VALUE; ArrayList<Coup> coups = Jeu.coupsPossibles(terrain); for
     * (Coup coup : coups) { boolean[][] xterrain = futureTerrain(terrain,coup);
     * System.out.println("We will try coup : "+coup);
     * 
     * 
     * int score = minimax(xterrain, true); if(score<=bestScore){ nextMove = coup;
     * bestScore = score; System.out.println("score after MIN : "+bestScore); return
     * bestScore; }
     * 
     * } return bestScore; }
     */
    private static boolean[][] futureTerrain(boolean[][] terrain, Coup coup) {
        // La fonction occupe(x,y) change la classe Jeu directement, il faut
        // re-implementer le mechanisme içi.
        for (int i = coup.i; i < Jeu.longueur(); i++) {
            for (int j = coup.j; j < Jeu.largeur(); j++) {
                terrain[i][j] = false;
            }
        }
        if (tempPlayer == Turn.Player1) {
            tempPlayer = Turn.Player2;
        } else {
            tempPlayer = Turn.Player1;
        }
        return terrain;
    }
}