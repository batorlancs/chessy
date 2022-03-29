package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MyFrame extends JFrame implements ActionListener {

    private JPanel panel = new JPanel();
    private JButton[] buttons = new JButton[64];
    public Color color1 = new Color(150, 181, 110);
    public Color color2 = new Color(207, 214, 201);
    public Color color3 = new Color(233, 87, 87);
    public Color lastBackground = color1;
    private int lastClicked = 0;

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
            if ((i / 8) % 2 == 0) {
                if (i % 8 % 2 == 0) buttons[i].setBackground(color1);
                else buttons[i].setBackground(color2);
            } else {
                if (i % 8 % 2 == 1) buttons[i].setBackground(color1);
                else buttons[i].setBackground(color2);
            }
            panel.add(buttons[i]);
        }


        //adding to panel
        this.add(panel);

        //set visible / last in contructor
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        for (int i = 0; i < 64; i++) {
            //if you hit any in the thing
            if (e.getSource() == buttons[i]) {
                buttons[lastClicked].setBackground(lastBackground);
                lastBackground = buttons[i].getBackground();
                buttons[i].setBackground(color3);
                lastClicked = i;
            }
        }
    }
}
