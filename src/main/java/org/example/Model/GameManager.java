package org.example.Model;

import org.example.Model.GameState.GameState;
import org.example.Model.GameState.MapState;
import org.example.Model.GameState.MenuState;
import org.example.Model.GameState.RoundState;

import java.util.ArrayList;

public class GameManager {


    private  MapObserver mapObs;
    public Round currentRound;
    public GameMap gameMap;
    private User user;


    RoundObserver roundObs;
    ArrayList<StateObserver> stateObservers = new ArrayList<>();
    GameState state;





    public  GameManager(RoundObserver roundObs, MapObserver mapObs) {

        this.currentRound = new Round(new Opponent(4,15,1, "bossman"),  roundObs);
        this.gameMap = new GameMap(1, user,this, mapObs);
        this.roundObs = roundObs;
        this. mapObs = mapObs;
        this.user = currentRound.getUser();


        this.setState(new MenuState());
        notifyState();


    }

    public GameManager(User user, GameMap map, RoundObserver roundObs, MapObserver mapObs) {
        // Load saved game, start att map State
        this.user = user;
        this.gameMap = map;
        this.roundObs = roundObs;
        this.mapObs = mapObs;

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
        Opponent op = gameMap.currentOpponent;
        this.currentRound = new Round(this.user,op,roundObs);

        setState(new RoundState());
        notifyState();
        currentRound.init();
    }
    public void initMap() {

    }

    public void startGame( ){
        initMap();
        setState(new MapState());;
        notifyState();

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
