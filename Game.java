import java.util.ArrayList;
import java.util.Stack;

public class Game {

    Deck deck;
    Upgrades upgrades;
    User user;
    Opponent opponent;
    Stack<Card> gameDeck;   
    int turn = 0; 

    Game(){
        this.deck     = new Deck();
        this.upgrades = new Upgrades();
        this.user     = new User(100);
        this.opponent = new Opponent(500, 5, 10);        
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
                        
            
            // TODO: Refill deck when needed
            if (this.gameDeck.size() <= 5) {
                this.deck.createInGameDeck();
                this.gameDeck = this.deck.getInGameDeck();  
            }
            
                                                             
                        
            
            turn++;
            if (this.opponent.turns != turn) {
                
                // TODO: add logic to choose cards from hand to play
                // Connect with frontend
                this.user.selectedCards.add(this.user.hand.getFirst()); // TEMPORARY

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
        }
    }

    public static void main(String[]args){
        Game game = new Game();             
        System.out.println(game.getGameEndContext());
    }
}
