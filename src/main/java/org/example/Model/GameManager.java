package org.example.Model;

import org.example.Model.GameState.*;
import org.example.Model.GameState.GameState;
import org.example.Model.GameState.MapState;
import org.example.Model.GameState.MenuState;
import org.example.Model.GameState.RoundState;
import org.example.Model.OpponentFactories.OpponentInterface;
import org.example.Model.OpponentFactories.Opponent;
import org.example.Model.OpponentFactories.OpponentInterface;

import java.util.ArrayList;

public class GameManager {
    private  MapObserver mapObs;
    public Round currentRound;
    public GameMap gameMap;
    private User user;



    private RoundState roundState;
    private ShopState shopState;
    private MenuState menuState;

    RoundObserver roundObs;
    ArrayList<StateObserver> stateObservers = new ArrayList<>();
    GameState state;


    public  GameManager(RoundObserver roundObs, MapObserver mapObs) {

        this.gameMap = new GameMap(100, this, mapObs);
        this.roundObs = roundObs;
        this.mapObs = mapObs;
        this.user = new User(1000);

        shopState = new ShopState();
        menuState = new MenuState();

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
        notifyState();
    }

    public void setShopState(){
        setState(new ShopState());
        notifyState();
    }

    public void setHandbookState(){
        setState(new HandbookState());
        notifyState();
    }

    public String getState() {
        return state.getName();
    }

    public void resetRound(){

        this.currentRound = new Round(user, gameMap.currentOpponent, roundObs);
        notifyState();
        currentRound.init();
    }

    public void initRound() {
        Opponent op = gameMap.currentOpponent;
        this.currentRound = new Round(this.user,op,roundObs);

        System.out.println("This round: "+ currentRound.getOpponent().getName());
        setState(new RoundState());
        notifyState();
        currentRound.init();
    }

    public void initMap() {
        this.gameMap.updateMap();

    }

    public User getUser(){return user;}

    public void closeShop(){
        setState(this.roundState);
        notifyState();
        initRound();
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
