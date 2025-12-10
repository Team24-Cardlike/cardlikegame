package org.example.Model.Upgrades;

import org.example.Model.Round;

public class ReviveUpgrade extends Upgrade{
    int healthAfterRevive;
    ReviveUpgrade(String name, String desc, int healthAfterRevive, int cost) {
        super(name, desc, cost, "Sustain");
        this.healthAfterRevive = healthAfterRevive;
    }

    /*@Override
    public T getNum() {
        T t = null;
        return t;
    }*/

    @Override
    public boolean checkCondition(Round round) {
        return round.getUser().getHealthRatio() <= 0;
    }

    @Override
    public void onTriggered(Round round) {
        round.getUser().setHealth(this.healthAfterRevive);
    }
}
