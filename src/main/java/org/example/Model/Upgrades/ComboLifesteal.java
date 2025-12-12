package org.example.Model.Upgrades;

import org.example.Model.Combo;

public class ComboLifesteal extends Upgrade{
    final Combo combo;
    final double vampAmount;
    ComboLifesteal(String name, Combo combo, double vampAmount, int cost, String pic) {
        super(name,
                "Gain " + vampAmount + "% lifesteal on " + combo.name + " combos.",
                cost, "Sustain", pic);
        this.combo = combo;
        this.vampAmount = vampAmount;
    }
}
