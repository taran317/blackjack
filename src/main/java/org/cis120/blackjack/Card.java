package org.cis120.blackjack;

public class Card {

    public enum Suits {
        SPADES,
        HEARTS,
        DIAMONDS,
        CLUBS;
    }

    private Suits suit;
    private int rank;

    public Card(Suits suit, int rank) {
        this.suit = suit;
        this.rank = rank;
    }
}
