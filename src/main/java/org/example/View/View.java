package org.example.View;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import org.example.Model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class View extends ApplicationAdapter  implements GameObserver{
    public FitViewport viewport;
    private SpriteBatch spriteBatch;
    private ShapeRenderer sr;

    Texture background;
    public Image startButton;

    private List<String> handImages = new ArrayList<>();
    public ArrayList<Sprite> cardSprites;
    private Texture opponentTexture;
    private Sprite opponentSprite;
    // Seving hand if LibGDX not yet initialized
    private List<String> tempHand;
    private Game game;
    private Stage stage;
    public ArrayList<Boolean> hoveredCards;
    public ArrayList<Boolean> boolSelectedCards;
    private ArrayList<Sprite> centerSelectedCard;

    private boolean animatingOpponent = false;
    private boolean falling = true;

    private float animationTime = 0f;
    private float fallDuration = 0.3f;  // snabb fallning
    private float riseDuration = 0.6f;  // långsam uppgång

    private float opponentStartY = 3f;      // original Y
    private float opponentDropY = 1.8f;
    int a = 0;
    public ArrayList<Integer> selectedIndices;    

    public void setGame(Game game) {
        this.game = game;
    }
    
    public void create() {
    // public View() {
        centerSelectedCard = new ArrayList<>();
        sr = new ShapeRenderer();
        spriteBatch = new SpriteBatch();
        viewport = new FitViewport(8, 5);
        stage = new Stage(viewport, spriteBatch);
        cardSprites = new ArrayList<>();

        hoveredCards = new ArrayList<>();
        boolSelectedCards = new ArrayList<>();

        startButton = new Image(new Texture("assets/images/enemyEvil.png"));
        startButton.setPosition(0,2);
        startButton.setSize(1.5f, 1);
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
        opponentSprite.setSize(2.5f, 1.5f);
        opponentSprite.setPosition(viewport.getWorldWidth()/2-(1.25f), 3.5f);
        background = new Texture("assets/images/bräde.png");

        
    }

    public void drawHealthBars(){
        sr.setProjectionMatrix(viewport.getCamera().combined);

        // USER HEALTH BAR (bottom-left)
        float x = 0.1f;
        float y = 4.7f;
        float width = 2.5f;
        float height = 0.3f;

        float healthPercent = (float) game.user.health / game.user.maxHealth;

        sr.begin(ShapeRenderer.ShapeType.Filled);

        float greenWidthUser = width * healthPercent;
        float redWidthUser = width - greenWidthUser;

        // RED background
        sr.setColor(Color.RED);
        sr.rect(x, y, redWidthUser, height);

        // GREEN foreground (scaled)
        sr.setColor(Color.GREEN);
        sr.rect(x+redWidthUser, y, greenWidthUser, height);

        // ENEMY HEALTH BAR (top-right)
        float ex = 5.4f;
        float ey = 4.7f;

        float enemyPercent = (float) game.opponent.health / game.opponent.maxHealth;

        float greenWidthOpp = width * enemyPercent;
        float redWidthOpp = width - greenWidthOpp;
        // RED
        sr.setColor(Color.RED);
        sr.rect(ex, ey, redWidthOpp, height);

        // GREEN
        sr.setColor(Color.GREEN);
        sr.rect(ex+redWidthOpp, ey, greenWidthOpp, height);

        sr.end();
    }
    

    public void createSpriteList(){
        cardSprites.clear();        
        
        for (int i = 0; i < game.getUser().getHand().size(); i++) {                        
            Sprite cardSprite = new Sprite(new Texture("assets/images/" + game.getUser().getHand().get(i).pic));
            cardSprite.setSize(0.75f,1.25f);
            cardSprite.setPosition(0.05f + i*0.9f, 0.1f);
            cardSprites.add(cardSprite);

            boolSelectedCards.add(false);
            hoveredCards.add(false);
        }
    }

    //Animation moving card forward
    public void playSelectedCards(){
        float lift = viewport.getWorldHeight() * 0.1f; // t.ex. 10% uppåt
        // ArrayList<Integer> selectedIndices = new ArrayList<>();
        selectedIndices = new ArrayList<>();
        for (int i = 0; i < cardSprites.size(); i++) {
            if (boolSelectedCards.get(i)) {
                selectedIndices.add(i);

                Sprite card = cardSprites.get(i);
                card.setY(card.getY() + lift);
                boolSelectedCards.set(i, false);
            }
        }
        System.out.println(selectedIndices + "" + this.selectedIndices);
        // this.selectedIndices = selectedIndices;
               
    }

    public void draw() {
        centerSelectedCard.clear();

        for (int i = 0; i < cardSprites.size(); i++) {
            if (boolSelectedCards.get(i)) {
                centerSelectedCard.add(cardSprites.get(i));
            }
        }

        ScreenUtils.clear(Color.BLACK);
        viewport.apply();
        spriteBatch.setProjectionMatrix(viewport.getCamera().combined);
        spriteBatch.begin();


        // store the worldWidth and worldHeight as local variables for brevity
        float worldWidth = viewport.getWorldWidth();
        float worldHeight = viewport.getWorldHeight();
        spriteBatch.draw(background, 0, 0, viewport.getWorldWidth(), viewport.getWorldHeight()); // draw the background
        //ArrayList<Sprite> cards = user.getHand();

        for (int i = 0; i < centerSelectedCard.size(); i++) {
            Sprite selectedCard = centerSelectedCard.get(i);

            // temporär position under render
            float cx = 1.5f + i * 0.9f;
            float cy = 2f;

            selectedCard.setPosition(cx, cy);
            selectedCard.setColor(Color.GOLD);
            selectedCard.draw(spriteBatch);
        }
        for (int i = 0; i < cardSprites.size(); i++) {
            Sprite card = cardSprites.get(i);

            if (boolSelectedCards.get(i))
                continue;   // valda kort ritas i mitten istället

            card.setColor(hoveredCards.get(i) ? Color.LIGHT_GRAY : Color.WHITE);
            card.draw(spriteBatch);
        }
        /*
        for (int i = 0; i < cardSprites.size(); i++) {
            Sprite card = cardSprites.get(i);
            Sprite selectedCard = centerSelectedCard.get(i);


            if (boolSelectedCards.get(i)){
                card.setColor(Color.GOLD);
            }
            else if (hoveredCards.get(i)) {
                // lightgrey highlight = hover
                card.setColor(Color.LIGHT_GRAY);
            }
            else
                card.setColor(Color.WHITE);
            card.draw(spriteBatch);
        }*/
        opponentSprite.draw(spriteBatch);
        spriteBatch.end();
        drawHealthBars();
       stage.act(Gdx.graphics.getDeltaTime());
       if(game.getTurnManager().getCurrentPlayer()){
           stage.draw();
       }
    }

    public void oppAnimation(){
        animatingOpponent = true;
        falling = true;
        animationTime = 0;
    }

    public void updateOpponentAnimation(float delta) {
        if (!animatingOpponent) return;

        animationTime += delta;

        if (falling) {
            // Ease-out fall: snabbt → långsamt
            float t = animationTime / fallDuration;
            if (t > 1f) {
                t = 1f;
                falling = false;
                animationTime = 0f; // reset for rise
            }

            // t^0.5 ger snabb start, mjuk slut
            float eased = (float)Math.pow(t, 0.5);
            float newY = opponentStartY + (opponentDropY - opponentStartY) * eased;
            opponentSprite.setY(newY);

        } else {
            // Ease-in rise: långsam → snabb
            float t = animationTime / riseDuration;
            if (t > 1f) {
                t = 1f;
                animatingOpponent = false;
                opponentSprite.setY(opponentStartY);

                game.getTurnManager().swapTurn(); // animation klar → byt tur
                return;
            }

            // t^2 ger långsam start, snabb slut
            float eased = (float)Math.pow(t, 2);
            float newY = opponentDropY + (opponentStartY - opponentDropY) * eased;
            opponentSprite.setY(newY);
        }
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
