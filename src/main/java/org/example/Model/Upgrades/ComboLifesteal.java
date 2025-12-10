package org.example.Model.Upgrades;

import org.example.Model.Combo;

public class ComboLifesteal<T extends Number> extends Upgrade<T>{
    final Combo combo;
    final T vampAmount;
    ComboLifesteal(String name, Combo combo, T vampAmount, int cost) {
        super(name,
                "Gain " + vampAmount + "% lifesteal on " + combo.name + " combos.",
                cost, "Sustain");
        this.combo = combo;
        this.vampAmount = vampAmount;
    }


    @Override
    public T getNum() {
        return vampAmount;
    }
}
