package org.cis120.blackjack;

public enum Ranks {
    TWO(2, 2),
    THREE(3, 3),
    FOUR(4, 4),
    FIVE(5, 5),
    SIX(6, 6),
    SEVEN(7, 7),
    EIGHT(8,8),
    NINE(9, 9),
    TEN(10, 10),
    JACK(11, 10),
    QUEEN(12, 10),
    KING(13, 10),
    ACE(14, 11);

    private final int val;
    private final int bjVal;

    Ranks(int val, int bjVal) {
        this.val = val;
        this.bjVal = bjVal;
    }

    public int getVal() {
        return val;
    }

    public int getBjVal() {
        return bjVal;
    }
}
