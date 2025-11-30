package org.example.Model;


import java.util.ArrayList;
import java.util.List;

public class GameObservers {
    //List with observers who is observing game state
    final private ArrayList<GameObserver> observers = new ArrayList<>();
    Game game;

    public GameObservers(Game g) {this.game = g;}

    //Add observer to observer's list
    public void addObserver(GameObserver observer) {
        observers.add(observer);
    }
    //Remove observer from observer's list
    public void removeObserver(GameObserver observer) {
        observers.remove(observer);
    }

    public void notifyGameInit() {
        notifyHandChanged(game.getUser().getHand());
        notifyHealthChanged(game.user.getHealthRatio(),game.opponent.getHealthRatio());
        notifyPlayerTurn(true);

    }

    // Hand changed sending updated hand to view in a list of strings
    public void notifyHandChanged(List<Card> hand) {
        List<String> handToString = new ArrayList<>();

        for (Card c : hand ){
            handToString.add(c.pic);}

        for (GameObserver o : observers) {
            o.onHandChanged(handToString);}
    }

    public void notifyHealthChanged(float playerHealth,float opponentHealth) {
        for (GameObserver o : observers) {
            o.onHealthChanged(playerHealth,opponentHealth);
        }
    }

    public void notifyGameEnded() {
        for (GameObserver o : observers) {
            o.onGameEnded("game ended ?? ");
        }
    }

    public void notifyCardSelect(int index, boolean b) {
        for (GameObserver o : observers) {
            o.onCardSelected(index,b);
        }
    }
    public void notifyPlayerTurn(boolean playerTurn) {
        for (GameObserver o : observers) {
            o.onPlayerTurn(playerTurn);
        }
    }
}
