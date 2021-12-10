package org.cis120.blackjack;

import org.junit.jupiter.api.*;

import java.util.ArrayList;

import static org.cis120.blackjack.Ranks.*;
import static org.cis120.blackjack.Suits.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * You can use this file (and others) to test your
 * implementation.
 */

public class GameTest {

    public static Blackjack setupWithFivePlayers() {
        Blackjack t = new Blackjack(5);
        t.bet(0, 100);
        t.bet(1, 200);
        t.bet(2, 50);
        t.bet(3, 100);
        t.bet(4, 100);
        return t;
    }

    public static void hitTimesTillBust(Blackjack t, int player) {
        for (int i = 0; i < 10; i++) {
            t.hit(player);
        }
    }

    public static Blackjack getCustomInstance() {
        Card[][] arr = { { new Card(SPADES, FIVE), new Card(CLUBS, TWO), new Card(SPADES, QUEEN),
                new Card(HEARTS, THREE), new Card(DIAMONDS, JACK), new Card(DIAMONDS, TWO) },
            { null, new Card(DIAMONDS, FIVE), new Card(CLUBS, EIGHT), new Card(CLUBS, QUEEN),
                new Card(SPADES, ACE), new Card(HEARTS, TEN) },
            { null, null, null, null, null, null },
            { null, null, null, null, null, null },
            { null, null, null, null, null, null },
            { null, null, null, null, null, null },
            { null, null, null, null, null, null },
            { null, null, null, null, null, null },
            { null, null, null, null, null, null },
            { null, null, null, null, null, null }
        };

        ArrayList<Card> l = new ArrayList<>();
        l.add(new Card(HEARTS, FIVE));
        l.add(new Card(HEARTS, QUEEN));
        l.add(new Card(CLUBS, SEVEN));
        l.add(new Card(CLUBS, ACE));
        l.add(new Card(CLUBS, JACK));
        l.add(new Card(CLUBS, FIVE));
        l.add(new Card(HEARTS, KING));
        l.add(new Card(DIAMONDS, QUEEN));
        l.add(new Card(HEARTS, ACE));
        l.add(new Card(HEARTS, SEVEN));
        l.add(new Card(SPADES, JACK));
        l.add(new Card(SPADES, FOUR));
        l.add(new Card(HEARTS, SIX));
        l.add(new Card(SPADES, TWO));
        l.add(new Card(HEARTS, EIGHT));
        l.add(new Card(CLUBS, FOUR));

        Blackjack t = new Blackjack(5, arr, l);
        return t;
    }

    @Test
    public void testBustOrBlackjack() {
        Blackjack t = setupWithFivePlayers();
        hitTimesTillBust(t, 1);
        hitTimesTillBust(t, 2);
        hitTimesTillBust(t, 3);
        hitTimesTillBust(t, 4);
        hitTimesTillBust(t, 5);
        assertTrue(t.calculateTotal(1) >= 21);
        assertTrue(t.calculateTotal(2) >= 21);
        assertTrue(t.calculateTotal(3) >= 21);
        assertTrue(t.calculateTotal(4) >= 21);
        assertTrue(t.calculateTotal(5) >= 21);
    }

    @Test
    public void testHit() {
        Blackjack t = setupWithFivePlayers();
        t.hit(1);
        t.hit(2);
        t.hit(3);
        t.hit(4);
        t.hit(5);
        assertTrue(t.calculateTotal(1) >= t.getCards(1).get(0).getRank().getBjVal());
        assertTrue(t.calculateTotal(1) >= t.getCards(2).get(0).getRank().getBjVal());
        assertTrue(t.calculateTotal(1) >= t.getCards(3).get(0).getRank().getBjVal());
        assertTrue(t.calculateTotal(1) >= t.getCards(4).get(0).getRank().getBjVal());
        assertTrue(t.calculateTotal(1) >= t.getCards(5).get(0).getRank().getBjVal());
    }

    @Test
    public void testDealerBlackjack() {
        for (int i = 0; i < 100; i++) {
            Blackjack t = setupWithFivePlayers();
            if (t.calculateDealerTotal() == 21) {
                assertTrue(t.isDealerBlackjack());
            }
        }
    }

