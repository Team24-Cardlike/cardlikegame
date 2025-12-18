package org.example.Model;

import org.example.Model.GameState.*;
import org.example.Model.GameState.GameState;
import org.example.Model.GameState.MapState;
import org.example.Model.GameState.MenuState;
import org.example.Model.GameState.RoundState;
import org.example.Model.GameState.ShopState;
import org.example.Model.OpponentFactories.OpponentInterface;
import org.example.Model.Upgrades.UpgradeLibrary;
import org.example.Model.OpponentFactories.Opponent;


import java.util.ArrayList;
import java.util.Objects;
import java.util.Set;

public class GameManager {
    private  MapObserver mapObs;
    public Round currentRound;
    public GameMap gameMap;
    private User user;



    private RoundState roundState;
    private ShopState shopState;
    private MenuState menuState;
    private MapState mapState;

    RoundObserver roundObs;
    ArrayList<StateObserver> stateObservers = new ArrayList<>();
    GameState state;
    private UpgradeLibrary upgradeLibrary = new UpgradeLibrary();

    String difficulty = "Normal";

    public GameManager(RoundObserver roundObs, MapObserver mapObs) {
        this.gameMap = new GameMap(this, mapObs);
        this.roundObs = roundObs;
        this.mapObs = mapObs;
        this.user = new User(50);

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
    void setCompletedLvls(Set<String> completedLvls) {
        gameMap.setCompletedLvls(completedLvls);
    }

    void setAvailableLvls(Set<String> availableLvls) {
        gameMap.setAvailableLvls(availableLvls);
    }

    String getDifficulty() {
        return this.difficulty;
    }

    Set<String> getCompletedLvls() {
        return gameMap.getCompletedLvls();
    }

    Set<String> getAvailableLvls() {
        return gameMap.getAvailableLvls();
    }

    public void gameLoop() {
        state.update(this);
    }

    public  void setState(GameState state) {
        this.state = state;
        notifyState();
    }

    public void setShopState(){
        setState(shopState);
        notifyState();
    }

    public void setHandbookState(){
        setState(new HandbookState());
        notifyState();
    }

    public String getState() {
        return this.state.getName();
    }

    public void resetRound(){
        //this.currentRound = new Round(user, gameMap.currentOpponent, roundObs);
        setState(this.mapState);
        notifyState();
        //currentRound.init();
    }

    public void initRound() {
        Opponent op = gameMap.currentOpponent;
        this.currentRound = new Round(this.user,op,roundObs);
        this.roundState = new RoundState();
        System.out.println("This round: "+ currentRound.getOpponent().getName());
        setState(this.roundState);
        notifyState();
        currentRound.init();
    }

    public void initMap() {
        this.gameMap.updateMap();

    }

    public User getUser(){return user;}

    public void closeShop(){
        setState(this.mapState);
        notifyState();
    }

    public void closeHandbook(){
        setState(this.roundState);
        notifyState();
        currentRound.init();
    }

    public void startGame( ){
        initMap();
        gameMap.setDifficulty(this.difficulty);
        this.mapState = new MapState();
        setState(mapState);;
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

    public Round getRound() {
       return this.currentRound;
    }

    public void setUpgrades(ArrayList<Integer> upgradeIds) {
        for (int id : upgradeIds) {
            user.getUpgrades().add(upgradeLibrary.getUpgrade(id));
        }
    }

    public void setDifficulty(String s){
        this.difficulty = s;
    }
}
