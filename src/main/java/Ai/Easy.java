package Ai;

import java.util.Random;

import Backend.Jeu;

public class Easy {

    public static void Play(){
        private static Random r = new Random();
        int i = r.nextInt(Jeu.longueur());
        int j = r.nextInt(Jeu.largeur());
        if(Jeu.isFree(i, j)){
            Jeu.occupe(i, j);
        }
        else{
            Play();
        }
    }
}