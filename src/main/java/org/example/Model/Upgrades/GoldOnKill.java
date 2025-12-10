package org.example.Model.Upgrades;

import org.example.Model.Round;

public class GoldOnKill extends Upgrade{
    int extraGold;
    GoldOnKill(String name, int cost, int extraGold){
        super(name, "Grants you " + extraGold + " extra gold after a kill.", cost, "Economy");
        this.extraGold = extraGold;
    }
    @Override
    public boolean checkCondition(Round round) {
        return round.getOpponent().getHealth() <= 0;
    }

    /***
     *
     * @param round the current gameState of the round (call with "this")
     * @implNote make sure to only call after getting true from checkCondition()
     */
    @Override
    public void onTriggered(Round round) {
        int gold = round.getUser().getGold() + this.extraGold;
        round.getUser().setGold(gold);
    }
}
