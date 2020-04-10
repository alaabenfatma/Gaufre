package Ihm;

import javax.swing.*;

import Ai.Ai;
import Backend.GameMode;
import Backend.Jeu;
import Backend.Turn;

import java.awt.*;
import java.awt.event.*;

public class mouseListener implements MouseListener {
    Cell _cell;
    boolean firstMove = false;

    mouseListener(Cell cell) {
        this._cell = cell;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int i = _cell.x;
        int j = _cell.y;
        System.out.printf("Mouse position : (%d,%d)\nGame position : (%d,%d)\n", e.getX(), e.getY(), i, j);
        Jeu.occupe(i, j);
        if (Jeu.mode_JEU == GameMode.PVA && Jeu.GameOver == false)
            Ai.IAgagnante();

        System.out.println("AI is playing.");
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
        if (firstMove) {
            return;
        }
        firstMove = true;
        if (Jeu.mode_JEU == GameMode.PVA && Jeu.GameOver == false)
            Ai.IAgagnante();
    }

    @Override
    public void mouseExited(MouseEvent e) {
        // TODO Auto-generated method stub

    }

}