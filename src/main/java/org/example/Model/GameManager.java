package org.example.Model;

public class GameManager {



     public Round currentRound;
     public GameMap gameMap;
    public User user;

    RoundObserver obs;



    public enum GameState {
        ROUND,
        MAP,
        SHOP
    }

    public GameState state = GameState.ROUND; // TODO INITIAL maybe map?


    public  GameManager(RoundObserver o) {
        this.currentRound = new Round(new Opponent(400,15,1, "bossman"),  o);
        this.gameMap = new GameMap(100, currentRound.user,this);
        this.obs = o;
    }
    public GameManager(User user, GameMap map, RoundObserver o) {
        // Load saved game, start att map State

        this.user = user;
        this.gameMap = map;
        state = GameState.MAP;
        this.obs = o;
    }
    public void gameLoop() {
        if (state == GameState.MAP) {}
        if (state == GameState.SHOP) {}
        if (state == GameState.ROUND) {

            currentRound.roundUppdate();
            if (currentRound.roundFinished ) { }//TODO: add logick ending round and switching to map view
        }
    }


    public void initRound() {
        currentRound.init();
    }

}
