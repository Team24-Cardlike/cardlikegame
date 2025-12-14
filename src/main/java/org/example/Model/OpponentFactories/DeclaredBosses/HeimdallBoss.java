package org.example.Model.OpponentFactories.DeclaredBosses;

import org.example.Model.OpponentFactories.BossOpponent;

public class HeimdallBoss extends BossOpponent {

    @Override
    public void Create() {
        super.name = "Heimdall";
        super.maxHealth = 400;
        super.damage = 10;
        super.turns = 3;
        super.image = name+".png";
    }
}
