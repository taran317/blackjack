package org.cis120.blackjack;

/**
 * CIS 120 HW09 - Blackjack Demo
 * (c) University of Pennsylvania
 * Created by Bayley Tuch, Sabrina Green, and Nicolas Corona in Fall 2020.
 */

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;

/**
 * This class instantiates a Blackjack object, which is the model for the game.
 * As the user clicks the game board, the model is updated. Whenever the model
 * is updated, the game board repaints itself and updates its status JLabel to
 * reflect the current state of the model.
 * <p>
 * This game adheres to a Model-View-Controller design framework. This
 * framework is very effective for turn-based games. We STRONGLY
 * recommend you review these lecture slides, starting at slide 8,
 * for more details on Model-View-Controller:
 * https://www.seas.upenn.edu/~cis120/current/files/slides/lec37.pdf
 * <p>
 * In a Model-View-Controller framework, GameBoard stores the model as a field
 * and acts as both the controller (with a MouseListener) and the view (with
 * its paintComponent method and the status JLabel).
 */
@SuppressWarnings("serial")
public class GameBoard extends JPanel {

    private Blackjack ttt; // model for the game
    private JLabel status; // current status text

    // Game constants
    public static final int BOARD_WIDTH = 1440;
    public static final int BOARD_HEIGHT = 700;

    /**
     * Initializes the game board.
     */
    public GameBoard(JLabel statusInit) {
        // creates border around the court area, JComponent method
        setBorder(BorderFactory.createLineBorder(Color.BLACK));

        // Enable keyboard focus on the court area. When this component has the
        // keyboard focus, key events are handled by its key listener.
        setFocusable(true);

        ttt = new Blackjack(6); // initializes model for the game
        status = statusInit; // initializes the status JLabel

        /*
         * Listens for mouseclicks. Updates the model, then updates the game
         * board based off of the updated model.
         */
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                Point p = e.getPoint();

                // updates the model given the coordinates of the mouseclick
//                ttt.playTurn(p.x / 100, p.y / 100);


                updateStatus(); // updates the status JLabel
                repaint(); // repaints the game board
            }
        });
    }

    /**
     * (Re-)sets the game to its initial state.
     */
    public void reset() {
        ttt.reset(ttt.getNumPlayers());
        status.setText("Player " + ttt.getCurrentPlayer() + "'s Turn");
        repaint();

        // Makes sure this component has keyboard/mouse focus
        requestFocusInWindow();
    }

    public void hit() {
        ttt.hit(ttt.getCurrentPlayer());
        repaint();
        updateStatus();
    }

    public void stay() {
        ttt.stay(ttt.getCurrentPlayer());
        updateStatus();
    }

    /**
     * Updates the JLabel to reflect the current state of the game.
     */
    private void updateStatus() {
        System.out.println("STATUS UPDATE");
        status.setText(String.format("Player %d's Turn", ttt.getCurrentPlayer()));
    }

    /**
     * Draws the game board.
     * <p>
     * There are many ways to draw a game board. This approach
     * will not be sufficient for most games, because it is not
     * modular. All of the logic for drawing the game board is
     * in this method, and it does not take advantage of helper
     * methods. Consider breaking up your paintComponent logic
     * into multiple methods or classes, like Mushroom of Doom.
     */
    @Override
    public void paintComponent(Graphics g) {
        System.out.println("NEW CYCLE");
        super.paintComponent(g);
        g.setFont(new Font("TimesRoman", Font.PLAIN, 40));
        // Draws board grid
        g.drawLine(0, 500, BOARD_WIDTH, 500);
        int num = ttt.getNumPlayers();
        int playerWidth = BOARD_WIDTH / num;
        for (int player = 1; player <= num; player++) {
            int x = playerWidth / 2 - 85 + playerWidth * (player - 1);
            g.drawString("Player " + player, x, 50);
            g.drawLine(playerWidth * player, 0, playerWidth * player, 500);
            int y = 90;
            System.out.println(player + ": " + ttt.getCards(player).size() + "total: " + ttt.calculateTotal(player));
            boolean left = true;
            for (Card c : ttt.getCards(player)) {
                if (left) {
                    paintPlayerCard(g, c, x + 10, y);
                } else {
                    paintPlayerCard(g, c, x + 80, y);
                    y += 110;
                }
                left = !left;
            }
        }
        int x = 300;
        int y = 530;
        for (Card c : ttt.getCards(0)) {
            paintDealerCard(g, c, x, y);
            x += 120;
        }
        g.drawString("Dealer", 700, 540);

    }

    public void paintPlayerCard(Graphics g, Card c, int x, int y) {
        paintCard(g, c, x, y, 60);
    }

    public void paintDealerCard(Graphics g, Card c, int x, int y) {
        paintCard(g, c, x, y, 100);
    }

    public void paintCard(Graphics g, Card c, int x, int y, int width) {
        BufferedImage img = null;
        String IMG_FILE = "files/" + c.getRank().getVal() + "_of_" + c.getSuit().toString() + ".png";
        try {
            if (img == null) {
                img = ImageIO.read(new File(IMG_FILE));
            }
        } catch (IOException e) {
            System.out.println("Internal Error:" + e.getMessage());
        }

        g.drawImage(img, x, y, width, (int) (726.0 / 500 * width), null);
    }


    /**
     * Returns the size of the game board.
     */
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(BOARD_WIDTH, BOARD_HEIGHT);
    }
}
