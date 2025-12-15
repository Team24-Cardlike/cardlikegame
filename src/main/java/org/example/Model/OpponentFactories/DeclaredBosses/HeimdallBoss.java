package org.example.Model.OpponentFactories.DeclaredBosses;

import org.example.Model.OpponentFactories.BossOpponent;

public class HeimdallBoss extends BossOpponent {

    public HeimdallBoss() {
        super.name = "Heimdall";
        super.maxHealth = 400;
        super.damage = 10;
        super.turns = 3;
        super.image = name+".png";
    }

    @Override
    public String getName() {
        return name;
    }
}
