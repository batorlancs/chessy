package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import static com.company.Main.*;

public class MyFrame extends JFrame implements MouseListener, MouseMotionListener {

    // gui
    private JPanel panel = new JPanel();
    private JButton[] buttons = new JButton[64];
    private JLabel labelDrag = new JLabel();
    boolean isDragging = false;
    int whichButton = 0;

    // colors for displaying on the board
    public Color[] colors = new Color[64]; // basic color of the board
    public Color[] stepColors = new Color[64]; // possible step color
    public Color[] lastColors = new Color[64]; // last step taken color

    // variables fot gameplay
    private int lastClicked = 0;
    private ImageManager imageManager = new ImageManager();
    private Gameplay gameplay;
    private int passant = -1; // where the en passant pawn is
    private int passantStep = -1; // where is the step the other pawn should take to destroy this pawn

    /////////////////////////////////////////////////////////////////////////////////////////
    // CONSTRUCTOR
    /////////////////////////////////////////////////////////////////////////////////////////
    MyFrame() {
        initFrame();
    }

    private void initFrame() {
        // frame setup
        this.setSize(640, 668);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(null);
        this.setResizable(false);
        this.setTitle("chess");

        //panel setup
        panel.setBounds(0, 0, 640, 640);
        panel.setLayout(new GridLayout(8, 8, 0, 0));

        //buttons setup
        for (int i = 0; i < 64; i++) {
            buttons[i] = new JButton();
            buttons[i].setSize(80, 80);
            buttons[i].setBorderPainted(false);
            buttons[i].setOpaque(true);
            //buttons[i].addActionListener(this);
            buttons[i].addMouseListener(this);
            buttons[i].addMouseMotionListener(this);
            buttons[i].setVerticalAlignment(JButton.CENTER);
            buttons[i].setHorizontalAlignment(JButton.CENTER);
            panel.add(buttons[i]);
        }

        // drag label setup
        labelDrag.setSize(80, 80);
        labelDrag.setOpaque(false);
        labelDrag.setVerticalAlignment(JLabel.CENTER);
        labelDrag.setHorizontalAlignment(JLabel.CENTER);
        labelDrag.setVisible(false);


        // fill up arrays with the correct chess color pattern
        fillColorBoard(colors, color1, color2);
        fillColorBoard(stepColors, color4, color5);
        fillColorBoard(lastColors, color6, color7);

        //setting up buttons and colors
        restartGame();

        // gameplay
        lastClicked = -1;

        //adding to frame
        this.add(labelDrag);
        this.add(panel);


        //set visible / last in constructor
        this.setVisible(true);
    }
    /////////////////////////////////////////////////////////////////////////////////////////

    // --------------------------------------------------------------------------------------
    // fill up color arrays, only used in constructor
    // --------------------------------------------------------------------------------------
    private void fillColorBoard(Color[] arr, Color c1, Color c2) {
        for (int i = 0; i < 64; i++) {
            if ((i / 8) % 2 == 0) {
                if (i % 8 % 2 == 0) arr[i] = c1;
                else arr[i] = c2;
            } else {
                if (i % 8 % 2 == 1) arr[i] =  c1;
                else arr[i] = c2;
            }
        }
    }

    // --------------------------------------------------------------------------------------
    // restart / initiate the game : attach a new gameplay to frame, and refresh everything
    // --------------------------------------------------------------------------------------
    public void restartGame() {
        gameplay = new Gameplay();
        refreshBoard();
    }

    // --------------------------------------------------------------------------------------
    // refresh the all the icons and colors of the board
    // --------------------------------------------------------------------------------------
    private void refreshBoard() {
        for (int i = 0; i < 64; i++) {
            if (gameplay.isPieceThere(i)) {
                buttons[i].setIcon(imageManager.getImage(gameplay.getPieceWhich(i), gameplay.isPieceWhite(i)));
            } else buttons[i].setIcon(null);
        }
        refreshColorBoard();
    }

    private void refreshColorBoard() {
        for (int i = 0; i < 64; i++) {
            buttons[i].setBackground(colors[i]);
        }
    }

    // --------------------------------------------------------------------------------------
    // set all of the buttons enabled or disabled
    // --------------------------------------------------------------------------------------
    public void setButtonsEnabled(boolean x) {
        for (JButton a: buttons) {
            if (!x) a.setDisabledIcon(a.getIcon());
            a.setEnabled(x);
        }
    }

    // --------------------------------------------------------------------------------------
    // if the pawn reached the other side call promotion function in Piece class
    // --------------------------------------------------------------------------------------
    private void checkIfPromotion(int index) {
        if (gameplay.getPiece(index).getImageNum() == 0) {
            int num;
            if (com.company.Main.calcPosy(index) == 7 && gameplay.isPieceWhite(index)) {
                gameplay.getPiece(index).promotion(true, index);
            }
            if (com.company.Main.calcPosy(index) == 0 && !gameplay.isPieceWhite(index)) {
                gameplay.getPiece(index).promotion(false, index);
            }
        }
    }

