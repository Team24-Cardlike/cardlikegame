package org.example.Controller;

import java.util.ArrayList;

import org.example.Model.GameManager;
import org.example.Model.Round;

public class RoundController {
    public Round round;
    public GameManager manager;
    

    public RoundController(Round round, GameManager manager){
        this.manager = manager;
        this.round = round;        
    }    

    public void nextRound(){
        round.endRound();

    }

    public void selectCard(int a) {
        round.addSelectedCards(a);

    }

    public void unselectCard(int a) {
        round.unselectCard(a);

    }
    public void discardCards(ArrayList<Integer> cards){
        this.round.discard();
    }

    public void playCards() {        
        this.round.playCards();
    }

    public void restart(){manager.resetRound();}
    public void switchView(String view){manager.setShopState();}
}
