package org.example.Model.OpponentFactories.DeclaredBosses;

import org.example.Model.OpponentFactories.BossOpponent;

public class OdenBoss extends BossOpponent {

    public OdenBoss() {
        super.name = "Oden";
        super.maxHealth = 1500;
        super.damage = 40;
        super.turns = 2;
        super.image = name+".png";
    }
    @Override
    public String getName() {
        return name;
    }
}
