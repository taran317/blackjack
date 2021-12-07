package org.cis120.blackjack;

/**
 * CIS 120 HW09 - Blackjack Demo
 * (c) University of Pennsylvania
 * Created by Bayley Tuch, Sabrina Green, and Nicolas Corona in Fall 2020.
 */

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * This class is a model for Blackjack.
 * <p>
 * This game adheres to a Model-View-Controller design framework.
 * This framework is very effective for turn-based games. We
 * STRONGLY recommend you review these lecture slides, starting at
 * slide 8, for more details on Model-View-Controller:
 * https://www.seas.upenn.edu/~cis120/current/files/slides/lec36.pdf
 * <p>
 * This model is completely independent of the view and controller.
 * This is in keeping with the concept of modularity! We can play
 * the whole game from start to finish without ever drawing anything
 * on a screen or instantiating a Java Swing object.
 * <p>
 * Run this file to see the main method play a game of Blackjack,
 * visualized with Strings printed to the console.
 */
public class Blackjack {
    private Card[][] board;
    private int[] bets;
    private int[] money;
    private int numPlayers;
    private int currPlayer;
    private boolean gameOver;
    private List<Card> deck;
    private boolean[] bust;

    /**
     * Constructor sets up game state.
     */
    public Blackjack(int players) {
        money = new int[players];
        for (int i = 0; i < money.length; i++) {
            money[i] = 1000;
        }
        reset(players);
    }

    /**
     * reset (re-)sets the game state to start a new game.
     */
    public void reset(int players) {
        board = new Card[10][players + 1];
        bets = new int[players];
        bets[0] = 100;
        bust = new boolean[players + 1];
        currPlayer = 0;
        numPlayers = players;
        gameOver = false;
        deck = new ArrayList<>();
        for (Suits s : Suits.values()) {
            for (Ranks r : Ranks.values()) {
                Card c = new Card(s, r);
                deck.add(c);
            }
        }
        Collections.shuffle(deck);
        board[0][0] = pop();
        for (int i = 1; i < board[0].length; i++) {
            board[0][i] = pop();
            board[1][i] = pop();
        }
    }

    public void bet(int player, int amount) {
        bets[player] = amount;
        money[player] -= amount;
    }

    public int calculateTotal(int player) {
        int aces = 0;
        int total = 0;
        for (int i = 0; i < board.length; i++) {
            if (board[i][player] != null) {
                switch (board[i][player].getRank()) {
                    case TWO:
                        total += 2;
                        break;
                    case THREE:
                        total += 3;
                        break;
                    case FOUR:
                        total += 4;
                        break;
                    case FIVE:
                        total += 5;
                        break;
                    case SIX:
                        total += 6;
                        break;
                    case SEVEN:
                        total += 7;
                        break;
                    case EIGHT:
                        total += 8;
                        break;
                    case NINE:
                        total += 9;
                        break;
                    case TEN:
                    case KING:
                    case JACK:
                    case QUEEN:
                        total += 10;
                        break;
                    case ACE:
                        total += 1;
                        aces++;
                        break;
                }
            }
        }
        for (int i = 0; i < aces; i++) {
            if (total + 10 <= 21) {
                total += 10;
            }
        }
        return total;
    }

    public boolean isBust(int player) {
        return bust[player];
    }

    private int calculateDealerTotal() {
        while (calculateTotal(0) <= 16) {
            if (hit(0)) {
                System.out.println("HERE ASF");
                break;
            }
        }
        return calculateTotal(0);
    }

    /**
     * printGameState prints the current game state
     * for debugging.
     */
    public void printGameState() {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                System.out.print(board[i][j]);
                if (j < 2) {
                    System.out.print(" | ");
                }
            }
            if (i < 2) {
                System.out.println("\n---------");
            }
        }
    }

    private Card pop() {
        Card r = null;
        r = deck.get(0);
        deck.remove(0);
        return r;
    }

    public int getNumPlayers() {
        return numPlayers;
    }

    /**
     * @param player
     * @return true if player bust or got a blackjack (turn ended)
     */
    public boolean hit(int player) {
        int newCardIndex = 0;
        if (calculateTotal(player) >= 21) {
            nextTurn();
            return true;
        }
        for (int i = 0; i < board.length; i++) {
            if (board[i][player] == null) {
                newCardIndex = i;
                break;
            }
        }
        board[newCardIndex][player] = pop();
        if (calculateTotal(player) >= 21) {
            bust(player);
            nextTurn();
            return true;
        }
        return false;
    }

    public void stand() {
        if (currPlayer == numPlayers) {
            gameOver = true;
            System.out.println("HEEEEREEEEEEEEEE");
            calculateDealerTotal();
            settle();
        }
        nextTurn();
    }

    private void bust(int player) {
        bust[player] = true;
    }

    public int[] settle() {
        System.out.println("IN SETTLE");
        gameOver = true;
        int[] output = new int[numPlayers];
        for (int player = 0; player < numPlayers; player++) {
            if (isBust(0) && !isBust(player)) {
                money[player] += 2 * bets[player];
                output[player] = 2;
            } else {
                int dealerTotal = calculateDealerTotal();
                if (calculateTotal(player) > dealerTotal && !isBust(player)) {
                    money[player] += 2 * bets[player];
                    output[player] = 2;
                } else if (calculateTotal(player) == dealerTotal && !isBust(player)) {
                    money[player] += bets[player];
                    output[player] = 1;
                }
            }
            output[player] = 0;
        }
        return output;
    }

    public int getCurrentPlayer() {
        return currPlayer + 1;
    }

    public void nextTurn() {
        if (currPlayer + 1 < numPlayers) {
            System.out.println("curr players " + currPlayer);
            System.out.println("total # of players " + numPlayers);
            currPlayer++;
        } else if (!gameOver) {
            System.out.println("SETTLING");
            gameOver = true;
            calculateDealerTotal();
        }
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public int getMoney(int player) {
        return money[player - 1];
    }

    public List<Card> getCards(int player) {
        List<Card> c = new ArrayList<>();
        for (int i = 0; i < board.length; i++) {
            if (board[i][player] != null) {
                c.add(board[i][player]);
            }
        }
        return c;
    }

    /**
     * This main method illustrates how the model is completely independent of
     * the view and controller. We can play the game from start to finish
     * without ever creating a Java Swing object.
     * <p>
     * This is modularity in action, and modularity is the bedrock of the
     * Model-View-Controller design framework.
     * <p>
     * Run this file to see the output of this method in your console.
     */
    public static void main(String[] args) {
        Blackjack t = new Blackjack(5);
        t.bet(1, 100);
        t.bet(2, 200);
        t.bet(3, 50);
        t.bet(4, 100);
        t.bet(5, 100);
        for (int i = 0; i < 2; i++) {
            if (t.hit(1)) {
                break;
            }
        }

        for (int i = 0; i < 3; i++) {
            if (t.hit(2)) {
                break;
            }
        }

        for (int i = 0; i < 1; i++) {
            if (t.hit(3)) {
                break;
            }
        }

        for (int i = 0; i < 6; i++) {
            if (t.hit(4)) {
                break;
            }
        }

        for (int i = 0; i < 2; i++) {
            if (t.hit(5)) {
                break;
            }
        }

        t.settle();

        System.out.println();
        System.out.println();
        System.out.println("Winner is: ");
    }
}
