package org.cis120.blackjack;

/**
 * CIS 120 HW09 - Blackjack Demo
 * (c) University of Pennsylvania
 * Created by Bayley Tuch, Sabrina Green, and Nicolas Corona in Fall 2020.
 */

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.List;
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
public class GameBoard extends JPanel implements Serializable {

    private static final long serialVersionUID = -1314492449393479677L;
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
        setBackground(Color.decode("#ccffcc"));

        // Enable keyboard focus on the court area. When this component has the
        // keyboard focus, key events are handled by its key listener.
        setFocusable(true);
        ttt = new Blackjack(4); // initializes model for the game
        // try {
        // ttt = Blackjack.readState();
        // } catch (IOException | ClassNotFoundException e) {
        // e.printStackTrace();
        // }
        status = statusInit; // initializes the status JLabel
    }

    public void bet(int amount) {
        if (ttt.isBetting()) {
            ttt.bet(ttt.getCurrentPlayer() - 1, amount);
            repaint();

            // Makes sure this component has keyboard/mouse focus
            requestFocusInWindow();
            updateStatus();
        }
    }

    public void allIn() {
        bet(ttt.getMoney(ttt.getCurrentPlayer()));
    }

    public void buyIn() {
        if (ttt.isBetting()) {
            ttt.buyIn(ttt.getCurrentPlayer());
            repaint();
            // Makes sure this component has keyboard/mouse focus
            requestFocusInWindow();
            updateStatus();
        }
    }

    /**
     * (Re-)sets the game to its initial state.
     */
    public void nextRound() {
        ttt.reset(ttt.getNumPlayers());
        status.setText("Player 1's Turn");
        repaint();

        // Makes sure this component has keyboard/mouse focus
        requestFocusInWindow();
    }

    public void reset(int n) {
        ttt.reset(n);
        status.setText("Player 1's Turn");
        repaint();

        // Makes sure this component has keyboard/mouse focus
        requestFocusInWindow();
    }

    public void reset() {
        ttt.reset();
        status.setText("Player 1's Turn");
        repaint();

        // Makes sure this component has keyboard/mouse focus
        requestFocusInWindow();
    }

    public void hit() {
        if (!ttt.isGameOver() && !ttt.isBetting()) {
            ttt.hit(ttt.getCurrentPlayer());
            repaint();
            updateStatus();
        }
    }

    public void stand() {
        if (!ttt.isGameOver() && !ttt.isBetting()) {
            ttt.stand();
            repaint();
            updateStatus();
        }
    }

    /**
     * Updates the JLabel to reflect the current state of the game.
     */
    private void updateStatus() {
        // System.out.println("STATUS UPDATE");
        if (!ttt.isGameOver()) {
            status.setText("Player " + ttt.getCurrentPlayer() + "'s Turn");
        } else {
            status.setText("Game over! Play again!");
        }
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
        // System.out.println("NEW CYCLE");
        super.paintComponent(g);
        g.setFont(new Font("TimesRoman", Font.PLAIN, 40));
        g.setColor(Color.BLACK);
        g.drawString("Dealer", 665, 540);
        // Draws board grid
        g.drawLine(0, 500, BOARD_WIDTH, 500);
        int num = ttt.getNumPlayers();
        int playerWidth = BOARD_WIDTH / num;
        // System.out.println("dealer's total: " + ttt.calculateTotal(0));
        for (int player = 1; player <= num; player++) {
            int x = playerWidth / 2 - 85 + playerWidth * (player - 1);
            g.drawString("Player " + player, x, 50);
            if (player == ttt.getCurrentPlayer() && !ttt.isGameOver() && ttt.isBetting()) {
                g.setColor(Color.ORANGE);
                g.fillOval(x + 150, 22, 30, 30);
                g.setColor(Color.BLACK);
            }
            if (player == ttt.getCurrentPlayer() && !ttt.isGameOver() && !ttt.isBetting()) {
                g.setColor(Color.BLUE);
                g.fillOval(x + 150, 22, 30, 30);
                g.setColor(Color.BLACK);
            }
            g.setFont(new Font("TimesRoman", Font.PLAIN, 20));
            if (!ttt.isBetting()) {
                int total = ttt.calculateTotal(player);
                if (total > 21) {
                    g.drawString("BUST", x - 44, 150);
                } else if (total == 21 && ttt.getCards(player).size() == 2) {
                    g.setFont(new Font("TimesRoman", Font.BOLD, 20));
                    g.drawString("BLACKJACK", x + 15, 250);
                    g.setColor(getBackground());
                    g.fillOval(x + 150, 22, 30, 30);
                    g.setColor(Color.BLACK);
                    while (ttt.getCurrentPlayer() == player && !ttt.isGameOver()
                            && !ttt.isBetting()) {
                        ttt.nextTurn();
                        updateStatus();
                    }
                } else {
                    g.setFont(new Font("TimesRoman", Font.BOLD, 30));
                    g.drawString("" + ttt.calculateTotal(player), x - 44, 150);
                    g.setFont(new Font("TimesRoman", Font.PLAIN, 20));
                }
            } else {
                int total = ttt.getCards(player).get(0).getRank().getBjVal();
                printTotal(g, x - 44, 150, total);
            }
            g.setFont(new Font("TimesRoman", Font.PLAIN, 20));
            g.drawString("Money: " + ttt.getMoney(player), x - 50, 80);
            if (!ttt.isBetting()) {
                g.drawString("Bet: " + ttt.getBet(player), x + 120, 80);
            }
            g.setFont(new Font("TimesRoman", Font.PLAIN, 40));
            g.drawLine(playerWidth * player, 0, playerWidth * player, 500);
            int y = 90;
            // System.out.println(player + ": " + ttt.getCards(player).size() + "total: " +
            // ttt.calculateTotal(player));
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
            y -= 110;
            if (ttt.isBetting()) {
                paintBackCard(g, x + 95, y, 60);
            }
        }
        int x = 475;
        int y = 545;
        int y0 = y + 75;
        if (!ttt.isGameOver()) {
            paintBackCard(g, x + 120, y, 100);
        }
        int total = ttt.calculateTotal(0);
        // System.out.println("CURRENT :" + total);
        // System.out.println("SIZE :" + ttt.getCards(0).size());
        g.setFont(new Font("TimesRoman", Font.PLAIN, 20));
        List<Card> dealerCards = ttt.getCards(0);
        paintDealerCard(g, dealerCards.get(0), x, y);
        Boolean temp = dealerCards.size() == 1;
        // System.out.println("bool 1: " + temp);
        Boolean temp1 = ttt.calculateTotal(0) == 10 || ttt.calculateTotal(0) == 11;
        // System.out.println("bool 2: " + temp1);
        if (total > 21) {
            g.drawString("BUST", x - 100, y0);
            ttt.bust(0);
        } else if (dealerCards.size() == 1 &&
                (ttt.calculateTotal(0) == 10 || ttt.calculateTotal(0) == 11)
                && !ttt.isBetting()) {
            ttt.hit(0);
            // System.out.println("card 1: " + ttt.getCards(0).get(0).toString());
            // System.out.println("card 2: " + ttt.getCards(0).get(1).toString());
            // System.out.println("2 card total: " + ttt.calculateTotal(0));
            if (ttt.calculateTotal(0) == 21) {
                // System.out.println("card 1: " + ttt.getCards(0).get(0).toString());
                // System.out.println("card 2: " + ttt.getCards(0).get(1).toString());
                if (ttt.calculateTotal(0) == 21 && ttt.getCards(0).size() == 2) {
                    g.setFont(new Font("TimesRoman", Font.BOLD, 20));
                    g.drawString("BLACKJACK", x - 150, y0);
                    ttt.dealerBlackjack();
                }
                int secondCardXCoor = x + 120;
                paintDealerCard(g, ttt.getCards(0).get(1), secondCardXCoor, y);
                updateStatus();
            } else {
                printTotal(g, x - 50, y0, total);
            }
        } else if (!ttt.isGameOver()) {
            printTotal(g, x - 50, y0, total);
        } else {
            printTotal(g, x - 50, y0, ttt.calculateTotal(0));
        }
        gameOverActions(g, x, y, playerWidth);
        try {
            writeState(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void printTotal(Graphics g, int x, int y, int total) {
        g.setFont(new Font("TimesRoman", Font.BOLD, 30));
        g.drawString("" + total, x, y);
        g.setFont(new Font("TimesRoman", Font.PLAIN, 20));
    }

    public void gameOverActions(Graphics g, int x, int y, int playerWidth) {
        // System.out.println("END OF GAME ACTIONS");
        if (ttt.isGameOver()) {
            // System.out.println("Num of dealer cards: " + ttt.getCards(0));
            for (Card c : ttt.getCards(0)) {
                paintDealerCard(g, c, x, y);
                x += 120;
            }
            int[] outcome = ttt.settle();
            g.setFont(new Font("TimesRoman", Font.BOLD, 40));
            // System.out.println("Outcome: " + outcome);
            if (!ttt.isBetting()) {
                for (int player = 0; player < ttt.getNumPlayers(); player++) {
                    int w = playerWidth / 2 - 65 + playerWidth * player;
                    // System.out.println("player " + player + " outcome: " + outcome[player]);
                    switch (outcome[player]) {
                        case 0:
                            g.setColor(Color.RED);
                            g.drawString("Loss", w, 470);
                            break;
                        case 1:
                            g.setColor(Color.BLACK);
                            g.drawString("Push (tie)", w, 470);
                            break;
                        case 2:
                            g.setColor(Color.decode("#004d00"));
                            g.drawString("Win!", w, 470);
                            break;
                        default:
                            break;
                    }
                }
            }
            g.setFont(new Font("TimesRoman", Font.PLAIN, 20));
        }
        // System.out.println("Is game over? " + ttt.isGameOver());
    }

    public void paintPlayerCard(Graphics g, Card c, int x, int y) {
        paintCard(g, c, x + 15, y, 60);
    }

    public void paintDealerCard(Graphics g, Card c, int x, int y) {
        paintCard(g, c, x, y, 100);
    }

    public void paintCard(Graphics g, Card c, int x, int y, int width) {
        BufferedImage img = null;
        String imgFile = "files/" + c.getRank().getVal() + "_of_" + c.getSuit().toString()
                + ".png";
        try {
            if (img == null) {
                img = ImageIO.read(new File(imgFile));
            }
        } catch (IOException e) {
            // System.out.println("Internal Error:" + e.getMessage());
        }

        g.drawImage(img, x, y, width, (int) (726.0 / 500 * width), null);
    }

    public void paintBackCard(Graphics g, int x, int y, int width) {
        int w = width;
        BufferedImage img = null;
        String imgFile = "files/back.png";
        try {
            if (img == null) {
                img = ImageIO.read(new File(imgFile));
            }
        } catch (IOException e) {
            // System.out.println("Internal Error:" + e.getMessage());
        }
        g.drawImage(img, x, y, w, (int) (726.0 / 500 * w), null);
    }

    public static GameBoard readState() throws IOException, ClassNotFoundException {
        ObjectInputStream o = new ObjectInputStream(new FileInputStream("state.bin"));
        GameBoard g = (GameBoard) o.readObject();
        return g;
    }

    public static void writeState(GameBoard g) throws IOException {
        ObjectOutputStream o = new ObjectOutputStream(new FileOutputStream("state.bin"));
        o.writeObject(g);
    }

    @Override
    public String toString() {
        return "GameBoard{" +
                "ttt=" + ttt.toString() +
                ", status=" + status.toString() +
                '}';
    }

    /**
     * Returns the size of the game board.
     */
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(BOARD_WIDTH, BOARD_HEIGHT);
    }
}
