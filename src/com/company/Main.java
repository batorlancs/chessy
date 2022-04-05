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
    public static Color[] colorTheme = new Color[7];
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

    public static boolean isPosInRange(int pos) {
        if (pos < 0 || pos > 63) return false;
        return true;
    }

    public static void changeTheme(int which) {
        if (which == 0) {
            color1 = new Color(140, 127, 109);
            color2 = new Color(181, 175, 168);
            color3 = new Color(153, 143, 130);
            color4 = new Color(140, 135, 109);
            color5 = new Color(179, 172, 145);
            color6 = new Color(134, 140, 109);
            color7 = new Color(175, 179, 145);
        } else if (which == 1) {
            color1 = new Color(120, 140, 109);
            color2 = new Color(173, 181, 168);
            color3 = new Color(154, 176, 143);
            color4 = new Color(140, 158, 122);
            color5 = new Color(186, 199, 171);
            color6 = new Color(156, 156, 120);
            color7 = new Color(191, 191, 161);
        } else if (which == 2) {
            color1 = new Color(109, 125, 140);
            color2 = new Color(168, 175, 181);
            color3 = new Color(140, 154, 168);
            color4 = new Color(121, 133, 156);
            color5 = new Color(183, 187, 196);
            color6 = new Color(109, 111, 140);
            color7 = new Color(146, 148, 173);
        } else if (which == 3) {
            color1 = new Color(156, 90, 90);
            color2 = new Color(186, 153, 153);
            color3 = new Color(191, 117, 117);
            color4 = new Color(179, 109, 104);
            color5 = new Color(194, 149, 143);
            color6 = new Color(120, 140, 109);
            color7 = new Color(173, 181, 168);
        }
    }

    public static void updateColorTheme() {
        colorTheme[0] = color1;
        colorTheme[1] = color2;
        colorTheme[2] = color3;
        colorTheme[3] = color4;
        colorTheme[4] = color5;
        colorTheme[5] = color6;
        colorTheme[6] = color7;
    }

    // MAIN FUNCTION ///////////
    public static void main(String[] args) {
        updateColorTheme();
        frame = new MyFrame();
    }
    ///////////////////////////
}
