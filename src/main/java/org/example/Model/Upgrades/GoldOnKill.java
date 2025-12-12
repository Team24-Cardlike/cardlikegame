package org.example.Model.Upgrades;

import org.example.Model.Round;

public class GoldOnKill extends Upgrade{
    int extraGold;
    GoldOnKill(String name, int cost, int extraGold, String pic, int id){
        super(name, "Grants you " + extraGold + " extra gold after a kill.", cost, "Economy", pic, id);
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
        System.out.println("You earned " + this.extraGold + " extra gold from " + this.name + "!");
        round.getUser().addGold(this.extraGold);
    }
}
