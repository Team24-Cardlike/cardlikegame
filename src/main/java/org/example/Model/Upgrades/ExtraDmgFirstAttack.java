package org.example.Model.Upgrades;

import org.example.Model.Round;

public class ExtraDmgFirstAttack extends Upgrade{
    /***
     * @param name String name of the upgrade
     * @param cost int cost of the upgrade
     * @param pic
     * @param id
     */
    int extraDamage;
    ExtraDmgFirstAttack(String name, int cost, String pic, int id, int extraDamage) {
        super(name, "Your first attack each fight does " + extraDamage + " damage.", cost, "Damage", pic, id);
        this.extraDamage = extraDamage;
    }

    @Override
    public boolean checkCondition(Round round) {
        return round.turnNumber == 1;
    }

    @Override
    public void onTriggered(Round round) {
        round.getUser().damage += extraDamage;
    }
}
