package org.example.Model;


import java.util.ArrayList;
import java.util.Stack;

public class Game {

    public GameObservers observers;

    Deck deck;
    //Upgrades upgrades;
    public User user;
    public Opponent opponent;
    Stack<Card> gameDeck;   
    int turn = 0;

    //Stage stage;


    public Game(){
        this.deck = new Deck();
        //this.upgrades = new Upgrades();
        this.user     = new User(100);
        this.opponent = new Opponent(500, 5, 6, "enemyEvil");
        this.deck.createInGameDeck();
        this.gameDeck = this.deck.getInGameDeck();
        user.drawCards(deck.getInGameDeck(), user.cardsPerHand);
        observers = new GameObservers(this);
    }

    public void gameLoop() {

        while(this.opponent.health>0 && this.user.health>0){
            System.out.println("-------------");
            for (Card c : user.hand) {
                System.out.print(c.rank + " ");
            }
            System.out.println();
            System.out.println("-------------");

            if (this.gameDeck.size() <= this.deck.cards.size() - user.cardsPerHand) { // Hard-coded
                deck.refill(user.hand);
            }


            turn++;
            if (this.opponent.turns != turn) {
                // TODO: add logic to choose cards from hand to play
                // Connect with frontend
                //this.playCards(new ArrayList<Card>((this.user.hand.getFirst(), this.user.hand.getLast())));
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
    void playCards(ArrayList<Card> playedCards){
        this.user.playCards(playedCards);
        damage(opponent, user);
        this.user.drawCards(this.gameDeck, this.user.selectedCards.size());
        this.user.selectedCards.clear();

    }
}

