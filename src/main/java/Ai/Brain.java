package Ai;

import java.io.*;
import java.util.ArrayList;

import Backend.Jeu;
import Backend.Turn;

public class Brain {
    private static Coup nextMove = null;
    private static InputStream is;

    public static Coup nextMove() {
        try {
            System.setOut(new PrintStream(new File("tries.txt")));
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
            if(Jeu.gameOver(Jeu.terrain())){
                nextMove = new Coup(0, 0);
                return nextMove;
             }
            minimax(Jeu.copyOfTerrain(), true);
        System.out.println("AI is going to play : " + nextMove);
        return nextMove;
    }

    public static int minimax(boolean[][] terrain, boolean maximizing) {
        if (Jeu.gameOver(terrain)) {
            if( Jeu.tour == Turn.Player1){
                System.out.println("This scenario will make player 1 win.");
                return -1;
            }
            else{
                System.out.println("This scenario will make AI win.");
                return +1;
            }
            
        }
        if (maximizing) {
            System.out.println("MAX : ");
            Jeu.affiche(terrain);
            return maximize(terrain);
        } else {
            System.out.println("MIN : ");
            Jeu.affiche(terrain);
            return minimize(terrain);
        }
    }

    public static int maximize(boolean[][] terrain) {
        int bestScore = Integer.MIN_VALUE;
        ArrayList<Coup> coups = Jeu.coupsPossibles(terrain);
        for (Coup coup : coups) {
            boolean[][] xterrain = futureTerrain(terrain,coup);

            System.out.println("We will try coup : "+coup);
           
            int score =  minimax(xterrain, false);
            if(score>=bestScore){
                nextMove = coup;
                bestScore = score;
                System.out.println("score after MAX : "+bestScore);
                return bestScore;
            }

            
        }
        return bestScore;
    }

    public static int minimize(boolean[][] terrain) {
        int bestScore = Integer.MAX_VALUE;
        ArrayList<Coup> coups = Jeu.coupsPossibles(terrain);
        for (Coup coup : coups) {
            boolean[][] xterrain = futureTerrain(terrain,coup);
            System.out.println("We will try coup : "+coup);
            
        
            int score =  minimax(xterrain, true);
            if(score<=bestScore){
                nextMove = coup;
                bestScore = score;
                System.out.println("score after MIN : "+bestScore);
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