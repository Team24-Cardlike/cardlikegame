package org.example.Controller;

import java.util.ArrayList;

import org.example.Model.GameManager;
import org.example.Model.Round;
import org.example.Model.Save;

public class RoundController {
    public Round round;
    public GameManager manager;
    private Save save;

    public RoundController(Round round, GameManager manager, Save save){
        this.manager = manager;
        this.round = round;
        this.save = save;
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
        save.loadGame();
    }

    public void playCards() {
        save.saveGame(); // TODO: Remove this, it's just for testing
        this.round.playCards();
    }

    public void restart(){manager.resetRound();}
    public void switchView(String view){manager.setShopState();}
}
