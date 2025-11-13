import org.junit.*;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.*;

public class Testing {

    @Test
    public void test_deck_cards(){
        Deck deck = new Deck();
        for(Card card : deck.cards){
            assertTrue(deck.suits.contains(card.suit));
            assertTrue(deck.ranks.contains(card.rank));
            assertEquals(card.name, card.rank + " of " + card.suit);
            assertEquals(card.pic, card.rank + card.suit +".png");
        }
        Card card = new Card("sun", 13);
        assertEquals("13 of sun", card.name);
        assertEquals("13sun.png", card.pic);
    }

    @Test
    public void test_shuffledCards() {
        Deck deck = new Deck();
        assertEquals(52, deck.cards.size());
        ArrayList<Card> shuffled = deck.shuffle();
        assertEquals(deck.cards.size(), shuffled.size());
        for(Card card: shuffled){
            assertTrue(deck.cards.contains(card));
        }
    }

    @Test
    public void test_CardCombo_checkCombo(){
        User user = new User(100);
        Opponent opp = new Opponent(2000, 15, 3);
        ArrayList<Card> cards = new ArrayList<>(Arrays.asList(new Card("sun", 10),
                new Card("sun", 11))
        );
        user.hand.addAll(cards);
        user.selectedCards.addAll(user.hand);
        opp.health -= user.getDamage();
        assertEquals(1989, opp.health);
        cards.addAll(Arrays.asList(new Card("sun", 12),
                new Card("sun", 13),
                new Card("sun", 14)));
        user.setHand(cards);
        user.selectedCards = user.hand;
        opp.health -= user.getDamage();
        assertEquals(475, opp.health);
    }
}
