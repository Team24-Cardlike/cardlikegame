package org.example.Model.OpponentFactories.DeclaredBosses;

import org.example.Model.OpponentFactories.BossOpponent;

public class BalderBoss extends BossOpponent {

    public BalderBoss() {
        super.name = "Balder";
        super.maxHealth = 500;
        super.damage = 15;
        super.turns = 3;
        super.image = name+".png";
        super.setHealth(maxHealth);
    }
    @Override
    public String getName() {
        return name;
    }
}
