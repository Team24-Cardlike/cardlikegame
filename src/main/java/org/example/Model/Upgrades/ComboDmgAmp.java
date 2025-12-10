package org.example.Model.Upgrades;

import org.example.Model.Combo;

public class ComboDmgAmp<T extends Number> extends Upgrade<T>{
    final Combo ampedCombo;
    final T ampAmount;
    ComboDmgAmp(String name, Combo ampedCombo, T ampAmount, int cost) {
        super(name, "Gain " + ampAmount + " damage-amp on " + ampedCombo.name + " combos.", cost, "Damage");
        this.ampedCombo = ampedCombo;
        this.ampAmount = ampAmount;
    }

    @Override
    public T getNum() {
        return ampAmount;
    }
}