    @Test
    public void testIsDealerBust() {
        for (int i = 0; i < 100; i++) {
            Blackjack t = setupWithFivePlayers();
            if (t.calculateDealerTotal() > 21) {
                assertTrue(t.isBust(0));
            }
        }
    }

    @Test
    public void testIsPlayerBust() {
        Blackjack t = setupWithFivePlayers();
        hitTimesTillBust(t, 1);
        hitTimesTillBust(t, 2);
        hitTimesTillBust(t, 3);
        hitTimesTillBust(t, 4);
        hitTimesTillBust(t, 5);
        for (int i = 1; i < 6; i++) {
            if (t.calculateTotal(i) != 21) {
                assertTrue(t.isBust(i));
            }
        }
    }

    @Test
    public void getNumPlayers() {
        Blackjack t = setupWithFivePlayers();
        assertEquals(5, t.getNumPlayers());
    }

    @Test
    public void testReset() {
        Blackjack t = setupWithFivePlayers();
        hitTimesTillBust(t, 1);
        hitTimesTillBust(t, 2);
        hitTimesTillBust(t, 3);
        hitTimesTillBust(t, 4);
        hitTimesTillBust(t, 5);
        for (int i = 1; i < 6; i++) {
            if (t.calculateTotal(i) != 21) {
                assertTrue(t.isBust(i));
            }
        }
        t.reset();
        for (int i = 1; i < 6; i++) {
            if (t.calculateTotal(i) != 21) {
                assertFalse(t.isBust(i));
            }
        }
    }

    @Test
    public void testHitBooleanReturnValueTrue() {
        for (int i = 0; i < 100; i++) {
            Blackjack t = setupWithFivePlayers();
            hitTimesTillBust(t, 1);
            assertTrue(t.hit(1));
        }
    }

    @Test
    public void testNextTurn() {
        Blackjack t = setupWithFivePlayers();
        int curr = t.getCurrentPlayer();
        t.nextTurn();
        assertEquals(curr + 1, t.getCurrentPlayer());
    }

    @Test
    public void testIsBettingTrueDuringBettingFalseAfter() {
        Blackjack t = new Blackjack(5);
        assertTrue(t.isBetting());
        t.bet(0, 100);
        assertTrue(t.isBetting());
        t.bet(1, 200);
        assertTrue(t.isBetting());
        t.bet(2, 50);
        assertTrue(t.isBetting());
        t.bet(3, 100);
        assertTrue(t.isBetting());
        t.bet(4, 100);
        assertFalse(t.isBetting());
    }

    @Test
    public void testIsGameOverDuringBetting() {
        for (int i = 0; i < 100; i++) {
            Blackjack t = new Blackjack(5);
            assertFalse(t.isGameOver());
            t.bet(0, 100);
            assertFalse(t.isGameOver());
            t.bet(1, 200);
            assertFalse(t.isGameOver());
            t.bet(2, 50);
            assertFalse(t.isGameOver());
            t.bet(3, 100);
            assertFalse(t.isGameOver());
            t.bet(4, 100);
            assertFalse(t.isGameOver());
        }
    }

    @Test
    public void testInitialMoney() {
        Blackjack t = new Blackjack(5);
        for (int player = 1; player <= 5; player++) {
            assertEquals(1000, t.getMoney(player));
        }
    }

    @Test
    public void testMoneyAfterBets() {
        Blackjack t = setupWithFivePlayers();
        assertEquals(900, t.getMoney(1));
        assertEquals(800, t.getMoney(2));
        assertEquals(950, t.getMoney(3));
        assertEquals(900, t.getMoney(4));
        assertEquals(900, t.getMoney(5));
    }

    @Test
    public void testMoneyAfterAllInBets() {
        Blackjack t = new Blackjack(5);
        t.bet(0, t.getMoney(1));
        assertEquals(0, t.getMoney(1));
        t.bet(1, t.getMoney(2));
        assertEquals(0, t.getMoney(1));
        t.bet(2, t.getMoney(3));
        assertEquals(0, t.getMoney(1));
        t.bet(3, t.getMoney(4));
        assertEquals(0, t.getMoney(1));
        t.bet(4, t.getMoney(5));
        assertEquals(0, t.getMoney(1));
    }

