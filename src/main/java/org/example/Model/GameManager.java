package org.example.Model;

import org.example.Model.GameState.GameState;
import org.example.Model.GameState.MapState;
import org.example.Model.GameState.RoundState;

public class GameManager {



    public Round currentRound;
    public GameMap gameMap;
    private User user;

    RoundObserver roundObs;
    GameState state;





    public  GameManager(RoundObserver o) {
        this.currentRound = new Round(new Opponent(400,15,1, "bossman"),  o);
        this.gameMap = new GameMap(100, user,this);
        this.roundObs = o;

        this.setState(new RoundState());
    }

    public GameManager(User user, GameMap map, RoundObserver o) {
        // Load saved game, start att map State
        this.user = user;
        this.gameMap = map;
        this.roundObs = o;

        setState(new MapState());
    }
    public void gameLoop() {
        state.update(this);
    }

    public  void setState(GameState state) {
        this.state = state;
    }

    public void initRound() {
        currentRound.init();
    }

}
