import org.junit.*;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class Testing {
    @Test
    public void test_cardPic(){
        assertEquals("moon13.png", new Card("moon", 13).pic);
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
}