    @Test
    public void testCustomBoardHit() {
        Blackjack t = getCustomInstance();
        t.bet(0, t.getMoney(1));
        t.bet(1, t.getMoney(2));
        t.bet(2, t.getMoney(3));
        t.bet(3, t.getMoney(4));
        t.bet(4, t.getMoney(5));

        assertEquals(5, t.calculateTotal(0));
        assertEquals(7, t.calculateTotal(1));
        assertEquals(18, t.calculateTotal(2));
        assertEquals(13, t.calculateTotal(3));
        assertEquals(12, t.calculateTotal(5));

        assertEquals(21, t.calculateTotal(4));
        hitTimesTillBust(t, 4);
        assertEquals(21, t.calculateTotal(4));
    }

    @Test
    public void testCustomCalculateDealerTotal() {
        Blackjack t = getCustomInstance();
        t.bet(0, t.getMoney(1));
        t.bet(1, t.getMoney(2));
        t.bet(2, t.getMoney(3));
        t.bet(3, t.getMoney(4));
        t.bet(4, t.getMoney(5));

        assertEquals(20, t.calculateDealerTotal());
    }

    @Test
    public void testEdgeCaseCustomHitAfterBust() {
        Blackjack t = getCustomInstance();
        t.bet(0, t.getMoney(1));
        t.bet(1, t.getMoney(2));
        t.bet(2, t.getMoney(3));
        t.bet(3, t.getMoney(4));
        t.bet(4, t.getMoney(5));

        t.hit(1);
        assertEquals(12, t.calculateTotal(1));

        t.hit(1);
        assertEquals(22, t.calculateTotal(1));

        t.hit(1);
        assertEquals(22, t.calculateTotal(1));
    }

    @Test
    public void testCustomStand() {
        Blackjack t = getCustomInstance();
        t.bet(0, t.getMoney(1));
        t.bet(1, t.getMoney(2));
        t.bet(2, t.getMoney(3));
        t.bet(3, t.getMoney(4));
        t.bet(4, t.getMoney(5));

        t.hit(1);

        assertEquals(12, t.calculateTotal(1));
        assertEquals(1, t.getCurrentPlayer());

        t.stand();
        assertEquals(2, t.getCurrentPlayer());
    }

    @Test
    public void testCustomIsBust() {
        Blackjack t = getCustomInstance();
        t.bet(0, t.getMoney(1));
        t.bet(1, t.getMoney(2));
        t.bet(2, t.getMoney(3));
        t.bet(3, t.getMoney(4));
        t.bet(4, t.getMoney(5));

        assertFalse(t.isBust(1));
        t.hit(1);

        assertFalse(t.isBust(1));
        assertEquals(12, t.calculateTotal(1));

        t.hit(1);
        assertTrue(t.isBust(1));
    }

    @Test
    public void testCustomSettle() {
        Blackjack t = getCustomInstance();
        t.bet(0, 100);
        t.bet(1, 200);
        t.bet(2, 300);
        t.bet(3, 400);
        t.bet(4, 500);

        t.hit(1);
        t.stand(); // 12
        t.hit(2); // 28 so no need to stand
        t.hit(3);
        t.stand(); // 20
        t.hit(4); // blackjack, so no need to stand
        t.hit(5);
        t.stand(); // 13
        t.calculateDealerTotal(); // 20

        t.settle();

        assertEquals(900, t.getMoney(1));
        assertEquals(800, t.getMoney(2));
        assertEquals(1000, t.getMoney(3));
        assertEquals(1400, t.getMoney(4));
        assertEquals(500, t.getMoney(5));
    }

