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
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import org.example.Controller.RoundController;
import org.example.Model.*;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import org.example.Views.Animations.ImageAnimations;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class RoundView extends ApplicationAdapter implements RoundObserver, Screen {
    public FitViewport viewport;
    private ShapeRenderer sr;


    public Image startButton;
    public Image handbookButton;
    public Image discardButton;
    public Image nextButton;
    public Image retryButton;
    public Image shopButton;

    Texture background;
    public Texture shopButtonTexture;
    public Texture retryButtonTexture;
    public Texture nextButtonTexture;
    public Texture vicTxt;
    public Texture lossTxt;

    //ArrayList containing card-sprites for selected cards and hand
    public ArrayList<Image> cardImages = new ArrayList<>();
    public ArrayList<Image> selectedCardImages = new ArrayList<>();

    private Texture opponentTexture;
    private Image opponentImage;

    private Stage stage;
    public ArrayList<Boolean> hoveredCards = new ArrayList<>();;
    private Label currentComboLabel;
    public Vector3 coords;
    private Image panel;

    Label.LabelStyle style;

    private Label opponentName;
    private Label playerName;
    private float opponentStartY = 300;      // original Y
    private float opponentDropY = 180;
    private boolean animatingCard = false;

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


    private int lastIndex;

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
        //spriteBatch = new SpriteBatch();

        OrthographicCamera camera = new OrthographicCamera();
        camera.setToOrtho(false, 8, 5);
        camera.position.set(4, 2.5f, 0); // center camera
        camera.update();
        viewport = new FitViewport(1280,720, camera);

        stage = new Stage(viewport);

        Image backgroundImage =
                new Image(new Texture("assets/images/bräde.png"));

        backgroundImage.setSize(
                viewport.getWorldWidth(),
                viewport.getWorldHeight()
        );

        backgroundImage.setPosition(0, 0);
        backgroundImage.setZIndex(0); // längst bak

        stage.addActor(backgroundImage);
        cardImages = new ArrayList<>();

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

        opponentTexture = new Texture("assets/images/enemyCorrect.png");
        opponentImage = new Image(opponentTexture);//new Sprite(opponentTexture);
        opponentImage.setSize(350, 200);
        opponentImage.setPosition(
                viewport.getWorldWidth() / 2f - opponentImage.getWidth() / 2f,
                viewport.getWorldHeight() - opponentImage.getHeight() - 50
        );
        stage.addActor(opponentImage);


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

        Label opponentName = new Label("Opponent", style);
        opponentName.setPosition(850, 600);
        stage.addActor(opponentName);
        Label playerName = new Label("Player", style);
        playerName.setPosition(150, 600);
        stage.addActor(playerName);
        input();
    }

    public void input() {
        coords = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
        viewport.unproject(coords);
    }

    public void onPlaySelectedCards(){
        roundController.playCards();
        //TODO: Why is the gameEnd-check in onPlaySelectedCards()?
        if (gameEnded) {
            //Display match stats
            System.out.println("fsd");
            endGame();
            // Adding next button
            if(isVictory){
                nextButton.addListener(new ClickListener(){
                    @Override
                    public void clicked(InputEvent event, float x, float y){
                        roundController.nextRound();
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
        float x = 50;
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
        float ex = 1000;
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
    public void createSpriteList( ){}

    public void draw() {
        ScreenUtils.clear(Color.BLACK);
        viewport.apply();

        // store the worldWidth and worldHeight as local variables for brevity
        float worldWidth = viewport.getWorldWidth();
        float worldHeight = viewport.getWorldHeight();

        showCombo(currentBestCombo);

        stage.act(Gdx.graphics.getDeltaTime());


        if (playerTurn || gameEnded) {
            stage.draw();
        }
        drawHealthBars();
    }


    public void oppAnimation() {
        float initX = opponentImage.getX();
        float initY = opponentImage.getY();
        ImageAnimations.opponentAnimation(opponentImage, initX, initY,viewport.getWorldHeight()/2);
        stage.getRoot().addAction(ImageAnimations.screenShake(10f, 0.3f));
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


    public void resetView() {
        if (stage != null) stage.dispose();
        //if (spriteBatch != null) spriteBatch.dispose();
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
        for (Image card : cardImages) {
            card.remove();
        }
        this.cardImages.clear();

        for (int i = 0; i < hand.size(); i++) {
            final int index = i;
            Image cardSprite = new Image(new Texture("assets/images/" + hand.get(i)));
            cardSprite.setName(hand.get(i));
            cardSprite.setSize(100,175);
            cardSprite.setOrigin(Align.center);
            float y = 35;

            cardSprite.setPosition(handX(i, cardImages.size()), y);
            cardSprite.setRotation(5 * (hand.size()/2 - i)); // Rotating cars
            cardSprite.setTouchable(Touchable.enabled);

            cardSprite.addListener(new InputListener() {

                @Override
                public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                    cardSprite.setColor(Color.LIGHT_GRAY);
                }

                @Override
                public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                    cardSprite.setColor(Color.WHITE);
                }
            });
            cardSprite.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {

                    // MAX 5 cards
                    if (selectedCardImages.size() >= 5) {
                        return;
                    }
                    lastIndex = index;
                    roundController.selectCard(index);
                }
            });
            cardImages.add(cardSprite);
            stage.addActor(cardSprite);
            hoveredCards.add(false); //Resetting hovered if marked.
        }
    }

    private float handX(int index, int totalCards){
        float spacing = 150;
        float totalWidth = totalCards * spacing;
        float startX = viewport.getWorldWidth() / 4f - totalWidth / 2f;
        return startX + index * spacing;
    }

    private float selectedX(int selectedCardImagesSize, int index){
        float spacing = 250;
        float total = selectedCardImagesSize*spacing;
        float sx = viewport.getWorldWidth()/4f - total/2f;
        return sx +index*spacing;
    }

    @Override
    public void onUnselectedCard(ArrayList<String> selected){
        for (Image img : selectedCardImages) {
            img.remove();
        }
        selectedCardImages.clear();
        for (int i = 0; i < selected.size(); i++) {
            generateSelectedCardImages(selected, i);
        }
    }

    private void generateSelectedCardImages(ArrayList<String> selected, int i){
        float spacing = 250;
        Image cardSprite = new Image(new Texture("assets/images/" + selected.get(i)));
        cardSprite.setName(selected.get(i));
        cardSprite.setSize(100, 175);
        cardSprite.setOrigin(Align.center);
        float total = selectedCardImages.size() * spacing;
        float sx = viewport.getWorldWidth() / 4f - total / 2f;
        final int index = i;
        cardSprite.setPosition(sx + i * spacing, 250);
        cardSprite.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ImageAnimations.moveToHand(cardSprite, handX(index, cardImages.size()-1), 35 );
                roundController.unselectCard(index);
                cardSprite.setColor(Color.WHITE);
            }
        });

        cardSprite.addListener(new InputListener() {
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                cardSprite.setColor(Color.LIGHT_GRAY);
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                cardSprite.setColor(Color.GOLD);
            }
        });
        cardSprite.setColor(Color.GOLD);
        selectedCardImages.add(cardSprite);
        stage.addActor(cardSprite);
    }

    @Override
    public void onSelectedCard(ArrayList<String> selected) {
        float spacing = 250;
            for (Image img : selectedCardImages) {
                img.remove();
            }
            selectedCardImages.clear();
            for (int i = 0; i < selected.size(); i++) {
                final int index = i;
                if(i == selected.size()-1){
                    float total = selectedCardImages.size() * spacing;
                    float sx = viewport.getWorldWidth() / 4f - total / 2f;
                    Image cardSprite = new Image(new Texture("assets/images/" + selected.get(i)));
                    cardSprite.setSize(100, 175);
                    float x = handX(lastIndex, lastIndex);
                    cardSprite.setPosition(x,35);
                    stage.addActor(cardSprite);

                    ImageAnimations.moveToSelected(cardSprite, sx + i*spacing, 250);

                    cardSprite.addListener(new ClickListener() {
                        @Override
                        public void clicked(InputEvent event, float x, float y) {
                            ImageAnimations.moveToHand(cardSprite, handX(index, cardImages.size()-1), 35 );
                            roundController.unselectCard(index);
                            cardSprite.setColor(Color.WHITE);
                        }
                    });

                    cardSprite.addListener(new InputListener() {
                        @Override
                        public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                            cardSprite.setColor(Color.LIGHT_GRAY);
                        }

                        @Override
                        public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                            cardSprite.setColor(Color.GOLD);
                        }
                    });
                    cardSprite.setColor(Color.GOLD);
                    selectedCardImages.add(cardSprite);
                }
                else{
                generateSelectedCardImages(selected, index);
                }
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
        System.out.println(playerTurn);
    }

    @Override
    public void onRoundInit() {

    }

    @Override
    public void onOpponentAttack(int damage) {
            Label oppAttack = new Label("Name attacked you!\n" +
                    "You took "+damage+ " damage!\n" +
                    "Players turn.", style);
            oppAttack.setPosition(800,275);
            stage.addActor(oppAttack);
    }

    @Override
    public void render ( float v){
        gameManager.gameLoop();
        input();
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
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
