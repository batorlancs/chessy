package com.company;

import java.util.HashSet;

public class Pawn extends Piece {

    ////// CONSTRUCTOR //////
    public Pawn(int x, int y, boolean white) {
        initPawn(x, y, white);
    }


    private void initPawn(int x, int y, boolean white) {
        setFirstStepDone(false);
        setPosx(x);
        setPosy(y);
        setAlive(true);
        setImageNum(0);
        setWhite(white);
    }

    public void possibleSteps(Gameplay gp) {
        HashSet<Integer> hset = new HashSet<Integer>();

        if (isWhite()) {
            int yy = getPosy() + 2;
            // first double the steps
            if (!isFirstStepDone())
                yy++;

            for (int i = getPosy() + 1; i < yy; i++) {
                int p = com.company.Main.calcPos(getPosx(), i);
                if (p < 0 || p > 63) break;
                if (gp.isPieceThere(p)) break;
                hset.add(p);
            }
            if (gp.isEnemyPieceThere(com.company.Main.calcPos(getPosx() -1 , getPosy() + 1))) {
                hset.add(com.company.Main.calcPos(getPosx() -1 , getPosy() + 1));
            }
            if (gp.isEnemyPieceThere(com.company.Main.calcPos(getPosx() + 1 , getPosy() + 1))) {
                hset.add(com.company.Main.calcPos(getPosx() + 1 , getPosy() + 1));
            }

        } else {
            int yy = getPosy() - 2;
            // first double the steps
            if (!isFirstStepDone())
                yy--;

            for (int i = getPosy() - 1; i > yy; i--) {
                int p = com.company.Main.calcPos(getPosx(), i);
                if (p < 0 || p > 63) break;
                if (gp.isPieceThere(p)) break;
                hset.add(p);
            }

            if (gp.isEnemyPieceThere(com.company.Main.calcPos(getPosx() -1 , getPosy() - 1))) {
                hset.add(com.company.Main.calcPos(getPosx() -1 , getPosy() - 1));
            }
            if (gp.isEnemyPieceThere(com.company.Main.calcPos(getPosx() + 1 , getPosy() - 1))) {
                hset.add(com.company.Main.calcPos(getPosx() + 1 , getPosy() - 1));
            }

        }
        //-----------------------
        this.setPossSteps(hset);
        //-----------------------
    }

}
