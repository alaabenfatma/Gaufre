package Ihm;

import javax.swing.*;

import Backend.GameMode;
import Backend.Jeu;
import Backend.Turn;

import java.awt.*;
import java.awt.event.*;

public class keyListener implements KeyListener {

    @Override
    public void keyTyped(KeyEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.isControlDown()&& (e.getKeyCode() == KeyEvent.VK_Z)) {
            Jeu.CTRL_Z();
            if(Jeu.mode_JEU== GameMode.PVA){
                Jeu.CTRL_Z();
            }
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {
        // TODO Auto-generated method stub

    }
  

}