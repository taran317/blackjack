package org.cis120.blackjack;

/**
 * CIS 120 HW09 - Blackjack Demo
 * (c) University of Pennsylvania
 * Created by Bayley Tuch, Sabrina Green, and Nicolas Corona in Fall 2020.
 */

import java.awt.*;
import javax.swing.*;

/**
 * This class sets up the top-level frame and widgets for the GUI.
 * 
 * This game adheres to a Model-View-Controller design framework. This
 * framework is very effective for turn-based games. We STRONGLY
 * recommend you review these lecture slides, starting at slide 8,
 * for more details on Model-View-Controller:
 * https://www.seas.upenn.edu/~cis120/current/files/slides/lec37.pdf
 * 
 * In a Model-View-Controller framework, Game initializes the view,
 * implements a bit of controller functionality through the reset
 * button, and then instantiates a GameBoard. The GameBoard will
 * handle the rest of the game's view and controller functionality, and
 * it will instantiate a Blackjack object to serve as the game's model.
 */
public class RunBlackjack implements Runnable {
    public void run() {
        // NOTE: the 'final' keyword denotes immutability even for local variables.

        // Top-level frame in which game components live
        final JFrame frame = new JFrame("Blackjack");
        frame.getContentPane().setBackground(Color.YELLOW);
        frame.setLocation(1700, 1000);

        // Status panel
        final JPanel status_panel = new JPanel();
        frame.add(status_panel, BorderLayout.SOUTH);
        final JLabel status = new JLabel("Setting up...");
        status_panel.add(status);

        // Game board
        final GameBoard board = new GameBoard(status);
        frame.add(board, BorderLayout.CENTER);

        // Reset button
        final JPanel control_panel = new JPanel();
        frame.add(control_panel, BorderLayout.NORTH);

        // Note here that when we add an action listener to the reset button, we
        // define it as an anonymous inner class that is an instance of
        // ActionListener with its actionPerformed() method overridden. When the
        // button is pressed, actionPerformed() will be called.
        final JButton buyIn = new JButton("Buy In 1000");
        buyIn.addActionListener(e -> board.buyIn());
        control_panel.add(buyIn);

        final JButton bet100 = new JButton("Bet 100");
        bet100.addActionListener(e -> board.bet(100));
        control_panel.add(bet100);

        final JButton bet200 = new JButton("Bet 200");
        bet200.addActionListener(e -> board.bet(200));
        control_panel.add(bet200);

        final JButton bet500 = new JButton("Bet 500");
        bet500.addActionListener(e -> board.bet(500));
        control_panel.add(bet500);

        final JButton allIn = new JButton("ALL IN");
        allIn.addActionListener(e -> board.allIn());
        control_panel.add(allIn);

        final JButton hit = new JButton("Hit");
        hit.addActionListener(e -> board.hit());
        control_panel.add(hit);

        final JButton stand = new JButton("Stand");
        stand.addActionListener(e -> board.stand());
        control_panel.add(stand);

        final JButton reset = new JButton("Next Round");
        reset.addActionListener(e -> board.reset());
        control_panel.add(reset);

        final JButton one = new JButton("1 Player");
        one.addActionListener(e -> board.reset(1));
        control_panel.add(one);

        final JButton two = new JButton("2 Players");
        two.addActionListener(e -> board.reset(2));
        control_panel.add(two);

        final JButton three = new JButton("3 Players");
        three.addActionListener(e -> board.reset(3));
        control_panel.add(three);

        final JButton four = new JButton("4 Players");
        four.addActionListener(e -> board.reset(4));
        control_panel.add(four);

        final JButton five = new JButton("5 Players");
        five.addActionListener(e -> board.reset(5));
        control_panel.add(five);

        // Put the frame on the screen
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        // Start the game
        board.reset();
    }
}