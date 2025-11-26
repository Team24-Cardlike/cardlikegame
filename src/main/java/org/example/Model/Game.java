package org.example.Model;



import java.util.ArrayList;
import java.util.Stack;
import java.util.*;

public class Game {

    public GameObservers observers;

    Deck deck;
    //Upgrades upgrades;
    public User user;
    public Opponent opponent;
    Stack<Card> gameDeck;   
    int turn = 0;    
    //Stage stage;
    boolean playerTurn;
    turnManager tm;
    ArrayList selectedCards;

    public Game(Opponent opponent){
        this.deck = new Deck();
        //this.upgrades = new Upgrades();
        this.user     = new User(100);
        this.opponent = opponent;
        this.deck.createInGameDeck();
        this.gameDeck = this.deck.getInGameDeck();
        user.drawCards(deck.getInGameDeck(), user.cardsPerHand);
        this.selectedCards = new ArrayList<>(Collections.nCopies(user.getHand().size(),false));
        observers = new GameObservers(this);
        playerTurn = true;
        // this.tm = new turnManager(true); maybe remove idk

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

        if (playerTurn) {

            // Wait for player to make turn
        }
        else {
            opponentTurn();
        }

        //round ends
        if ( user.health <= 0 || opponent.health <= 0) {

        }

    }


    public void gameLoop() {
        while(this.opponent.health>0 && this.user.health>0){


            System.out.println("-------------");
            for (Card c : user.hand) {
                System.out.print(c.rank + " ");
            }
            System.out.println();
            System.out.println("-------------");

            if (this.gameDeck.size() <= this.deck.cards.size() - user.cardsPerHand) { 
                deck.refill(user.hand);
            }


            turn++;
            if (this.opponent.turns != turn) {
                // TODO: add logic to choose cards from hand to play
                // Connect with frontend
                //this.playCards(new ArrayList<Card>((this.user.hand.getFirst(), this.user.hand.getLast())));
                playCards();
            }
            else {
                damage(user, opponent);
                turn = 0;
            }
            System.out.println("Your health: " + user.health + ", Opponent's health: " + opponent.health);
            System.out.println(gameDeck.size());
        }
    }


    public User getUser(){return user;}

    String getGameEndContext(){
        if(this.opponent.health<=0){
            return("You won! :D");
        }
        else if(this.user.health<=0){
            return("You lost! :(");
        }
        else{
            return("IDK man...");
        }
    }


    void damage(Player defender, Player attacker){
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
     */
    public void playCards(){
        //Gör att det blir motståndarens runda
        ArrayList<Card> playedCards = getSelectedCards();
        int damage = user.playCards(playedCards);
        this.opponent.takeDamage(damage);
        System.out.println("Din motståndare tog "+damage+" skada! "+ this.opponent.getHealth(opponent)+ " kvar");
       playerTurn = false;
       observers.notifyHealthChanged(user.getHealthRatio(),opponent.getHealthRatio());
       observers.notifyPlayerTurn(false);
    }



    private void opponentTurn() {
        this.user.takeDamage(opponent.getDamage());
        System.out.println("Du tog " + opponent.getDamage() + " skada! Du har " + this.user.health + " hp kvar");
        playerTurn = true;
        observers.notifyHealthChanged(user.getHealthRatio(),opponent.getHealthRatio());
        observers.notifyPlayerTurn(true);
    }

    private ArrayList<Card>  getSelectedCards() {
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



    public void setSelectedCards(int index, boolean b) {
        boolean newValue = !((boolean) selectedCards.get(index));
         selectedCards.set(index, newValue);
        observers.notifyCardSelect(index,newValue);

    }


}