    public void callPromotion(int index, int which, boolean isWhite) {
        gameplay.createPromotion(index, which, isWhite);
    }

    // --------------------------------------------------------------------------------------
    // refresh the piece and color array based on Piece locations, and refresh
    // --------------------------------------------------------------------------------------
    public void refreshEverything() {
        gameplay.refreshColorCheck();
        gameplay.refreshPieceCheck();
        refreshBoard();
    }

    // --------------------------------------------------------------------------------------
    // checks if move was a double step by a pawn --> -1 val if not, pos val if yes
    // --------------------------------------------------------------------------------------
    private void checkIfPassant(int index) {
        if (Math.abs(Main.calcPosy(lastClicked) - Main.calcPosy(index)) == 2 && gameplay.getPiece(index).getImageNum() == 0) {
            setPassant(index);
            calcPassantStep(index);
        } else {
            setPassant(-1);
            setPassantStep(-1);
        }
    }
    // --------------------------------------------------------------------------------------
    // calculates passant step based on position --> -1 val if out of range
    // --------------------------------------------------------------------------------------
    private void calcPassantStep(int index) {
        if (gameplay.getPiece(index).isWhite() && index < 55) {
            setPassantStep(index+8);
        } else if (!gameplay.getPiece(index).isWhite() && index > 7) {
            setPassantStep(index-8);
        } else {
            setPassantStep(-1);
        }
    }

    // --------------------------------------------------------------------------------------
    // checks if move is castling
    // --------------------------------------------------------------------------------------
    private boolean clickedCastling(int indexLast, int index) {
        if (lastClicked == -1) return false;
        if (gameplay.getPieceWhich(indexLast) == 5 && gameplay.getPieceWhich(index) == 1) {
            if (!gameplay.isEnemyPieceThere(index) && !gameplay.getPiece(indexLast).isFirstStepDone()&& !gameplay.getPiece(index).isFirstStepDone())
                return true;
            else
                return false;
        } else return false;
    }

    /////////////////////////////////////////////////////////////////////////////////////////
    // ACTION LISTENER
    /////////////////////////////////////////////////////////////////////////////////////////
//    @Override
//    public void actionPerformed(ActionEvent e) {
//        for (int i = 0; i < 64; i++) {
//            if (e.getSource() == buttons[i]) {
//
//                // --------------------------------------------------------------------------
//                // if last click was a piece and current click is not a friendly piece
//                // --------------------------------------------------------------------------
//                if (lastClicked != -1 && !gameplay.checkIfRightClick(i)) {
//
//                    // if clicked to make a step (diff colored button and circle)
//                    if (buttons[i].getBackground() == stepColors[i]) {
//
//                        //kill if there is a figure
//                        if (gameplay.isEnemyPieceThere(i)) {
//                            gameplay.getPiece(i).killPiece();
//                        // kill if made en passant
//                        } else if (getPassantStep() == i && gameplay.getPieceWhich(lastClicked) == 0) {
//                            gameplay.getPiece(getPassant()).killPiece();
//                        }
//
//                        // move piece to clicked position
//                        gameplay.getPiece(lastClicked).move1(i);
//
//                        // refresh all arrays, update steps, switch turns, display refreshed
//                        refreshEverything();
//                        gameplay.updateSteps();
//                        gameplay.switchTurn();
//                        gameplay.displayBoard(); // debugging
//
//                        // show last steps when switching turns
//                        buttons[lastClicked].setBackground(lastColors[lastClicked]);
//                        buttons[i].setBackground(lastColors[i]);
//
//                        // check for special moves
//                        checkIfPassant(i);
//                        checkIfPromotion(i);
//
//                        lastClicked = -1; // reset
//
//                    // if made an invalid move, cancelled move
//                    } else {
//                        refreshBoard();
//                        lastClicked = -1;
//                    }
//
//                // --------------------------------------------------------------------------
//                // if clicked to make castling
//                // --------------------------------------------------------------------------
//                } else if (clickedCastling(lastClicked, i)) {
//
//                    //this might be unnecessary
//                    if (gameplay.getPieceWhich(lastClicked) == 5 && gameplay.getPieceWhich(i) == 1 && !gameplay.isEnemyPieceThere(i)) {
//                        if (calcPosx(lastClicked) > calcPosx(i)) {
//                            int castPos = calcPos(calcPosx(lastClicked) - 2, calcPosy(lastClicked));
//                            gameplay.getPiece(lastClicked).move1(castPos);
//                            gameplay.getPiece(i).move2(calcPosx(castPos) + 1, calcPosy(castPos));
//                        } else {
//                            int castPos = calcPos(calcPosx(lastClicked) + 2, calcPosy(lastClicked));
//                            gameplay.getPiece(lastClicked).move1(castPos);
//                            gameplay.getPiece(i).move2(calcPosx(castPos) - 1, calcPosy(castPos));
//                        }
//                    }
//
//                    // refresh all arrays, update steps, switch turns, display refreshed
//                    refreshEverything();
//                    gameplay.updateSteps();
//                    gameplay.switchTurn();
//                    gameplay.displayBoard(); //debugging
//
//                    // show last steps when switching turns
//                    buttons[lastClicked].setBackground(lastColors[lastClicked]);
//                    buttons[i].setBackground(lastColors[i]);
//
//                    lastClicked = -1; // reset
//
//                // --------------------------------------------------------------------------
//                // if not making a move
//                // --------------------------------------------------------------------------
//                } else {
//                    // if clicked on same piece: reset
//                    if (lastClicked == i) {
//                        refreshBoard();
//                        lastClicked = -1;
//                    }
//
//                    // if clicked on friendly piece show possible steps
//                    else if (gameplay.checkIfRightClick(i)) {
//
//                        refreshBoard();
//                        buttons[i].setBackground(stepColors[i]); //color click position
//                        lastClicked = i; // save click position
//                        gameplay.updateSteps();
//
//                        // display all the possible steps
//                        for (int num: gameplay.getPiece(i).getPossSteps()) {
//                            buttons[num].setBackground(stepColors[num]);
//                            buttons[num].setIcon(imageManager.getImageAnim(gameplay.getPieceWhich(num), gameplay.isPieceWhite(num)));
//                            if (num == getPassantStep() && getPassant() != -1 && gameplay.getPieceWhich(lastClicked) == 0)
//                                buttons[getPassant()].setIcon(imageManager.getImagePassant(gameplay.isPieceWhite(getPassant())));
//                            if (gameplay.getPieceWhich(num) == 1 && !gameplay.isEnemyPieceThere(num))
//                                buttons[num].setIcon(imageManager.getImageCastling(gameplay.isPieceWhite(num)));
//                        }
//                    }
//                }
//            }
//        }
//
//        //check if game is over
//        if (gameplay.detectGameOver() != 0) {
//            this.setButtonsEnabled(false);
//            new EndFrame(gameplay.detectGameOver()); // show end frame to restart, or exit
//        }
//
//    }

