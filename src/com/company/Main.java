package com.company;

import java.awt.*;

public class Main {

    // colors and fonts //////////
    public static Color color1 = new Color(140, 127, 109);
    public static Color color2 = new Color(181, 175, 168);
    public static Color color3 = new Color(153, 143, 130);
    public static Color color4 = new Color(140, 135, 109);
    public static Color color5 = new Color(179, 172, 145);
    public static Color color6 = new Color(134, 140, 109);
    public static Color color7 = new Color(175, 179, 145);
    public static Font font2 = new Font("Calibiri", Font.BOLD, 15);
    public static Color fontcolor1 = new Color(95, 95, 95);
    public static Color fontcolor2 = new Color(237, 237, 237);
    //////////////////////////////

    public static MyFrame frame;

    // calculates the basic position based on x and y theoretical positions
    public static int calcPos(int x, int y) {
        return (x + (7-y) * 8);
    }

    // calculates x theoretical position based on button/array pos (basic position)
    public static int calcPosx(int pos) {
        return (pos % 8);
    }

    // calculates y theoretical position based on button/array pos (basic position)
    public static int calcPosy(int pos) {
        return (7 - (pos / 8));
    }

    public static int calcPosMouse() {
        int x = (int) MouseInfo.getPointerInfo().getLocation().getX() - (int)frame.getLocationOnScreen().getX();
        int y = (int) MouseInfo.getPointerInfo().getLocation().getY() - (int)frame.getLocationOnScreen().getY() - 28;
        x /= 80;
        y = 7 - (y/80);
        return calcPos(x,  y);
    }

    // MAIN FUNCTION ///////////
    public static void main(String[] args) {
        frame = new MyFrame();
    }
    ///////////////////////////
}
