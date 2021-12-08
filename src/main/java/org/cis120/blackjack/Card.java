package org.cis120.blackjack;

import java.io.Serializable;

public class Card implements Serializable {
    private static final long serialVersionUID = 349786514855787890L;
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

    public Suits getSuit()
    {
        return suit;
    }

    @Override
    public String toString() {
        return rank.toString() + " of " + suit.toString();
    }
}
