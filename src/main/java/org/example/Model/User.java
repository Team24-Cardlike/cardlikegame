package org.example.Model;

import com.badlogic.gdx.utils.Array;

import java.util.ArrayList;
// import java.util.Collections;
import java.util.Stack;

public class User extends Player {
    
    ArrayList<Card> hand;
    int gold;
    int cardsPerHand = 10;
    ArrayList<Card> selectedCards;
    CardCombos combos = new CardCombos();
    ArrayList<Upgrade> upgrades = new ArrayList<>();

    private Array<Boolean> selected;
    private Array<Boolean> hovered;

    public User(int startHealth){
        this.maxHealth = startHealth;
        this.hand = new ArrayList<>();
        this.health = maxHealth;
        this.gold = 0;
        this.selectedCards = new ArrayList<>();
    }

    void drawCards(Stack<Card> deck, int amount){
        for (int i = 0; i < amount; i++) {                   
            this.hand.add(deck.pop());
        }        
    }

    public ArrayList<Card> getSelectedCards(){
        return this.selectedCards;
    }

    /**
     *
     * @return the value of the highest card + the value of the best combo, in the cards played.
     */
    @Override
    public int getDamage() {
        int damage = getHiVal(this.selectedCards) + combos.checkCombo(this.selectedCards).value;
        return damage;
    }

    int getHiVal(ArrayList<Card> cards){
        ArrayList<Integer> ranks = new ArrayList<>();
        for (Card c : cards) {
            ranks.add(c.rank);
        }        
        return ranks.getLast();
    }
    /**
     * Removes selected cards from hand
     *
     * @param cardsPlayed Cards that you selected
     */
    void playCards(ArrayList<Card> cardsPlayed){
        for(Card card : cardsPlayed){
            this.selectedCards.add(card);
            this.hand.remove(card);
        }
    }

    public void setHand(ArrayList<Card> hand) {
        this.hand = hand;
    }

    public ArrayList<Card> getHand() {
        return this.hand;
    }

    public void setSelectedCards(ArrayList<Card> selectedCards){
        this.selectedCards = selectedCards;
    }

    public ArrayList<Boolean>
}
