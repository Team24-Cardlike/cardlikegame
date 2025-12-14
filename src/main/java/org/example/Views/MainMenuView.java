package org.example.Views;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.TextArea;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import org.example.Controller.MenuController;
import org.example.Controller.RoundController;
import org.example.Model.GameManager;

public class MainMenuView implements Screen {
    public Image menuButton;
    Stage stage;
    Texture background;
    Viewport viewport;
    MenuController controller;
    GameManager gameManager;

    public void setGameManager (GameManager manager ) {
        this.gameManager = manager;
    }

    public void setController(MenuController controller) {
        this.controller = controller;
    }

    public Stage getStage() { return stage; }

    @Override
    public void show() {
        stage = new Stage(new FitViewport(1280, 720));
        Gdx.input.setInputProcessor(stage);

        background = new Texture("assets/images/mainmenu.png");
        Image bg = new Image(background);
        bg.setFillParent(true);
        stage.addActor(bg);

        menuButton = new Image(new Texture("assets/images/playbutton.png"));
        menuButton.setSize(200, 50);
        menuButton.setPosition(stage.getWidth()/2 -100, 150);
        stage.addActor(menuButton);

        menuButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.startGame();
            }
        });


    }
/*
    public void input() {
        if (Gdx.input.justTouched()) {
            // Få muskoordinater
            float touchX = Gdx.input.getX();
            float touchY = Gdx.input.getY();

            // Omvandla till Stage-koordinater
            Vector2 stageCoords = stage.screenToStageCoordinates(new Vector2(touchX, touchY));

            // Hämta knappens position och storlek
            float btnX = menuButton.getX();
            float btnY = menuButton.getY();
            float btnWidth = menuButton.getWidth();
            float btnHeight = menuButton.getHeight();

            // Kolla om musen är över knappen
            if (stageCoords.x >= btnX && stageCoords.x <= btnX + btnWidth &&
                    stageCoords.y >= btnY && stageCoords.y <= btnY + btnHeight) {
                controller.startGame(); // skicka till controller
            }
        }
    }*/
    @Override
    public void render(float v) {
        gameManager.gameLoop();
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
    }
}
