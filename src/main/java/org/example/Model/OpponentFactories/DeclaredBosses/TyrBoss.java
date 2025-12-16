package org.example.Model.OpponentFactories.DeclaredBosses;

import org.example.Model.OpponentFactories.BossOpponent;

public class TyrBoss extends BossOpponent {

    public TyrBoss() {
        super.name = "Tyr";
        super.maxHealth = 800;
        super.damage = 20;
        super.turns = 1;
        super.image = name+".png";
    }
    @Override
    public String getName() {
        return name;
    }
}
