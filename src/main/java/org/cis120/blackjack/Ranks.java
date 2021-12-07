package org.cis120.blackjack;

public enum Ranks {
    TWO(2),
    THREE(3),
    FOUR(4),
    FIVE(5),
    SIX(6),
    SEVEN(7),
    EIGHT(8),
    NINE(9),
    TEN(10),
    JACK(11),
    QUEEN(12),
    KING(13),
    ACE(14);

    private final int val;

    Ranks(int val) {
        this.val = val;
    }

    public int getVal() {
        return val;
    }
}
