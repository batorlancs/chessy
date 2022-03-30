package com.company;

public class Pawn extends Piece {

    ////// CONSTRUCTOR //////
    public Pawn(int x, int y, boolean white) {
        initPawn(x, y, white);
    }

    private void initPawn(int x, int y, boolean white) {
        setPosx(x);
        setPosy(y);
        setWhite(white);
    }
}
