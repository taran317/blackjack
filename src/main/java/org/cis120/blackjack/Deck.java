package org.cis120.blackjack;

public class Deck {

    for (Card.Suits s : Card.Suits.values()) {
        for (Ranks r : Ranks.values()) {
            Card c = new Card(s,r);
        }
    }
}
