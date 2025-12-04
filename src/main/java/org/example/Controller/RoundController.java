package org.example.Controller;

import java.util.ArrayList;

import org.example.Model.Round;

public class RoundController {
    public Round round;

    public RoundController(Round round){
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

        this.round.discard(cards);
    }

    public void playCards() {
        this.round.playCards();
    }    
}
