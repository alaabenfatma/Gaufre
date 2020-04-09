package Gaufre;

import org.junit.Test;

import Backend.Jeu;

import static org.junit.Assert.*;

import java.util.ArrayList;

/**
 * Unit test for simple App.
 */
public class AppTest {
    /**
     * Rigorous Test.
     */
  
    
     @Test
    public void testApp() {
        testisFree();
    }

    @Test
    public  void testisFree(){
        Jeu.init();
        int x = 3;
        int y = 1;
        assertTrue(Jeu.isFree(x, y));
        
    }

}
