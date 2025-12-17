package org.example.Model.Upgrades;

import org.example.Model.Round;

public class ExtraDmgFirstAttack extends Upgrade{
    int extraDamage;

    ExtraDmgFirstAttack(String name, int extraDamage, int cost, String pic, int id) {
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
