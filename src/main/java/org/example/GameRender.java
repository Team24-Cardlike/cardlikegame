package org.example;

import org.example.Controller.Controller;
import org.example.Model.Game;
import org.example.View.View;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;

public class GameRender extends ApplicationAdapter {

    private Game game;
    private View view;
    private Controller controller;

    GameRender(Game game, View view, Controller controller) {
        this.game = game;
        this.view = view;
        this.controller = controller;
    }

    @Override
    public void render() {
        float delta = Gdx.graphics.getDeltaTime();
        view.updateOpponentAnimation(delta);  // <<< Lägg till detta

        game.gameLoop1();
        view.input();
        view.draw();         // graphics

        // view.playSelectedCards(); // Move cards up        
    }

    @Override
    public void create() {
        view.create();
    }

    @Override
    public void resize(int width, int height) {
        view.resize(width, height);
    }
}
