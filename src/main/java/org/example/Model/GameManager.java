package org.example.Model;

import org.example.Model.GameState.*;

import java.util.ArrayList;

public class GameManager {

    public Round currentRound;
    public GameMap gameMap;
    private User user;
    private boolean gameMenu = true;

    private RoundState roundState;
    private ShopState shopState;
    private MenuState menuState;

    RoundObserver roundObs;
    ArrayList<StateObserver> stateObservers = new ArrayList<>();
    GameState state;

    public GameManager(RoundObserver o) {
        this.currentRound = new Round(new Opponent(100,15,1, "bossman"),  o);
        this.roundObs = o;
        this.user = currentRound.getUser();
        this.gameMap = new GameMap(100, user,this);
        //this.setState(new ShopState());

        shopState = new ShopState();
        menuState = new MenuState();

        this.setState(new MenuState());
        notifyState();
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
        this.currentRound = new Round(user, new Opponent(400,15,1, "bossman"), roundObs);
        notifyState();
        currentRound.init();
    }

    public void initRound() {
        currentRound.init();
    }

    public User getUser(){return user;}

    public void closeShop(){
        setState(this.roundState);
        notifyState();
        initRound();
    }

    public void startGame( ){
        roundState = new RoundState();
        setState(roundState);
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
