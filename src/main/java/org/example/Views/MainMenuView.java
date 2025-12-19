package org.example.Views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import org.example.Controller.MenuController;

public class MainMenuView implements Screen {
    private Image playButton;
    private Image loadButton;
    private Image easyButton;
    private Image normalButton;
    private Image hardButton;

    Stage stage;
    Texture background;
    MenuController controller;
    BitmapFont font;

    public void setController(MenuController controller) {
        this.controller = controller;
    }

    @Override
    public void show() {
        stage = new Stage(new FitViewport(1280, 720));
        Gdx.input.setInputProcessor(stage);

        background = new Texture("assets/images/mainMenu.png");
        Image bg = new Image(background);
        bg.setFillParent(true);
        stage.addActor(bg);

        playButton = new Image(new Texture("assets/images/buttons/playButton.png"));
        playButton.setSize(200, 80);
        playButton.setPosition(stage.getWidth()/2 - playButton.getWidth()/2, stage.getHeight()/2 - 200);
        stage.addActor(playButton);

        loadButton = new Image(new Texture("assets/images/buttons/loadButton.png"));
        loadButton.setSize(200, 80);
        loadButton.setPosition(stage.getWidth()/2 - playButton.getWidth()/2, playButton.getY() - playButton.getHeight() - 40);
        stage.addActor(loadButton);

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/Roboto-Italic.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 24;
        font = generator.generateFont(parameter);
        generator.dispose();

        Group difPopup = new Group();
        difPopup.setSize(800, 300);
        difPopup.setPosition(stage.getWidth()/2 - difPopup.getWidth()/2, stage.getHeight()/2 - difPopup.getHeight()/2);
        Texture popupBG = new Texture("assets/images/itemBG.png");
        Image popupImage = new Image(popupBG);
        difPopup.addActor(popupImage);
        popupImage.setSize(difPopup.getWidth(), difPopup.getHeight());

        Label.LabelStyle ls = new Label.LabelStyle(font, Color.WHITE);
        Label title = new Label("Choose your difficulty:", ls);
        title.setPosition(difPopup.getWidth()/2 - title.getWidth()/2, difPopup.getHeight() - 80);
        difPopup.addActor(title);

        Label loadFail = new Label("No save available", ls);
        loadFail.setPosition(stage.getWidth()/2 - loadFail.getWidth()/2, loadButton.getY() - loadFail.getHeight()/2 - 20);

        Image backButton = new Image(new Texture("assets/images/redX.png"));
        backButton.setSize(30, 30);
        backButton.setPosition(difPopup.getWidth()-35, difPopup.getHeight()-35);
        difPopup.addActor(backButton);

        normalButton = new Image(new Texture("assets/images/buttons/normalButton.png"));
        normalButton.setSize(200, 80);
        normalButton.setPosition(difPopup.getWidth()/2 - normalButton.getWidth()/2, difPopup.getHeight()/2 - normalButton.getHeight()/2);
        difPopup.addActor(normalButton);

        easyButton = new Image(new Texture("assets/images/buttons/easyButton.png"));
        easyButton.setSize(200, 80);
        easyButton.setPosition(normalButton.getX() - normalButton.getWidth() - 40, difPopup.getHeight()/2 - easyButton.getHeight()/2);
        difPopup.addActor(easyButton);

        hardButton = new Image(new Texture("assets/images/buttons/hardButton.png"));
        hardButton.setSize(200, 80);
        hardButton.setPosition(normalButton.getX() + normalButton.getWidth() + 40,difPopup.getHeight()/2 - hardButton.getHeight()/2);
        difPopup.addActor(hardButton);

        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                difPopup.remove();
                stage.addActor(playButton);
                stage.addActor(loadButton);
            }
        });

        playButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                playButton.remove();
                loadButton.remove();
                loadFail.remove();
                stage.addActor(difPopup);
            }
        });

        loadButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(controller.loadGame())
                    controller.startGame();
                else {
                    stage.addActor(loadFail);
                    loadButton.setColor(0.5f, 0.5f, 0.5f, 0.6f);
                    loadButton.setTouchable(Touchable.disabled);
                }
            }
        });

        easyButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                difPopup.remove();
                controller.setDifficulty("Easy");
                controller.startGame();
            }
        });

        normalButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                difPopup.remove();
                controller.setDifficulty("Normal");
                controller.startGame();
            }
        });

        hardButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                difPopup.remove();
                controller.setDifficulty("Hard");
                controller.startGame();
            }
        });
    }

    @Override
    public void render(float v) {
        ScreenUtils.clear(Color.BLACK);
        stage.act(v);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        if (width <= 0 || height <= 0) return;
        stage.getViewport().update(width, height, true); // true centers the camera
    }

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {
        stage.dispose();
    }
}
