package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static com.company.Main.*;

public class MyFrame extends JFrame implements ActionListener {

    private JPanel panel = new JPanel();
    private JButton[] buttons = new JButton[64];

    public Color[] colors = new Color[64];
    public Color[] stepColors = new Color[64];
    public Color[] lastColors = new Color[64];
    private int lastClicked = 0;

    private ImageManager imageManager = new ImageManager();
    private Gameplay gameplay;
    private int passant = -1;
    private int passantStep = -1;

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
            buttons[i].addActionListener(this);
            buttons[i].setVerticalAlignment(JButton.CENTER);
            buttons[i].setHorizontalAlignment(JButton.CENTER);
            panel.add(buttons[i]);
        }

        fillColorBoard(colors, color1, color2);
        fillColorBoard(stepColors, color4, color5);
        fillColorBoard(lastColors, color6, color7);

        //setting up buttons and colors
        restartGame();

        // gameplay
        lastClicked = -1;

        //adding to panel
        this.add(panel);

        //set visible / last in constructor
        this.setVisible(true);
    }

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

    public void restartGame() {
        gameplay = new Gameplay();
        refreshBoard();
    }

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

    public void setButtonsEnabled(boolean x) {
        for (JButton a: buttons) {
            if (!x) a.setDisabledIcon(a.getIcon());
            a.setEnabled(x);
        }
    }

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

    public void refreshEverything() {
        gameplay.refreshColorCheck();
        gameplay.refreshPieceCheck();
        refreshBoard();
        refreshColorBoard();
    }

    private void checkIfPassant(int index) {
        if (Math.abs(Main.calcPosy(lastClicked) - Main.calcPosy(index)) == 2) {
            if (gameplay.getPiece(index).getImageNum() == 0) {
                setPassant(index);
                calcPassantStep(index);
            } else {
                setPassant(-1);
                setPassantStep(-1);
            }
        } else {
            setPassant(-1);
            setPassantStep(-1);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        for (int i = 0; i < 64; i++) {
            //if you hit any in the thing
            if (e.getSource() == buttons[i]) {

                if (lastClicked != -1 && !gameplay.checkIfRightClick(i)) {
                    if (buttons[i].getBackground() == stepColors[i]) {
                        //kill if there is a figure
                        if (gameplay.isEnemyPieceThere(i)) {
                            gameplay.getPiece(i).killPiece();
                        } else if (getPassantStep() == i) {
                            gameplay.getPiece(getPassant()).killPiece();
                        }
                        gameplay.getPiece(lastClicked).move1(i);
                        //refresh all arrays and stuff
                        gameplay.refreshColorCheck();
                        gameplay.refreshPieceCheck();
                        gameplay.updateSteps();
                        gameplay.switchTurn();
                        gameplay.displayBoard();
                        refreshBoard();
                        buttons[lastClicked].setBackground(lastColors[lastClicked]);
                        buttons[i].setBackground(lastColors[i]);
                        checkIfPassant(i);
                        lastClicked = -1;
                        //check if promotion for pawn
                        checkIfPromotion(i);
                    } else {
                        refreshBoard();
                        lastClicked = -1;
                    }
                } else {
                    if (lastClicked == i) {
                        lastClicked = -1;
                        refreshBoard();
                    }
                    else if (gameplay.checkIfRightClick(i)) {
                        refreshBoard();
                        buttons[i].setBackground(stepColors[i]);
                        lastClicked = i;
                        gameplay.updateSteps();
                        for (int num: gameplay.getPiece(i).getPossSteps()) {
                            buttons[num].setBackground(stepColors[num]);
                            buttons[num].setIcon(imageManager.getImageAnim(gameplay.getPieceWhich(num), gameplay.isPieceWhite(num)));
                            if (num == getPassantStep() && getPassant() != -1) buttons[getPassant()].setIcon(imageManager.getImageAnim(gameplay.getPieceWhich(getPassant()), gameplay.isPieceWhite(getPassant())));
                        }
                    }
                }
                //System.out.println("click pos: " + i);
            }

        }

        //check if game is over
        if (gameplay.detectGameOver() != 0) {
            this.setButtonsEnabled(false);
            new EndFrame(gameplay.detectGameOver());
        }

    }

    // GETTERS SETTERS

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

    private void calcPassantStep(int index) {
        if (gameplay.getPiece(index).isWhite() && index < 55) {
            setPassantStep(index+8);
        } else if (!gameplay.getPiece(index).isWhite() && index > 7) {
            setPassantStep(index-8);
        } else {
            setPassantStep(-1);
        }
    }
}
