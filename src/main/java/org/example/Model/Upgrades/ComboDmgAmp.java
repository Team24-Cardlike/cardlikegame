package org.example.Model.Upgrades;

import org.example.Model.Combo;
import org.example.Model.Round;

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
    ComboDmgAmp(String name, Combo ampedCombo, float ampAmount, int cost) {
        super(name, "Gain " + ampAmount + " damage-amp on " + ampedCombo.name + " combos.", cost, "Damage");
        this.ampedCombo = ampedCombo;
        this.ampAmount = ampAmount;
    }

    /*@Override
    public T getNum() {
        return ampAmount;
    }*/

    @Override
    public boolean checkCondition(Round round) {
        return round.getUser().getComboPlayedCards() == this.ampedCombo;
    }

    @Override
    public void onTriggered(Round round) {
        round.getUser().damage = (int) (round.getUser().damage * (float)ampAmount);
    }
}
