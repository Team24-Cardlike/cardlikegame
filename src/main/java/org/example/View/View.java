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

import org.example.Controller.Controller;
import org.example.Model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class View extends ApplicationAdapter  implements GameObserver{
    FitViewport viewport;
    private SpriteBatch spriteBatch;
    Texture background;
    private List<String> handImages = new ArrayList<>();
    private ArrayList<Sprite> cardSprites;

    // Seving hand if LibGDX not yet initialized
    private List<String> tempHand;

    private Stage stage;
    private ArrayList<Boolean> hoveredCards;
    private ArrayList<Boolean> boolSelectedCards;


    @Override
    public void create() {
        spriteBatch = new SpriteBatch();
        viewport = new FitViewport(8, 5);
        stage = new Stage(viewport, spriteBatch);
        cardSprites = new ArrayList<>();
        hoveredCards = new ArrayList<>();
        boolSelectedCards = new ArrayList<>();

        //if onHandChanged is caled before libGDX inits

        if (tempHand != null) {
            this.handImages = new ArrayList<>(tempHand);
            tempHand = null;
        }
        createSpriteList();
        background = new Texture("assets/images/bräde.png");
    }

    @Override
    public void render() {
        float delta = Gdx.graphics.getDeltaTime();
        Controller.input(cardSprites, viewport, hoveredCards, boolSelectedCards);
        playSelectedCards(); // Move cards up
        draw();              // graphics
    }

    private void createSpriteList(){
        cardSprites.clear();
        hoveredCards = new ArrayList<>();
        boolSelectedCards = new ArrayList<>();

        for (int i = 0; i < handImages.size(); i++) {
            Sprite cardSprite = new Sprite(new Texture("assets/images/" + handImages.get(i)));
            cardSprite.setSize(1,1.5f);
            cardSprite.setPosition(0.25f + i * 1.2f, 0.25f);
            cardSprites.add(cardSprite);

            boolSelectedCards.add(false);
            hoveredCards.add(false);
        }
    }

    //Animation moving card forward
    public void playSelectedCards(){
        float lift = viewport.getWorldHeight() * 0.1f; // 10% up
        for (int i = 0; i < cardSprites.size(); i++) {
            if (boolSelectedCards.get(i)) {
                Sprite card = cardSprites.get(i);
                card.setY(card.getY() + lift);
                boolSelectedCards.set(i, false);}
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
        spriteBatch.draw(background, 0, 0, viewport.getWorldWidth(), viewport.getWorldHeight()); // draw the background
        //ArrayList<Sprite> cards = user.getHand();

        for (int i = 0; i < cardSprites.size(); i++) {
            Sprite card = cardSprites.get(i);

            if (boolSelectedCards.get(i))
                card.setColor(Color.GOLD);
            else if (hoveredCards.get(i)) {
                // lightgrey highlight = hover
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




    @Override
    //Draw updated hand
    public void onHandChanged(List<String> hand) {
        if (spriteBatch == null) { // If LibGDX not initialized shave hand
            tempHand = new ArrayList<>(hand);}
        else {
        this.handImages = new ArrayList<>(hand); //Save updated hand
        createSpriteList();}        
    }

    @Override

    public void onHealthChanged(int userHealth, int opponentHealth) {

    }

    @Override
    public void onCardSelected(int index, boolean selected) {

    }

    @Override
    public void onGameEnded(String resultMessage) {

    }


}
