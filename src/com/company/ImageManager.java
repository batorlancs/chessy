package com.company;

import javax.swing.*;
import java.awt.*;

public class ImageManager {

    private ImageIcon[] white = new ImageIcon[6];
    private ImageIcon[] black = new ImageIcon[6];
    private final int imageScale = 85;

    public ImageManager() {
        init();
    }

    private void init() {
        // read in white sticks
        Image temp;
        for (int i = 0; i < 6; i++) {
            //white
            white[i] = new ImageIcon(getClass().getResource("/images/sticks/feher" + Integer.toString(i) + ".png"));
            temp = white[i].getImage().getScaledInstance(imageScale, imageScale, Image.SCALE_SMOOTH);
            white[i] = new ImageIcon(temp);
            //black
            black[i] = new ImageIcon(getClass().getResource("/images/sticks/fekete" + Integer.toString(i) + ".png"));
            temp = black[i].getImage().getScaledInstance(imageScale, imageScale, Image.SCALE_SMOOTH);
            black[i] = new ImageIcon(temp);
        }
    }

    public ImageIcon getImage(int which, boolean isWhite) {
        if (isWhite) return white[which];
        else return black[which];
    }
}
