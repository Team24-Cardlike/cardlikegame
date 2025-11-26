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
    turnManager tm;

    public Game(Opponent opponent){
        this.deck = new Deck();
        //this.upgrades = new Upgrades();
        this.user     = new User(100);
        this.opponent = opponent;
        this.deck.createInGameDeck();
        this.gameDeck = this.deck.getInGameDeck();
        user.drawCards(deck.getInGameDeck(), user.cardsPerHand);
        observers = new GameObservers(this);
        this.tm = new turnManager(true);
    }
    public turnManager getTurnManager(){
        return tm;
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
                //playCards(user.getSelectedCards());
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
     * @param playedCards cards played from the front end
     */
    public void playCards(ArrayList<Card> playedCards){
        //Gör att det blir motståndarens runda
        int damage = user.playCards(playedCards);
        this.opponent.takeDamage(damage);
        System.out.println("Din motståndare tog "+damage+" skada! "+ this.opponent.getHealth(opponent)+ " kvar");
        // controller.updateView(playedCards); // TODO: REMOVE
        tm.swapTurn();
        if(!tm.getCurrentPlayer()){
            this.user.takeDamage(opponent.getDamage());
            System.out.println("Du tog "+opponent.getDamage()+" skada! Du har "+ this.user.health+ " hp kvar");
            // controller.opponentAnimation();

            //tm.swapTurn();
        }
        /**this.user.playCards(playedCards);
        damage(opponent, user);
        this.user.drawCards(this.gameDeck, this.user.selectedCards.size());
        this.user.selectedCards.clear();*/

    }
/*
    public void playCards(ArrayList<Integer> selectedCards) {
        int totalDamage = 0;

        for (int index : selectedCards) {
            Card c = user.getHand().get(index);
            totalDamage += c.rank;
            // eller kombologik här
        }

        opponent.takeDamage(totalDamage);

        // uppdatera handen
        user.removeCards(selectedCards);

        notifyHandChanged();
        notifyHealthChanged(player.getHealth(), opponent.getHealth());
    }*/
}

