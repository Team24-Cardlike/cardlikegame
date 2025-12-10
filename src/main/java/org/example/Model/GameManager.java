package org.example.Model;

import org.example.Model.GameState.GameState;
import org.example.Model.GameState.MenuState;
import org.example.Model.GameState.RoundState;

import java.util.ArrayList;

public class GameManager {



    public Round currentRound;
    public GameMap gameMap;
    private User user;
    private boolean gameMenu = true;

    RoundObserver roundObs;
    ArrayList<StateObserver> stateObservers = new ArrayList<>();
    GameState state;





    public  GameManager(RoundObserver o) {

        this.currentRound = new Round(new Opponent(400,15,1, "bossman"),  o);
        this.gameMap = new GameMap(100, user,this);
        this.roundObs = o;


        this.setState(new MenuState());
        notifyState();


    }

    public GameManager(User user, GameMap map, RoundObserver o) {
        // Load saved game, start att map State
        this.user = user;
        this.gameMap = map;
        this.roundObs = o;

    }
    public void gameLoop() {

        state.update(this);

    }

    public  void setState(GameState state) {
        this.state = state;
    }

    public String getState() {
        return state.getName();
    }



    public void initRound() {
        currentRound.init();
    }

    public void startGame( ){
        setState(new RoundState());
        notifyState();
        initRound();
    }

    public void setStateObs(StateObserver obs) {
        stateObservers.add(obs);
    }
    public void notifyState() {
       for(StateObserver o: stateObservers) {
           o.updateState(getState());
       }
    }

}
