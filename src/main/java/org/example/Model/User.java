package org.example.Model;
import org.example.Model.Upgrades.Upgrade;
import org.example.Model.Upgrades.UpgradeLibrary;
import org.example.Model.Upgrades.UpgradeManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Stack;

public class User extends Player {
    public int damage;
    ArrayList<Card> hand;
    int gold;
    public int cardsPerHand = 10;
    ArrayList<Card> selectedCards;

    CardCombos combos = new CardCombos();

    public ArrayList<Upgrade> upgrades = new ArrayList<>();

    public User(int startHealth){
        this.damage = 0;
        this.maxHealth = startHealth;
        this.hand = new ArrayList<>();
        this.health = maxHealth;
        this.gold = 0;
        this.selectedCards = new ArrayList<>();
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
        return this.damage;
    }

    public void setDamage(){
        this.damage = getHiVal(this.selectedCards) + getComboPlayedCards().value;
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
        setDamage();
        return this.damage;
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

    public int getGold(){
        return this.gold;
    }

    public void addGold(int amount){ this.gold += amount;}

    public void setGold(int amount){
        this.gold = amount;
    }

    public ArrayList<Upgrade> getUpgrades(){
        return this.upgrades;
    }

    public void setUpgrades(ArrayList<Upgrade> upgrades){
        this.upgrades.addAll(upgrades);
    }

    public void addUpgrade(Upgrade upgrade){
        if(this.upgrades.contains(upgrade))
            System.out.println("Already have that upgrade!");
        else
            this.upgrades.add(upgrade);
    }

    public void buyUpgrade(Upgrade upgrade){
        if(this.gold >= upgrade.getCost()) {
            this.gold -= upgrade.getCost();
            this.upgrades.add(upgrade);
        }
        else{
            System.out.println("Too poor!");
        }
    }
}