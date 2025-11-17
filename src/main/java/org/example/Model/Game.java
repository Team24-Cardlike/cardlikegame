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
    //Viewport viewport;
    //SpriteBatch spriteBatch;
    //Stage stage;

    ArrayList<Sprite> spriteList;
    ArrayList<Boolean> selected;
    ArrayList<Boolean> hovered;

    public Game(){
        this.deck = new Deck();
        //this.upgrades = new Upgrades();
        this.user     = new User(100);
        this.opponent = new Opponent(500, 5, 6);        
        this.deck.createInGameDeck();
        this.gameDeck = this.deck.getInGameDeck();
        user.drawCards(deck.getInGameDeck(), user.cardsPerHand);
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

    public void update(float delta) {
        if (this.opponent.health <= 0 || this.user.health <= 0) return;

        // exempel på tur-logik
        turn++;
        if (turn >= this.opponent.turns) {
            damage(user, opponent);
            turn = 0;
        }

        // Dra kort EN gång per runda
        if (user.hand.size() < user.cardsPerHand) {
            int toDraw = user.cardsPerHand - user.hand.size();
            while (!gameDeck.isEmpty() && toDraw > 0) {
                user.drawCards(gameDeck, 1);
                toDraw--;
            }
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

    public void create() {
        Board board = new Board("bräde");
        //background =  new Texture(Gdx.files.internal("assets/images/bräde.png"));
        Texture background = new Texture(Gdx.files.internal("assets/images/" + "bräde"+ ".png"));
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


        Image startButton = new Image(new Texture("assets/images/start (1).png"));
        startButton.setPosition(0,0);
        startButton.setSize(2, 1);

        startButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                playSelectedCards();
            }
        });
    }

    private void playSelectedCards(){
        float lift = 10;//viewport.getWorldHeight() * 0.1f; // t.ex. 10% uppåt
        for (int i = 0; i < user.getHand().size(); i++) {
            if (selected.get(i)) {
                Sprite card = spriteList.get(i);
                card.setY(card.getY() + lift);
                selected.set(i, false);
            }
        }
    }

    /*public static void main(String[]args){
        Game game = new Game();
        game.create();
        System.out.println(game.getGameEndContext());
    }*/
}
