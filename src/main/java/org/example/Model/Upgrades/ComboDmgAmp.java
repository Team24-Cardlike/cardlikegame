package org.example.Model.Upgrades;

import org.example.Model.Combo;
import org.example.Model.Round;

import java.util.Objects;

public class ComboDmgAmp extends Upgrade{
    final Combo ampedCombo;
    final float ampAmount;
    /***
     * @
     * @param name the name of the Upgrade.
     * @param ampedCombo the combo that triggers the amplifier.
     * @param ampAmount the float that you multiply with your damage.
     * @param cost the cost of the upgrade in the shop.
     */
    ComboDmgAmp(String name, Combo ampedCombo, float ampAmount, int cost, String pic, int id) {
        super(name, "Gain " + ampAmount + "x damage-amp on " + ampedCombo.name + " combos.", cost, "Damage", pic, id);
        this.ampedCombo = ampedCombo;
        this.ampAmount = ampAmount;
    }

    @Override
    public boolean checkCondition(Round round) {
        return Objects.equals(round.getUser().getComboPlayedCards().name, this.ampedCombo.name);
    }

    @Override
    public void onTriggered(Round round) {
        round.getUser().damage = (int) (round.getUser().damage * ampAmount);
    }
}
