package Ihm;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.*;

public class Cell extends JComponent {
    Image _img;
    int x, y, w, h;

    public Cell(Image img, int _x, int _y, int _w, int _h) {
        this._img = img;
        this.x = _x;
        this.y = _y;
        this.w = _w;
        this.h = _h;
        this.setVisible(true);
        this.addMouseListener(new mouseListener(this));
    }

    protected void paintComponent(Graphics g) {
        Graphics2D drawable = (Graphics2D) g;
        drawable.drawImage(_img, 0, 0, w, h, this);
    }
}