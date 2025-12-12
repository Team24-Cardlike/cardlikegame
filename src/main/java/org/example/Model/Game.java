package org.example.Model;


/*
import java.util.*;

public class Game {

    private GameObservers observers;

    private Deck deck; // Object with all possible cards, and the deck you draw from
    //Upgrades upgrades;
    private User user;
    private Opponent opponent;
    private Stack<Card> gameDeck; // The deck you draw from
    // private int turn = 0; // Not needed?
    private int totalDamageToOpponent;
    private int totalDamageToPlayer;
    //Stage stage;
    private boolean playerTurn;
    private turnManager tm;
    private boolean gameState;

    private ArrayList selectedCards;

    public Game(Opponent opponent){
        this.deck = new Deck();
        //this.upgrades = new Upgrades();
        this.user     = new User(1000);
        this.opponent = opponent;
        this.deck.createInGameDeck();
        this.gameDeck = this.deck.getInGameDeck();
        user.drawCards(deck.getInGameDeck(), user.cardsPerHand);
        this.selectedCards = new ArrayList<>(Collections.nCopies(user.getHand().size(),false));
        observers = new GameObservers(this);
        this.tm = new turnManager(true);
        gameState = true;
    }

    Opponent getOpponent() {
        return opponent;
    }

    Stack<Card> getGameDeck() {
        return gameDeck;
    }


    public int getTotDamageToOpponent() {
        return totalDamageToOpponent;
    }

    public int getTotDamageToPlayer() {
        return totalDamageToPlayer;
    }

    public boolean getGameState() {
        return gameState;
    }

    public GameObservers getGameObservers() {
        return observers;
    }

    Deck getDeck() {
        return deck;
    }

    public turnManager getTurnManager(){
        return tm;
    }


    public void gameLoop1() {

        // System.out.println(this.gameDeck.size() + this.user.hand.size());
        // System.out.println(selectedCards.size());
        if (gameDeck.size() + user.hand.size() <= deck.cards.size()) deck.refill(user.hand);
        while (user.hand.size() < user.cardsPerHand) user.hand.add(gameDeck.pop());
        observers.notifyHandChanged(user.getHand());
        observers.notifyHealthChanged(user.getHealthRatio(), opponent.getHealthRatio());

        if (playerTurn) {

            // Wait for player to make turn
        }
        else {
            opponentTurn();
        }

        //round ends
        if ( user.getHealth() <= 0 || opponent.getHealth() <= 0) {

        }
    }


    public void gameLoop() {
        while(this.opponent.getHealth()>0 && this.user.getHealth()>0){


            System.out.println("-------------");
            for (Card c : user.hand) {
                System.out.print(c.rank + " ");
            }
            System.out.println();
            System.out.println("-------------");

            if (this.gameDeck.size() <= this.deck.cards.size() - user.cardsPerHand) {
                deck.refill(user.hand);
            }


            // turn++;
            // if (this.opponent.turns != turn) {
            //     // TODO: add logic to choose cards from hand to play
            //     // Connect with frontend
            //     //this.playCards(new ArrayList<Card>((this.user.hand.getFirst(), this.user.hand.getLast())));
            //     //playCards(user.getSelectedCards());
            // }
            // else {
            //     damage(user, opponent);
            //     // turn = 0;
            // }
            System.out.println("Your health: " + user.getHealth() + ", Opponent's health: " + opponent.getHealth());
            System.out.println(gameDeck.size());
        }
    }


    User getUser(){return user;}

    String getGameEndContext(){
        if(this.opponent.getHealth()<=0){
            return("You won! :D");
        }
        else if(this.user.getHealth()<=0){
            return("You lost! :(");
        }
        else{
            return("IDK man...");
        }
    }


    private void damage(Player defender, Player attacker){
        defender.takeDamage(attacker.getDamage());
    }

    /**
     *  <b>Does the following:</b>
     * <ul>
     *   <li>user.playCards()</li>
     *   <li>this.damage()</li>
     *   <li>user.drawCards()</li>
     * </ul>
     * @param //playedCards cards played from the front end
     *//*
    public void playCards(ArrayList<Card> playedCards){
        int damage = user.playCards(playedCards);
        this.opponent.takeDamage(damage*100);
        totalDamageToOpponent = totalDamageToOpponent + damage;

        if(checkDeadOpponent()){
            gameState = false;
            return;
        }

        System.out.println("Din motståndare tog "+damage+" skada! "+ this.opponent.getHealth()+ " kvar");
       playerTurn = false;
       observers.notifyHealthChanged(user.getHealthRatio(),opponent.getHealthRatio());
       observers.notifyPlayerTurn(false);
    }



    private void opponentTurn() {
        int oppDamage = opponent.getDamage();
        user.takeDamage(oppDamage);
        totalDamageToPlayer += oppDamage;


        System.out.println("Du tog " + opponent.getDamage() + " skada! Du har " + user.getHealth() + " hp kvar");
        playerTurn = true;
        observers.notifyHealthChanged(user.getHealthRatio(),opponent.getHealthRatio());
        observers.notifyPlayerTurn(true);
    }

    public ArrayList<Card> getSelectedCards() {
        ArrayList<Card> temp = new ArrayList<>();

        for (int i = selectedCards.size() - 1; i >= 0 ; i --) {
            if ((boolean) selectedCards.get(i)) {
            temp.add(user.hand.get(i));
            user.hand.remove(i);

            observers.notifyCardSelect(i, false);
            }
        }
        // selectedCards = new ArrayList<>(Collections.nCopies(user.hand.size(), false));
        selectedCards = new ArrayList<>(Collections.nCopies(user.cardsPerHand, false));
        observers.notifyHandChanged(user.getHand());
        return temp;


    }

    private boolean checkDeadOpponent(){
        return opponent.getHealth() <= 0;
    }

    public String bestCombo(ArrayList<Card> cards){
        user.setSelectedCards(cards);
        return user.getComboPlayedCards().name;
    }

    public void discard(ArrayList<Integer> indices){
        user.removeCards(indices);
    }

    // public void playCards(ArrayList<Integer> selectedCards) {
    //     int totalDamage = 0;

    public void setSelectedCards(int index, boolean b) {
        boolean newValue = !((boolean) selectedCards.get(index));
        selectedCards.set(index, newValue);
        observers.notifyCardSelect(index, newValue);

    }

    public ArrayList<Card> getSelectedCardsAsCards(ArrayList<Integer> cards){
            ArrayList<Card> hand = user.getHand();
            ArrayList<Card> temp = new ArrayList<>();
            for(int i : cards) {
                temp.add(hand.get(i));
            }
            return temp;
    }


    public int getNumberOfSelected(ArrayList<Boolean> cardsBool){
        int i = 0;
        for(Boolean bool : cardsBool){
            if(bool)i++;
        }
        return i;
    }

    public void nextRound(){
        this.user.resetUser();
        this.deck = new Deck();
        this.deck.createInGameDeck();
        opponent = new Opponent(2000, 25, 3, "enemyEvil");
        totalDamageToOpponent = 0;
        totalDamageToPlayer = 0;
        gameState = true;

        //factory method på nu opp

        this.gameDeck = this.deck.getInGameDeck();
        user.drawCards(deck.getInGameDeck(), user.cardsPerHand);
        this.selectedCards = new ArrayList<>(Collections.nCopies(user.getHand().size(),false));
        this.playerTurn = true;
    }
}
*/