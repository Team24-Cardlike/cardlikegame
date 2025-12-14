package org.example.Model.OpponentFactories.DeclaredBosses;

import org.example.Model.OpponentFactories.BossOpponent;

public class TorBoss extends BossOpponent {

    @Override
    public void Create() {
        super.name = "Tor";
        super.maxHealth = 1000;
        super.damage = 30;
        super.turns = 3;
        super.image = name+".png";
    }
}
