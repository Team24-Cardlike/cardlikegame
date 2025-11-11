import java.util.Stack;

public class Game {
    Deck deck;
    Upgrades upgrades;
    User user;
    Opponent opponent;
    Stack<Card> gameDeck;
    Game(){
        this.deck = new Deck();
        this.upgrades = new Upgrades();
        this.user = new User(100);
        this.opponent = new Opponent(500, 5, 2);
        this.gameDeck = this.deck.createInGameDeck();
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

    void damage(Player damagee, Player damaged){
        //TODO: make the damagee damage the damaged
    }
    public static void main(String[]args){
        Game game = new Game();
        while(game.opponent.health>0 && game.user.health>0){
            game.user.health = --game.opponent.damage;
        }
        System.out.println(game.getGameEndContext());
    }
}
