package Gaufre;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import Ai.Coup;
import Backend.Backend;
import Backend.Jeu;
import Backend.Turn;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * Unit test for simple App.
 */
public class AppTest {
    private static int x_test = 10, y_test = 10;
    @Test
    public void test_initialisation1_1(){
        Jeu.init();
        assertEquals(10,Jeu.longueur());
    }


    @Test
    public void test_initialisation1_2(){
        Jeu.init();
        assertEquals(10,Jeu.largeur());
    }


    @Test
    public void test_initialisation1_3(){
        Jeu.init();
        assertEquals(Turn.Player1, Jeu.tour);
    }


    @Test
    public void test_initialisation1_4(){
        Jeu.init();
        Boolean test = true;
        for(int y = 0; y < Jeu.longueur(); y++){
            for(int x = 0; x < Jeu.largeur(); x++){
                test = test & Jeu.isFree(y, x);
            }
        }
        assertTrue(test);
    }


    @Test
    public void test_initialisation2_1(){
        Jeu.init(y_test,x_test);
        assertEquals(10,Jeu.longueur());
    }


    @Test
    public void test_initialisation2_2(){
        Jeu.init(y_test,x_test);
        assertEquals(10,Jeu.largeur());
    }


    @Test
    public void test_initialisation2_3(){
        Jeu.init(y_test,x_test);
        assertEquals(Turn.Player1, Jeu.tour);
    }


    @Test
    public void testinit_tab_all_true2(){
        Jeu.init(y_test, x_test);
        
        Boolean test = true;
        for(int y = 0; y < Jeu.longueur(); y++){
            for(int x = 0; x < Jeu.largeur(); x++){
                test = test & Jeu.isFree(y, x);
            }
        }
        assertTrue(test);
    }


    @Test
    public void testisFree() {
        Jeu.init();
        int x = 3;
        int y = 1;
        assertTrue(Jeu.isFree(x, y));
    }


    @Rule
    public ExpectedException thrownException = ExpectedException.none();
    @Test
    public void testisFree2() throws Exception{
        Jeu.init(); //longueur = 10
        int x = 50;
        int y = 1;
        thrownException.expect(RuntimeException.class);
        thrownException.expectMessage("Erreur isBusy: x > longueur du terrain");
        Jeu.isFree(x, y);
    }


    @Test
    public void testisFree3() throws Exception{
        Jeu.init();
        int x = 3;
        int y = 50;
        thrownException.expect(RuntimeException.class);
        thrownException.expectMessage("Erreur isBusy: y > largeur du terrain");
        Jeu.isFree(x, y);
    }

    @Test
    public void testisFree4(){
        Jeu.init();
        int x = 5;
        int y = 5;
        Jeu.occupe(x, y);
        assertFalse(Jeu.isFree(x, y));
    }

    @Test 
    public void testisFree_valeursNegatives_x() throws Exception {
        Jeu.init();
        int x = -3;
        int y = 3;
        thrownException.expect(RuntimeException.class);
        thrownException.expectMessage("x ou y est inférieur à 0");
        Jeu.isFree(x, y);
    }


    @Test 
    public void testisFree_valeursNegatives_y() throws Exception {
        Jeu.init();
        int x = 3;
        int y = -3;
        thrownException.expect(RuntimeException.class);
        thrownException.expectMessage("x ou y est inférieur à 0");
        Jeu.isFree(x, y);
    }
    
    
    @Test 
    public void testoccupe_2arguments_valeursNegatives_x() throws Exception {
        Jeu.init();
        int x = -3;
        int y = 3;
        thrownException.expect(RuntimeException.class);
        thrownException.expectMessage("occupe : x ou y est inférieur à 0");
        Jeu.occupe(x, y);
    }

    @Test 
    public void testoccupe_2arguments_valeursNegatives_y() throws Exception {
        Jeu.init();
        int x = 3;
        int y = -3;
        thrownException.expect(RuntimeException.class);
        thrownException.expectMessage("occupe : x ou y est inférieur à 0");
        Jeu.occupe(x, y);
    }

    @Test 
    public void testoccupe_3arguments_valeursNegatives_x() throws Exception {
        Jeu.init();
        int x = -3;
        int y = 3;
        boolean[][] map = new boolean[10][10];
        thrownException.expect(RuntimeException.class);
        thrownException.expectMessage("occupe : x ou y est inférieur à 0");
        Jeu.occupe(map, x, y);
    }