    @Test
    public void testEdgeCaseDealerBlackjack() {
        Card[][] arr = { { new Card(SPADES, FIVE), new Card(CLUBS, TWO), new Card(SPADES, QUEEN),
                new Card(HEARTS, THREE), new Card(DIAMONDS, JACK), new Card(DIAMONDS, TWO) },
            { null, new Card(DIAMONDS, FIVE), new Card(CLUBS, EIGHT), new Card(CLUBS, QUEEN),
              new Card(SPADES, ACE), new Card(HEARTS, TEN) },
            { null, null, null, null, null, null },
            { null, null, null, null, null, null },
            { null, null, null, null, null, null },
            { null, null, null, null, null, null },
            { null, null, null, null, null, null },
            { null, null, null, null, null, null },
            { null, null, null, null, null, null },
            { null, null, null, null, null, null }
        };

        ArrayList<Card> l = new ArrayList<>();
        l.add(new Card(HEARTS, FIVE));
        l.add(new Card(HEARTS, QUEEN));
        l.add(new Card(CLUBS, SEVEN));
        l.add(new Card(CLUBS, ACE));
        l.add(new Card(CLUBS, JACK));
        l.add(new Card(CLUBS, SIX));
        l.add(new Card(HEARTS, KING));
        l.add(new Card(DIAMONDS, QUEEN));
        l.add(new Card(HEARTS, ACE));
        l.add(new Card(HEARTS, SEVEN));
        l.add(new Card(SPADES, JACK));
        l.add(new Card(SPADES, FOUR));
        l.add(new Card(HEARTS, SIX));
        l.add(new Card(SPADES, TWO));
        l.add(new Card(HEARTS, EIGHT));
        l.add(new Card(CLUBS, FOUR));

        Blackjack t = new Blackjack(5, arr, l);
        t.bet(0, 100);
        t.bet(1, 200);
        t.bet(2, 300);
        t.bet(3, 400);
        t.bet(4, 500);

        t.hit(1);
        t.stand(); // 12
        t.hit(2); // 28 so no need to stand
        t.hit(3);
        t.stand(); // 20
        t.hit(4); // blackjack, so no need to stand
        t.hit(5);
        t.stand(); // 13
        t.calculateDealerTotal(); // 21

        t.settle();

        assertTrue(t.isDealerBlackjack());
        assertEquals(900, t.getMoney(1));
        assertEquals(800, t.getMoney(2));
        assertEquals(700, t.getMoney(3));
        assertEquals(1000, t.getMoney(4));
        assertEquals(500, t.getMoney(5));
    }

    @Test
    public void testEdgeCaseDealerBust() {
        Card[][] arr = { { new Card(SPADES, FIVE), new Card(CLUBS, TWO), new Card(SPADES, QUEEN),
                new Card(HEARTS, THREE), new Card(DIAMONDS, JACK), new Card(DIAMONDS, TWO) },
            { null, new Card(DIAMONDS, FIVE), new Card(CLUBS, EIGHT), new Card(CLUBS, QUEEN),
              new Card(SPADES, ACE), new Card(HEARTS, TEN) },
            { null, null, null, null, null, null },
            { null, null, null, null, null, null },
            { null, null, null, null, null, null },
            { null, null, null, null, null, null },
            { null, null, null, null, null, null },
            { null, null, null, null, null, null },
            { null, null, null, null, null, null },
            { null, null, null, null, null, null }
        };

        ArrayList<Card> l = new ArrayList<>();
        l.add(new Card(HEARTS, FIVE));
        l.add(new Card(HEARTS, QUEEN));
        l.add(new Card(CLUBS, SEVEN));
        l.add(new Card(CLUBS, ACE));
        l.add(new Card(CLUBS, JACK));
        l.add(new Card(DIAMONDS, SEVEN));
        l.add(new Card(HEARTS, KING));
        l.add(new Card(DIAMONDS, QUEEN));
        l.add(new Card(HEARTS, ACE));
        l.add(new Card(HEARTS, SEVEN));
        l.add(new Card(SPADES, JACK));
        l.add(new Card(SPADES, FOUR));
        l.add(new Card(HEARTS, SIX));
        l.add(new Card(SPADES, TWO));
        l.add(new Card(HEARTS, EIGHT));
        l.add(new Card(CLUBS, FOUR));

        Blackjack t = new Blackjack(5, arr, l);
        t.bet(0, 100);
        t.bet(1, 200);
        t.bet(2, 300);
        t.bet(3, 400);
        t.bet(4, 500);

        t.hit(1);
        t.stand(); // 12
        t.hit(2); // 28 so no need to stand
        t.hit(3);
        t.stand(); // 20
        t.hit(4); // blackjack, so no need to stand
        t.hit(5);
        t.stand(); // 13
        t.calculateDealerTotal(); // 22

        t.settle();

        assertEquals(1100, t.getMoney(1));
        assertEquals(800, t.getMoney(2));
        assertEquals(1300, t.getMoney(3));
        assertEquals(1400, t.getMoney(4));
        assertEquals(1500, t.getMoney(5));
    }
}
