package org.example.View;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
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

import org.example.Controller.Controller;
import org.example.Model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class View extends ApplicationAdapter implements GameObserver{
    public FitViewport viewport;    
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
    private Stage stage;
    public ArrayList<Boolean> hoveredCards;
    public ArrayList<Boolean> boolSelectedCards;
    private ArrayList<Sprite> centerSelectedCard;
    private Label currentComboLabel;
    public Vector3 coords;

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

    int a = 0;
    public ArrayList<Integer> selectedIndices;
    public ArrayList<Integer> removedIndices;
    private Game game;
    private Controller controller;

    public void setGame(Game game) {
        this.game = game;
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }
    
    public void create() {        

        BitmapFont font = new BitmapFont(); // standard font
        style = new Label.LabelStyle();
        style.font = font;

        vicTxt = new Texture("assets/images/victoryPlaceholder.png");
        nextButtonTexture = new Texture("assets/images/nextPlaceholder.png");

        centerSelectedCard = new ArrayList<>();
        sr = new ShapeRenderer();
        spriteBatch = new SpriteBatch();
        
        OrthographicCamera camera = new OrthographicCamera();
        camera.setToOrtho(false, 8, 5); 
        camera.position.set(4, 2.5f, 0); // center camera
        camera.update();
        viewport = new FitViewport(800, 600, camera);
        
        stage = new Stage(viewport, spriteBatch);
        cardSprites = new ArrayList<>();        

        hoveredCards = new ArrayList<>();
        boolSelectedCards = new ArrayList<>();

        discardButton = new Image(new Texture("assets/images/discard.png"));
        discardButton.setPosition(600,200);
        discardButton.setSize(80, 100);
        stage.addActor(discardButton);

        startButton = new Image(new Texture("assets/images/endTurn.png"));
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
        opponentSprite.setSize(250f, 150f);
         opponentSprite.setPosition(
                viewport.getWorldWidth() / 2f - opponentSprite.getWidth()/2f,
                viewport.getWorldHeight() - 250
        );
        background = new Texture("assets/images/bräde.png");


        startButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // game.playSelectedCards()
                playSelectedCards();

                onPlaySelectedCards(selectedIndices);
            }
        });

        discardButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                throwCards();
                controller.discardCards(removedIndices);
            }
        });        
    }



    public void bestCombo(ArrayList<Boolean> boolList){
        ArrayList<Integer> intList = new ArrayList<>();
        int i = 0;
        for(Boolean bool : boolList){
            if(bool)intList.add(i);
            i++;
        }

        String combo = game.bestCombo(game.getSelectedCardsAsCards(intList));
        showCombo(combo);
    }



    public void input() {
        coords = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);        
        viewport.unproject(coords);        

        if (Gdx.input.justTouched()) {
            
            for (int a = cardSprites.size() - 1; a >= 0; a--){   
                                
                Polygon poly = generateHitbox(a);            

            //Send input to controller                
                if (poly.contains(coords.x, coords.y) && (game.getNumberOfSelected(boolSelectedCards) < 5)) {                                        
                    controller.selectCard(a, true);

                    boolSelectedCards.set(a, !boolSelectedCards.get(a));                                                            
                    
                    if(game.getNumberOfSelected(boolSelectedCards) > 0){
                        bestCombo(boolSelectedCards);
                    }                    
                    break;
                                        
                }                            
            }
        }
    }



    public void onPlaySelectedCards(ArrayList<Integer> cards){
        ArrayList<Card> temp = game.getSelectedCardsAsCards(cards);        
        controller.playCards(temp);

        if(game.gameState){
            updateView(temp);
            opponentAnimation();
        }
        else{            
            endGame(game.totalDamageToPlayer, game.totalDamageToOpponent);
            nextButton.addListener(new ClickListener(){
                @Override
                public void clicked(InputEvent event, float x, float y){
                    //View.nextView();? Eller ska nytt objekt
                    controller.nextRound();
                    nextRound();
                }
            });
        }
    }


    public void updateView(ArrayList<Card> cards){
        createSpriteList();
    }
    public void opponentAnimation(){
        oppAnimation();
    }

    public void drawHealthBars(){        
        sr.setProjectionMatrix(viewport.getCamera().combined);

        // USER HEALTH BAR (bottom-left)
        float x = 50;
        float y = 550;
        float width = 250;
        float height = 30;

        //float healthPercent = (float) game.user.health / game.user.maxHealth; TODO REMOVE

        sr.begin(ShapeRenderer.ShapeType.Filled);

        float greenWidthUser = width * userHealthPercentage;
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

       //  float enemyPercent = (float) game.opponent.health / game.opponent.maxHealth; TODO REMOVE

        float greenWidthOpp = width * opponentHealthPercentage;
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

        for (int i = 0; i < handImages.size(); i++) {
            Sprite cardSprite = new Sprite(new Texture("assets/images/" + handImages.get(i)));
            cardSprite.setSize(75,125);        
            cardSprite.setPosition(80 + i*60, 80 - 7.5f * (float)Math.pow(Math.abs(i - handImages.size()/2), 1.20f));            


            cardSprite.setOriginCenter();   
            // TODO: selected cards shouldn't affect hitbox
            cardSprite.setRotation(5 * (handImages.size()/2 - i)); 

            cardSprites.add(cardSprite);

            boolSelectedCards.add(false);
            hoveredCards.add(false);
        }
    }

    //Animation moving card forward
    public void playSelectedCards(){
        float lift = viewport.getWorldHeight() * 10; // t.ex. 10% uppåt             
        selectedIndices = new ArrayList<>();
        for (int i = 0; i < cardSprites.size(); i++) {
            if (boolSelectedCards.get(i)) {
                selectedIndices.add(i);

                Sprite card = cardSprites.get(i);
                card.setY(card.getY() + lift);
                boolSelectedCards.set(i, false);   
                                            
            }
        }
        // System.out.println(selectedIndices + "" + this.selectedIndices);
        // this.selectedIndices = selectedIndices;
    }


    private Polygon generateHitbox(int index) {

        // AI-generated solution for getting the correct hitbox now that the
        // cards are rotated

        Sprite sprite = cardSprites.get(index);            
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

    //Check if user hovers over card
    public void getHoverdCards() {        
        for (int a = 0; a < cardSprites.size(); a++){            
                
            Polygon poly = generateHitbox(a);
            
            // Check if card is hovered over
            if (poly.contains(coords.x, coords.y)) {
            // if (cardSprites.get(a).getBoundingRectangle().contains(coords.x,coords.y)) {            
                // hoveredCards.set(a,cardSprites.get(a).getBoundingRectangle().contains(cords.x,cords.y));
                hoveredCards.set(a, true);
                for (int i = 0; i < cardSprites.size(); i ++) {
                    if (i != a) hoveredCards.set(i, false);
                }
                
            }
            else {
                hoveredCards.set(a, false);
            }            
        }
    }

    public void draw() {
        centerSelectedCard.clear();
        getHoverdCards();        

        for (int i = 0; i < cardSprites.size(); i++) {
            if (boolSelectedCards.get(i)) {
                centerSelectedCard.add(cardSprites.get(i));  
                
                cardSprites.get(i).setRotation(0);
                // cardSprites.get(i).setRotation((float) (10 * (0.5f - Math.random())));
                //  + (float) (10 * (0.5f - Math.random())));
            }            
        }

        ScreenUtils.clear(Color.BLACK);
        viewport.apply();
        spriteBatch.setProjectionMatrix(viewport.getCamera().combined);
        spriteBatch.begin();


        // store the worldWidth and worldHeight as local variables for brevity
        float worldWidth = viewport.getWorldWidth();
        float worldHeight = viewport.getWorldHeight();
        spriteBatch.draw(background, 0, 0,  viewport.getWorldWidth(), viewport.getWorldHeight()); // draw the background
        //ArrayList<Sprite> cards = user.getHand();

        for (int i = 0; i < centerSelectedCard.size(); i++) {
            Sprite selectedCard = centerSelectedCard.get(i);

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
        currentComboLabel.setPosition(200,150);
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

                 // game.getTurnManager().swapTurn(); // animation klar → byt tur TODO Remove, should be updated in model
                return;
            }

            // t^2 ger långsam start, snabb slut
            float eased = (float)Math.pow(t, 2);
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
        if (opponentTexture != null) opponentTexture.dispose();
        if (background != null) background.dispose();
        if (discardButton != null) discardButton.remove();
        if (startButton != null) startButton.remove();
    }

    public void nextRound(){
        resetView();
        create();
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
    // Getting notified to update healthPercentage
    public void onHealthChanged(float userHealth, float opponentHealth) {
        userHealthPercentage= userHealth;
        opponentHealthPercentage = opponentHealth;
    }

    @Override
    public void onCardSelected(int index, boolean selected) {
        // boolSelectedCards.set(index,selected);


    }

    @Override
    public void onGameEnded(String resultMessage) {

    }

    @Override
    public void onPlayerTurn(boolean playerTurn) {
        this.playerTurn = playerTurn;
        if (!playerTurn) {
            oppAnimation();}
    }

    @Override
    public void resize(int width, int height) {
        if(width <= 0 || height <= 0) return;
        viewport.update(width, height, true); // true centers the camera
    }

}
