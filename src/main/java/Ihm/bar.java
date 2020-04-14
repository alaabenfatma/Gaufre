package Ihm;

import java.awt.Dimension;

import javax.swing.JLabel;

public class bar extends JLabel {

    public bar(String msg) {
        super();
        super.setPreferredSize(new Dimension(100, 16));
        setMessage(msg);
    }

    public void setMessage(String message) {
        setText(" "+message);        
    }        
}