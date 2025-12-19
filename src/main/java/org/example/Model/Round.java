package org.example.Model;

import org.example.Model.OpponentFactories.Opponent;
import org.example.Model.Upgrades.Upgrade;
import org.example.Model.Upgrades.UpgradeManager;

import java.util.*;

public class Round {
    private RoundObsMethods o = new RoundObsMethods(this);
    private Deck deck = new Deck();
    private User user;
    private Opponent opponent;

    private int totalDamageToOpponent = 0;
    private int totalDamageToPlayer = 0;
    private boolean playerTurn = true;
    public boolean roundFinished = false;
    private boolean won = false;

    private float userHealth = 1;
    private float opponentHealth = 1;

    private String currentBestCombo;

    private UpgradeManager upgradeManager = new UpgradeManager();
    private ArrayList<String> upgradeNames = new ArrayList<>();
    public int turnNumber = 0;
    public boolean beenAttacked = false;

    public Round(User user, Opponent opponent, RoundObserver ob){
        this.user = user;
        this.user.resetUser();
        this.opponent = opponent;
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

        if (playerTurn && !this.roundFinished) {
            // Wait for player to make turn
            currentBestCombo = bestCombo(user.getSelectedCards());
            o.notifyBestCombo(currentBestCombo);
        }

        else if(!playerTurn && !this.roundFinished){
            opponentTurn();
        }
        // round ends
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
        deck.refill(user.getHand());
        if (user.getSelectedCards().isEmpty()) {return;}
        this.turnNumber += 1;
        this.user.damage = 0;
        this.user.damage = this.user.playCards();
        checkUpgrades(1);
        this.opponent.takeDamage(this.user.damage);
        this.opponentHealth = opponent.getHealthRatio();
        this.totalDamageToOpponent += this.user.damage;
        user.drawCards(deck.getInGameDeck(), user.cardsPerHand - user.getHand().size());


        System.out.println("Din motståndare tog "+this.user.damage+" skada! "+ this.opponent.getHealth()+ " kvar");
        //After damaging
        checkUpgrades(3);
        this.user.selectedCards = new ArrayList<>();
        this.user.damage = 0;
        playerTurn = false;

        o.notifyHealthChanged(userHealth, opponentHealth); // Notify observer of health changed
        o.notifyPlayerTurn(playerTurn, user.getGold()); // Notify observer of changed player turn
        o.notifyUnselected(user.getSelectedCards()); // Notify observer of reset selected
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
        //After taking damage
        checkUpgrades(2);
        checkDeadPlayer();
        playerTurn = true;

        o.notifyHealthChanged(userHealth,opponentHealth);// Notify observer of health changed
        o.notifyOpponentAttacked(oppDamage);//, oppopnent.name, opponent.attackName));
        o.notifyPlayerTurn(playerTurn, user.getGold()); // notify player turn changed

    }

    private void checkDeadPlayer(){
        if (checkDeadUser() || checkDeadOpponent()) {
            this.roundFinished = true;
            if(checkDeadOpponent()) {
                this.won = true;
                o.notifyGameEnded("Victory", totalDamageToOpponent,totalDamageToPlayer);}
            else {
                this.won = false;
                o.notifyGameEnded("GameOver", totalDamageToOpponent,totalDamageToPlayer);
            }
        }
    }

    private boolean checkDeadOpponent(){
        return this.opponent.getHealth() <= 0;
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
        deck.refill(user.getHand());
        removeSelectedCards();
        user.drawCards(deck.getInGameDeck(), user.cardsPerHand - user.getHand().size());
        o.notifyUnselected(user.getSelectedCards());
        o.notifyHandChanged(user.getHand());
    }

    private void removeSelectedCards() {
        user.setSelectedCards(new ArrayList<>());
        o.notifyUnselected(user.getSelectedCards());
    }

    /**
     * Removing selected card from hand and adding it to selected cards.
     */

    public void addSelectedCards(int index) {
        ArrayList<Card> tempHand = new ArrayList<>(user.getHand());
        Card c = tempHand.get(index);
        user.removeCardFromHand(index); // Remove form hand
        user.addSelectedCard(c); // Added to selected cards
        //Notify hand changed
        o.notifyHandChanged(user.getHand());
        o.notifySelected(user.getSelectedCards());
    }

    /**
     * Removing card from selected and returning it back to the hand.
     */
    public void unselectCard(int index) {
        ArrayList<Card> temp = user.getSelectedCards();
        user.hand.add(temp.get(index));
        temp.remove(index);
        user.setSelectedCards(temp);

        o.notifyUnselected(user.getSelectedCards());
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

    /**
     * IDs coded by four-sequences int (cxyz)
     * <ul>
     * <li>c - The category: <ul>(1 - damage, 2 - sustain, 3 - economy)</ul></li>
     * <li>x - When to check it:
     *     <ul>
     *     <li>0 - when round starts</li> <li>1 - when damaging</li> <li>2 - when taking damage</li> <li>3 - after damaging</li> <li>... (not implemented)</li>  <li>9 - when round ends</li>
     *     </ul>
     * <li>yz - number in the sequence (01 -> 99)</li>
     * </ul>
     * @param id2ndNum a number that decides what upgrades we check (x in list above)
     *
     */
    public void checkUpgrades(int id2ndNum){
        for(Upgrade upgrade : this.getUser().getUpgrades()) {
            int triggerNum = (upgrade.getIdNum() / 100) % 10;
            if (triggerNum == id2ndNum)
                this.upgradeManager.checkUpgrade(upgrade, this);
        }
    }

    public boolean getWon() {
        return won;
    }

    public void init() {
        for(Upgrade upg : this.user.getUpgrades()){
            upgradeNames.add(upg.getPic());
        }
        o.notifyHandChanged(user.getHand());
        o.notifyUnselected(user.getSelectedCards());
        o.notifyBestCombo(currentBestCombo);
        o.notifyHealthChanged(userHealth, opponentHealth);
        o.notifyCurrentOpponent(this.opponent.getName(), this.opponent.getName(), this.opponent.getDamage());
        o.notifyPlayerTurn(playerTurn, user.getGold());
        o.notifyNewRound(upgradeNames);
    }
}

