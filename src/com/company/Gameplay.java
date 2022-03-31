package com.company;

public class Gameplay {

    private int[] pieceCheck = new int[64];
    private int[] colorCheck = new int[64];
    private boolean whiteTurn;
    private boolean isGameOver;
    // PIECES
    public Pawn whitePawns[] = new Pawn[8];
    public Pawn blackPawns[] = new Pawn[8];
    public Rook whiteRooks[] = new Rook[2];
    public Rook blackRooks[] = new Rook[2];

    public Gameplay() {
        initGameplay();
    }

    private void initGameplay() {
        createPieces();
        refreshPieceCheck();
        refreshColorCheck();
        whiteTurn = true;
        isGameOver = false;
        //display stuff
        displayInt64Array(pieceCheck);
        displayInt64Array(colorCheck);
    }

    private void createPieces() {
        for (int i = 0; i < 8; i++) {
            whitePawns[i] = new Pawn(i, 1, true);
            blackPawns[i] = new Pawn(i, 6, false);
            //System.out.println("white: " + whitePawns[i].getPos() + ", black: " + blackPawns[i].getPos());
        }
        for (int i = 0; i < 2; i++) {
            whiteRooks[i] = new Rook(i*7,0, true);
            blackRooks[i] = new Rook(i*7, 7, false);
        }
    }

    public void refreshPieceCheck() {
        for (int i = 0; i < 64; i++) pieceCheck[i] = -1;
        for (int i = 0; i < 8; i++) {
            if (whitePawns[i].isAlive())
                pieceCheck[whitePawns[i].getPos()] = whitePawns[i].getImageNum();
            if (blackPawns[i].isAlive())
                pieceCheck[blackPawns[i].getPos()] = blackPawns[i].getImageNum();
        }
        for (int i = 0; i < 2; i++) {
            if (whiteRooks[i].isAlive())
                pieceCheck[whiteRooks[i].getPos()] = whiteRooks[i].getImageNum();
            if (blackRooks[i].isAlive())
                pieceCheck[blackRooks[i].getPos()] = blackRooks[i].getImageNum();
        }
    }

    public void refreshColorCheck() {
        for (int i = 0; i < 64; i++) colorCheck[i] = 0;
        for (int i = 0; i < 8; i++) {
            if (whitePawns[i].isAlive())
                colorCheck[whitePawns[i].getPos()] = 1;
            if (blackPawns[i].isAlive())
                colorCheck[blackPawns[i].getPos()] = 2;
        }
        for (int i = 0; i < 2; i++) {
            if (whiteRooks[i].isAlive())
                colorCheck[whiteRooks[i].getPos()] = 1;
            if (blackRooks[i].isAlive())
                colorCheck[blackRooks[i].getPos()] = 2;
        }
    }

    // check if clicked button is appropriate
    public boolean checkIfRightClick(int index) {
        int check;
        if (whiteTurn) check = 1;
        else check = 2;
        return check == colorCheck[index];
    }

    // gives turn to the next player
    public void switchTurn() {
        whiteTurn = !whiteTurn;
    }

    public void displayBoard() {
        displayInt64Array(pieceCheck);
        displayInt64Array(colorCheck);
    }

    private boolean isInRange(int index) {
        return (index >=0 && index<=63);
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

    public Piece getPiece(int index) {
        for (int i = 0; i< 8 ; i++) {
            if (whitePawns[i].getPos() == index && whitePawns[i].isAlive()) return whitePawns[i];
            if (blackPawns[i].getPos() == index && blackPawns[i].isAlive()) return blackPawns[i];
        }
        for (int i = 0; i < 2; i++) {
            if (whiteRooks[i].getPos() == index && whiteRooks[i].isAlive()) return whiteRooks[i];
            if (blackRooks[i].getPos() == index && blackRooks[i].isAlive()) return blackRooks[i];
        }
        return whitePawns[0];
    }

    public boolean isEnemyPieceThere(int index) {
        if (!isInRange(index)) return false;
        if (whiteTurn && isPieceThere(index) && !isPieceWhite(index)) return true;
        if (!whiteTurn && isPieceThere(index) && isPieceWhite(index)) return true;
        return false;
    }

    public boolean isPieceThere(int index) {
        if (!isInRange(index)) return false;
        return pieceCheck[index] != -1;
    }

    public int getPieceWhich(int index) {
        return pieceCheck[index];
    }

    public boolean isPieceWhite(int index) {
        return colorCheck[index] == 1;
    }

    public void updateSteps() {
        for (int i = 0; i < 8; i++) {
            whitePawns[i].possibleSteps(this);
            blackPawns[i].possibleSteps(this);
        }
        for (int i = 0; i < 2; i++) {
            whiteRooks[i].possibleSteps(this);
            blackRooks[i].possibleSteps(this);
        }
    }
}
