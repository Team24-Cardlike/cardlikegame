package org.example.Model.OpponentFactories.DeclaredBosses;

import org.example.Model.OpponentFactories.BossOpponent;

public class OdenBoss extends BossOpponent {

    public OdenBoss() {
        super("Oden", 1500, 40, 2, "Oden.png");
        super.setHealth(getMaxHealth());
    }
}
