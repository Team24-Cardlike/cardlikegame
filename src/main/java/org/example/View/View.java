package org.example.View;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
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
    Image startButton;

    private List<String> handImages = new ArrayList<>();
    private ArrayList<Sprite> cardSprites;
    private Texture opponentTexture;
    private Sprite opponentSprite;
    // Seving hand if LibGDX not yet initialized
    private List<String> tempHand;
    Controller controller;
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

        startButton = new Image(new Texture("assets/images/start (1).png"));
        startButton.setPosition(0,2);
        startButton.setSize(2, 1);
        stage.addActor(startButton);
        Gdx.input.setInputProcessor(stage);
        //if onHandChanged is caled before libGDX inits

        if (tempHand != null) {
            this.handImages = new ArrayList<>(tempHand);
            tempHand = null;
        }
        createSpriteList();

        opponentTexture = new Texture("assets/images/enemyCorrect.png");
        opponentSprite = new Sprite(opponentTexture);
        opponentSprite.setSize(3, 2);
        opponentSprite.setPosition(3,3);
        background = new Texture("assets/images/bräde.png");

        startButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                playSelectedCards();
            }
        });
    }

    @Override
    public void render() {
        float delta = Gdx.graphics.getDeltaTime();
        Controller.input(cardSprites, viewport, hoveredCards, boolSelectedCards);
        playSelectedCards(); // Move cards up
        input();
        draw();         // grafik
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

    //Sends input to controler to update Game
    private void input() {
        //float speed = 4f;
        //float delta = Gdx.graphics.getDeltaTime(); // retrieve the current delta

        Vector3 cords = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
        viewport.getCamera().unproject(cords);

        //Check if user hovers over card
        for (int a = 0; a < cardSprites.size(); a++){
            hoveredCards.set(a,cardSprites.get(a).getBoundingRectangle().contains(cords.x,cords.y));
        }

        if (Gdx.input.justTouched()) {
            for (int i = 0; i < cardSprites.size(); i ++) {
                if (cardSprites.get(i).getBoundingRectangle().contains(cords.x,cords.y)) {
                    boolSelectedCards.set(i,!boolSelectedCards.get(i));
                }
            }
            //Send input to controler

           /* for (int i = 0; i < game.getUser().getHand().size(); i++){
                Sprite card = cardSprites.get(i);
                if(card.getBoundingRectangle().contains(cords.x, cords.y)){
                    boolean bool = game.getUser().getBoolSelectedCards().get(i);
                    game.getUser().setCardAsSelectedBool(i, !bool);
                    //selected.set(i, !selected.get(i));
                }
            } */
        }
    }

    //Animation moving card forward
    private void playSelectedCards(){
        float lift = viewport.getWorldHeight() * 0.1f; // t.ex. 10% uppåt
        ArrayList<Integer> selectedIndices = new ArrayList<>();
        for (int i = 0; i < cardSprites.size(); i++) {
            if (boolSelectedCards.get(i)) {
                selectedIndices.add(i);

                Sprite card = cardSprites.get(i);
                card.setY(card.getY() + lift);
                boolSelectedCards.set(i, false);
            }
        }
        controller.onPlaySelectedCards(selectedIndices);
    }

    private void draw() {
        ScreenUtils.clear(Color.BLACK);
        viewport.apply();
        spriteBatch.setProjectionMatrix(viewport.getCamera().combined);
        spriteBatch.begin();


        // store the worldWidth and worldHeight as local variables for brevity
        float worldWidth = viewport.getWorldWidth();
        float worldHeight = viewport.getWorldHeight();
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
        opponentSprite.draw(spriteBatch);
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
    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true); // true centers the camera

        // If the window is minimized on a desktop (LWJGL3) platform, width and height are 0, which causes problems.
        // In that case, we don't resize anything, and wait for the window to be a normal size before updating.
        if(width <= 0 || height <= 0) return;

        // Resize your application here. The parameters represent the new window size.
    }


}
