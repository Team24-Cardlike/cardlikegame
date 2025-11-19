package org.example.Controller;

import java.util.ArrayList;

import org.example.Model.Card;
import org.example.Model.Game;
import org.example.View.View;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class Controller{
    private View view;
    public Game game;

    public Controller(View _view, Game _game){
        this.view = _view;
        this.game = _game;
    }

    public static void input(ArrayList<Sprite> cardSprites, FitViewport viewport, ArrayList<Boolean> hoveredCards, ArrayList<Boolean> boolSelectedCards) {
        //float speed = 4f;
        //float delta = Gdx.graphics.getDeltaTime(); // retrieve the current delta

        Vector3 cords = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
        viewport.getCamera().unproject(cords);

        //Check if user hovers over card
        for (int a = 0; a < cardSprites.size(); a++){
            hoveredCards.set(a,cardSprites.get(a).getBoundingRectangle().contains(cords.x,cords.y));
        }

        if (Gdx.input.justTouched()) {
            for (int i = 0; i < cardSprites.size(); i ++) {
                if (cardSprites.get(i).getBoundingRectangle().contains(cords.x,cords.y)) {
                    boolSelectedCards.set(i,!boolSelectedCards.get(i));
                }
            }
            // playSelectedCards();

            //Send input to controler

           /* for (int i = 0; i < game.getUser().getHand().size(); i++){
                Sprite card = cardSprites.get(i);
                if(card.getBoundingRectangle().contains(cords.x, cords.y)){
                    boolean bool = game.getUser().getBoolSelectedCards().get(i);
                    game.getUser().setCardAsSelectedBool(i, !bool);
                    //selected.set(i, !selected.get(i));
                }
            } */
        }
    }
    /*
    public void onPlaySelectedCards(ArrayList<Integer> cards){
        this.game.user.setSelectedCards(cards);
    }*/
}
