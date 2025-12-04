package org.example.Controller;

import java.security.PublicKey;
import java.util.ArrayList;

import org.example.GameRender;
import org.example.Model.Card;
import org.example.Model.Round;
import com.badlogic.gdx.math.Vector3;

public class Controller{    
    public Round round;
    public GameRender gr;
    public Controller(Round round, GameRender gr){
        this.round = round;
        this.gr = gr;
    }    

    public void nextRound(){
        round.nextRound();
    }

    public void selectCard(int a, boolean bool) {
        round.setSelectedCards(a, bool);
    }

    public void discardCards(ArrayList<Integer> cards){
        this.round.discard(cards);
    }

    public void playCards(ArrayList<Card> temp) {
        this.round.playCards(temp);
    }

    public void switchGameView(){
        gr.switchView();
    }
}
