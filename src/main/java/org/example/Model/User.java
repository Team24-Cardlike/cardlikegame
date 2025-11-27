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
    //ArrayList<Upgrade> upgrades = new ArrayList<>();


    public User(int startHealth){
        this.maxHealth = startHealth;
        this.hand = new ArrayList<>();
        this.health = maxHealth;
        this.gold = 0;
        this.selectedCards = new ArrayList<>();
        this.hoveredCards = new ArrayList<>();
        this.boolSelectedCards = new ArrayList<>();
    }

    public void drawCards(Stack<Card> deck, int amount){
        Card card;
        for (int i = 0; i < amount; i++) {
            card = deck.pop();
            this.hand.add(card);
            boolSelectedCards.add(false);
            hoveredCards.add(false);
        }

        hoveredCards = new ArrayList<>();
        boolSelectedCards = new ArrayList<>();

        for (int i = 0; i < amount; i++) {
            hoveredCards.add(false);
            boolSelectedCards.add(false);
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
     * @param indices indices of cards that you selected
     */

    public void removeCards(ArrayList<Integer> indices) {
        //ArrayList<Card> played = new ArrayList<>();

        // Sortera indices fallande så remove() funkar
        //indices.sort(Collections.reverseOrder());

        for (int index : indices) {
            //played.add(hand.remove(index));
            hand.remove(index);
        }

        //return played;
    }

    int playCards(ArrayList<Card> cardsPlayed){
        for(Card card : cardsPlayed){
            this.selectedCards.add(card);
            this.hand.remove(card);
        }
        int damage = getDamage();
        this.selectedCards.clear();
        //MAYBE ADD DRAWCARDS HERE
        return damage;
        // TODO: FINISH FUNCTION
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

    public void setCardAsSelectedBool(int i, boolean value){
        this.boolSelectedCards.set(i, value);
    }
    public void setCardAsHovered(int i, boolean value){
        this.hoveredCards.set(i, value);
    }
    public ArrayList<Boolean> getBoolSelectedCards(){
        return this.boolSelectedCards;
    }
    public ArrayList<Boolean> getHoveredCards(){
        return this.hoveredCards;
    }


}
