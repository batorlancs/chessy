package com.company;

import java.util.HashSet;

public class Knight extends Piece {

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

    public void possibleSteps(Gameplay gp) {
        HashSet<Integer> hset = new HashSet<Integer>();

        calcStepsDirection(gp, hset, 1, 2);
        calcStepsDirection(gp, hset, -1, 2);
        calcStepsDirection(gp, hset, 1, -2);
        calcStepsDirection(gp, hset, -1, -2);
        calcStepsDirection(gp, hset, 2, 1);
        calcStepsDirection(gp, hset, -2, 1);
        calcStepsDirection(gp, hset, 2, -1);
        calcStepsDirection(gp, hset, -2, -1);

        //-----------------------
        this.setPossSteps(hset);
        //-----------------------
    }

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
