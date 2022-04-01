package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PromoFrame extends JFrame implements ActionListener {

    int which = 1;
    boolean isWhite = true;
    int index = 0;
    JPanel panel = new JPanel();
    JButton buttons[] = new JButton[4];
    ImageManager imageManager = new ImageManager();

    public PromoFrame(boolean isWhite, int pos) {
        initPromo(isWhite, pos);
    }

    private void initPromo(boolean isWhite, int pos) {
        this.setSize(400, 128);
        this.setLocationRelativeTo(Main.frame);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(null);
        this.setResizable(false);
        this.setTitle("choose promotion");

        //other stuff
        index = pos;
        this.isWhite = isWhite;

        panel.setBounds(0, 0, 400, 100);
        panel.setLayout(new GridLayout(1, 4, 0, 0));

        for (int i = 0; i < 4; i++) {
            buttons[i] = new JButton();
            buttons[i].setSize(100, 100);
            buttons[i].setBorderPainted(false);
            buttons[i].setOpaque(true);
            buttons[i].addActionListener(this);
            buttons[i].setVerticalAlignment(JButton.CENTER);
            buttons[i].setHorizontalAlignment(JButton.CENTER);
            buttons[i].setIcon(imageManager.getImage(i+1, isWhite));
            if (i % 2 == 0) buttons[i].setBackground(Main.color1);
            else buttons[i].setBackground(Main.color2);
            panel.add(buttons[i]);
        }

        Main.frame.setButtonsEnabled(false);

        //add to frame
        this.add(panel);

        this.setVisible(true);

        //while (true) {}
    }

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