package org.example.Model.OpponentFactories.DeclaredBosses;

import org.example.Model.OpponentFactories.BossOpponent;

public class BalderBoss extends BossOpponent {

    @Override
    public void Create() {
        super.name = "Balder";
        super.maxHealth = 500;
        super.damage = 15;
        super.turns = 3;
        super.image = name+".png";
    }
    @Override
    public String getName() {
        return name;
    }
}
