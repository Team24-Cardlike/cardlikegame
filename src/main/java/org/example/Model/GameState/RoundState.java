package org.example.Model.GameState;

import org.example.Controller.RoundController;
import org.example.Model.GameManager;
import org.example.Model.Round;
import org.example.Model.RoundObserver;
import org.example.Views.RoundView;

public class RoundState implements GameState{
    String name = "round";
    Round r;

    @Override
    public void update(GameManager manager) {
        r = manager.currentRound;
        r.roundUpdate();
        if (r.roundFinished) {manager.initMap();
            System.out.println();
            manager.setState(new MapState());


        }

    }

    @Override
    public String getName() {
        return name;
    }
}
