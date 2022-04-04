package com.company;

import javax.swing.*;
import java.awt.*;

public class ImageManager {

    private ImageIcon[] white = new ImageIcon[6];
    private ImageIcon[] black = new ImageIcon[6];
    private ImageIcon[] whiteAnim = new ImageIcon[6];
    private ImageIcon[] blackAnim = new ImageIcon[6];
    private ImageIcon step; // possible step indicating circle
    private ImageIcon[] passantAnim = new ImageIcon[2]; // animation for en passant
    private ImageIcon[] castlingAnim = new ImageIcon[2]; // animation for castling
    private final int imageScale = 80; // resizing

    /////////////////////////////////////////////////////////////////////////////////////////
    // CONSTRUCTOR
    /////////////////////////////////////////////////////////////////////////////////////////
    public ImageManager() {
        init();
    }

    private void init() {
        // INPUT ALL IMAGE SOURCES AND RESIZE THEM
        Image temp;
        for (int i = 0; i < 6; i++) {
            //white
            white[i] = new ImageIcon(getClass().getResource("/images/sticks/feher" + i + ".png"));
            temp = white[i].getImage().getScaledInstance(imageScale, imageScale, Image.SCALE_SMOOTH);
            white[i] = new ImageIcon(temp);
            //black
            black[i] = new ImageIcon(getClass().getResource("/images/sticks/fekete" + i + ".png"));
            temp = black[i].getImage().getScaledInstance(imageScale, imageScale, Image.SCALE_SMOOTH);
            black[i] = new ImageIcon(temp);

            //whiteAnim
            whiteAnim[i] = new ImageIcon(getClass().getResource("/images/animations/feheranim" + i + ".png"));
            temp = whiteAnim[i].getImage().getScaledInstance(imageScale, imageScale, Image.SCALE_SMOOTH);
            whiteAnim[i] = new ImageIcon(temp);
            //blackAnim
            blackAnim[i] = new ImageIcon(getClass().getResource("/images/animations/feketeanim" + i + ".png"));
            temp = blackAnim[i].getImage().getScaledInstance(imageScale, imageScale, Image.SCALE_SMOOTH);
            blackAnim[i] = new ImageIcon(temp);
            
        }

        step = new ImageIcon(getClass().getResource("/images/animations/anim.png"));
        temp = step.getImage().getScaledInstance(imageScale, imageScale, Image.SCALE_SMOOTH);
        step = new ImageIcon(temp);

        for (int i = 0; i < 2; i++) {
            passantAnim[i] = new ImageIcon(getClass().getResource("/images/moreAnimations/passant" + i + ".png"));
            temp = passantAnim[i].getImage().getScaledInstance(imageScale, imageScale, Image.SCALE_SMOOTH);
            passantAnim[i] = new ImageIcon(temp);
        }

        for (int i = 0; i < 2; i++) {
            castlingAnim[i] = new ImageIcon(getClass().getResource("/images/moreAnimations/castling" + i + ".png"));
            temp = castlingAnim[i].getImage().getScaledInstance(imageScale, imageScale, Image.SCALE_SMOOTH);
            castlingAnim[i] = new ImageIcon(temp);
        }
    }
    /////////////////////////////////////////////////////////////////////////////////////////


    // --------------------------------------------------------------------------------------
    // get the correct image based on color and kind
    // --------------------------------------------------------------------------------------
    public ImageIcon getImage(int which, boolean isWhite) {
        if (isWhite) return white[which];
        else return black[which];
    }

    // --------------------------------------------------------------------------------------
    // get the correct image animation based on color and kind
    // --------------------------------------------------------------------------------------
    public ImageIcon getImageAnim(int which, boolean isWhite) {
        if (which == -1) {
            return step;
        }
        if (isWhite) return whiteAnim[which];
        else return blackAnim[which];
    }

    // --------------------------------------------------------------------------------------
    // get passant animation image
    // --------------------------------------------------------------------------------------
    public ImageIcon getImagePassant(boolean isWhite) {
        if (isWhite) return passantAnim[0];
        return passantAnim[1];
    }

    // --------------------------------------------------------------------------------------
    // get castling animation image
    // --------------------------------------------------------------------------------------
    public ImageIcon getImageCastling(boolean isWhite) {
        if (isWhite) return castlingAnim[0];
        return castlingAnim[1];
    }
}
