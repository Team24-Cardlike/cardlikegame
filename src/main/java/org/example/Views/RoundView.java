package org.example.Views;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
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

import org.example.Controller.RoundController;
import com.badlogic.gdx.utils.viewport.Viewport;
import org.example.Model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Stack;

public class RoundView extends ApplicationAdapter implements RoundObserver, Screen {
    public FitViewport viewport;
    private SpriteBatch spriteBatch;
    private ShapeRenderer sr;

    Texture background;

    public Image victoryWindow;
    public Image startButton;
    private Image menuButton;
    public Image discardButton;
    public Image nextButton;
    public Image retryButton;
    public Image shopButton;

    public Texture shopButtonTexture;
    public Texture retryButtonTexture;
    public Texture nextButtonTexture;
    public Texture vicTxt;
    public Texture lossTxt;

    //ArrayList containing card-sprites for selected cards and hand
    public ArrayList<Sprite> cardSprites = new ArrayList<>();
    public ArrayList<Sprite> selectedCardSprites = new ArrayList<>();

    private Texture opponentTexture;
    private Sprite opponentSprite;

    // Seving hand if LibGDX not yet initialized
    private List<String> tempHand;
    private Stage stage;
    public ArrayList<Boolean> hoveredCards = new ArrayList<>();;
    private Label currentComboLabel;
    public Vector3 coords;
    private Image panel;

    private boolean animatingOpponent = false;
    private boolean falling = true;
    Label.LabelStyle style;
    private float animationTime = 0;
    private float fallDuration = 0.3f;  // snabb fallning
    private float riseDuration = 0.6f;  // långsam uppgång

    private float opponentStartY = 300;      // original Y
    private float opponentDropY = 180;

    private float opponentHealthPercentage;
    private float userHealthPercentage;
    private boolean playerTurn;
    private boolean gameEnded = false;
    private boolean isVictory = false;

    int a = 0;
    public ArrayList<Integer> selectedIndices;
    public ArrayList<Integer> removedIndices;
    private GameManager gameManager;
    private RoundController roundController;
    private String currentBestCombo;
    private int totalDamageToOpponent;
    private int totalDamageToUser;

    public void setGameManager (GameManager manager ) {
        this.gameManager = manager;
    }

    public void setController(RoundController roundController) {
        this.roundController = roundController;
    }


