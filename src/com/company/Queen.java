package com.company;

import java.util.HashSet;

public class Queen extends Piece {

    /////////////////////////////////////////////////////////////////////////////////////////
    // CONSTRUCTOR
    /////////////////////////////////////////////////////////////////////////////////////////
    public Queen(int x, int y, boolean white) {
        initQueen(x, y, white);
    }

    private void initQueen(int x, int y, boolean white) {
        setFirstStepDone(false);
        setPosx(x);
        setPosy(y);
        setAlive(true);
        setImageNum(4);
        setWhite(white);
    }
    /////////////////////////////////////////////////////////////////////////////////////////

    // --------------------------------------------------------------------------------------
    // calculate the possible steps for this piece
    // --------------------------------------------------------------------------------------
    public void possibleSteps(Gameplay gp) {
        HashSet<Integer> hset = new HashSet<Integer>();

        calcStepsDirection(gp, hset, 1, 1); //up right
        calcStepsDirection(gp, hset, -1, 1); //down right
        calcStepsDirection(gp, hset, 1, -1); //up left
        calcStepsDirection(gp, hset, -1, -1); //down left
        calcStepsDirection(gp, hset, 0, 1); //up
        calcStepsDirection(gp, hset, 0, -1); //down
        calcStepsDirection(gp, hset, -1, 0); //left
        calcStepsDirection(gp, hset, 1, 0); //right

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
        while (x>=0 && x<=7 && y>=0 && y<=7) {
            int p = com.company.Main.calcPos(x, y);
            if (!gp.isEnemyPieceThere(p) && gp.isPieceThere(p)) break;
            hset.add(p);
            if (gp.isEnemyPieceThere(p)) break;
            x += addx;
            y += addy;
        }
    }
}
