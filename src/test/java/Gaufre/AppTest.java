package Gaufre;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import Backend.Jeu;
import Backend.Turn;

import static org.junit.Assert.*;

/**
 * Unit test for simple App.
 */
public class AppTest {
    /**
     * Rigorous Test.
     */

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
        Jeu.init();
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
        int x=5;
        int y=5;
        Jeu.occupe(x, y);
        assertFalse(Jeu.isFree(x, y));
    }
    @Test
    public void testinit_tab_all_true(){
        Jeu.init();
        Boolean test = true;
        
        for(int y = 0; y < Jeu.longueur(); y++){
            for(int x = 0; x < Jeu.largeur(); x++){
                test = test & Jeu.isFree(y,x);
            }
        }
        assertTrue(test);
    }

    @Test
    public void test_joueur_1_init(){
        Jeu.init();
        assertEquals(Turn.Player1,Jeu.tour);
    }
    
    @Test
    public void test_joueur_2_apres_J1(){
        Jeu.init();
        Jeu.occupe(5, 5);
        assertEquals(Turn.Player2,Jeu.tour);
    }

}