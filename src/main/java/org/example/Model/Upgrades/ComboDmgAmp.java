package org.example.Model.Upgrades;

import org.example.Model.Combo;

public class ComboDmgAmp extends DamageUpgrade{
    final Combo combo;
    final double ampAmount;
    public ComboDmgAmp(String name, Combo combo, double ampAmount, int cost) {
        super(name, "Gain " + ampAmount + " damage-amp on " + combo.name + " combos.", cost);
        this.combo = combo;
        this.ampAmount = ampAmount;
    }
}
