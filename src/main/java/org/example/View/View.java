package org.example.View;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import org.example.Model.*;

import java.util.ArrayList;
import java.util.Stack;

public class View extends ApplicationAdapter {
    FitViewport viewport;
    private SpriteBatch spriteBatch;
    private Game game;
    private ArrayList<Sprite> cardSprites;
    private Board board;
    private User user;
    private Stage stage;
    private Stack<Card> gameDeck;
    private Deck deck;
    private ArrayList<Boolean> hoveredCards;
    private ArrayList<Boolean> boolSelectedCards;


    @Override
    public void create() {
        game = new Game();  // modellen har ingen rendering
        user = game.getUser();

        deck = new Deck();
        deck.createInGameDeck();
        gameDeck = deck.getInGameDeck();

        user.drawCards(this.gameDeck, user.cardsPerHand);
        spriteBatch = new SpriteBatch();
        board = new Board("bräde");
        viewport = new FitViewport(8, 5);
        stage = new Stage(viewport, spriteBatch);
        cardSprites = new ArrayList<>();
        createSpriteList();
    }

    @Override
    public void render() {
        float delta = Gdx.graphics.getDeltaTime();
        input();
        game.update(delta);
        draw();         // grafik
    }

    private void createSpriteList(){

        for (int i = 0; i < user.getHand().size(); i++) {
            Sprite cardSprite = new Sprite(new Texture("assets/images/3sun.png"));
            cardSprites.add(cardSprite);
            cardSprite.setSize(1,2);
            cardSprite.setPosition(1 + i * 1.2f, 1);

            user.getBoolSelectedCards().add(false);
            user.getHoveredCards().add(false);
        }
    }
    private void input() {
        //float speed = 4f;
        //float delta = Gdx.graphics.getDeltaTime(); // retrieve the current delta

        Vector3 cords = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
        viewport.getCamera().unproject(cords);
        ArrayList<Card> cards = game.getUser().getHand();

        for (int a = 0; a < cards.size(); a++){
            Sprite card = cardSprites.get(a);
            //Sprite cardSprite = new Sprite(cards.get(a).getCardTexture());
            game.getUser().setCardAsHovered(a, card.getBoundingRectangle().contains(cords.x, cords.y));
        }

        if (Gdx.input.justTouched()) {

            for (int i = 0; i < game.getUser().getHand().size(); i++){
                Sprite card = cardSprites.get(i);
                if(card.getBoundingRectangle().contains(cords.x, cords.y)){
                    boolean bool = game.getUser().getBoolSelectedCards().get(i);
                    game.getUser().setCardAsSelectedBool(i, !bool);
                    //selected.set(i, !selected.get(i));
                }
            }
        }
    }

    private void draw() {
        ScreenUtils.clear(Color.BLACK);
        viewport.apply();
        spriteBatch.setProjectionMatrix(viewport.getCamera().combined);
        spriteBatch.begin();

        // store the worldWidth and worldHeight as local variables for brevity
        //float worldWidth = viewport.getWorldWidth();
        //float worldHeight = viewport.getWorldHeight();
        Texture background = board.getBoard();
        spriteBatch.draw(background, 0, 0, viewport.getWorldWidth(), viewport.getWorldHeight()); // draw the background
        //ArrayList<Sprite> cards = user.getHand();

        for (int i = 0; i < cardSprites.size(); i++) {
            Sprite card = cardSprites.get(i);

            if (user.getBoolSelectedCards().get(i))
                card.setColor(Color.GOLD);
            else if (user.getHoveredCards().get(i)) {
                // Ljusgrå highlight = hover
                card.setColor(Color.LIGHT_GRAY);
            }
            else
                card.setColor(Color.WHITE);
            card.draw(spriteBatch);
        }
        spriteBatch.end();

       stage.act(Gdx.graphics.getDeltaTime());
       stage.draw();
    }
}
