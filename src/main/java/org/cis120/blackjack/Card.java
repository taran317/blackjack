package org.cis120.blackjack;

public class Card {
    private Suits suit;
    private Ranks rank;

    public Card(Suits suit, Ranks rank) {
        this.suit = suit;
        this.rank = rank;
    }

    public Ranks getRank()
    {
        return rank;
    }
}
