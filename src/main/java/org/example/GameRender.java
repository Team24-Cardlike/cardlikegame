package org.example;

import org.example.Controller.RoundController;
import org.example.Model.GameManager;
import org.example.Model.GameMap;
import org.example.Model.Round;
import org.example.View.RoundView;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;

public class GameRender extends ApplicationAdapter {

    private GameManager maneger;
    private RoundView roundView;
    private RoundController roundController;

    GameRender(RoundView roundView, RoundController roundController, GameManager maneger) {
        this.maneger = maneger;
        this.roundView = roundView;
        this.roundController = roundController;
    }

    @Override
    public void render() {
        float delta = Gdx.graphics.getDeltaTime();
        roundView.updateOpponentAnimation(delta);  // <<< Lägg till detta
        roundView.input();
        maneger.gameLoop();


        roundView.draw();         // graphics


        // roundView.playSelectedCards(); // Move cards up
    }

    @Override
    public void create() {
        roundView.create();
        maneger.initRound();

    }

    @Override
    public void resize(int width, int height) {                    
        
        roundView.resize(width, height);
    }
}
