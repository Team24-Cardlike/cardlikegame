package org.example.Model;

// import java.util.ArrayList;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.ArrayList;
import java.util.Stack;

public class Game {

    Deck deck;
    //Upgrades upgrades;
    User user;
    Opponent opponent;
    Stack<Card> gameDeck;   
    int turn = 0;
    Viewport viewport;
    SpriteBatch spriteBatch;
    Stage stage;

    ArrayList<Sprite> spriteList;
    ArrayList<Boolean> selected;
    ArrayList<Boolean> hovered;

    Game(){
        this.deck     = new Deck();
        //this.upgrades = new Upgrades();
        this.user     = new User(100);
        this.opponent = new Opponent(500, 5, 6);        
        this.deck.createInGameDeck();
        this.gameDeck = this.deck.getInGameDeck();        

        this.gameLoop();
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

    public void create() {
        Board board = new Board("bräde");
        //background =  new Texture(Gdx.files.internal("assets/images/bräde.png"));
        Texture background = board.getBoard();
        //card = new Texture("assets/images/3sun.png");
        spriteList = new ArrayList<Sprite>();
        selected = new ArrayList<Boolean>();
        hovered = new ArrayList<Boolean>();

        for(int i = 0; i < user.getHand().size(); i++){
            Texture card = (user.getHand().get(i).getCardTexture());
            Sprite cardSprite = new Sprite(card);
            cardSprite.setSize(1,2);
            spriteList.add(cardSprite);
            cardSprite.setPosition(1+i*1.2f,1);
            selected.add(false);
            hovered.add(false);
        }

        viewport = new FitViewport(8, 5);
        spriteBatch = new SpriteBatch();
        stage = new Stage(viewport, spriteBatch);

        Image startButton = new Image(new Texture("assets/images/start (1).png"));
        startButton.setPosition(0,0);
        startButton.setSize(2, 1);
        stage.addActor(startButton);
        Gdx.input.setInputProcessor(stage);


        startButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                playSelectedCards();
            }
        });
    }

    private void playSelectedCards(){
        float lift = viewport.getWorldHeight() * 0.1f; // t.ex. 10% uppåt
        for (int i = 0; i < user.getHand().size(); i++) {
            if (selected.get(i)) {
                Sprite card = spriteList.get(i);
                card.setY(card.getY() + lift);
                selected.set(i, false);
            }
        }
    }

    public static void main(String[]args){
        Game game = new Game();
        game.create();
        System.out.println(game.getGameEndContext());
    }
}
