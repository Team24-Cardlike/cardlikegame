package org.example.Controller;

import java.security.PublicKey;
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
                view.playSelectedCards();
                onPlaySelectedCards(view.selectedIndices);                
            }
        });

        view.discardButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                view.throwCards();
                discardCards(view.removedIndices);
            }
        });

    }

    public void input() {
        //float speed = 4f;
        //float delta = Gdx.graphics.getDeltaTime(); // retrieve the current delta

        Vector3 cords = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
        view.viewport.getCamera().unproject(cords);

        //Check if user hovers over card
        for (int a = 0; a < view.cardSprites.size(); a++){
            view.hoveredCards.set(a,view.cardSprites.get(a).getBoundingRectangle().contains(cords.x,cords.y));
        }

        if (Gdx.input.justTouched()) {
            for (int i = 0; i < view.cardSprites.size(); i ++) {
                if (view.cardSprites.get(i).getBoundingRectangle().contains(cords.x,cords.y) && (getNumberOfSelected(view.boolSelectedCards) < 5)){
                    view.boolSelectedCards.set(i,!view.boolSelectedCards.get(i));
                    if(getNumberOfSelected(view.boolSelectedCards) > 2){
                        bestCombo(view.boolSelectedCards);
                    }
                    //view.showCombo;
                }
            }
            // playSelectedCards();

            //Send input to controller

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

    private void nextRound(){

    }

    private void bestCombo(ArrayList<Boolean> boolList){
        ArrayList<Integer> intList = new ArrayList<>();
        int i = 0;
        for(Boolean bool : boolList){
            if(bool)intList.add(i);
            i++;
        }

        String combo = game.bestCombo(getSelectedCardsAsCards(intList));
        view.showCombo(combo);
    }

    private int getNumberOfSelected(ArrayList<Boolean> cardsBool){
        int i = 0;
        for(Boolean bool : cardsBool){
            if(bool)i++;
        }
        return i;
    }

    public ArrayList<Card> getSelectedCardsAsCards(ArrayList<Integer> cards){
            ArrayList<Card> hand = this.game.user.getHand();
            ArrayList<Card> temp = new ArrayList<>();
            for(int i : cards) {
                temp.add(hand.get(i));
            }
            return temp;
    }

    public void discardCards(ArrayList<Integer> cards){
        this.game.discard(cards);
    }

    public void onPlaySelectedCards(ArrayList<Integer> cards){
        ArrayList<Card> temp = getSelectedCardsAsCards(cards);
        this.game.playCards(temp);

        if(game.gameState){
        updateView(temp);
        opponentAnimation();
        }
        else{
            view.endGame(game.totalDamageToPlayer, game.totalDamageToOpponent);
            view.nextButton.addListener(new ClickListener(){
                @Override
                public void clicked(InputEvent event, float x, float y){
                    //View.nextView();? Eller ska nytt objekt
                    nextRound();
                }
            });
        }
    }

    public void updateView(ArrayList<Card> cards){
        view.createSpriteList();
    }
    public void opponentAnimation(){
        view.oppAnimation();
    }
}
