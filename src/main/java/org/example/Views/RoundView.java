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
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
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
    private Image backgroundImage;
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

    private String opponentName;
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

    public final Image[] upgradeSlots = new Image[4];
    private int lastIndex;
    private int currentUpgradeIndex = 0;
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

        vicTxt = new Texture("assets/images/victoryBanner.png");
        lossTxt = new Texture("assets/images/defeatBanner.png");
        nextButtonTexture = new Texture("assets/images/nextButton.png");
        retryButtonTexture = new Texture("assets/images/nextButton.png");
        sr = new ShapeRenderer();
        //spriteBatch = new SpriteBatch();

        OrthographicCamera camera = new OrthographicCamera();
        camera.setToOrtho(false, 8, 5);
        camera.position.set(4, 2.5f, 0); // center camera
        camera.update();
        viewport = new FitViewport(1280,720, camera);

        stage = new Stage(viewport);

        backgroundImage =
                new Image(new Texture("assets/images/background.png"));

        backgroundImage.setSize(
                viewport.getWorldWidth(),
                viewport.getWorldHeight()
        );

        backgroundImage.setPosition(0, 0);
        backgroundImage.setZIndex(0); // längst bak

        stage.addActor(backgroundImage);
        cardImages = new ArrayList<>();

        /*
        shopButton = new Image(new Texture("assets/images/victoryPlaceholder.png"));
        shopButton.setSize(150, 100);
        shopButton.setPosition(
                20,
                viewport.getWorldHeight() - shopButton.getHeight() - 20
        );
        stage.addActor(shopButton);
*/
        handbookButton = new Image(new Texture("assets/images/rules.png"));
        handbookButton.setSize(80, 120);
        handbookButton.setPosition(10, viewport.getWorldHeight() - handbookButton.getHeight() -30);
        stage.addActor(handbookButton);

        discardButton = new Image(new Texture("assets/images/discardButton.png"));
        discardButton.setSize(140, 60);
        discardButton.setPosition(viewport.getWorldWidth() - discardButton.getWidth() - 10, viewport.getWorldHeight()/2 - discardButton.getHeight() - 10);
        stage.addActor(discardButton);

        startButton = new Image(new Texture("assets/images/playCards.png"));
        startButton.setSize(140, 60);
        startButton.setPosition(viewport.getWorldWidth()-startButton.getWidth() - 10, viewport.getWorldHeight()/2+10);

        stage.addActor(startButton);
        Gdx.input.setInputProcessor(stage);

        opponentTexture = new Texture("assets/images/opponents/Heimdall.png");
        opponentImage = new Image(opponentTexture);//new Sprite(opponentTexture);
        opponentImage.setSize(350, 200);
        opponentImage.setPosition(
                viewport.getWorldWidth() / 2f - opponentImage.getWidth() / 2f,
                viewport.getWorldHeight() - opponentImage.getHeight() -10
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

        discardButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                //throwCards();
                roundController.discardCards(removedIndices);
            }
        });
        /*
        shopButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                //throwCards();
                roundController.switchView("shop");
            }
        });*/

        Label playerName = new Label("Player", style);
        playerName.setPosition(180, 615);
        stage.addActor(playerName);
        createUpgradeTable();
        input();
    }


    @Override
    public void onNewOpponent(String name, int damage, String image){
        Label opponentName = new Label(name, style);
        opponentName.setPosition(915, 615);

        stage.addActor(opponentName);
    }

    public void input() {
        coords = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
        viewport.unproject(coords);
    }

    public void onPlaySelectedCards(){
        if(!selectedCardImages.isEmpty())
            roundController.playCards();
        //TODO: Why is the gameEnd-check in onPlaySelectedCards()?
        // if (gameEnded) {
        //     //Display match stats
        //     endGame();
        //     // Adding next button
        //     if(isVictory){

        //         System.out.println(isVictory);

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
        else if(playerTurn){
            opponentAnimation();
        }
    }

    private void createUpgradeTable(){
        Table table = new Table();
        //table.setFillParent(true);
        table.setPosition(50, 350);

        Label title = new Label("Upgrades", style);
        title.setFontScale(0.5f);

        table.add(title).padBottom(10);
        table.row();

        for(int i = 0; i < 4; i++){
            Image slot = createEmptySlot();
            upgradeSlots[i] = slot;

            table.add(slot).size(50,50).pad(5);
            table.row();
        }
        stage.addActor(table);
    }

    private Image createEmptySlot() {
        Texture emptyTexture = new Texture(Gdx.files.internal("assets/images/upgradePics/lone_wolf.png"));
        return new Image(emptyTexture);
    }

    private void addUpgradeImage(String image){
        if(currentUpgradeIndex >= 4){
            return;
        }
        upgradeSlots[currentUpgradeIndex].setDrawable(new TextureRegionDrawable(new TextureRegion(new Texture(image))));
        currentUpgradeIndex++;
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

        // User health bar

        float width = 250;
        float height = 40;

        float x = viewport.getWorldWidth()/2 - opponentImage.getWidth()/2 - width -30;
        float y = 665;

        float clampedUserHealth = Math.clamp(userHealthPercentage, 0f, 1f);

        float greenWidthUser = width * clampedUserHealth;
        //float redWidthUser = width - greenWidthUser;

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
        float ex = viewport.getWorldWidth()/2 + opponentImage.getWidth()/2 +30;;
        float ey = 665;

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

    public void createSpriteList( ){}


    /**
     * AI-generated solution for getting the correct hitbox now that the
     * cards are rotated
     * @param index index of the card
     * @param cards all cards in hand
     * @return
     */
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


    /**
     * Check if user hovers over card
     */
    /*public void getHoverdCards() {
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
    }*/

    public void draw() {
        ScreenUtils.clear(Color.BLACK);
        viewport.apply();

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
            nextButton.addListener(new ClickListener(){
                @Override
                public void clicked(InputEvent event, float x, float y){
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

        float x = viewport.getWorldWidth()-1000;
        float y = viewport.getWorldHeight() - currentComboLabel.getPrefHeight() - 240;

        currentComboLabel.setPosition(x, y);

        stage.addActor(currentComboLabel);
    }


    public void resetView() {
        if (stage != null) stage.dispose();
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
            hoveredCards.add(false);
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
        else {
            this.isVictory = false;
        }
        this.gameEnded = true;
        this.totalDamageToOpponent = totalDamageToOpponent;
        this.totalDamageToUser = totalDamageToUser;
        endGame();
    }

    @Override
    public void onPlayerTurn(boolean playerTurn, ArrayList<String> upgrades) {
        for(int i = 0; i < upgrades.size(); i++){
            addUpgradeImage(upgrades.get(i));
        }
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
        viewport.update(width, height, true);
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
