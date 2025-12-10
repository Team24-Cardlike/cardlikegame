package org.example.Model.Upgrades;

import org.example.Model.Combo;
import org.example.Model.Round;

public class ComboLifesteal extends Upgrade{
    final Combo vampCombo;
    final float vampAmount;
    ComboLifesteal(String name, Combo combo, float vampAmount, int cost) {
        super(name,
                "Gain " + vampAmount*100 + "% lifesteal on " + combo.name + " combos.",
                cost, "Sustain");
        this.vampCombo = combo;
        this.vampAmount = vampAmount;
    }


    /*@Override
    public T getNum() {
        return vampAmount;
    }*/

    @Override
    public boolean checkCondition(Round round) {
        return round.getUser().getComboPlayedCards() == this.vampCombo;
    }

    @Override
    public void onTriggered(Round round) {
        int health = (int) ((round.getUser().getDamage() * vampAmount) + round.getUser().getHealth());
        round.getUser().setHealth(health);
    }
}
