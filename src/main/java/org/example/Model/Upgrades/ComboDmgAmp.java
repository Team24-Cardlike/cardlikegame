package org.example.Model.Upgrades;

import org.example.Model.Combo;

public class ComboDmgAmp extends DamageUpgrade{
    final Combo combo;
    final double ampAmount;
    ComboDmgAmp(String name, Combo combo, double ampAmount, int cost, String pic) {
        super(name, "Gain " + ampAmount + " damage-amp on " + combo.name + " combos.", cost, pic);
        this.combo = combo;
        this.ampAmount = ampAmount;
    }
}
