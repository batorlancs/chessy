package com.company;

import java.util.LinkedList;

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
    public Knight whiteKnights[] = new Knight[2];
    public Knight blackKnights[] = new Knight[2];
    public Bishop whiteBishops[] = new Bishop[2];
    public Bishop blackBishops[] = new Bishop[2];
    public Queen whiteQueen;
    public Queen blackQueen;
    public King whiteKing;
    public King blackKing;

    //------ promotion ------//
    public LinkedList<Rook> whiteRookPromo = new LinkedList<>();
    public LinkedList<Rook> blackRookPromo = new LinkedList<>();
    public LinkedList<Knight> whiteKnightPromo = new LinkedList<>();
    public LinkedList<Knight> blackKnightPromo = new LinkedList<>();
    public LinkedList<Bishop> whiteBishopPromo = new LinkedList<>();
    public LinkedList<Bishop> blackBishopPromo = new LinkedList<>();
    public LinkedList<Queen> whiteQueenPromo = new LinkedList<>();
    public LinkedList<Queen> blackQueenPromo = new LinkedList<>();
    //-----------------------//


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
        int f = 1;
        for (int i = 0; i < 2; i++) {
            whiteRooks[i] = new Rook(i*7,0, true);
            blackRooks[i] = new Rook(i*7, 7, false);
            whiteKnights[i] = new Knight(i*7+f, 0, true);
            blackKnights[i] = new Knight(i*7+f, 7, false);
            whiteBishops[i] = new Bishop(i*7+f*2, 0, true);
            blackBishops[i] = new Bishop(i*7+f*2, 7, false);
            f = -1;
        }
        whiteQueen = new Queen(3, 0, true);
        blackQueen = new Queen(3, 7, false);
        whiteKing = new King(4, 0, true);
        blackKing = new King(4, 7, false);
    }

    public void createPromotion(int pos, int imageNum, boolean isWhite) {
        getPiece(pos).killPiece();
        int x = com.company.Main.calcPosx(pos);
        int y = com.company.Main.calcPosy(pos);
        switch (imageNum) {
            case 1:
                if (isWhite) whiteRookPromo.add(new Rook(x, y, true));
                else blackRookPromo.add(new Rook(x, y, false));
                break;
            case 2:
                if (isWhite) whiteKnightPromo.add(new Knight(x, y, true));
                else blackKnightPromo.add(new Knight(x, y, false));
                break;
            case 3:
                if (isWhite) whiteBishopPromo.add(new Bishop(x, y, true));
                else blackBishopPromo.add(new Bishop(x, y, false));
                break;
            case 4:
                if (isWhite) whiteQueenPromo.add(new Queen(x, y, true));
                else blackQueenPromo.add(new Queen(x, y, false));
                break;
        }
        refreshPieceCheck();
        refreshColorCheck();
    }

    public void refreshPieceCheck() {
        for (int i = 0; i < 64; i++) pieceCheck[i] = -1;
        //pawns
        for (int i = 0; i < 8; i++) {
            if (whitePawns[i].isAlive())
                pieceCheck[whitePawns[i].getPos()] = whitePawns[i].getImageNum();
            if (blackPawns[i].isAlive())
                pieceCheck[blackPawns[i].getPos()] = blackPawns[i].getImageNum();
        }
        //rooks, knights, bishops
        for (int i = 0; i < 2; i++) {
            if (whiteRooks[i].isAlive())
                pieceCheck[whiteRooks[i].getPos()] = whiteRooks[i].getImageNum();
            if (blackRooks[i].isAlive())
                pieceCheck[blackRooks[i].getPos()] = blackRooks[i].getImageNum();
            if (whiteKnights[i].isAlive())
                pieceCheck[whiteKnights[i].getPos()] = whiteKnights[i].getImageNum();
            if (blackKnights[i].isAlive())
                pieceCheck[blackKnights[i].getPos()] = blackKnights[i].getImageNum();
            if (whiteBishops[i].isAlive())
                pieceCheck[whiteBishops[i].getPos()] = whiteBishops[i].getImageNum();
            if (blackBishops[i].isAlive())
                pieceCheck[blackBishops[i].getPos()] = blackBishops[i].getImageNum();
        }
        //queen
        if (whiteQueen.isAlive())
            pieceCheck[whiteQueen.getPos()] = whiteQueen.getImageNum();
        if (blackQueen.isAlive())
            pieceCheck[blackQueen.getPos()] = blackQueen.getImageNum();
        //king
        if (whiteKing.isAlive())
            pieceCheck[whiteKing.getPos()] = whiteKing.getImageNum();
        if (blackKing.isAlive())
            pieceCheck[blackKing.getPos()] = blackKing.getImageNum();
        //promotions
        for (Rook a: whiteRookPromo) if (a.isAlive()) pieceCheck[a.getPos()] = a.getImageNum();
        for (Rook a: blackRookPromo) if (a.isAlive()) pieceCheck[a.getPos()] = a.getImageNum();
        for (Knight a: whiteKnightPromo) if (a.isAlive()) pieceCheck[a.getPos()] = a.getImageNum();
        for (Knight a: blackKnightPromo) if (a.isAlive()) pieceCheck[a.getPos()] = a.getImageNum();
        for (Bishop a: whiteBishopPromo) if (a.isAlive()) pieceCheck[a.getPos()] = a.getImageNum();
        for (Bishop a: blackBishopPromo) if (a.isAlive()) pieceCheck[a.getPos()] = a.getImageNum();
        for (Queen a: whiteQueenPromo) if (a.isAlive()) pieceCheck[a.getPos()] = a.getImageNum();
        for (Queen a: blackQueenPromo) if (a.isAlive()) pieceCheck[a.getPos()] = a.getImageNum();
    }

    public void refreshColorCheck() {
        for (int i = 0; i < 64; i++) colorCheck[i] = 0;
        //pawns
        for (int i = 0; i < 8; i++) {
            if (whitePawns[i].isAlive())
                colorCheck[whitePawns[i].getPos()] = 1;
            if (blackPawns[i].isAlive())
                colorCheck[blackPawns[i].getPos()] = 2;
        }
        //rooks, knights, bishops
        for (int i = 0; i < 2; i++) {
            if (whiteRooks[i].isAlive())
                colorCheck[whiteRooks[i].getPos()] = 1;
            if (blackRooks[i].isAlive())
                colorCheck[blackRooks[i].getPos()] = 2;
            if (whiteKnights[i].isAlive())
                colorCheck[whiteKnights[i].getPos()] = 1;
            if (blackKnights[i].isAlive())
                colorCheck[blackKnights[i].getPos()] = 2;
            if (whiteBishops[i].isAlive())
                colorCheck[whiteBishops[i].getPos()] = 1;
            if (blackBishops[i].isAlive())
                colorCheck[blackBishops[i].getPos()] = 2;
        }
        //queen
        if (whiteQueen.isAlive())
            colorCheck[whiteQueen.getPos()] = 1;
        if (blackQueen.isAlive())
            colorCheck[blackQueen.getPos()] = 2;
        //king
        if (whiteKing.isAlive())
            colorCheck[whiteKing.getPos()] = 1;
        if (blackKing.isAlive())
            colorCheck[blackKing.getPos()] = 2;
        //promotions
        for (Rook a: whiteRookPromo) if (a.isAlive()) colorCheck[a.getPos()] = 1;
        for (Rook a: blackRookPromo) if (a.isAlive()) colorCheck[a.getPos()] = 2;
        for (Knight a: whiteKnightPromo) if (a.isAlive()) colorCheck[a.getPos()] = 1;
        for (Knight a: blackKnightPromo) if (a.isAlive()) colorCheck[a.getPos()] = 2;
        for (Bishop a: whiteBishopPromo) if (a.isAlive()) colorCheck[a.getPos()] = 1;
        for (Bishop a: blackBishopPromo) if (a.isAlive()) colorCheck[a.getPos()] = 2;
        for (Queen a: whiteQueenPromo) if (a.isAlive()) colorCheck[a.getPos()] = 1;
        for (Queen a: blackQueenPromo) if (a.isAlive()) colorCheck[a.getPos()] = 2;
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
        //pawns
        for (int i = 0; i< 8 ; i++) {
            if (whitePawns[i].getPos() == index && whitePawns[i].isAlive()) return whitePawns[i];
            if (blackPawns[i].getPos() == index && blackPawns[i].isAlive()) return blackPawns[i];
        }
        //rooks, knights, bishops
        for (int i = 0; i < 2; i++) {
            if (whiteRooks[i].getPos() == index && whiteRooks[i].isAlive()) return whiteRooks[i];
            if (blackRooks[i].getPos() == index && blackRooks[i].isAlive()) return blackRooks[i];
            if (whiteKnights[i].getPos() == index && whiteKnights[i].isAlive()) return whiteKnights[i];
            if (blackKnights[i].getPos() == index && blackKnights[i].isAlive()) return blackKnights[i];
            if (whiteBishops[i].getPos() == index && whiteBishops[i].isAlive()) return whiteBishops[i];
            if (blackBishops[i].getPos() == index && blackBishops[i].isAlive()) return blackBishops[i];
        }
        //queens
        if (whiteQueen.getPos() == index && whiteQueen.isAlive()) return whiteQueen;
        if (blackQueen.getPos() == index && blackQueen.isAlive()) return blackQueen;
        if (whiteKing.getPos() == index && whiteKing.isAlive()) return whiteKing;
        if (blackKing.getPos() == index && blackKing.isAlive()) return blackKing;
        //promotions
        for (Rook a: whiteRookPromo) if (a.getPos() == index && a.isAlive()) return a;
        for (Rook a: blackRookPromo) if (a.getPos() == index && a.isAlive()) return a;
        for (Knight a: whiteKnightPromo) if (a.getPos() == index && a.isAlive()) return a;
        for (Knight a: blackKnightPromo) if (a.getPos() == index && a.isAlive()) return a;
        for (Bishop a: whiteBishopPromo) if (a.getPos() == index && a.isAlive()) return a;
        for (Bishop a: blackBishopPromo) if (a.getPos() == index && a.isAlive()) return a;
        for (Queen a: whiteQueenPromo) if (a.getPos() == index && a.isAlive()) return a;
        for (Queen a: blackQueenPromo) if (a.getPos() == index && a.isAlive()) return a;
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
        //pawns
        for (int i = 0; i < 8; i++) {
            whitePawns[i].possibleSteps(this);
            blackPawns[i].possibleSteps(this);
        }
        //rooks, knights, bishops
        for (int i = 0; i < 2; i++) {
            whiteRooks[i].possibleSteps(this);
            blackRooks[i].possibleSteps(this);
            whiteKnights[i].possibleSteps(this);
            blackKnights[i].possibleSteps(this);
            whiteBishops[i].possibleSteps(this);
            blackBishops[i].possibleSteps(this);
        }
        //queen
        whiteQueen.possibleSteps(this);
        blackQueen.possibleSteps(this);
        //king
        whiteKing.possibleSteps(this);
        blackKing.possibleSteps(this);
        //promotions
        for (Rook a: whiteRookPromo) a.possibleSteps(this);
        for (Rook a: blackRookPromo) a.possibleSteps(this);
        for (Knight a: whiteKnightPromo) a.possibleSteps(this);
        for (Knight a: blackKnightPromo) a.possibleSteps(this);
        for (Bishop a: whiteBishopPromo) a.possibleSteps(this);
        for (Bishop a: blackBishopPromo) a.possibleSteps(this);
        for (Queen a: whiteQueenPromo) a.possibleSteps(this);
        for (Queen a: blackQueenPromo) a.possibleSteps(this);
    }

    public int detectGameOver() {
        // 0: game is not over
        // 1: white won
        // 2: black won
        if (!whiteKing.isAlive()) return 2;
        if (!blackKing.isAlive()) return 1;
        return 0;
    }
}
