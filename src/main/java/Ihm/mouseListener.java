package Ihm;

import javax.swing.*;

import Backend.Jeu;
import Backend.Turn;

import java.awt.*;
import java.awt.event.*;

public class mouseListener implements MouseListener {
    UI _ui;

    mouseListener(UI ui) {
        _ui = ui;

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int i,j;
        i = (int)Math.floor(e.getY() /_ui.cellSize.height);
        j = (int)Math.floor(e.getX() /_ui.cellSize.width);
        System.out.printf("Mouse position : (%d,%d)\nGame position : (%d,%d)\n",e.getX(),e.getY(),i,j);
        if ((i == 0) && (j == 0)) {
            // on a perdu
            if (Jeu.tour() == Turn.Player2) {
                msgBox.MessageBox("Player 2 a perdu","Gameover");
            } else {
                msgBox.MessageBox("Player 1 a perdu","Gameover");
            }
            
        } else {
            Jeu.occupe(i, j);
            if (Jeu.tour() == Turn.Player2) {
                
                _ui.player.setText("Player 2");
            } else {
                _ui.player.setText("Player 1");
            }
        }
        _ui.repaint();
    }

    @Override
    public void mousePressed(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseEntered(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseExited(MouseEvent e) {
        // TODO Auto-generated method stub

    }

}