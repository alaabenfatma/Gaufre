package Ai;

import java.util.Random;

import Backend.Jeu;

public class Coupgagnant {

    
    private static Random r = new Random();

    public static void PlayC(){
        

        int i = r.nextInt(Jeu.longueur());
        int j = r.nextInt(Jeu.largeur());

        if(!Jeu.isFree(0,1)){//Coup gagnant
            Jeu.occupe(1,0);
        }else if(!Jeu.isFree(1,0)){//coup gagnant
            Jeu.occupe(0,1);            
        }else if(Jeu.isFree(0,1) && Jeu.isFree(1,0) && i == 0 && j == 1){//Coup perdant a ne pas jouer
            PlayC();
        }else if(Jeu.isFree(0,1) && Jeu.isFree(1,0) && i == 1 && j == 0){//Coup perdant a ne pas jouer
            PlayC();
        }else if(Jeu.isFree(i, j)){
            Jeu.occupe(i, j);
        }
        else{
            PlayC();
        }
    }

}