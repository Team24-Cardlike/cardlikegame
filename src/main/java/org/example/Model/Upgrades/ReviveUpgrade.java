package org.example.Model.Upgrades;

import org.example.Model.Round;

public class ReviveUpgrade extends Upgrade{
    int healthAfterRevive;
    boolean beenUsed = false;
    ReviveUpgrade(String name, String desc, int healthAfterRevive, int cost, String pic, int id) {
        super(name, desc, cost, "Sustain", pic, id);
        this.healthAfterRevive = healthAfterRevive;
    }

    @Override
    public boolean checkCondition(Round round) {
        return round.getUser().getHealthRatio() <= 0 && !beenUsed;
    }

    @Override
    public void onTriggered(Round round) {
        System.out.println("Hel gave you another chance! (Revived with 1HP)");
        round.getUser().setHealth(this.healthAfterRevive);
        this.beenUsed = true;
    }
}
