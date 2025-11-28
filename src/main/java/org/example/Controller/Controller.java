package org.example.Controller;

import java.util.ArrayList;

import org.example.Model.Card;
import org.example.Model.Game;
import org.example.View.View;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class Controller{
    private View view;
    public Game game;

    public Controller(View view, Game game){
        this.view = view;
        this.game = game;

    }

    public void create() {
        view.startButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
               // game.playSelectedCards()
               // view.playSelectedCards();

                onPlaySelectedCards();
            }
        });
    }

    public void input() {
        //float speed = 4f;
        //float delta = Gdx.graphics.getDeltaTime(); // retrieve the current delta

        Vector3 cords = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
        view.viewport.getCamera().unproject(cords);

       /* //Check if user hovers over card
        for (int a = 0; a < view.cardSprites.size(); a++){
            view.hoveredCards.set(a,view.cardSprites.get(a).getBoundingRectangle().contains(cords.x,cords.y));
        } */// TODO Remove from here, moved to view

        if (Gdx.input.justTouched()) {
            // for (int i = 0; i < view.cardSprites.size(); i ++) {
            //     if (view.cardSprites.get(i).getBoundingRectangle().contains(cords.x,cords.y)) {
            //         game.setSelectedCards(i, true);
            //     }
            // }
            for (int a = view.cardSprites.size() - 1; a >= 0; a--){            
                if (view.cardSprites.get(a).getBoundingRectangle().contains(cords.x,cords.y)) {
                    // hoveredCards.set(a,cardSprites.get(a).getBoundingRectangle().contains(cords.x,cords.y));      
                    game.setSelectedCards(a, true);
                    break;
                    // for (int i = 0; i < view.cardSprites.size(); i ++) {
                    //     // if (i != a) game.setSelectedCards(i, false);
                    // }
                    
                }
                // else {
                //     game.setSelectedCards(a, false);
                // }            
        }
    }}

    public void onPlaySelectedCards(){
        game.playCards();
    }



}