    /////////////////////////////////////////////////////////////////////////////////////////
    // MOUSE LISTENER
    /////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        System.out.println("clicked");
        for (int i = 0; i < 64; i++) {
            if (e.getSource() == buttons[i]) {
                if (gameplay.checkIfRightClick(i)) {
                    labelDrag.setIcon(buttons[i].getIcon());
                    labelDrag.setVisible(true);
                    whichButton = i;
                    isDragging = true;
                    labelDrag.setBounds(e.getX() + buttons[whichButton].getX() - 40, e.getY() + buttons[whichButton].getY() - 40, 80, 80);
                    labelDrag.setVisible(true);

                    refreshBoard();
                    buttons[i].setBackground(stepColors[i]); //color click position
                    lastClicked = i; // save click position
                    gameplay.updateSteps();

                    // display all the possible steps
                    for (int num: gameplay.getPiece(i).getPossSteps()) {
                        buttons[num].setBackground(stepColors[num]);
                        buttons[num].setIcon(imageManager.getImageAnim(gameplay.getPieceWhich(num), gameplay.isPieceWhite(num)));
                        if (num == getPassantStep() && getPassant() != -1 && gameplay.getPieceWhich(lastClicked) == 0)
                            buttons[getPassant()].setIcon(imageManager.getImagePassant(gameplay.isPieceWhite(getPassant())));
                        if (gameplay.getPieceWhich(num) == 1 && !gameplay.isEnemyPieceThere(num))
                            buttons[num].setIcon(imageManager.getImageCastling(gameplay.isPieceWhite(num)));
                    }
                    buttons[i].setIcon(null);
                }
                if (lastClicked != -1 && !gameplay.checkIfRightClick(i)) {
                    // if clicked to make a step (diff colored button and circle)
                    if (buttons[i].getBackground() == stepColors[i]) {

                        //kill if there is a figure
                        if (gameplay.isEnemyPieceThere(i)) {
                            gameplay.getPiece(i).killPiece();
                            // kill if made en passant
                        } else if (getPassantStep() == i && gameplay.getPieceWhich(lastClicked) == 0) {
                            gameplay.getPiece(getPassant()).killPiece();
                        }

                        // move piece to clicked position
                        gameplay.getPiece(lastClicked).move1(i);

                        // refresh all arrays, update steps, switch turns, display refreshed
                        refreshEverything();
                        gameplay.updateSteps();
                        gameplay.switchTurn();
                        //gameplay.displayBoard(); // debugging

                        // show last steps when switching turns
                        buttons[lastClicked].setBackground(lastColors[lastClicked]);
                        buttons[i].setBackground(lastColors[i]);

                        // check for special moves
                        checkIfPassant(i);
                        checkIfPromotion(i);

                        lastClicked = -1; // reset

                        // if made an invalid move, cancelled move
                    } else {
                        buttons[lastClicked].setIcon(imageManager.getImage(gameplay.getPieceWhich(lastClicked), gameplay.isPieceWhite(lastClicked)));
                        //lastClicked = -1;
                    }
                }
            }
        }
    }

    /////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public void mouseReleased(MouseEvent e) {

        int i = calcPosMouse();

        if (isDragging) {
            System.out.println("released");
            isDragging = false;
            labelDrag.setVisible(false);
        }

        System.out.println(lastClicked + ", " + i);

        if (lastClicked != -1 && !gameplay.checkIfRightClick(i)) {

            // if clicked to make a step (diff colored button and circle)
            if (buttons[i].getBackground() == stepColors[i]) {

                //kill if there is a figure
                if (gameplay.isEnemyPieceThere(i)) {
                    gameplay.getPiece(i).killPiece();
                    // kill if made en passant
                } else if (getPassantStep() == i && gameplay.getPieceWhich(lastClicked) == 0) {
                    gameplay.getPiece(getPassant()).killPiece();
                }

                // move piece to clicked position
                gameplay.getPiece(lastClicked).move1(i);

                // refresh all arrays, update steps, switch turns, display refreshed
                refreshEverything();
                gameplay.updateSteps();
                gameplay.switchTurn();
                //gameplay.displayBoard(); // debugging

                // show last steps when switching turns
                buttons[lastClicked].setBackground(lastColors[lastClicked]);
                buttons[i].setBackground(lastColors[i]);

                // check for special moves
                checkIfPassant(i);
                checkIfPromotion(i);

                lastClicked = -1; // reset

            // if made an invalid move, cancelled move
            } else {
                buttons[lastClicked].setIcon(imageManager.getImage(gameplay.getPieceWhich(lastClicked), gameplay.isPieceWhite(lastClicked)));
            }

        // --------------------------------------------------------------------------
        // if clicked to make castling
        // --------------------------------------------------------------------------
        } else if (clickedCastling(lastClicked, i)) {

            //this might be unnecessary
            if (gameplay.getPieceWhich(lastClicked) == 5 && gameplay.getPieceWhich(i) == 1 && !gameplay.isEnemyPieceThere(i)) {
                if (calcPosx(lastClicked) > calcPosx(i)) {
                    int castPos = calcPos(calcPosx(lastClicked) - 2, calcPosy(lastClicked));
                    gameplay.getPiece(lastClicked).move1(castPos);
                    gameplay.getPiece(i).move2(calcPosx(castPos) + 1, calcPosy(castPos));
                } else {
                    int castPos = calcPos(calcPosx(lastClicked) + 2, calcPosy(lastClicked));
                    gameplay.getPiece(lastClicked).move1(castPos);
                    gameplay.getPiece(i).move2(calcPosx(castPos) - 1, calcPosy(castPos));
                }
            }

            // refresh all arrays, update steps, switch turns, display refreshed
            refreshEverything();
            gameplay.updateSteps();
            gameplay.switchTurn();
            //gameplay.displayBoard(); //debugging

            // show last steps when switching turns
            buttons[lastClicked].setBackground(lastColors[lastClicked]);
            buttons[i].setBackground(lastColors[i]);

            lastClicked = -1; // reset

        // --------------------------------------------------------------------------
        // if not making a move
        // --------------------------------------------------------------------------
        } else {
            if (lastClicked == i) {
                buttons[lastClicked].setIcon(imageManager.getImage(gameplay.getPieceWhich(lastClicked), gameplay.isPieceWhite(lastClicked)));
            }
        }

        //check if game is over
        if (gameplay.detectGameOver() != 0) {
            this.setButtonsEnabled(false);
            new EndFrame(gameplay.detectGameOver()); // show end frame to restart, or exit
        }

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    /////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public void mouseDragged(MouseEvent e) {
        if (isDragging) {
            labelDrag.setBounds(e.getX() + buttons[whichButton].getX() - 40, e.getY() + buttons[whichButton].getY() - 40, 80, 80);
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }

    /////////////////////////////////////////////////////////////////////////////////////////
    // GETTERS,  SETTERS
    /////////////////////////////////////////////////////////////////////////////////////////

    public int getPassant() {
        return passant;
    }

    public void setPassant(int num) {
        this.passant = num;
    }

    public int getPassantStep() {
        return this.passantStep;
    }

    public void setPassantStep(int num) {
        this.passantStep = num;
    }



    /////////////////////////////////////////////////////////////////////////////////////////

}
