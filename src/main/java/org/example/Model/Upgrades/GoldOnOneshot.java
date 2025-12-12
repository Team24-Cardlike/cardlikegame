package org.example.Model.Upgrades;

import org.example.Model.Round;

public class GoldOnOneshot extends Upgrade{
    int extraGold;
    GoldOnOneshot(String name, int cost, int extraGold, String pic, int id){
        super(name, "Grants you " + extraGold + " extra gold if you manage to kill the opponent without taking damage.", cost, "Economy", pic, id);
        this.extraGold = extraGold;
    }
    @Override
    public boolean checkCondition(Round round) {
        return round.getOpponent().getHealth() <= 0 && !round.beenAttacked;
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

