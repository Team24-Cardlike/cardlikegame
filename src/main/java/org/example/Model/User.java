package org.example.Model;

import com.badlogic.gdx.utils.Array;

import java.util.ArrayList;
// import java.util.Collections;
import java.util.Collections;
import java.util.Stack;

public class User extends Player {

    ArrayList<Card> hand;
    int gold;
    public int cardsPerHand = 10;
    ArrayList<Card> selectedCards;
    ArrayList<Boolean> hoveredCards;
    ArrayList<Boolean> boolSelectedCards;

    CardCombos combos = new CardCombos();



    public User(int startHealth){
        this.maxHealth = startHealth;
        this.hand = new ArrayList<>();
        this.health = maxHealth;
        this.gold = 0;
        this.selectedCards = new ArrayList<>();
        this.hoveredCards = new ArrayList<>();
        this.boolSelectedCards = new ArrayList<>();
        this.usersUpgrades = new ArrayList<>();
    }

    public void drawCards(Stack<Card> deck, int amount){
        Card card;
        for (int i = 0; i < amount; i++) {
            card = deck.pop();
            this.hand.add(card);
        }
    }

    public ArrayList<Card> getSelectedCards(){
        return this.selectedCards;
    }

    public void addSelectedCard(Card c) {
        this.selectedCards.add(c);
    }

    /**
     *
     * @return the value of the highest card + the value of the best combo, in the cards played.
     */
    public Combo getComboPlayedCards(){
        return combos.checkCombo(this.selectedCards);
    }

    @Override
    public int getDamage() {
        int damage = getHiVal(this.selectedCards) + getComboPlayedCards().value;
        //Beräkna beroende på aktiva upgrades
        return damage;
    }

    int getHiVal(ArrayList<Card> cards){
        ArrayList<Integer> ranks = new ArrayList<>();
        for (Card c : cards) {
            ranks.add(c.rank);
        }
        Collections.sort(ranks);
        return ranks.getLast();
    }
    /**
     * Removes selected cards from hand
     *
     * @param indices indices of cards that you selected
     */

    public void removeCards(ArrayList<Integer> indices) {
        for (int index : indices) {
            hand.remove(index);
        }
    }

    int playCards(){
        int damage = getDamage();
        selectedCards = new ArrayList<>();
        return damage;
        // TODO: FINISH FUNCTION
    }

    public void setHand(ArrayList<Card> hand) {
        this.hand = hand;
    }

    public ArrayList<Card> getHand() {
        return this.hand;
    }
    public void removeCardFromHand(int index) {
        this.hand.remove(index);
    }

    public void setSelectedCards(ArrayList<Card> selectedCards){
        this.selectedCards = selectedCards;
    }

    public void resetUser(){
        this.health = maxHealth;
        this.hand = new ArrayList<>();
        this.selectedCards = new ArrayList<>();
    }
}