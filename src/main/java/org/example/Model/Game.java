package org.example.Model;

// import java.util.ArrayList;
import java.util.Stack;

public class Game {

    Deck deck;
    //Upgrades upgrades;
    User user;
    Opponent opponent;
    Stack<Card> gameDeck;   
    int turn = 0; 

    Game(){
        this.deck     = new Deck();
        //this.upgrades = new Upgrades();
        this.user     = new User(100);
        this.opponent = new Opponent(500, 5, 6);        
        this.deck.createInGameDeck();
        this.gameDeck = this.deck.getInGameDeck();        

        this.gameLoop();
    }

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
        //TODO: make the damagee damage the damaged
    }

    void gameLoop() {

        this.user.drawCards(this.gameDeck, user.cardsPerHand);

        while(this.opponent.health>0 && this.user.health>0){    
                        
                        
            if (this.gameDeck.size() <= this.deck.cards.size() - user.cardsPerHand) { // Hard-coded                
                deck.refill(user.hand);                                
            }
                                                                                                 
            
            turn++;
            if (this.opponent.turns != turn) {
                
                // TODO: add logic to choose cards from hand to play
                // Connect with frontend
                this.user.selectedCards.add(this.user.hand.getFirst()); // TEMPORARY
                this.user.selectedCards.add(this.user.hand.getLast());

                damage(opponent, user);
                                
                this.user.playCards(this.user.selectedCards);
                this.user.drawCards(this.gameDeck, this.user.selectedCards.size());                               
                                
                this.user.selectedCards.clear();
            
            }
            else {                              
                damage(user, opponent);        
                turn = 0;
            }
            System.out.println("Your health: " + user.health + ", Opponent's health: " + opponent.health);
            System.out.println(gameDeck.size());
            
            for (Card c : user.hand) {
                System.err.print(c.rank + " ");
            }
            System.out.println();
        }
    }

    public static void main(String[]args){
        Game game = new Game();             
        System.out.println(game.getGameEndContext());
    }
}
