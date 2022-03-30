package com.company;

public class Gameplay {

    private int[] pieceCheck = new int[64];
    private int[] colorCheck = new int[64];
    private boolean whiteTurn;
    private boolean isGameOver;

    public Gameplay() {
        initGameplay();
    }

    private void initGameplay() {
        // init colorCheck
        for (int i = 0; i < 16;  i++)
            colorCheck[i] = 2;
        for (int i = 16; i < 48; i++)
            colorCheck[i] = 0;
        for (int i = 48; i < 64; i++)
            colorCheck[i] = 1;
        // init pieceCheck
        for (int i = 1; i < 6; i++)
            pieceCheck[i-1] = i;
        for (int i = 3; i > 0; i--)
            pieceCheck[8-i] = i;
        for (int i = 0; i < 8; i++)
            pieceCheck[i+8] = 0;
        for (int i = 16; i < 48; i++)
            pieceCheck[i] = -1;
        for (int i = 48; i < 56; i++)
            pieceCheck[i] = 0;
        for (int i = 1; i < 6; i++)
            pieceCheck[i+55] = i;
        for (int i = 3; i > 0; i--)
            pieceCheck[64-i] = i;
        //display stuff
        displayInt64Array(pieceCheck);
        displayInt64Array(colorCheck);
    }

    // for debugging
    public void displayInt64Array(int[] arr) {
        System.out.print("-----------------------------------------");
        for (int i = 0; i < 64; i++) {
            if (i % 8 == 0) System.out.println();

            StringBuilder s = new StringBuilder(String.valueOf(arr[i]));
            switch (s.length()) {
                case 0: s.append(" x "); break;
                case 1: s.append("  "); break;
                case 2: s.append(" "); break;
            }
            System.out.print(s);
        }
        System.out.println("\n-----------------------------------------");
    }

    public boolean isPieceThere(int index) {
        if (pieceCheck[index] == -1) return false;
        return true;
    }

    public int getPieceWhich(int index) {
        return pieceCheck[index];
    }

    public boolean isPieceWhite(int index) {
        if (colorCheck[index] == 1) return true;
        return false;
    }
}
