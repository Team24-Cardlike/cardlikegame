package org.example.Model.Upgrades;

import org.example.Model.Combo;
import org.example.Model.Round;

import java.util.Objects;

public class ComboLifesteal extends Upgrade{
    final Combo vampCombo;
    final float vampAmount;
    ComboLifesteal(String name, Combo combo, float vampAmount, int cost, String pic, int id) {
        super(name,
                "Gain " + (int)(vampAmount*100) + "% lifesteal on " + combo.name + " combos.",
                cost, "Sustain", pic, id);
        this.vampCombo = combo;
        this.vampAmount = vampAmount;
    }


    /*@Override
    public T getNum() {
        return vampAmount;
    }*/

    @Override
    public boolean checkCondition(Round round) {
        return Objects.equals(round.getUser().getComboPlayedCards().name, this.vampCombo.name);
    }

    @Override
    public void onTriggered(Round round) {

        int addedHealth = (int) ((round.getUser().getDamage() * vampAmount));
        System.out.println("Du healade: " + addedHealth + "HP från " + this.name + "!");
        int health = addedHealth + round.getUser().getHealth();
        round.getUser().setHealth(health);
    }
}
