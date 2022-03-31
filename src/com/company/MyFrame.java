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
    private int lastClicked = 0;

    private ImageManager imageManager = new ImageManager();
    private Gameplay gameplay = new Gameplay();

    MyFrame() {
        initFrame();
    }

    private void initFrame() {
        // frame setup
        this.setSize(640, 668);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(null);
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
            if ((i / 8) % 2 == 0) {
                if (i % 8 % 2 == 0) colors[i] = color1;
                else colors[i] = color2;
            } else {
                if (i % 8 % 2 == 1) colors[i] =  color1;
                else colors[i] = color2;
            }
            if ((i / 8) % 2 == 0) {
                if (i % 8 % 2 == 0) stepColors[i] = color4;
                else stepColors[i] = color5;
            } else {
                if (i % 8 % 2 == 1) stepColors[i] =  color4;
                else stepColors[i] = color5;
            }
            panel.add(buttons[i]);
        }

        //setting up buttons
        restartGame();

        // gameplay
        lastClicked = -1;

        //adding to panel
        this.add(panel);

        //set visible / last in constructor
        this.setVisible(true);
    }

    private void restartGame() {
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
                        }
                        gameplay.getPiece(lastClicked).move1(i);
                        //refresh all arrays and stuff
                        gameplay.refreshColorCheck();
                        gameplay.refreshPieceCheck();
                        gameplay.updateSteps();
                        gameplay.switchTurn();
                        gameplay.displayBoard();
                    }
                    refreshBoard();
                    refreshColorBoard();
                    lastClicked = -1;

                } else {
                    if (gameplay.checkIfRightClick(i)) {
                        refreshColorBoard();
                        buttons[i].setBackground(color3);
                        lastClicked = i;
                        gameplay.updateSteps();
                        for (int num: gameplay.getPiece(i).getPossSteps()) {
                            buttons[num].setBackground(stepColors[num]);
                        }
                    }
                }
                //System.out.println("click pos: " + i);
            }
        }
    }
}
