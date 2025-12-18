package org.example.Model;


import org.example.DesktopLauncher;
import org.example.Model.OpponentFactories.BossFactory;
import org.example.Model.OpponentFactories.Opponent;
import org.example.Model.OpponentFactories.OpponentFactory;
import org.example.Model.OpponentFactories.RegularFactory;
import org.example.Model.Upgrades.ComboDmgAmp;
import org.example.Model.Upgrades.Upgrade;
import org.example.Model.Upgrades.UpgradeLibrary;
import org.example.Model.Upgrades.UpgradeManager;
import org.junit.*;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.*;

public class Testing {
    UpgradeLibrary lib = new UpgradeLibrary();
    OpponentFactory bf = new BossFactory();
    OpponentFactory rf = new RegularFactory();
    @Test
    public void test_deck_cards(){
        Deck deck = new Deck();
        for(Card card : deck.getCards()){
            assertTrue(deck.suits.contains(card.suit));
            assertTrue(deck.ranks.contains(card.rank));
            assertEquals(card.name, card.rank + " of " + card.suit);
            assertEquals(card.pic, "cardPics/" + card.suit+"/" + card.rank + card.suit + ".png");
        }
        Card card = new Card("sun", 13);
        assertEquals("13 of sun", card.name);
        assertEquals("cardPics/sun/13sun.png", card.pic);
    }

    @Test
    public void test_shuffledCards() {
        Deck deck = new Deck();
        assertEquals(52, deck.getCards().size());
        ArrayList<Card> shuffled = deck.shuffle();
        assertEquals(deck.getCards().size(), shuffled.size());
        for(Card card: shuffled){
            assertTrue(deck.getCards().contains(card));
        }
    }

    //<editor-fold desc="Test-cases for every combo (AI-generated them using my own Tests on 'single' and 'royal straight flush')"
    @Test
    public void test_cardCombo_single() {
        User user = new User(100);
        Opponent opp = bf.Create("TESTBOSS");
        ArrayList<Card> cards = new ArrayList<>(Arrays.asList(new Card("sun", 10),
                new Card("sun", 11), new Card("sun", 13), new Card("moon", 3), new Card("sun", 2))
        );
        user.setHand(cards);
        user.setSelectedCards(cards);
        user.setDamage();
        opp.takeDamage(user.getDamage());
        assertEquals(opp.getMaxHealth() - (0 + 13), opp.getHealth());
    }

    @Test
    public void test_cardCombo_single2(){
        CardCombos combos = new CardCombos();
        ArrayList<Card> cards = new ArrayList<>(Arrays.asList(new Card("sun", 10),
                new Card("sun", 11), new Card("sun", 13), new Card("moon", 3), new Card("sun", 2))
        );
        Combo combo = combos.checkCombo(cards);
        assertEquals("Single", combo.name);
    }

    @Test
    public void test_CardCombo_pair() {
        User user = new User(100);
        Opponent opp = bf.Create("TESTBOSS");

        ArrayList<Card> cards = new ArrayList<>(Arrays.asList(
                new Card("sun", 10),
                new Card("moon", 10)
        ));
        user.setHand(cards);
        user.setSelectedCards(cards);
        user.setDamage();
        opp.takeDamage(user.getDamage());

        assertEquals(opp.getMaxHealth() - (20 + 10), opp.getHealth());
    }

    @Test
    public void test_CardCombo_twoPair() {
        User user = new User(100);
        Opponent opp = bf.Create("TESTBOSS");

        ArrayList<Card> cards = new ArrayList<>(Arrays.asList(
                new Card("sun", 10),
                new Card("moon", 10),
                new Card("sun", 12),
                new Card("moon", 12)
        ));
        user.setHand(cards);
        user.setSelectedCards(cards);
        user.setDamage();
        opp.takeDamage(user.getDamage());

        assertEquals(opp.getMaxHealth() - (40 + 12) , opp.getHealth());
    }

