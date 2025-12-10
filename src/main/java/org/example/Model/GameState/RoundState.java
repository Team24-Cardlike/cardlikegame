package org.example.Model.GameState;

import org.example.Model.GameManager;
import org.example.Model.Round;
import org.example.Model.RoundObserver;

public class RoundState implements GameState{
    Round r;
    @Override
    public void update(GameManager maneger) {
        r = maneger.currentRound;
        r.roundUpdate();
        if (r.roundFinished) {maneger.setState(new MapState());
        }

    }
}
