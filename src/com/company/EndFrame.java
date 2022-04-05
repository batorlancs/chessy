package com.company;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class EndFrame extends JFrame implements ActionListener, MouseListener {

    JLabel label = new JLabel();
    JButton buttonRestart = new JButton("restart");
    JButton buttonExit = new JButton("exit");

    /////////////////////////////////////////////////////////////////////////////////////////
    // CONSTRUCTOR
    /////////////////////////////////////////////////////////////////////////////////////////
    public EndFrame(int winner) {
        initEndFrame(winner);
    }

    private void initEndFrame(int winner) {
        // create frame
        this.setSize(400, 128);
        this.setLocationRelativeTo(Main.frame);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setLayout(null);
        this.setTitle("game over");

        // create label with message
        label.setBounds(0, 0, 400, 50);
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setVerticalAlignment(JLabel.CENTER);
        label.setOpaque(true);
        label.setBackground(Main.color2);
        label.setForeground(Main.fontcolor1);
        label.setFont(Main.font2);
        if (winner == 1) label.setText("congratulations player white, you won!");
        else label.setText("congratulations player black, you won!");

        // create restart button
        buttonRestart.setBounds(0, 50, 200, 50);
        buttonRestart.addActionListener(this);
        buttonRestart.addMouseListener(this);
        buttonRestart.setOpaque(true);
        buttonRestart.setBackground(Main.color1);
        buttonRestart.setForeground(Main.color2);
        buttonRestart.setFont(Main.font2);
        buttonRestart.setHorizontalAlignment(JButton.CENTER);
        buttonRestart.setBorderPainted(false);

        // create exit button
        buttonExit.setBounds(200, 50, 200, 50);
        buttonExit.addActionListener(this);
        buttonExit.addMouseListener(this);
        buttonExit.setOpaque(true);
        buttonExit.setBackground(Main.color1);
        buttonExit.setForeground(Main.color2);
        buttonExit.setFont(Main.font2);
        buttonExit.setHorizontalAlignment(JButton.CENTER);
        buttonExit.setBorderPainted(false);

        //adding to frame
        this.add(label);
        this.add(buttonRestart);
        this.add(buttonExit);

        // freeze other window
        Main.frame.setOtherWindowisOpen(true);

        this.setVisible(true);
    }

    /////////////////////////////////////////////////////////////////////////////////////////
    // ACTION LISTENER
    /////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == buttonExit) {
            System.out.println("exiting program...");
            this.dispose();
            Main.frame.dispose();
            System.exit(0);
        }
        if (e.getSource() == buttonRestart) {
            Main.frame.setOtherWindowisOpen(false);
            Main.frame.restartGame();
            Main.frame.refreshEverything();
            this.dispose();
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    // --------------------------------------------------------------------------------------
    @Override
    public void mouseEntered(MouseEvent e) {
        if (e.getSource() == buttonRestart) {
            buttonRestart.setForeground(Main.fontcolor1);
            buttonRestart.setBackground(Main.color3);
        }
        if (e.getSource() == buttonExit) {
            buttonExit.setForeground(Main.fontcolor1);
            buttonExit.setBackground(Main.color3);
        }
    }

    // --------------------------------------------------------------------------------------
    @Override
    public void mouseExited(MouseEvent e) {
        if (e.getSource() == buttonRestart) {
            buttonRestart.setForeground(Main.color2);
            buttonRestart.setBackground(Main.color1);
        }
        if (e.getSource() == buttonExit) {
            buttonExit.setForeground(Main.color2);
            buttonExit.setBackground(Main.color1);
        }
    }
    /////////////////////////////////////////////////////////////////////////////////////////
}