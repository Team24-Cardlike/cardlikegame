package org.example.Model.OpponentFactories.DeclaredBosses;

import org.example.Model.OpponentFactories.BossOpponent;

public class HeimdallBoss extends BossOpponent {

    public HeimdallBoss() {
        super("Heimdall", 40, 10, 3, "Heimdall.png");
        super.setHealth(getMaxHealth());
    }
}
