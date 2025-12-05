package org.example;

import org.example.Controller.Controller;
import org.example.Model.Game;
import org.example.View.ShopView;
import org.example.View.View;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;

public class GameRender extends ApplicationAdapter {

    private Game game;
    private View view;
    private Controller controller;
    private ShopView sv;
    GameRender(Game game, View view, ShopView sv, Controller controller) {
        this.game = game;
        this.view = view;
        this.sv = sv;
        this.controller = controller;
    }

    @Override
    public void render() {
        float delta = Gdx.graphics.getDeltaTime();
        //view.updateOpponentAnimation(delta);  // <<< Lägg till detta
        sv.render(delta);
        //sv.show();
        //game.gameLoop1();
        //view.input();
        //view.draw();         // graphics

        // view.playSelectedCards(); // Move cards up        
    }

    @Override
    public void create() {
        sv.show();
        //view.create();
    }

    @Override
    public void resize(int width, int height) {
        //view.resize(width, height);
        sv.resize(width,height);
    }
}
