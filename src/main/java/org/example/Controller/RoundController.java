package org.example.Controller;

import java.util.ArrayList;

import org.example.Model.GameManager;
import org.example.Model.Round;
import org.example.Model.GameState.MapState;

public class RoundController {
    public Round round;
    public GameManager manager;
    

    public RoundController(GameManager manager){
        this.manager = manager;
    }


    public void nextRound(){
        manager.setState(new MapState());        
        round.endRound();
    }

    public void selectCard(int a) {
        round.addSelectedCards(a);
    }

    public void unselectCard(int a) {
        round.unselectCard(a);
    }

    public void discardCards(){
        this.round.discard();
    }

    public void playCards() {        
        this.round.playCards();
    }

    public void closeHandBook(){
        manager.closeHandbook();
    }

    public void openHandbook(){manager.setHandbookState();}

    public void restart(){manager.resetRound();}

    public void updateManager() {
        manager.gameLoop();
    }

    public void initRound() {
        Round r = this.manager.getRound();
        this.round = r;
    }
}
