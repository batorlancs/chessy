package com.company;

import javax.swing.*;
import java.util.HashSet;

public class Piece {

    private int posx;
    private int posy;
    private int pos;
    private int imageNum;
    private boolean isWhite;
    private boolean isAlive;
    private boolean isFirstStepDone;
    private HashSet<Integer> possSteps = new HashSet<Integer>();
    public PromoFrame promoFrame;


    // UPDATES POSITION BASED ON POS X AND POS Y VALUES //
    private void updatePos() {
        pos = posx + (7-posy) * 8;
        //System.out.println("piece pos: " + pos);
    }

    private void updatePosxy() {
        posx = pos % 8;
        posy = 7 - (pos / 8);
        //System.out.println("posx: " + posx + ", posy: " + posy);

    }
    public void move1(int pos) {
        this.pos = pos;
        if (!isFirstStepDone) isFirstStepDone = true;
        updatePosxy();
    }
    public void move2(int x, int y) {
        this.posx = x;
        this.posy = y;
        if (!isFirstStepDone) isFirstStepDone = true;
        updatePos();
    }

    public void killPiece() {
        setAlive(false);
    }

    public void promotion(boolean isWhite, int pos) {
        promoFrame = new PromoFrame(isWhite, pos);
    }

    /////// getters, setters ////////

    // POSS STEPS
    public void setPossSteps(HashSet<Integer> set) {
        possSteps = new HashSet<Integer>(set);
    }

    public HashSet<Integer> getPossSteps() {
        return this.possSteps;
    }

    // POS //
    public void setPos(int pos) {
        this.pos = pos;
        updatePosxy();
    }

    public int getPos() {
        return this.pos;
    }

    // POS X //
    public void setPosx(int posx) {
        this.posx = posx;
        updatePos();
    }

    public int getPosx() {
        return this.posx;
    }

    // POS Y //
    public void setPosy(int posy) {
        this.posy = posy;
        updatePos();
    }

    public int getPosy() {
        return this.posy;
    }

    // IMAGE NUM //
    public void setImageNum(int imageNum) {
        this.imageNum = imageNum;
    }

    public int getImageNum() {
        return this.imageNum;
    }

    // IS WHITE //
    public void setWhite(boolean isWhite) {
        this.isWhite = isWhite;
    }

    public boolean isWhite() {
        return this.isWhite;
    }

    // IS ALIVE //
    public void setAlive(boolean isAlive) {
        this.isAlive = isAlive;
    }

    public boolean isAlive() {
        return this.isAlive;
    }

    // IS PAWN //
    public void setFirstStepDone(boolean isFirstStepDone) {
        this.isFirstStepDone = isFirstStepDone;
    }

    public boolean isFirstStepDone() {
        return this.isFirstStepDone;
    }
}
