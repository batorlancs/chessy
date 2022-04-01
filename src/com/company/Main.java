package com.company;

import java.awt.*;

public class Main {

    public static Color color1 = new Color(140, 127, 109);
    public static Color color2 = new Color(181, 175, 168);
    public static Color color4 = new Color(140, 135, 109);
    public static Color color5 = new Color(179, 172, 145);
    public static Color color6 = new Color(140, 135, 108);
    public static Color color7 = new Color(179, 172, 144);
    public static Font font1 = new Font("Calibri", Font.BOLD, 20);
    public static Font font2 = new Font("Calibiri", Font.BOLD, 15);
    public static Color fontcolor1 = new Color(95, 95, 95);
    public static Color fontcolor2 = new Color(237, 237, 237);
//    public static Color color6 = new Color(136, 140, 109);
//    public static Color color7 = new Color(171, 179, 145);
    public static MyFrame frame;

    public static int calcPos(int x, int y) {
        return (x + (7-y) * 8);
    }

    public static int calcPosx(int pos) {
        return (pos % 8);
    }

    public static int calcPosy(int pos) {
        return (7 - (pos / 8));
    }

    public static void main(String[] args) {
        frame = new MyFrame();
    }
}
