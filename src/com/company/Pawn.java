package com.company;

import java.util.HashSet;

public class Pawn extends Piece {

    /////////////////////////////////////////////////////////////////////////////////////////
    // CONSTRUCTOR
    /////////////////////////////////////////////////////////////////////////////////////////
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
    /////////////////////////////////////////////////////////////////////////////////////////

    // --------------------------------------------------------------------------------------
    // calculate the possible steps of this piece
    // --------------------------------------------------------------------------------------
    public void possibleSteps(Gameplay gp) {
        HashSet<Integer> hset = new HashSet<Integer>();

        // white piece moving in their direction ---------------------------
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
            // check if able to destroy enemy piece
            if (gp.isEnemyPieceThere(com.company.Main.calcPos(getPosx() -1 , getPosy() + 1))) {
                hset.add(com.company.Main.calcPos(getPosx() -1 , getPosy() + 1));
            }
            if (gp.isEnemyPieceThere(com.company.Main.calcPos(getPosx() + 1 , getPosy() + 1))) {
                hset.add(com.company.Main.calcPos(getPosx() + 1 , getPosy() + 1));
            }

            // check if en passant is available
            passantCheck(hset, true);

        // black piece moving the opposite way -----------------------------
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
            // check if able to destroy enemy piece
            if (gp.isEnemyPieceThere(com.company.Main.calcPos(getPosx() -1 , getPosy() - 1))) {
                hset.add(com.company.Main.calcPos(getPosx() -1 , getPosy() - 1));
            }
            if (gp.isEnemyPieceThere(com.company.Main.calcPos(getPosx() + 1 , getPosy() - 1))) {
                hset.add(com.company.Main.calcPos(getPosx() + 1 , getPosy() - 1));
            }

            // check if en passant is available
            passantCheck(hset, false);

        }
        // -----------------------
        // put in piece class
        // -----------------------
        this.setPossSteps(hset);
    }

    // --------------------------------------------------------------------------------------
    // en passant check
    // --------------------------------------------------------------------------------------
    private void passantCheck(HashSet<Integer> hset, boolean isWhite) {
        int x1 = getPosx();
        int x2 = getPosx();
        if (x1 > 0) x1--;
        if (x2 < 7) x2++;
        if (Main.frame.getPassant() == Main.calcPos(x1, getPosy()) || Main.frame.getPassant() == Main.calcPos(x2, getPosy())) {
            if (Main.frame.getPassantStep() != -1)
                hset.add(Main.frame.getPassantStep());
        }
    }

}
