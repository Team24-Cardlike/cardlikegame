import java.util.ArrayList;
import java.util.Stack;

public class Game {
    Deck deck;
    Upgrades upgrades;
    User user;
    Opponent opponent;
    Stack<Card> gameDeck;

    Game(){
        this.deck     = new Deck();
        this.upgrades = new Upgrades();
        this.user     = new User(100);
        this.opponent = new Opponent(500, 5, 2);        
        this.deck.createInGameDeck();
        this.gameDeck = this.deck.getDeck();        

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

    void damage(Player damagee, Player damaged, int damage){
        //TODO: make the damagee damage the damaged
    }

    void gameLoop() {

        this.user.drawCards(this.gameDeck, user.cardsPerHand);

        while(this.opponent.health>0 && this.user.health>0){
            
            // TODO: add logic to choose cards from hand to play
            ArrayList<Card> selectedCards = new ArrayList<>(); // Connect with frontend

            // Test 
            selectedCards.add(this.user.hand.get(0));
            //
            
            int damageToOpponent = this.user.getDamage(selectedCards);
            damage(user, opponent, damageToOpponent);

            this.user.playCards(selectedCards);
            this.user.drawCards(this.gameDeck, selectedCards.size());

            int damageRecieved = this.opponent.getDamage(null);
            this.user.takeDamage(null, damageRecieved);                            

        }
    }

    public static void main(String[]args){
        Game game = new Game();             
        System.out.println(game.getGameEndContext());
    }
}
