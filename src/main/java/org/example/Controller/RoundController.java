package org.example.Controller;

import java.util.ArrayList;

import org.example.Model.GameManager;
import org.example.Model.Round;

public class RoundController {
    public Round round;
    public GameManager manager;

    public RoundController(GameManager manager){
        this.manager = manager;
    }

    public void setRound(Round r) {
        this.round = manager.currentRound;
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

    public void openHandbook(){manager.setHandbookState();}

    public void restart(){manager.resetRound();}

    public void switchView(String view){manager.setShopState();}

    public void updateManager() {
        manager.gameLoop();
    }
}
