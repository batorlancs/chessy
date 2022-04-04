package com.company;

import java.util.HashSet;

public class Knight extends Piece {

    /////////////////////////////////////////////////////////////////////////////////////////
    // CONSTRUCTOR
    /////////////////////////////////////////////////////////////////////////////////////////
    public Knight(int x, int y, boolean white) {
        initKnight(x, y, white);
    }

    private void initKnight(int x, int y, boolean white) {
        setFirstStepDone(false);
        setPosx(x);
        setPosy(y);
        setAlive(true);
        setImageNum(2);
        setWhite(white);
    }
    /////////////////////////////////////////////////////////////////////////////////////////

    // --------------------------------------------------------------------------------------
    // calculate the possible steps for this piece
    // --------------------------------------------------------------------------------------
    public void possibleSteps(Gameplay gp) {
        HashSet<Integer> hset = new HashSet<Integer>();

        calcStepsDirection(gp, hset, 1, 2); // x+1, y+2
        calcStepsDirection(gp, hset, -1, 2); // x-1, y+2
        calcStepsDirection(gp, hset, 1, -2); // x+1, x-2
        calcStepsDirection(gp, hset, -1, -2); // x-1, x-2
        calcStepsDirection(gp, hset, 2, 1); // x+2, y+1
        calcStepsDirection(gp, hset, -2, 1); // x-2, y+1
        calcStepsDirection(gp, hset, 2, -1); // x+2, y-1
        calcStepsDirection(gp, hset, -2, -1); // x-2, y-1

        //-----------------------
        // put in piece class
        //-----------------------
        this.setPossSteps(hset);
    }

    // --------------------------------------------------------------------------------------
    // calculate the position of a movement in one possible direction (l shapes)
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

}