    /**
     * Initialize buttons and add listeners
     */
    @Override
    public void show() {
        BitmapFont font = new BitmapFont(); // standard font
        style = new Label.LabelStyle();
        style.font = font;

        vicTxt = new Texture("assets/images/victory.png");
        lossTxt = new Texture("assets/images/gameover.png");
        nextButtonTexture = new Texture("assets/images/nextbutton.png");
        retryButtonTexture = new Texture("assets/images/nextPlaceholder.png");
        sr = new ShapeRenderer();
        spriteBatch = new SpriteBatch();

        OrthographicCamera camera = new OrthographicCamera();
        camera.setToOrtho(false, 8, 5);
        camera.position.set(4, 2.5f, 0); // center camera
        camera.update();
        viewport = new FitViewport(800, 600, camera);

        stage = new Stage(viewport, spriteBatch);
        cardSprites = new ArrayList<>();

        shopButton = new Image(new Texture("assets/images/victoryPlaceholder.png"));
        shopButton.setPosition(5, 400);
        shopButton.setSize(150, 100);
        stage.addActor(shopButton);

        menuButton = new Image(new Texture("assets/images/victoryPlaceholder.png"));
        menuButton.setPosition(0.2f, 3.5f);
        menuButton.setSize(1, 0.7f);
        stage.addActor(menuButton);

        discardButton = new Image(new Texture("assets/images/discard.png"));
        discardButton.setPosition(600, 200);
        discardButton.setSize(80, 100);
        stage.addActor(discardButton);



        startButton = new Image(new Texture("assets/images/endTurn.png"));
        startButton.setPosition(0, 200);
        startButton.setSize(150, 100);
        stage.addActor(startButton);
        Gdx.input.setInputProcessor(stage);


        opponentTexture = new Texture("assets/images/enemyCorrect.png");
        opponentSprite = new Sprite(opponentTexture);
        opponentSprite.setSize(250f, 150f);
        opponentSprite.setPosition(
                viewport.getWorldWidth() / 2f - opponentSprite.getWidth() / 2f,
                viewport.getWorldHeight() - 250
        );
        background = new Texture("assets/images/bräde.png");


        startButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                onPlaySelectedCards();
            
            }
        });

        discardButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                //throwCards();
                roundController.discardCards(removedIndices);
            }
        });
        shopButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                //throwCards();
                roundController.switchView("shop");
            }
        });
        input();
    }

    /**
     * Handles selecting and unselecting cards
     */
    public void input() {
        coords = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
        viewport.unproject(coords);

        if (Gdx.input.justTouched()) {

            for (int a = cardSprites.size() - 1; a >= 0; a--) {
                Polygon poly = generateHitbox(a, cardSprites);

                //Send input to roundController
                if (poly.contains(coords.x, coords.y) && (selectedCardSprites.size() < 5)) {
                    roundController.selectCard(a);

                    break;
                }
            }

            //SelectLoop
            for (int a = selectedCardSprites.size() -1; a >= 0; a--) {
                Polygon poly = generateHitbox(a,selectedCardSprites);
                //Send input to roundController
                if (poly.contains(coords.x, coords.y)) {
                    roundController.unselectCard(a);
                    break;
                }

            }
        }
    }



    public void onPlaySelectedCards(){
        roundController.playCards();        
        if(playerTurn){
            opponentAnimation();
        }
        // if (gameEnded) {
        //     //Display match stats
        //     // endGame();
        //     // Adding next button
        //     if(isVictory){
        //         nextButton.addListener(new ClickListener(){
        //             @Override
        //             public void clicked(InputEvent event, float x, float y){                        
        //                 roundController.nextRound();  
        //                 isVictory = false;
        //                 gameEnded = false;                                                                  
        //             }
        //         });                
        //     }

        //     else{
        //         retryButton.addListener(new ClickListener(){
        //             @Override
        //             public void clicked(InputEvent event, float x, float y){
        //                 roundController.restart();
        //                 nextRound();
        //             }
        //         });
        //     }
        // }
    }


    public void updateView(ArrayList<Card> cards) {
        createSpriteList();
    }

    public void opponentAnimation() {
        oppAnimation();
    }

    public Stage getStage() {
        return stage;
    }

    public void drawHealthBars() {
        sr.setProjectionMatrix(viewport.getCamera().combined);

        // USER HEALTH BAR (bottom-left)
        float x = 50;
        float y = 550;
        float width = 250;
        float height = 30;

        //float healthPercent = (float) round.user.health / round.user.maxHealth; TODO REMOVE

        sr.begin(ShapeRenderer.ShapeType.Filled);

        float greenWidthUser = width * userHealthPercentage;
        float redWidthUser = width - greenWidthUser;

        // RED background
        sr.setColor(Color.RED);
        sr.rect(x, y, redWidthUser, height);

        // GREEN foreground (scaled)
        sr.setColor(Color.GREEN);
        sr.rect(x + redWidthUser, y, greenWidthUser, height);

        // ENEMY HEALTH BAR (top-right)
        float ex = 520;
        float ey = 550;

       //  float enemyPercent = (float) game.opponent.health / game.opponent.maxHealth; TODO REMOVE

        float greenWidthOpp = width * opponentHealthPercentage;
        float redWidthOpp = width - greenWidthOpp;
        // RED
        sr.setColor(Color.RED);
        sr.rect(ex, ey, redWidthOpp, height);

        // GREEN
        sr.setColor(Color.GREEN);
        sr.rect(ex + redWidthOpp, ey, greenWidthOpp, height);

        sr.end();
    }
    
    // Creates
    public void createSpriteList( ){


    }


    /**
     * AI-generated solution for getting the correct hitbox now that the
     * cards are rotated
     * @param index index of the card
     * @param cards all cards in hand
     * @return
     */
    private Polygon generateHitbox(int index,ArrayList<Sprite> cards) {
        Sprite sprite = cards.get(index);
        float[] vertices = new float[]{
                0, 0,
                sprite.getWidth(), 0,
                sprite.getWidth(), sprite.getHeight(),
                0, sprite.getHeight()
        };
        Polygon poly = new Polygon(vertices);

        poly.setOrigin(sprite.getOriginX(), sprite.getOriginY());
        poly.setPosition(sprite.getX(), sprite.getY());
        poly.setRotation(sprite.getRotation());
        poly.setScale(sprite.getScaleX(), sprite.getScaleY());
        return poly;
    }


    /**
     * Check if user hovers over card
     */
    public void getHoverdCards() {
        for (int a = 0; a < cardSprites.size(); a++){
            Polygon poly = generateHitbox(a, cardSprites);
            if (poly.contains(coords.x, coords.y)) {                
                hoveredCards.set(a, true);
                for (int i = 0; i < cardSprites.size(); i++) {
                    if (i != a) hoveredCards.set(i, false);
                }

            } else {
                hoveredCards.set(a, false);
            }
        }
    }

    public void draw() {
        getHoverdCards();
        ScreenUtils.clear(Color.BLACK);
        viewport.apply();
        spriteBatch.setProjectionMatrix(viewport.getCamera().combined);
        spriteBatch.begin();

        // store the worldWidth and worldHeight as local variables for brevity
        float worldWidth = viewport.getWorldWidth();
        float worldHeight = viewport.getWorldHeight();
        spriteBatch.draw(background, 0, 0, viewport.getWorldWidth(), viewport.getWorldHeight()); // draw the background
        //ArrayList<Sprite> cards = user.getHand();

        drawCardSprites();
        showCombo(currentBestCombo);
        opponentSprite.draw(spriteBatch);

        spriteBatch.end();
        drawHealthBars();
        stage.act(Gdx.graphics.getDeltaTime());


        if (playerTurn || gameEnded) {
            stage.draw();
        }
    }

    public void drawCardSprites() {

        for (int i = 0; i < selectedCardSprites.size(); i++) {
            Sprite selectedCard = selectedCardSprites.get(i);

            float cx = 150 + i * 90;
            float cy = 200;

            selectedCard.setPosition(cx, cy);
            selectedCard.setColor(Color.GOLD);
            selectedCard.draw(spriteBatch);
        }
        for (int i = 0; i < cardSprites.size(); i++) {

            Sprite card = cardSprites.get(i);
            card.setColor(hoveredCards.get(i) ? Color.LIGHT_GRAY : Color.WHITE);
            card.draw(spriteBatch);
        }
    }

    public void oppAnimation() {
        animatingOpponent = true;
        falling = true;
        animationTime = 0;
    }

    public void throwCards() {
        removedIndices = new ArrayList<>();
    }

    public void endGame(){
        
        if(isVictory){
            panel = new Image(new TextureRegionDrawable(vicTxt)); 
            opponentHealthPercentage = 0;                  
        }
        else{
            panel = new Image(new TextureRegionDrawable(lossTxt));
            userHealthPercentage = 0;
        }
        panel.setSize(600, 400);
        panel.setPosition(100,50);
        stage.addActor(panel);

        Label label1 = new Label("Post-round statistics", style);
        label1.setPosition(200,200);
        Label label2 = new Label("You did: " + totalDamageToOpponent+" damage to your enemy!", style);
        label2.setPosition(200,150);
        Label label3 = new Label("You took: "+ totalDamageToUser+" damage", style);
        label3.setPosition(200,100);

        stage.addActor(label1);
        stage.addActor(label2);
        stage.addActor(label3);

        if(true)
        {
            nextButton = new Image(nextButtonTexture);
            nextButton.setPosition(600,200);
            nextButton.setSize(80, 50);
            stage.addActor(nextButton);            
        }
        else{
            retryButton = new Image(nextButtonTexture);
            retryButton.setPosition(600,200);
            retryButton.setSize(80, 50);
            stage.addActor(retryButton);
        }

        if (gameEnded) {
            //Display match stats
            // endGame();
            // Adding next button
            if(true){
                nextButton.addListener(new ClickListener(){
                    @Override
                    public void clicked(InputEvent event, float x, float y){                        
                        roundController.nextRound();  
                        isVictory = false;
                        gameEnded = false;                                                                  
                    }
                });                
            }

            else{
                retryButton.addListener(new ClickListener(){
                    @Override
                    public void clicked(InputEvent event, float x, float y){
                        roundController.restart();
                        nextRound();
                    }
                });
            }
        }
    }

    public void showCombo(String comboName){
        if (currentComboLabel != null) {
            currentComboLabel.remove();
        }

        currentComboLabel = new Label(comboName, style);
        currentComboLabel.setPosition(200, 200);
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
            float eased = (float) Math.pow(t, 0.5);
            float newY = opponentStartY + (opponentDropY - opponentStartY) * eased;
            opponentSprite.setY(newY);

        } else {
            // Ease-in rise: långsam → snabb
            float t = animationTime / riseDuration;
            if (t > 1f) {
                t = 1f;
                animatingOpponent = false;
                opponentSprite.setY(opponentStartY);

                 // game.getTurnManager().swapTurn(); // animation klar → byt tur TODO Remove, should be updated in model
                return;
            }
            // t^2 ger långsam start, snabb slut
            float eased = (float) Math.pow(t, 2);
            float newY = opponentDropY + (opponentStartY - opponentDropY) * eased;
            opponentSprite.setY(newY);
        }
    }

    public void resetView() {
        if (stage != null) stage.dispose();
        if (spriteBatch != null) spriteBatch.dispose();
        if (sr != null) sr.dispose();
        if (vicTxt != null) vicTxt.dispose();
        if (nextButtonTexture != null) nextButtonTexture.dispose();
        if (retryButtonTexture != null) retryButtonTexture.dispose();
        if (opponentTexture != null) opponentTexture.dispose();
        if (background != null) background.dispose();
        if (discardButton != null) discardButton.remove();
        if (startButton != null) startButton.remove();
    }

    public void nextRound() {
        resetView();
        show();
    }

    @Override
    public void onHandChanged(ArrayList<String> hand) {

        this.cardSprites.clear(); // Clear current sprites before update

        for (int i = 0; i < hand.size(); i++) {
            // Iterating over hand to get card png
            Sprite cardSprite = new Sprite(new Texture("assets/images/" + hand.get(i)));
            cardSprite.setSize(75,125);
            // Sets the card's position: x is centered based on the number of cards,
            // y is adjusted to create a slight curved spread before rotation.
            float y = 80 - 7.5f * (float)Math.pow(Math.abs(i - hand.size()/2), 1.20f);
            
            // startX is position of card at index 0, put cards in center horizontally
            float startX = viewport.getWorldWidth()/2 - cardSprite.getWidth()/2 + 40 - 60 * (hand.size()/2 - i);

            cardSprite.setPosition( startX,y );
            cardSprite.setOriginCenter();
            cardSprite.setRotation(5 * (hand.size()/2 - i)); // Rotating cars

            cardSprites.add(cardSprite);
            hoveredCards.add(false); //Resetting hovered if marked.

        }

    }

    @Override
    public void onSelectedChanged(ArrayList<String> selected) {
        ArrayList<String> temp = selected;


        selectedCardSprites.clear();
        for (int i = 0; i < temp.size(); i++) {
            Sprite cardSprite = new Sprite(new Texture("assets/images/" + temp.get(i)));
            cardSprite.setSize(75, 125);

            cardSprite.setPosition(80 + i * 60, 200);
            cardSprite.setOriginCenter();
            selectedCardSprites.add(cardSprite);
        }
    }

    @Override
    public void onBestComboChanged(String bestCombo) {
        this.currentBestCombo = bestCombo;

    }

    @Override
    public void onHealthChanged(float userHealth, float opponentHealth) {
        this.opponentHealthPercentage = opponentHealth;
        this.userHealthPercentage = userHealth;

    }

    @Override
    public void onGameEnded(String resultMessage, int totalDamageToOpponent, int totalDamageToUser) {        
        if(Objects.equals(resultMessage, "Victory")){
            this.isVictory = true;            
        }
        else {
            this.isVictory = false;
        }
        this.gameEnded = true;
        this.totalDamageToOpponent = totalDamageToOpponent;
        this.totalDamageToUser = totalDamageToUser;
        endGame();
    }

    @Override
    public void onPlayerTurn(boolean playerTurn) {
        this.playerTurn = playerTurn;
        if (!playerTurn) {
            oppAnimation();
        }

    }

    @Override
    public void onRoundInit() {
        Round r = gameManager.currentRound;
        this.roundController.setRound(r);
    }

    @Override
    public void render ( float v){        
        gameManager.gameLoop();
        input();
        draw();
    }

    @Override
    public void resize (int width, int height){
        if (width <= 0 || height <= 0) return;
        viewport.update(width, height, true); // true centers the camera

        // If the window is minimized on a desktop (LWJGL3) platform, width and height are 0, which causes problems.
        // In that case, we don't resize anything, and wait for the window to be a normal size before updating.

        // Resize your application here. The parameters represent the new window size.
    }


    @Override
    public void pause () {

    }

    @Override
    public void resume () {

    }

    @Override
    public void hide () {

    }

    @Override
    public void dispose () {

    }
}
