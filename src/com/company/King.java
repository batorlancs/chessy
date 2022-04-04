package com.company;

import java.util.HashSet;

public class King extends Piece {

    /////////////////////////////////////////////////////////////////////////////////////////
    // CONSTRUCTOR
    /////////////////////////////////////////////////////////////////////////////////////////
    public King(int x, int y, boolean white) {
        initKing(x, y, white);
    }

    private void initKing(int x, int y, boolean white) {
        setFirstStepDone(false);
        setPosx(x);
        setPosy(y);
        setAlive(true);
        setImageNum(5);
        setWhite(white);
    }
    /////////////////////////////////////////////////////////////////////////////////////////

    // --------------------------------------------------------------------------------------
    // calculate the possible steps for this piece
    // --------------------------------------------------------------------------------------
    public void possibleSteps(Gameplay gp) {
        HashSet<Integer> hset = new HashSet<Integer>();

        calcStepsDirection(gp, hset, 1, 1); //up right
        calcStepsDirection(gp, hset, -1, 1); //up left
        calcStepsDirection(gp, hset, 1, -1); //down right
        calcStepsDirection(gp, hset, -1, -1); //down left
        calcStepsDirection(gp, hset, 1, 0); //right
        calcStepsDirection(gp, hset, -1, 0); //left
        calcStepsDirection(gp, hset, 0, 1); //up
        calcStepsDirection(gp, hset, 0, -1); //down

        castling(gp, hset, -1);
        castling(gp, hset, 1);

        //-----------------------
        // put in piece class
        //-----------------------
        this.setPossSteps(hset);
    }

    // --------------------------------------------------------------------------------------
    // calculates the step in one direction
    // --------------------------------------------------------------------------------------
    private void calcStepsDirection(Gameplay gp, HashSet<Integer> hset, int addx, int addy) {
        int x = getPosx() + addx;
        int y = getPosy() + addy;
        if (x>=0 && x<=7 && y>=0 && y<=7) {
            int p = com.company.Main.calcPos(x, y);
            if (gp.isEnemyPieceThere(p) || !gp.isPieceThere(p))
                hset.add(p);
        }
    }

    // --------------------------------------------------------------------------------------
    // if castling is available add the position to the possible steps
    // --------------------------------------------------------------------------------------
    private void castling(Gameplay gp, HashSet<Integer> hset, int dir) {
        int x = getPosx() + dir; // start point
        boolean space = true; // isItCastling?

        //check if king moved already
        if (this.isFirstStepDone()) space = false;

        // check if left rook moved already
        if (dir < 0) {
            if (gp.getPieceWhich(Main.calcPos(0, getPosy())) == 1) {
                if (gp.getPiece(Main.calcPos(0, getPosy())).isFirstStepDone()) space = false;
            } else {
                space = false;
            }
        }

        // check if right rook moved already
        if (dir > 0) {
            if (gp.getPieceWhich(Main.calcPos(7, getPosy())) == 1) {
                if (gp.getPiece(Main.calcPos(7, getPosy())).isFirstStepDone()) space = false;
            } else {
                space = false;
            }
        }

        // check if there are pieces in between
        while (x > 0 && x < 7) {
            if (gp.isPieceThere(Main.calcPos(x, getPosy()))) {
                space = false;
            }
            x += dir;
        }

        // if eit is castling add rook position to possible steps
        if (space) hset.add(Main.calcPos(x, getPosy()));
    }
}
