package com.company;

import javax.swing.*;
import java.util.HashSet;

public class Piece {

    private int posx;
    private int posy;
    private int pos;

    private int imageNum; // 0:pawn, 1:rook, 2:knight, 3:bishop, 4:queen, 5:king
    private boolean isWhite;
    private boolean isAlive;
    private boolean isFirstStepDone;
    private HashSet<Integer> possSteps = new HashSet<Integer>(); //storing possible steps

    // --------------------------------------------------------------------------------------
    // updates pos based on x and y pos
    // --------------------------------------------------------------------------------------
    private void updatePos() {
        pos = posx + (7-posy) * 8;
    }

    // --------------------------------------------------------------------------------------
    // updates x and y pos based on pos
    // --------------------------------------------------------------------------------------
    private void updatePosxy() {
        posx = pos % 8;
        posy = 7 - (pos / 8);

    }

    // --------------------------------------------------------------------------------------
    // move to given pos
    // --------------------------------------------------------------------------------------
    public void move1(int pos) {
        this.pos = pos;
        if (!isFirstStepDone) isFirstStepDone = true;
        updatePosxy();
    }

    // --------------------------------------------------------------------------------------
    // move to x and y pos
    // --------------------------------------------------------------------------------------
    public void move2(int x, int y) {
        this.posx = x;
        this.posy = y;
        if (!isFirstStepDone) isFirstStepDone = true;
        updatePos();
    }
    // --------------------------------------------------------------------------------------
    public void killPiece() {
        setAlive(false);
    }

    // --------------------------------------------------------------------------------------
    public void promotion(boolean isWhite, int pos) {
        new PromoFrame(isWhite, pos);
    }

    /////////////////////////////////////////////////////////////////////////////////////////
    // GETTERS AND SETTERS
    /////////////////////////////////////////////////////////////////////////////////////////

    // --------------------------------------------------------------------------------------
    // possible steps
    // --------------------------------------------------------------------------------------
    public void setPossSteps(HashSet<Integer> set) {
        possSteps = new HashSet<Integer>(set);
    }

    public HashSet<Integer> getPossSteps() {
        return this.possSteps;
    }

    // --------------------------------------------------------------------------------------
    // pos
    // --------------------------------------------------------------------------------------
    public void setPos(int pos) {
        this.pos = pos;
        updatePosxy();
    }

    public int getPos() {
        return this.pos;
    }

    // --------------------------------------------------------------------------------------
    // pos x
    // --------------------------------------------------------------------------------------
    public void setPosx(int posx) {
        this.posx = posx;
        updatePos();
    }

    public int getPosx() {
        return this.posx;
    }

    // --------------------------------------------------------------------------------------
    // pos y
    // --------------------------------------------------------------------------------------
    public void setPosy(int posy) {
        this.posy = posy;
        updatePos();
    }

    public int getPosy() {
        return this.posy;
    }

    // --------------------------------------------------------------------------------------
    // image num
    // --------------------------------------------------------------------------------------
    public void setImageNum(int imageNum) {
        this.imageNum = imageNum;
    }

    public int getImageNum() {
        return this.imageNum;
    }

    // --------------------------------------------------------------------------------------
    // is white
    // --------------------------------------------------------------------------------------
    public void setWhite(boolean isWhite) {
        this.isWhite = isWhite;
    }

    public boolean isWhite() {
        return this.isWhite;
    }

    // --------------------------------------------------------------------------------------
    // is alive
    // --------------------------------------------------------------------------------------
    public void setAlive(boolean isAlive) {
        this.isAlive = isAlive;
    }

    public boolean isAlive() {
        return this.isAlive;
    }

    // --------------------------------------------------------------------------------------
    // is first step done
    // --------------------------------------------------------------------------------------
    public void setFirstStepDone(boolean isFirstStepDone) {
        this.isFirstStepDone = isFirstStepDone;
    }

    public boolean isFirstStepDone() {
        return this.isFirstStepDone;
    }
    /////////////////////////////////////////////////////////////////////////////////////////
}
