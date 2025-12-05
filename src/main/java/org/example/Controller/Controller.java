package org.example.Controller;

import java.util.ArrayList;

import org.example.Model.Card;
import org.example.Model.Game;

public class Controller{    
    private Game game;    

    public Controller(Game game){        
        this.game = game;
    }    

    public void nextRound(){
        game.nextRound();
    }

    public void selectCard(int a, boolean bool) {
        game.setSelectedCards(a, bool);
    }

    public void discardCards(ArrayList<Integer> cards){
        this.game.discard(cards);
    }

    public void playCards(ArrayList<Card> temp) {
        this.game.playCards(temp);
    }    
}
