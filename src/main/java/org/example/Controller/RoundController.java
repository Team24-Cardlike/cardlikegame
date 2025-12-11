package org.example.Controller;

import java.util.ArrayList;

import org.example.Model.GameManager;
import org.example.Model.Round;

public class RoundController {
    public Round round;
    public GameManager manager;

    public RoundController(Round round, GameManager manager){
        this.round = round;
        this.manager = manager;
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

    public void openShop(){
        manager.openShop();
    }

    public void playCards() {
        this.round.playCards();
    }
}
