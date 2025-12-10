package org.example.Model.Upgrades;

import org.example.Model.GameObserver;

import java.util.List;

public class UpgradeManager implements GameObserver {
    UpgradeLibrary lib = new UpgradeLibrary();
    @Override
    public void onHandChanged(List<String> hand) {

    }

    @Override
    public void onHealthChanged(float userHealth, float opponentHealth) {
        if(opponentHealth <= (float)lib.getUpgrade("Fatality").getNum()){

        }
    }

    @Override
    public void onCardSelected(int index, boolean selected) {

    }

    @Override
    public void onGameEnded(String resultMessage) {

    }

    @Override
    public void onPlayerTurn(boolean playerTurn) {

    }
}
