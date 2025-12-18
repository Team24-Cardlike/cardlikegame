package org.example.Model;

import org.example.Model.OpponentFactories.BossOpponent;
import org.example.Model.OpponentFactories.OpponentInterface;
import org.example.Model.OpponentFactories.BossOpponent;
import org.example.Model.OpponentFactories.Opponent;
import org.example.Model.OpponentFactories.OpponentInterface;
import org.example.Model.Upgrades.Upgrade;
import org.example.Model.Upgrades.UpgradeLibrary;
import org.example.Model.Upgrades.UpgradeManager;

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

    private UpgradeManager upgradeManager = new UpgradeManager();
    private UpgradeLibrary lib = new UpgradeLibrary();

    public int turnNumber = 0;
    public boolean beenAttacked = false;

    public Round(User user, Opponent opponent, RoundObserver ob){
        this.user = user;
        this.user.resetUser();
        this.opponent = opponent;
        //this.opponent.resetOpponent();
        this.deck.createInGameDeck();
        user.drawCards(deck.getInGameDeck(), user.cardsPerHand);
        o.addObserver(ob);

    }

    public Round(Opponent opponent , RoundObserver ob){
        this.user = new User(1000);
        this.opponent = opponent;
        this.deck.createInGameDeck();
        user.drawCards(deck.getInGameDeck(), user.cardsPerHand);
        o.addObserver(ob);
    }

    public User getUser(){
        return user;
    }
    // Check states
    public void roundUpdate() {

        if (deck.getInGameDeck().size() + user.hand.size() <= deck.cards.size()) deck.refill(user.hand);


        if (playerTurn && !this.roundFinished) {
            // Wait for player to make turn
            currentBestCombo = bestCombo(user.getSelectedCards());
            o.notifyBestCombo(currentBestCombo);
        }

        else if(!playerTurn && !this.roundFinished){
            opponentTurn();
        }
        //round ends


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
    public void playCards(){
        if (user.getSelectedCards().isEmpty()) {return;}
        this.turnNumber += 1;
        this.user.damage = 0;
        this.user.damage = this.user.playCards();
        for(Upgrade upg : this.user.upgrades){
            checkUpgrade(upg);
        }
        this.opponent.takeDamage(this.user.damage);
        opponentHealth = opponent.getHealthRatio();
        totalDamageToOpponent = totalDamageToOpponent + this.user.damage;
        while (user.hand.size() < user.cardsPerHand) user.hand.add(deck.gameDeck.pop());


        System.out.println("Din motståndare tog "+this.user.damage+" skada! "+ this.opponent.getHealth()+ " kvar");
        checkUpgrade(lib.getUpgrade(15));
        checkUpgrade(lib.getUpgrade(31));
        checkUpgrade(lib.getUpgrade(32));
        this.user.selectedCards = new ArrayList<>();
        this.user.damage = 0;
        playerTurn = false;

        o.notifyHealthChanged(userHealth, opponentHealth); // Notify observer of health changed
        o.notifyPlayerTurn(playerTurn); // Notify observer of changed player turn
        o.notifySelectedChanged(user.getSelectedCards()); // Notify observer of reset selected
        o.notifyHandChanged(user.getHand());// Notify observer of new hand
        checkDeadPlayer();
    }



    private void opponentTurn() {
        this.beenAttacked = true;
        int oppDamage = opponent.getDamage();
        user.takeDamage(oppDamage);
        userHealth = user.getHealthRatio();
        totalDamageToPlayer += oppDamage;

        System.out.println("Du tog " + opponent.getDamage() + " skada! Du har " + this.user.health + " HP kvar");
        checkUpgrade(lib.getUpgrade(22));
        checkDeadPlayer();
        playerTurn = true;

        o.notifyHealthChanged(userHealth,opponentHealth);// Notify observer of health changed
        o.notifyPlayerTurn(playerTurn); // notify player turn changed

    }

    private void checkDeadPlayer(){
        if (checkDeadUser() || checkDeadOpponent()) {
            if(checkDeadOpponent()) {
                o.notifyGameEnded("Victory", totalDamageToOpponent,totalDamageToPlayer);}
            else {
                System.out.println("hejhej");
                o.notifyGameEnded("GameOver", totalDamageToOpponent,totalDamageToPlayer);
            }
        }
    }

    private boolean checkDeadOpponent(){
        return opponent.getHealth() <= 0;
    }

    private boolean checkDeadUser(){
        return this.user.health <= 0;
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
        user.addGold(totalDamageToOpponent);
        this.roundFinished = true;
    }

    public Opponent getOpponent() {
        return this.opponent;
    }

    public void checkUpgrade(Upgrade upgrade){
        if(this.getUser().getUpgrades().contains(upgrade))
            this.upgradeManager.checkUpgrade(upgrade, this);
    }


    public void init() {
        o.notifyHandChanged(user.getHand());

        o.notifySelectedChanged(user.getSelectedCards());
        o.notifyBestCombo(currentBestCombo);
        o.notifyHealthChanged(userHealth, opponentHealth);
        o.notifyPlayerTurn(playerTurn);
        o.notifyNewRound();
    }



}

