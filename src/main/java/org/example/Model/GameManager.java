package org.example.Model;

public class GameManager {



     public Round currentRound;
     private GameMap gameMap;
     private User user;

    RoundObserver roundObs;





    public  GameManager(RoundObserver o) {
        this.currentRound = new Round(new Opponent(400,15,1, "bossman"),  o);
        this.gameMap = new GameMap(100, user,this);
        this.roundObs = o;
    }

    public GameManager(User user, GameMap map, RoundObserver o) {
        // Load saved game, start att map State
        this.user = user;
        this.gameMap = map;
        this.roundObs = o;
    }
    public void gameLoop() {


            currentRound.roundUpdate();
            if (currentRound.roundFinished ) { }//TODO: add logick ending round and switching to map view

    }


    public void initRound() {
        currentRound.init();
    }

}
