package org.example.Model;

import org.example.Model.Upgrades.Upgrade;
import java.util.*;

public class Round {
    private RoundObsMethods o = new RoundObsMethods(this);
    private Deck deck = new Deck();
    Upgrade upgrades;
    private User user;
    private Opponent opponent;

    private int totalDamageToOpponent = 0;
    private int totalDamageToPlayer = 0;
    private boolean playerTurn = true;
    public boolean roundFinished = false;

    private float userHealth = 1;
    private float opponentHealth = 1;

    private String currentBestCombo;

    public Round(User user, Opponent opponent, RoundObserver ob){
        this.user = user;
        this.opponent = opponent;
        this.deck.createInGameDeck();
        o.addObserver(ob);
    }

    public Round(Opponent opponent , RoundObserver ob){
        this.user = new User(1000);
        this.opponent = opponent;
        this.deck.createInGameDeck();
        user.drawCards(deck.getInGameDeck(), user.cardsPerHand);
        o.addObserver(ob);
    }

    // Check states
    public void roundUpdate() {
        if (deck.getInGameDeck().size() + user.hand.size() <= deck.cards.size()) deck.refill(user.hand);

        if (playerTurn) {
            // Wait for player to make turn
            currentBestCombo = bestCombo(user.getSelectedCards());
            o.notifyBestCombo(currentBestCombo);
        }
        else {
            opponentTurn();

        }
        //round ends
        if ( user.health <= 0 || checkDeadOpponent()) {

            roundFinished = true;
            if(opponentHealth < userHealth) {
            o.notifyGameEnded("Victory", totalDamageToOpponent,totalDamageToPlayer);}
            else {
                o.notifyGameEnded("GameOver", totalDamageToOpponent,totalDamageToPlayer);
            }
        }

    }


    /**
     *  <b>Does the following:</b>
     * <ul>
     *   <li>user.playCards()</li>
     *   <li>this.damage()</li>
     *   <li>user.drawCards()</li>
     * </ul>
     * @param //playedCards cards played from the front end
     */
    public void playCards() {
        if (user.getSelectedCards().size() > 0) {
            int damage = user.playCards();
            this.opponent.takeDamage(damage);
            opponentHealth = opponent.getHealthRatio();
            totalDamageToOpponent = totalDamageToOpponent + damage;
            while (user.hand.size() < user.cardsPerHand) user.hand.add(deck.gameDeck.pop());


            System.out.println("Din motståndare tog " + damage + " skada! " + this.opponent.getHealth(opponent) + " kvar");
            playerTurn = false;

            o.notifyHealthChanged(userHealth, opponentHealth); // Notify observer of health changed
            o.notifyPlayerTurn(playerTurn); // Notify observer of changed player turn
            o.notifySelectedChanged(user.getSelectedCards()); // Notify observer of reset selected
            o.notifyHandChanged(user.getHand()); // Notify observer of new hand
        }
    }



    private void opponentTurn() {
        int oppDamage = opponent.getDamage();
        user.takeDamage(oppDamage);
        userHealth = user.getHealthRatio();
        totalDamageToPlayer += oppDamage;

        System.out.println("Du tog " + opponent.getDamage() + " skada! Du har " + this.user.health + " hp kvar");
        playerTurn = true;

        o.notifyHealthChanged(userHealth,opponentHealth);// Notify observer of health changed
        o.notifyPlayerTurn(playerTurn); // notify player turn changed
    }


    private boolean checkDeadOpponent(){
        return opponent.health <= 0;
    }

    private String bestCombo(ArrayList<Card> cards){
        user.setSelectedCards(cards);
        if (user.getComboPlayedCards() == null ) {return "";}
        return user.getComboPlayedCards().name;
    }

    public void discard(){
        removeSelectedCards();
        user.drawCards(deck.getInGameDeck(),10 - user.getHand().size());
        o.notifySelectedChanged(user.getSelectedCards());
        o.notifyHandChanged(user.getHand());
    }

    private void removeSelectedCards() {
        user.setSelectedCards(new ArrayList<>());
        o.notifySelectedChanged(user.getSelectedCards());
    }


    // Removing selected card from hand and adding it to selected cards
    public void addSelectedCards(int index) {
        ArrayList<Card> tempHand = new ArrayList<>(user.getHand());
        Card c = tempHand.get(index);
        user.removeCardFromHand(index); // Remove form hand
        user.addSelectedCard(c); // Added to selected cards
        //Notify hand changed
        o.notifyHandChanged(user.getHand());
        o.notifySelectedChanged(user.getSelectedCards());
    }

    //Removing card from selected and returning it back to the hand.
    public void unselectCard(int index) {
        ArrayList<Card> temp = user.getSelectedCards();
        user.hand.add(temp.get(index));
        temp.remove(index);
        user.setSelectedCards(temp);

        o.notifySelectedChanged(user.getSelectedCards());
        o.notifyHandChanged(user.getHand());
    }

    // Round ended
    public void endRound() {
        this.roundFinished = true;
    }


    public void init() {
        o.notifyHandChanged(user.getHand());
        System.out.println(user.getHand().size());
        o.notifySelectedChanged(user.getSelectedCards());
        o.notifyBestCombo(currentBestCombo);
        o.notifyHealthChanged(userHealth, opponentHealth);
        o.notifyPlayerTurn(playerTurn);
    }

}

