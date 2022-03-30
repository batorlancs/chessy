package com.company;

public class Piece {

    private int posx;
    private int posy;
    private int pos;
    private int imageNum;
    private boolean isWhite;
    private boolean isAlive;

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

    public void move(int x, int y) {
        this.posx = x;
        this.posy = y;
        updatePos();
    }

    /////// getters, setters ////////

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
}
