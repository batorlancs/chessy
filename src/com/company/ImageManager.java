package com.company;

import javax.swing.*;
import java.awt.*;

public class ImageManager {

    private ImageIcon[] white = new ImageIcon[6];
    private ImageIcon[] black = new ImageIcon[6];
    private ImageIcon[] whiteAnim = new ImageIcon[6];
    private ImageIcon[] blackAnim = new ImageIcon[6];
    private ImageIcon step; // possible step indicating circle
    private ImageIcon square;
    private ImageIcon square2;
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
        for (int i = 0; i < 6; i++) {
            //white
            white[i] = new ImageIcon(getClass().getResource("/images/sticks/feher" + i + ".png"));
            white[i] = resizeImage(white[i]);
            //black
            black[i] = new ImageIcon(getClass().getResource("/images/sticks/fekete" + i + ".png"));
            black[i] = resizeImage(black[i]);

            //whiteAnim
            whiteAnim[i] = new ImageIcon(getClass().getResource("/images/animations/feheranim" + i + ".png"));
            whiteAnim[i] = resizeImage(whiteAnim[i]);
            //blackAnim
            blackAnim[i] = new ImageIcon(getClass().getResource("/images/animations/feketeanim" + i + ".png"));
            blackAnim[i] = resizeImage(blackAnim[i]);
        }

        step = new ImageIcon(getClass().getResource("/images/animations/anim.png"));
        step = resizeImage(step);

        square = new ImageIcon(getClass().getResource("/images/animations/stickanim.png"));
        square = resizeImage(square);

        square2 = new ImageIcon(getClass().getResource("/images/animations/stickanim2.png"));
        square2 = resizeImage(square2);

        for (int i = 0; i < 2; i++) {
            passantAnim[i] = new ImageIcon(getClass().getResource("/images/moreAnimations/passant" + i + ".png"));
            passantAnim[i] = resizeImage(passantAnim[i]);
        }

        for (int i = 0; i < 2; i++) {
            castlingAnim[i] = new ImageIcon(getClass().getResource("/images/moreAnimations/castling" + i + ".png"));
            castlingAnim[i] = resizeImage(castlingAnim[i]);
        }
    }

    private ImageIcon resizeImage(ImageIcon im) {
        Image temp = im.getImage().getScaledInstance(imageScale, imageScale, Image.SCALE_SMOOTH);
        ImageIcon newIm = new ImageIcon(temp);
        return newIm;
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

    // --------------------------------------------------------------------------------------
    // get castling animation image
    // --------------------------------------------------------------------------------------
    public ImageIcon getImageSquare() {
        return square;
    }
}
