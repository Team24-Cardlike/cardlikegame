package org.example.Views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import org.example.Controller.RoundController;
import org.example.Controller.ShopController;

public class HandbookView implements Screen {
    private Stage stage;
    private Image closeButton;
    private BitmapFont font;
    private SpriteBatch batch;
    private RoundController roundController;

   // public HandbookView(){}
    public void setController(RoundController ctrl){
        this.roundController = ctrl;
    }
    @Override
    public void show() {
        stage = new Stage(new FitViewport(1280,720));
        Gdx.input.setInputProcessor(stage);
        batch = new SpriteBatch();

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/Roboto-italic.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 24;
        font = generator.generateFont(parameter);
        generator.dispose();

        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = font;
        Label handbookText = new Label("The travelers handbook: \n" +
                "Each round you get to place up to 5 cards. These cards will damage your opponent according to the combination of cards placed\n" +
                "these combinations follow regular poker hands. You can also choose to discard up to 5 cards.\n" +
                "\n" +
                "These hands are: \n" +
                "Royal flush: A-K-Q-J-10 of the same suit\n" +
                "Straight flush: Five cards in sequence, all of the same suit\n" +
                "Four of kind: Four cards of the same rank, plus one other card\n" +
                "Full house: Three cards of one rank and two cards of another\n" +
                "Flush: Five cards of the same suit, not in sequence\n" +
                "Straight: Five cards in sequence, but not all of the same suit\n" +
                "Three of a Kind: Three cards of the same rank, plus to unrelated cards\n" +
                "Two pair: Two different pairs, plus one kicker card\n" +
                "Pair: Two cards of the same rank, plus three unrelated cards\n" +
                "High card: When no other combination is made, the highest card in the hand wins", labelStyle);
        handbookText.setWrap(true);

        ScrollPane.ScrollPaneStyle scrollStyle = new ScrollPane.ScrollPaneStyle();
        ScrollPane sacrollPane = new ScrollPane(handbookText, scrollStyle);
        sacrollPane.setFadeScrollBars(false);

        closeButton = new Image(new Texture("assets/images/xButton.png"));
        closeButton.setSize(100, 50);
        closeButton.setPosition(stage.getWidth()/2 -50, 50);
        stage.addActor(closeButton);

        closeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                roundController.closeHandBook();
            }
        });
        table.add(sacrollPane).expand().fill().pad(20).row();
        table.add(closeButton).pad(10);
    }

    @Override
    public void render(float v) {
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(v);
        stage.draw();
    }

    @Override
    public void resize(int i, int i1) {
        stage.getViewport().update(i, i1, true);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        stage.dispose();
        batch.dispose();
        font.dispose();
    }
}
