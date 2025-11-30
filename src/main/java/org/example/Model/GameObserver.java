package org.example.Model;


import java.util.List;

//Observer between Model and View letting View get notified when a in-game state has changed
public interface GameObserver {
    void onHandChanged(List<String> hand);
    void onHealthChanged(float userHealth, float opponentHealth);
    void onCardSelected(int index, boolean selected);
    void onGameEnded(String resultMessage);
    void onPlayerTurn(boolean playerTurn);
}