    @Test 
    public void testoccupe_3arguments_valeursNegatives_y() throws Exception {
        Jeu.init();
        int x = 3;
        int y = -3;
        boolean[][] map = new boolean[10][10];
        thrownException.expect(RuntimeException.class);
        thrownException.expectMessage("occupe : x ou y est inférieur à 0");
        Jeu.occupe(map, x, y);
    }

    @Test 
    public void testoccupe_3arguments_map() throws Exception {
        Jeu.init(5, 5);
        int x = 3;
        int y = 3;
        boolean[][] map = new boolean[1][5];
        thrownException.expect(ArrayIndexOutOfBoundsException.class);
        Jeu.occupe(map, x, y);
    }
    

    @Test
    /*
        On vérifie si c'est au tour de Player 2 de jouer
    */
    public void test_joueur_2_apres_J1(){
        
        Jeu.init();
        Jeu.occupe(5, 5);
        assertEquals(Turn.Player2, Jeu.tour);
    }


    @Test
    public void test_CTRL_Z(){
        final Stack<boolean[][]> Save;
        Jeu.init();
        Jeu.occupe(5, 5);
        Save = Jeu.pile();
        Jeu.occupe(2, 2);
        Jeu.CTRL_Z();
        assertEquals(Save, Jeu.pile());
    }


    @Test
    public void test_joueur_1_CTRL_Z(){
        Turn joueur;
        Jeu.init();
        joueur=Jeu.tour();
        Jeu.occupe(5,5);
        Jeu.CTRL_Z();
        assertEquals(joueur,Jeu.tour());

    }


    @Test
    public void test_joueur_2_CTRL_Z(){
        Turn joueur;
        Jeu.init();
        Jeu.occupe(5, 5);
        joueur = Jeu.tour();
        Jeu.occupe(4, 5);
        Jeu.occupe(4, 3);
        Jeu.CTRL_Z();
        Jeu.CTRL_Z();
        assertEquals(joueur, Jeu.tour());

    }


    @Test
    public void testremainingMovesTrue(){
        int longueur = 10;
        int largeur = 10;
        Jeu.init(longueur, largeur);
        Jeu.occupe(5, 5);
        assertEquals(Jeu.remainingMoves(), 75);
      }


    @Test
    public void testremainingMovesFalse(){
        int longueur = 10;
        int largeur = 10;
        Jeu.init(longueur, largeur);
        Jeu.occupe(5, 5);
        assertNotEquals(Jeu.remainingMoves(), 20);
    }

    /*
    A vérifier avec alaa la fonction gameOver dans Jeu.java ne marche
    pas très bien
    */
    @Test
    public void testgameOverTrue(){
        Jeu.init();
        Jeu.occupe(0, 0);
        assertTrue(Jeu.GameOver);
    }


    @Test
    public void testgameOverFalse(){
        Jeu.init();
        Jeu.occupe(5, 5);
        assertFalse(Jeu.gameOver(Jeu.terrain()));
    }


    @Test
    public void test_fonctionnel_1(){
        Jeu.init();
        boolean[][] matrice;
        matrice=Jeu.terrain();
        List<Coup> J1 = new ArrayList<Coup>();
        List<Coup> J2 = new ArrayList<Coup>();
        Jeu.init();
        Jeu.occupe(2, 1);
        Jeu.occupe(1,5);
        matrice=Jeu.terrain();
        Jeu.init();
        
        J1.add(new Coup(7,8));
        J1.add(new Coup(3,7));
        J1.add(new Coup(5,1));
        J1.add(new Coup(1,5));

        J2.add(new Coup(6,5));
        J2.add(new Coup(7,3));
        J2.add(new Coup(2,1));
        while(!J1.isEmpty() || !J2.isEmpty()){
            if((J1.isEmpty() && Jeu.tour==Turn.Player1) || (J2.isEmpty() && Jeu.tour==Turn.Player2)){
                break;
            }
            if(Jeu.tour==Turn.Player1){
                Backend.jouer(J1.get(0).i,J1.get(0).j);
                J1.remove(0);
            }
            else{
                Backend.jouer(J2.get(0).i,J2.get(0).j);
                J2.remove(0);
            }
        }
        Boolean test=true;
        boolean[][]gaufre=Jeu.terrain();
        for(int j=0;j<Jeu.longueur();j++){
            for(int i=0;i<Jeu.largeur();i++){
                if(matrice[j][i]!=gaufre[j][i]){
                    test=false;
                }
            }
        }
        assertTrue(test);
    }
}