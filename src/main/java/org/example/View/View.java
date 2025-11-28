package org.example.View;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import org.example.Model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class View extends ApplicationAdapter  implements GameObserver{
    public ScreenViewport viewport;
    private SpriteBatch spriteBatch;
    private ShapeRenderer sr;

    Texture background;

    public Image victoryWindow;
    public Image startButton;
    public Image discardButton;
    public Image nextButton;

    public Texture nextButtonTexture;
    public Texture vicTxt;
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
    private Label currentComboLabel;

    private boolean animatingOpponent = false;
    private boolean falling = true;
    Label.LabelStyle style;
    private float animationTime = 0;
    private float fallDuration = 0.3f;  // snabb fallning
    private float riseDuration = 0.6f;  // långsam uppgång

    private float opponentStartY = 300;      // original Y
    private float opponentDropY = 180;
    int a = 0;
    public ArrayList<Integer> selectedIndices;
    public ArrayList<Integer> removedIndices;

    public void setGame(Game game) {
        this.game = game;
    }
    
    public void create() {
    // public View() {
        BitmapFont font = new BitmapFont(); // standard font
        style = new Label.LabelStyle();
        style.font = font;

        vicTxt = new Texture("assets/images/victoryPlaceholder.png");
        nextButtonTexture = new Texture("assets/images/nextPlaceholder.png");

        centerSelectedCard = new ArrayList<>();
        sr = new ShapeRenderer();
        spriteBatch = new SpriteBatch();

        //viewport = new FitViewport(8, 5);
        viewport = new ScreenViewport();
        stage = new Stage(viewport, spriteBatch);
        cardSprites = new ArrayList<>();

        hoveredCards = new ArrayList<>();
        boolSelectedCards = new ArrayList<>();

        discardButton = new Image(new Texture("assets/images/discardTest.png"));
        discardButton.setPosition(600,200);
        discardButton.setSize(80, 100);
        stage.addActor(discardButton);


        startButton = new Image(new Texture("assets/images/enemyEvil.png"));
        startButton.setPosition(0,200);
        startButton.setSize(150, 100);
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
        opponentSprite.setSize(250, 150);
        opponentSprite.setPosition((float) Gdx.graphics.getWidth() /2-(125), 400);
        background = new Texture("assets/images/bräde.png");

        
    }

    public void drawHealthBars(){
        sr.setProjectionMatrix(viewport.getCamera().combined);

        // USER HEALTH BAR (bottom-left)
        float x = 50;
        float y = 550;
        float width = 250;
        float height = 30;

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
        float ex = 520;
        float ey = 550;

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
            cardSprite.setSize(75,125);
            cardSprite.setPosition(10 + i*90, 25);
            cardSprites.add(cardSprite);

            boolSelectedCards.add(false);
            hoveredCards.add(false);
        }
    }

    //Animation moving card forward
    public void playSelectedCards(){
        float lift = viewport.getWorldHeight() * 10; // t.ex. 10% uppåt
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
        spriteBatch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight()); // draw the background
        //ArrayList<Sprite> cards = user.getHand();

        for (int i = 0; i < centerSelectedCard.size(); i++) {
            Sprite selectedCard = centerSelectedCard.get(i);

            // temporär position under render
            float cx = 150 + i * 90;
            float cy = 200;

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

       if(game.getTurnManager().getCurrentPlayer() || !game.gameState){
           stage.draw();
       }
    }

    public void oppAnimation(){
        animatingOpponent = true;
        falling = true;
        animationTime = 0;
    }

    public void throwCards(){
        removedIndices = new ArrayList<>();

        for (int i = cardSprites.size()-1; i >= 0; i--) {
            if (boolSelectedCards.get(i)) {
                removedIndices.add(i);

                cardSprites.remove(i);
                boolSelectedCards.remove(i);
            }
        }
    }

    public void endGame(int totToPlayer, int totToOpp){
        Image panel = new Image(new TextureRegionDrawable(vicTxt));
        panel.setSize(600, 400);
        panel.setPosition(100,50);
        stage.addActor(panel);

        Label label1 = new Label("Post-game statistics", style);
        label1.setPosition(200,200);
        Label label2 = new Label("You did: " + totToOpp+" damage to your enemy!", style);
        label2.setPosition(200,150);
        Label label3 = new Label("You took: "+ totToPlayer+" damage", style);
        label3.setPosition(200,100);


        stage.addActor(label1);
        stage.addActor(label2);
        stage.addActor(label3);


        nextButton = new Image(nextButtonTexture);
        nextButton.setPosition(600,200);
        nextButton.setSize(80, 100);

        stage.addActor(nextButton);

    }

    public void showCombo(String comboName){
        if (currentComboLabel != null) {
            currentComboLabel.remove();
        }

        currentComboLabel = new Label(comboName, style);
        currentComboLabel.setPosition(200,200);
        System.out.println(currentComboLabel);
        stage.addActor(currentComboLabel);
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
