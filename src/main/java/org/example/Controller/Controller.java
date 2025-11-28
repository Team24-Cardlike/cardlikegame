package org.example.Controller;

import java.util.ArrayList;

import org.example.Model.Card;
import org.example.Model.Game;
import org.example.View.View;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class Controller{
    private View view;
    public Game game;
    Vector3 coords;

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

        coords = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
        view.coords = coords;
        view.viewport.unproject(coords);
        // view.viewport.getCamera().unproject(coords);
        

       /* //Check if user hovers over card
        for (int a = 0; a < view.cardSprites.size(); a++){
            view.hoveredCards.set(a,view.cardSprites.get(a).getBoundingRectangle().contains(cords.x,cords.y));
        } */// TODO Remove from here, moved to view

        if (Gdx.input.justTouched()) {
            
            for (int a = view.cardSprites.size() - 1; a >= 0; a--){   
                
                Sprite sprite = view.cardSprites.get(a);            
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
                

                // if (view.cardSprites.get(a).getBoundingRectangle().contains(coords.x,coords.y)) {
                if (poly.contains(coords.x, coords.y)) {
                    // hoveredCards.set(a,cardSprites.get(a).getBoundingRectangle().contains(cords.x,cords.y));      
                    game.setSelectedCards(a, true);
                    break;
                    
                    
                }                            
        }
    }}

    public void onPlaySelectedCards(){
        game.playCards();
    }



}
