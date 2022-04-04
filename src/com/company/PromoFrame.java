package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PromoFrame extends JFrame implements ActionListener {

    int which = 1; // choice of promotion
    boolean isWhite = true; // is piece white or black
    int index = 0; // position of piece

    JPanel panel = new JPanel();
    JButton buttons[] = new JButton[4];
    ImageManager imageManager = new ImageManager();

    /////////////////////////////////////////////////////////////////////////////////////////
    // CONSTRUCTOR
    /////////////////////////////////////////////////////////////////////////////////////////
    public PromoFrame(boolean isWhite, int pos) {
        initPromo(isWhite, pos);
    }

    private void initPromo(boolean isWhite, int pos) {
        // creating frame
        this.setSize(400, 128);
        this.setLocationRelativeTo(Main.frame);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(null);
        this.setResizable(false);
        this.setTitle("choose promotion");

        // storing given values
        index = pos;
        this.isWhite = isWhite;

        // setting up grid panel
        panel.setBounds(0, 0, 400, 100);
        panel.setLayout(new GridLayout(1, 4, 0, 0));

        // creating buttons
        for (int i = 0; i < 4; i++) {
            buttons[i] = new JButton();
            buttons[i].setSize(100, 100);
            buttons[i].setBorderPainted(false);
            buttons[i].setOpaque(true);
            buttons[i].addActionListener(this);
            buttons[i].setVerticalAlignment(JButton.CENTER);
            buttons[i].setHorizontalAlignment(JButton.CENTER);
            buttons[i].setIcon(imageManager.getImage(i+1, isWhite));
            // grid coloring, chess pattern like
            if (i % 2 == 0) buttons[i].setBackground(Main.color1);
            else buttons[i].setBackground(Main.color2);
            panel.add(buttons[i]);
        }

        // disable all other buttons on the game frame (MyFrame Class)
        Main.frame.setButtonsEnabled(false);

        //add to frame
        this.add(panel);

        this.setVisible(true);
    }
    /////////////////////////////////////////////////////////////////////////////////////////
    // ACTION LISTENER
    /////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public void actionPerformed(ActionEvent e) {
        for (int i = 0; i < 4; i++) {
            if (e.getSource() == buttons[i]) {
                which = i+1;
                Main.frame.setButtonsEnabled(true);
                Main.frame.callPromotion(index, which, isWhite);
                Main.frame.refreshEverything();
                this.setVisible(false);
                this.dispose();
            }
        }
    }
}