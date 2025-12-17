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
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import org.example.Controller.RoundController;
import org.example.Model.*;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class RoundView extends ApplicationAdapter implements RoundObserver, Screen {
    public FitViewport viewport;
    private SpriteBatch spriteBatch;
    private ShapeRenderer sr;

    Texture background;

    public Image startButton;
    public Image handbookButton;
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

    @Override
    public void show() {
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/impact.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 35;
        parameter.borderColor = Color.BLACK;
        parameter.borderWidth = 4f;
        parameter.color = new Color(1f, 1f, 0f, 1f);
        parameter.shadowOffsetX = 4;
        parameter.shadowOffsetY = 4;
        parameter.shadowColor = new Color(0,0,0,0.75f);

        BitmapFont comboFont = generator.generateFont(parameter);
        generator.dispose();

        style = new Label.LabelStyle();
        style.font = comboFont;

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
        viewport = new FitViewport(1280,720, camera);

        stage = new Stage(viewport, spriteBatch);
        cardSprites = new ArrayList<>();

        shopButton = new Image(new Texture("assets/images/victoryPlaceholder.png"));
        shopButton.setSize(150, 100);
        shopButton.setPosition(
                20,
                viewport.getWorldHeight() - shopButton.getHeight() - 20
        );
        stage.addActor(shopButton);

        handbookButton = new Image(new Texture("assets/images/rules.png"));
        handbookButton.setSize(70, 120);
        handbookButton.setPosition(
                20,
                viewport.getWorldHeight() - shopButton.getHeight()
                        - handbookButton.getHeight() - 40
        );
        stage.addActor(handbookButton);

        discardButton = new Image(new Texture("assets/images/discard.png"));
        discardButton.setSize(140, 60);
        discardButton.setPosition(
                viewport.getWorldWidth() - discardButton.getWidth() - 20,
                20
        );
        stage.addActor(discardButton);

        startButton = new Image(new Texture("assets/images/endTurn.png"));
        startButton.setPosition(20, 20);
        startButton.setSize(140, 60);
        stage.addActor(startButton);
        Gdx.input.setInputProcessor(stage);


        opponentTexture = new Texture("assets/images/Heimdall.png");
        opponentSprite = new Sprite(opponentTexture);
        opponentSprite.setSize(350, 200);
        opponentSprite.setPosition(
                viewport.getWorldWidth() / 2f - opponentSprite.getWidth() / 2f,
                viewport.getWorldHeight() - opponentSprite.getHeight() - 50
        );
        background = new Texture("assets/images/board.png");


        startButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                onPlaySelectedCards();
            }
        });

        handbookButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                roundController.openHandbook();
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
        if(!selectedCardSprites.isEmpty())
            roundController.playCards();
        //TODO: Why is the gameEnd-check in onPlaySelectedCards()?
        if (gameEnded) {
            //Display match stats
            endGame();
            // Adding next button
            if(isVictory){

                System.out.println(isVictory);

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
        else if(playerTurn){
            opponentAnimation();
        }

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
        float x = 150;
        float y = 650;
        float width = 250;
        float height = 30;

        // Clamp mellan 0 och 1
        float clampedUserHealth = Math.clamp(userHealthPercentage, 0f, 1f);

        float greenWidthUser = width * clampedUserHealth;
        float redWidthUser = width - greenWidthUser;

        sr.begin(ShapeRenderer.ShapeType.Filled);

        // RED background
        sr.setColor(Color.RED);
        sr.rect(x, y, width, height); // rita hela baren röd först

        // GREEN foreground (skalar med procent)
        if (greenWidthUser > 0) {
            sr.setColor(Color.GREEN);
            sr.rect(x, y, greenWidthUser, height);
        }

        // ENEMY HEALTH BAR (top-right)
        float ex = 900;
        float ey = 650;

        float clampedOpponentHealth = Math.clamp(opponentHealthPercentage, 0f, 1f);

        float greenWidthOpp = width * clampedOpponentHealth;

        sr.setColor(Color.RED);
        sr.rect(ex, ey, width, height); // röd bakgrund

        if (greenWidthOpp > 0) {
            sr.setColor(Color.GREEN);
            sr.rect(ex, ey, greenWidthOpp, height);
        }

        sr.end();
    }
    
    // Creates
    public void createSpriteList( ){

    }


    private Polygon generateHitbox(int index,ArrayList<Sprite> cards) {
        // AI-generated solution for getting the correct hitbox now that the
        // cards are rotated
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

    //Check if user hovers over card
    public void getHoverdCards() {
        for (int a = 0; a < cardSprites.size(); a++){
            Polygon poly = generateHitbox(a, cardSprites);
            // Check if card is hovered over
            if (poly.contains(coords.x, coords.y)) {
                // if (cardSprites.get(a).getBoundingRectangle().contains(coords.x,coords.y)) {
                // hoveredCards.set(a,cardSprites.get(a).getBoundingRectangle().contains(cords.x,cords.y));
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

            //float cx = 150 + i * 90;
            //float cy = 200;

            //selectedCard.setPosition(cx, cy);
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
        }
        else{
            panel = new Image(new TextureRegionDrawable(lossTxt));
        }
        panel.setSize(600, 400);
        panel.setPosition(
                viewport.getWorldWidth() / 2f - panel.getWidth() / 2f,
                viewport.getWorldHeight() / 2f - panel.getHeight() / 2f
        );
        stage.addActor(panel);

        Label label1 = new Label("Post-round statistics", style);
        label1.setPosition(
                viewport.getWorldWidth() / 2f - label1.getPrefWidth() / 2f,
                viewport.getWorldHeight() / 2f + 80
        );
        Label label2 = new Label("You did: " + totalDamageToOpponent+" damage to your enemy!", style);
        label2.setPosition(
                viewport.getWorldWidth() / 2f - label1.getPrefWidth() / 2f,
                viewport.getWorldHeight() / 2f + 50
        );
        Label label3 = new Label("You took: "+ totalDamageToUser+" damage", style);
        label2.setPosition(
                viewport.getWorldWidth() / 2f - label1.getPrefWidth() / 2f,
                viewport.getWorldHeight() / 2f + 20
        );

        stage.addActor(label1);
        stage.addActor(label2);
        stage.addActor(label3);

        if(isVictory)
        {
            nextButton = new Image(nextButtonTexture);
            nextButton.setPosition(600,200);
            nextButton.setSize(80, 50);
            stage.addActor(nextButton);
            nextButton.addListener(new ClickListener(){
                @Override
                public void clicked(InputEvent event, float x, float y){
                    System.out.println("hej hej ");
                    roundController.nextRound();
                    nextRound();

                }
            });
        }
        else{
            retryButton = new Image(nextButtonTexture);
            retryButton.setPosition(600,200);
            retryButton.setSize(80, 50);
            stage.addActor(retryButton);
        }
    }

    public void showCombo(String comboName){
        if (currentComboLabel != null) {
            currentComboLabel.remove();
        }

        currentComboLabel = new Label(comboName, style);

        // Position: top right
        float x = viewport.getWorldWidth() - currentComboLabel.getPrefWidth() - 90;
        float y = viewport.getWorldHeight() - currentComboLabel.getPrefHeight() - 200;

        currentComboLabel.setPosition(x, y);

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
            //Iterating ocher hand to get card png
            Sprite cardSprite = new Sprite(new Texture("assets/images/" + hand.get(i)));
            cardSprite.setSize(100,175);
            // Sets the card's position: x is centered based on the number of cards,
            // y is adjusted to create a slight curved spread before rotation.
            //float y = 80 - 7.5f * (float)Math.pow(Math.abs(i - hand.size()/2), 1.20f);
            // startX is position of card at index 0
            //float startX = 60* i + 80 ;
            float cardSpacing = 150;
            float totalWidth = cardSprites.size() * cardSpacing;
            float startX = viewport.getWorldWidth() / 4f - totalWidth / 2f;

            float x = startX + i * cardSpacing;
            float y = 35;

            cardSprite.setPosition(x, y);
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
            cardSprite.setSize(100, 175);
            float spacing = 250;
            float total = selectedCardSprites.size() * spacing;
            float sx = viewport.getWorldWidth() / 4f - total / 2f;

            cardSprite.setPosition(sx + i * spacing, 250);
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

        this.isVictory = false;
        this.gameEnded = false;
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
