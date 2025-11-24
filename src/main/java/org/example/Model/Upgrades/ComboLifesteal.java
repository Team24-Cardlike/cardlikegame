package org.example.Model.Upgrades;

public class ComboLifesteal extends Upgrade{
    final Combo combo;
    final double vampAmount;
    ComboLifesteal(String name, Combo combo, double vampAmount, int cost) {
        super(name,
                "Gain " + vampAmount + "% lifesteal on " + combo.name + " combos.",
                cost, "Sustain");
        this.combo = combo;
        this.vampAmount = vampAmount;
    }
}
