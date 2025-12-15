package org.example.Model.OpponentFactories.DeclaredBosses;

import org.example.Model.OpponentFactories.BossOpponent;

public class TyrBoss extends BossOpponent {

    @Override
    public void Create() {
        super.name = "Tyr";
        super.maxHealth = 900;
        super.health = maxHealth;
        super.damage = 25;
        super.turns = 2;
        super.image = name+".png";
    }
}
