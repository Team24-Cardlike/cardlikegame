package org.example.Model;


import java.util.ArrayList;

//Observer between Model and RoundView letting RoundView get notified when a in-round state has changed
public interface RoundObserver {
    void onHandChanged(ArrayList<String> hand);
    void onSelectedCard(ArrayList<String> selected);
    void onBestComboChanged(String bestCombo);
    void onHealthChanged(float userHealth, float opponentHealth);
    void onGameEnded(String resultMessage, int totalDamageUser, int totalDamageOpponent);
    void onPlayerTurn(boolean playerTurn);
    void onOpponentAttack(int damage);
    void onUnselectedCard(ArrayList<String> selected);
    void onRoundInit();
}


