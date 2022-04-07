package com.company;

import java.util.LinkedList;

public class MoveList {

    /////////////////////////////////////////////////////////////////////////////////////////
    // REC CLASS : stores a step made
    /////////////////////////////////////////////////////////////////////////////////////////
    public class Rec {
        private int from;
        private int to;
        private int whichPiece;
        private boolean isWhite;
        private boolean isCapture;

        // CONSTRUCTOR 1 -------------------------------------------------------------------
        public Rec(int from, int to, int whichPiece, boolean isWhite, boolean isCapture) {
            initRec1(from, to, whichPiece, isWhite, isCapture);
        }

        private void initRec1(int from, int to, int whichPiece, boolean isWhite, boolean isCapture) {
            this.from = from;
            this.to = to;
            this.whichPiece = whichPiece;
            this.isWhite = isWhite;
            this.isCapture = isCapture;
        }

        // CONSTRUCTOR 2 -------------------------------------------------------------------
        public Rec(Rec a) {
            initRec2(a);
        }

        private void initRec2(Rec a) {
            this.from = a.getFrom();
            this.to = a.getTo();
            this.whichPiece = a.getWhichPiece();
            this.isWhite = a.isWhite();
            this.isCapture = a.isCapture();
        }

        // getters, setters ---------------------------------------------------------------
        public int getFrom() { return this.from; }
        public int getTo() { return this.to; }
        public int getWhichPiece() { return this.whichPiece; }
        public boolean isWhite() { return this.isWhite; }
        public boolean isCapture() { return this.isCapture; }

        //see if the step is the same (same for back and forth)
        public boolean equals(Rec a) {
            return (((this.from == a.getFrom() && this.to == a.getTo()) || (this.from == a.getTo() && this.to == a.getFrom())) && this.whichPiece == a.getWhichPiece() && this.isWhite == a.isWhite() && this.isCapture == a.isCapture());
        }
    }

    /////////////////////////////////////////////////////////////////////////////////////////
    // MOVE LIST CLASS
    /////////////////////////////////////////////////////////////////////////////////////////

    LinkedList<Rec> moves;

    public MoveList() {
        moves =  new LinkedList<>();
    }

    // --------------------------------------------------------------------------
    // add a new move (Rec) to the moves list
    // --------------------------------------------------------------------------
    public void addToList(int from, int to, int whichPiece, boolean isWhite, boolean isCapture) {
        moves.add(new Rec(from, to, whichPiece, isWhite, isCapture));
        if (detectDraw()) new EndFrame(3);
    }

    // --------------------------------------------------------------------------
    // returns true if the game is a draw by the rules
    // --------------------------------------------------------------------------
    public boolean detectDraw() {
        int drawCount = 0; // for first rule
        int drawCount2 = 0; // for second rule
        Rec whiteMove = new Rec(0, 0, 0, true, false);
        Rec blackMove = new Rec(0, 0, 0, false, false);

        for (Rec a: moves) {
            // first rule: if 50 moves have been made without capture or pawn move: it is a draw
            if (a.getWhichPiece() != 0 && !a.isCapture()) drawCount++;
            else drawCount = 0;

            // second rule: if the same 3 movements have been repeated by both: it is a draw
            if (a.isWhite() && a.equals(whiteMove)) drawCount2++;
            else if (!a.isWhite() && a.equals(blackMove)) drawCount2++;
            else  {
                if (a.isWhite()) whiteMove = new Rec(a);
                else blackMove = new Rec(a);
                drawCount2 = 0;
            }

            // third rule: if there is only 2 kings left: it is a draw --- .isThereOnly2Kings()
            // (only if king is not taken after capture) ---- drawCount > 0
        }

        //System.out.println("draw count: " + drawCount + ",  draw count2: " + drawCount2 + ", kings: " + Main.frame.isThereOnly2Kings());
        return (drawCount >= 50 || drawCount2 >= 10 || (Main.frame.isThereOnly2Kings() && drawCount > 0));
    }

    // --------------------------------------------------------------------------
    // displays the moves (for debugging) - PRESS 'D' IN THE GAME TO GET INFO
    // --------------------------------------------------------------------------
    public void displayMoves() {
        int i = 0;
        System.out.println("---------------------------------");
        for (Rec a: moves) {
            if (a.isWhite()) System.out.print(i + ". white ");
            else System.out.print(i + ". black ");
            System.out.print(a.getWhichPiece() + ": " + a.getFrom() + " -> " + a.getTo());
            if (a.isCapture()) System.out.println(" capture");
            else System.out.println();
            i++;
        }
    }

}