    @Test
    public void test_CardCombo_threeOfAKind() {
        User user = new User(100);
        Opponent opp = bf.Create("TESTBOSS");

        ArrayList<Card> cards = new ArrayList<>(Arrays.asList(
                new Card("sun", 8),
                new Card("moon", 8),
                new Card("star", 8)
        ));
        user.setHand(cards);
        user.setSelectedCards(cards);
        user.setDamage();
        opp.takeDamage(user.getDamage());

        assertEquals(opp.getMaxHealth() - (80 + 8), opp.getHealth());
    }

    @Test
    public void test_CardCombo_straight() {
        User user = new User(100);
        Opponent opp = bf.Create("TESTBOSS");

        ArrayList<Card> cards = new ArrayList<>(Arrays.asList(
                new Card("sun", 4),
                new Card("moon", 5),
                new Card("sun", 6),
                new Card("moon", 7),
                new Card("sun", 8)
        ));
        user.setHand(cards);
        user.setSelectedCards(cards);
        user.setDamage();
        opp.takeDamage(user.getDamage());

        assertEquals(opp.getMaxHealth() - (100 + 8), opp.getHealth());
    }

    @Test
    public void test_CardCombo_flush() {
        User user = new User(100);
        Opponent opp = bf.Create("TESTBOSS");

        ArrayList<Card> cards = new ArrayList<>(Arrays.asList(
                new Card("sun", 2),
                new Card("sun", 5),
                new Card("sun", 9),
                new Card("sun", 11),
                new Card("sun", 13)
        ));
        user.setHand(cards);
        user.setSelectedCards(cards);
        user.setDamage();
        opp.takeDamage(user.getDamage());

        assertEquals(opp.getMaxHealth() - (125+13), opp.getHealth());
    }

    @Test
    public void test_CardCombo_fullHouse() {
        User user = new User(100);
        Opponent opp = bf.Create("TESTBOSS");

        ArrayList<Card> cards = new ArrayList<>(Arrays.asList(
                new Card("sun", 7),
                new Card("moon", 7),
                new Card("star", 7),
                new Card("sun", 12),
                new Card("moon", 12)
        ));
        user.setHand(cards);
        user.setSelectedCards(cards);
        user.setDamage();
        opp.takeDamage(user.getDamage());

        assertEquals(opp.getMaxHealth() - (175 + 12), opp.getHealth());
    }

    @Test
    public void test_CardCombo_fourOfAKind() {
        User user = new User(100);
        Opponent opp = bf.Create("TESTBOSS");

        ArrayList<Card> cards = new ArrayList<>(Arrays.asList(
                new Card("sun", 5),
                new Card("moon", 5),
                new Card("star", 5),
                new Card("cloud", 5)
        ));
        user.setHand(cards);
        user.setSelectedCards(cards);
        user.setDamage();
        opp.takeDamage(user.getDamage());

        assertEquals(opp.getMaxHealth() - (400 + 5), opp.getHealth());
    }

    @Test
    public void test_CardCombo_straightFlush() {
        User user = new User(100);
        Opponent opp = bf.Create("TESTBOSS");

        ArrayList<Card> cards = new ArrayList<>(Arrays.asList(
                new Card("sun", 3),
                new Card("sun", 4),
                new Card("sun", 5),
                new Card("sun", 6),
                new Card("sun", 7)
        ));
        user.setHand(cards);
        user.setSelectedCards(cards);
        user.setDamage();
        opp.takeDamage(user.getDamage());

        assertEquals(opp.getMaxHealth() - (600 + 7), opp.getHealth());
    }

    @Test
    public void test_cardCombo_royalStraightFlush(){
        User user = new User(100);
        Opponent opp = bf.Create("TESTBOSS");
        ArrayList<Card> cards = new ArrayList<>(Arrays.asList(
                new Card("sun", 10),
                new Card("sun", 11),
                new Card("sun", 12),
                new Card("sun", 13),
                new Card("sun", 14)));
        user.setHand(cards);
        user.setSelectedCards(cards);
        user.setDamage();
        opp.takeDamage(user.getDamage());
        assertEquals(opp.getMaxHealth() - (1500 + 14), opp.getHealth());
    }
    //</editor-fold>
}
