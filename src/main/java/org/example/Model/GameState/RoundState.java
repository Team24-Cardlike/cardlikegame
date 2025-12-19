package org.example.Model.GameState;

import org.example.Model.GameManager;
import org.example.Model.Round;

public class RoundState implements GameState{
    String name = "round";
    Round r;

    @Override
    public void update(GameManager manager) {
        r = manager.currentRound;
        r.roundUpdate();
        if (r.roundFinished) {                             
            if (manager.currentRound.getWon()) {                                            
                manager.initMap();                       
            }            
        }

    }

    @Override
    public String getName() {
        return this.name;
    }
}
