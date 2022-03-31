package com.company;

import java.awt.*;

public class Main {

    public static Color color1 = new Color(125, 110, 90);
    public static Color color2 = new Color(156, 150, 143);
    public static Color color3 = new Color(186, 91, 91);
    public static Color color4 = new Color(07, 138, 99);
    public static Color color5 = new Color(158, 181, 152);

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
        new MyFrame();
    }
}
